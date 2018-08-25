package com.qiuchenly.comicparse.MVP.UI.Activitys

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.qiuchenly.comicparse.Bean.ComicBookInfo
import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.Bean.HotComicStrut
import com.qiuchenly.comicparse.MVP.Contract.ComicDetailContract
import com.qiuchenly.comicparse.MVP.Presenter.ComicDetailsPresenter
import com.qiuchenly.comicparse.MVP.UI.Adapter.ComicPageAda
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Simple.AppManager
import com.qiuchenly.comicparse.Simple.BaseApp
import com.qiuchenly.comicparse.Simple.WaveSideBarView
import com.qiuchenly.comicparse.VolleyImp.BaseURL
import com.r0adkll.slidr.Slidr
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_comicdetails.*
import org.jetbrains.anko.find

class ComicDetails : BaseApp<ComicDetailContract.Presenter>(), ComicDetailContract.View, ComicPageAda.OnSaveCB {
    override fun pleaseSave2DB() {
        mPres.Save2DB(ComicBookInfo_Recently().apply {
            this.BookName = comicInfo.BookName
            this.BookImgSrc = comicInfo.BookImgSrc
            this.BookLink = comicInfo.BookLink
            this.Author = comicInfo.Author
        })
    }

    override fun getScoreSucc(rate: String) {
        tv_bookScore.text = "网友评分：$rate"
    }

    @SuppressLint("SetTextI18n")
    override fun GetInfoSucc(author: String,
                             updateTime: String,
                             hits: String,
                             category: String,
                             introduction: String,
                             retPageList: ArrayList<ComicBookInfo>) {
        comicInfo.Author = author
        tv_bookName.text = comicInfo.BookName
        tv_bookAuthor.text = "原著作者：$author"
        tv_bookCategory.text = "剧情类别：$category"
        tv_bookUpdateTime.text = "最后更新：$updateTime"
        tv_bookIntroduction.text = "简介：" + introduction.trim()
        comicPageAdas?.setData(retPageList)
        comicPageAdas?.sort(1)
        val size = (1..retPageList.size).map { it.toString() }
        mWaveSideBar.letters = size
        mWaveSideBar.setOnTouchLetterChangeListener { letter, newChoose -> scrollWithPosition(newChoose) }

        val point = realm.where(ComicBookInfo_Recently::class.java)
                .equalTo("BookName", comicInfo.BookName)
                .findFirst()
                ?.BookName_read_point
        if (point != null)
            retPageList.forEachIndexed { index, comicBookInfo ->
                if (comicBookInfo.title == point)
                    scrollWithPosition(retPageList.size - index - 1)
            }
    }

    override fun scrollWithPosition(position: Int) {
        rv_comicPage.scrollToPosition(position)
        val manager = rv_comicPage.layoutManager as LinearLayoutManager
        manager.scrollToPositionWithOffset(position, 0)
    }

    private var comicInfo = HotComicStrut()

    override fun getLayoutID(): Int {
        return R.layout.activity_comicdetails
    }

    override fun setPres(mPres: ComicDetailContract.Presenter) {
        this.mPres = mPres
    }

    var comicPageAdas: ComicPageAda? = null

    lateinit var fa_add_local_list: FloatingActionButton
    lateinit var rv_comicPage: RecyclerView
    lateinit var tv_bookScore: TextView
    lateinit var tv_bookName: TextView
    lateinit var tv_bookAuthor: TextView
    lateinit var tv_bookCategory: TextView
    lateinit var tv_bookUpdateTime: TextView
    lateinit var tv_bookIntroduction: TextView
    lateinit var mWaveSideBar: WaveSideBarView
    lateinit var img_book: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Slidr.attach(this)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        ComicDetailsPresenter(this)
        fa_add_local_list = find(R.id.add_local_list)
        rv_comicPage = find(R.id.rv_comicPage)
        tv_bookScore = find(R.id.tv_bookScore)
        tv_bookName = find(R.id.tv_bookName)
        tv_bookAuthor = find(R.id.tv_bookAuthor)
        tv_bookCategory = find(R.id.tv_bookCategory)
        tv_bookUpdateTime = find(R.id.tv_bookUpdateTime)
        tv_bookIntroduction = find(R.id.tv_bookIntroduction)
        mWaveSideBar = find(R.id.mWaveSideBar)
        img_book = find(R.id.img_book)



        fa_add_local_list.setOnClickListener {
            val book = realm.where(HotComicStrut::class.java).equalTo("BookName", comicInfo.BookName).findFirst()
            if (book == null) {
                realm.beginTransaction()
                realm.createObject(HotComicStrut::class.java, comicInfo.BookName)
                        .apply {
                            this.Author = comicInfo.Author
                            this.LastedPage_src = comicInfo.LastedPage_src
                            this.LastedPage_name = comicInfo.LastedPage_name
                            this.BookLink = comicInfo.BookLink
                            this.BookImgSrc = comicInfo.BookImgSrc
                        }
                realm.commitTransaction()
                ShowErrorMsg("已加入本地图书列表！")
            } else {
                realm.executeTransaction {
                    book.deleteFromRealm()
                }
                ShowErrorMsg("移除成功！")
            }
            (AppManager.getActivity(MainSwitch::class.java) as MainSwitch).updateInfo()
            initFB()
        }

        val string = (intent.extras["data"] as String).split("|")
        comicInfo = HotComicStrut().apply {
            BookName = string[0]
            BookImgSrc = (if (string[1].indexOf(BaseURL.BASE_URL) == -1) BaseURL.BASE_URL else "") + string[1]
            LastedPage_name = string[2]
            LastedPage_src = (if (string[1].indexOf(BaseURL.BASE_URL) == -1) BaseURL.BASE_URL else "") + string[3]
            BookLink = (if (string[1].indexOf(BaseURL.BASE_URL) == -1) BaseURL.BASE_URL else "") + string[4]
        }
        val point = realm.where(ComicBookInfo_Recently::class.java)
                .equalTo("BookName", comicInfo.BookName)
                .findFirst()
                ?.BookName_read_point
        comicPageAdas = ComicPageAda(this, point, this)

        rv_comicPage.layoutManager = LinearLayoutManager(this)
        rv_comicPage.adapter = comicPageAdas

        Glide.with(this)
                .load(comicInfo.BookImgSrc)
                .crossFade(500)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_book)

        Glide.with(this)
                .load(comicInfo.BookImgSrc)
                .crossFade(500)
                .bitmapTransform(BlurTransformation(this, 20))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(comicDetails_img)


        mPres.initPageInfo(comicInfo.BookLink!!)
        initFB()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            fa_add_local_list.visibility = View.GONE
        return super.onKeyUp(keyCode, event)
    }


    fun initFB() {
        if (realm.where(HotComicStrut::class.java).equalTo("BookName", comicInfo.BookName).findFirst() != null) {
            fa_add_local_list.setImageResource(R.drawable.ic_remove_black_24dp)
        } else {
            fa_add_local_list.setImageResource(R.drawable.ic_add_black_24dp)
        }
    }
}
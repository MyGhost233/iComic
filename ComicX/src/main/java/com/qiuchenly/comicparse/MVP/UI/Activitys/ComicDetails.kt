package com.qiuchenly.comicparse.MVP.UI.Activitys

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
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
import com.qiuchenly.comicparse.Service.DownloadService
import com.qiuchenly.comicparse.Simple.AppManager
import com.qiuchenly.comicparse.Simple.BaseApp
import com.qiuchenly.comicparse.VolleyImp.BaseURL
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_comicdetails.*
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.find


class ComicDetails :
        BaseApp<ComicDetailContract.Presenter>(),
        ComicDetailContract.View,
        ComicPageAda.OnSaveCB {

    override fun onProgressChanged() {

    }

    override fun getUISet(mSet: UISet): UISet {
        return mSet.apply {
            isSlidr = true
        }
    }

    override fun pleaseSave2DB() {
        mPres?.Save2DB(ComicBookInfo_Recently().apply {
            this.BookName = comicInfo.BookName
            this.BookImgSrc = comicInfo.BookImgSrc
            this.BookLink = comicInfo.BookLink
            this.Author = comicInfo.Author
        })
    }

    override fun getScoreSucc(rate: String) {
        //tv_bookScore.text = "网友评分：$rate"
    }

    @SuppressLint("SetTextI18n")
    override fun GetInfoSucc(author: String,
                             updateTime: String,
                             hits: String,
                             category: String,
                             introduction: String,
                             retPageList: ArrayList<ComicBookInfo>) {
        comicInfo.Author = author
        tv_bookAuthor.text = "原著作者：$author"
        tv_bookCategory.text = "剧情类别：$category"
        //tv_bookUpdateTime.text = "最后更新：$updateTime"
        //tv_bookIntroduction.text = "简介：" + introduction.trim()
        tv_bookname_title.text = comicInfo.BookName
        tv_bookname.text = comicInfo.BookName
        comicPageAdas?.setData(retPageList)
        comicPageAdas?.sort(1)
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

    var comicPageAdas: ComicPageAda? = null

    lateinit var fa_add_local_list: FloatingActionButton
    lateinit var rv_comicPage: RecyclerView
    lateinit var tv_bookScore: TextView
    lateinit var tv_bookAuthor: TextView
    lateinit var tv_bookCategory: TextView
    lateinit var tv_bookUpdateTime: TextView
    lateinit var tv_bookIntroduction: TextView
    lateinit var img_book: ImageView
    lateinit var mConnect: ServiceConnection
    var mBinder: DownloadService.DownloadBinder? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ComicDetailsPresenter(this)
        fa_add_local_list = find(R.id.add_local_list)
        rv_comicPage = find(R.id.rv_comicPage)
        tv_bookAuthor = find(R.id.tv_bookAuthor)
        tv_bookCategory = find(R.id.tv_bookCategory)
        img_book = find(R.id.comicDetails_img_real)
        mConnect = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {

            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                mBinder = service as DownloadService.DownloadBinder
            }
        }

        download.setOnClickListener {
            //this will call with download service
            if (mBinder != null) {
                if (mBinder!!.hasBookInList(comicInfo)) {
                    ShowErrorMsg("已在下载列表中")
                } else {
                    mBinder?.download(comicInfo, this)
                }
            }
        }

        bindService(
                Intent(this, DownloadService::class.java),
                mConnect,
                Context.BIND_AUTO_CREATE)
        fa_add_local_list.setOnClickListener {
            val book =
                    realm.where(HotComicStrut::class.java)
                            .equalTo("BookName",
                                    comicInfo.BookName)
                            .findFirst()
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

        mPres?.initPageInfo(comicInfo.BookLink!!)
        initFB()

        comicDetails_img.alpha = 0f
        tv_bookname_title.alpha = 0f
        appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val CurrentPercents = (-verticalOffset * 1f) / appBarLayout.totalScrollRange
            comicDetails_img.alpha = CurrentPercents
            details.alpha = 1f - CurrentPercents
            tv_bookname.alpha = 1f - CurrentPercents
            tv_bookname_title.alpha = CurrentPercents
        }
        back_up.setOnClickListener { finish() }
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

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mConnect)
        mBinder = null
    }
}
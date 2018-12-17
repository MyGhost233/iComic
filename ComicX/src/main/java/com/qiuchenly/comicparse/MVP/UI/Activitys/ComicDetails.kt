package com.qiuchenly.comicparse.MVP.UI.Activitys

import android.annotation.SuppressLint
import android.app.Service
import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
import com.qiuchenly.comicparse.Simple.BaseApp
import com.qiuchenly.comicparse.VolleyImp.BaseURL
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_comicdetails.*
import org.jetbrains.anko.find


class ComicDetails :
        BaseApp<ComicDetailContract.Presenter>(),
        ComicDetailContract.View,
        ComicPageAda.OnSaveCB {
    //==============================   变量声明   ===================================================
    private var comicInfo = HotComicStrut()
    private var comicPageAdas: ComicPageAda? = null
    private lateinit var rv_comicPage: RecyclerView
    private lateinit var tv_bookAuthor: TextView
    private lateinit var tv_bookCategory: TextView
    private lateinit var mRealImageNoBlur: ImageView
    private lateinit var mServerConnect: ServiceConnection
    var mBinder: DownloadService.DownloadBinder? = null

    //==============================   代码整理 界面预设  ============================================

    override fun getUISet(mSet: UISet): UISet {
        return mSet.apply {
            isSlidr = true
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_comicdetails
    }

    //==============================   网络数据回调  ================================================
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
        tv_bookAuthor.text = author
        tv_bookCategory.text = category
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

    override fun onProgressChanged() {

    }

    override fun scrollWithPosition(position: Int) {
        rv_comicPage.scrollToPosition(position)
        val manager = rv_comicPage.layoutManager as LinearLayoutManager
        manager.scrollToPositionWithOffset(position, 0)
    }

    //==============================   常规系统初始化方法  ============================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ComicDetailsPresenter(this)
        rv_comicPage = find(R.id.rv_comicPage)
        tv_bookAuthor = find(R.id.tv_bookAuthor)
        tv_bookCategory = find(R.id.tv_bookCategory)
        mRealImageNoBlur = find(R.id.comicDetails_img_real)
        mServerConnect = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                mBinder = null
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                mBinder = service as DownloadService.DownloadBinder
            }
        }

        mBookDownload.setOnClickListener {
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
                mServerConnect,
                Context.BIND_AUTO_CREATE)

        /* add_local_list.setOnClickListener {
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
                 add_local_list_iv.setImageResource(R.drawable.ic_remove_black_24dp)
             } else {
                 realm.executeTransaction {
                     book.deleteFromRealm()
                 }
                 ShowErrorMsg("移除成功！")
                 add_local_list_iv.setImageResource(R.drawable.ic_add_black_24dp)
             }
             (AppManager.getActivity(MainSwitch::class.java) as MainSwitch).updateInfo()
         }*/

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
                .into(mRealImageNoBlur)

        Glide.with(this)
                .load(comicInfo.BookImgSrc)
                .crossFade(500)
                .bitmapTransform(BlurTransformation(this, 20))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(comicDetails_img)

        mPres?.initPageInfo(comicInfo.BookLink!!)

        /* if (realm.where(HotComicStrut::class.java).equalTo("BookName", comicInfo.BookName).findFirst() != null) {
             add_local_list_iv.setImageResource(R.drawable.ic_remove_black_24dp)
         }*/

        comicDetails_img.alpha = 0f
        tv_bookname_title.alpha = 0f
        //此处实现淡入淡出效果
        appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val mCurrentPercents = (-verticalOffset * 1f) / appBarLayout.totalScrollRange
            comicDetails_img.alpha = mCurrentPercents//实现渐变模糊特效
            details.alpha = 1f - mCurrentPercents
            tv_bookname.alpha = 1f - mCurrentPercents
            tv_bookname_title.alpha = mCurrentPercents
        }
        back_up.setOnClickListener { finish() }
        mShareButton.setOnClickListener {
            val mClipboardManager = getSystemService(Service.CLIPBOARD_SERVICE) as ClipboardManager
            mClipboardManager.primaryClip = ClipData.newPlainText("text", comicInfo.BookLink)
            ShowErrorMsg("已复制漫画网站网页地址!")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mServerConnect)
        mBinder = null
    }
}
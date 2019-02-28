package com.qiuchenly.comicparse.Modules.ComicDetailsActivity

import android.annotation.SuppressLint
import android.app.Service
import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.PagerAdapter
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.qiuchenly.comicparse.BaseImp.BaseApp
import com.qiuchenly.comicparse.BaseImp.BaseNavigatorCommon
import com.qiuchenly.comicparse.Bean.ComicBookInfo
import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.Http.BaseURL
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Adapter.ComicPageAda
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Fragments.ComicBasicInfo.BasicInfo
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Fragments.ComicList.ComicList
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface.ComicDetailContract
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ViewModel.ComicDetailsViewModel
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.Modules.RecentlyReading.Adapter.RecentlyPagerAdapter
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Service.DownloadService
import com.qiuchenly.comicparse.Utils.CustomUtils
import com.qiuchenly.comicparse.Utils.CustomUtils.subStr
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_comicdetails.*
import kotlinx.android.synthetic.main.view_magic_indicator_base.*
import org.jetbrains.anko.find

class ComicDetails :
        BaseApp(),
        ComicDetailContract.View,
        ComicPageAda.OnSaveCB {
    override fun getLayoutID(): Int? {
        return R.layout.activity_comicdetails
    }

    override fun onLoading() {
        onFailed.visibility = View.INVISIBLE
        onSuccess.visibility = View.INVISIBLE
        onFailed.isClickable = false
        onLoading.visibility = View.VISIBLE
    }

    override fun onLoadSuccess() {
        onFailed.visibility = View.INVISIBLE
        onSuccess.visibility = View.VISIBLE
        onLoading.visibility = View.INVISIBLE
        onFailed.isClickable = false
    }


    override fun onLoadFailed() {
        onFailed.isClickable = true
        onFailed.setOnClickListener {
            mViewModel.getBookDetails(subStr(comicInfo.BookLink!!, "comic/", ".html"))
        }
        onFailed.visibility = View.VISIBLE
        onSuccess.visibility = View.INVISIBLE
        onLoading.visibility = View.INVISIBLE
    }

    //==============================   变量声明   ===================================================
    private var comicInfo = HotComicStrut()
    private lateinit var mBookAuthor: TextView
    private lateinit var mBookCategory: TextView
    private lateinit var mRealImageNoBlur: ImageView
    private lateinit var mServerConnect: ServiceConnection
    var mBinder: DownloadService.DownloadBinder? = null
    private lateinit var mViewModel: ComicDetailsViewModel

    private lateinit var onSuccess: CoordinatorLayout
    private lateinit var onFailed: TextView
    private lateinit var onLoading: ProgressBar

    //==============================   代码整理 界面预设  =============================================

    override fun getUISet(mSet: BaseApp.UISet): UISet {
        return mSet.apply {
            isSlidr = true
        }
    }

    //==============================   网络数据回调  =================================================
    override fun pleaseSave2DB() {
        mViewModel.Save2DB(ComicBookInfo_Recently().apply {
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
        mBookAuthor.text = author
        mBookCategory.text = category
        tv_bookname_title_small.text = author
        //tv_bookUpdateTime.text = "最后更新：$updateTime"
        //tv_bookIntroduction.text = "简介：" + introduction.trim()
        tv_bookname_title.text = comicInfo.BookName
        tv_bookname.text = comicInfo.BookName
        val point = Realm.getDefaultInstance().where(ComicBookInfo_Recently::class.java)
                .equalTo("BookName", comicInfo.BookName)
                .findFirst()
                ?.BookName_read_point
        if (point != null) {
            retPageList.forEachIndexed { index, comicBookInfo ->
                if (comicBookInfo.title == point)
                    scrollWithPosition(retPageList.size - index - 1)
            }
        }

        ComicList.getInstance().initializationData(retPageList)
        BasicInfo.getInstance().setDefaultIndexUrl(retPageList[retPageList.size].link!!)
        CustomUtils.loadImage(comicInfo.BookImgSrc!!, mRealImageNoBlur, 0, null, 500)
        CustomUtils.loadImage(comicInfo.BookImgSrc!!, comicDetails_img, 300, 500)
    }

    override fun onProgressChanged() {

    }

    override fun scrollWithPosition(position: Int) {
        ComicList.getInstance().scrollWithPosition(position)
    }

    //==============================   常规系统初始化方法  ============================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ComicDetailsPresenter(this)

        mViewModel = ComicDetailsViewModel(this)
        mBookAuthor = find(R.id.tv_bookAuthor)
        mBookCategory = find(R.id.tv_bookCategory)
        mRealImageNoBlur = find(R.id.comicDetails_img_real)

        onLoading = find(R.id.load_ing)
        onFailed = find(R.id.load_failed)
        onSuccess = find(R.id.mCoordinatorLayout)

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

        val string = (intent.extras["data"] as String).split("|")
        comicInfo = HotComicStrut().apply {
            BookName = string[0]
            BookImgSrc = (if (string[1].indexOf(BaseURL.BASE_URL) == -1) BaseURL.BASE_URL else "") + string[1]
            LastedPage_name = string[2]
            LastedPage_src = (if (string[1].indexOf(BaseURL.BASE_URL) == -1) BaseURL.BASE_URL else "") + string[3]
            BookLink = (if (string[1].indexOf(BaseURL.BASE_URL) == -1) BaseURL.BASE_URL else "") + string[4]
        }

        val mFragmentsList = arrayListOf(
                RecentlyPagerAdapter.Struct("漫画信息", BasicInfo.getInstance(this, comicInfo)),
                RecentlyPagerAdapter.Struct("漫画章节", ComicList.getInstance(comicInfo, this, this))
        )
        /* if (realm.where(HotComicStrut::class.java).equalTo("BookName", comicInfo.BookName).findFirst() != null) {
             add_local_list_iv.setImageResource(R.drawable.ic_remove_black_24dp)
         }*/

        comicDetails_img.alpha = 0f
        mTitleLayout.alpha = 0f
        //此处实现淡入淡出效果
        appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val mCurrentPercents = (-verticalOffset * 1f) / appBarLayout.totalScrollRange
            comicDetails_img.alpha = mCurrentPercents//实现渐变模糊特效
            details.alpha = 1f - mCurrentPercents
            tv_bookname.alpha = 1f - mCurrentPercents
            mTitleLayout.alpha = mCurrentPercents
        }
        back_up.setOnClickListener { finish() }
        mShareButton.setOnClickListener {
            val mClipboardManager = getSystemService(Service.CLIPBOARD_SERVICE) as ClipboardManager
            mClipboardManager.primaryClip = ClipData.newPlainText("text", comicInfo.BookLink)
            ShowErrorMsg("已复制漫画网站网页地址!")
        }

        mAdapter = RecentlyPagerAdapter(supportFragmentManager, mFragmentsList)
        mDetailsInfoViewPager.adapter = mAdapter
        BaseNavigatorCommon.setUpWithPager(
                this,
                mFragmentsList,
                magic_indicator,
                mDetailsInfoViewPager)


        mViewModel.getBookDetails(subStr(comicInfo.BookLink!!, "comic/", ".html"))
    }

    private var mAdapter: PagerAdapter? = null

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mServerConnect)
        mBinder = null
        mViewModel.cancel()
    }
}
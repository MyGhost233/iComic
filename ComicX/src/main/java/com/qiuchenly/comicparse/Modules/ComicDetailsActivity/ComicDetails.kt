package com.qiuchenly.comicparse.Modules.ComicDetailsActivity

import android.annotation.SuppressLint
import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ServiceConnection
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.qiuchenly.comicparse.BaseImp.BaseApp
import com.qiuchenly.comicparse.BaseImp.BaseNavigatorCommon
import com.qiuchenly.comicparse.Bean.ComicInfoBean
import com.qiuchenly.comicparse.Bean.DataItem
import com.qiuchenly.comicparse.Core.ActivityKey
import com.qiuchenly.comicparse.Enum.ComicSourceType
import com.qiuchenly.comicparse.Http.Bika.ComicListObject
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Fragments.ComicBasicInfo.ComicBasicInfo
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Fragments.ComicList.ComicList
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface.ComicDetailContract
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ViewModel.ComicDetailsViewModel
import com.qiuchenly.comicparse.Modules.RecentlyReading.Adapter.SuperPagerAdapter
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Service.DownloadService
import com.qiuchenly.comicparse.Utils.CustomUtils
import kotlinx.android.synthetic.main.activity_comicdetails.*
import kotlinx.android.synthetic.main.layout_loading.*
import kotlinx.android.synthetic.main.view_magic_indicator_base.*
import kotlinx.android.synthetic.main.vpitem_top_ad.*

class ComicDetails :
        BaseApp(),
        ComicDetailContract.View {
    override fun onAppBarChange(position: Int) {
        if (position == 0) {
            appBarLayout.setExpanded(true, true)
        } else {
            appBarLayout.setExpanded(false, true)
        }
    }

    override fun getLayoutID(): Int? {
        return R.layout.activity_comicdetails
    }

    override fun onLoading() {
        onFailed?.visibility = View.INVISIBLE
        onSuccess?.visibility = View.INVISIBLE
        onFailed?.isClickable = false
        onLoading?.visibility = View.VISIBLE
    }

    override fun onLoadSuccess() {
        onFailed?.visibility = View.INVISIBLE
        onSuccess?.visibility = View.VISIBLE
        onLoading?.visibility = View.INVISIBLE
        onFailed?.isClickable = false
    }


    override fun onLoadFailed() {
        onFailed?.isClickable = true
        onFailed?.setOnClickListener {

        }
        onFailed?.visibility = View.VISIBLE
        onSuccess?.visibility = View.INVISIBLE
        onLoading?.visibility = View.INVISIBLE
    }

    //==============================   变量声明   ===================================================
    var mBinder: DownloadService.DownloadBinder? = null
    private var mViewModel: ComicDetailsViewModel? = null

    private var onSuccess: CoordinatorLayout? = null
    private var onFailed: TextView? = null
    private var onLoading: ProgressBar? = null

    private var mComicInfo: ComicListObject? = null

    //==============================   代码整理 界面预设  =============================================

    override fun getUISet(mSet: BaseApp.UISet): UISet {
        return mSet.apply {
            isSlidr = true
        }
    }

    //==============================   网络数据回调  =================================================

    override fun onProgressChanged() {

    }

    override fun scrollWithPosition(position: Int) {
        if (mAdapter != null) {
            val mFragment = mAdapter?.getInstance("章节") as ComicList
            mFragment.scrollWithPosition(position)
        }
    }

    private var mComicTag = "SimpleName|SimpleCode"
    private var mPageChange: ViewPager.OnPageChangeListener? = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            onAppBarChange(position)
        }
    }

    //==============================   常规系统初始化方法  ============================================
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ComicDetailsViewModel(this)

        onLoading = load_ing
        onFailed = load_failed
        onSuccess = mCoordinatorLayout

        //对Intent传来的数据做处理
        val mComicStr = intent.getStringExtra(ActivityKey.KEY_BIKA_CATEGORY_JUMP)
        if (mComicStr.isNullOrEmpty()) finish()
        val baseInfo = Gson().fromJson(mComicStr, ComicInfoBean::class.java)

        var mComicSourceName = "预设来源"
        var mComicSrc = ""
        var mComicTitle = ""
        var mComicAuthor = ""
        var mBookCategory = "暂无分类"

        when (baseInfo.mComicType) {
            ComicSourceType.BIKA -> {
                mComicSourceName = "哔咔漫画源"
                mComicSrc = baseInfo.mComicImg
                mComicTag = baseInfo.mComicName + "|" + baseInfo.mComicID
                mComicInfo = Gson().fromJson(baseInfo.mComicString, ComicListObject::class.java)
                mComicTitle = mComicInfo!!.title
                mComicAuthor = mComicInfo!!.author
                mBookCategory = baseInfo.mComicTAG
            }
            ComicSourceType.DMZJ -> {
                mComicSourceName = "动漫之家漫画源"
                val mComic = Gson().fromJson(baseInfo.mComicString, DataItem::class.java)
                mComicSrc = mComic.cover
                mComicAuthor = mComic.sub_title
                mComicTitle = mComic.title
            }
        }
        onLoadSuccess()
        //=================  初始化界面数据  ===================

        CustomUtils.loadImageEx(this, mComicSrc, mRealImageNoBlur, 0, null)
        CustomUtils.loadImage(this, mComicSrc, comicDetails_img, 10, 20)

        tv_bookname.text = mComicTitle
        mBookAuthor.text = "来源:$mComicAuthor"
        tv_bookname_title_small.text = mBookAuthor.text
        mBookCategoryView.text = "类别:$mBookCategory"

        val mFragmentsList = arrayListOf(
                SuperPagerAdapter.Struct("简介", ComicBasicInfo().apply {
                    setUI(baseInfo)
                }),
                SuperPagerAdapter.Struct("章节", ComicList().apply {
                    setUI(baseInfo)
                })
        )
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
            mClipboardManager.primaryClip = ClipData.newPlainText("text", mComicTag)
            ShowErrorMsg("已复制漫画相关信息")
        }

        mAdapter = SuperPagerAdapter(supportFragmentManager, mFragmentsList)
        mComicInfoViewPager.adapter = mAdapter
        BaseNavigatorCommon.setUpWithPager(
                this,
                mFragmentsList,
                magic_indicator,
                mComicInfoViewPager)

        mComicInfoViewPager.addOnPageChangeListener(mPageChange!!)
        tv_bookname_title.text = mComicSourceName
    }

    //获取单一实例
    private var mAdapter: SuperPagerAdapter? = null

    override fun onDestroy() {
        super.onDestroy()
//        unbindService(mServerConnect)
        mComicInfoViewPager.removeOnPageChangeListener(mPageChange!!)
        mPageChange = null
        mViewModel?.cancel()
        mViewModel = null
        mBinder = null
        mAdapter = null

        onLoading = null
        onFailed = null
        onSuccess = null
    }
}
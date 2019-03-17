package com.qiuchenly.comicparse.Modules.ComicDetailsActivity

import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.qiuchenly.comicparse.BaseImp.BaseApp
import com.qiuchenly.comicparse.BaseImp.BaseNavigatorCommon
import com.qiuchenly.comicparse.Bean.ComicInfoBean
import com.qiuchenly.comicparse.Enum.ComicSourcceType
import com.qiuchenly.comicparse.Http.Bika.ComicListObject
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Fragments.ComicBasicInfo.BasicInfo
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Fragments.ComicList.ComicList
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface.ComicDetailContract
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ViewModel.ComicDetailsViewModel
import com.qiuchenly.comicparse.Modules.RecentlyReading.Adapter.RecentlyPagerAdapter
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Service.DownloadService
import kotlinx.android.synthetic.main.activity_comicdetails.*
import kotlinx.android.synthetic.main.view_magic_indicator_base.*
import org.jetbrains.anko.find

class ComicDetails :
        BaseApp(),
        ComicDetailContract.View {
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

        }
        onFailed.visibility = View.VISIBLE
        onSuccess.visibility = View.INVISIBLE
        onLoading.visibility = View.INVISIBLE
    }

    //==============================   变量声明   ===================================================
    private lateinit var mBookAuthor: TextView
    private lateinit var mBookCategory: TextView
    private lateinit var mRealImageNoBlur: ImageView
    private lateinit var mServerConnect: ServiceConnection
    var mBinder: DownloadService.DownloadBinder? = null
    private lateinit var mViewModel: ComicDetailsViewModel

    private lateinit var onSuccess: CoordinatorLayout
    private lateinit var onFailed: TextView
    private lateinit var onLoading: ProgressBar

    private lateinit var mComicInfo: ComicListObject

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
        ComicList.getInstance().scrollWithPosition(position)
    }

    //==============================   常规系统初始化方法  ============================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            /*if (mBinder != null) {
                if (mBinder!!.hasBookInList(comicInfo)) {
                    ShowErrorMsg("已在下载列表中")
                } else {
                    mBinder?.download(comicInfo, this)
                }
            }*/
        }

        /*bindService(
                Intent(this, DownloadService::class.java),
                mServerConnect,
                Context.BIND_AUTO_CREATE)*/

        val mComicStr = intent.getStringExtra("comic")
        val baseInfo = Gson().fromJson(mComicStr, ComicInfoBean::class.java)
        mComicInfo = Gson().fromJson(baseInfo.mComicString, ComicListObject::class.java)
        val mFragmentsList = arrayListOf(
                RecentlyPagerAdapter.Struct("漫画信息", BasicInfo.getInstance(this, baseInfo)),
                RecentlyPagerAdapter.Struct("漫画章节", ComicList.getInstance(baseInfo, this))
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
            mClipboardManager.primaryClip = ClipData.newPlainText("text", "漫画信息")
            ShowErrorMsg("已复制漫画网站网页地址!")
        }

        mAdapter = RecentlyPagerAdapter(supportFragmentManager, mFragmentsList)
        mDetailsInfoViewPager.adapter = mAdapter
        BaseNavigatorCommon.setUpWithPager(
                this,
                mFragmentsList,
                magic_indicator,
                mDetailsInfoViewPager)

        mDetailsInfoViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    appBarLayout.setExpanded(true, true)
                }
            }
        })
        when (baseInfo.mComicType) {
            ComicSourcceType.BIKA -> {
                onLoadSuccess()
            }
        }
    }

    private var mAdapter: PagerAdapter? = null

    override fun onDestroy() {
        super.onDestroy()
//        unbindService(mServerConnect)
        mBinder = null
        mViewModel.cancel()
    }
}
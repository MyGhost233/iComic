package com.qiuchenly.comicparse.UI.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.qiuchenly.comicparse.UI.BaseImp.BaseApp
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_FAILED
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_ING
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_NO_MORE
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_SUCCESS
import com.qiuchenly.comicparse.Bean.ComicChapterData
import com.qiuchenly.comicparse.Bean.ComicInfoBean
import com.qiuchenly.comicparse.Core.ActivityKey
import com.qiuchenly.comicparse.Enum.ComicSourceType
import com.qiuchenly.comicparse.UI.adapter.ComicReadingAdapter
import com.qiuchenly.comicparse.UI.viewModel.ReadViewModel
import com.qiuchenly.comicparse.UI.view.ReaderContract
import com.qiuchenly.comicparse.ProductModules.Bika.ComicEpisodeObject
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.activity_reader_page.*


class ReadPage : BaseApp(), ReaderContract.View, BaseRecyclerAdapter.LoaderListener {
    private var lastPoint = 0
    private var currentState = ON_LOAD_SUCCESS

    override fun showMsg(str: String) = ShowErrorMsg(str)

    override fun onLoadMore(isRetry: Boolean) {
        if (
                currentState != ON_LOAD_SUCCESS &&
                currentState != ON_LOAD_FAILED
        ) return
        if (nextUrl.isNotEmpty()) {
            currentState = ON_LOAD_ING
            //TODO 此处加载下一页
            when (mTempComicInfo!!.mComicType) {
                ComicSourceType.DMZJ -> {
                    mViewModel?.getDMZJImage(bookID, nextUrl)
                }
                ComicSourceType.BIKA -> {
                    mViewModel?.getBikaImage(bookID, nextUrl.toInt())
                }
            }
        } else {
            currentState = ON_LOAD_NO_MORE
            mComicImagePageAda?.onNoMore()
            //onFailed("没有更多信息了")
        }
    }

    override fun getUISet(mSet: BaseApp.UISet): UISet {
        return mSet.apply {
            isSlidr = true
        }
    }

    override fun onFailed(reasonStr: String) {
        if (currentState != ON_LOAD_FAILED) {
            ShowErrorMsg(reasonStr)
            currentState = ON_LOAD_FAILED
            mComicImagePageAda?.onLoadNextFailed()
        }
    }

    override fun onLoadSucc(lst: ArrayList<String>, next: String, currInfo: String, isOver: Boolean) {
        currentState = ON_LOAD_SUCCESS
        nextUrl = next
        if (isOver) {
            onLoadMore(true)
            return
        }

        when (mTempComicInfo!!.mComicType) {
            ComicSourceType.DMZJ -> {
                mPoint++
                if (mPoint < mDMZJChapter!!.size) {
                    nextUrl = mDMZJChapter!!.get(mPoint).chapter_id
                    currInfos.text = mDMZJChapter!!.get(mPoint - 1).chapter_title
                } else {
                    nextUrl = ""
                }
            }
            ComicSourceType.BIKA -> {
                mPoint++
                if (mPoint < mBikaChapter!!.size) {
                    nextUrl = mBikaChapter!!.get(mPoint).order.toString()
                    currInfos.text = mBikaChapter!!.get(mPoint - 1).title
                } else {
                    nextUrl = ""
                }
            }
            else -> {
            }
        }

        lastPoint = mComicImagePageAda?.itemCount!!
        mComicImagePageAda?.addData(lst)
        //currInfos.text = currInfo
        if (lastPoint < 0) {
            mAppBarComicReader.setExpanded(true, true)
            rv_comicRead_list.scrollToPosition(lastPoint)
        }
    }

    private var nextUrl = ""

    override fun getLayoutID(): Int {
        return R.layout.activity_reader_page
    }

    private var currUrl = ""
    private var mComicImagePageAda: ComicReadingAdapter? = null
    private var curr = -1

    var bookID = ""
    var order = 1

    private fun getArr2Str(clazz: ArrayList<String>): ArrayList<ComicChapterData> {
        val mArr = ArrayList<ComicChapterData>()
        clazz.forEach {
            mArr.add(Gson().fromJson(it, ComicChapterData::class.java))
        }
        return mArr
    }

    private fun getArr2StrA(clazz: ArrayList<String>): ArrayList<ComicEpisodeObject> {
        val mArr = ArrayList<ComicEpisodeObject>()
        clazz.forEach {
            mArr.add(Gson().fromJson(it, ComicEpisodeObject::class.java))
        }
        return mArr
    }

    private var mViewModel: ReadViewModel? = null
    private var mDMZJChapter: ArrayList<ComicChapterData>? = null
    private var mBikaChapter: ArrayList<ComicEpisodeObject>? = null
    private var mPoint = 0
    private var mTempComicInfo: ComicInfoBean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ReadViewModel(this)
        val mStr = intent.getStringExtra(ActivityKey.KEY_CATEGORY_JUMP)
        mTempComicInfo = Gson().fromJson(mStr, ComicInfoBean::class.java)
        mComicImagePageAda = ComicReadingAdapter(this)
        rv_comicRead_list.layoutManager = LinearLayoutManager(this)
        rv_comicRead_list.adapter = mComicImagePageAda
        rv_comicRead_list.isEnableScale = true //感谢这个作者的开源项目。https://github.com/PortgasAce/ZoomRecyclerView/blob/master/demo/src/main/java/com/portgas/view/demo/MainActivity.java
        //=============  初始化界面数据  ===============
        bookID = mTempComicInfo!!.mComicID
        when (mTempComicInfo!!.mComicType) {
            ComicSourceType.DMZJ -> {
                mDMZJChapter = getArr2Str(Gson().fromJson(mTempComicInfo!!.mComicTAG, ArrayList<ComicChapterData>()::class.java) as ArrayList<String>)
                mPoint = mTempComicInfo!!.mComicString.toInt()
                val mBase = mDMZJChapter?.get(mPoint)
                currInfos.text = mBase?.chapter_title
                mViewModel?.getDMZJImage(bookID, mBase!!.chapter_id)
            }
            ComicSourceType.BIKA -> {
                mBikaChapter = getArr2StrA(Gson().fromJson(mTempComicInfo!!.mComicTAG, ArrayList<ComicEpisodeObject>()::class.java) as ArrayList<String>)
                mPoint = mTempComicInfo!!.mComicString.toInt()
                val mBase = mBikaChapter?.get(mPoint)
                currInfos.text = mBase?.title
                mViewModel?.getBikaImage(bookID, mBase!!.order)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.cancel()
        mViewModel = null
        mComicImagePageAda = null
        finish()
    }
}
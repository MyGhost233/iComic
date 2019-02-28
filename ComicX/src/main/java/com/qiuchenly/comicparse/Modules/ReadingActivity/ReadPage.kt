package com.qiuchenly.comicparse.Modules.ReadingActivity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.qiuchenly.comicparse.Modules.ReadingActivity.Adapter.ComicImagePageAda
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.BaseImp.BaseApp
import kotlinx.android.synthetic.main.activity_reader_page.*


class ReadPage : BaseApp(), ReaderContract.View, ComicImagePageAda.onLoadMore {
    private var lastPoint = 0
    private var currentState = ComicImagePageAda.ON_SUCC

    override fun showMsg(str: String) = ShowErrorMsg(str)

    override fun onLoadMore() {
        if (
                currentState != ComicImagePageAda.ON_SUCC &&
                currentState != ComicImagePageAda.ON_LOAD_FAILED
        ) return
        if (nextUrl.isNotEmpty()) {
            currentState = ComicImagePageAda.ON_LOADING
            mViewModel?.getParsePicList(nextUrl)
        } else {
            currentState = ComicImagePageAda.ON_NOMORE
            mComicImagePageAda?.onNoMore()
            onFailed("没有更多信息了")
        }
    }

    override fun getUISet(mSet: BaseApp.UISet): UISet {
        return mSet.apply {
            isSlidr = true
        }
    }

    override fun onFailed(reasonStr: String) {
        if (currentState != ComicImagePageAda.ON_LOAD_FAILED) {
            ShowErrorMsg(reasonStr)
            currentState = ComicImagePageAda.ON_LOAD_FAILED
            mComicImagePageAda?.onLoadNextFailed()
        }
    }

    override fun onLoadSucc(lst: ArrayList<String>, next: String, currInfo: String) {
        lastPoint = mComicImagePageAda?.itemCount!!
        mComicImagePageAda?.addData(lst)

        currInfos.text = currInfo

        nextUrl = next

        if (lastPoint < 0) {
            mAppBarComicReader.setExpanded(true, true)
            rv_comicRead_list.scrollToPosition(lastPoint)
        }
        currentState = ComicImagePageAda.ON_SUCC
    }

    private var nextUrl = ""

    override fun getLayoutID(): Int {
        return R.layout.activity_reader_page
    }

    private var currUrl = ""
    private var mComicImagePageAda: ComicImagePageAda? = null
    private var curr = -1

    private var mViewModel: ReadViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ReadViewModel(this)
        curr = intent.extras.getInt("curr")
        val url = intent.extras.getString("link")
        currUrl = url
        mComicImagePageAda = ComicImagePageAda(this)
        rv_comicRead_list.layoutManager = LinearLayoutManager(this)
        rv_comicRead_list.adapter = mComicImagePageAda
        mViewModel?.getParsePicList(url)
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.cancel()
    }
}
package com.qiuchenly.comicparse.Modules.ReadingActivity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.qiuchenly.comicparse.BaseImp.BaseApp
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_FAILED
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_ING
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_NO_MORE
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_SUCCESS
import com.qiuchenly.comicparse.Modules.ReadingActivity.Adapter.ComicImagePageAda
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
            mViewModel?.getParsePicList(nextUrl)
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
        lastPoint = mComicImagePageAda?.itemCount!!
        mComicImagePageAda?.addData(lst)
        currInfos.text = currInfo
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
    private var mComicImagePageAda: ComicImagePageAda? = null
    private var curr = -1

    var bookID = ""
    var order = 1

    var isBika = false
    private var mViewModel: ReadViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ReadViewModel(this)
        isBika = intent.getBooleanExtra("isBika", false)
        mComicImagePageAda = ComicImagePageAda(this)
        if (isBika) {
            bookID = intent.getStringExtra("bookID")
            order = intent.getIntExtra("order", 1)
            mComicImagePageAda?.setBikaMode()
            mViewModel?.getBikaImage(bookID, order)
        } else {
            curr = intent.extras.getInt("curr")
            val url = intent.extras.getString("link")
            currUrl = url
            mViewModel?.getParsePicList(url)
        }
        rv_comicRead_list.layoutManager = LinearLayoutManager(this)
        rv_comicRead_list.adapter = mComicImagePageAda

    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.cancel()
    }
}
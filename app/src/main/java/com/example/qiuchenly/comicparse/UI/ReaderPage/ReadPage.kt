package com.example.qiuchenly.comicparse.UI.ReaderPage

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.example.qiuchenly.comicparse.Adapter.ComicImagePageAda
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.BaseApp
import kotlinx.android.synthetic.main.activity_reader_page.*


class ReadPage : BaseApp<ReaderContract.Presenter>(), ReaderContract.View {

    override fun onFailed(reasonStr: String) {
        ShowErrorMsg(reasonStr)
    }

    var noMore = false
    override fun onLoadSucc(lst: ArrayList<String>, next: String, currInfo: String) {
        if (next.indexOf(".html") <= 0) {
            noMore = true
            onFailed("没有更多信息了")
            mComicImagePageAda?.notifyItemRemoved(mComicImagePageAda?.getData()?.size!! - 1)
            return
        }
        val pointRange = mComicImagePageAda?.getData()?.size
        mComicImagePageAda?.addData(lst)
        currInfos.text = currInfo
        nextUrl = next
        if (pointRange!! != 0)
            rv_comicRead_list.smoothScrollToPosition(pointRange)
        mAppBarComicReader.setExpanded(true)
    }

    private var nextUrl = ""

    override fun getLayoutID(): Int {
        return R.layout.activity_reader_page
    }

    override fun setPres(mPres: ReaderContract.Presenter) {
        this.mPres = mPres
    }

    private var mComicImagePageAda: ComicImagePageAda? = null
    private var curr = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        ReadPresenter(this)
        curr = intent.extras.getInt("curr")
//        intent.extras.getString("title")
        val url = intent.extras.getString("link")
        mComicImagePageAda = ComicImagePageAda()
        rv_comicRead_list.layoutManager = LinearLayoutManager(this)
        rv_comicRead_list.adapter = mComicImagePageAda
        rv_comicRead_list.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, state: Int) {
                if (state === RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView!!.layoutManager
                    val lastVisiblePosition: Int
                    lastVisiblePosition =
                            when (layoutManager) {
                                is GridLayoutManager -> (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                                is StaggeredGridLayoutManager -> {
                                    val into = IntArray((layoutManager as StaggeredGridLayoutManager).spanCount)
                                    (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(into)
                                    into.max()!!
                                }
                                else -> (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                            }
                    if (layoutManager.childCount > 0             //当当前显示的item数量>0
                            && lastVisiblePosition >= layoutManager.itemCount - 1           //当当前屏幕最后一个加载项位置>=所有item的数量
                            && layoutManager.itemCount > layoutManager.childCount) { // 当当前总Item数大于可见Item数
                        if (nextUrl != "" && !noMore) {
                            mPres.getParsePicList(nextUrl, this@ReadPage)
                        } else {
                            onFailed("没有更多信息了")
                            mComicImagePageAda?.notifyItemRemoved(mComicImagePageAda?.getData()?.size!! - 1)
                        }
                    }
                }
            }
        })

        mPres.getParsePicList(url, this)
    }
}
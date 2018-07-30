package com.example.qiuchenly.comicparse.UI.ReaderPage

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
        mComicImagePageAda?.addData(lst)
        currInfos.text = currInfo
        nextUrl = next
        mAppBarComicReader.setExpanded(true)
    }

    private var nextUrl = ""

    override fun getLayoutID(): Int {
        return R.layout.activity_reader_page
    }

    override fun setPres(mPres: ReaderContract.Presenter) {
        this.mPres = mPres
    }

    private var currUrl = ""
    private var mComicImagePageAda: ComicImagePageAda? = null
    private var curr = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        ReadPresenter(this)
        curr = intent.extras.getInt("curr")
//        intent.extras.getString("title")
        var url = intent.extras.getString("link")
        currUrl = url
        mComicImagePageAda = ComicImagePageAda()
        rv_comicRead_list.layoutManager = LinearLayoutManager(this)
        rv_comicRead_list.adapter = mComicImagePageAda
        rv_comicRead_list.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, state: Int) {
                val state1 = !recyclerView!!.canScrollVertically(1)
                if (state1) {
                    if (nextUrl != "" && !noMore) {
                        if (currUrl == nextUrl) {
                            println("the same as Url")
                        } else {
                            currUrl = nextUrl
                            mPres.getParsePicList(nextUrl, this@ReadPage)
                        }
                    } else {
                        onFailed("没有更多信息了")
                        mComicImagePageAda?.notifyItemRemoved(mComicImagePageAda?.getData()?.size!! - 1)
                    }
                }
            }
        })

        mPres.getParsePicList(url, this)
    }
}
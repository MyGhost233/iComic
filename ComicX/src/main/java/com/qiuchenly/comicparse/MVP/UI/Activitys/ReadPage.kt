package com.qiuchenly.comicparse.MVP.UI.Activitys

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.qiuchenly.comicparse.MVP.Contract.ReaderContract
import com.qiuchenly.comicparse.MVP.Presenter.ReadPresenter
import com.qiuchenly.comicparse.MVP.UI.Adapter.ComicImagePageAda
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Simple.BaseApp
import kotlinx.android.synthetic.main.activity_reader_page.*


class ReadPage : BaseApp<ReaderContract.Presenter>(), ReaderContract.View {
    override fun getUISet(mSet: UISet): UISet {
        return mSet.apply {
            isSlidr = true
        }
    }

    override fun onFailed(reasonStr: String) {
        ShowErrorMsg(reasonStr)
        loading = false
    }

    var noMore = false

    var lastPoint = 0
    override fun onLoadSucc(lst: ArrayList<String>, next: String, currInfo: String) {
        if (next.indexOf(".html") <= 0) {
            noMore = true
            onFailed("没有更多信息了")
            mComicImagePageAda?.setNoMore()
            mComicImagePageAda?.notifyItemChanged(mComicImagePageAda?.getData()?.size!!)
            return
        }
        lastPoint = mComicImagePageAda?.itemCount!!
        mComicImagePageAda?.addData(lst)//removeAdultPicture(lst)

        currInfos.text = currInfo
        getPres()?.updateReadPoint(currInfo)
        nextUrl = next
        loading = false

        if (lastPoint < 0) {
            mAppBarComicReader.setExpanded(true, true)
            rv_comicRead_list.scrollToPosition(lastPoint)
        }
    }

    private var nextUrl = ""

    override fun getLayoutID(): Int {
        return R.layout.activity_reader_page
    }

    override fun setPres(mPres: ReaderContract.Presenter) {
        this.mPres = mPres
    }

    private var loading = false
    private var currUrl = ""
    private var mComicImagePageAda: ComicImagePageAda? = null
    private var curr = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                if (loading || noMore) return//cancel request and did't report information to user
                if (state1) {
                    loading = true
                }
//                if (state1)
//                    Log.d("Qiuchen", "$nextUrl 滑动到最底部状态$state1 state = $state")
                if (state1) {
                    if (nextUrl != "" && !noMore) {
                        mPres?.getParsePicList(nextUrl, this@ReadPage)
                    } else {
                        noMore = true
                        onFailed("没有更多信息了")
                        mComicImagePageAda?.setNoMore()
                        mComicImagePageAda?.notifyItemChanged(mComicImagePageAda?.getData()?.size!!)
                    }
                }
            }
        })

        mPres?.getParsePicList(url, this)
    }
}
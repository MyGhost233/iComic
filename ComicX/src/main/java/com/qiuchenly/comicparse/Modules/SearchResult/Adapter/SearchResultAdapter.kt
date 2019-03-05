package com.qiuchenly.comicparse.Modules.SearchResult.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import com.qiuchenly.comicparse.BaseImp.BaseRVAdapter
import com.qiuchenly.comicparse.Http.BikaApi.ComicListObject
import com.qiuchenly.comicparse.Http.BikaApi.Tools
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ComicDetails
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Utils.CustomUtils
import kotlinx.android.synthetic.main.comic_local_list.view.*
import kotlinx.android.synthetic.main.loadmore_view.view.*

class SearchResultAdapter(private val mCallback: onLoadMore) : BaseRVAdapter<ComicListObject>() {
    override fun getLayout(viewType: Int): Int {
        return when (viewType) {
            ON_NORMAL -> {
                R.layout.comic_local_list
            }
            ON_LOADMORE -> {
                R.layout.loadmore_view
            }
            else -> {
                R.layout.comic_local_list
            }
        }
    }

    override fun getItemCount(): Int {
        return if (super.getItemCount() > 0) super.getItemCount() + 1 else 0
    }

    companion object {
        val ON_LOADMORE = 0x01
        val ON_LOADSUCC = 0x02
        val ON_NORMAL = 0x03

        object LoadStatus {
            val ON_LOADMORE = 0x01
            val ON_LOADSUCC = 0x02
            val ON_LOADFAILED = 0x03
            val ON_LOAD_NOMORE = 0x04
            val ON_LOADING = 0x05
        }
    }

    var current = LoadStatus.ON_LOADSUCC

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            super.getItemCount() -> {
                ON_LOADMORE
            }
            else -> {
                ON_NORMAL
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun InitUI(item: View, data: ComicListObject?, position: Int) {
        when (getItemViewType(position)) {
            ON_NORMAL -> {
                if (data != null) {
                    with(item) {
                        CustomUtils.loadImage(context, Tools.getThumbnailImagePath(data.thumb), bookNameImg, 0, 500)
                        bookName.text = data.title
                        bookAuthor.text = data.author
                        val categorys = data.categories.joinToString(prefix = "", postfix = ",")
                        curr_read.text = "分类:" + categorys.substring(0, categorys.length - 1)
                        updateTo.visibility = View.INVISIBLE
                        setOnClickListener {
                            context.startActivity(Intent(context, ComicDetails::class.java).apply {
                                putExtra("comicID", data.comicId)
                                putExtra("isBika", true)
                            })
                        }
                    }
                }
            }
            ON_LOADMORE -> {
                with(item) {
                    when (current) {
                        LoadStatus.ON_LOAD_NOMORE -> {
                            noMore_tip.text = "没有更多的结果了 铁汁!"
                            noMore_tip.visibility = android.view.View.VISIBLE
                            loadingView.visibility = android.view.View.INVISIBLE
                            clickRetry.visibility = android.view.View.INVISIBLE
                            setOnClickListener(null)
                        }
                        LoadStatus.ON_LOADFAILED -> {
                            noMore_tip.visibility = android.view.View.INVISIBLE
                            loadingView.visibility = android.view.View.INVISIBLE
                            clickRetry.visibility = android.view.View.VISIBLE
                            setOnClickListener {
                                onLoading(true)
                            }
                        }
                        else -> {
                            noMore_tip.visibility = android.view.View.INVISIBLE
                            loadingView.visibility = android.view.View.VISIBLE
                            clickRetry.visibility = android.view.View.INVISIBLE
                            if (current != LoadStatus.ON_LOAD_NOMORE)
                                onLoading(false)
                            setOnClickListener(null)
                        }
                    }
                }
            }
        }
    }

    fun onLoading(retry: Boolean) {
        current = LoadStatus.ON_LOADING
        mCallback.onLoadMore(retry)
    }

    fun addBikaComic(data: ArrayList<ComicListObject>) {
        current = LoadStatus.ON_LOADSUCC
        addData(data)
    }

    fun setNoMore() {
        current = LoadStatus.ON_LOAD_NOMORE
        notifyItemChanged(itemCount - 1)
    }

    fun setLoadFailed() {
        current = LoadStatus.ON_LOADFAILED
        notifyItemChanged(itemCount - 1)
    }
}
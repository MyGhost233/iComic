package com.qiuchenly.comicparse.Modules.SearchResult.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import com.google.gson.Gson
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_FAILED
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_ING
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_MORE
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_NO_MORE
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_SUCCESS
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_NORMAL
import com.qiuchenly.comicparse.Bean.ComicInfoBean
import com.qiuchenly.comicparse.Enum.ComicSourcceType
import com.qiuchenly.comicparse.Http.Bika.ComicListObject
import com.qiuchenly.comicparse.Http.Bika.Tools
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ComicDetails
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Utils.CustomUtils
import kotlinx.android.synthetic.main.comic_local_list.view.*
import kotlinx.android.synthetic.main.loadmore_view.view.*

class SearchResultAdapter(private val mCallback: LoaderListener) : BaseRecyclerAdapter<ComicListObject>() {
    init {
        setFixMemory()
    }

    override fun getViewType(position: Int): Int {
        return when (position) {
            getRealSize() -> {
                ON_LOAD_MORE
            }
            else -> {
                ON_NORMAL
            }
        }
    }

    override fun canLoadMore(): Boolean {
        return true
    }

    override fun getItemLayout(viewType: Int): Int {
        return when (viewType) {
            ON_NORMAL -> {
                R.layout.comic_local_list
            }
            ON_LOAD_MORE -> {
                R.layout.loadmore_view
            }
            else -> {
                R.layout.comic_local_list
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewShow(item: View, data: ComicListObject, position: Int, ViewType: Int) {
        with(item) {
            when (ViewType) {
                ON_NORMAL -> {
                    CustomUtils.loadImageEx(context, Tools.getThumbnailImagePath(data.thumb), bookNameImg, 0, null)
                    bookName.text = data.title
                    bookAuthor.text = data.author
                    val categorys = data.categories?.joinToString(prefix = "", postfix = ",")
                    curr_read.text = "分类:" + categorys?.substring(0, categorys.length - 1)
                    updateTo.visibility = View.INVISIBLE
                    setOnClickListener {
                        context.startActivity(Intent(context, ComicDetails::class.java).apply {
                            //TODO 需要优化此处
                            putExtra("comic", Gson().toJson(ComicInfoBean().apply {
                                this.mComicType = ComicSourcceType.BIKA
                                mComicID = data.comicId
                                this.mComicString = Gson().toJson(data)
                            }))
                        })
                    }
                }
                ON_LOAD_MORE -> {
                    when (getState()) {
                        ON_LOAD_NO_MORE -> {
                            noMore_tip.text = "没有更多的结果了 铁汁!"
                            noMore_tip.visibility = android.view.View.VISIBLE
                            loadingView.visibility = android.view.View.INVISIBLE
                            clickRetry.visibility = android.view.View.INVISIBLE
                            setOnClickListener(null)
                        }
                        ON_LOAD_FAILED -> {
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
                            onLoading(false)
                            setOnClickListener(null)
                        }
                    }
                }
                else -> {

                }
            }
        }
    }

    fun onLoading(retry: Boolean) {
        setState(ON_LOAD_ING)
        mCallback.onLoadMore(retry)
    }

    fun addBikaComic(data: ArrayList<ComicListObject>) {
        setState(ON_LOAD_SUCCESS)
        addData(data)
    }

    fun setNoMore() {
        setState(ON_LOAD_NO_MORE)
    }

    fun setLoadFailed() {
        setState(ON_LOAD_FAILED)
    }
}
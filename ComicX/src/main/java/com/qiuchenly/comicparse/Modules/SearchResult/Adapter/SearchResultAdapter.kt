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
import com.qiuchenly.comicparse.Bean.ComicHome_CategoryComic
import com.qiuchenly.comicparse.Bean.ComicInfoBean
import com.qiuchenly.comicparse.Bean.DataItem
import com.qiuchenly.comicparse.Core.ActivityKey
import com.qiuchenly.comicparse.Enum.ComicSourceType
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ComicDetails
import com.qiuchenly.comicparse.ProductModules.Bika.ComicListObject
import com.qiuchenly.comicparse.ProductModules.Bika.Tools
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Utils.CustomUtils
import kotlinx.android.synthetic.main.comic_local_list.view.*
import kotlinx.android.synthetic.main.loadmore_view.view.*

class SearchResultAdapter(private val mCallback: LoaderListener) : BaseRecyclerAdapter<String>() {
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
    override fun onViewShow(item: View, data: String, position: Int, ViewType: Int) {
        with(item) {
            when (ViewType) {
                ON_NORMAL -> {
                    var mImage = ""
                    var mAuthor = ""
                    var mTitle = ""
                    var mCategory = ""

                    when (mType) {
                        ComicSourceType.BIKA -> {
                            val mComicListObject = Gson().fromJson(data, ComicListObject::class.java)
                            mImage = Tools.getThumbnailImagePath(mComicListObject.thumb)
                            mAuthor = mComicListObject.author ?: ""//解决分类中无作者名的问题
                            mTitle = mComicListObject.title
                            val categorys = mComicListObject.categories?.joinToString(prefix = "", postfix = ",")
                            mCategory = if (categorys.isNullOrEmpty()) ""
                            else
                                categorys.substring(0, categorys.length - 1)
                        }
                        ComicSourceType.DMZJ -> {
                            val mComicListObject = Gson().fromJson(data, ComicHome_CategoryComic::class.java)
                            mImage = mComicListObject.cover
                            mAuthor = mComicListObject.authors
                            mTitle = mComicListObject.title
                            mCategory = mComicListObject.types
                        }
                    }
                    CustomUtils.loadImageCircle(context, mImage, bookNameImg, 8)
                    bookName.text = mTitle
                    bookAuthor.text = mAuthor
                    curr_read.text = "分类:$mCategory"
                    setOnClickListener {
                        context.startActivity(Intent(context, ComicDetails::class.java).apply {
                            //TODO 需要优化此处
                            putExtra(ActivityKey.KEY_CATEGORY_JUMP, Gson().toJson(ComicInfoBean().apply {
                                this.mComicType = mType
                                mComicTAG = mCategory
                                when (mType) {
                                    ComicSourceType.DMZJ -> {
                                        val mComicListObject = Gson().fromJson(data, ComicHome_CategoryComic::class.java)
                                        this.mComicString = Gson().toJson(DataItem().apply {
                                            this.cover = mComicListObject.cover
                                            this.obj_id = mComicListObject.id
                                            this.title = mComicListObject.title
                                            this.status = mComicListObject.status
                                            this.sub_title = mComicListObject.authors
                                            this.type = mComicListObject.types
                                        })
                                    }
                                    else -> {
                                        this.mComicString = data
                                    }
                                }
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

    private var mType = ComicSourceType.BIKA
    fun addBikaComic(data: ArrayList<ComicListObject>) {
        setState(ON_LOAD_SUCCESS)
        mType = ComicSourceType.BIKA
        data.forEach {
            addData(Gson().toJson(it))
        }
    }

    fun setNoMore() {
        setState(ON_LOAD_NO_MORE)
    }

    fun setLoadFailed() {
        setState(ON_LOAD_FAILED)
    }

    fun addDMZJComic(list: List<ComicHome_CategoryComic>) {
        setState(ON_LOAD_SUCCESS)
        mType = ComicSourceType.DMZJ
        list.forEach {
            addData(Gson().toJson(it))
        }
    }
}
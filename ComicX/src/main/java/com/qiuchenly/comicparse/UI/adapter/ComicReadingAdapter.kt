package com.qiuchenly.comicparse.UI.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_FAILED
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_ING
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_MORE
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_NO_MORE
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_NORMAL
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Utils.CustomUtils
import kotlinx.android.synthetic.main.item_comicpage.view.*
import kotlinx.android.synthetic.main.loadmore_view.view.*
import kotlin.math.roundToInt


class ComicReadingAdapter(private val loadListenter: LoaderListener) : BaseRecyclerAdapter<String>() {

    private var TAG = "ComicReadingAdapter"
    override fun getViewType(position: Int): Int {
        return if (position == getRealSize()) ON_LOAD_MORE else ON_NORMAL
    }

    override fun canLoadMore() = true

    override fun getItemLayout(viewType: Int): Int {
        return when (viewType) {
            ON_LOAD_MORE -> R.layout.loadmore_view
            else -> R.layout.item_comicpage
        }
    }

    init {
        setFixMemory()
    }

    override fun onViewShow(item: View, data: String, position: Int, ViewType: Int) {
        with(item) {
            when (ViewType) {
                ON_LOAD_MORE -> {
                    when (getState()) {
                        ON_LOAD_NO_MORE -> {
                            noMore_tip.visibility = View.VISIBLE
                            loadingView.visibility = View.INVISIBLE
                            clickRetry.visibility = View.INVISIBLE
                            setOnClickListener {
                                loadListenter.showMsg("都说了没有更多章节辣!")
                            }
                        }
                        ON_LOAD_FAILED -> {
                            noMore_tip.visibility = View.INVISIBLE
                            loadingView.visibility = View.INVISIBLE
                            clickRetry.visibility = View.VISIBLE
                            setOnClickListener {
                                onLoading()
                            }
                        }
                        else -> {
                            noMore_tip.visibility = View.INVISIBLE
                            loadingView.visibility = View.VISIBLE
                            clickRetry.visibility = View.INVISIBLE
                            loadListenter.onLoadMore(true)
                            setOnClickListener(null)
                        }
                    }
                }
                ON_NORMAL -> {
                    mRetryLoad.setOnClickListener {
                        onViewShow(item, data, position, ViewType)
                        mRetryLoad.text = "加载中..."
                        mRetryLoad.isClickable = false
                    }
                    CustomUtils.loadImageEx(
                            item.context,
                            data,
                            item.iv_img_page,
                            R.drawable.loading,
                            object : CustomUtils.ImageListener {
                                override fun onRet(state: CustomUtils.GlideState, resource: Drawable?, target: Target<Drawable>?): Boolean {
                                    if (state == CustomUtils.GlideState.LoadFailed) {
                                        mRetryLoad.visibility = View.VISIBLE
                                        mRetryLoad.isClickable = true
                                        mRetryLoad.text = "点击重试!"
                                    } else {
                                        if (resource == null) return false
                                        mRetryLoad.visibility = View.INVISIBLE
                                        val params = item.iv_img_page.layoutParams
                                        val realWidth = iv_img_page.width - iv_img_page.paddingLeft - iv_img_page.paddingRight
                                        val scale = realWidth / (resource.intrinsicWidth * 1.0000)
                                        val realHeight = (resource.intrinsicHeight * scale).roundToInt()
                                        params.height = realHeight + iv_img_page.paddingTop + iv_img_page.paddingBottom
                                        iv_img_page.layoutParams = params
                                        iv_img_page.invalidate()
                                    }
                                    return false
                                }
                            })
                    if (position + 1 < getRealSize()) {
                        Log.d(TAG, "onViewShow: Size = " + getRealSize() + ", position = " + (position + 1))
                        Glide.with(this.context)
                                .load(getIndexData(position + 1))
                                .preload()
                    }
                }
            }
        }
    }

    fun onNoMore() {
        setState(ON_LOAD_NO_MORE)
    }

    fun onLoading() {
        setState(ON_LOAD_ING)
    }

    fun onLoadNextFailed() {
        setState(ON_LOAD_FAILED)
    }
}
package com.qiuchenly.comicparse.Modules.ReadingActivity.Adapter

import android.graphics.drawable.Drawable
import android.view.View
import com.bumptech.glide.request.target.Target
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_FAILED
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_ING
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_MORE
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_LOAD_NO_MORE
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter.RecyclerState.ON_NORMAL
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Utils.CustomUtils
import kotlinx.android.synthetic.main.item_comicpage.view.*
import kotlinx.android.synthetic.main.loadmore_view.view.*


class ComicImagePageAda(private val loadListenter: LoaderListener) : BaseRecyclerAdapter<String>() {
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

    enum class DataSourceType {
        BIKA, MH1234
    }

    var currentSource = DataSourceType.MH1234
    fun setBikaMode() {
        currentSource = DataSourceType.BIKA
    }

    override fun onViewShow(item: View, data: String, position: Int, ViewType: Int) {
        with(item) {
            when (ViewType) {
                ON_LOAD_MORE -> {
                    when (getState()) {
                        ON_LOAD_NO_MORE -> {
                            noMore_tip.visibility = android.view.View.VISIBLE
                            loadingView.visibility = android.view.View.INVISIBLE
                            clickRetry.visibility = android.view.View.INVISIBLE
                            setOnClickListener {
                                loadListenter.showMsg("都说了没有更多章节辣!")
                            }
                        }
                        ON_LOAD_FAILED -> {
                            noMore_tip.visibility = android.view.View.INVISIBLE
                            loadingView.visibility = android.view.View.INVISIBLE
                            clickRetry.visibility = android.view.View.VISIBLE
                            setOnClickListener {
                                onLoading()
                            }
                        }
                        else -> {
                            noMore_tip.visibility = android.view.View.INVISIBLE
                            loadingView.visibility = android.view.View.VISIBLE
                            clickRetry.visibility = android.view.View.INVISIBLE
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
                    CustomUtils.loadImage(
                            item.context,
                            data,
                            item.iv_img_page,
                            R.drawable.loading,
                            object : CustomUtils.ImageListener {
                                override fun onRet(state: CustomUtils.GlideState, resource: Drawable?, target: Target<Drawable>?): Boolean {
                                    if (state == CustomUtils.GlideState.onLoadFailed) {
                                        mRetryLoad.visibility = View.VISIBLE
                                        mRetryLoad.isClickable = true
                                        mRetryLoad.text = "点击重试!"
                                    } else {
                                        if (resource == null) return false
                                        mRetryLoad.visibility = View.INVISIBLE
                                        val params = item.iv_img_page.layoutParams
                                        val realWidth = iv_img_page.width - iv_img_page.paddingLeft - iv_img_page.paddingRight
                                        val scale = realWidth / (resource.intrinsicWidth * 1.0000)
                                        val realHeight = Math.round(resource.intrinsicHeight * scale).toInt()
                                        params.height = realHeight + iv_img_page.paddingTop + iv_img_page.paddingBottom
                                        iv_img_page.layoutParams = params
                                        iv_img_page.invalidate()
                                    }
                                    return false
                                }
                            })
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
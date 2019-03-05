package com.qiuchenly.comicparse.Modules.ReadingActivity.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.target.Target
import com.qiuchenly.comicparse.BaseImp.BaseRVAdapter
import com.qiuchenly.comicparse.BaseImp.BaseVH
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Utils.CustomUtils
import kotlinx.android.synthetic.main.item_comicpage.view.*
import kotlinx.android.synthetic.main.loadmore_view.view.*


class ComicImagePageAda(private val onLoad: onLoadMore) : BaseRVAdapter<String>() {




    override fun getLayout(viewType: Int): Int {
        return R.layout.item_comicpage
    }

    enum class SourceType {
        BIKA, MH1234
    }

    var currentSource = SourceType.MH1234
    fun setBikaMode() {
        currentSource = SourceType.BIKA
    }

    override fun InitUI(item: View, data: String?, position: Int) {
        if (data != null) {
            item.mRetryLoad.setOnClickListener {
                InitUI(item, data, position)
                item.mRetryLoad.text = "加载中..."
                item.mRetryLoad.isClickable = false
            }
            CustomUtils.loadImage(
                    item.context, data,
                    item.iv_img_page,
                    R.drawable.loading,
                    object : CustomUtils.ImageListener {
                        override fun onRet(state: CustomUtils.GlideState, resource: GlideDrawable?, fromMemoryCache: Boolean, model: String?, target: Target<GlideDrawable>?): Boolean {
                            if (state == CustomUtils.GlideState.ON_EXCEPTION) {
                                item.mRetryLoad.visibility = View.VISIBLE
                                item.mRetryLoad.isClickable = true
                                item.mRetryLoad.text = "加载失败,点击重试!"
                            } else {
                                if (resource == null) return false
                                item.mRetryLoad.visibility = View.INVISIBLE
                                val params = item.iv_img_page.layoutParams
                                val realWidth = item.iv_img_page.width - item.iv_img_page.paddingLeft - item.iv_img_page.paddingRight
                                val scale = realWidth / (resource.intrinsicWidth * 1.0000)
                                val realHeight = Math.round(resource.intrinsicHeight * scale).toInt()
                                params.height = realHeight + item.iv_img_page.paddingTop + item.iv_img_page.paddingBottom
                                item.iv_img_page.layoutParams = params
                                item.iv_img_page.invalidate()
                            }
                            return false
                        }
                    })
        }
        when (getItemViewType(position)) {
            TYPE_LOAD_MORE -> {
                with(item) {
                    when (currentState) {
                        ON_NOMORE -> {
                            noMore_tip.visibility = android.view.View.VISIBLE
                            loadingView.visibility = android.view.View.INVISIBLE
                            clickRetry.visibility = android.view.View.INVISIBLE
                            setOnClickListener {
                                onLoad.showMsg("都说了没有更多章节辣!")
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
                            if (currentSource == SourceType.BIKA) {

                            } else
                                onLoad.onLoadMore(true)
                            setOnClickListener {
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (getData()?.size!! > 0) super.getItemCount() + 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
        return BaseVH(LayoutInflater.from(parent.context).inflate(
                when (viewType) {
                    TYPE_LOAD_MORE -> R.layout.loadmore_view
                    else -> R.layout.item_comicpage
                }
                , parent, false))
    }

    companion object {
        val ON_NOMORE = 0
        val ON_LOAD_FAILED = 1
        val ON_LOADING = -1
        val ON_SUCC = 2
    }

    private var currentState = ON_LOADING
    fun onNoMore() {
        currentState = ON_NOMORE
        notifyItemChanged(getData()?.size!!)
    }

    fun onLoading() {
        currentState = ON_LOADING
        notifyItemChanged(getData()?.size!!)
    }

    fun onLoadNextFailed() {
        currentState = ON_LOAD_FAILED
        notifyItemChanged(getData()?.size!!)
    }

    val TYPE_NORMAL = 0
    val TYPE_LOAD_MORE = 1
    override fun getItemViewType(position: Int): Int {
        return if (position == getData()?.size) TYPE_LOAD_MORE else TYPE_NORMAL
    }
}
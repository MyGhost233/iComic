package com.qiuchenly.comicparse.Modules.ReadingActivity.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.target.Target
import com.qiuchenly.comicparse.MVP.UI.Adapter.BaseVH
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Simple.BaseRVAdapter
import com.qiuchenly.comicparse.Utils.CustomUtils
import kotlinx.android.synthetic.main.item_comicpage.view.*
import kotlinx.android.synthetic.main.loadmore_view.view.*


class ComicImagePageAda : BaseRVAdapter<String>() {
    override fun getLayout(): Int {
        return R.layout.item_comicpage
    }

    override fun InitUI(item: View, data: String?, position: Int) {
        if (data != null) {
            item.mRetryLoad.setOnClickListener {
                InitUI(item, data, position)
                item.mRetryLoad.text = "加载中..."
                item.mRetryLoad.isClickable = false
            }

            CustomUtils.loadImage(
                    "http://mhpic.dongzaojiage.com$data",
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
                    if (noMore) {
                        noMore_tip.visibility = android.view.View.VISIBLE
                        loadingView.visibility = android.view.View.GONE
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (getData()?.size!! > 0) super.getItemCount() + 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
        return if (viewType == TYPE_LOAD_MORE) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.loadmore_view, parent, false)
            BaseVH(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_comicpage, parent, false)
            BaseVH(itemView)
        }
    }

    private var noMore = false
    fun setNoMore() {
        noMore = true
        notifyItemChanged(getData()?.size!!)
    }

    val TYPE_NORMAL = 0
    val TYPE_LOAD_MORE = 1
    override fun getItemViewType(position: Int): Int {
        return if (position == getData()?.size) TYPE_LOAD_MORE else TYPE_NORMAL
    }
}
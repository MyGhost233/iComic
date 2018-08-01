package com.example.qiuchenly.comicparse.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.AppManager
import com.example.qiuchenly.comicparse.Simple.BaseRVAdapter
import kotlinx.android.synthetic.main.item_comicpage.view.*
import java.lang.Exception


class ComicImagePageAda : BaseRVAdapter<String>() {
    override fun getLayout(): Int {
        return R.layout.item_comicpage
    }

    override fun InitUI(item: View, data: String?, position: Int) {
        if (data != null)
            Glide.with(AppManager.getAppm().currentActivity())
                    .load("http://mhpic.dongzaojiage.com" + data)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(object : RequestListener<String, GlideDrawable> {
                        override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                            if (resource == null) return false
                            val params = item.iv_img_page.layoutParams
                            val realWidth = item.iv_img_page.width - item.iv_img_page.paddingLeft - item.iv_img_page.paddingRight
                            val scale = realWidth / (resource.intrinsicWidth * 1.0000)
                            val realHeight = Math.round(resource.intrinsicHeight * scale).toInt()
                            params.height = realHeight + item.iv_img_page.paddingTop + item.iv_img_page.paddingBottom
                            item.iv_img_page.layoutParams = params
                            return false
                        }

                    })
                    .into(item.iv_img_page)
    }

    override fun getItemCount(): Int {
        return if (getData()?.size!! > 0) super.getItemCount() + 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
        return if (viewType === TYPE_LOAD_MORE) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.loadmore_view, parent, false)
            BaseVH(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_comicpage, parent, false)
            BaseVH(itemView)
        }
    }

    val TYPE_NORMAL = 0
    val TYPE_LOAD_MORE = 1
    override fun getItemViewType(position: Int): Int {
        return if (position == getData()?.size) TYPE_LOAD_MORE else TYPE_NORMAL
    }
}
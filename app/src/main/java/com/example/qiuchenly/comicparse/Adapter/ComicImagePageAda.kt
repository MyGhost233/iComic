package com.example.qiuchenly.comicparse.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.AppManager
import com.example.qiuchenly.comicparse.Simple.BaseRVAdapter
import kotlinx.android.synthetic.main.item_comicpage.view.*


class ComicImagePageAda : BaseRVAdapter<String>() {
    override fun getLayout(): Int {
        return R.layout.item_comicpage
    }

    override fun InitUI(item: View, data: String?, position: Int) {
        if (data != null)
            Glide.with(AppManager.getAppm().currentActivity())
                    .load("http://mhpic.dongzaojiage.com" + data)
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
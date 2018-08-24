package com.qiuchenly.comicparse.MVP.UI.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qiuchenly.comicparse.MVP.Contract.NetRecommentContract
import com.qiuchenly.comicparse.R

class RecommendRecyclerViewAdapter(view: NetRecommentContract.View) : RecyclerView.Adapter<BaseVH>() {

    private val TYPE_TOPVIEW = 0
    private val TYPE_RANKVIEW = 1
    private val TYPE_NORMAL = 2
//    private val TYPE_NORMAL = 3
//    private val TYPE_NORMAL = 4
//    private val TYPE_NORMAL = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
        return BaseVH(LayoutInflater.from(parent.context).inflate(
                when (viewType) {
                    TYPE_TOPVIEW -> R.layout.item_recommend_topview
                    TYPE_RANKVIEW -> R.layout.item_rankview
                    else -> R.layout.item_recommend_normal
                }, parent, false))
    }

    override fun getItemCount(): Int {
        return 13
    }

    override fun onBindViewHolder(holder: BaseVH, position: Int) {
        initUI(holder.itemView, position)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_TOPVIEW
            1 -> TYPE_RANKVIEW
            else -> TYPE_NORMAL
        }
    }

    fun initUI(view: View, position: Int) {

    }
}
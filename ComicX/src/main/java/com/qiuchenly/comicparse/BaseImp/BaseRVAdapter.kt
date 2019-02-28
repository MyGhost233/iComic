package com.qiuchenly.comicparse.BaseImp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseRVAdapter<T> : RecyclerView.Adapter<BaseVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
        return BaseVH(LayoutInflater.from(parent.context).inflate(getLayout(), parent, false))
    }

    private var map: ArrayList<T>? = ArrayList()

    fun addData(map: ArrayList<T>) {
        val startPoint = this.map?.size
        this.map?.addAll(map)
        notifyItemRangeInserted(startPoint!!, map.size)
    }

    fun setData(map: ArrayList<T>) {
        this.map = map
        notifyDataSetChanged()
    }

    fun getData() = map

    abstract fun getLayout(): Int

    override fun getItemCount(): Int {
        return if (map != null) map?.size!!
        else 0
    }

    /**
     * 快速排序算法
     * @param MODE =1 正序 =2 倒序
     */
    fun sort(MODE: Int) {
        if (map != null) {
            val ret = ArrayList<T>()
            for (m: T in map!!) {
                ret.add(if (MODE == 1) 0 else ret.size, m)
            }
            map = ret
            notifyDataSetChanged()
        }
        //排序算法2
    }

    override fun onBindViewHolder(holder: BaseVH, position: Int) {
        InitUI(holder.itemView, if (position == map?.size!!) null else map!![position], position)
    }

    abstract fun InitUI(item: View, data: T?, position: Int)
}
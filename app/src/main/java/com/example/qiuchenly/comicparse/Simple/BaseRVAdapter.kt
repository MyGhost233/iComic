package com.example.qiuchenly.comicparse.Simple

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qiuchenly.comicparse.Adapter.BaseVH

abstract class BaseRVAdapter<T> : RecyclerView.Adapter<BaseVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
        return BaseVH(LayoutInflater.from(parent.context).inflate(getLayout(), parent, false))
    }

    private var map: ArrayList<T>? = ArrayList()

    fun addData(map: ArrayList<T>) {
        this.map?.addAll(map)
        notifyDataSetChanged()
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

    override fun onBindViewHolder(holder: BaseVH, position: Int) {
        InitUI(holder.itemView, if (position==map?.size!!) null else map!![position], position)
    }

    abstract fun InitUI(item: View, data: T?, position: Int)
}
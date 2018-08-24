package com.qiuchenly.comicparse.MVP.UI.Adapter

import android.graphics.Color
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.qiuchenly.comicparse.MVP.Contract.NetRecommentContract
import com.qiuchenly.comicparse.R
import me.crosswall.lib.coverflow.CoverFlow
import me.crosswall.lib.coverflow.core.PageItemClickListener
import me.crosswall.lib.coverflow.core.PagerContainer
import org.jetbrains.anko.find

class RecommendRecyclerViewAdapter(var view: NetRecommentContract.View) : RecyclerView.Adapter<BaseVH>() {

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
        when (getItemViewType(position)) {
            0 -> {
                val pager_container = view.find<PagerContainer>(R.id.pager_container)
                val pager = pager_container.getViewPager()
                CoverFlow.Builder()
                        .with(pager)
                        .scale(0.3f)
                        .pagerMargin(0f)
                        .spaceSize(0f)
                        .rotationY(25f)
                        .build()
                val adapter = MyPagerAdapter(view)
                pager.setAdapter(adapter)

                pager.setOffscreenPageLimit(adapter.getCount())

                pager.setClipChildren(false)

                pager_container.setPageItemClickListener(PageItemClickListener { mView, mPosition ->
                    Toast.makeText(view.context, "position:$position", Toast.LENGTH_SHORT).show()
                })

            }
        }
    }

    private inner class MyPagerAdapter(val v: View) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = TextView(v.context)
            view.text = "Item $position"
            view.gravity = Gravity.CENTER
            view.setBackgroundColor(Color.argb(255, position * 50, position * 10, position * 50))

            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun getCount(): Int {
            return 15
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }
    }
}
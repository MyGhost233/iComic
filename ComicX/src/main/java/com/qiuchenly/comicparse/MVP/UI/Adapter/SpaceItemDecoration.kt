package com.qiuchenly.comicparse.MVP.UI.Adapter

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

open class SpaceItemDecoration(private val space: Int, private val list: Int = 2) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = space
        outRect.bottom = space
        //由于每行都只有list个，所以第一个都是list的倍数，把左边距设为0
        if (parent.getChildLayoutPosition(view) % list == 0) {
            outRect.left = space
        } else {
            outRect.right = space
        }
    }
}
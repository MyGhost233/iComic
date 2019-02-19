package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.TuiJian.Adapter

import android.support.v4.view.PagerAdapter
import android.view.View

class RecommendTopViewAdapter : PagerAdapter() {
    val mList = ArrayList<String>()
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mList.size
    }
}
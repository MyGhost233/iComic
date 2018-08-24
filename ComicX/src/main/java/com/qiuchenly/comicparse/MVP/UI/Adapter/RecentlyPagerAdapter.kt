package com.qiuchenly.comicparse.MVP.UI.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class RecentlyPagerAdapter(fm: FragmentManager, private val structArr: List<Struct>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return structArr[position].fram
    }

    override fun getCount(): Int {
        return structArr.size
    }

    public class Struct(val name: String, val fram: Fragment)

    override fun getPageTitle(position: Int): CharSequence? {
        return structArr[position].name
    }
    // private val arr = arrayListOf<String>("最近一周", "一周以前")
}
package com.qiuchenly.comicparse.UI.BaseImp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class SuperPagerAdapter(fm: FragmentManager, private val structArr: List<Struct>) : FragmentStatePagerAdapter(fm) {
    class Struct(val name: String, val fram: Fragment)

    //FragmentStateAdapter
    override fun getItem(position: Int): Fragment {
        return structArr[position].fram
    }

    override fun getCount(): Int {
        return structArr.size
    }

    fun getInstance(name: String): Fragment? {
        for (fragment in structArr) {
            if (fragment.name == name) {
                return fragment.fram
            }
        }
        return null
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return structArr[position].name
    }
    // private val arr = arrayListOf<String>("最近一周", "一周以前")
}
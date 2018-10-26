package com.qiuchenly.comicparse.MVP.UI.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class BaseFragmentPagerStatement(fm: FragmentManager, private val _fragment: List<Fragment>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return _fragment[position]
    }

    override fun getCount(): Int {
        return _fragment.size
    }
}

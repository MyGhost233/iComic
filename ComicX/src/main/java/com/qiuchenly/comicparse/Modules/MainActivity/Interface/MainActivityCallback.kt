package com.qiuchenly.comicparse.Modules.MainActivity.Interface

import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.view.View

class MainActivityCallback(mCallback: Callbacks) : ViewPager.OnPageChangeListener, DrawerLayout.DrawerListener {

    private var mCallbacks = mCallback

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        mCallbacks.PagerSelect(position)
    }

    override fun onDrawerStateChanged(newState: Int) {

    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

    }

    companion object {
        val TYPE_CLOSED = 0
        val TYPE_OPEND = 1
    }

    override fun onDrawerClosed(drawerView: View) {
        mCallbacks.DrawerChange(TYPE_CLOSED)
    }

    override fun onDrawerOpened(drawerView: View) {
        mCallbacks.DrawerChange(TYPE_OPEND)
    }
}
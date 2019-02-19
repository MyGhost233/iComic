package com.qiuchenly.comicparse.Modules.MainActivity.ViewModel

import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.KeyEvent
import android.widget.ImageView
import com.qiuchenly.comicparse.MVP.UI.Adapter.BaseFragmentPagerStatement
import com.qiuchenly.comicparse.MVP.UI.Fragments.ComicBoardFragment
import com.qiuchenly.comicparse.MVP.UI.Fragments.Main
import com.qiuchenly.comicparse.MVP.UI.Fragments.MyDetailsFragment
import com.qiuchenly.comicparse.Modules.MainActivity.Activity.MainActivityUI
import com.qiuchenly.comicparse.Modules.MainActivity.Interface.Callbacks
import com.qiuchenly.comicparse.Modules.MainActivity.Interface.MainActivityCallback
import kotlinx.android.synthetic.main.activity_switch_main.*

class MainActivityViewModel(private var mContentView: MainActivityUI) : Callbacks {
    private var mSwitchList = ArrayList<ImageView>()
    /**
     * 上一个点击的pager序号
     */
    private var mCurrentPosition = 0

    /**
     * Main页面的所有碎片化容器聚合
     */
    private val mFragments = ArrayList<Fragment>().apply {
        add(MyDetailsFragment())
        add(ComicBoardFragment())
        add(Main())
    }

    private var mCallback: MainActivityCallback = MainActivityCallback(this)

    /**
     * 侧滑菜单是否开启
     */
    private var isOpenDrawable = false

    /**
     * 用于确定按下按键时间是否小于2秒
     */
    private var lastTime = -1L

    init {
        with(mContentView) {
            mSwitchList.add(switch_my_list_img)
            mSwitchList.add(switch_my_website_more_img)
            mSwitchList.add(switch_my_website_addition_img)

            switch_my_list_img.imageAlpha = 255
            switch_my_website_more_img.imageAlpha = 100
            switch_my_website_addition_img.imageAlpha = 100
            dl_navigation_main.addDrawerListener(mCallback)
            val statement = BaseFragmentPagerStatement(
                    supportFragmentManager,
                    mFragments)
            vp_main_pages.adapter = statement
            vp_main_pages.offscreenPageLimit = 4
            vp_main_pages.addOnPageChangeListener(mCallback)

            btn_menu_main.setOnClickListener {
                dl_navigation_main.openDrawer(Gravity.START)
                isOpenDrawable = true
            }

            switch_my_list.setOnClickListener {
                vp_main_pages.currentItem = 0
            }

            switch_my_website_more.setOnClickListener {
                vp_main_pages.currentItem = 1
            }

            switch_my_website_addition.setOnClickListener {
                vp_main_pages.currentItem = 2
            }
        }
    }

    override fun PagerSelect(position: Int) {
        mSwitchList[mCurrentPosition].imageAlpha = 100
        mSwitchList[position].imageAlpha = 255
        mCurrentPosition = position
    }

    override fun DrawerChange(state: Int) {
        isOpenDrawable = state == MainActivityCallback.TYPE_OPEND
    }

    fun closeDrawer() {
        with(mContentView) {
            dl_navigation_main.closeDrawer(Gravity.START)
        }
    }

    fun notifyData() {
        (mFragments[0] as MyDetailsFragment).notifyData()
    }

    fun canExit(keyCode: Int): Boolean {
        return if (isOpenDrawable && keyCode == KeyEvent.KEYCODE_BACK) {
            closeDrawer()
            false
        } else {
            val curr = System.currentTimeMillis()
            if (curr - lastTime > 2000) {
                mContentView.ShowErrorMsg("再按一次退出")
                lastTime = curr
                false
            } else true
        }
    }

    fun cancel() {

    }
}
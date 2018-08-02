package com.example.qiuchenly.comicparse.UI.SwicthMain

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewTreeObserver
import com.example.qiuchenly.comicparse.Adapter.BaseFragmentPagerStatement
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.BaseApp
import com.example.qiuchenly.comicparse.UI.Main.Main
import com.example.qiuchenly.comicparse.UI.Main_MyIndex.MyDetailsFragment
import com.example.qiuchenly.comicparse.Utils.CustomUtils.Companion.blurs
import com.example.qiuchenly.comicparse.Utils.CustomUtils.Companion.catchBitmap
import kotlinx.android.synthetic.main.activity_switch_main.*
import java.util.*

class MainSwitch : BaseApp<MainSwitchContract.Presenter>(), MainSwitchContract.View, ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    var lastPosition = 0

    override fun onPageSelected(position: Int) {
        when (lastPosition) {
            0 -> {
                switch_my_list_img
            }
            1 -> {
                switch_my_website_more_img
            }
            2 -> {
                switch_my_website_addition_img
            }
            else -> {
                switch_my_list_img
            }
        }.setImageResource(R.drawable.ic_menu_black_24dp)
        when (position) {
            0 -> {
                switch_my_list_img
            }
            1 -> {
                switch_my_website_more_img
            }
            2 -> {
                switch_my_website_addition_img
            }
            else -> {
                switch_my_list_img
            }
        }.setImageResource(R.drawable.ic_menu_black_24dp_light_select)
        lastPosition = position
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_switch_main
    }

    companion object BlurImageCache {
        var contentView: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        var imageGetters: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        control_menu.visibility = View.INVISIBLE

        //start create appbar ui
        fl_main_root_view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                fl_main_root_view.buildDrawingCache()
                var imageGetter = fl_main_root_view.drawingCache
                imageGetters = blurs(imageGetter, 20)

                var tmp = catchBitmap(ab_main_navigation_layout, imageGetter)
                var retImg = blurs(tmp, 70)
                ab_main_navigation_layout.background = BitmapDrawable(null, retImg)

                tmp = catchBitmap(vp_main_pages, imageGetters)
                contentView = tmp
                vp_main_pages.background = BitmapDrawable(null, tmp)

                control_menu.visibility = View.VISIBLE
                iv_backimg_nv.setImageBitmap(imageGetters)
                continueInit()
                fl_main_root_view.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
        MainSwichPresenter(this)
    }

    override fun setPres(mPres: MainSwitchContract.Presenter) {
        this.mPres = mPres
    }

    fun continueInit() {
        val fragmentList = ArrayList<Fragment>().apply {
            add(MyDetailsFragment())
            add(Main())
            add(Fragment())
        }

        val statement = BaseFragmentPagerStatement(
                supportFragmentManager,
                fragmentList)
        vp_main_pages.adapter = statement
        vp_main_pages.offscreenPageLimit = 3
        vp_main_pages.setOnPageChangeListener(this)

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

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (isOpenDrawable && keyCode == KeyEvent.KEYCODE_BACK) {
            dl_navigation_main.closeDrawer(Gravity.START)
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

    private var isOpenDrawable = false
}
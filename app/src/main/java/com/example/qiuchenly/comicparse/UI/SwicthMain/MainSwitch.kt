package com.example.qiuchenly.comicparse.UI.SwicthMain

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewTreeObserver
import com.example.qiuchenly.comicparse.Adapter.BaseFragmentPagerStatement
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.BaseApp
import com.example.qiuchenly.comicparse.UI.Main.Main
import kotlinx.android.synthetic.main.activity_switch_main.*
import net.qiujuer.genius.blur.StackBlur

class MainSwitch : BaseApp<MainSwitchContract.Presenter>(), MainSwitchContract.View {
    override fun getLayoutID(): Int {
        return R.layout.activity_switch_main
    }

    companion object BlurImageCache {
        var navigationView = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        var contentView = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        //start create appbar ui
        fl_main_root_view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                fl_main_root_view.buildDrawingCache()
                val imageGetter = fl_main_root_view.getDrawingCache()
                var retImg = blurs(ab_main_navigation_layout, imageGetter, 300)
                ab_main_navigation_layout.background = BitmapDrawable(retImg)
                navigationView = retImg
                retImg = blurs(vp_main_pages, imageGetter, 30)
                vp_main_pages.background = BitmapDrawable(retImg)
                iv_backimg_nv.setImageBitmap(retImg)
                contentView = retImg
                fl_main_root_view.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        val statement = BaseFragmentPagerStatement(
                supportFragmentManager,
                arrayListOf(Main(),
                        Fragment()))
        vp_main_pages.adapter = statement
        vp_main_pages.offscreenPageLimit = 5

    }

    fun blurs(view: View, bitmap: Bitmap, radius: Int): Bitmap {
        val bit1 = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bit1)
        canvas.translate(-view.left.toFloat(), -view.top.toFloat())
        canvas.drawBitmap(bitmap, 0.toFloat(), 0.toFloat(), null)
        return StackBlur.blurNatively(bit1, radius, false)
    }
}
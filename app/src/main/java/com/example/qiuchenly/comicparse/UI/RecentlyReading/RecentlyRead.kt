package com.example.qiuchenly.comicparse.UI.RecentlyReading

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.BaseNavigatorCommon
import com.example.qiuchenly.comicparse.UI.RecentlyReading.RecnetByWeek.RecentlyByWeekFragment
import com.example.qiuchenly.comicparse.UI.SwicthMain.MainSwitch
import com.example.qiuchenly.comicparse.Utils.CustomUtils
import kotlinx.android.synthetic.main.activity_recently_read.*


/**
 * 这个类 就这么跟你说吧 最近阅读活动类 你的 了解？
 * 作者：不要问我是谁 我就是一个传说
 * 时间：还有几天要考科目一
 */
class RecentlyRead : AppCompatActivity() {

    private var PgAdapter: PagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recently_read)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (supportActionBar != null) supportActionBar!!.hide()

        //init ui
        back_up.setOnClickListener {
            finish()
        }
        clear_all.setOnClickListener {
            Toast.makeText(this, "SB Func", Toast.LENGTH_SHORT).show()
        }

        val list = arrayListOf(
                PagerAdapter.Struct("最近一周", RecentlyByWeekFragment()),
                PagerAdapter.Struct("一周以前", RecentlyByWeekFragment())
        )
        PgAdapter = PagerAdapter(supportFragmentManager, list)
        tl_recently_tab_setup_vp.adapter = PgAdapter

        //create tips bottom
        BaseNavigatorCommon.setUpWithPager(this, list, magic_indicator, tl_recently_tab_setup_vp)


        al_recently_bar.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                var bit = CustomUtils.catchBitmap(al_recently_bar, MainSwitch.imageGetters)
                bit = CustomUtils.blurs(bit, 70)
                al_recently_bar.background = BitmapDrawable(bit)
                al_recently_bar.viewTreeObserver.removeOnGlobalLayoutListener(this)
                tl_recently_tab_setup_vp.background = BitmapDrawable(MainSwitch.contentView)
            }
        })


    }
}
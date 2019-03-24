package com.qiuchenly.comicparse.Modules.RecentlyReading

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.qiuchenly.comicparse.BaseImp.BaseNavigatorCommon
import com.qiuchenly.comicparse.Modules.RecentlyReading.Adapter.SuperPagerAdapter
import com.qiuchenly.comicparse.Modules.RecentlyReading.RecnetByWeek.RecentlyByWeekFragment
import com.qiuchenly.comicparse.R
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_recently_read.*
import kotlinx.android.synthetic.main.view_magic_indicator_base.*


/**
 * 这个类 就这么跟你👄吧 最近阅读活动类 你了解⑧？
 * 作者：新津恶霸丶mata川
 * 时间：⚽️⚽️你萌让我①个月拿驾驶证⑧
 */
class RecentlyRead : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recently_read)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        Slidr.attach(this)
        if (supportActionBar != null) supportActionBar!!.hide()
        InitUI(this, getFramList())
    }

    fun getFramList(): ArrayList<SuperPagerAdapter.Struct> {
        return arrayListOf(
                SuperPagerAdapter.Struct("最近阅读", RecentlyByWeekFragment()),
                SuperPagerAdapter.Struct("一月之前", Fragment())
        )
    }

    companion object {
        private var mPgAdapter: SuperPagerAdapter? = null
        fun InitUI(app: AppCompatActivity, arr: ArrayList<SuperPagerAdapter.Struct>) {
            //init ui
            app.back_up.setOnClickListener {
                app.finish()
            }
            app.clear_all.setOnClickListener {
                Toast.makeText(app, "这个功能还没做", Toast.LENGTH_SHORT).show()
            }
            val list = arr
            mPgAdapter = SuperPagerAdapter(app.supportFragmentManager, list)
            app.tl_recently_tab_setup_vp.adapter = mPgAdapter

            //create tips bottom
            BaseNavigatorCommon.setUpWithPager(app, list, app.magic_indicator, app.tl_recently_tab_setup_vp)
        }
    }
}
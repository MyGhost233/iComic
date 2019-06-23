package com.qiuchenly.comicparse.UI.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.qiuchenly.comicparse.Modules.RecentlyReading.RecnetByWeek.RecentlyByWeekFragment
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.UI.BaseImp.BaseNavigatorCommon
import com.qiuchenly.comicparse.UI.BaseImp.SuperPagerAdapter
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
        InitUI(getFramList())
    }

    fun getFramList(): ArrayList<SuperPagerAdapter.Struct> {
        return arrayListOf(
                SuperPagerAdapter.Struct("最近阅读", RecentlyByWeekFragment()),
                SuperPagerAdapter.Struct("一月之前", Fragment())
        )
    }

    private var mPgAdapter: SuperPagerAdapter? = null
    fun InitUI(arr: ArrayList<SuperPagerAdapter.Struct>) {
        //init ui
        back_up.setOnClickListener {
            finish()
        }
        clear_all.setOnClickListener {
            Toast.makeText(this, "这个功能还没做", Toast.LENGTH_SHORT).show()
        }
        val list = arr
        mPgAdapter = SuperPagerAdapter(supportFragmentManager, list)
        tl_recently_tab_setup_vp.adapter = mPgAdapter

        //create tips bottom
        BaseNavigatorCommon.setUpWithPager(this.applicationContext, list, magic_indicator, tl_recently_tab_setup_vp)
    }
}
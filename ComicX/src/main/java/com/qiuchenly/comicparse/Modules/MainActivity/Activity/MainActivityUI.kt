package com.qiuchenly.comicparse.Modules.MainActivity.Activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.qiuchenly.comicparse.BaseImp.BaseApp
import com.qiuchenly.comicparse.Modules.MainActivity.ViewModel.MainActivityViewModel
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.activity_switch_main.*
import kotlinx.android.synthetic.main.navigation_main.*


class MainActivityUI : BaseApp(), View.OnClickListener {
    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == R.id.switch_my_list
                    || v.id == R.id.switch_my_website_more
                    || v.id == R.id.switch_my_website_addition
                    && v.tag != null
            )
                vp_main_pages.currentItem = v.tag as Int
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_switch_main
    }

    private var mViewModel: MainActivityViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel处理UI
        mViewModel = MainActivityViewModel(this)
        mUpdateInfo.setOnRefreshListener {
            mViewModel?.getWeathers()
        }
        mViewModel?.getWeathers()
        //TODO 此处启动后台下载服务暂时不写
        //startService(Intent(this, DownloadService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mViewModel != null) {
            mViewModel!!.cancel()
            mViewModel = null
            System.gc()
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return if (!mViewModel!!.canExit(keyCode)) false
        else super.onKeyUp(keyCode, event)
    }

    fun updateInfo() {
        mViewModel!!.notifyData()
    }
}
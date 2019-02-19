package com.qiuchenly.comicparse.Modules.MainActivity.Activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import com.qiuchenly.comicparse.Modules.MainActivity.ViewModel.MainActivityViewModel
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Service.DownloadService
import com.qiuchenly.comicparse.Simple.BaseApp

class MainActivityUI : BaseApp() {
    override fun getLayoutID(): Int {
        return R.layout.activity_switch_main
    }

    private var mViewModel: MainActivityViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel处理UI
        mViewModel = MainActivityViewModel(this)
        startService(Intent(this, DownloadService::class.java))
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
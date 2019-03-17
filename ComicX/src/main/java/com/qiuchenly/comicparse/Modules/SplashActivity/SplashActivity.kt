package com.qiuchenly.comicparse.Modules.SplashActivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.qiuchenly.comicparse.BaseImp.BaseApp
import com.qiuchenly.comicparse.Modules.MainActivity.Activity.MainActivityUI
import com.qiuchenly.comicparse.R

class SplashActivity : BaseApp() {

    private var TAG = "SplashActivity"
    override fun getLayoutID() =
            R.layout.splash_view

    override fun getUISet(mSet: UISet): UISet {
        return mSet.apply {
            this.isFullScreen = true
            this.isSlidr = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_view)
        Handler().postDelayed({
            startActivity(Intent(this, MainActivityUI::class.java))
            finish()
        }, 3000)
    }
}
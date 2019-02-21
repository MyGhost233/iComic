package com.qiuchenly.comicparse.Modules.SplashActivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.qiuchenly.comicparse.Modules.MainActivity.Activity.MainActivityUI
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Simple.BaseApp
import org.greenrobot.eventbus.util.AsyncExecutor
import org.jetbrains.anko.startActivity

class SplashActivity : BaseApp() {
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
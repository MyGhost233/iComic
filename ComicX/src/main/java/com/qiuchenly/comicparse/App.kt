package com.qiuchenly.comicparse

import android.app.Application
import com.qiuchenly.comicparse.Core.Comic
import com.squareup.leakcanary.LeakCanary
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        //LeakCanary.install(this)
        Comic.initialization(this)
    }

    companion object {
        private val TAG = "App"

        fun closedApp() {
            Comic.closed()
            System.exit(0)
        }
    }
}

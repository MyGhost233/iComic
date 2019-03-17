package com.qiuchenly.comicparse

import android.app.Application
import com.qiuchenly.comicparse.Core.Comic


class App : Application() {
    override fun onCreate() {
        super.onCreate()
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

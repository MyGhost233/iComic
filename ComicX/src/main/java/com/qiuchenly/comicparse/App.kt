package com.qiuchenly.comicparse

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Http.CookieHelper
import io.realm.Realm
import io.realm.RealmConfiguration


class App : Application() {
    /**
     * 64 bits
     * @return
     */
    private fun getKey(): ByteArray {
        return byteArrayOf(0, 1, 2, 3, 4, 3, 2, 1, 0, 1, 2, 3, 4, 3, 2, 1, 0, 1, 2, 3, 4, 3, 2, 1, 0, 1, 2, 3, 4, 3, 2, 1, 0, 1, 2, 3, 4, 3, 2, 1, 0, 1, 2, 3, 4, 3, 2, 1, 0, 1, 2, 3, 4, 3, 2, 1, 0, 1, 2, 3, 4, 3, 2, 1)
    }

    override fun onCreate() {
        super.onCreate()
        Comic.initialization(this)
        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .name("realm.my_hooks")// 库文件名
                .encryptionKey(getKey())  // 加密
                .schemaVersion(1)  // 版本号
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)


        queue = Volley.newRequestQueue(this)
        cookieMaster = CookieHelper()
        sp = this.getSharedPreferences("qcly", Context.MODE_PRIVATE)
        cookieMaster!!.addCookie(sp!!.getString("Cookie", ""))
    }

    companion object {
        private val TAG = "App"

        var queue: RequestQueue? = null
            private set
        var cookieMaster: CookieHelper? = null
            private set
        internal var sp: SharedPreferences? = null

        fun closedApp() {
            Save()
            queue = null
            cookieMaster = null
            sp = null
            System.exit(0)
        }

        fun Save() {
            sp!!.edit().putString("Cookie", cookieMaster!!.toString()).apply()
        }
    }
}

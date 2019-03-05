package com.qiuchenly.comicparse.Core

import android.annotation.SuppressLint
import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

@SuppressLint("StaticFieldLeak")
object Comic {
    private var mContext: Context? = null
    /**
     * 64 bits
     * @return
     */
    private fun getKey(): ByteArray {
        return byteArrayOf(0, 1, 2, 3, 4, 3, 2, 1, 0, 1, 2, 3, 4, 3, 2, 1, 0, 1, 2, 3, 4, 3, 2, 1, 0, 1, 2, 3, 4, 3, 2, 1, 0, 1, 2, 3, 4, 3, 2, 1, 0, 1, 2, 3, 4, 3, 2, 1, 0, 1, 2, 3, 4, 3, 2, 1, 0, 1, 2, 3, 4, 3, 2, 1)
    }

    fun initialization(ctx: Context) {
        mContext = ctx
        Realm.init(mContext)
        val config = RealmConfiguration.Builder()
                .name("realm.my_hooks")// 库文件名
                .encryptionKey(getKey())  // 加密
                .schemaVersion(1)  // 版本号
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)
        realm = Realm.getDefaultInstance()
    }

    private var realm: Realm? = null
    fun getRealm() = realm!!

    fun getContext() = mContext

    fun closed() {
        Comic.getRealm().close()
        realm = null
        mContext = null
    }
}
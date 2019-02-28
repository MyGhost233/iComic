package com.qiuchenly.comicparse

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Http.RetrofitManager
import io.realm.Realm
import io.realm.RealmConfiguration
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*


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
        Comic.initialization(this)
        RetrofitManager.biCaClient(this)
        sp = this.getSharedPreferences("qcly", Context.MODE_PRIVATE)

        //记录崩溃信息
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            //获取崩溃时的UNIX时间戳
            val timeMillis = System.currentTimeMillis()
            //将时间戳转换成人类能看懂的格式，建立一个String拼接器
            val stringBuilder = StringBuilder(SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Date(timeMillis)))
            stringBuilder.append(":\n")
            //获取错误信息
            stringBuilder.append(throwable.message)
            stringBuilder.append("\n")
            //获取堆栈信息
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            throwable.printStackTrace(pw)
            stringBuilder.append(sw.toString())

            //这就是完整的错误信息了，你可以拿来上传服务器，或者做成本地文件保存等等等等
            val errorLog = stringBuilder.toString()

            Log.d(TAG,"onCreate:$errorLog")

            //最后如何处理这个崩溃，这里使用默认的处理方式让APP停止运行
            //defaultHandler.uncaughtException(thread, throwable)
        }
    }

    companion object {
        private val TAG = "App"
        internal var sp: SharedPreferences? = null

        fun closedApp() {
            sp = null
            System.exit(0)
        }
    }
}

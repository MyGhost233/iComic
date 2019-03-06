package com.qiuchenly.comicparse

import android.app.Application
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Http.RetrofitManager
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Comic.initialization(this)
        RetrofitManager.biCaClient(this)
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
            //最后如何处理这个崩溃，这里使用默认的处理方式让APP停止运行
            defaultHandler.uncaughtException(thread, throwable)
        }
    }

    companion object {
        private val TAG = "App"

        fun closedApp() {
            Comic.closed()
            System.exit(0)
        }
    }
}

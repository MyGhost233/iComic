package com.qiuchenly.comicparse.Service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.*
import com.qiuchenly.comicparse.Bean.ComicBookInfo
import com.qiuchenly.comicparse.Bean.DownloadBookInfo
import com.qiuchenly.comicparse.Bean.HotComicStrut
import com.qiuchenly.comicparse.MVP.Contract.ComicDetailContract
import com.qiuchenly.comicparse.MVP.Contract.ReaderContract
import com.qiuchenly.comicparse.MVP.Model.Activity_ComicModel
import com.qiuchenly.comicparse.MVP.Model.Activity_ReaderModel
import com.qiuchenly.comicparse.Utils.CustomUtils
import com.qiuchenly.comicparse.Utils.CustomUtils.Companion.MD5
import com.qiuchenly.comicparse.Utils.NotificationType
import com.qiuchenly.comicparse.VolleyImp.BaseURL
import io.realm.Realm
import org.jetbrains.anko.doAsync
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class DownloadService : Service() {

    private val realm: Realm? by lazy {
        Realm.getDefaultInstance()
    }

    override fun onCreate() {
        super.onCreate()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (realm != null) {
            realm?.close()
        }
    }

    var mBinder: DownloadBinder? = null
    override fun onBind(intent: Intent): IBinder {
        if (mBinder == null)
            mBinder = DownloadBinder(realm!!, this)
        return mBinder!!
    }

    class DownloadBinder(var realm: Realm, val context: Context) : Binder() {
        private var mList = ArrayList(realm.where(DownloadBookInfo::class.java).findAll())
        val mReader = Activity_ReaderModel()
        val mThreadPool = Executors.newFixedThreadPool(3) as ExecutorService
        val mCacheThread = ArrayList<DownThread>()

        init {

        }

        fun AddDownloadBook(comicInfo: HotComicStrut, view: ComicDetailContract.View) {
            thread {
                synchronized(realm) {
                    Activity_ComicModel().InitPageInfo(comicInfo.BookLink!!, object : ComicDetailContract.GetPageInfo {
                        override fun onFailed(reasonStr: String) {
                            CustomUtils.SendNotificationEx(context, "下载出错", "无法获取该漫画的详细数据:" + comicInfo.BookName, NotificationType.TYPE_ERROR_MSG)
                            view.ShowErrorMsg("下载出错:请检查该漫画数据是否正常。")
                        }

                        override fun onSuccessGetInfo(author: String, updateTime: String, hits: String, category: String, introduction: String, retPageList: ArrayList<ComicBookInfo>) {
                            if (realm.where(DownloadBookInfo::class.java).equalTo("BookName",
                                            comicInfo.BookName!!).findFirst()
                                    == null) {
                                realm.beginTransaction()
                                realm.createObject(DownloadBookInfo::class.java, comicInfo.BookName).apply {
                                    this.Author = comicInfo.Author!!
                                }
                                realm.commitTransaction()
                                mList.add(DownloadBookInfo().apply {
                                    BookName = comicInfo.BookName!!
                                })
                                mThreadPool.execute(object : DownThread(comicInfo.BookName!!,
                                        CustomUtils.sort(1, retPageList)) {
                                    override fun onThreadOver() {
                                        mCacheThread.remove(this)
                                    }

                                    override fun onThreadStart() {
                                        mCacheThread.add(this)
                                    }
                                })
                                view.ShowErrorMsg("开始下载...")
                            }
                        }
                    })
                }
            }
        }

        abstract inner class DownThread(bookName: String, retPageList: ArrayList<ComicBookInfo>) : Thread() {
            val mHand = Handler(Looper.getMainLooper())
            val BookName = bookName
            val PageListArr = retPageList

            abstract fun onThreadOver()
            abstract fun onThreadStart()

            private var BookID = System.currentTimeMillis().toString().substring(7, 13).toInt()

            private var downing = false
            private val fileName = Environment.getExternalStorageDirectory().path + "/ComicParseReader/"
            override fun run() {
                onThreadStart()
                for (comic in PageListArr) {
                    downing = true
                    val pageMD5 = MD5(comic.title!!)
                    mReader.getParsePicList(BaseURL.BASE_URL + comic.link!!, object : ReaderContract.GetPageCB {
                        override fun onLoadSucc(lst: ArrayList<String>, next: String, currInfo: String) {
                            CustomUtils.SendNotificationEx(context,
                                    "开始下载:$BookName",
                                    comic.title!!,
                                    BookID)
                            val bookFile = "$fileName$BookName/$pageMD5/"
                            doAsync {
                                for ((i, page) in lst.withIndex()) {
                                    val picMD5 = MD5(BaseURL.BASE_URL + page)
                                    val conn = URL(BaseURL.BASE_URL + page).openConnection()
                                            as HttpURLConnection
                                    conn.doInput = true
                                    val code = conn.responseCode
                                    if (code == 200) {
                                        val s = BitmapFactory.decodeStream(conn.inputStream)

                                        println(fileName)
                                    }
                                }
                                downing = false
                            }
                        }

                        override fun onFailed(reasonStr: String) {
                            mHand.post {
                                CustomUtils.SendNotificationEx(context,
                                        "下载部分章节失败:$BookName",
                                        "章节:${comic.title} 无法从服务器获取数据",
                                        System.currentTimeMillis().toInt()
                                )
                            }
                            downing = false
                        }
                    })
                    while (downing) {
                        Thread.sleep(500)
                    }
                }
                mHand.post {
                    Thread.sleep(500)
                    CustomUtils.SendNotificationEx(context,
                            "下载结束:$BookName",
                            "下载完成!",
                            BookID)
                }
                onThreadOver()
            }
        }

        fun hasBookInList(comicInfo: HotComicStrut): Boolean {
            mList.forEach {
                if (it.BookName == comicInfo.BookName) {
                    return true
                }
            }
            return false
        }
    }
}

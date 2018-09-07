package com.qiuchenly.comicparse.Service

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Environment
import com.qiuchenly.comicparse.Bean.*
import com.qiuchenly.comicparse.MVP.Contract.ComicDetailContract
import com.qiuchenly.comicparse.MVP.Contract.ReaderContract
import com.qiuchenly.comicparse.MVP.Model.Activity_ComicModel
import com.qiuchenly.comicparse.MVP.Model.Activity_ReaderModel
import com.qiuchenly.comicparse.Utils.CustomUtils
import com.qiuchenly.comicparse.Utils.CustomUtils.Companion.MD5
import com.qiuchenly.comicparse.Utils.NotificationType
import com.qiuchenly.comicparse.VolleyImp.BaseURL
import io.realm.Realm
import io.realm.RealmResults
import org.jetbrains.anko.runOnUiThread
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.thread
import kotlin.math.roundToInt

/**
 * 认真的写①次注释
 * 后台下载服务类,实现后台下载并将数据保存到本地数据库
 * @author 秋城落叶
 * @sample \NM$L
 */
class DownloadService : Service(), ServiceNotification {

    /**
     * 获取所有本地书籍书
     * @return 从数据库返回所有数据
     */
    override fun onGetAllDownBook(): RealmResults<DownloadBookInfo>? {
        return mRealm.where(DownloadBookInfo::class.java)
                .findAll()
    }

    /**
     * 将新书书籍加入本地数据库
     * @param mDownloadBookInfo 新书数据集合
     */
    override fun onBookAdded(mDownloadBookInfo: DownloadBookInfo) {
        mRealm.beginTransaction()
        mRealm.copyToRealm(mDownloadBookInfo)
        mRealm.commitTransaction()
    }

    /**
     * 检查下载本地数据库中是否存在此书
     * @param mBookName 书名
     * @return true or false
     */
    override fun onBookHasInDataBase(mBookName: String): Boolean {
        return mRealm.where(DownloadBookInfo::class.java)
                .equalTo("BookName", mBookName)
                .findFirst() == null
    }

    override fun onSaveBookPage(mBookName: String, mPageInfo: PageInfo) {
        runOnUiThread {
            val mBook = mRealm.where(DownloadBookInfo::class.java)
                    .equalTo("BookName", mBookName).findFirst()
            if (mBook != null) {
                mRealm.beginTransaction()
                mBook.PageList.add(mPageInfo)
                mRealm.commitTransaction()
            }
        }

    }

    override fun onMessage(title: String, content: String, NoticeID: Int) {
        CustomUtils.SendNotificationEx(this, title, content, NoticeID)
    }

    private val mRealm = Realm.getDefaultInstance()
    private var mBinder = DownloadBinder(this)
    override fun onBind(intent: Intent) = mBinder

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
        mBinder.mThreadPool.shutdownNow()
    }

    class DownloadBinder(private val mServiceNotification: ServiceNotification) : Binder() {
        private var mList = ArrayList<DownloadBookInfo>()
        val mThreadPool = Executors.newFixedThreadPool(3) as ExecutorService
        val mCacheThread = ArrayList<DownThread>()

        fun hasBookInList(comicInfo: HotComicStrut) = mList.any { it.BookName == comicInfo.BookName }

        fun download(comicInfo: HotComicStrut, view: ComicDetailContract.View) {
            mList = ArrayList(mServiceNotification.onGetAllDownBook())
            Activity_ComicModel().InitPageInfo(comicInfo.BookLink!!, object : ComicDetailContract.GetPageInfo {
                override fun onFailed(reasonStr: String) {
                    mServiceNotification.onMessage(
                            "下载出错",
                            "无法获取该漫画的详细数据:" + comicInfo.BookName,
                            NotificationType.TYPE_ERROR_MSG
                    )
                    view.ShowErrorMsg("下载出错:请检查该漫画数据是否正常。")
                }

                override fun onSuccessGetInfo(author: String,
                                              updateTime: String,
                                              hits: String, category: String,
                                              introduction: String,
                                              retPageList: ArrayList<ComicBookInfo>) {
                    val realList = CustomUtils.sort(
                            1,//排序正向
                            retPageList
                    )
                    if (mServiceNotification.onBookHasInDataBase(comicInfo.BookName!!)) {
                        val arr = DownloadBookInfo().apply {
                            BookName = comicInfo.BookName!!
                            realList.forEach {
                                this.PageList.add(PageInfo().apply {
                                    this.titleName = it.title!!
                                })
                            }
                        }
                        mServiceNotification.onBookAdded(arr)
                        mList.add(arr)
                        mThreadPool.execute(object : DownThread(
                                comicInfo.BookName!!,
                                realList,
                                mServiceNotification
                        ) {
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

    abstract class DownThread(
            private val BookName: String,
            private val PageList: ArrayList<ComicBookInfo>,
            private val mServiceNotification: ServiceNotification
    ) : Thread() {
        abstract fun onThreadOver()
        abstract fun onThreadStart()

        private val mReader = Activity_ReaderModel()
        private var mBookID = System.currentTimeMillis().toString().substring(7, 13).toInt()
        private var mIsDown = false
        private val mFileName = Environment.getExternalStorageDirectory().path + "/ComicParseReader/"

        override fun run() {
            onThreadStart()
            val fileParent = File(mFileName)
            if (!fileParent.exists()) {
                fileParent.mkdirs()
            }
            for ((a, comic) in PageList.withIndex()) {
                mIsDown = true
                val pageMD5 = MD5(comic.title!!)
                val single = PageInfo().apply {
                    titleName = comic.title!!
                }
                mReader.getParsePicList(BaseURL.BASE_URL + comic.link!!, object : ReaderContract.GetPageCB {
                    override fun onLoadSucc(lst: ArrayList<String>, next: String, currInfo: String) {
                        mServiceNotification.onMessage(
                                "开始下载:$BookName",
                                comic.title!!,
                                mBookID)
                        thread {
                            val bookFile = "$mFileName$BookName/$pageMD5/"
                            val parent = File(bookFile)
                            if (!parent.exists()) {
                                parent.mkdirs()//has permission of WRITE_READ SD
                            }
                            for ((i, page) in lst.withIndex()) {
                                mServiceNotification.onMessage(
                                        "开始下载:$BookName [${(((a + 1.00) / PageList.size) * 100).roundToInt()}%]",
                                        comic.title!! + " [${i + 1}/${lst.size} , 章节总进度:${(((i + 1.00) / lst.size) * 100).roundToInt()}%]",
                                        mBookID)
                                val realUrl = BaseURL.BASE_URL + page
                                val picMD5 = MD5(realUrl)
                                var code: Int
                                var retImage: Bitmap? = null
                                try {
                                    val conn = (URL(realUrl).openConnection() as HttpURLConnection).apply {
                                        doInput = true
                                    }
                                    code = conn.responseCode
                                    retImage = BitmapFactory.decodeStream(conn.inputStream)
                                } catch (e: Exception) {
                                    code = -1
                                    mServiceNotification.onMessage("读取网络图片时出错:${e.cause}",
                                            BookName + comic.title + " 第 $i 张图片",
                                            NotificationType.TYPE_ERROR_MSG)
                                }
                                if (code == 200) {
                                    val myPic = File("$bookFile$picMD5.jpg")
                                    if (myPic.createNewFile()) {
                                        val out = FileOutputStream(myPic)
                                        retImage!!.compress(Bitmap.CompressFormat.JPEG, 100, out)
                                        out.flush()
                                        out.close()
                                        single.imageList.add(ImageUrl().apply {
                                            this.urlAddress = BaseURL.BASE_URL + page
                                            localSaveAddress = "$bookFile$picMD5.jpg"
                                        })
                                    }
                                }
                            }
                            mIsDown = false
                        }
                    }

                    override fun onFailed(reasonStr: String) {
                        mServiceNotification.onMessage(
                                "下载部分章节失败:$BookName",
                                "章节:${comic.title} 无法从服务器获取数据",
                                System.currentTimeMillis().toInt())
                        mIsDown = false
                    }
                })
                while (mIsDown) {
                    Thread.sleep(500)
                }
                mServiceNotification.onSaveBookPage(BookName, single)
            }
            mServiceNotification.onMessage(
                    "下载结束:$BookName",
                    "下载完成!",
                    mBookID)
            onThreadOver()
        }
    }
}

interface ServiceNotification {
    fun onMessage(title: String, content: String, NoticeID: Int)
    fun onSaveBookPage(mBookName: String, mPageInfo: PageInfo)
    fun onBookHasInDataBase(mBookName: String): Boolean
    fun onBookAdded(mDownloadBookInfo: DownloadBookInfo)
    fun onGetAllDownBook(): RealmResults<DownloadBookInfo>?
}
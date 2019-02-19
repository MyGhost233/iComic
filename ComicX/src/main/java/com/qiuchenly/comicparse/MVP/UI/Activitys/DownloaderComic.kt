package com.qiuchenly.comicparse.MVP.UI.Activitys

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import com.qiuchenly.comicparse.MVP.Contract.DownloaderContract
import com.qiuchenly.comicparse.MVP.UI.Adapter.RecentlyPagerAdapter
import com.qiuchenly.comicparse.MVP.UI.Fragments.CurrDownItemFragment
import com.qiuchenly.comicparse.MVP.UI.Fragments.DownSuccessItemFragment
import com.qiuchenly.comicparse.MVP.UI.RecentlyReading.RecentlyRead
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Service.DownloadService
import com.qiuchenly.comicparse.Simple.BaseApp
import kotlinx.android.synthetic.main.activity_recently_read.*

class DownloaderComic : BaseApp(), DownloaderContract.View,CurrDownItemFragment.OnListTaskInfo {
    override fun onSuspendTask() {

    }

    override fun onResumeTask() {

    }

    override fun onDeleteTask() {

    }

    private var mConnect: ServiceConnection? = null
    private var mBinder: DownloadService.DownloadBinder? = null

    override fun getLayoutID(): Int {
        return R.layout.activity_recently_read
    }

    override fun getUISet(mSet: BaseApp.UISet): BaseApp.UISet {
        return mSet.apply {
            isSlidr = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val list = arrayListOf(
                RecentlyPagerAdapter.Struct("正在下载", CurrDownItemFragment.newInstance(1)),
                RecentlyPagerAdapter.Struct("下载完成", DownSuccessItemFragment.newInstance(1))
        )
        RecentlyRead.InitUI(this, list)
        clear_all.visibility = View.GONE
        activityName.text = "下载管理"

        mConnect = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {

            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                mBinder = service as DownloadService.DownloadBinder
            }
        }
        bindService(Intent(this, DownloadService::class.java), mConnect, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mConnect)
        mConnect = null
    }
}
package com.qiuchenly.comicparse.MVP.UI.Activitys

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.qiuchenly.comicparse.MVP.Contract.DownloaderContract
import com.qiuchenly.comicparse.MVP.Presenter.DownloaderPresenter
import com.qiuchenly.comicparse.MVP.UI.Adapter.RecentlyPagerAdapter
import com.qiuchenly.comicparse.MVP.UI.RecentlyReading.RecentlyRead
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Simple.BaseApp
import kotlinx.android.synthetic.main.activity_recently_read.*

class DownloaderComic : BaseApp<DownloaderContract.Presenter>(), DownloaderContract.View {
    override fun getLayoutID(): Int {
        return R.layout.activity_recently_read
    }

    override fun getUISet(mSet: UISet): UISet {
        return mSet.apply {
            isSlidr = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DownloaderPresenter(this)

        val list = arrayListOf(
                RecentlyPagerAdapter.Struct("正在下载", Fragment()),
                RecentlyPagerAdapter.Struct("下载完成", Fragment())
        )
        RecentlyRead.InitUI(this, list)
        clear_all.visibility = View.GONE
        activityName.text = "下载管理"
    }
}
package com.qiuchenly.comicparse.UI.model

import com.qiuchenly.comicparse.Bean.RecentlyReadingBean
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Modules.RecentlyReading.RecnetByWeek.WeekContract
import io.realm.Realm
import java.lang.ref.WeakReference

class RecentlyModel(mView: WeekContract.View?) {
    var realm: WeakReference<Realm>? = null

    init {
        realm = WeakReference(Comic.getRealm())
    }

    fun getAllRecently(): ArrayList<RecentlyReadingBean> {
        val list = realm?.get()?.where(RecentlyReadingBean::class.java)
                ?.findAll() ?: return ArrayList()
        return ArrayList(list.toList())
    }
}
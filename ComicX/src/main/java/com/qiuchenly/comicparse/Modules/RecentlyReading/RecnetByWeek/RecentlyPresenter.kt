package com.qiuchenly.comicparse.Modules.RecentlyReading.RecnetByWeek

import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.Core.Comic
import io.realm.Realm

class RecentlyPresenter(mView: WeekContract.View?) {

    fun getAllRecently(): ArrayList<ComicBookInfo_Recently> {
        return ArrayList(Comic.getRealm().where(ComicBookInfo_Recently::class.java).findAll())
    }
}
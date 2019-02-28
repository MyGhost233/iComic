package com.qiuchenly.comicparse.Modules.RecentlyReading.RecnetByWeek

import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import io.realm.Realm

class RecentlyPresenter(mView: WeekContract.View?) {

    fun getAllRecently(): ArrayList<ComicBookInfo_Recently> {
        return ArrayList(Realm.getDefaultInstance().where(ComicBookInfo_Recently::class.java).findAll())
    }
}
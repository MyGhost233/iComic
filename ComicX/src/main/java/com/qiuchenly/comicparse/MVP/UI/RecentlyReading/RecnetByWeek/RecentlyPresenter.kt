package com.qiuchenly.comicparse.MVP.UI.RecentlyReading.RecnetByWeek

import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.Simple.BasePresenterImp
import io.realm.Realm

class RecentlyPresenter(mView: WeekContract.View?) : BasePresenterImp<WeekContract.View, Model>(mView), WeekContract.Presenter {
    override fun createModel(): Model {
        return Model()
    }

    override fun getAllRecently(): ArrayList<ComicBookInfo_Recently> {
        return ArrayList(Realm.getDefaultInstance().where(ComicBookInfo_Recently::class.java).findAll())
    }
}
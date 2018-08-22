package com.example.qiuchenly.comicparse.MVP.UI.RecentlyReading.RecnetByWeek

import com.example.qiuchenly.comicparse.App
import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import com.example.qiuchenly.comicparse.Simple.BasePresenterImp

class RecentlyPresenter(mView: WeekContract.View?) : BasePresenterImp<WeekContract.View, Model>(mView), WeekContract.Presenter {
    override fun createModel(): Model {
        return Model()
    }

    override fun getAllRecently(): ArrayList<ComicBookInfo.ComicBookInfo_Recently> {
        return ArrayList(App.mDataBase.RECENTLY_GET_ALL())
    }
}
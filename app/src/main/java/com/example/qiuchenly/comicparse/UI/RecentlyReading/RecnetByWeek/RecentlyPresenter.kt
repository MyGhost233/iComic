package com.example.qiuchenly.comicparse.UI.RecentlyReading.RecnetByWeek

import com.example.qiuchenly.comicparse.App
import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import com.example.qiuchenly.comicparse.Simple.BasePresenterImp

class RecentlyPresenter(mView: WeekContract.View?) : BasePresenterImp<WeekContract.View, Model>(mView), WeekContract.Presenter {
    override fun createModel(): Model {
        return Model()
    }

    override fun getAllRecently(): MutableList<ComicBookInfo.ComicBookInfo_Recently> {
        return App.mDataBase.RECENTLY_GET_ALL()
    }
}
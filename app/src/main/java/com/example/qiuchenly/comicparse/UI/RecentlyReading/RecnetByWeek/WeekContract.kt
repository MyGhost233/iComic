package com.example.qiuchenly.comicparse.UI.RecentlyReading.RecnetByWeek

import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import com.example.qiuchenly.comicparse.Simple.BaseModel
import com.example.qiuchenly.comicparse.Simple.BasePresenter
import com.example.qiuchenly.comicparse.Simple.BaseView

interface WeekContract {

    interface Presenter : BasePresenter {
        fun getAllRecently(): MutableList<ComicBookInfo.ComicBookInfo_Recently>
    }

    interface View : BaseView<Presenter>

    interface Model : BaseModel

}
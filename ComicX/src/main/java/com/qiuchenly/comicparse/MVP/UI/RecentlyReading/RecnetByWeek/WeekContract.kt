package com.qiuchenly.comicparse.MVP.UI.RecentlyReading.RecnetByWeek

import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.Simple.BaseModel
import com.qiuchenly.comicparse.Simple.BasePresenter
import com.qiuchenly.comicparse.Simple.BaseView

interface WeekContract {

    interface Presenter : BasePresenter {
        fun getAllRecently(): MutableList<ComicBookInfo_Recently>
    }

    interface View : BaseView<Presenter>

    interface Model : BaseModel

}
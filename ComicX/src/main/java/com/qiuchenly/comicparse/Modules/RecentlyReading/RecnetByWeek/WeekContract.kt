package com.qiuchenly.comicparse.Modules.RecentlyReading.RecnetByWeek

import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.BaseImp.BaseModel
import com.qiuchenly.comicparse.BaseImp.BasePresenter
import com.qiuchenly.comicparse.BaseImp.BaseView

interface WeekContract {

    interface Presenter : BasePresenter {
        fun getAllRecently(): MutableList<ComicBookInfo_Recently>
    }

    interface View : BaseView

    interface Model : BaseModel

}
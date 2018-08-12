package com.example.qiuchenly.comicparse.UI.Main_MyIndex

import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import com.example.qiuchenly.comicparse.Simple.BaseModel
import com.example.qiuchenly.comicparse.Simple.BasePresenter
import com.example.qiuchenly.comicparse.Simple.BaseView

interface MyDetailsContract {

    interface Presenter : BasePresenter {
        fun getLocalBookByDB(): ArrayList<ComicBookInfo.ComicBookInfo_Recently>?
    }

    interface View : BaseView<Presenter> {
        fun getAllLocalBook(): ArrayList<ComicBookInfo.ComicBookInfo_Recently>?
    }

    interface Model : BaseModel
}
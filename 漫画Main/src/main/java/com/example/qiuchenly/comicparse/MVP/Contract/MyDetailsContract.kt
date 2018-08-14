package com.example.qiuchenly.comicparse.MVP.Contract

import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import com.example.qiuchenly.comicparse.Bean.HotComicStrut
import com.example.qiuchenly.comicparse.Simple.BaseModel
import com.example.qiuchenly.comicparse.Simple.BasePresenter
import com.example.qiuchenly.comicparse.Simple.BaseView
import io.realm.RealmResults

interface MyDetailsContract {

    interface Presenter : BasePresenter {
        fun getLocalBookByDB(): ArrayList<ComicBookInfo.ComicBookInfo_Recently>?
    }

    interface View : BaseView<Presenter> {
        fun getAllLocalBook(): ArrayList<ComicBookInfo.ComicBookInfo_Recently>?
        fun getLocalListData(): RealmResults<HotComicStrut>?
    }

    interface Model : BaseModel
}
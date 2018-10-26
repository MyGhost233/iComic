package com.qiuchenly.comicparse.MVP.Contract

import com.qiuchenly.comicparse.Bean.HotComicStrut
import com.qiuchenly.comicparse.Simple.BaseModel
import com.qiuchenly.comicparse.Simple.BasePresenter
import com.qiuchenly.comicparse.Simple.BaseView
import io.realm.RealmResults

interface MyDetailsContract {

    interface Presenter : BasePresenter {
        fun getLocalBookByDB(): ArrayList<HotComicStrut>?
    }

    interface View : BaseView<Presenter> {
        fun getAllLocalBook(): ArrayList<HotComicStrut>?
        fun getLocalListData(): RealmResults<HotComicStrut>?
    }

    interface Model : BaseModel
}
package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.Views

import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.Simple.BaseModel
import com.qiuchenly.comicparse.Simple.BasePresenter
import com.qiuchenly.comicparse.Simple.BaseView
import io.realm.RealmResults

interface MyDetailsContract {

    interface Presenter : BasePresenter {
        fun getLocalBookByDB(): ArrayList<HotComicStrut>?
    }

    interface View : BaseView {
        fun getAllLocalBook(): ArrayList<HotComicStrut>?
        fun getLocalListData(): RealmResults<HotComicStrut>?
        fun onSrcReady(img: String)
    }

    interface Model : BaseModel
}
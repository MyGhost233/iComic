package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.Views

import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.BaseImp.BaseModel
import com.qiuchenly.comicparse.BaseImp.BasePresenter
import com.qiuchenly.comicparse.BaseImp.BaseView
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
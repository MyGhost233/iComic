package com.qiuchenly.comicparse.UI.view

import com.qiuchenly.comicparse.Bean.LocalFavoriteBean
import com.qiuchenly.comicparse.UI.BaseImp.BaseView
import io.realm.RealmResults

interface MyDetailsContract {
    interface View : BaseView {
        fun onSrcReady(img: String)
        fun setRecentlySize(size: Int)
        fun setLocateComic(realmResults: RealmResults<LocalFavoriteBean>?)
    }
}
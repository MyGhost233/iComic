package com.example.qiuchenly.comicparse.UI.Main

import com.example.qiuchenly.comicparse.Bean.HotComicStrut
import com.example.qiuchenly.comicparse.Simple.BaseModel
import com.example.qiuchenly.comicparse.Simple.BasePresenter
import com.example.qiuchenly.comicparse.Simple.BaseView

interface MainContract {
    interface View : BaseView<Presenter> {
        fun getHotComicList(arr: ArrayList<HotComicStrut>)
    }

    interface Presenter : BasePresenter {
        fun getHotComic()
    }

    interface Model : BaseModel {

    }

    interface BaseGetCallBack {
        fun onFailed(reasonStr: String)
    }

    interface GetHotComic : BaseGetCallBack {
         fun onSuccessGetHot(arr: ArrayList<HotComicStrut>)
     }
}
package com.qiuchenly.comicparse.MVP.Contract

import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.Simple.BaseModel
import com.qiuchenly.comicparse.Simple.BasePresenter
import com.qiuchenly.comicparse.Simple.BaseView

interface MainContract {
    interface View : BaseView {
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
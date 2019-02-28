package com.qiuchenly.comicparse.MVP.OtherTemp

import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.BaseImp.BaseModel
import com.qiuchenly.comicparse.BaseImp.BasePresenter
import com.qiuchenly.comicparse.BaseImp.BaseView

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
package com.qiuchenly.comicparse.MVP.Contract

import com.qiuchenly.comicparse.Bean.HotComicStrut
import com.qiuchenly.comicparse.MVP.Model.FragmentRecommendModel
import com.qiuchenly.comicparse.Simple.BaseModel
import com.qiuchenly.comicparse.Simple.BasePresenter
import com.qiuchenly.comicparse.Simple.BaseView

interface NetRecommentContract {
    interface Presenter : BasePresenter {
        fun getWebSiteByIndexData()
    }

    interface View : BaseView<Presenter> {
        fun GetIndexPageSucc(mTopViewComicBook: ArrayList<HotComicStrut>?, newUpdate: ArrayList<HotComicStrut>?, rhmh: ArrayList<HotComicStrut>?, ommh: ArrayList<HotComicStrut>?, dlmh: ArrayList<HotComicStrut>?, rhhk: ArrayList<HotComicStrut>?, omhk: ArrayList<HotComicStrut>?, dlhk: ArrayList<HotComicStrut>?, a_Z: ArrayList<HotComicStrut>?)

    }

    interface Model : BaseModel {
        fun GetIndexPage(ret: FragmentRecommendModel.IndexPageGetter)
    }

}
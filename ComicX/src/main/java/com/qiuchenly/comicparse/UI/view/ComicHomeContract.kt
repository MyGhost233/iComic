package com.qiuchenly.comicparse.UI.view

import com.qiuchenly.comicparse.Bean.ComicComm
import com.qiuchenly.comicparse.UI.BaseImp.BaseView
import com.qiuchenly.comicparse.Bean.ComicHome_Category

interface ComicHomeContract {
    interface View : BaseView, DongManZhiJia {
        fun OnNetFailed(message: String?)
        fun goSelectSource()
        fun final()
    }

    interface DongManZhiJia {
        /**
         * 获得动漫之家的首页推荐数据
         */
        fun onGetDMZRecommendSuch(mComicList: ArrayList<ComicComm>)

        fun onGetDMZJCategory(mComicCategory: ArrayList<ComicHome_Category>)
    }

    interface DMZJ_Adapter {
        fun addDMZJCategory(mComicCategory: ArrayList<ComicHome_Category>)
        fun addDMZJData(mComicList: ArrayList<ComicComm>)
    }
}
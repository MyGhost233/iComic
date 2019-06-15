package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend

import com.qiuchenly.comicparse.BaseImp.BaseView
import com.qiuchenly.comicparse.Bean.ComicHome_Category
import com.qiuchenly.comicparse.Bean.ComicHome_RecomendList
import com.qiuchenly.comicparse.ProductModules.Bika.CategoryObject

interface RecommentContract {
    interface View : BaseView, DongManZhiJia {
        fun OnNetFailed(message: String?)
        fun goSelectSource()
        fun final()
    }

    interface DongManZhiJia {
        /**
         * 获得动漫之家的首页推荐数据
         */
        fun onGetDMZRecommendSuch(mComicList: ComicHome_RecomendList)

        fun onGetDMZJCategory(mComicCategory: ArrayList<ComicHome_Category>)
    }

    interface DMZJ_Adapter {
        fun addDMZJCategory(mComicCategory: ArrayList<ComicHome_Category>)
        fun addDMZJData(mComicList: ComicHome_RecomendList)
    }
}
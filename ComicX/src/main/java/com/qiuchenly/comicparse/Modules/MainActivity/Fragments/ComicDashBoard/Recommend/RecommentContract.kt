package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend

import com.qiuchenly.comicparse.BaseImp.BaseView
import com.qiuchenly.comicparse.Bean.ComicHome_RecomendList
import com.qiuchenly.comicparse.Http.Bika.CategoryObject

interface RecommentContract {
    interface View : BaseView {
        fun OnNetFailed(message: String?)
        fun onGetBikaCategorySucc(arrayList_categories: java.util.ArrayList<CategoryObject>?)

        /**
         * 获得动漫之家的首页推荐数据
         */
        fun onGetDMZRecommendSuch(mComicList: ComicHome_RecomendList)

        fun goLoginBika()
        fun goSelectSource()
        fun final()
    }
}
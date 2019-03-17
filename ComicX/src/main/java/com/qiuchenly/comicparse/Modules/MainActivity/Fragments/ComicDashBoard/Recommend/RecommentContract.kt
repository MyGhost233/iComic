package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend

import com.qiuchenly.comicparse.BaseImp.BaseView
import com.qiuchenly.comicparse.Http.Bika.CategoryObject

interface RecommentContract {
    interface View : BaseView {
        fun OnNetFailed()
        fun onGetBikaCategorySucc(arrayList_categories: java.util.ArrayList<CategoryObject>?)
        fun goLoginBika()
        fun goSelectSource()
        fun final()
    }
}
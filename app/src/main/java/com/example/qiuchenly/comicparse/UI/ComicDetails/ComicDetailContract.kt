package com.example.qiuchenly.comicparse.UI.ComicDetails

import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import com.example.qiuchenly.comicparse.Simple.BaseModel
import com.example.qiuchenly.comicparse.Simple.BasePresenter
import com.example.qiuchenly.comicparse.Simple.BaseView

interface ComicDetailContract {
    interface View : BaseView<Presenter> {
        fun GetInfoSucc(author: String, updateTime: String, hits: String, category: String, introduction: String, retPageList: ArrayList<ComicBookInfo>)
    }

    interface Presenter : BasePresenter {
        fun InitPageInfo(page:String)
    }

    interface Model : BaseModel {

    }

    interface BaseGetCallBack {
        fun onFailed(reasonStr: String)
    }

    interface GetPageInfo : BaseGetCallBack {
         fun onSuccessGetInfo(author: String, updateTime: String, hits: String, category: String, introduction: String, retPageList: ArrayList<ComicBookInfo>)
     }
}
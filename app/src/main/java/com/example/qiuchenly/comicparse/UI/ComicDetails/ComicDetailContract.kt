package com.example.qiuchenly.comicparse.UI.ComicDetails

import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import com.example.qiuchenly.comicparse.Bean.HotComicStrut
import com.example.qiuchenly.comicparse.Simple.BaseModel
import com.example.qiuchenly.comicparse.Simple.BasePresenter
import com.example.qiuchenly.comicparse.Simple.BaseView

interface ComicDetailContract {
    interface View : BaseView<Presenter> {
        fun GetInfoSucc(author: String, updateTime: String, hits: String, category: String, introduction: String, retPageList: ArrayList<ComicBookInfo>)
        fun getScoreSucc(rate: String)
    }

    interface Presenter : BasePresenter {
        fun initPageInfo(page: String)
        fun Save2DB(comicInfo: HotComicStrut, isLocal: Boolean = false)
    }

    interface Model : BaseModel {
        fun getBookScore(bookID: String, cb: ComicDetailContract.GetScore)
        fun InitPageInfo(page: String, cb: ComicDetailContract.GetPageInfo)
    }

    interface BaseGetCallBack {
        fun onFailed(reasonStr: String)
    }

    interface GetScore : BaseGetCallBack {
        fun getScoreSucc(rate: String)

    }

    interface GetPageInfo : BaseGetCallBack {
        fun onSuccessGetInfo(author: String, updateTime: String, hits: String, category: String, introduction: String, retPageList: ArrayList<ComicBookInfo>)
    }
}
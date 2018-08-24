package com.qiuchenly.comicparse.MVP.Contract

import com.qiuchenly.comicparse.Bean.ComicBookInfo
import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.Simple.BaseModel
import com.qiuchenly.comicparse.Simple.BasePresenter
import com.qiuchenly.comicparse.Simple.BaseView

interface ComicDetailContract {
    interface View : BaseView<Presenter> {
        fun GetInfoSucc(author: String, updateTime: String, hits: String, category: String, introduction: String, retPageList: ArrayList<ComicBookInfo>)
        fun getScoreSucc(rate: String)
    }

    interface Presenter : BasePresenter {
        fun initPageInfo(page: String)
        fun Save2DB(comicInfo: ComicBookInfo_Recently)
    }

    interface Model : BaseModel {
        fun getBookScore(bookID: String, cb: GetScore)
        fun InitPageInfo(page: String, cb: GetPageInfo)
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
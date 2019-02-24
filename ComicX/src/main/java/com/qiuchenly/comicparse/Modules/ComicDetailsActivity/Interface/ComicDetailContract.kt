package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface

import com.qiuchenly.comicparse.Bean.ComicBookInfo
import com.qiuchenly.comicparse.Simple.BaseLoadingView
import com.qiuchenly.comicparse.Simple.BaseView

interface ComicDetailContract {
    interface View : BaseView, BaseLoadingView {
        fun GetInfoSucc(author: String, updateTime: String, hits: String, category: String, introduction: String, retPageList: ArrayList<ComicBookInfo>)
        fun getScoreSucc(rate: String)
        fun scrollWithPosition(position: Int)
        fun onProgressChanged()
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
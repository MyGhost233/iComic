package com.example.qiuchenly.comicparse.MVP.Contract

import com.example.qiuchenly.comicparse.Simple.BaseModel
import com.example.qiuchenly.comicparse.Simple.BasePresenter
import com.example.qiuchenly.comicparse.Simple.BaseView

interface ReaderContract {
    interface View : BaseView<Presenter>, GetPageCB

    interface Presenter : BasePresenter {
        fun getParsePicList(url: String, CB: GetPageCB)
        fun updateReadPoint(point: String)
    }

    interface Model : BaseModel {
        fun getParsePicList(url: String, CB: GetPageCB)
    }

    interface BaseGetCallBack {
        fun onFailed(reasonStr: String)
    }

    interface GetPageCB : BaseGetCallBack {
        fun onLoadSucc(lst: ArrayList<String>, next: String, currInfo: String)
    }
}
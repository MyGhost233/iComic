package com.qiuchenly.comicparse.Modules.ReadingActivity

import com.qiuchenly.comicparse.Simple.BaseModel
import com.qiuchenly.comicparse.Simple.BasePresenter
import com.qiuchenly.comicparse.Simple.BaseView

interface ReaderContract {
    interface View : BaseView, GetPageCB

    interface BaseGetCallBack {
        fun onFailed(reasonStr: String)
    }

    interface GetPageCB : BaseGetCallBack {
        fun onLoadSucc(lst: ArrayList<String>, next: String, currInfo: String)
    }
}
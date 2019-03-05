package com.qiuchenly.comicparse.Modules.ReadingActivity

import com.qiuchenly.comicparse.BaseImp.BaseView

interface ReaderContract {
    interface View : BaseView, GetPageCB

    interface BaseGetCallBack {
        fun onFailed(reasonStr: String)
    }

    interface GetPageCB : BaseGetCallBack {
        fun onLoadSucc(lst: ArrayList<String>, next: String, currInfo: String, isOver: Boolean)
    }
}
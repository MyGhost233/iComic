package com.qiuchenly.comicparse.UI.view

import com.qiuchenly.comicparse.UI.BaseImp.BaseView

interface SearchContract {
    interface View : BaseView, GetPageCB {
        fun onKeysLoadSucc(arr: ArrayList<String>)
    }

    interface BaseGetCallBack {
        fun onFailed(reasonStr: String)
    }

    interface GetPageCB : BaseGetCallBack
}
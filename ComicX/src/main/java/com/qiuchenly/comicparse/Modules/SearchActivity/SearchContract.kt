package com.qiuchenly.comicparse.Modules.SearchActivity

import com.qiuchenly.comicparse.BaseImp.BaseView

interface SearchContract {
    interface View : BaseView, GetPageCB {
        fun onKeysLoadSucc(arr: ArrayList<String>)
    }

    interface BaseGetCallBack {
        fun onFailed(reasonStr: String)
    }

    interface GetPageCB : BaseGetCallBack {
    }
}
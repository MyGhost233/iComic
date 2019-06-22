package com.qiuchenly.comicparse.UI.view

import com.qiuchenly.comicparse.UI.BaseImp.BaseView

interface MyDetailsContract {
    interface View : BaseView {
        fun onSrcReady(img: String)
    }
}
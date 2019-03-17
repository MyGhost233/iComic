package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.Views

import com.qiuchenly.comicparse.BaseImp.BaseView

interface MyDetailsContract {
    interface View : BaseView {
        fun onSrcReady(img: String)
    }
}
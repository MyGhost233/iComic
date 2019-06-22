package com.qiuchenly.comicparse.UI.view

import com.qiuchenly.comicparse.UI.BaseImp.BaseView

interface AuthBikaViewContract {

    interface View : BaseView {
        fun LoginSucc()
        fun LoginFailed()
    }
}
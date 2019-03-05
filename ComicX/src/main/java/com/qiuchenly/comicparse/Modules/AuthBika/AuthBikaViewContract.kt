package com.qiuchenly.comicparse.Modules.AuthBika

import com.qiuchenly.comicparse.BaseImp.BaseView

interface AuthBikaViewContract {

    interface View : BaseView {
        fun LoginSucc()
        fun LoginFailed()
    }
}
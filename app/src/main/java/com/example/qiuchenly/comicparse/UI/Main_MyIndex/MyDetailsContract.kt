package com.example.qiuchenly.comicparse.UI.Main_MyIndex

import com.example.qiuchenly.comicparse.Simple.BasePresenter
import com.example.qiuchenly.comicparse.Simple.BaseView

interface MyDetailsContract {

    interface Presenter : BasePresenter {

    }

    interface View : BaseView<Presenter> {

    }
}
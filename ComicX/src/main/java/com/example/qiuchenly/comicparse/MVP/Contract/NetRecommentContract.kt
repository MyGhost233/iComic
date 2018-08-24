package com.example.qiuchenly.comicparse.MVP.Contract

import com.example.qiuchenly.comicparse.Simple.BaseModel
import com.example.qiuchenly.comicparse.Simple.BasePresenter
import com.example.qiuchenly.comicparse.Simple.BaseView

interface NetRecommentContract {
    interface Presenter : BasePresenter {

    }

    interface View : BaseView<Presenter> {

    }

    interface Model:BaseModel{

    }

}
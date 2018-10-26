package com.qiuchenly.comicparse.MVP.Contract

import com.qiuchenly.comicparse.Simple.BaseModel
import com.qiuchenly.comicparse.Simple.BasePresenter
import com.qiuchenly.comicparse.Simple.BaseView

interface DownloaderContract {
    interface View : BaseView<Presenter> {

    }

    interface Model : BaseModel {

    }

    interface Presenter : BasePresenter {

    }
}
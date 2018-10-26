package com.qiuchenly.comicparse.MVP.Presenter

import com.qiuchenly.comicparse.MVP.Contract.MainSwitchContract
import com.qiuchenly.comicparse.MVP.Model.Activity_MainModel
import com.qiuchenly.comicparse.Simple.BasePresenterImp

class MainSwichPresenter(view: MainSwitchContract.View) : BasePresenterImp<MainSwitchContract.View, Activity_MainModel>(view), MainSwitchContract.Presenter {
    override fun createModel(): Activity_MainModel {
        return Activity_MainModel()
    }
}
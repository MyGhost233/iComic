package com.example.qiuchenly.comicparse.MVP.Presenter

import com.example.qiuchenly.comicparse.MVP.Contract.MainSwitchContract
import com.example.qiuchenly.comicparse.MVP.Model.Activity_MainModel
import com.example.qiuchenly.comicparse.Simple.BasePresenterImp

class MainSwichPresenter(view: MainSwitchContract.View): BasePresenterImp<MainSwitchContract.View, Activity_MainModel>(view), MainSwitchContract.Presenter {
    override fun createModel(): Activity_MainModel {
        return Activity_MainModel()
    }
}
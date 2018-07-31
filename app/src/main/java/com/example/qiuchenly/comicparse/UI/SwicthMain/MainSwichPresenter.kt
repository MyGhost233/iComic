package com.example.qiuchenly.comicparse.UI.SwicthMain

import com.example.qiuchenly.comicparse.Simple.BasePresenterImp

class MainSwichPresenter(view: MainSwitchContract.View): BasePresenterImp<MainSwitchContract.View, MainModel>(view), MainSwitchContract.Presenter {
    override fun createModel(): MainModel {
        return MainModel()
    }
}
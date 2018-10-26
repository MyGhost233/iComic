package com.qiuchenly.comicparse.MVP.Presenter

import com.qiuchenly.comicparse.MVP.Contract.DownloaderContract
import com.qiuchenly.comicparse.MVP.Model.Activity_downloaderModel
import com.qiuchenly.comicparse.Simple.BasePresenterImp

class DownloaderPresenter(mView: DownloaderContract.View) : BasePresenterImp<DownloaderContract.View, DownloaderContract.Model>(mView) {
    override fun createModel(): Activity_downloaderModel {
        return Activity_downloaderModel()
    }
}
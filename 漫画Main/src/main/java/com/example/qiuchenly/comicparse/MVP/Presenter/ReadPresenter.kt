package com.example.qiuchenly.comicparse.MVP.Presenter

import com.example.qiuchenly.comicparse.App
import com.example.qiuchenly.comicparse.MVP.Contract.ReaderContract
import com.example.qiuchenly.comicparse.MVP.Model.Activity_ReaderModel
import com.example.qiuchenly.comicparse.MVP.UI.Activitys.MainSwitch
import com.example.qiuchenly.comicparse.Simple.AppManager
import com.example.qiuchenly.comicparse.Simple.BasePresenterImp

class ReadPresenter(mView: ReaderContract.View?) : BasePresenterImp<ReaderContract.View, Activity_ReaderModel>(mView), ReaderContract.Presenter {
    override fun createModel(): Activity_ReaderModel {
        return Activity_ReaderModel()
    }

    override fun updateReadPoint(point: String) {
        val s = point.split("\n")
        App.mDataBase.updatePoint(s[0], s[1])
        (AppManager.getActivity(MainSwitch::class.java) as MainSwitch).updateInfo()
    }

    override fun getParsePicList(url: String, CB: ReaderContract.GetPageCB) {
        SuperModel!!.getParsePicList(url, object : ReaderContract.GetPageCB {
            override fun onFailed(reasonStr: String) {
                if (isShow) SuperView?.onFailed(reasonStr)
            }

            override fun onLoadSucc(lst: ArrayList<String>, next: String, currInfo: String) {
                if (isShow) SuperView?.onLoadSucc(lst, next, currInfo)
            }
        })
    }
}
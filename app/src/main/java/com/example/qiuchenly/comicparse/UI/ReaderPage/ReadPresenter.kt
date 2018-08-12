package com.example.qiuchenly.comicparse.UI.ReaderPage

import com.example.qiuchenly.comicparse.App
import com.example.qiuchenly.comicparse.Simple.AppManager
import com.example.qiuchenly.comicparse.UI.SwicthMain.MainSwitch

class ReadPresenter(var mView: ReaderContract.View?) : ReaderContract.Presenter {
    override fun updateReadPoint(point: String) {
        val s = point.split("\n")
        App.mDataBase.updatePoint(s[0], s[1])
        (AppManager.getActivity(MainSwitch::class.java) as MainSwitch).updateInfo()
    }

    override fun getParsePicList(url: String, CB: ReaderContract.GetPageCB) {
        model.getParsePicList(url, object : ReaderContract.GetPageCB {
            override fun onFailed(reasonStr: String) {
                if (mView != null) mView?.onFailed(reasonStr)
            }

            override fun onLoadSucc(lst: ArrayList<String>, next: String, currInfo: String) {
                if (mView != null) mView?.onLoadSucc(lst, next, currInfo)
            }
        })
    }

    private val model = ReadModel()

    init {
        mView?.setPres(this)
    }

    override fun Destory() {
        if (mView != null) mView = null
    }
}
package com.qiuchenly.comicparse.MVP.Presenter

import com.qiuchenly.comicparse.MVP.UI.Activitys.MainSwitch
import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.MVP.Contract.ReaderContract
import com.qiuchenly.comicparse.MVP.Model.Activity_ReaderModel
import com.qiuchenly.comicparse.Simple.AppManager
import com.qiuchenly.comicparse.Simple.BasePresenterImp
import io.realm.Realm

class ReadPresenter(mView: ReaderContract.View?) : BasePresenterImp<ReaderContract.View, Activity_ReaderModel>(mView), ReaderContract.Presenter {
    override fun createModel(): Activity_ReaderModel {
        return Activity_ReaderModel()
    }

    override fun updateReadPoint(point: String) {
        val realm = Realm.getDefaultInstance()
        val s = point.split("\n")
        val obj = realm.where(ComicBookInfo_Recently::class.java)
                .equalTo("BookName", s[0])
                .findFirst()!!
        realm.beginTransaction()
        obj.BookName_read_point = s[1]
        realm.commitTransaction()
        realm.close()
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
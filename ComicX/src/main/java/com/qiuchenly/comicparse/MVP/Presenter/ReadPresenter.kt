package com.qiuchenly.comicparse.MVP.Presenter

import com.qiuchenly.comicparse.Modules.MainActivity.Activity.MainActivityUI
import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.MVP.Contract.ReaderContract
import com.qiuchenly.comicparse.MVP.Model.Activity_ReaderModel
import com.qiuchenly.comicparse.Simple.AppManager
import io.realm.Realm

class ReadPresenter(private var mView: ReaderContract.View?) {
    fun updateReadPoint(point: String) {
        val realm = Realm.getDefaultInstance()
        val s = point.split("\n")
        val obj = realm.where(ComicBookInfo_Recently::class.java)
                .equalTo("BookName", s[0])
                .findFirst()!!
        realm.beginTransaction()
        obj.BookName_read_point = s[1]
        realm.commitTransaction()
        realm.close()
        (AppManager.getActivity(MainActivityUI::class.java) as MainActivityUI).updateInfo()
    }

    var superModel = Activity_ReaderModel()

    fun getParsePicList(url: String, CB: ReaderContract.GetPageCB) {
        superModel.getParsePicList(url, object : ReaderContract.GetPageCB {
            override fun onFailed(reasonStr: String) {
                mView?.onFailed(reasonStr)
            }

            override fun onLoadSucc(lst: ArrayList<String>, next: String, currInfo: String) {
                mView?.onLoadSucc(lst, next, currInfo)
            }
        })
    }
}
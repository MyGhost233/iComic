package com.qiuchenly.comicparse.MVP.Presenter

import com.qiuchenly.comicparse.Bean.ComicBookInfo
import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.MVP.Contract.ComicDetailContract
import com.qiuchenly.comicparse.MVP.Model.Activity_ComicModel
import com.qiuchenly.comicparse.Simple.BasePresenterImp
import io.realm.Realm

class ComicDetailsPresenter(view: ComicDetailContract.View) : BasePresenterImp<ComicDetailContract.View, ComicDetailContract.Model>(view), ComicDetailContract.Presenter {
    override fun Save2DB(comicInfo: ComicBookInfo_Recently) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val obj = realm.createObject(ComicBookInfo_Recently::class.java, comicInfo.BookName)
        realm.commitTransaction()
        realm.close()
    }

    override fun createModel(): ComicDetailContract.Model {
        return Activity_ComicModel()
    }

    override fun initPageInfo(page: String) {
        SuperModel!!.InitPageInfo(page, object : ComicDetailContract.GetPageInfo {
            override fun onFailed(reasonStr: String) {
                if (isShow) SuperView!!.ShowErrorMsg(reasonStr)
            }

            override fun onSuccessGetInfo(author: String, updateTime: String, hits: String, category: String, introduction: String, retPageList: ArrayList<ComicBookInfo>) {
                if (isShow) SuperView!!.GetInfoSucc(author, updateTime, hits, category, introduction, retPageList)
            }
        })
        SuperModel!!.getBookScore(page, object : ComicDetailContract.GetScore {
            override fun onFailed(reasonStr: String) {
                if (isShow) SuperView?.ShowErrorMsg(reasonStr)
            }

            override fun getScoreSucc(rate: String) {
                if (isShow) SuperView?.getScoreSucc(rate)
            }
        })
    }
}
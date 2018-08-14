package com.example.qiuchenly.comicparse.MVP.Presenter

import com.example.qiuchenly.comicparse.App
import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import com.example.qiuchenly.comicparse.Bean.HotComicStrut
import com.example.qiuchenly.comicparse.MVP.Contract.ComicDetailContract
import com.example.qiuchenly.comicparse.MVP.Model.Activity_ComicModel
import com.example.qiuchenly.comicparse.Simple.BasePresenterImp

class ComicDetailsPresenter(view: ComicDetailContract.View) : BasePresenterImp<ComicDetailContract.View, ComicDetailContract.Model>(view), ComicDetailContract.Presenter {
    override fun Save2DB(comicInfo: HotComicStrut, isLocal: Boolean) {
        val book = ComicBookInfo.ComicBookInfo_Recently().apply {
            this.BookName = comicInfo.bookName
            this.BookName_Link = comicInfo.bookLink
            this.BookName_Pic_Link = comicInfo.bookImgSrc
            this.BookName_read_point = "暂无"
            this.author = comicInfo.author
        }
        if (isLocal) App.mDataBase.LOCALBOOK_INSERT(book)
        else App.mDataBase.RECENTLY_INSERT(book)


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
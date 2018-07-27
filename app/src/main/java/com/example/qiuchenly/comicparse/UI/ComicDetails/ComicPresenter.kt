package com.example.qiuchenly.comicparse.UI.ComicDetails

import com.example.qiuchenly.comicparse.Bean.ComicBookInfo

class ComicPresenter(var mView: ComicDetailContract.View?) : ComicDetailContract.Presenter {
    override fun InitPageInfo(page: String) {
        model.InitPageInfo(page, object : ComicDetailContract.GetPageInfo {
            override fun onFailed(reasonStr: String) {
               if (mView!=null) mView!!.ShowErrorMsg(reasonStr)
            }

            override fun onSuccessGetInfo(author: String, updateTime: String, hits: String, category: String, introduction: String, retPageList: ArrayList<ComicBookInfo>) {
                if (mView!=null) mView!!.GetInfoSucc(author, updateTime, hits, category, introduction, retPageList)
            }
        })
    }

    private val model = ComicModel()

    init {
        if (mView!=null) mView!!.setPres(this)
    }

    override fun Destory() {
        mView = null
    }
}
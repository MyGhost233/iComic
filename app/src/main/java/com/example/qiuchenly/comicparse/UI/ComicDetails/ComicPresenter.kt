package com.example.qiuchenly.comicparse.UI.ComicDetails
import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import com.example.qiuchenly.comicparse.Simple.BasePresenterImp

class ComicPresenter(view: ComicDetailContract.View) : BasePresenterImp<ComicDetailContract.View, ComicDetailContract.Model>(view), ComicDetailContract.Presenter {
    override fun createModel(): ComicDetailContract.Model {
        return ComicModel()
    }

    override fun initPageInfo(page: String) {
        SuperModel.InitPageInfo(page, object : ComicDetailContract.GetPageInfo {
            override fun onFailed(reasonStr: String) {
                if (isShow) SuperView!!.ShowErrorMsg(reasonStr)
            }

            override fun onSuccessGetInfo(author: String, updateTime: String, hits: String, category: String, introduction: String, retPageList: ArrayList<ComicBookInfo>) {
                if (isShow) SuperView!!.GetInfoSucc(author, updateTime, hits, category, introduction, retPageList)
            }
        })
        SuperModel.getBookScore(page, object : ComicDetailContract.GetScore {
            override fun onFailed(reasonStr: String) {
                if (isShow) SuperView?.ShowErrorMsg(reasonStr)
            }

            override fun getScoreSucc(rate: String) {
                if (isShow) SuperView?.getScoreSucc(rate)
            }
        })
    }
}
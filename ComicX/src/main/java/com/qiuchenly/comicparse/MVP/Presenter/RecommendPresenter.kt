package com.qiuchenly.comicparse.MVP.Presenter

import com.qiuchenly.comicparse.Bean.HotComicStrut
import com.qiuchenly.comicparse.MVP.Contract.NetRecommentContract
import com.qiuchenly.comicparse.MVP.Model.FragmentRecommendModel
import com.qiuchenly.comicparse.Simple.BasePresenterImp

class RecommendPresenter(mView: NetRecommentContract.View) : BasePresenterImp<NetRecommentContract.View, NetRecommentContract.Model>(mView), NetRecommentContract.Presenter {
    override fun createModel(): NetRecommentContract.Model {
        return FragmentRecommendModel()
    }

    override fun getWebSiteByIndexData() {
        SuperModel.GetIndexPage(object : FragmentRecommendModel.IndexPageGetter {
            override fun mGetResult(retCode: Int,
                                    reasonStr: String,
                                    mTopViewComicBook: ArrayList<HotComicStrut>?,
                                    newUpdate: ArrayList<HotComicStrut>?,
                                    rhmh: ArrayList<HotComicStrut>?,
                                    ommh: ArrayList<HotComicStrut>?,
                                    dlmh: ArrayList<HotComicStrut>?,
                                    rhhk: ArrayList<HotComicStrut>?,
                                    omhk: ArrayList<HotComicStrut>?,
                                    dlhk: ArrayList<HotComicStrut>?,
                                    a_Z: ArrayList<HotComicStrut>?) {
                if (isShow && retCode == 200)
                    SuperView.GetIndexPageSucc(mTopViewComicBook,
                            newUpdate ,
                            rhmh,
                            ommh,
                            dlmh,
                            rhhk,
                            omhk,
                            dlhk,
                            a_Z)
                else if (isShow)
                    SuperView.OnNetFailed()
            }
        })
    }
}
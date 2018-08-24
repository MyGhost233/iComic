package com.qiuchenly.comicparse.MVP.Presenter

import com.qiuchenly.comicparse.MVP.Contract.NetRecommentContract
import com.qiuchenly.comicparse.MVP.Model.FragmentRecommendModel
import com.qiuchenly.comicparse.Simple.BasePresenterImp

class RecommendPresenter(mView: NetRecommentContract.View) : BasePresenterImp<NetRecommentContract.View, NetRecommentContract.Model>(mView), NetRecommentContract.Presenter {
    override fun createModel(): NetRecommentContract.Model {
        return FragmentRecommendModel()
    }
}
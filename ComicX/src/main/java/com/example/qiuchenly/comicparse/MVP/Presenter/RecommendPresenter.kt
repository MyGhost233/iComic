package com.example.qiuchenly.comicparse.MVP.Presenter

import com.example.qiuchenly.comicparse.MVP.Contract.NetRecommentContract
import com.example.qiuchenly.comicparse.MVP.Model.FragmentRecommendModel
import com.example.qiuchenly.comicparse.Simple.BasePresenterImp

class RecommendPresenter(mView: NetRecommentContract.View): BasePresenterImp<NetRecommentContract.View, NetRecommentContract.Model>(mView),NetRecommentContract.Presenter {
    override fun createModel(): NetRecommentContract.Model {
        return FragmentRecommendModel()
    }
}
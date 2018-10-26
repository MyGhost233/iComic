package com.qiuchenly.comicparse.MVP.Presenter

import com.qiuchenly.comicparse.Bean.HotComicStrut
import com.qiuchenly.comicparse.MVP.Contract.MyDetailsContract
import com.qiuchenly.comicparse.MVP.Model.Fragment_IndexPage_Model
import com.qiuchenly.comicparse.Simple.BasePresenterImp
import io.realm.Realm

class IndexPagePresenter(mView: MyDetailsContract.View) : BasePresenterImp<MyDetailsContract.View, MyDetailsContract.Model>(mView), MyDetailsContract.Presenter {
    override fun createModel(): MyDetailsContract.Model {
        return Fragment_IndexPage_Model()
    }

    override fun getLocalBookByDB(): ArrayList<HotComicStrut>? {
        return ArrayList(Realm.getDefaultInstance().where(HotComicStrut::class.java).findAll())
    }
}
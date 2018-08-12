package com.example.qiuchenly.comicparse.MVP.Presenter

import com.example.qiuchenly.comicparse.App
import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import com.example.qiuchenly.comicparse.MVP.Contract.MyDetailsContract
import com.example.qiuchenly.comicparse.MVP.Model.Fragment_IndexPage_Model
import com.example.qiuchenly.comicparse.Simple.BasePresenterImp

class IndexPagePresenter(mView: MyDetailsContract.View) : BasePresenterImp<MyDetailsContract.View, MyDetailsContract.Model>(mView), MyDetailsContract.Presenter {
    override fun createModel(): MyDetailsContract.Model {
        return Fragment_IndexPage_Model()
    }

    override fun getLocalBookByDB(): ArrayList<ComicBookInfo.ComicBookInfo_Recently>? {
        return ArrayList(App.mDataBase.LOCALBOOK_GET_ALL())
    }
}
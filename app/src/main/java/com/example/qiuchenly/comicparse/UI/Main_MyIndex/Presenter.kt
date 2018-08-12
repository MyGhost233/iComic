package com.example.qiuchenly.comicparse.UI.Main_MyIndex

import com.example.qiuchenly.comicparse.App
import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import com.example.qiuchenly.comicparse.Simple.BasePresenterImp

class Presenter(mView: MyDetailsContract.View) : BasePresenterImp<MyDetailsContract.View, MyDetailsContract.Model>(mView), MyDetailsContract.Presenter {
    override fun createModel(): MyDetailsContract.Model {
        return Model()
    }

    override fun getLocalBookByDB(): ArrayList<ComicBookInfo.ComicBookInfo_Recently>? {
        return ArrayList(App.mDataBase.LOCALBOOK_GET_ALL())
    }
}
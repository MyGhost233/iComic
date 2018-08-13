package com.example.qiuchenly.comicparse.MVP.Presenter

import com.example.qiuchenly.comicparse.MVP.Contract.ComicBoardContract
import com.example.qiuchenly.comicparse.MVP.Model.ComicBoardModel
import com.example.qiuchenly.comicparse.Simple.BasePresenterImp

class ComicBoardPresenter(view:ComicBoardContract.View): BasePresenterImp<ComicBoardContract.View, ComicBoardContract.Model>(view) {
    override fun createModel(): ComicBoardContract.Model {
        return ComicBoardModel()
    }

}
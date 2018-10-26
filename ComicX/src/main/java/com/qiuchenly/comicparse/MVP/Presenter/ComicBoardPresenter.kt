package com.qiuchenly.comicparse.MVP.Presenter

import com.qiuchenly.comicparse.MVP.Contract.ComicBoardContract
import com.qiuchenly.comicparse.MVP.Model.ComicBoardModel
import com.qiuchenly.comicparse.Simple.BasePresenterImp

class ComicBoardPresenter(view: ComicBoardContract.View) : BasePresenterImp<ComicBoardContract.View, ComicBoardContract.Model>(view) {
    override fun createModel(): ComicBoardContract.Model {
        return ComicBoardModel()
    }

}
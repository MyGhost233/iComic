package com.example.qiuchenly.comicparse.UI.ReaderPage

class ReadPresenter(var mView: ReaderContract.View?) : ReaderContract.Presenter {
    override fun getParsePicList(url: String, CB: ReaderContract.GetPageCB) {
        model.getParsePicList(url, object : ReaderContract.GetPageCB {
            override fun onFailed(reasonStr: String) {
                mView?.onFailed(reasonStr)
            }

            override fun onLoadSucc(lst: ArrayList<String>, next: String, currInfo: String) {
                mView?.onLoadSucc(lst, next, currInfo)
            }
        })
    }

    private val model = ReadModel()

    init {
        mView?.setPres(this)
    }

    override fun Destory() {
        if (mView != null) mView = null
    }
}
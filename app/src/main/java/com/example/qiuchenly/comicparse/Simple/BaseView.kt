package com.example.qiuchenly.comicparse.Simple

interface BaseView<P:BasePresenter> {
    fun initView()
    fun setPres(mPres:P)

    fun ShowErrorMsg(msg:String)
}
package com.example.qiuchenly.comicparse.Simple

interface BaseView<P> {
    fun setPres(mPres: P)

    fun ShowErrorMsg(msg: String)
}
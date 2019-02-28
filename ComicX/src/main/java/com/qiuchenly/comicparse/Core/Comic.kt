package com.qiuchenly.comicparse.Core

import android.annotation.SuppressLint
import android.content.Context
import io.realm.Realm

@SuppressLint("StaticFieldLeak")
object Comic {
    private var mContext: Context? = null

    fun initialization(ctx: Context) {
        mContext = ctx
    }

    fun getContext() = mContext
}
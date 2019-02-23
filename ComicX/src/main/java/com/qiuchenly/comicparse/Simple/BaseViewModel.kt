package com.qiuchenly.comicparse.Simple

import android.databinding.ObservableBoolean
import android.util.Log
import retrofit2.Callback
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

abstract class BaseViewModel<T> : Callback<T> {

    private var TAG = "BaseViewModel"
    override fun onFailure(call: Call<T>, t: Throwable) {
        whenFailure(t)
    }

    fun whenFailure(t: Throwable) {
        Log.d(TAG, "whenFailure:" + t.message)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (!cancel)
            GetSuccess(call, response)
    }

    private var cancel = false
    abstract fun GetSuccess(call: Call<T>, response: Response<T>)
    open fun cancel() {
        cancel = true
    }

    fun Call<T>.getUrl(): String {
        return request().url().toString()
    }
}
package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ViewModel

import com.qiuchenly.comicparse.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface.ComicDetailContract
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class ComicDetailsViewModel(private var callback: ComicDetailContract.View) : BaseViewModel<ResponseBody>() {
    override fun loadFailure(t: Throwable) {

    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        super.onFailure(call, t)
        callback.onLoadFailed()
    }

    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        callback.onLoadSuccess()
    }

    private var mCall: Call<ResponseBody>? = null

    override fun cancel() {
        super.cancel()
        if (mCall != null) mCall!!.cancel()
        mCall = null
    }
}
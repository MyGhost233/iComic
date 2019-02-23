package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.Recommend.ViewModel

import com.qiuchenly.comicparse.Http.RetrofitManager
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.Recommend.Request.Requests
import com.qiuchenly.comicparse.Simple.BaseViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class RecommendViewModel : BaseViewModel<ResponseBody>() {

    override fun GetSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        super.onFailure(call, t)

    }

    private var mCall: Call<ResponseBody>? = null


    private var indexUrl = ""
    fun getIndex() {
        mCall = RetrofitManager.get()
                .create(Requests::class.java)
                .getIndex()
        indexUrl = mCall!!.getUrl()
        mCall!!.enqueue(this)
    }

    override fun cancel() {
        super.cancel()
        if (mCall != null) mCall!!.cancel()
    }
}
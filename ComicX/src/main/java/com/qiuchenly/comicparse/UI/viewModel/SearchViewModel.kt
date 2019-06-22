package com.qiuchenly.comicparse.UI.viewModel

import com.qiuchenly.comicparse.UI.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicparse.ProductModules.Bika.responses.GeneralResponse
import com.qiuchenly.comicparse.ProductModules.Bika.responses.KeywordsResponse
import com.qiuchenly.comicparse.ProductModules.Bika.BikaApi
import com.qiuchenly.comicparse.UI.view.SearchContract
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(private var mView: SearchContract.View?) : BaseViewModel<ResponseBody>() {
    override fun loadFailure(t: Throwable) {
        mView?.ShowErrorMsg(t.message!!)
    }

    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }

    override fun cancel() {
        super.cancel()
        mView = null
    }

    fun getBikaKeyWords() {
        BikaApi.getAPI()?.getKeywords(PreferenceHelper.getToken(Comic.getContext()))
                ?.enqueue(object : Callback<GeneralResponse<KeywordsResponse>> {
                    override fun onFailure(call: Call<GeneralResponse<KeywordsResponse>>, t: Throwable) {
                        loadFailure(Throwable("加载Bika关键词失败!"))
                    }

                    override fun onResponse(call: Call<GeneralResponse<KeywordsResponse>>, response: Response<GeneralResponse<KeywordsResponse>>) {
                        mView?.onKeysLoadSucc(response.body()?.data!!.keywords)
                    }
                })
    }
}
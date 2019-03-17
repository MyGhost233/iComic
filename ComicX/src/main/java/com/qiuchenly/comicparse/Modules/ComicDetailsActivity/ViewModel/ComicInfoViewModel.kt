package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ViewModel

import com.qiuchenly.comicparse.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Http.Bika.PreferenceHelper
import com.qiuchenly.comicparse.Http.Bika.responses.ComicDetailResponse
import com.qiuchenly.comicparse.Http.Bika.responses.GeneralResponse
import com.qiuchenly.comicparse.Http.BikaApi
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface.ComicDetailContract
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicInfoViewModel(view: ComicDetailContract.ComicInfo.View) : BaseViewModel<ResponseBody>() {
    override fun loadFailure(t: Throwable) {
        mView?.ShowErrorMsg(t.message!!)
    }

    var mView: ComicDetailContract.ComicInfo.View? = view
    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }

    override fun cancel() {
        super.cancel()
        mView = null
    }

    fun getComicInfo(bookID: String?) {
        BikaApi.getAPI()?.getComicWithId(PreferenceHelper.getToken(Comic.getContext()), bookID)
                ?.enqueue(object : Callback<GeneralResponse<ComicDetailResponse>> {
                    override fun onFailure(call: Call<GeneralResponse<ComicDetailResponse>>, t: Throwable) {
                        loadFailure(Throwable("网络请求失败!"))
                    }

                    override fun onResponse(call: Call<GeneralResponse<ComicDetailResponse>>, response: Response<GeneralResponse<ComicDetailResponse>>) {
                        mView?.SetBikaInfo(response.body()?.data?.comic)
                    }
                })
    }
}
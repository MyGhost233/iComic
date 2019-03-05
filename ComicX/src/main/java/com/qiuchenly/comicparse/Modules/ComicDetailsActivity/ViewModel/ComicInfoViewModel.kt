package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ViewModel

import com.qiuchenly.comicparse.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Http.BikaApi.PreferenceHelper
import com.qiuchenly.comicparse.Http.BikaApi.responses.ComicDetailResponse
import com.qiuchenly.comicparse.Http.BikaApi.responses.GeneralResponse
import com.qiuchenly.comicparse.Http.RetrofitManager
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface.ComicDetailContract
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicInfoViewModel(view: ComicDetailContract.ComicInfo.View) : BaseViewModel<ResponseBody>() {
    var mView: ComicDetailContract.ComicInfo.View? = view
    override fun GetSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }

    override fun cancel() {
        super.cancel()
        mView = null
    }

    fun getComicInfo(bookID: String?) {
        RetrofitManager.getBiCaApi()?.getComicWithId(PreferenceHelper.getToken(Comic.getContext()), bookID)
                ?.enqueue(object : Callback<GeneralResponse<ComicDetailResponse>> {
                    override fun onFailure(call: Call<GeneralResponse<ComicDetailResponse>>, t: Throwable) {
                        mView?.ShowErrorMsg("加载漫画章节失败!")
                    }

                    override fun onResponse(call: Call<GeneralResponse<ComicDetailResponse>>, response: Response<GeneralResponse<ComicDetailResponse>>) {
                        mView?.SetBikaInfo(response.body()?.data?.comic)
                    }
                })
    }
}
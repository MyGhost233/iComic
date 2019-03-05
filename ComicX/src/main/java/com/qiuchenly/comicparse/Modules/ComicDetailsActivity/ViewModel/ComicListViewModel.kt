package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ViewModel

import com.qiuchenly.comicparse.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Http.BikaApi.PreferenceHelper
import com.qiuchenly.comicparse.Http.BikaApi.responses.DataClass.ComicEpisodeResponse.ComicEpisodeResponse
import com.qiuchenly.comicparse.Http.BikaApi.responses.GeneralResponse
import com.qiuchenly.comicparse.Http.RetrofitManager
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface.ComicDetailContract
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicListViewModel(view: ComicDetailContract.Comiclist.View) : BaseViewModel<ResponseBody>() {
    var mView: ComicDetailContract.Comiclist.View? = view
    override fun GetSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }

    override fun cancel() {
        super.cancel()
        mView = null
    }


    fun getComicList(id: String) {
        RetrofitManager.getBiCaApi()?.getComicEpisode(PreferenceHelper.getToken(Comic.getContext()), id, 1)
                ?.enqueue(object : Callback<GeneralResponse<ComicEpisodeResponse>> {
                    override fun onFailure(call: Call<GeneralResponse<ComicEpisodeResponse>>, t: Throwable) {
                        mView?.ShowErrorMsg("加载漫画章节失败!")
                    }

                    override fun onResponse(call: Call<GeneralResponse<ComicEpisodeResponse>>, response: Response<GeneralResponse<ComicEpisodeResponse>>) {
                        mView?.SetBikaPages(response.body()?.data?.eps?.docs,id)
                    }
                })
    }
}
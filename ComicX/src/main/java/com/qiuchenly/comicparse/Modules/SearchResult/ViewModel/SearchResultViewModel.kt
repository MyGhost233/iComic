package com.qiuchenly.comicparse.Modules.SearchResult.ViewModel

import com.qiuchenly.comicparse.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Http.BikaApi.PreferenceHelper
import com.qiuchenly.comicparse.Http.BikaApi.responses.ComicRandomListResponse
import com.qiuchenly.comicparse.Http.BikaApi.responses.DataClass.ComicListResponse.ComicListResponse
import com.qiuchenly.comicparse.Http.BikaApi.responses.GeneralResponse
import com.qiuchenly.comicparse.Http.RetrofitManager
import com.qiuchenly.comicparse.Modules.SearchResult.View.ResultViews
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultViewModel(view: ResultViews) : BaseViewModel<ResponseBody>() {

    var mView: ResultViews? = view

    override fun cancel() {
        super.cancel()
        mView = null
    }

    override fun GetSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        super.onFailure(call, t)
    }

    fun getCategoryComic(categoryName: String?, page: Int) {
        RetrofitManager.getBiCaApi()?.getComicList(PreferenceHelper.getToken(Comic.getContext()), page, categoryName, null, null, null, "ua", null, null)?.enqueue(object : Callback<GeneralResponse<ComicListResponse>> {
            override fun onFailure(call: Call<GeneralResponse<ComicListResponse>>, t: Throwable) {
                mView?.ShowErrorMsg("加载漫画信息时出错!")
                mView?.getComicList_Bika(null)
            }

            override fun onResponse(call: Call<GeneralResponse<ComicListResponse>>, response: Response<GeneralResponse<ComicListResponse>>) {
                mView?.getComicList_Bika(response.body()?.data?.comics)
            }
        })
    }

    fun getRandomComic() {
        RetrofitManager.getBiCaApi()?.getRandomComicList(PreferenceHelper.getToken(Comic.getContext()))?.enqueue(object : Callback<GeneralResponse<ComicRandomListResponse>> {
            override fun onFailure(call: Call<GeneralResponse<ComicRandomListResponse>>, t: Throwable) {
                mView?.ShowErrorMsg("加载漫画信息时出错!")
                mView?.getRandomComicList_Bika(null)
            }

            override fun onResponse(call: Call<GeneralResponse<ComicRandomListResponse>>, response: Response<GeneralResponse<ComicRandomListResponse>>) {
                mView?.getRandomComicList_Bika(response.body()?.data?.comics)
            }
        })
    }
}
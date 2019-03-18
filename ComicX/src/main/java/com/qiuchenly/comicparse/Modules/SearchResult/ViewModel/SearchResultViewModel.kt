package com.qiuchenly.comicparse.Modules.SearchResult.ViewModel

import com.qiuchenly.comicparse.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Http.Bika.PreferenceHelper
import com.qiuchenly.comicparse.Http.Bika.responses.ComicRandomListResponse
import com.qiuchenly.comicparse.Http.Bika.responses.DataClass.ComicListResponse.ComicListResponse
import com.qiuchenly.comicparse.Http.Bika.responses.GeneralResponse
import com.qiuchenly.comicparse.Http.BikaApi
import com.qiuchenly.comicparse.Modules.SearchResult.View.ResultViews
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultViewModel(view: ResultViews) : BaseViewModel<ResponseBody>() {
    override fun loadFailure(t: Throwable) {

    }

    var mView: ResultViews? = view

    override fun cancel() {
        super.cancel()
        mView = null
    }

    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }

    fun getCategoryComic(categoryName: String?, page: Int) {
        BikaApi.getAPI()?.getComicList(PreferenceHelper.getToken(Comic.getContext()), page, categoryName, null, null, null, "ua", null, null)?.enqueue(object : Callback<GeneralResponse<ComicListResponse>> {
            override fun onFailure(call: Call<GeneralResponse<ComicListResponse>>, t: Throwable) {
                mView?.ShowErrorMsg("加载漫画信息时出错!")
                mView?.getComicList_Bika(null)
            }

            override fun onResponse(call: Call<GeneralResponse<ComicListResponse>>, response: Response<GeneralResponse<ComicListResponse>>) {
                mView?.getComicList_Bika(response.body()?.data?.comics)
            }
        })
    }

    fun searchComic(key: String?, page: Int) {
        BikaApi.getAPI()?.getComicListWithSearchKey(PreferenceHelper.getToken(Comic.getContext()), page, key)?.enqueue(object : Callback<GeneralResponse<ComicListResponse>> {
            override fun onFailure(call: Call<GeneralResponse<ComicListResponse>>, t: Throwable) {
                mView?.ShowErrorMsg("搜索漫画信息时出错!")
                mView?.getComicList_Bika(null)
            }

            override fun onResponse(call: Call<GeneralResponse<ComicListResponse>>, response: Response<GeneralResponse<ComicListResponse>>) {
                mView?.getComicList_Bika(response.body()?.data?.comics)
            }
        })
    }

    fun getRandomComic() {
        BikaApi.getAPI()?.getRandomComicList(PreferenceHelper.getToken(Comic.getContext()))?.enqueue(object : Callback<GeneralResponse<ComicRandomListResponse>> {
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
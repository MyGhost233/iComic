package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ViewModel

import com.qiuchenly.comicparse.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Bean.ComicHomeComicChapterList
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Http.Bika.PreferenceHelper
import com.qiuchenly.comicparse.Http.Bika.responses.DataClass.ComicEpisodeResponse.ComicEpisodeResponse
import com.qiuchenly.comicparse.Http.Bika.responses.GeneralResponse
import com.qiuchenly.comicparse.Http.BikaApi
import com.qiuchenly.comicparse.Http.DongManZhiJia
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface.ComicDetailContract
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicListViewModel(view: ComicDetailContract.Comiclist.View) : BaseViewModel<ResponseBody>() {
    override fun loadFailure(t: Throwable) {

    }

    var mView: ComicDetailContract.Comiclist.View? = view
    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }

    override fun cancel() {
        super.cancel()
        mView = null
    }


    fun getComicList(id: String) {
        BikaApi.getAPI()?.getComicEpisode(PreferenceHelper.getToken(Comic.getContext()), id, 1)
                ?.enqueue(object : Callback<GeneralResponse<ComicEpisodeResponse>> {
                    override fun onFailure(call: Call<GeneralResponse<ComicEpisodeResponse>>, t: Throwable) {
                        loadFailure(Throwable("加载漫画章节失败!"))
                    }

                    override fun onResponse(call: Call<GeneralResponse<ComicEpisodeResponse>>, response: Response<GeneralResponse<ComicEpisodeResponse>>) {
                        mView?.SetBikaPages(response.body()?.data?.eps?.docs, id)
                    }
                })
    }

    fun getDMZJComicList(obj_id: String) {
        DongManZhiJia.getV3API().getComic(obj_id)
                .enqueue(object : Callback<ComicHomeComicChapterList> {
                    override fun onFailure(call: Call<ComicHomeComicChapterList>, t: Throwable) {
                        loadFailure(Throwable("加载动漫之家漫画章节失败!"))
                    }

                    override fun onResponse(call: Call<ComicHomeComicChapterList>, response: Response<ComicHomeComicChapterList>) {
                        if (response.body() != null)
                            mView?.SetDMZJChapter(response.body()!!)
                    }
                })
    }
}
package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.ViewModel

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qiuchenly.comicparse.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Bean.ComicHome_RecomendList
import com.qiuchenly.comicparse.Bean.ComicHome_Recommend
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Http.Bika.CategoryObject
import com.qiuchenly.comicparse.Http.Bika.DefaultCategoryObject
import com.qiuchenly.comicparse.Http.Bika.PreferenceHelper
import com.qiuchenly.comicparse.Http.Bika.RestWakaClient
import com.qiuchenly.comicparse.Http.Bika.responses.CategoryResponse
import com.qiuchenly.comicparse.Http.Bika.responses.GeneralResponse
import com.qiuchenly.comicparse.Http.Bika.responses.InitialResponse
import com.qiuchenly.comicparse.Http.Bika.responses.WakaInitResponse
import com.qiuchenly.comicparse.Http.BikaApi
import com.qiuchenly.comicparse.Http.DongManZhiJia
import com.qiuchenly.comicparse.Http.NMSL.NMSLClient
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.RecommentContract
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecommendViewModel(Views: RecommentContract.View?) : BaseViewModel<ResponseBody>() {
    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }

    override fun loadFailure(t: Throwable) {
        mView?.OnNetFailed(t.message)
    }

    private var mView = Views

    fun destory() {
        mView = null
    }

    private var mCall: Call<ResponseBody>? = null

    fun getIndex() {
        getDMZJRecomend()
        if (!PreferenceHelper.getNoLoginBika(Comic.getContext())) {
            getBikaAllCategory()
        } else {
            mView?.ShowErrorMsg("你还未选择漫画数据提供源!搞快丶去选择!")
            if (PreferenceHelper.getIsFirst(Comic.getContext()))
                mView?.goSelectSource()
            mView?.final()
        }
    }

    fun getDMZJRecomend() {
        val mCall = DongManZhiJia.getV3API().recommend
        mCall.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loadFailure(Throwable("加载动漫之家的推荐数据失败!"))
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val ret = response.body()?.string() ?: return
                val cls = JSONArray(ret)
                var size = 0
                val mArrs = ArrayList<ComicHome_Recommend>()
                while (size < cls.length() - 1) {
                    mArrs.add(Gson().fromJson(cls.getJSONObject(size).toString(), ComicHome_Recommend()::class.java))
                    size++
                }
                val mComicList = ComicHome_RecomendList()
                mComicList.lastNewer = Gson().fromJson(cls.getJSONObject(8).toString(), ComicHome_Recommend()::class.java)
                mComicList.normalType = mArrs
                mView?.onGetDMZRecommendSuch(mComicList)
            }
        })
    }

    fun getInit() {
        if (PreferenceHelper.getToken(Comic.getContext()) == "") {
            getBikaIndex()
            return
        }
        val callInit = BikaApi.getAPI()!!.getInit(PreferenceHelper.getToken(Comic.getContext()))
        callInit.enqueue(object : Callback<GeneralResponse<InitialResponse>> {
            override fun onResponse(call: Call<GeneralResponse<InitialResponse>>, response: Response<GeneralResponse<InitialResponse>>) {
                if (response.code() == 200) {
                    val imageServer = ((response.body() as GeneralResponse<*>).data as InitialResponse).imageServer
                    if (imageServer != null && imageServer.isNotEmpty()) {
                        PreferenceHelper.setImageStorage(Comic.getContext(), imageServer)
                        mView?.ShowErrorMsg("使用上次登录的Token.")
                    }
                } else {
                    loadFailure(Throwable("完啦,bika图片服务器炸了."))
                }
            }

            override fun onFailure(call: Call<GeneralResponse<InitialResponse>>, t: Throwable) {
                t.printStackTrace()
                loadFailure(Throwable("网络有点问题."))
            }
        })
    }

    var userName = ""
    var pass = ""
    private fun getBikaIndex() {
        userName = PreferenceHelper.getUserLoginEmail(Comic.getContext())
        pass = PreferenceHelper.getUserLoginPassword(Comic.getContext())
        if (userName == "" || pass == "") {
            mView?.ShowErrorMsg("请先登录bika账号后再刷新!")
            mView?.goLoginBika()
        } else {
            if (PreferenceHelper.getLocalApiDataCategoryList(Comic.getContext()) != "") {
                arrayList_categories = Gson().fromJson(PreferenceHelper.getLocalApiDataCategoryList(Comic.getContext()), object : TypeToken<List<CategoryObject>>() {}.type) as ArrayList<CategoryObject>
                mView?.onGetBikaCategorySucc(arrayList_categories)
            } else getBikaAllCategory()
        }
    }

    var arrayList_categories: ArrayList<CategoryObject>? = null
    var arrayList_defaultCategories: ArrayList<DefaultCategoryObject>? = null
    var arrayList_keywords: ArrayList<String>? = null
    var arrayList_tags: ArrayList<String>? = null
    fun getBikaAllCategory() {
        if (PreferenceHelper.getToken(Comic.getContext()) == "") {
            if (PreferenceHelper.getNoLoginBika(Comic.getContext())) {
            } else
            //initBikaApi()
            return
        }
        if (PreferenceHelper.getNoLoginBika(Comic.getContext())) {

        } else
            BikaApi.getAPI()?.getCategories(PreferenceHelper.getToken(Comic.getContext()))?.enqueue(
                    object : Callback<GeneralResponse<CategoryResponse>> {
                        override fun onFailure(call: Call<GeneralResponse<CategoryResponse>>, t: Throwable) {
                            //this.onFailure(call, t)
                            mView?.OnNetFailed(t.message)
                        }

                        override fun onResponse(call: Call<GeneralResponse<CategoryResponse>>, response: Response<GeneralResponse<CategoryResponse>>) {
                            arrayList_categories = response.body()?.data?.getCategories()
                            if (arrayList_categories != null) {
                                arrayList_categories?.add(0, CategoryObject("lastUpdate", "最近更新", "", arrayList_categories!![0].thumb))
                                arrayList_categories?.add(0, CategoryObject("randomComic", "随机本子", "", arrayList_categories!![0].thumb))
                                arrayList_categories?.add(0, CategoryObject("", "哔咔排行", "", arrayList_categories!![0].thumb))
                            }
                            PreferenceHelper.setLocalApiDataCategoryList(Comic.getContext(), Gson().toJson(arrayList_categories))
                            mView?.onGetBikaCategorySucc(arrayList_categories)
                        }
                    }
            )
    }

    override fun cancel() {
        super.cancel()
        if (mCall != null) mCall!!.cancel()
    }
}
package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.ViewModel

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qiuchenly.comicparse.BaseImp.BaseViewModel
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
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.RecommentContract
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecommendViewModel(Views: RecommentContract.View?) : BaseViewModel<ResponseBody>() {
    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {


    }

    override fun loadFailure(t: Throwable) {

    }

    private var mView = Views

    fun destory() {
        mView = null
    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        mView?.OnNetFailed()
    }

    private var mCall: Call<ResponseBody>? = null

    private var indexUrl = ""
    fun getIndex() {
        if (!PreferenceHelper.getNoLoginBika(Comic.getContext())) {
            getBikaAllCategory()
        } else {
            mView?.ShowErrorMsg("你还未选择漫画数据提供源!搞快丶去选择!")
            if (PreferenceHelper.getIsFirst(Comic.getContext()))
                mView?.goSelectSource()
            mView?.final()
        }
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
                        getBikaAllCategory()

                    }
                } else {
                    mView?.OnNetFailed()
                    mView?.ShowErrorMsg("bika 图片提供服务器炸了.")
                }
            }

            override fun onFailure(call: Call<GeneralResponse<InitialResponse>>, t: Throwable) {
                t.printStackTrace()
                mView?.OnNetFailed()
                mView?.ShowErrorMsg("网络有点问题.")
            }
        })
    }

    fun initBikaApi() {
        RestWakaClient().apiService.wakaInit.enqueue(object : Callback<WakaInitResponse> {
            override fun onResponse(call: Call<WakaInitResponse>, response: Response<WakaInitResponse>?) {
                if ((response!!.body() as WakaInitResponse).addresses != null && (response.body() as WakaInitResponse).addresses.size > 0) {
                    PreferenceHelper.setDnsIp(Comic.getContext(), HashSet((response.body() as WakaInitResponse).addresses))
                    mView?.ShowErrorMsg("初始化bika CDN缓存成功.")
                    PreferenceHelper.setGirl(Comic.getContext(), true)
                    PreferenceHelper.setChannel(Comic.getContext(), 2)
                    getInit()
                } else {
                    mView?.OnNetFailed()
                    mView?.ShowErrorMsg("无法获取到Bika服务器的CDN地址!请使用VPN后重新加载.")
                }
                mView?.final()
            }

            override fun onFailure(call: Call<WakaInitResponse>, t: Throwable) {
                t.printStackTrace()
                mView?.OnNetFailed()
                mView?.ShowErrorMsg("试图初始化Bika服务器的CDN地址失败!请使用VPN后重新加载.")
            }
        })
    }

    var userName = ""
    var pass = ""
    fun getBikaIndex() {
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
                initBikaApi()
            return
        }
        if (PreferenceHelper.getNoLoginBika(Comic.getContext())) {

        } else
            BikaApi.getAPI()?.getCategories(PreferenceHelper.getToken(Comic.getContext()))?.enqueue(
                    object : Callback<GeneralResponse<CategoryResponse>> {
                        override fun onFailure(call: Call<GeneralResponse<CategoryResponse>>, t: Throwable) {
                            //this.onFailure(call, t)
                            mView?.OnNetFailed()
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
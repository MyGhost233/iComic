package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.ViewModel

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qiuchenly.comicparse.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Http.BikaApi.CategoryObject
import com.qiuchenly.comicparse.Http.BikaApi.DefaultCategoryObject
import com.qiuchenly.comicparse.Http.BikaApi.PreferenceHelper
import com.qiuchenly.comicparse.Http.BikaApi.RestWakaClient
import com.qiuchenly.comicparse.Http.BikaApi.responses.CategoryResponse
import com.qiuchenly.comicparse.Http.BikaApi.responses.GeneralResponse
import com.qiuchenly.comicparse.Http.BikaApi.responses.WakaInitResponse
import com.qiuchenly.comicparse.Http.RetrofitManager
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.RecommentContract
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Request.Requests
import com.qiuchenly.comicparse.Utils.parseComicInfoUtils.getComicInfoByAuto
import com.qiuchenly.comicparse.Utils.parseComicInfoUtils.parseA_Z
import com.qiuchenly.comicparse.Utils.parseComicInfoUtils.parseByNewUpdate
import com.qiuchenly.comicparse.Utils.parseComicInfoUtils.parseOMMH
import com.qiuchenly.comicparse.Utils.parseComicInfoUtils.parseRHMH
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.Charset


class RecommendViewModel(Views: RecommentContract.View?) : BaseViewModel<ResponseBody>() {

    private var mView = Views

    fun destory() {
        mView = null
    }

    override fun GetSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        if (call.getUrl() == indexUrl) {
            val node = Jsoup.parse(String(response.body()?.bytes()!!, Charset.forName("gb2312")))
            val nodeTop = node.getElementById("hotUpdateList")
            val mTopViewComicBook = getComicInfoByAuto(nodeTop)

            val nodeUpdate = node.getElementsByClass("newUpdate")
            val newUpdate = parseByNewUpdate(nodeUpdate[0])


            var ComicSort = node.getElementsByAttributeValue("class", "fl topList")
            //日韩
            val rhmh = parseRHMH(ComicSort[0])

            //欧美
            val ommh = parseRHMH(ComicSort[1])

            //大陆
            val dlmh = parseRHMH(ComicSort[2])

            //日韩好看
            val rhhk = parseRHMH(node.getElementsByAttributeValue("class", "fr outline h450")[0], true)

            val arr = node.getElementsByAttributeValue("class", "fr outline h450 w812")
            //欧美好看
            val omhk = parseOMMH(arr[0])

            //大陆好看
            val dlhk = parseOMMH(arr[1])

            ComicSort = node.getElementsByClass("ichr_list")

            val A_Z = ArrayList<HotComicStrut>()
            for (sort in ComicSort) {
                A_Z.addAll(parseA_Z(sort))
            }
            mView?.GetIndexPageSucc(mTopViewComicBook, newUpdate, rhmh, ommh,
                    dlmh, rhhk, omhk, dlhk, A_Z)
        }
    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        super.onFailure(call, t)
        mView?.OnNetFailed()
    }

    private var mCall: Call<ResponseBody>? = null

    private var indexUrl = ""
    fun getIndex() {
        if (PreferenceHelper.getUseMH1234(Comic.getContext())) {
            mCall = RetrofitManager.get()
                    .create(Requests::class.java)
                    .getIndex()
            indexUrl = mCall!!.getUrl()
            mCall!!.enqueue(this)
        } else if (!PreferenceHelper.getNoLoginBika(Comic.getContext())) {
            getRandomBika()
        } else {
            mView?.ShowErrorMsg("你还未选择漫画数据提供源!搞快丶去选择!")
            if (PreferenceHelper.getIsFirst(Comic.getContext()))
                mView?.goSelectSource()
            mView?.final()
        }
    }

    fun initBikaApi() {
        RestWakaClient().apiService.wakaInit.enqueue(object : Callback<WakaInitResponse> {
            override fun onResponse(call: Call<WakaInitResponse>, response: Response<WakaInitResponse>?) {
                if ((response!!.body() as WakaInitResponse).addresses != null && (response.body() as WakaInitResponse).addresses.size > 0) {
                    PreferenceHelper.setDnsIp(Comic.getContext(), HashSet((response.body() as WakaInitResponse).addresses))
                    mView?.ShowErrorMsg("初始化bika CDN缓存成功.")
                    PreferenceHelper.setGirl(Comic.getContext(), true)
                    PreferenceHelper.setChannel(Comic.getContext(), 2)
                    if (PreferenceHelper.getToken(Comic.getContext()) != "") {
                        mView?.ShowErrorMsg("使用上次登录的Token.")
                        getRandomBika()
                    } else {
                        getBikaIndex()
                    }

                } else {
                    mView?.ShowErrorMsg("无法获取到Bika服务器的CDN地址!请使用VPN后重新加载.")
                }
                mView?.final()
            }

            override fun onFailure(call: Call<WakaInitResponse>, t: Throwable) {
                t.printStackTrace()
                this.onFailure(call, t)
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
            } else getRandomBika()
        }
    }

    var arrayList_categories: ArrayList<CategoryObject>? = null
    var arrayList_defaultCategories: ArrayList<DefaultCategoryObject>? = null
    var arrayList_keywords: ArrayList<String>? = null
    var arrayList_tags: ArrayList<String>? = null
    private val CATEGORY_RANDOM = "CATEGORY_RANDOM"
    private val KEY_LOCAL_API_CATEGORY = "KEY_LOCAL_API_CATEGORY"
    fun getRandomBika() {
        if (PreferenceHelper.getToken(Comic.getContext()) == "") {
            if (PreferenceHelper.getNoLoginBika(Comic.getContext())) {
            } else
                initBikaApi()
            return
        }
        if (PreferenceHelper.getNoLoginBika(Comic.getContext())) {

        } else
            RetrofitManager.getBiCaApi()?.getCategories(PreferenceHelper.getToken(Comic.getContext()))?.enqueue(
                    object : Callback<GeneralResponse<CategoryResponse>> {
                        override fun onFailure(call: Call<GeneralResponse<CategoryResponse>>, t: Throwable) {
                            this.onFailure(call, t)
                        }

                        override fun onResponse(call: Call<GeneralResponse<CategoryResponse>>, response: Response<GeneralResponse<CategoryResponse>>) {
                            arrayList_categories = response.body()?.data?.getCategories()
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
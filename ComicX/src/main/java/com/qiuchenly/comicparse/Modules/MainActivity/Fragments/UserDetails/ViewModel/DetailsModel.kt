package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.ViewModel

import com.qiuchenly.comicparse.Http.BaseURL
import com.qiuchenly.comicparse.Http.RetrofitManager
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.Request.Requests
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.Views.MyDetailsContract
import com.qiuchenly.comicparse.Simple.BaseViewModel
import com.qiuchenly.comicparse.Utils.CustomUtils
import io.realm.Realm
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class DetailsModel(private val mView: MyDetailsContract.View?) : BaseViewModel<ResponseBody>() {

    fun getBingSrc() {
        mCall = RetrofitManager.getCusUrl(BaseUrl = BaseURL.BASE_URL_BING)
                .create(Requests::class.java)
                .getImageSrc()
        mCall!!.enqueue(this)

    }

    override fun GetSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        var str = response.body()?.string()
        if (str != null) {
            str = CustomUtils.subStr(str, "rel=\"preload\" href=\"", "\" as=\"image\"")
        }
        if (str == null) str = ""
        if (str.indexOf(BaseURL.BASE_URL_BING) == -1)
            str = BaseURL.BASE_URL_BING + str
        if (str != CustomUtils.getCachedBingUrl()) {
            mView?.onSrcReady(str)
            CustomUtils.setCachedBingUrl(str)
        }

    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        super.onFailure(call, t)
        mView?.ShowErrorMsg("无法访问Bing美图服务器,使用默认图片替换.")
        mView?.onSrcReady(CustomUtils.getCachedBingUrl())
    }

    private var mCall: Call<ResponseBody>? = null

    fun getLocalBookByDB(): ArrayList<HotComicStrut>? {
        return ArrayList(Realm.getDefaultInstance().where(HotComicStrut::class.java).findAll())
    }
}
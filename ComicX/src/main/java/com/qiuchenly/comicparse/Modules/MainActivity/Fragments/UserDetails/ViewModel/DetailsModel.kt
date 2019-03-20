package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.ViewModel

import com.qiuchenly.comicparse.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Http.BaseURL
import com.qiuchenly.comicparse.Http.BikaApi
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.Request.Requests
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.Views.MyDetailsContract
import com.qiuchenly.comicparse.Utils.CustomUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class DetailsModel(private val mView: MyDetailsContract.View?) : BaseViewModel<ResponseBody>() {
    override fun loadFailure(t: Throwable) {

    }

    fun getBingSrc() {
        mCall = BikaApi.getCusUrl(BaseUrl = BaseURL.BASE_URL_BING)
                .create(Requests::class.java)
                .getImageSrc()
        mCall!!.enqueue(this)
    }

    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        var str = response.body()?.string()
        if (str != null) {
            //fix imageSrc link error
            str = CustomUtils.subStr(str, "rel=\"preload\" href=\"", "&amp;")
        }
        if (str == null) str = ""
        if (str.indexOf(BaseURL.BASE_URL_BING) == -1)
            str = BaseURL.BASE_URL_BING + str
        if (str != CustomUtils.getCachedBingUrl()) {
            CustomUtils.setCachedBingUrl(str)
        }
        mView?.onSrcReady(str)
    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        super.onFailure(call, t)
        mView?.ShowErrorMsg("无法访问Bing美图服务器,使用默认图片替换.")
        mView?.onSrcReady(CustomUtils.getCachedBingUrl())
    }

    private var mCall: Call<ResponseBody>? = null

}
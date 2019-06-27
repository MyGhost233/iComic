package com.qiuchenly.comicparse.UI.model

import com.qiuchenly.comicparse.Bean.RecentlyReadingBean
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.HttpRequests.BingRequests
import com.qiuchenly.comicparse.ProductModules.Bika.BikaApi
import com.qiuchenly.comicparse.ProductModules.Common.BaseURL
import com.qiuchenly.comicparse.UI.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.UI.view.MyDetailsContract
import com.qiuchenly.comicparse.Utils.CustomUtils
import io.realm.Realm
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.lang.ref.WeakReference

class DetailsModel(private val mView: MyDetailsContract.View?) : BaseViewModel<ResponseBody>() {

    private var mRealm: WeakReference<Realm>? = null

    init {
        mRealm = WeakReference(Comic.getRealm())
    }

    override fun loadFailure(t: Throwable) {

    }

    override fun cancel() {
        super.cancel()
        mRealm = null
        if (mCall?.isCanceled == false) {
            mCall?.cancel()
            mCall = null
        }
    }

    fun getBingSrc() {
        mCall = BikaApi.getCusUrl(BaseUrl = BaseURL.BASE_URL_BING)
                .create(BingRequests::class.java)
                .getImageSrc()
        mCall!!.enqueue(this)
    }

    fun getRecentlyReadSize() {
        mView?.setRecentlySize(mRealm?.get()?.where(RecentlyReadingBean::class.java)
                ?.findAll()?.size ?: 0)
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
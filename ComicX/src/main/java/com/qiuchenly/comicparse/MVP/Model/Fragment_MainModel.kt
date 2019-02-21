package com.qiuchenly.comicparse.MVP.Model

import com.qiuchenly.comicparse.MVP.Contract.MainContract
import com.qiuchenly.comicparse.Simple.BaseModelImp
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.Http.BaseRequest
import com.qiuchenly.comicparse.Http.BaseURL
import org.jsoup.Jsoup
import java.util.*

class Fragment_MainModel : BaseModelImp(), MainContract.Model {

    fun getHotsComic(cb: MainContract.GetHotComic) {
        SendRequest(BaseURL.BASE_URL, object : BaseRequest.RequestCallback {
            override fun onSuccess(RetStr: String) {
                val arr = ArrayList<HotComicStrut>()
                val elements = Jsoup.parse(RetStr)
                        .getElementById("icmd_list")
                        .getElementsByTag("ul")
                for (e in elements) {
                    val LIS = e.getElementsByTag("li")
                    //解决第二页面图片采集问题
                    for (a in LIS) {
                        arr.add(getComicInfo(a))
                    }
                }
                cb.onSuccessGetHot(arr)
            }

            override fun onFailed(ReasonStr: String) {
                cb.onFailed(ReasonStr)
            }
        })
    }
}

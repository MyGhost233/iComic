package com.qiuchenly.comicparse.MVP.Model

import com.qiuchenly.comicparse.Bean.HotComicStrut
import com.qiuchenly.comicparse.MVP.Contract.NetRecommentContract
import com.qiuchenly.comicparse.Simple.BaseModelImp
import com.qiuchenly.comicparse.VolleyImp.BaseURL
import org.jsoup.Jsoup

class FragmentRecommendModel : BaseModelImp(), NetRecommentContract.Model {
    override fun GetIndexPage(ret: IndexPageGetter) {
        SendRequest(BaseURL.BASE_URL, object : RequestCallback {
            override fun onSuccess(RetStr: String) {
                    val node = Jsoup.parse(RetStr)
                    val nodeTop = node.getElementById("hotUpdateList")
                    val mTopViewComicBook = getComicInfoByAuto(nodeTop)

                    val nodeUpdate = node.getElementsByClass("newUpdate")
                    val newUpdate = parseByNewUpdate(nodeUpdate[0])

                    println("")
                    ret.mGetResult(200, RetStr, mTopViewComicBook, newUpdate)
            }

            override fun onFailed(ReasonStr: String) {
                ret.mGetResult(-1, ReasonStr, null, null)
            }
        })
    }


    interface IndexPageGetter {
        fun mGetResult(retCode: Int, reasonStr: String, mTopViewComicBook: ArrayList<HotComicStrut>?, newUpdate: ArrayList<HotComicStrut>?)
    }
}
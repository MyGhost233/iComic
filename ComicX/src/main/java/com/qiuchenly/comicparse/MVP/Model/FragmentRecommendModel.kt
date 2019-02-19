package com.qiuchenly.comicparse.MVP.Model

import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.TuiJian.Beans.HotComicStrut
import com.qiuchenly.comicparse.MVP.Contract.NetRecommentContract
import com.qiuchenly.comicparse.Simple.BaseModelImp
import com.qiuchenly.comicparse.Http.BaseURL
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
                ret.mGetResult(200, RetStr, mTopViewComicBook, newUpdate, rhmh, ommh,
                        dlmh, rhhk, omhk, dlhk, A_Z)
            }

            override fun onFailed(ReasonStr: String) {
                ret.mGetResult(-1, ReasonStr, null, null, null, null, null, null, null, null, null)
            }
        })
    }


    interface IndexPageGetter {
        fun mGetResult(retCode: Int,
                       reasonStr: String,
                       mTopViewComicBook: ArrayList<HotComicStrut>?,
                       newUpdate: ArrayList<HotComicStrut>?,
                       rhmh: ArrayList<HotComicStrut>?,
                       ommh: ArrayList<HotComicStrut>?,
                       dlmh: ArrayList<HotComicStrut>?,
                       rhhk: ArrayList<HotComicStrut>?,
                       omhk: ArrayList<HotComicStrut>?,
                       dlhk: ArrayList<HotComicStrut>?,
                       a_Z: ArrayList<HotComicStrut>?)
    }
}
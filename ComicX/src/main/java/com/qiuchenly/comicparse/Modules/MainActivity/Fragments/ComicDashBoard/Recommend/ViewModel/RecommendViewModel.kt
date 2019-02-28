package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.ViewModel

import com.qiuchenly.comicparse.Http.RetrofitManager
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.RecommentContract
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Request.Requests
import com.qiuchenly.comicparse.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Utils.parseComicInfoUtils.getComicInfoByAuto
import com.qiuchenly.comicparse.Utils.parseComicInfoUtils.parseA_Z
import com.qiuchenly.comicparse.Utils.parseComicInfoUtils.parseByNewUpdate
import com.qiuchenly.comicparse.Utils.parseComicInfoUtils.parseOMMH
import com.qiuchenly.comicparse.Utils.parseComicInfoUtils.parseRHMH
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Call
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
        mCall = RetrofitManager.get()
                .create(Requests::class.java)
                .getIndex()
        indexUrl = mCall!!.getUrl()
        mCall!!.enqueue(this)
    }

    override fun cancel() {
        super.cancel()
        if (mCall != null) mCall!!.cancel()
    }
}
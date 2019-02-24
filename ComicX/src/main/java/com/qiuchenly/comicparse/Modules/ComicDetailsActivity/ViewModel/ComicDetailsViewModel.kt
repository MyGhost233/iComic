package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ViewModel

import com.qiuchenly.comicparse.Bean.ComicBookInfo
import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.Http.RetrofitManager
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface.ComicDetailContract
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Request.Requests
import com.qiuchenly.comicparse.Simple.BaseViewModel
import com.qiuchenly.comicparse.Utils.CustomUtils.subStr
import io.realm.Realm
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import retrofit2.Call
import retrofit2.Response
import java.nio.charset.Charset
import java.text.DecimalFormat

class ComicDetailsViewModel(private var callback: ComicDetailContract.View) : BaseViewModel<ResponseBody>() {
    companion object {
        var cachePageList = ArrayList<ComicBookInfo>()
    }

    fun Save2DB(comicInfo: ComicBookInfo_Recently) {
        val realm = Realm.getDefaultInstance()
        if (realm.where(ComicBookInfo_Recently::class.java).equalTo("BookName", comicInfo.BookName)
                        .findFirst() == null) {
            realm.beginTransaction()
            realm.createObject(ComicBookInfo_Recently::class.java, comicInfo.BookName).apply {
                this.BookImgSrc = comicInfo.BookImgSrc
                this.BookLink = comicInfo.BookLink
                this.Author = comicInfo.Author
            }
            realm.commitTransaction()
        }
        realm.close()
    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        super.onFailure(call, t)
        callback.onLoadFailed()
    }

    override fun GetSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        callback.onLoadSuccess()
        val retStr = String(response.body()!!.bytes(), Charset.forName("gb2312"))
        var path = call.request().url().toString()
        if (path == getDetailsURI) {
            val init = Jsoup.parse(retStr)
            val infoClass = init.getElementsByClass("info")[0]
            val tmp = infoClass.getElementsByTag("span")
            val author = infoClass.getElementsByTag("p")[1]
                    .html().split("em>")[2]
            val updateTime = tmp[0]
                    .html()
            val hits = tmp[1]
                    .html()
            val category = infoClass.getElementsByTag("a")[1].html()
            val list = init.getElementById("play_0")
                    .getElementsByTag("a")
            val retPageList = ArrayList<ComicBookInfo>()
            for (e: Element in list) {
                retPageList.add(ComicBookInfo().apply {
                    link = e.attr("href")
                    title = e.attr("title")
                })
            }
            val introduction = init
                    .getElementsByClass("introduction")[0]
                    .getElementsByTag("p")[0].html()
//                initScore:function(){
//                var data = this.data;
//                var t = data.t;
//                var ret = [];
//                var _max = Math.max.apply(Math,data.s);
//                var _len = 46;
//                var _avg = data.t>0?((data.s[0]+2*data.s[1]+3*data.s[2]+4*data.s[3]+5*data.s[4])/t*2).toFixed(1):'0.0';
//                _avg = _avg.substring(0,3);
            cachePageList = retPageList
            callback.GetInfoSucc(author, updateTime, hits, category, introduction, retPageList)
        } else if (path == getBookScoreURI) {
            val ret = retStr.replace("var Scorepl=", "")
            val peopleLimit = Integer.valueOf(subStr(ret, ":", ","))
            val list = subStr(ret, "[", "]").split(",")
            val s = ArrayList<Int>()
            for (a: String in list) {
                s.add(Integer.valueOf(a))
            }
            val range = (s[0] + 2 * s[1] + 3 * s[2] + 4 * s[3] + 5 * s[4]) * 1.0
            val b = range / peopleLimit * 2
            val _avg = if (peopleLimit > 0) DecimalFormat("#.0").format(b) else "0.0"
            callback.getScoreSucc(_avg.toString())
        }
    }

    private var mCall: Call<ResponseBody>? = null

    override fun cancel() {
        super.cancel()
        if (mCall != null) mCall!!.cancel()
    }

    private var getDetailsURI = ""
    fun getBookDetails(bookID: String) {
        callback.onLoading()

        mCall = RetrofitManager
                .get()
                .create(Requests::class.java)
                .getBookInfo(bookID)
        getDetailsURI = mCall!!.request().url().toString()
        mCall!!.enqueue(this)
    }

    private var getBookScoreURI = ""
    fun getBookScore(bookID: String) {
        callback.onLoading()
        mCall = RetrofitManager
                .get()
                .create(Requests::class.java)
                .getBookScore(bookID)
        getBookScoreURI = mCall!!.request().url().toString()
        mCall!!.enqueue(this)
    }
}
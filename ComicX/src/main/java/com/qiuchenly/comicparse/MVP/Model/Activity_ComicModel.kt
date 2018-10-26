package com.qiuchenly.comicparse.MVP.Model

import com.qiuchenly.comicparse.Bean.ComicBookInfo
import com.qiuchenly.comicparse.MVP.Contract.ComicDetailContract
import com.qiuchenly.comicparse.Simple.BaseModelImp
import com.qiuchenly.comicparse.VolleyImp.BaseURL
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.text.DecimalFormat

class Activity_ComicModel : BaseModelImp(), ComicDetailContract.Model {
    companion object {
        var cachePageList = ArrayList<ComicBookInfo>()
    }

    override fun getBookScore(bookID: String, cb: ComicDetailContract.GetScore) {
        val id = subStr(bookID, "comic/", ".html")
        val url = BaseURL.GET_BOOK_SCORE + id
        SendRequest(url, object : RequestCallback {
            override fun onSuccess(RetStr: String) {
                val ret = RetStr.replace("var Scorepl=", "")
                val peopleLimit = Integer.valueOf(subStr(ret, ":", ","))
                val list = subStr(ret, "[", "]").split(",")
                val s = ArrayList<Int>()
                for (a: String in list) {
                    s.add(Integer.valueOf(a))
                }
                val range = (s[0] + 2 * s[1] + 3 * s[2] + 4 * s[3] + 5 * s[4]) * 1.0
                val b = range / peopleLimit * 2
                val _avg = if (peopleLimit > 0) DecimalFormat("#.0").format(b) else 0.0
                cb.getScoreSucc(_avg.toString())
            }

            override fun onFailed(ReasonStr: String) {
                cb.onFailed(ReasonStr)
            }
        })
    }

    override fun InitPageInfo(page: String, cb: ComicDetailContract.GetPageInfo) {
        SendRequest(page, object : RequestCallback {
            override fun onSuccess(RetStr: String) {
                val init = Jsoup.parse(RetStr)
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
                cb.onSuccessGetInfo(author, updateTime, hits, category, introduction, retPageList)
            }

            override fun onFailed(ReasonStr: String) {
                if (ReasonStr != null) {
                    cb.onFailed(ReasonStr)
                }
            }
        })
    }

}
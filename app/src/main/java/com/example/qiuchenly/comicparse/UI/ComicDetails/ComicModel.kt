package com.example.qiuchenly.comicparse.UI.ComicDetails

import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import com.example.qiuchenly.comicparse.Simple.BaseModelImp
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class ComicModel : BaseModelImp(), ComicDetailContract.Model {
    companion object {
        var cachePageList = ArrayList<ComicBookInfo>()
    }

    fun InitPageInfo(page: String, cb: ComicDetailContract.GetPageInfo) {
        SendRequest(page, object : RequestCallback {
            override fun onSuccess(RetStr: String?) {
                val init = Jsoup.parse(RetStr)
                val infoClass = init.getElementsByClass("info")[0]
                var tmp = infoClass.getElementsByTag("span")
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

            override fun onFailed(ReasonStr: String?) {
                if (ReasonStr != null) {
                    cb.onFailed(ReasonStr)
                }
            }
        })
    }

}
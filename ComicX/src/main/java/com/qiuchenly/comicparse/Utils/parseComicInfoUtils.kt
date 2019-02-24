package com.qiuchenly.comicparse.Utils

import com.qiuchenly.comicparse.Http.BaseURL
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Beans.HotComicStrut
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

object parseComicInfoUtils {

    /**
     * 修复前缀网址高级方法
     * @param infoEle 原网页对象
     * @return 修复完成前缀的对象返回
     */
    fun getComicInfoEx(infoEle: Element): HotComicStrut {
        return getComicInfo(infoEle).apply {
            this.BookImgSrc = BaseURL.BASE_URL + this.BookImgSrc
            this.BookLink = BaseURL.BASE_URL + this.BookLink
            this.LastedPage_src = BaseURL.BASE_URL + this.LastedPage_src
        }
    }

    fun getComicInfoEx(comic: HotComicStrut): HotComicStrut {
        return comic.apply {
            this.BookImgSrc = BaseURL.BASE_URL + this.BookImgSrc
            this.BookLink = BaseURL.BASE_URL + this.BookLink
            this.LastedPage_src = BaseURL.BASE_URL + this.LastedPage_src
        }
    }

    fun parseByNewUpdate(element: Element): ArrayList<HotComicStrut> {
        //        <li><span class="red"><font color="#ff0000">2018-08-24</font></span><a href="/comic/13016.html" target="_blank" title="与神兽同居的日子" class="video" i="/upload/2018-02-09/20182916575152.jpg">与神兽同居的日子</a>[<a class="red" href="/comic/13016/386246.html" target="_blank" title="第32话 火拼上">第32话 火拼上</a>]</li>
        val lis = element.getElementsByTag("li")
        val arr = ArrayList<HotComicStrut>()
        for (li in lis) {
            val obj = li.getElementsByTag("a")
            arr.add(getComicInfoEx(HotComicStrut().apply {
                this.BookImgSrc = obj[0].attr("i")
                this.BookLink = obj[0].attr("href")
                this.BookName = obj[0].attr("title")
                this.LastedPage_name = obj[1].attr("title")
                this.LastedPage_src = obj[1].attr("href")
            }))
        }
        return arr
    }

    fun parseRHMH(element: Element, hasUpdate: Boolean = false): ArrayList<HotComicStrut> {
        val arr = ArrayList<HotComicStrut>()
        for (li in getAllElement(element)) {
            val obj = li.getElementsByTag("a")
            arr.add(getComicInfoEx(HotComicStrut().apply {
                this.BookImgSrc = obj[0].attr("i")
                this.BookLink = obj[0].attr("href")
                this.BookName = obj[0].text()
                if (hasUpdate) {
                    this.LastedPage_name = li.getElementsByTag("em").text()
                }
            }))
        }
        return arr
    }

    fun getAllElement(element: Element): Elements {
        return element.getElementsByTag("li")
    }

    fun parseOMMH(element: Element): ArrayList<HotComicStrut> {
        val arr = ArrayList<HotComicStrut>()
        for (li in getAllElement(element)) {
            val obj = li.getElementsByTag("a")
            arr.add(getComicInfoEx(HotComicStrut().apply {
                this.BookImgSrc = obj[0].getElementsByTag("img")[0].attr("src")
                this.BookLink = obj[0].attr("href")
                this.BookName = obj[0].getElementsByTag("img")[0].attr("alt")
            }))
        }
        return arr
    }

    fun parseA_Z(element: Element): ArrayList<HotComicStrut> {
        val arr = ArrayList<HotComicStrut>()
        for (li in getAllElement(element)) {
            val obj = li.getElementsByTag("a")
            arr.add(getComicInfoEx(HotComicStrut().apply {
                this.BookImgSrc = obj[0].attr("i")
                this.BookLink = obj[0].attr("href")
                this.BookName = obj[0].text()
                this.LastedPage_name = li.getElementsByTag("em").text()
            }))
        }
        arr.forEach {
            it.Tag = element.getElementsByTag("h5").text()
        }
        return arr
    }

    fun getComicInfoByAuto(element: Element): ArrayList<HotComicStrut> {
        val list = element.getElementsByTag("li")
        val mTopViewComicBook = ArrayList<HotComicStrut>()
        for (a in list) {
            mTopViewComicBook.add(getComicInfoEx(a))
        }
        return mTopViewComicBook
    }

    fun getComicInfo(infoEle: Element): HotComicStrut {
        val book = HotComicStrut()
        var tmp = infoEle.getElementsByTag("img")[0]
        val tag: String
        tag = if (tmp.attr("_src").isNotEmpty())
            "_src"
        else
            "src"
        book.BookImgSrc = tmp.attr(tag)
        book.BookName = tmp.attr("alt")
        val tmps = infoEle.getElementsByTag("a")
        tmp = tmps[1]
        book.LastedPage_name = tmp.html()
        book.LastedPage_src = tmp.attr("href")
        tmp = tmps[2]
        book.BookLink = tmp.attr("href")
        return book
    }
}

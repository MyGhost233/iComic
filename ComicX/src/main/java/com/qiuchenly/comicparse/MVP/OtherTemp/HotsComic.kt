package com.qiuchenly.comicparse.MVP.OtherTemp

import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.Utils.parseComicInfoUtils.getComicInfo
import org.jsoup.Jsoup
import java.util.*

class HotsComic : MainContract.Model {
    fun getHotsComic(cb: MainContract.GetHotComic) {
        val arr = ArrayList<HotComicStrut>()
        val elements = Jsoup.parse("")
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
}

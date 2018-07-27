package com.example.qiuchenly.comicparse.UI.Main;

import com.example.qiuchenly.comicparse.Bean.HotComicStrut;
import com.example.qiuchenly.comicparse.Simple.BaseModelImp;
import com.example.qiuchenly.comicparse.VolleyImp.BaseURL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainModel extends BaseModelImp implements MainContract.Model {

    public void getHotsComic(final MainContract.GetHotComic cb) {
        SendRequest(BaseURL.BASE_URL + "", new RequestCallback() {
            @Override
            public void onSuccess(String RetStr) {
                ArrayList<HotComicStrut> arr = new ArrayList<HotComicStrut>();
                Elements elements = Jsoup.parse(RetStr).getElementById("icmd_list").getElementsByTag("ul");
                boolean isSec=false;
                for (Element e : elements) {
                    Elements LIS = e.getElementsByTag("li");
                    //解决第二页面图片采集问题
                    for (Element a : LIS) {
                        HotComicStrut strut = new HotComicStrut();
                        Element tmp = a.getElementsByTag("img").get(0);
                        String tag ;
                        if(isSec) tag="_src"; else tag="src";
                        strut.bookImgSrc = tmp.attr(tag);
                        strut.bookName = tmp.attr("alt");
                        Elements tmps = a.getElementsByTag("a");
                        tmp = tmps.get(1);
                        strut.lastedPage_name = tmp.html();
                        strut.lastedPage_src  = tmp.attr("href");
                        tmp = tmps.get(2);
                        strut.bookLink = tmp.attr("href");
                        arr.add(strut);
                    }
                    isSec =true;
                }
                cb.onSuccessGetHot(arr);
            }

            @Override
            public void onFailed(String ReasonStr) {
                cb.onFailed(ReasonStr);
            }
        });
    }



}

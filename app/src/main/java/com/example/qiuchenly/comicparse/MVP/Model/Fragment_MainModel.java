package com.example.qiuchenly.comicparse.MVP.Model;

import com.example.qiuchenly.comicparse.Bean.HotComicStrut;
import com.example.qiuchenly.comicparse.MVP.Contract.MainContract;
import com.example.qiuchenly.comicparse.Simple.BaseModelImp;
import com.example.qiuchenly.comicparse.VolleyImp.BaseURL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Fragment_MainModel extends BaseModelImp implements MainContract.Model {

    public void getHotsComic(final MainContract.GetHotComic cb) {
        SendRequest(BaseURL.BASE_URL, new RequestCallback() {
            @Override
            public void onSuccess(String RetStr) {
                ArrayList<HotComicStrut> arr = new ArrayList<HotComicStrut>();
                Elements elements = Jsoup.parse(RetStr)
                        .getElementById("icmd_list")
                        .getElementsByTag("ul");
                for (Element e : elements) {
                    Elements LIS = e.getElementsByTag("li");
                    //解决第二页面图片采集问题
                    for (Element a : LIS) {
                        arr.add(getComicInfo(a));
                    }
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

package com.example.qiuchenly.comicparse.Simple;

import com.example.qiuchenly.comicparse.Bean.HotComicStrut;
import com.example.qiuchenly.comicparse.VolleyImp.BaseRequest;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BaseModelImp extends BaseRequest implements BaseModel {

    /**
     * 取文本中间
     *
     * @param all   欲取文本中间的全文本
     * @param left  欲取文本的左边文本
     * @param right 欲取文本的右边文本
     * @return 返回得到的文本
     */
    protected String subStr(String all, String left, String right) {
        int sta = all.indexOf(left) + left.length();
        return all.substring(sta, all.indexOf(right, sta));
    }

    protected HotComicStrut getComicInfo(Element infoEle) {
        HotComicStrut book = new HotComicStrut();
        Element tmp = infoEle.getElementsByTag("img").get(0);
        String tag;
        if (tmp.attr("_src").length() > 0) tag = "_src";
        else tag = "src";
        book.bookImgSrc = tmp.attr(tag);
        book.bookName = tmp.attr("alt");
        Elements tmps = infoEle.getElementsByTag("a");
        tmp = tmps.get(1);
        book.lastedPage_name = tmp.html();
        book.lastedPage_src = tmp.attr("href");
        tmp = tmps.get(2);
        book.bookLink = tmp.attr("href");
        return book;
    }
}

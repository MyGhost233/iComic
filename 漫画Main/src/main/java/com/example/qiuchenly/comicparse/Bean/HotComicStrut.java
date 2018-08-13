package com.example.qiuchenly.comicparse.Bean;

public class HotComicStrut {
    public String bookName;
    public String bookImgSrc;
    public String lastedPage_name;
    public String lastedPage_src;
    public String bookLink;
    public String author;

    @Override
    public String toString() {
        return bookName + "|" + bookImgSrc + "|" + lastedPage_name + "|" + lastedPage_src + "|" + bookLink;
    }
}
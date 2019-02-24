package com.qiuchenly.comicparse.Http.BikaApi.responses;

import com.qiuchenly.comicparse.Http.BikaApi.ComicDetailObject;

public class ComicDetailResponse {
    ComicDetailObject comic;

    public ComicDetailResponse(ComicDetailObject comic) {
        this.comic = comic;
    }

    public ComicDetailObject getComic() {
        return this.comic;
    }

    public void setComic(ComicDetailObject comic) {
        this.comic = comic;
    }

    public String toString() {
        return "ComicDetailResponse{comic=" + this.comic + '}';
    }
}

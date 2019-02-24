package com.qiuchenly.comicparse.Http.BikaApi.databaseTable;

public class DbComicEpisodeIndexObject {
    String comicId;
    String episodeId;
    int limit;
    int page;
    int pages;
    int total;

    public DbComicEpisodeIndexObject(String comicId, String episodeId, int total, int limit, int page, int pages) {
        this.comicId = comicId;
        this.episodeId = episodeId;
        this.total = total;
        this.limit = limit;
        this.page = page;
        this.pages = pages;
    }

    public DbComicEpisodeIndexObject(DbComicEpisodeIndexObject dbComicEpisodeIndexObject) {
        this.comicId = dbComicEpisodeIndexObject.getComicId();
        this.episodeId = dbComicEpisodeIndexObject.getEpisodeId();
        this.total = dbComicEpisodeIndexObject.getTotal();
        this.limit = dbComicEpisodeIndexObject.getLimit();
        this.page = dbComicEpisodeIndexObject.getPage();
        this.pages = dbComicEpisodeIndexObject.getPages();
    }

    public void updateDbComicEpisodeIndexObject(DbComicEpisodeIndexObject dbComicEpisodeIndexObject) {
        this.comicId = dbComicEpisodeIndexObject.getComicId();
        this.episodeId = dbComicEpisodeIndexObject.getEpisodeId();
        this.total = dbComicEpisodeIndexObject.getTotal();
        this.limit = dbComicEpisodeIndexObject.getLimit();
        this.page = dbComicEpisodeIndexObject.getPage();
        this.pages = dbComicEpisodeIndexObject.getPages();
    }

    public String getComicId() {
        return this.comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public String toString() {
        return "DbComicEpisodeIndexObject{comicId='" + this.comicId + '\'' + ", episodeId='" + this.episodeId + '\'' + ", total=" + this.total + ", limit=" + this.limit + ", page=" + this.page + ", pages=" + this.pages + '}';
    }

    public String getEpisodeId() {
        return this.episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return this.pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}

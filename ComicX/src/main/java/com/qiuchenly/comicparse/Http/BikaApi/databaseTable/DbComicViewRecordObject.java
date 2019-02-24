package com.qiuchenly.comicparse.Http.BikaApi.databaseTable;

import com.orm.SugarRecord;

public class DbComicViewRecordObject extends SugarRecord {
    String comicId;
    int episodeOrder;
    String episodeTitle;
    int episodeTotal;
    long lastViewTimestamp;
    int page;

    public DbComicViewRecordObject(String comicId, int page, String episodeTitle, int episodeOrder, int episodeTotal, long lastViewTimestamp) {
        this.comicId = comicId;
        this.page = page;
        this.episodeTitle = episodeTitle;
        this.episodeOrder = episodeOrder;
        this.episodeTotal = episodeTotal;
        this.lastViewTimestamp = lastViewTimestamp;
    }

    public void updateDbComicViewRecordObject(DbComicViewRecordObject dbComicViewRecordObject) {
        this.comicId = dbComicViewRecordObject.getComicId();
        this.page = dbComicViewRecordObject.getPage();
        this.episodeTitle = dbComicViewRecordObject.getEpisodeTitle();
        this.episodeOrder = dbComicViewRecordObject.getEpisodeOrder();
        this.episodeTotal = dbComicViewRecordObject.getEpisodeTotal();
        this.lastViewTimestamp = dbComicViewRecordObject.getLastViewTimestamp();
    }

    public String getComicId() {
        return this.comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getEpisodeTitle() {
        return this.episodeTitle;
    }

    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }

    public int getEpisodeOrder() {
        return this.episodeOrder;
    }

    public void setEpisodeOrder(int episodeOrder) {
        this.episodeOrder = episodeOrder;
    }

    public long getLastViewTimestamp() {
        return this.lastViewTimestamp;
    }

    public void setLastViewTimestamp(long lastViewTimestamp) {
        this.lastViewTimestamp = lastViewTimestamp;
    }

    public int getEpisodeTotal() {
        return this.episodeTotal;
    }

    public void setEpisodeTotal(int episodeTotal) {
        this.episodeTotal = episodeTotal;
    }

    public String toString() {
        return "DbComicViewRecordObject{comicId='" + this.comicId + '\'' + ", page=" + this.page + ", episodeTitle=" + this.episodeTitle + ", episodeOrder=" + this.episodeOrder + ", episodeTotal=" + this.episodeTotal + ", lastViewTimestamp=" + this.lastViewTimestamp + '}';
    }
}

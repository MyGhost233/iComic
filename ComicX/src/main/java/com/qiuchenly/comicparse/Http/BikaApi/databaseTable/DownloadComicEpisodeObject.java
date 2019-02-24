package com.qiuchenly.comicparse.Http.BikaApi.databaseTable;

import com.orm.SugarRecord;
import com.qiuchenly.comicparse.Http.BikaApi.ComicEpisodeObject;

public class DownloadComicEpisodeObject extends SugarRecord {
    String comicId;
    String episodeId;
    int episodeOrder;
    int status;
    String title;
    int total;
    String updatedAt;

    public DownloadComicEpisodeObject(String comicId, ComicEpisodeObject comicEpisodeObject) {
        this.comicId = comicId;
        this.episodeId = comicEpisodeObject.getEpisodeId();
        this.title = comicEpisodeObject.getTitle();
        this.episodeOrder = comicEpisodeObject.getOrder();
        this.updatedAt = comicEpisodeObject.getUpdatedAt();
        this.status = 0;
    }

    public DownloadComicEpisodeObject(String comicId, ComicEpisodeObject comicEpisodeObject, int status) {
        this.comicId = comicId;
        this.episodeId = comicEpisodeObject.getEpisodeId();
        this.title = comicEpisodeObject.getTitle();
        this.episodeOrder = comicEpisodeObject.getOrder();
        this.updatedAt = comicEpisodeObject.getUpdatedAt();
        this.status = status;
    }

    public ComicEpisodeObject getComicEpisodeObject() {
        return new ComicEpisodeObject(this.episodeId, this.title, this.episodeOrder, this.updatedAt);
    }

    public String getComicId() {
        return this.comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public String getEpisodeId() {
        return this.episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEpisodeOrder() {
        return this.episodeOrder;
    }

    public void setEpisodeOrder(int episodeOrder) {
        this.episodeOrder = episodeOrder;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String toString() {
        return "DownloadComicEpisodeObject{episodeId='" + this.episodeId + '\'' + ", comicId='" + this.comicId + '\'' + ", title='" + this.title + '\'' + ", episodeOrder=" + this.episodeOrder + ", total=" + this.total + ", updatedAt='" + this.updatedAt + '\'' + ", status=" + this.status + '}';
    }
}

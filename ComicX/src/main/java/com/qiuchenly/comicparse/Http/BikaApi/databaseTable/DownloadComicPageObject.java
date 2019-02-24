package com.qiuchenly.comicparse.Http.BikaApi.databaseTable;

import com.orm.SugarRecord;
import com.qiuchenly.comicparse.Http.BikaApi.ComicPageObject;
import com.qiuchenly.comicparse.Http.BikaApi.ThumbnailObject;

public class DownloadComicPageObject extends SugarRecord {
    String comicId;
    String comicPageId;
    String episodeId;
    String mediaFileServer;
    String mediaOriginalName;
    String mediaPath;
    String storageFolder;

    public DownloadComicPageObject(String comicId, String episodeId, String storageFolder, ComicPageObject comicPageObject) {
        this.comicId = comicId;
        this.episodeId = episodeId;
        this.comicPageId = comicPageObject.getComicPageId();
        this.mediaFileServer = comicPageObject.getMedia().getFileServer();
        this.mediaOriginalName = comicPageObject.getMedia().getOriginalName();
        this.mediaPath = comicPageObject.getMedia().getPath();
        this.storageFolder = storageFolder;
    }

    public void setMedia(ThumbnailObject media) {
        if (media != null) {
            this.mediaFileServer = media.getFileServer();
            this.mediaOriginalName = media.getOriginalName();
            this.mediaPath = media.getPath();
        }
    }

    public ThumbnailObject getMedia() {
        return new ThumbnailObject(this.mediaFileServer, this.mediaPath, this.mediaOriginalName);
    }

    public ComicPageObject getComicPageObject() {
        return new ComicPageObject(this.comicPageId, new ThumbnailObject(this.mediaFileServer, this.mediaPath, this.mediaOriginalName));
    }

    public void updateWithDownloadComicPageObject(DownloadComicPageObject downloadComicPageObject) {
        this.comicId = downloadComicPageObject.getComicId();
        this.episodeId = downloadComicPageObject.getEpisodeId();
        this.comicPageId = downloadComicPageObject.getComicPageId();
        this.mediaFileServer = downloadComicPageObject.getMediaFileServer();
        this.mediaOriginalName = downloadComicPageObject.getMediaOriginalName();
        this.mediaPath = downloadComicPageObject.getMediaPath();
        this.storageFolder = downloadComicPageObject.getStorageFolder();
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

    public String getComicPageId() {
        return this.comicPageId;
    }

    public void setComicPageId(String comicPageId) {
        this.comicPageId = comicPageId;
    }

    public String getMediaFileServer() {
        return this.mediaFileServer;
    }

    public void setMediaFileServer(String mediaFileServer) {
        this.mediaFileServer = mediaFileServer;
    }

    public String getMediaOriginalName() {
        return this.mediaOriginalName;
    }

    public void setMediaOriginalName(String mediaOriginalName) {
        this.mediaOriginalName = mediaOriginalName;
    }

    public String getMediaPath() {
        return this.mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public String getStorageFolder() {
        return this.storageFolder;
    }

    public void setStorageFolder(String storageFolder) {
        this.storageFolder = storageFolder;
    }

    public String toString() {
        return "DownloadComicPageObject{comicId='" + this.comicId + '\'' + ", episodeId='" + this.episodeId + '\'' + ", comicPageId='" + this.comicPageId + '\'' + ", mediaFileServer='" + this.mediaFileServer + '\'' + ", mediaOriginalName='" + this.mediaOriginalName + '\'' + ", mediaPath='" + this.mediaPath + '\'' + ", storageFolder='" + this.storageFolder + '\'' + '}';
    }
}

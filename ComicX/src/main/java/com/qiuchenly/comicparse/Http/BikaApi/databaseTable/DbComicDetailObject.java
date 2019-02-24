package com.qiuchenly.comicparse.Http.BikaApi.databaseTable;

import com.google.gson.Gson;
import com.orm.SugarRecord;
import com.qiuchenly.comicparse.Http.BikaApi.ComicDetailObject;
import com.qiuchenly.comicparse.Http.BikaApi.CreatorObject;
import com.qiuchenly.comicparse.Http.BikaApi.ThumbnailObject;

import java.util.ArrayList;

public class DbComicDetailObject extends SugarRecord {
    String author;
    String categories;
    String chineseTeam;
    String comicId;
    int commentsCount;
    String createdAt;
    String creatorAvatarFileServer;
    String creatorAvatarOriginalName;
    String creatorAvatarPath;
    String creatorGender;
    String creatorId;
    String creatorName;
    String description;
    int downloadStatus;
    long downloadedAt;
    int episodeCount;
    boolean finished;
    boolean isFavourite;
    boolean isLiked;
    long lastViewTimestamp;
    int likesCount;
    int pagesCount;
    String tags;
    String thumbFileServer;
    String thumbOriginalName;
    String thumbPath;
    String title;
    String updatedAt;
    int viewsCount;

    public DbComicDetailObject(String comicId, String title, String author, String description, String chineseTeam, String creatorId, String creatorName, String creatorGender, String creatorAvatarFileServer, String creatorAvatarPath, String creatorAvatarOriginalName, String thumbFileServer, String thumbPath, String thumbOriginalName, ArrayList<String> categories, ArrayList<String> tags, int commentsCount, int pagesCount, int episodeCount, int likesCount, int viewsCount, boolean finished, boolean isFavourite, boolean isLiked, String updatedAt, String createdAt, long lastViewTimestamp) {
        this.comicId = comicId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.chineseTeam = chineseTeam;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.creatorGender = creatorGender;
        this.creatorAvatarFileServer = creatorAvatarFileServer;
        this.creatorAvatarPath = creatorAvatarPath;
        this.creatorAvatarOriginalName = creatorAvatarOriginalName;
        this.thumbFileServer = thumbFileServer;
        this.thumbPath = thumbPath;
        this.thumbOriginalName = thumbOriginalName;
        Gson gson = new Gson();
        this.categories = gson.toJson((Object) categories);
        this.tags = gson.toJson((Object) tags);
        this.commentsCount = commentsCount;
        this.pagesCount = pagesCount;
        this.episodeCount = episodeCount;
        this.likesCount = likesCount;
        this.viewsCount = viewsCount;
        this.finished = finished;
        this.isFavourite = isFavourite;
        this.isLiked = isLiked;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.lastViewTimestamp = lastViewTimestamp;
    }

    public DbComicDetailObject(ComicDetailObject comicDetailObject) {
        this.comicId = comicDetailObject.getComicId();
        this.title = comicDetailObject.getTitle();
        this.author = comicDetailObject.getAuthor();
        this.description = comicDetailObject.getDescription();
        this.chineseTeam = comicDetailObject.getChineseTeam();
        if (comicDetailObject.getCreator() != null) {
            this.creatorId = comicDetailObject.getCreator().getCreatorId();
            this.creatorName = comicDetailObject.getCreator().getName();
            this.creatorGender = comicDetailObject.getCreator().getGender();
            if (comicDetailObject.getCreator().getAvatar() != null) {
                this.creatorAvatarFileServer = comicDetailObject.getCreator().getAvatar().getFileServer();
                this.creatorAvatarPath = comicDetailObject.getCreator().getAvatar().getPath();
                this.creatorAvatarOriginalName = comicDetailObject.getCreator().getAvatar().getOriginalName();
            }
        }
        if (comicDetailObject.getThumb() != null) {
            this.thumbFileServer = comicDetailObject.getThumb().getFileServer();
            this.thumbPath = comicDetailObject.getThumb().getPath();
            this.thumbOriginalName = comicDetailObject.getThumb().getOriginalName();
        }
        Gson gson = new Gson();
        this.categories = gson.toJson(comicDetailObject.getCategories());
        this.tags = gson.toJson(comicDetailObject.getTags());
        this.commentsCount = comicDetailObject.getCommentsCount();
        this.pagesCount = comicDetailObject.getPagesCount();
        this.episodeCount = comicDetailObject.getEpisodeCount();
        this.likesCount = comicDetailObject.getLikesCount();
        this.viewsCount = comicDetailObject.getViewsCount();
        this.finished = comicDetailObject.isFinished();
        this.isFavourite = comicDetailObject.isFavourite();
        this.isLiked = comicDetailObject.isLiked();
        this.updatedAt = comicDetailObject.getUpdatedAt();
        this.createdAt = comicDetailObject.getCreatedAt();
        this.lastViewTimestamp = System.currentTimeMillis();
    }

    public void updateDbComicDetailObject(DbComicDetailObject dbComicDetailObject) {
        this.comicId = dbComicDetailObject.getComicId();
        this.title = dbComicDetailObject.getTitle();
        this.author = dbComicDetailObject.getAuthor();
        this.description = dbComicDetailObject.getDescription();
        this.chineseTeam = dbComicDetailObject.getChineseTeam();
        this.creatorId = dbComicDetailObject.getCreatorId();
        this.creatorName = dbComicDetailObject.getCreatorName();
        this.creatorGender = dbComicDetailObject.getCreatorGender();
        this.creatorAvatarFileServer = dbComicDetailObject.getCreatorAvatarFileServer();
        this.creatorAvatarPath = dbComicDetailObject.getCreatorAvatarPath();
        this.creatorAvatarOriginalName = dbComicDetailObject.getCreatorAvatarOriginalName();
        this.thumbFileServer = dbComicDetailObject.getThumbFileServer();
        this.thumbPath = dbComicDetailObject.getThumbPath();
        this.thumbOriginalName = dbComicDetailObject.getThumbOriginalName();
        this.categories = dbComicDetailObject.getCategories();
        this.tags = dbComicDetailObject.getTags();
        this.commentsCount = dbComicDetailObject.getCommentsCount();
        this.pagesCount = dbComicDetailObject.getPagesCount();
        this.episodeCount = dbComicDetailObject.getEpisodeCount();
        this.likesCount = dbComicDetailObject.getLikesCount();
        this.viewsCount = dbComicDetailObject.getViewsCount();
        this.finished = dbComicDetailObject.isFinished();
        this.isFavourite = dbComicDetailObject.isFavourite();
        this.isLiked = dbComicDetailObject.isLiked();
        this.updatedAt = dbComicDetailObject.getUpdatedAt();
        this.createdAt = dbComicDetailObject.getCreatedAt();
        this.lastViewTimestamp = dbComicDetailObject.getLastViewTimestamp();
    }

    public ComicDetailObject getComicDetailObject() {
        Gson gson = new Gson();
        return new ComicDetailObject(this.comicId, this.title, this.author, this.description, this.chineseTeam, getCreator(), getThumb(), (ArrayList) gson.fromJson(this.categories, ArrayList.class), (ArrayList) gson.fromJson(this.tags, ArrayList.class), this.commentsCount, this.pagesCount, this.episodeCount, this.likesCount, this.viewsCount, this.finished, this.isFavourite, this.isLiked, this.updatedAt, this.createdAt);
    }

    public ThumbnailObject getThumb() {
        return new ThumbnailObject(this.thumbFileServer, this.thumbPath, this.thumbOriginalName);
    }

    public void setThumb(ThumbnailObject thumb) {
        this.thumbFileServer = thumb.getFileServer();
        this.thumbPath = thumb.getPath();
        this.thumbOriginalName = thumb.getOriginalName();
    }

    public CreatorObject getCreator() {
        return new CreatorObject(this.creatorId, this.creatorName, this.creatorGender, new ThumbnailObject(this.creatorAvatarFileServer, this.creatorAvatarPath, this.creatorAvatarOriginalName));
    }

    public void setCreator(CreatorObject creator) {
        this.creatorId = creator.getCreatorId();
        this.creatorName = creator.getName();
        this.creatorGender = creator.getName();
        if (creator.getAvatar() != null) {
            this.creatorAvatarFileServer = creator.getAvatar().getFileServer();
            this.creatorAvatarPath = creator.getAvatar().getPath();
            this.creatorAvatarOriginalName = creator.getAvatar().getOriginalName();
        }
    }

    public int getDownloadStatus() {
        return this.downloadStatus;
    }

    public void setDownloadStatus(int downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public String getComicId() {
        return this.comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChineseTeam() {
        return this.chineseTeam;
    }

    public void setChineseTeam(String chineseTeam) {
        this.chineseTeam = chineseTeam;
    }

    public String getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return this.creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorGender() {
        return this.creatorGender;
    }

    public void setCreatorGender(String creatorGender) {
        this.creatorGender = creatorGender;
    }

    public String getCreatorAvatarFileServer() {
        return this.creatorAvatarFileServer;
    }

    public void setCreatorAvatarFileServer(String creatorAvatarFileServer) {
        this.creatorAvatarFileServer = creatorAvatarFileServer;
    }

    public String getCreatorAvatarPath() {
        return this.creatorAvatarPath;
    }

    public void setCreatorAvatarPath(String creatorAvatarPath) {
        this.creatorAvatarPath = creatorAvatarPath;
    }

    public String getCreatorAvatarOriginalName() {
        return this.creatorAvatarOriginalName;
    }

    public void setCreatorAvatarOriginalName(String creatorAvatarOriginalName) {
        this.creatorAvatarOriginalName = creatorAvatarOriginalName;
    }

    public String getThumbFileServer() {
        return this.thumbFileServer;
    }

    public void setThumbFileServer(String thumbFileServer) {
        this.thumbFileServer = thumbFileServer;
    }

    public String getThumbPath() {
        return this.thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getThumbOriginalName() {
        return this.thumbOriginalName;
    }

    public void setThumbOriginalName(String thumbOriginalName) {
        this.thumbOriginalName = thumbOriginalName;
    }

    public String getCategories() {
        return this.categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getCommentsCount() {
        return this.commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getPagesCount() {
        return this.pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public int getEpisodeCount() {
        return this.episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public int getLikesCount() {
        return this.likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getViewsCount() {
        return this.viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFavourite() {
        return this.isFavourite;
    }

    public void setFavourite(boolean favourite) {
        this.isFavourite = favourite;
    }

    public boolean isLiked() {
        return this.isLiked;
    }

    public void setLiked(boolean liked) {
        this.isLiked = liked;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getLastViewTimestamp() {
        return this.lastViewTimestamp;
    }

    public void setLastViewTimestamp(long lastViewTimestamp) {
        this.lastViewTimestamp = lastViewTimestamp;
    }

    public long getDownloadedAt() {
        return this.downloadedAt;
    }

    public void setDownloadedAt(long downloadedAt) {
        this.downloadedAt = downloadedAt;
    }

    public String toString() {
        return "DbComicDetailObject{comicId='" + this.comicId + '\'' + ", title='" + this.title + '\'' + ", author='" + this.author + '\'' + ", description='" + this.description + '\'' + ", chineseTeam='" + this.chineseTeam + '\'' + ", creatorId='" + this.creatorId + '\'' + ", creatorName='" + this.creatorName + '\'' + ", creatorGender='" + this.creatorGender + '\'' + ", creatorAvatarFileServer='" + this.creatorAvatarFileServer + '\'' + ", creatorAvatarPath='" + this.creatorAvatarPath + '\'' + ", creatorAvatarOriginalName='" + this.creatorAvatarOriginalName + '\'' + ", thumbFileServer='" + this.thumbFileServer + '\'' + ", thumbPath='" + this.thumbPath + '\'' + ", thumbOriginalName='" + this.thumbOriginalName + '\'' + ", categories='" + this.categories + '\'' + ", tags='" + this.tags + '\'' + ", commentsCount='" + this.commentsCount + '\'' + ", pagesCount='" + this.pagesCount + '\'' + ", episodeCount='" + this.episodeCount + '\'' + ", likesCount='" + this.likesCount + '\'' + ", viewsCount='" + this.viewsCount + '\'' + ", finished='" + this.finished + '\'' + ", isFavourite='" + this.isFavourite + '\'' + ", isLiked='" + this.isLiked + '\'' + ", updatedAt='" + this.updatedAt + '\'' + ", createdAt='" + this.createdAt + '\'' + ", lastViewTimestamp='" + this.lastViewTimestamp + '\'' + ", downloadStatus='" + this.downloadStatus + '\'' + ", downloadedAt='" + this.downloadedAt + '\'' + '}';
    }
}

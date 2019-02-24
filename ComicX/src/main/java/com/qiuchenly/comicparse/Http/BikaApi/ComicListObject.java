package com.qiuchenly.comicparse.Http.BikaApi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.qiuchenly.comicparse.Http.BikaApi.databaseTable.DbComicDetailObject;

import java.util.ArrayList;

public class ComicListObject implements Parcelable {
    public static final Creator<ComicListObject> CREATOR = new Creator<ComicListObject>() {
        public ComicListObject createFromParcel(Parcel in) {
            return new ComicListObject(in);
        }

        public ComicListObject[] newArray(int size) {
            return new ComicListObject[size];
        }
    };
    String author;
    ArrayList<String> categories;
    @SerializedName("_id")
    String comicId;
    @SerializedName("epsCount")
    int episodeCount;
    boolean finished;
    int likesCount;
    int pagesCount;
    ThumbnailObject thumb;
    String title;

    public ComicListObject(String comicId) {
        this.comicId = comicId;
        this.title = null;
        this.author = null;
        this.likesCount = 0;
        this.pagesCount = 1;
        this.episodeCount = 1;
        this.finished = false;
        this.categories = null;
        this.thumb = null;
    }

    public ComicListObject(String comicId, String title, String author, int likesCount, int pagesCount, int episodeCount, boolean finished, ArrayList<String> categories, ThumbnailObject thumb) {
        this.comicId = comicId;
        this.title = title;
        this.author = author;
        this.likesCount = likesCount;
        this.pagesCount = pagesCount;
        this.episodeCount = episodeCount;
        this.finished = finished;
        this.categories = categories;
        this.thumb = thumb;
    }

    public ComicListObject(DbComicDetailObject dbComicDetailObject) {
        this.comicId = dbComicDetailObject.getComicId();
        this.title = dbComicDetailObject.getTitle();
        this.author = dbComicDetailObject.getAuthor();
        this.likesCount = dbComicDetailObject.getLikesCount();
        this.pagesCount = dbComicDetailObject.getPagesCount();
        this.episodeCount = dbComicDetailObject.getEpisodeCount();
        this.finished = dbComicDetailObject.isFinished();
        this.categories = (ArrayList) new Gson().fromJson(dbComicDetailObject.getCategories(), ArrayList.class);
        this.thumb = dbComicDetailObject.getThumb();
    }

    protected ComicListObject(Parcel in) {
        this.comicId = in.readString();
        this.title = in.readString();
        this.author = in.readString();
        this.likesCount = in.readInt();
        this.pagesCount = in.readInt();
        this.episodeCount = in.readInt();
        this.finished = in.readByte() != (byte) 0;
        this.categories = in.createStringArrayList();
        this.thumb = (ThumbnailObject) in.readParcelable(ThumbnailObject.class.getClassLoader());
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

    public int getLikesCount() {
        return this.likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
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

    public boolean isFinished() {
        return this.finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public ArrayList<String> getCategories() {
        return this.categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ThumbnailObject getThumb() {
        return this.thumb;
    }

    public void setThumb(ThumbnailObject thumb) {
        this.thumb = thumb;
    }

    public String toString() {
        return "ComicListObject{comicId='" + this.comicId + '\'' + ", title='" + this.title + '\'' + ", author='" + this.author + '\'' + ", likesCount=" + this.likesCount + ", pagesCount=" + this.pagesCount + ", episodeCount=" + this.episodeCount + ", finished=" + this.finished + ", categories=" + this.categories + ", thumb=" + this.thumb + '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.comicId);
        parcel.writeString(this.title);
        parcel.writeString(this.author);
        parcel.writeInt(this.likesCount);
        parcel.writeInt(this.pagesCount);
        parcel.writeInt(this.episodeCount);
        parcel.writeByte((byte) (this.finished ? 1 : 0));
        parcel.writeStringList(this.categories);
        parcel.writeParcelable(this.thumb, i);
    }
}

package com.qiuchenly.comicparse.ProductModules.Bika;

import com.google.gson.annotations.SerializedName;

public class CategoryObject {
    @SerializedName("_id")
    String categoryId;
    String description;
    ThumbnailObject thumb;
    String title;

    public CategoryObject(String categoryId, String title, String description, ThumbnailObject thumb) {
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.thumb = thumb;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ThumbnailObject getThumb() {
        return this.thumb;
    }

    public void setThumb(ThumbnailObject thumb) {
        this.thumb = thumb;
    }

    public String toString() {
        return "CategoryObject{categoryId='" + this.categoryId + '\'' + ", title='" + this.title + '\'' + ", description='" + this.description + '\'' + ", thumb=" + this.thumb + '}';
    }
}

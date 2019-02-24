package com.qiuchenly.comicparse.Http.BikaApi.requests;

public class UpdateUserTitleBody {
    String title;

    public UpdateUserTitleBody(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        return "UpdateUserTitleBody{title='" + this.title + '\'' + '}';
    }
}

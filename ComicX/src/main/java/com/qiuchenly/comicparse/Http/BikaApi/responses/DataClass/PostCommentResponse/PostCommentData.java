package com.qiuchenly.comicparse.Http.BikaApi.responses.DataClass.PostCommentResponse;

import com.google.gson.annotations.SerializedName;
import com.qiuchenly.comicparse.Http.BikaApi.UserBasicObject;

public class PostCommentData {
    String content;
    @SerializedName("_user")
    UserBasicObject user;

    public PostCommentData(String content, UserBasicObject user) {
        this.content = content;
        this.user = user;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserBasicObject getUser() {
        return this.user;
    }

    public void setUser(UserBasicObject user) {
        this.user = user;
    }

    public String toString() {
        return "PostCommentData{content='" + this.content + '\'' + ", user=" + this.user + '}';
    }
}

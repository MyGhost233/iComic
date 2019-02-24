package com.qiuchenly.comicparse.Http.BikaApi.requests;

public class CommentBody {
    String content;

    public CommentBody(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

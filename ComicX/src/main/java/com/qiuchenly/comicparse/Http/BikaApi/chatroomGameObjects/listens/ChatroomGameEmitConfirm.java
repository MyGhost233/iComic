package com.qiuchenly.comicparse.Http.BikaApi.chatroomGameObjects.listens;

public class ChatroomGameEmitConfirm {
    String id;
    String options;

    public ChatroomGameEmitConfirm(String id, String options) {
        this.id = id;
        this.options = options;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOptions() {
        return this.options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String toString() {
        return "ChatroomGameEmitConfirm{id='" + this.id + '\'' + ", options='" + this.options + '\'' + '}';
    }
}

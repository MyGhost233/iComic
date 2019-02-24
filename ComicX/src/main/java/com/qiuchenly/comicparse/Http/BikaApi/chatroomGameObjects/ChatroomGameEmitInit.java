package com.qiuchenly.comicparse.Http.BikaApi.chatroomGameObjects;

public class ChatroomGameEmitInit {
    String userId;

    public ChatroomGameEmitInit(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String toString() {
        return "ChatroomGameEmitInit{userId='" + this.userId + '\'' + '}';
    }
}

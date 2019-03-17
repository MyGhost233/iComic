package com.qiuchenly.comicparse.Http.Bika;

public class ChatSystemObject extends ChatBaseObject {
    String message;

    public ChatSystemObject(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "ChatSystemObject{message='" + this.message + '\'' + '}';
    }
}

package com.qiuchenly.comicparse.Http.BikaApi.chatroomObjects;

public class ChangeTitleAction extends ChatroomSystemAction {
    String from;
    String title;
    String user;
    String user_id;

    public ChangeTitleAction(String action, String email) {
        super(action, email);
    }

    public ChangeTitleAction(String action, String email, String title, String from, String user, String user_id) {
        super(action, email);
        this.title = title;
        this.from = from;
        this.user = user;
        this.user_id = user_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String toString() {
        return "ChangeTitleAction{action='" + this.action + '\'' + ", title='" + this.title + '\'' + ", from='" + this.from + '\'' + ", user='" + this.user + '\'' + ", user_id='" + this.user_id + '\'' + '}';
    }
}

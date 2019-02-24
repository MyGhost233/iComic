package com.qiuchenly.comicparse.Http.BikaApi.chatroomObjects;

public class SetAvatarExtraAction extends ChatroomSystemAction {
    int extra;
    String from;
    String user;
    String user_id;

    public SetAvatarExtraAction(String action, String email) {
        super(action, email);
    }

    public SetAvatarExtraAction(String action, String email, String user, String user_id, String from, int extra) {
        super(action, email);
        this.user = user;
        this.user_id = user_id;
        this.from = from;
        this.extra = extra;
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

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getExtra() {
        return this.extra;
    }

    public void setExtra(int extra) {
        this.extra = extra;
    }

    public String toString() {
        return "HideAvatarAction{action='" + this.action + '\'' + ", user='" + this.user + '\'' + ", user_id='" + this.user_id + '\'' + ", from='" + this.from + '\'' + ", extra='" + this.extra + '\'' + '}';
    }
}

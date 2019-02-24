package com.qiuchenly.comicparse.Http.BikaApi.chatroomObjects;

public class SetAvatarAction extends ChatroomSystemAction {
    String from;
    int no;
    String user;
    String user_id;

    public SetAvatarAction(String action, String email) {
        super(action, email);
    }

    public SetAvatarAction(String action, String email, String user, String user_id, String from, int no) {
        super(action, email);
        this.user = user;
        this.user_id = user_id;
        this.from = from;
        this.no = no;
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

    public int getNo() {
        return this.no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String toString() {
        return "HideAvatarAction{action='" + this.action + '\'' + ", user='" + this.user + '\'' + ", user_id='" + this.user_id + '\'' + ", from='" + this.from + '\'' + ", no='" + this.no + '\'' + '}';
    }
}

package com.qiuchenly.comicparse.Http.BikaApi.chatroomObjects;

public class ImageAction extends ChatroomSystemAction {
    String from;
    boolean toggle;

    public ImageAction(String action, String email) {
        super(action, email);
    }

    public ImageAction(String action, String email, boolean toggle, String from) {
        super(action, email);
        this.toggle = toggle;
        this.from = from;
    }

    public boolean isToggle() {
        return this.toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String toString() {
        return "ImageAction{action=" + this.action + ", toggle=" + this.toggle + ", from='" + this.from + '\'' + '}';
    }
}

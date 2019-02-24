package com.qiuchenly.comicparse.Http.BikaApi.requests;

public class AvatarBody {
    String avatar;

    public AvatarBody(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String toString() {
        return "AvatarBody{avatar='" + this.avatar + '\'' + '}';
    }
}

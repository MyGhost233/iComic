package com.qiuchenly.comicparse.Http.BikaApi.responses;


import com.qiuchenly.comicparse.Http.BikaApi.UserProfileObject;

public class UserProfileResponse {
    UserProfileObject user;

    public UserProfileResponse(UserProfileObject user) {
        this.user = user;
    }

    public UserProfileObject getUser() {
        return this.user;
    }

    public void setUser(UserProfileObject user) {
        this.user = user;
    }

    public String toString() {
        return "UserProfileResponse{user=" + this.user + '}';
    }
}

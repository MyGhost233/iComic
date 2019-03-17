package com.qiuchenly.comicparse.Http.Bika.responses;


import com.qiuchenly.comicparse.Http.Bika.UserProfileObject;

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

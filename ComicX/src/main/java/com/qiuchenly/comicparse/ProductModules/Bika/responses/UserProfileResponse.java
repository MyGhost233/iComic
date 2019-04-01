package com.qiuchenly.comicparse.ProductModules.Bika.responses;


import com.qiuchenly.comicparse.ProductModules.Bika.UserProfileObject;

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

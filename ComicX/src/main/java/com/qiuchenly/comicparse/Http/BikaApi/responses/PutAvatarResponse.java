package com.qiuchenly.comicparse.Http.BikaApi.responses;

import com.qiuchenly.comicparse.Http.BikaApi.ThumbnailObject;

public class PutAvatarResponse {
    ThumbnailObject avatar;

    public PutAvatarResponse(ThumbnailObject avatar) {
        this.avatar = avatar;
    }

    public ThumbnailObject getAvatar() {
        return this.avatar;
    }

    public void setAvatar(ThumbnailObject avatar) {
        this.avatar = avatar;
    }

    public String toString() {
        return "PutAvatarResponse{avatar=" + this.avatar + '}';
    }
}

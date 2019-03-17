package com.qiuchenly.comicparse.Http.Bika.responses;

import com.qiuchenly.comicparse.Http.Bika.ThumbnailObject;

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

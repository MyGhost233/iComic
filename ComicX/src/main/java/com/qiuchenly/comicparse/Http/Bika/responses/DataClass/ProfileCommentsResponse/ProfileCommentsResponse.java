package com.qiuchenly.comicparse.Http.Bika.responses.DataClass.ProfileCommentsResponse;

public class ProfileCommentsResponse {
    ProfileCommentsData comments;

    public ProfileCommentsResponse(ProfileCommentsData comments) {
        this.comments = comments;
    }

    public ProfileCommentsData getComments() {
        return this.comments;
    }

    public void setComments(ProfileCommentsData comments) {
        this.comments = comments;
    }
}

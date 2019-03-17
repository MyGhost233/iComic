package com.qiuchenly.comicparse.Http.Bika.responses;

import java.util.ArrayList;

public class TagListResponse {
    ArrayList<String> tags;

    public TagListResponse(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}

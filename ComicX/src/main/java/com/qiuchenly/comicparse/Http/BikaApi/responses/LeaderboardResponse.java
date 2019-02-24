package com.qiuchenly.comicparse.Http.BikaApi.responses;

import com.qiuchenly.comicparse.Http.BikaApi.LeaderboardComicListObject;

import java.util.ArrayList;

public class LeaderboardResponse {
    ArrayList<LeaderboardComicListObject> comics;

    public LeaderboardResponse(ArrayList<LeaderboardComicListObject> comics) {
        this.comics = comics;
    }

    public ArrayList<LeaderboardComicListObject> getComics() {
        return this.comics;
    }

    public void setComics(ArrayList<LeaderboardComicListObject> comics) {
        this.comics = comics;
    }

    public String toString() {
        return "LeaderboardResponse{comics=" + this.comics + '}';
    }
}

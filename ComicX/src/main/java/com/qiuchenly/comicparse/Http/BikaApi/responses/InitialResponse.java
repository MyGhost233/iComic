package com.qiuchenly.comicparse.Http.BikaApi.responses;

import com.qiuchenly.comicparse.Http.BikaApi.LatestApplicationObject;

public class InitialResponse {
    public String imageServer;
    public boolean isPunched;
    public LatestApplicationObject latestApplication;

    public String toString() {
        return "InitialResponse{latestApplication=" + this.latestApplication + ", isPunched=" + this.isPunched + ", imageServer=" + this.imageServer + '}';
    }
}

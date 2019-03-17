package com.qiuchenly.comicparse.Http.Bika.responses;

import com.qiuchenly.comicparse.Http.Bika.LatestApplicationObject;

public class InitialResponse {
    public String imageServer;
    public boolean isPunched;
    public LatestApplicationObject latestApplication;

    public String toString() {
        return "InitialResponse{latestApplication=" + this.latestApplication + ", isPunched=" + this.isPunched + ", imageServer=" + this.imageServer + '}';
    }
}

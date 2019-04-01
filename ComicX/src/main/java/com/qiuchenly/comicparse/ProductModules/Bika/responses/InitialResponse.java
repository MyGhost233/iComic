package com.qiuchenly.comicparse.ProductModules.Bika.responses;

import com.qiuchenly.comicparse.ProductModules.Bika.LatestApplicationObject;

public class InitialResponse {
    public String imageServer;
    public boolean isPunched;
    public LatestApplicationObject latestApplication;

    public String toString() {
        return "InitialResponse{latestApplication=" + this.latestApplication + ", isPunched=" + this.isPunched + ", imageServer=" + this.imageServer + '}';
    }
}

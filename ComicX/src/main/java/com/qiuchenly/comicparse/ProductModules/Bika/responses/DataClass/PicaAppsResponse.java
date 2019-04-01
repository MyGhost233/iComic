package com.qiuchenly.comicparse.ProductModules.Bika.responses.DataClass;

import com.qiuchenly.comicparse.ProductModules.Bika.PicaAppObject;

import java.util.ArrayList;

public class PicaAppsResponse {
    ArrayList<PicaAppObject> apps;

    public PicaAppsResponse(ArrayList<PicaAppObject> apps) {
        this.apps = apps;
    }

    public ArrayList<PicaAppObject> getApps() {
        return this.apps;
    }

    public void setApps(ArrayList<PicaAppObject> apps) {
        this.apps = apps;
    }

    public String toString() {
        return "PicaAppsResponse{apps=" + this.apps + '}';
    }
}

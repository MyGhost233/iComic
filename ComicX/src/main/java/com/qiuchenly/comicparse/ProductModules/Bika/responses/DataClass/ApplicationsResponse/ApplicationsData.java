package com.qiuchenly.comicparse.ProductModules.Bika.responses.DataClass.ApplicationsResponse;


import com.qiuchenly.comicparse.ProductModules.Bika.LatestApplicationObject;

import java.util.ArrayList;

public class ApplicationsData {
    ArrayList<LatestApplicationObject> docs;
    int limit;
    int page;
    int pages;
    int total;

    public ApplicationsData(int total, int limit, int page, int pages, ArrayList<LatestApplicationObject> docs) {
        this.total = total;
        this.limit = limit;
        this.page = page;
        this.pages = pages;
        this.docs = docs;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return this.pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public ArrayList<LatestApplicationObject> getDocs() {
        return this.docs;
    }

    public void setDocs(ArrayList<LatestApplicationObject> docs) {
        this.docs = docs;
    }

    public String toString() {
        return "ApplicationsData{total=" + this.total + ", limit=" + this.limit + ", page=" + this.page + ", pages=" + this.pages + ", docs=" + this.docs + '}';
    }
}

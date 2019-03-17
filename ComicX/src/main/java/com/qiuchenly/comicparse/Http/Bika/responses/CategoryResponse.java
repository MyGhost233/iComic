package com.qiuchenly.comicparse.Http.Bika.responses;

import com.qiuchenly.comicparse.Http.Bika.CategoryObject;

import java.util.ArrayList;

public class CategoryResponse {
    public ArrayList<CategoryObject> categories;

    public CategoryResponse(ArrayList<CategoryObject> categories) {
        this.categories = categories;
    }

    public ArrayList<CategoryObject> getCategories() {
        return this.categories;
    }

    public void setCategories(ArrayList<CategoryObject> categories) {
        this.categories = categories;
    }

    public String toString() {
        return "CategoryResponse{categories=" + this.categories + '}';
    }
}

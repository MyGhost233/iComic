package com.qiuchenly.comicparse.ProductModules.Bilibili

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface Bilibili {
    @GET("/x/tv/region")
    fun getCategory(): Call<ResponseBody>

    @GET("/api/oauth2/getKey")
    fun getKey(): Call<ResponseBody>
}
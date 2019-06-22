package com.qiuchenly.comicparse.HttpRequests

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface BingRequests {
    @GET("/")
    fun getImageSrc(): Call<ResponseBody>
}
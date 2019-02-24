package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Request

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface Requests {
    @GET("/")
    fun getIndex(): Call<ResponseBody>
}
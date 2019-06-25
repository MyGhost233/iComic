package com.qiuchenly.comicparse.ProductModules.Common.NMSL

import com.qiuchenly.comicparse.Bean.WelcomeLang
import retrofit2.Call
import retrofit2.http.GET

interface WelcomeLangApi {

    @GET("/?encode=json")
    fun getNiceOne(): Call<WelcomeLang>

}
package com.qiuchenly.comicparse.Http.NMSL

import com.qiuchenly.comicparse.Bean.NMSLBean
import retrofit2.Call
import retrofit2.http.GET

interface NMSLApi {

    @GET("/nmsl")
    fun getOne(): Call<NMSLBean>

    @GET("/?encode=json")
    fun getNiceOne(): Call<NMSLBean>

}
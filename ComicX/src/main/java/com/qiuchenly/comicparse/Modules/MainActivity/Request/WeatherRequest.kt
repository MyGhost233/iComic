package com.qiuchenly.comicparse.Modules.MainActivity.Request

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface WeatherRequest {
    @GET("/s?q=%E5%A4%A9%E6%B0%94")
    fun getWeatherInfo(): Call<ResponseBody>
}
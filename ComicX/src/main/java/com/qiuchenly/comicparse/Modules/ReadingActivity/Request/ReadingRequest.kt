package com.qiuchenly.comicparse.Modules.ReadingActivity.Request

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.HTTP
import retrofit2.http.Path

interface ReadingRequest {
    @HTTP(method = "GET", path = "{path}", hasBody = false)
    fun getAllImages(@Path("path") url: String): Call<ResponseBody>
}
package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Request

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Path

interface Requests {
    //https://www.mh1234.com/comic/15149.html
    @HTTP(method = "GET", path = "/comic/{bookid}.html", hasBody = false)
    fun getBookInfo(@Path("bookid") bookID: String): Call<ResponseBody>

    @HTTP(method = "GET", path = "/qTcms_Inc/Ajax.asp?action=GetScore&id={bookid}", hasBody = false)
    fun getBookScore(@Path("bookid") bookID: String): Call<ResponseBody>
}
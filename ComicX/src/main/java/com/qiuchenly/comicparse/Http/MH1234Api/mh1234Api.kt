package com.qiuchenly.comicparse.Http.MH1234Api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface mh1234Api {

    //key=%C8%EB%B9%C7&button=%CB%D1%CB%F7%C2%FE%BB%AD
    @POST("/search.asp")
    @FormUrlEncoded
    fun searchComic(@Field("key") name: String, @Field("button") button: String)

    //https://www.mh1234.com/comic/15149.html
    @HTTP(method = "GET", path = "/comic/{bookid}.html", hasBody = false)
    fun getBookInfo(@Path("bookid") bookID: String): Call<ResponseBody>

    @HTTP(method = "GET", path = "/qTcms_Inc/Ajax.asp?action=GetScore&id={bookid}", hasBody = false)
    fun getBookScore(@Path("bookid") bookID: String): Call<ResponseBody>

    @HTTP(method = "GET", path = "{path}", hasBody = false)
    fun getAllImages(@Path("path") url: String): Call<ResponseBody>
}
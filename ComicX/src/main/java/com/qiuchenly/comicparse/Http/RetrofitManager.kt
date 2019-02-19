package com.qiuchenly.comicparse.Http

import android.text.format.Time
import com.qiuchenly.comicparse.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {

    val BASE_URL = BaseURL.BASE_URL
    private var mRetrofitClient = buildRetrofit()
    fun get() = mRetrofitClient
    private fun buildRetrofit(): Retrofit {
        val mHttpInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        val mHttp = OkHttpClient.Builder().apply {
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            connectTimeout(10, TimeUnit.SECONDS)
            addInterceptor(mHttpInterceptor)
        }.build()

        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            client(mHttp)
            //addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

//    fun getBookDetails()
}
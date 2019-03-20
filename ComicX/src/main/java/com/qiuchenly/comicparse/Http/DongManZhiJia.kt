package com.qiuchenly.comicparse.Http

import com.qiuchenly.comicparse.Http.Dmzj.ComicApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

object DongManZhiJia : BaseRetrofitManager<ComicApi>() {
    fun getCusAPI(mApi: String): ComicApi {
        val httpClient = OkHttpClient.Builder()
        HttpLoggingInterceptor().level = HttpLoggingInterceptor.Level.BODY
        val api = Retrofit
                .Builder()
                .baseUrl(mApi)
                .addConverterFactory(
                        GsonConverterFactory.create()
                )
                .client(
                        httpClient.build()
                )
                .build()
                .create<ComicApi>(ComicApi::class.java)
                as ComicApi
        setAPI(api)
        return api
    }

    //https://images.dmzj.com/img/webpic/15/1003681551458703419.jpg
    fun getUserAPI(): ComicApi {
        val httpClient = OkHttpClient.Builder()
        HttpLoggingInterceptor().level = HttpLoggingInterceptor.Level.BODY
        val api = Retrofit
                .Builder()
                .baseUrl(ComicApi.BASE_API_USER)
                .addConverterFactory(
                        GsonConverterFactory.create()
                )
                .client(
                        httpClient.build()
                )
                .build()
                .create<ComicApi>(ComicApi::class.java)
                as ComicApi
        setAPI(api)
        return api
    }

    fun getV3API(): ComicApi {
        val httpClient = OkHttpClient.Builder()
                .hostnameVerifier(object : HostnameVerifier {
                    override fun verify(hostname: String?, session: SSLSession?): Boolean {
                        return true
                    }
                })
        //val tlsSocketFactory = TLSSocketFactory()
        //httpClient.sslSocketFactory(tlsSocketFactory, tlsSocketFactory.systemDefaultTrustManager())
        HttpLoggingInterceptor().level = HttpLoggingInterceptor.Level.BODY
        val api = Retrofit
                .Builder()
                .baseUrl(ComicApi.BASE_API_V3API)
                .addConverterFactory(
                        GsonConverterFactory.create()
                )
                .client(
                        httpClient.build()
                )
                .build()
                .create<ComicApi>(ComicApi::class.java)
                as ComicApi
        setAPI(api)
        return api
    }
}
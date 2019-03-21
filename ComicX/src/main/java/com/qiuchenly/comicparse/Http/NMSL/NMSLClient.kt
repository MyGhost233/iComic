package com.qiuchenly.comicparse.Http.NMSL

import com.qiuchenly.comicparse.Http.BaseRetrofitManager
import com.qiuchenly.comicparse.Http.Bika.RestWakaClient.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NMSLClient : BaseRetrofitManager<NMSLApi>() {
    const val BASE_URL_ZUICHOU = "http://39.106.18.14"
    const val BASE_URL_HAOCI = "https://v1.hitokoto.cn/"
    fun generateAPI(base: String) {
        val httpClient = OkHttpClient.Builder()
        HttpLoggingInterceptor().level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                return chain.proceed(
                        original.newBuilder()
                                .header("X-Requested-With", "XMLHttpRequest")
                                .method(original.method(), original.body())
                                .build()
                )
            }
        })
        //val tlsSocketFactory = TLSSocketFactory()
        //httpClient.sslSocketFactory(tlsSocketFactory, tlsSocketFactory.systemDefaultTrustManager())
        val api = Retrofit.Builder().baseUrl(base).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build().create<NMSLApi>(NMSLApi::class.java) as NMSLApi
        setAPI(api)
    }

    fun generateAPI() {
        val httpClient = OkHttpClient.Builder()
        HttpLoggingInterceptor().level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                return chain.proceed(
                        original.newBuilder()
                                .header("X-Requested-With", "XMLHttpRequest")
                                .method(original.method(), original.body())
                                .build()
                )
            }
        })
        //val tlsSocketFactory = TLSSocketFactory()
        //httpClient.sslSocketFactory(tlsSocketFactory, tlsSocketFactory.systemDefaultTrustManager())
        val api = Retrofit.Builder().baseUrl(BASE_URL_ZUICHOU).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build().create<NMSLApi>(NMSLApi::class.java) as NMSLApi
        setAPI(api)
    }

    fun generateNiceLang() {
        generateAPI(BASE_URL_HAOCI)
    }
    //  https://v1.hitokoto.cn
    //  /?encode=json
}
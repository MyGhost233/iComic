package com.qiuchenly.comicparse.Http

import android.content.Context
import android.util.Log
import com.qiuchenly.comicparse.BuildConfig
import com.qiuchenly.comicparse.EncryptUtils.BiKaJni
import com.qiuchenly.comicparse.Http.BikaApi.*
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.GeneralSecurityException
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSession

object RetrofitManager {

    private var TAG = "RetrofitManager"

    val BASE_URL = BaseURL.BASE_URL
    private var mRetrofitClient = buildRetrofit(BASE_URL)
    fun get() = mRetrofitClient
    fun getCusUrl(BaseUrl: String) = buildRetrofit(BaseUrl)
    private fun buildRetrofit(): Retrofit.Builder {
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
            client(mHttp)
            //addConverterFactory(GsonConverterFactory.create())
        }
    }

    private fun buildRetrofit(mBaseUrl: String): Retrofit {
        return buildRetrofit().baseUrl(mBaseUrl).build()
    }


    //-----------------------    bika   API  --------------------------
    private val API_KEY = "C69BAF41DA5ABD1FFEDC6D2FEA56B"
    private val BASE_URL_PIKA = "https://picaapi.picacomic.com/"
    private val CERT_URL = "picaapi.picacomic.com"
    private val CHATROOM = "https://chat.picacomic.com"
    private val CHATROOM_GAME = "https://game.picacomic.com"
    private var buildVersion: String = "39"
    private var uuid: String = "ca7ad142-bb59-388d-aafd-d6bc5a6c6b48"
    private var version: String = "2.1.0.5"
    private var apiService: ApiService? = null
    var channel: Int = 1


    fun getBiCaApi() = apiService
    private val hostnameVerifier: HostnameVerifier = object : HostnameVerifier {
        override fun verify(hostname: String?, session: SSLSession?): Boolean {
            return HttpsURLConnection.getDefaultHostnameVerifier().verify(CERT_URL, session)
        }
    }

    fun biCaClient(context: Context) {
        val e: GeneralSecurityException
        val httpClient = OkHttpClient.Builder()
        HttpLoggingInterceptor().level = HttpLoggingInterceptor.Level.BODY
        if (PreferenceHelper.isGirl(context)) {
            httpClient.dns(HttpDns())
        }
        httpClient.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val uid = UUID.randomUUID().toString().replace("-", "")
                val url = original.url().toString().replace(BASE_URL_PIKA, "")
                val time = ((System.currentTimeMillis() / 1000) + PreferenceHelper.getTimeDifference(context)).toString()
                val signature = BiKaJni.getStringCon(arrayOf(BASE_URL_PIKA, url, time, uid, original.method(), API_KEY, version, buildVersion))
                Log.d(TAG, "Signature = $signature")
                val response = chain.proceed(
                        original.newBuilder()
                                .header("api-key", API_KEY)
                                .header("accept", "application/vnd.picacomic.com.v1+json")
                                .header("app-channel", channel.toString())
                                .header(ChatroomActionInterface.TIME, time)
                                .header("nonce", uid)
                                .header("signature", signature)
                                .header("app-version", version)
                                .header("app-uuid", uuid)
                                .header("app-platform", "android")
                                .header("app-build-version", buildVersion)
                                .header("user-agent","okhttp/3.8.1")
                                .method(original.method(), original.body())
                                .build()
                )
                val serverTime = response.headers().get("Server-Time")
                Log.d(TAG, "Server Time = $serverTime")
                try {
                    val diffTime = java.lang.Long.parseLong(serverTime) - System.currentTimeMillis() / 1000
                    PreferenceHelper.setTimeDifference(context, diffTime)
                    Log.d(TAG, "Save Diff Time = $diffTime")
                } catch (e: Exception) {
                }

                return response
            }
        })
        val certificatePinner = CertificatePinner.Builder().build()
        try {
            val tlsSocketFactory = TLSSocketFactory()
            httpClient.sslSocketFactory(tlsSocketFactory, tlsSocketFactory.systemDefaultTrustManager())
        } catch (e2: KeyManagementException) {
            e = e2
            Log.d(TAG, "Failed to create Socket connection ")
            e.printStackTrace()
            this.apiService = Retrofit.Builder().baseUrl(BASE_URL_PIKA).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build().create<ApiService>(ApiService::class.java!!) as ApiService
        } catch (e3: NoSuchAlgorithmException) {
            e = e3
            Log.d(TAG, "Failed to create Socket connection ")
            e.printStackTrace()
            this.apiService = Retrofit.Builder().baseUrl(BASE_URL_PIKA).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build().create<ApiService>(ApiService::class.java!!) as ApiService
        }

        this.apiService = Retrofit.Builder().baseUrl(BASE_URL_PIKA).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build().create<ApiService>(ApiService::class.java!!) as ApiService
    }

    fun signature(vararg parameters: String): String {
        return parameters[0] + parameters[1] + parameters[2] + parameters[3] + parameters[4]
    }


//    fun getBookDetails()
}
package com.qiuchenly.comicparse.Utils

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.qiuchenly.comicparse.Http.Bika.HttpDns
import okhttp3.OkHttpClient
import java.io.InputStream
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

@GlideModule
class MyAppGlideModule : AppGlideModule() {
    /**
     * 全局配置Glide选项
     */
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        // 例如：全局设置图片格式为RGB_565
        builder.setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_RGB_565))
    }

    private var httpClient: OkHttpClient? = null
    /**
     * 利用哔咔服务器DNS地址解析图片
     * 注册自定义组件
     */
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        if (httpClient == null) {
            httpClient = OkHttpClient.Builder()
                   /* .hostnameVerifier(object : HostnameVerifier {
                        override fun verify(hostname: String?, session: SSLSession?): Boolean {
                            return true
                        }
                    })*/
                    .dns(HttpDns())
                    .build()
        }
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(httpClient!!))
    }
}

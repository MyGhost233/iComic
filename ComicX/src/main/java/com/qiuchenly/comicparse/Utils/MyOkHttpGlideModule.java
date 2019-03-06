package com.qiuchenly.comicparse.Utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public final class MyOkHttpGlideModule extends AppGlideModule {
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10000, TimeUnit.SECONDS)
            .readTimeout(10000, TimeUnit.SECONDS)
            .writeTimeout(10000, TimeUnit.SECONDS)
            .build();

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(client);
        registry.replace(GlideUrl.class, InputStream.class, factory);
    }
}
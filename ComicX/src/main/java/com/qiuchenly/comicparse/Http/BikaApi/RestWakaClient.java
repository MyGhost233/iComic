package com.qiuchenly.comicparse.Http.BikaApi;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestWakaClient {
    public static final String BASE_URL = "https://139.59.113.68/";
    public static final String TAG = RestWakaClient.class.getSimpleName();
    public static String buildVersion;
    public static String uuid;
    public static String version;
    private ApiService apiService;

    public RestWakaClient() {
        //uuid = Tools.getUuid(context);
        //version = context.getString(R.string.app_version);
        //buildVersion = context.getString(R.string.app_build_version);
        Builder httpClient = new Builder();
        new HttpLoggingInterceptor().setLevel(Level.BODY);
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            httpClient.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
            httpClient.hostnameVerifier((hostname, session) -> true);
            this.apiService = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build().create(ApiService.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ApiService getApiService() {
        return this.apiService;
    }

    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }
}
package com.qiuchenly.comicparse.ProductModules.Bika;

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
    public static final String BASE_URL = "http://68.183.234.72/";
    public static final String TAG = "RestWakaClient";
    private ApiService apiService;

    public RestWakaClient() {
        Builder builder = new Builder();
        new HttpLoggingInterceptor().setLevel(Level.BODY);
        try {
            TrustManager[] trustManagerArr = new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            SSLContext instance = SSLContext.getInstance("SSL");
            instance.init(null, trustManagerArr, new SecureRandom());
            builder.sslSocketFactory(instance.getSocketFactory(), (X509TrustManager) trustManagerArr[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                public boolean verify(String str, SSLSession sSLSession) {
                    return true;
                }
            });
            this.apiService = (ApiService) new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(builder.build()).build().create(ApiService.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ApiService getApiService() {
        return this.apiService;
    }

    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            public boolean verify(String str, SSLSession sSLSession) {
                return true;
            }
        };
    }
}
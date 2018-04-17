package com.support.retrofit.network;

import android.support.v4.BuildConfig;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.support.base.CoreApp;

import java.io.File;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB
    private static final int OKHTTP_TIMEOUT = 60; // seconds
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient;

    /**
     * You can create multiple methods for different BaseURL
     *
     * @return {@link Retrofit} object
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
//                    .setLenient()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(WebService.BaseLink + WebService.Version)
                    .client(getOKHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }


    /**
     * settings like caching, Request Timeout, Logging can be configured here.
     *
     * @return {@link OkHttpClient}
     */
    private static OkHttpClient getOKHttpClient() {
        if (okHttpClient == null) {
            Cache cache = new Cache(new File(CoreApp.getInstance().getCacheDir(), "http")
                    , DISK_CACHE_SIZE);

            // init cookie manager
            CookieHandler cookieHandler = new CookieManager();

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .cookieJar(new JavaNetCookieJar(cookieHandler))
                    .retryOnConnectionFailure(true)
                    .connectTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                    .cache(cache);

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor);
            }

            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    public static Retrofit getHeaderClient(String header) {

        Cache cache = new Cache(new File(CoreApp.getInstance().getCacheDir(), "http")
                , DISK_CACHE_SIZE);

        // init cookie manager
        CookieHandler cookieHandler = new CookieManager();

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .addInterceptor(new HeaderIntercepter(header))
                .retryOnConnectionFailure(true)
                .connectTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .cache(cache);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        OkHttpClient okHttpClient = builder.build();

        Gson gson = new GsonBuilder()
//                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebService.BaseLink + WebService.Version)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;

    }

    public interface WebService {
        //        String BaseLink = "http://generationonline.odoo.drcsystems.com/";//Test Staging server.
        String BaseLink = "https://thegenerationonline.com/";//Live Staging server.
        String Version = "api/";
    }

    private static class HeaderIntercepter implements Interceptor {

        private String headerString;

        HeaderIntercepter(String headerString) {
            this.headerString = headerString;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Log.v("Service", "Request");

            Request request = chain.request().newBuilder()
//                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .addHeader("Cookie", "session_id=" + headerString)
                    .addHeader("from_mobile", "true")
//                    .addHeader("authorization", "Bearer " + headerString)
                    .build();
            Response response = chain.proceed(request);

            return response;

        }
    }
}
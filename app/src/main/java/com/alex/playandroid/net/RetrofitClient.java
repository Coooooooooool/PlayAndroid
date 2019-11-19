package com.alex.playandroid.net;

import com.alex.playandroid.app.WanAndroid;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    /**
     * 超时时间
     */
    private static final int TIMEOUT = 30;

    private Retrofit retrofit;

    private static class RetrofitClientInstance{
        private static RetrofitClient retrofitClient = new RetrofitClient();
    }

    public static RetrofitClient getInstance() {
        return RetrofitClientInstance.retrofitClient;
    }

    private RetrofitClient(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(WanAndroid.getWanAndroid()));

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .addInterceptor(new LogInterceptor())
                .build();



        String baseUrl = "https://www.wanandroid.com/";
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

    }

    public <T> T create(final Class<T> tClass){
        if(tClass== null){
            throw new RuntimeException("Api class is null");
        }
        return retrofit.create(tClass);
    }


}

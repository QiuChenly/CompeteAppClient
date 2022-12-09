package com.qiuchen.smartcity.utils.http;

import com.qiuchen.smartcity.MyApp;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Http {
    public static Api api;
    public static String baseUrl = "http://127.0.0.1:8080";

    public static void resetApi(String ip, String port) {
        baseUrl = "http://" + ip + ":" + port;
        api = null;
        getApi();
    }

    public static Api getApi() {
        if (api == null) {
            api = new Retrofit.Builder().client(new OkHttpClient.Builder().addInterceptor(chain -> chain.proceed(
                    //MyApp.token 不可以以null的形式被设置到协议头
                    chain.request().newBuilder().addHeader("Authorization", "Bearer " + (MyApp.token == null ? "" : MyApp.token)).build())).retryOnConnectionFailure(true).build()).baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build().create(Api.class);
        }
        return api;
    }
}

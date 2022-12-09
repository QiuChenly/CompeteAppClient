package com.qiuchen.smartcity;

import android.app.Application;
import com.qiuchen.smartcity.utils.http.Http;
import com.qiuchen.smartcity.utils.local.SharedPreferenceHelper;

public class MyApp extends Application {

    public static String token = null;
    public static SharedPreferenceHelper spHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        spHelper = new SharedPreferenceHelper(this);
        token = spHelper.getToken();
        Http.resetApi(spHelper.getIP(false), spHelper.getIP(true));
    }
}

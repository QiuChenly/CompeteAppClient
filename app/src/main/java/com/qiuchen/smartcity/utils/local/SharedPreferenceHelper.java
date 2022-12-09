package com.qiuchen.smartcity.utils.local;

import android.content.Context;
import android.content.SharedPreferences;
import com.qiuchen.smartcity.utils.http.Http;

import java.lang.ref.WeakReference;

public class SharedPreferenceHelper {

    private static final String key_ip = "key_ip",
            key_port = "key_port",
            key_first = "key_first",
            key_token = "key_token";

    WeakReference<SharedPreferences> sp;

    public SharedPreferenceHelper(Context ctx) {
        this.sp = new WeakReference<>(ctx.getSharedPreferences("QiuChenly", Context.MODE_PRIVATE));
    }

    public void saveIP(String ip, String port) {
        sp.get().edit()
                .putString(key_ip, ip)
                .putString(key_port, port)
                .apply();
        Http.resetApi(ip, port);
    }

    public String getIP(boolean isPort) {
        if (isPort) {
            return sp.get().getString(key_port, "8080");
        } else return sp.get().getString(key_ip, "192.168.31.2");
    }

    public String getToken() {
        return sp.get().getString(key_token, null);
    }

    public void saveToken(String token) {
        sp.get().edit().putString(key_token, token).apply();
    }

    public boolean isFirstOpen() {
        boolean key = sp.get().getBoolean(key_first, true);
        if (key) sp.get().edit().putBoolean(key_first, false).apply();
        return key;
    }
}

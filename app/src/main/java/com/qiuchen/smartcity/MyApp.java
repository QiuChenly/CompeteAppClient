package com.qiuchen.smartcity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import com.qiuchen.smartcity.ui.act.NewsDetails;
import com.qiuchen.smartcity.ui.base.imp.PresenterImp;
import com.qiuchen.smartcity.utils.http.Http;
import com.qiuchen.smartcity.utils.local.SharedPreferenceHelper;

public class MyApp extends Application {

    public static String token = null;
    public static SharedPreferenceHelper spHelper;
    public static PresenterImp userPresenter;//避免重复创建新对象 直接引用节约内存

    @Override
    public void onCreate() {
        super.onCreate();
        spHelper = new SharedPreferenceHelper(this);
        token = spHelper.getToken();
        Http.resetApi(spHelper.getIP(false), spHelper.getIP(true));
        userPresenter = new PresenterImp();
    }

    public static void gotoNewsDetail(Context ctx, int id) {
        ctx.startActivity(new Intent(ctx, NewsDetails.class) {{
            putExtra("newsId", id);
        }});
    }
}

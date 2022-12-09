package com.qiuchen.smartcity.ui.base.model;

import com.qiuchen.smartcity.bean.request.LoginBean;
import com.qiuchen.smartcity.bean.response.BannerResponse;
import com.qiuchen.smartcity.bean.response.GetUserInfo;
import com.qiuchen.smartcity.bean.response.LoginResponse;
import com.qiuchen.smartcity.ui.base.BaseModel;
import com.qiuchen.smartcity.ui.base.imp.Calls;

import static com.qiuchen.smartcity.utils.http.Http.api;

public class MainModel extends BaseModel {
    public void getBanner(int type, Calls<BannerResponse> bannerResponseCalls) {
        api.getBanner(type).enqueue(bannerResponseCalls);
    }

    public void login(LoginBean loginBean, Calls<LoginResponse> loginResponseCalls) {
        api.login(loginBean).enqueue(loginResponseCalls);
    }

    public void getUserInfo(Calls<GetUserInfo> getUserInfoCalls) {
        api.getUserInfo().enqueue(getUserInfoCalls);
    }
}

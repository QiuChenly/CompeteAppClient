package com.qiuchen.smartcity.ui.base.imp;

import com.qiuchen.smartcity.bean.request.LoginBean;
import com.qiuchen.smartcity.bean.response.BannerResponse;
import com.qiuchen.smartcity.bean.response.GetUserInfo;
import com.qiuchen.smartcity.bean.response.LoginResponse;
import com.qiuchen.smartcity.ui.base.BasePresenter;
import com.qiuchen.smartcity.ui.base.view.BannerView;
import com.qiuchen.smartcity.ui.base.view.LoginView;
import com.qiuchen.smartcity.ui.base.view.UserInfoView;

public class PresenterImp extends BasePresenter {

    public void getBanner(int type, BannerView view) {
        model.getBanner(type, new Calls<BannerResponse>(view) {
            @Override
            public void GetData(BannerResponse body) {
                view.GetBannerList(body.rows);
            }
        });
    }

    public void login(String user, String pwd, LoginView view) {
        model.login(new LoginBean() {{
            username = user;
            password = pwd;
        }}, new Calls<LoginResponse>(view) {
            @Override
            public void GetData(LoginResponse body) {
                view.LoginSuccess(body.token);
            }
        });
    }

    public void getUserInfo(UserInfoView view) {
        model.getUserInfo(new Calls<GetUserInfo>(view) {
            @Override
            public void GetData(GetUserInfo body) {
                view.GetInfoSuccess(body.user);
            }
        });
    }
}

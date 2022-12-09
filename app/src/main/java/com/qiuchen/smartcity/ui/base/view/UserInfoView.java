package com.qiuchen.smartcity.ui.base.view;

import com.qiuchen.smartcity.bean.response.GetUserInfo;
import com.qiuchen.smartcity.ui.base.BaseView;

public interface UserInfoView extends BaseView {
    void GetInfoSuccess(GetUserInfo.UserBean user);
}

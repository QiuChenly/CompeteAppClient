package com.qiuchen.smartcity.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qiuchen.smartcity.R;
import com.qiuchen.smartcity.bean.response.GetUserInfo;
import com.qiuchen.smartcity.ui.base.BaseAct;
import com.qiuchen.smartcity.ui.base.UIParams;
import com.qiuchen.smartcity.ui.base.view.UserInfoView;
import com.qiuchen.smartcity.utils.http.Http;

public class HomeActivity extends BaseAct implements UserInfoView {
    @Override
    public UIParams getConfig(UIParams uiParams) {
        uiParams.layout = R.layout.home_activity;
        return uiParams;
    }

    ImageView user_logo;
    TextView user_nick, user_email, user_phone, user_balance;
    FloatingActionButton searchButton;

    @Override
    protected void init(Bundle savedInstanceState) {
        setBlackBar();
        user_logo = findViewById(R.id.user_logo);
        user_balance = findViewById(R.id.user_balance);
        user_email = findViewById(R.id.user_email);
        user_phone = findViewById(R.id.user_phone);
        user_nick = findViewById(R.id.user_nickName);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(v -> {
            startActivity(new Intent(this, SearchActivity.class));
        });

        mPresenterImp.getUserInfo(this);
    }

    @Override
    public void GetInfoSuccess(GetUserInfo.UserBean user) {
        Glide.with(this)
                .load(Http.baseUrl + user.avatar)
                .apply(RequestOptions
//                        .placeholder(R.drawable.ic_launcher_foreground)
                        .circleCropTransform()).into(user_logo);
        user_balance.setText(String.format("余额: %d | 积分: %d", user.balance, user.score));
        user_email.setText(user.email);
        user_nick.setText(user.nickName);
        user_phone.setText(user.phonenumber);
    }
}

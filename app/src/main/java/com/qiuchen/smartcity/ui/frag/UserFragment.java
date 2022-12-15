package com.qiuchen.smartcity.ui.frag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qiuchen.smartcity.MyApp;
import com.qiuchen.smartcity.R;
import com.qiuchen.smartcity.bean.response.GetUserInfo;
import com.qiuchen.smartcity.ui.act.LoginActivity;
import com.qiuchen.smartcity.ui.base.BaseFragment;
import com.qiuchen.smartcity.ui.base.view.UserInfoView;
import com.qiuchen.smartcity.utils.http.Http;

import static com.qiuchen.smartcity.MyApp.spHelper;

public class UserFragment extends BaseFragment implements UserInfoView {
    ImageView user_logo;
    TextView user_nick, user_email, user_phone, user_balance;

    @Override
    public int getLayout() {
        return R.layout.fragment_user;
    }


    FloatingActionButton searchButton;

    @Override
    public void init(View view, Bundle savedInstanceState) {
        user_logo = view.findViewById(R.id.user_logo);
        user_balance = view.findViewById(R.id.user_balance);
        user_email = view.findViewById(R.id.user_email);
        user_phone = view.findViewById(R.id.user_phone);
        user_nick = view.findViewById(R.id.user_nickName);
        searchButton = view.findViewById(R.id.searchButton);

        searchButton.setOnClickListener(v -> {
            spHelper.saveToken(null);
            MyApp.token = null;
            startActivity(new Intent(this.getContext(), LoginActivity.class));
            getActivity().finish();
        });

        mPresenterImp.getUserInfo(this);
    }

    @Override
    public void GetInfoSuccess(GetUserInfo.UserBean user) {
        if (this.getActivity() == null) return;
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

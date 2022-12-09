package com.qiuchen.smartcity.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;
import com.qiuchen.smartcity.MyApp;
import com.qiuchen.smartcity.R;
import com.qiuchen.smartcity.ui.base.BaseAct;
import com.qiuchen.smartcity.ui.base.UIParams;
import com.qiuchen.smartcity.ui.base.view.LoginView;

import static com.qiuchen.smartcity.MyApp.spHelper;

public class LoginActivity extends BaseAct implements LoginView {

    @Override
    public UIParams getConfig(UIParams uiParams) {
        uiParams.layout = R.layout.activity_login;
        return uiParams;
    }

    Button login, reg;
    TextInputEditText user, pass;

    @Override
    protected void init(Bundle savedInstanceState) {
        setBlackBar();

        user = findViewById(R.id.et_user_account);
        pass = findViewById(R.id.et_user_pass);

        login = (Button) findViewById(R.id.userLogin);
        reg = (Button) findViewById(R.id.userRegister);

        login.setOnClickListener(view -> {
            mPresenterImp.login(user.getText().toString(), pass.getText().toString(), this);
        });

        reg.setOnClickListener(v -> {
            msg("注册");
        });

    }

    @Override
    public void LoginSuccess(String token) {
        spHelper.saveToken(token);
        MyApp.token = token;//第一次登录成功后强制刷新缓存
        msg("登录成功.");
        startActivity(new Intent(this, HomeActivity.class));
    }
}
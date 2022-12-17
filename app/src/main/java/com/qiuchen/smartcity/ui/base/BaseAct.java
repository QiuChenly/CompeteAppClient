package com.qiuchen.smartcity.ui.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.qiuchen.smartcity.MyApp;
import com.qiuchen.smartcity.ui.base.imp.PresenterImp;

public abstract class BaseAct extends AppCompatActivity implements BaseView {
    protected PresenterImp mPresenterImp = MyApp.userPresenter;

    @Override
    public void msg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public abstract UIParams getConfig(UIParams uiParams);

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIParams config = getConfig(new UIParams());
        setContentView(config.layout);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        //透明状态栏 需要在布局上设置fitSystemWindow = true
        // | View.SYSTEM_UI_FLAG_LAYOUT_STABLE 状态栏白色字体
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
        init(savedInstanceState);
    }

    public void setBlackBar() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    protected abstract void init(Bundle savedInstanceState);
}

package com.qiuchen.smartcity.ui.act;

import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.qiuchen.smartcity.R;
import com.qiuchen.smartcity.ui.base.BaseAct;
import com.qiuchen.smartcity.ui.base.UIParams;
import com.qiuchen.smartcity.ui.frag.HomeFragment;
import com.qiuchen.smartcity.ui.frag.NewsFragment;
import com.qiuchen.smartcity.ui.frag.SimpleFragment;
import com.qiuchen.smartcity.ui.frag.UserFragment;

public class HomeActivity extends BaseAct {
    @Override
    public UIParams getConfig(UIParams uiParams) {
        uiParams.layout = R.layout.home_activity;
        return uiParams;
    }

    FrameLayout fragment_container;
    Toolbar home_toolbar;

    BottomNavigationView home_nav;

    @Override
    protected void init(Bundle savedInstanceState) {
        setBlackBar();

        fragment_container = findViewById(R.id.fragment_container);

        home_toolbar = findViewById(R.id.home_toolbar);
        home_nav = findViewById(R.id.home_nav);

        setSupportActionBar(home_toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commitNow();
        }

        home_nav.setOnItemSelectedListener(item -> {
            getSupportActionBar().setTitle(item.getTitle());
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.nav_home: {
                    fragment = new HomeFragment();
                    break;
                }
                case R.id.nav_data: {
                    fragment = new SimpleFragment();
                    break;
                }
                case R.id.nav_news: {
                    fragment = new NewsFragment();
                    break;
                }
                case R.id.nav_center: {
                    fragment = new UserFragment();
                    break;
                }
                case R.id.nav_service: {
                    fragment = new SimpleFragment();
                    break;
                }
                default: {
                    return false;
                }
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commitNow();
            return true;
        });
    }
}

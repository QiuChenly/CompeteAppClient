package com.qiuchen.smartcity.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.LinearLayoutCompat;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.qiuchen.smartcity.R;
import com.qiuchen.smartcity.bean.response.BannerResponse;
import com.qiuchen.smartcity.ui.adapter.ViewHolder;
import com.qiuchen.smartcity.ui.base.BaseAct;
import com.qiuchen.smartcity.ui.base.UIParams;
import com.qiuchen.smartcity.ui.base.view.BannerView;
import com.qiuchen.smartcity.utils.http.Http;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import static com.qiuchen.smartcity.MyApp.spHelper;

public class MainActivity extends BaseAct implements BannerView {

    private static final String TAG = "MainActivity";
    Banner mSplashBanner;
    BannerAdapter<BannerResponse.RowsBean, ViewHolder> adapter;

    @Override
    public UIParams getConfig(UIParams uiParams) {
        uiParams.layout = R.layout.activity_main;
        return uiParams;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        // 检查是否第一次打开 不是的话直接跳到下一个页面
        if (!spHelper.isFirstOpen()) {
            goHome();
            return;
        }

        mSplashBanner = findViewById(R.id.mSplashBanner);
        mSplashBanner.addBannerLifecycleObserver(this);
        mSplashBanner.setIndicator(new CircleIndicator(this));

        adapter = new BannerAdapter<BannerResponse.RowsBean, ViewHolder>(new ArrayList<BannerResponse.RowsBean>() {{
            add(new BannerResponse.RowsBean());//如果ip地址无法访问 至少可以保证修改ip按钮能够显示在界面上
        }}) {

            @Override
            public ViewHolder onCreateHolder(ViewGroup viewGroup, int i) {
                return new ViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.splash_banner, viewGroup, false));
            }

            @Override
            public void onBindView(ViewHolder viewHolder, BannerResponse.RowsBean rowsBean, int i, int i1) {
                ImageView im = viewHolder.itemView.findViewById(R.id.image);
                LinearLayoutCompat linearLayout = viewHolder.itemView.findViewById(R.id.control_bar);
                Button net = viewHolder.itemView.findViewById(R.id.net_config);
                Button enter_home = viewHolder.itemView.findViewById(R.id.enter_home);
                Glide.with(viewHolder.itemView).load(Http.baseUrl + rowsBean.advImg).into(im);
                if (i == i1 - 1) {
                    linearLayout.setVisibility(View.VISIBLE);
                    net.setOnClickListener(view -> {
                        openDialog();
                    });
                    enter_home.setOnClickListener(v -> goHome());
                } else linearLayout.setVisibility(View.INVISIBLE);
            }
        };
        mSplashBanner.setAdapter(adapter);
        mPresenterImp.getBanner(1, this);
    }

    @Override
    public void GetBannerList(List<BannerResponse.RowsBean> rows) {
        Log.d(TAG, "GetBannerList: " + rows.size());
        adapter.setDatas(rows);
        adapter.notifyDataSetChanged();
    }

    AlertDialog dialog;

    void openDialog() {
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_netset, null, false);
        TextInputEditText ip = v.findViewById(R.id.net_ip), port = v.findViewById(R.id.net_port);
        ip.setText(spHelper.getIP(false));
        port.setText(spHelper.getIP(true));
        dialog = new AlertDialog.Builder(this).setTitle("网络设置").setView(v).setPositiveButton("确定", (dialog, which) -> {
            String p;
            try {
                p = port.getText().toString();
                if (Integer.parseInt(p) > 65535 || Integer.parseInt(p) <= 0) {
                    msg("端口范围输入错误,应为1-65535!");
                    return;
                }
            } catch (Exception e) {
                p = spHelper.getIP(true);
            }
            spHelper.saveIP(ip.getText().toString(), p);
        }).show();
    }

    void goHome() {
        if (spHelper.getToken() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
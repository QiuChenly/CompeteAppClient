package com.qiuchen.smartcity.ui.frag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qiuchen.smartcity.R;
import com.qiuchen.smartcity.ui.act.SearchActivity;
import com.qiuchen.smartcity.ui.base.BaseFragment;

public class HomeFragment extends BaseFragment {
    @Override
    public int getLayout() {
        return R.layout.fragment_home;
    }

    FloatingActionButton searchButton;

    @Override
    public void init(View view, Bundle savedInstanceState) {
        searchButton = view.findViewById(R.id.searchButton);

        searchButton.setOnClickListener(v -> {
            startActivity(new Intent(this.getContext(), SearchActivity.class));
        });
    }
}

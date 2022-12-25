package com.qiuchen.smartcity.ui.frag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qiuchen.smartcity.MyApp;
import com.qiuchen.smartcity.R;
import com.qiuchen.smartcity.bean.response.BannerResponse;
import com.qiuchen.smartcity.bean.response.GetNewsCategoryList;
import com.qiuchen.smartcity.bean.response.GetNewsResponse;
import com.qiuchen.smartcity.ui.act.SearchActivity;
import com.qiuchen.smartcity.ui.adapter.NewsContainerAdapter;
import com.qiuchen.smartcity.ui.base.BaseFragment;
import com.qiuchen.smartcity.ui.base.view.BannerView;
import com.qiuchen.smartcity.ui.base.view.NewsView;

import java.util.LinkedHashMap;
import java.util.List;

public class HomeFragment extends BaseFragment implements NewsContainerAdapter.OperateListener, NewsView, BannerView {
    @Override
    public int getLayout() {
        return R.layout.fragment_home;
    }

    FloatingActionButton searchButton;
    RecyclerView rv_home;
    NewsContainerAdapter adapter;

    @Override
    public void init(View view, Bundle savedInstanceState) {
        searchButton = view.findViewById(R.id.searchButton);
        rv_home = view.findViewById(R.id.rv_home);

        adapter = new NewsContainerAdapter();
        adapter.setListener(this);
        rv_home.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getItemViewType(position) == adapter.TYPE_EXTRA_FUNCTIONS) return 1;
                return 4;
            }
        });
        rv_home.setLayoutManager(gridLayoutManager);

        LinkedHashMap<String, Integer> functions = new LinkedHashMap<>();
        functions.put("功能1", R.drawable.ic_launcher_background);
        functions.put("功能2", R.drawable.ic_launcher_background);
        functions.put("功能3", R.drawable.ic_launcher_background);
        functions.put("功能4", R.drawable.ic_launcher_background);
        functions.put("功能5", R.drawable.ic_launcher_background);
        functions.put("功能6", R.drawable.ic_launcher_background);
        functions.put("功能7", R.drawable.ic_launcher_background);
        functions.put("功能8", R.drawable.ic_launcher_background);
        adapter.setGridShow(functions);//调用此方法实现功能设置

        mPresenterImp.getNewsCategory(this);
        mPresenterImp.getBanner(2, this);
        searchButton.setOnClickListener(v -> {
            startActivity(new Intent(this.getContext(), SearchActivity.class));
        });
    }

    @Override
    public void functionsClick(String name, int pos) {
        msg("你点击了" + name + " 位置是" + pos);
    }

    @Override
    public void LoadTargetNewsByType(int type, int selectedTabPosition) {
        mPresenterImp.getNewsByType("" + type, this);
    }

    @Override
    public void SyncTabScroll(int scrollX, int scrollY) {

    }

    @Override
    public void GoNewsDetails(int id) {
        if (this.getContext() != null) MyApp.gotoNewsDetail(this.getContext(), id);
    }

    @Override
    public void GetNewsCategoryData(List<GetNewsCategoryList.RowsBean> rows) {
        adapter.setNewsCategory(rows);
    }

    @Override
    public void GetNewsBannerData(List<GetNewsResponse.RowsBean> rows) {

    }

    @Override
    public void GetNewsByTypesListData(List<GetNewsResponse.RowsBean> rows) {
        adapter.setLst(rows);
    }

    @Override
    public void GetBannerList(List<BannerResponse.RowsBean> rows) {
        adapter.setNormalBanner(rows);
    }
}

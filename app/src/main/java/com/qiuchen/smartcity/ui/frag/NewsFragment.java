package com.qiuchen.smartcity.ui.frag;

import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.qiuchen.smartcity.R;
import com.qiuchen.smartcity.bean.response.GetNewsCategoryList;
import com.qiuchen.smartcity.bean.response.GetNewsResponse;
import com.qiuchen.smartcity.ui.adapter.NewsContainerAdapter;
import com.qiuchen.smartcity.ui.base.BaseFragment;
import com.qiuchen.smartcity.ui.base.view.NewsView;

import java.util.LinkedList;
import java.util.List;

public class NewsFragment extends BaseFragment implements NewsView, NewsContainerAdapter.OperateListener {
    RecyclerView rv_container;//是时候表演真正的绝活了

    @Override
    public int getLayout() {
        return R.layout.fragment_news;
    }

    NewsContainerAdapter adapter;

    @Override
    public void init(View view, Bundle savedInstanceState) {
        rv_container = view.findViewById(R.id.rv_news_container);
        rv_container.setLayoutManager(new LinearLayoutManager(this.getContext()));

        adapter = new NewsContainerAdapter();
        adapter.setListener(this);
        rv_container.setAdapter(adapter);

        mPresenterImp.getBannerNews(10, this);
    }

    @Override
    public void GetNewsCategory(List<GetNewsCategoryList.RowsBean> rows) {
        adapter.setNewsTypes(new LinkedList<>(rows));
    }

    @Override
    public void GetNewsBanner(List<GetNewsResponse.RowsBean> rows) {

    }

    @Override
    public void GetNewsByTypesList(List<GetNewsResponse.RowsBean> rows) {

    }

    @Override
    public void GetNewsCategory() {
        mPresenterImp.getNewsCategory(this);
    }
}

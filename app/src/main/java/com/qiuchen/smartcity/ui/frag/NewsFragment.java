package com.qiuchen.smartcity.ui.frag;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import com.qiuchen.smartcity.MyApp;
import com.qiuchen.smartcity.R;
import com.qiuchen.smartcity.bean.response.GetNewsCategoryList;
import com.qiuchen.smartcity.bean.response.GetNewsResponse;
import com.qiuchen.smartcity.ui.adapter.NewsContainerAdapter;
import com.qiuchen.smartcity.ui.base.BaseFragment;
import com.qiuchen.smartcity.ui.base.view.NewsView;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class NewsFragment extends BaseFragment implements NewsView, NewsContainerAdapter.OperateListener {
    RecyclerView rv_container;//是时候表演真正的绝活了

    @Override
    public int getLayout() {
        return R.layout.fragment_news;
    }

    NewsContainerAdapter adapter;
    LinearLayoutManager mLinearLayoutManager;
    TabLayout tablayout_news_category;

    @Override
    public void init(View view, Bundle savedInstanceState) {
        rv_container = view.findViewById(R.id.rv_news_container);
        tablayout_news_category = view.findViewById(R.id.tablayout_news_category);
        tablayout_news_category.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                adapter.syncTabSelect(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tablayout_news_category.setOnScrollChangeListener((v, x, y, ox, oy) -> {
            adapter.syncTabScroll(x, y);
        });
        mLinearLayoutManager = new LinearLayoutManager(this.getContext());
        adapter = new NewsContainerAdapter();
        adapter.setListener(this);
        rv_container.setLayoutManager(mLinearLayoutManager);
        rv_container.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int mSuspensionHeight;

            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mSuspensionHeight = tablayout_news_category.getHeight();
            }

            private static final String TAG = "NewsFragment";

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d(TAG, String.format("onScrolled: dx dy = %s %s", dx, dy));
                View v = mLinearLayoutManager.findViewByPosition(0);//找到第0个 自定义布局的项目
                if (v != null) {
                    TabLayout view = v.findViewById(R.id.tablayout_news_category);
                    int sizeChild = view.getTop();
                    int sizeParent = v.getTop();
                    Log.d(TAG, String.format("onScrolled: %s %s sizeChild %s", sizeParent + sizeChild, sizeChild, mSuspensionHeight));
                    if (sizeParent + sizeChild <= mSuspensionHeight) {// <= mSuspensionHeight
                        int top = mSuspensionHeight - (sizeParent + sizeChild);
                        if (top >= mSuspensionHeight)//如果滑动到了这个位置
                        {
                            //tablayout_news_category.setY(-top);
                            tablayout_news_category.setVisibility(View.VISIBLE);
                        } else {
                            tablayout_news_category.setVisibility(View.INVISIBLE);
                        }
                        Log.d(TAG, String.format("onScrolled Val : %s", top));
                    } else {
                        tablayout_news_category.setVisibility(View.INVISIBLE);
//                        tablayout_news_category.setY(0);
                    }
                } else {
                    tablayout_news_category.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onScrolled: View is null  calc suspend");
                }

//                if (mCurrentPosition != mLinearLayoutManager.findFirstVisibleItemPosition()) {
//                    mCurrentPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
////                    tablayout_news_category.setY(0);
////                    tablayout_news_category.setVisibility(View.VISIBLE);
//                }
            }
        });
        rv_container.setAdapter(adapter);

        mPresenterImp.getBannerNews(10, this);
    }

    @Override
    public void GetNewsCategoryData(List<GetNewsCategoryList.RowsBean> rows) {
        for (GetNewsCategoryList.RowsBean ro : rows) {
            TabLayout.Tab t = tablayout_news_category.newTab();
            t.setText(ro.name);
            t.setTag(ro.id);
            tablayout_news_category.addTab(t);
        }
        adapter.setNewsCategory(new LinkedList<>(rows));
        isLoadingCategory = false;
    }

    @Override
    public void GetNewsBannerData(List<GetNewsResponse.RowsBean> rows) {
        adapter.setNewsBanner(rows);
    }

    @Override
    public void GetNewsByTypesListData(List<GetNewsResponse.RowsBean> rows) {
        adapter.setLst(new LinkedList<>(rows));
    }

    boolean isLoadingCategory = false;

    @Override
    public void GetNewsCategory() {
        if (!isLoadingCategory) {
            isLoadingCategory = true;
            mPresenterImp.getNewsCategory(this);
        }
    }

    @Override
    public void LoadTargetNewsByType(int type) {
        mPresenterImp.getNewsByType("" + type, this);
    }

    @Override
    public void GetBanners() {
        mPresenterImp.getBannerNews(10, this);
    }

    @Override
    public void SyncTabSelect(TabLayout.Tab tab) {
        tablayout_news_category.selectTab(tab);
    }

    @Override
    public void SyncTabScroll(int scrollX, int scrollY) {
        tablayout_news_category.scrollTo(scrollX, scrollY);
    }

    @Override
    public void GoNewsDetails(int id) {
        if (this.getContext() != null)
            MyApp.gotoNewsDetail(this.getContext(), id);
    }
}

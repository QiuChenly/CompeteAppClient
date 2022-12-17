package com.qiuchen.smartcity.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.qiuchen.smartcity.R;
import com.qiuchen.smartcity.bean.response.GetNewsCategoryList;
import com.qiuchen.smartcity.bean.response.GetNewsResponse;
import com.qiuchen.smartcity.utils.http.Http;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.indicator.CircleIndicator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NewsContainerAdapter extends RecyclerView.Adapter<ViewHolder> {

    public interface OperateListener {
        void GetNewsCategory();

        void LoadTargetNewsByType(int type);

        void GetBanners();

        void SyncTabSelect(TabLayout.Tab tab);

        void SyncTabScroll(int scrollX, int scrollY);
    }

    private OperateListener listener;

    public void setListener(OperateListener ls) {
        listener = ls;
    }

    /**
     * 创建对应type的item
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType == 1 ? R.layout.rv_news_header : R.layout.news_item, parent, false);
        return new ViewHolder(v);
    }

    /**
     * 控制每一个item显示什么ui
     *
     * @param position position to query
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position + 1 <= preSize ? position + 1 : preSize + 1;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == 1) {// 顶部布局
            initBanner(holder.itemView);
        } else {
            initNewsItem(holder.itemView, position);
        }
    }

    void initNewsItem(View v, int position) {
        GetNewsResponse.RowsBean row = newsLst.get(position - preSize);//-position 是去掉header后正确数据所在的位置
        SearchNewsAdapter.bindView(v, row, view -> {

        });
    }

    private List<GetNewsCategoryList.RowsBean> types;
    private List<GetNewsResponse.RowsBean> bannerList;

    public void setNewsCategory(List<GetNewsCategoryList.RowsBean> types) {
        this.types = types;
        notifyItemChanged(0);//由于第一个项目永远是组合布局 所以直接通知第一个数据即可
    }

    public void setNewsBanner(List<GetNewsResponse.RowsBean> banner) {
        this.bannerList = banner;
        notifyItemChanged(0);//由于第一个项目永远是组合布局 所以直接通知第一个数据即可
    }

    BannerAdapter<GetNewsResponse.RowsBean, ViewHolder> bannerAdapter;

    TabLayout mTabLayout;

    void initBanner(View v) {
        Banner banner = v.findViewById(R.id.banner_new);
        mTabLayout = v.findViewById(R.id.tablayout_news_category);

        banner.setIndicator(new CircleIndicator(v.getContext()));
        if (bannerAdapter == null) {
            bannerAdapter = new BannerAdapter<GetNewsResponse.RowsBean, ViewHolder>(new LinkedList<>()) {

                @Override
                public ViewHolder onCreateHolder(ViewGroup viewGroup, int i) {
                    return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.banner_item_news_banner, viewGroup, false));
                }

                @Override
                public void onBindView(ViewHolder viewHolder, GetNewsResponse.RowsBean rowsBean, int i, int i1) {
                    ImageView im = viewHolder.itemView.findViewById(R.id.banner_news_image);
                    Glide.with(viewHolder.itemView).load(Http.baseUrl + rowsBean.cover).into(im);
                }
            };
            if (bannerList == null) listener.GetBanners();
        }
        bannerAdapter.setDatas(bannerList);
        if (banner.getAdapter() == null) banner.setAdapter(bannerAdapter);

        mTabLayout.clearOnTabSelectedListeners();
        mTabLayout.setOnScrollChangeListener((v1, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            listener.SyncTabScroll(scrollX, scrollY);
        });
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                listener.LoadTargetNewsByType((tab.getTag() == null) ? -1 : (int) tab.getTag());
                listener.SyncTabSelect(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (types == null) {
            listener.GetNewsCategory();
        } else {
            if (mTabLayout.getTabCount() == 0) {
                //continue load
                for (GetNewsCategoryList.RowsBean row : types) {
                    TabLayout.Tab t = mTabLayout.newTab();
                    t.setText(row.name);
                    t.setTag(row.id);
                    mTabLayout.addTab(t);
                }
            }
        }
    }

    public void syncTabSelect(TabLayout.Tab tab) {
        mTabLayout.selectTab(tab);
    }

    public void syncTabScroll(int x, int y) {
        mTabLayout.scrollTo(x, y);
    }

    private int preSize = 1;//预留顶部布局
    private List<GetNewsResponse.RowsBean> newsLst;

    public void setLst(List<GetNewsResponse.RowsBean> newsLst) {
        this.newsLst = newsLst;
        notifyItemRangeChanged(1, newsLst.size());
    }

    public NewsContainerAdapter(List<GetNewsResponse.RowsBean> newsLst) {
        this.newsLst = newsLst;
    }

    public NewsContainerAdapter() {
        this.newsLst = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return preSize + newsLst.size();
    }
}

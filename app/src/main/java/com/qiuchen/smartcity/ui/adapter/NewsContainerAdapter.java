package com.qiuchen.smartcity.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
    private static final String TAG = "NewsContainerAdapter";

    public interface OperateListener {
        void LoadTargetNewsByType(int type);

        void SyncTabSelect(TabLayout.Tab tab);

        void SyncTabScroll(int scrollX, int scrollY);

        /**
         * 跳转到新闻详情页
         *
         * @param id
         */
        void GoNewsDetails(int id);
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
        int layout;
        switch (viewType) {
            case 1:
                layout = R.layout.rv_news_header_banner;
                break;
            case 2:
                layout = R.layout.rv_news_header;
                break;
            default:
                layout = R.layout.news_item;
                break;
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
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
        Log.d(TAG, String.format("onBindViewHolder: 开始绑定控件事件到界面上 Type = %s", getItemViewType(position)));
        switch (getItemViewType(position)) {
            case 1://banner布局
                initBanner((Banner) holder.itemView);
                break;
            case 2://菜单导航栏布局
                initTabLayout((TabLayout) holder.itemView);
                break;
            default:
                initNewsItem(holder.itemView, position);
                break;
        }
    }

    void initNewsItem(View v, int position) {
        GetNewsResponse.RowsBean row = newsLst.get(position - preSize);//-position 是去掉header后正确数据所在的位置
        // 复用函数 反正做的事情都是一样的 就抽取出来
        SearchNewsAdapter.bindView(v, row, view -> {
            listener.GoNewsDetails(row.id);
        });
    }

    private List<GetNewsCategoryList.RowsBean> types;
    private List<GetNewsResponse.RowsBean> bannerList;

    public void setNewsCategory(List<GetNewsCategoryList.RowsBean> types) {
        this.types = types;
        notifyItemChanged(1);//由于第一个项目永远是组合布局 所以直接通知第一个数据即可
    }

    public void setNewsBanner(List<GetNewsResponse.RowsBean> banner) {
        bannerList = banner;
        bannerAdapter.setDatas(bannerList);
        Log.d(TAG, "setNewsBanner: 增加新的banner = " + bannerList.size());
        notifyItemChanged(0);//由于第一个项目永远是组合布局 所以直接通知第一个数据即可
    }

    BannerAdapter<GetNewsResponse.RowsBean, ViewHolder> bannerAdapter;

    TabLayout mTabLayout;

    void initTabLayout(TabLayout tabLayout) {
        mTabLayout = tabLayout;
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
        if (mTabLayout.getTabCount() == 0 && types != null) {
            //continue load
            for (GetNewsCategoryList.RowsBean row : types) {
                TabLayout.Tab t = mTabLayout.newTab();
                t.setText(row.name);
                t.setTag(row.id);
                mTabLayout.addTab(t);
            }
        }
    }

    void initBanner(Banner banner) {
        Log.d(TAG, "initBanner: 开始初始化banner");
        banner.setIndicator(new CircleIndicator(banner.getContext()));
        if (bannerAdapter == null) {
            bannerAdapter = new BannerAdapter<GetNewsResponse.RowsBean, ViewHolder>(new LinkedList<>()) {
                private static final String TAG = "NewsContainerAdapter";

                @Override
                public ViewHolder onCreateHolder(ViewGroup viewGroup, int i) {
                    Log.d(TAG, String.format("onCreateHolder: %s 创建banner对象", i));
                    View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.banner_item_news_banner, viewGroup, false);
                    return new ViewHolder(v);
                }

                @Override
                public void onBindView(ViewHolder viewHolder, GetNewsResponse.RowsBean rowsBean, int i, int i1) {
                    Log.d(TAG, String.format("onBindView: 绑定子banner页面 %s", i));
                    ImageView im = viewHolder.itemView.findViewById(R.id.banner_news_image);
                    TextView news_title = viewHolder.itemView.findViewById(R.id.news_title);

                    CardView banner_items = viewHolder.itemView.findViewById(R.id.banner_items);
                    banner_items.setOnClickListener(view -> {
                        listener.GoNewsDetails(rowsBean.id);
                    });

                    news_title.setText(rowsBean.title);
                    Glide.with(viewHolder.itemView)
                            .load(Http.baseUrl + rowsBean.cover)
                            .into(im);
                }
            };
        }
        if (banner.getAdapter() == null) banner.setAdapter(bannerAdapter);
    }

    public void syncTabSelect(TabLayout.Tab tab) {
        mTabLayout.selectTab(tab);
    }

    public void syncTabScroll(int x, int y) {
        mTabLayout.scrollTo(x, y);
    }

    private final int preSize = 2;//预留顶部布局
    private List<GetNewsResponse.RowsBean> newsLst;

    public void setLst(List<GetNewsResponse.RowsBean> newsLst) {
        this.newsLst = newsLst;
        notifyItemRangeChanged(1, newsLst.size());
    }

    public NewsContainerAdapter() {
        this.newsLst = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return preSize + newsLst.size();
    }
}

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
import com.qiuchen.smartcity.bean.response.BannerResponse;
import com.qiuchen.smartcity.bean.response.GetNewsCategoryList;
import com.qiuchen.smartcity.bean.response.GetNewsResponse;
import com.qiuchen.smartcity.utils.http.Http;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.indicator.CircleIndicator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class NewsContainerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final String TAG = "NewsContainerAdapter";

    public interface OperateListener {
        void LoadTargetNewsByType(int type, int selectedTabPosition);

        void SyncTabScroll(int scrollX, int scrollY);

        /**
         * 跳转到新闻详情页
         *
         * @param id
         */
        void GoNewsDetails(int id);

        void functionsClick(String name, int pos);
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
            case TYPE_BANNER:
                layout = R.layout.rv_news_header_banner;
                break;
            case TYPE_NEWS_ITEM:
                layout = R.layout.news_item;
                break;
            case TYPE_TAB_LAYOUT:
                layout = R.layout.rv_news_header;
                break;
            case TYPE_EXTRA_FUNCTIONS:
                layout = R.layout.rv_news_header_grid;
                break;
            default:
                layout = R.layout.news_item;
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    private static final int TYPE_BANNER = 1;
    private static final int TYPE_TAB_LAYOUT = 2;
    private static final int TYPE_NEWS_ITEM = 3;
    public static final int TYPE_EXTRA_FUNCTIONS = 4;

    /**
     * 控制每一个item显示什么ui
     *
     * @param position position to query
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        } else {
            //当设置额外方法时
            if (functions != null && position <= functions.size()) {
                return TYPE_EXTRA_FUNCTIONS;
            }
            if (position == preSize - 1) return TYPE_TAB_LAYOUT;
            return TYPE_NEWS_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d(TAG, String.format("onBindViewHolder: 开始绑定控件事件到界面上 Type = %s", getItemViewType(position)));
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_BANNER: {
                initBanner((Banner) holder.itemView);
                break;
            }
            case TYPE_TAB_LAYOUT: {
                initTabLayout((TabLayout) holder.itemView);
                break;
            }
            case TYPE_NEWS_ITEM: {
                initNewsItem(holder.itemView, position);
                break;
            }
            case TYPE_EXTRA_FUNCTIONS: {
                initExtraFunctions(holder.itemView, position);
                break;
            }
        }
    }

    void initExtraFunctions(View view, int pos) {
        ImageView im = view.findViewById(R.id.func_img);
        TextView tv = view.findViewById(R.id.func_name);

        String name = (String) functions.keySet().toArray()[pos - 1];
        tv.setText(name);
        im.setImageResource(functions.get(name));

        view.setOnClickListener(v -> {
            listener.functionsClick(name, pos);
        });
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
    private List<BannerResponse.RowsBean> normalBanner;

    public void setNewsCategory(List<GetNewsCategoryList.RowsBean> types) {
        this.types = types;
        notifyItemChanged(preSize - 1);//由于第一个项目永远是组合布局 所以直接通知第一个数据即可
    }

    public void setNewsBanner(List<GetNewsResponse.RowsBean> banner) {
        bannerList = banner;
        bannerAdapter.setDatas(bannerList);
        Log.d(TAG, "setNewsBanner: 增加新的banner = " + bannerList.size());
        notifyItemChanged(0);//由于第一个项目永远是组合布局 所以直接通知第一个数据即可
    }

    public void setNormalBanner(List<BannerResponse.RowsBean> rows) {
        this.normalBanner = rows;
        normalBannerAdapter.setDatas(normalBanner);
        notifyItemChanged(0);
    }

    BannerAdapter<GetNewsResponse.RowsBean, ViewHolder> bannerAdapter;
    BannerAdapter<BannerResponse.RowsBean, ViewHolder> normalBannerAdapter;

    TabLayout mTabLayout;
    TabLayout.OnTabSelectedListener tabListener;

    void initTabLayout(TabLayout tabLayout) {
        mTabLayout = tabLayout;
        mTabLayout.setOnScrollChangeListener((v1, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            listener.SyncTabScroll(scrollX, scrollY);
        });

        if (tabListener == null) tabListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                listener.LoadTargetNewsByType((tab.getTag() == null) ? -1 : (int) tab.getTag(), mTabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
        mTabLayout.addOnTabSelectedListener(tabListener);//这里内部会使用instanceof判断是否为同一个Listener对象 所以重复add不会造成添加多个事件 因为tabListener只实例化了一次
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
        //下面的代码写的真的是垃圾代码 目前只需要能实现 后期有闲功夫再优化吧
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
                    bannerViewUpdate(viewHolder.itemView, rowsBean, null);
                }
            };
        }
        if (normalBannerAdapter == null) {
            normalBannerAdapter = new BannerAdapter<BannerResponse.RowsBean, ViewHolder>(new LinkedList<>()) {
                @Override
                public ViewHolder onCreateHolder(ViewGroup viewGroup, int i) {
                    View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.banner_item_news_banner, viewGroup, false);
                    return new ViewHolder(v);
                }

                @Override
                public void onBindView(ViewHolder viewHolder, BannerResponse.RowsBean rowsBean, int i, int i1) {
                    bannerViewUpdate(viewHolder.itemView, null, rowsBean);
                }
            };
        }
        if (banner.getAdapter() == null) {
            banner.setAdapter(normalBanner == null ? bannerAdapter : normalBannerAdapter);
        }
    }

    void bannerViewUpdate(View itemView, GetNewsResponse.RowsBean rowsBean, BannerResponse.RowsBean rowsBean2) {
        ImageView im = itemView.findViewById(R.id.banner_news_image);
        TextView news_title = itemView.findViewById(R.id.news_title);
        CardView banner_items = itemView.findViewById(R.id.banner_items);

        int id = rowsBean == null ? rowsBean2.targetId : rowsBean.id;
        String title = rowsBean == null ? rowsBean2.advTitle : rowsBean.title;
        String image = rowsBean == null ? rowsBean2.advImg : rowsBean.cover;

        banner_items.setOnClickListener(view -> {
            listener.GoNewsDetails(id);
        });

        news_title.setText(title);
        Glide.with(itemView).load(Http.baseUrl + image).into(im);
    }

    public void syncTabSelect(int tab) {
        if (mTabLayout.getSelectedTabPosition() == tab) return;
        TabLayout.Tab t = mTabLayout.getTabAt(tab);
        if (t != null) {
            t.select();
        }
    }

    LinkedHashMap<String, Integer> functions;

    public void setGridShow(LinkedHashMap<String, Integer> functions) {
        this.functions = functions;
        preSize += functions.size();
        // banner grid tablayout itemview
        //notifyDataSetChanged();//强制重绘列表
    }

    public void syncTabScroll(int x, int y) {
        mTabLayout.scrollTo(x, y);
    }

    private int preSize = 2;//预留顶部布局
    private List<GetNewsResponse.RowsBean> newsLst;

    public void setLst(List<GetNewsResponse.RowsBean> newsLst) {
        this.newsLst = newsLst;
        notifyItemRangeChanged(preSize, newsLst.size());//这里预留了两个槽 所以要从 0 1 后面的 2 开始 否则会导致界面重画 bug
    }

    public NewsContainerAdapter() {
        this.newsLst = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return preSize + newsLst.size();
    }
}

package com.qiuchen.smartcity.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import com.qiuchen.smartcity.R;
import com.qiuchen.smartcity.bean.response.GetNewsCategoryList;
import com.qiuchen.smartcity.bean.response.GetNewsResponse;
import com.youth.banner.Banner;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class NewsContainerAdapter extends RecyclerView.Adapter<ViewHolder> {

    public interface OperateListener {
        void GetNewsCategory();
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
        View v = LayoutInflater.from(parent.getContext()).inflate(
                viewType == 1 ? R.layout.rv_news_header :
                        R.layout.news_item
                , parent, false);
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
        switch (type) {
            case 1: {
                // 顶部布局
                initBanner(holder.itemView);
            }

            default: {

            }
        }
    }


    private LinkedList<GetNewsCategoryList.RowsBean> types;

    public void setNewsTypes(LinkedList<GetNewsCategoryList.RowsBean> types) {
        this.types = types;
        notifyItemChanged(0);//由于第一个项目永远是组合布局 所以直接通知第一个数据即可
    }

    void initBanner(View v) {
        Banner banner = v.findViewById(R.id.banner_new);
        TabLayout tab = v.findViewById(R.id.tablayout_news_category);

        if (types == null) {
            if (listener != null) listener.GetNewsCategory();
        } else {
            //continue load
            for (GetNewsCategoryList.RowsBean row : types) {
                tab.addTab(new TabLayout.Tab() {{
                    setText(row.name);
                }});
                tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        Toast.makeText(tab.parent.getContext(), tab.getText(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }
        }
    }

    private int preSize = 1;//预留顶部布局
    private LinkedList<GetNewsResponse.RowsBean> newsLst;

    public void setLst(LinkedList<GetNewsResponse.RowsBean> newsLst) {
        this.newsLst = newsLst;
        notifyAll();//通知所有数据发生改变了 刷新UI
    }

    public NewsContainerAdapter(LinkedList<GetNewsResponse.RowsBean> newsLst) {
        this.newsLst = newsLst;
    }

    public NewsContainerAdapter() {
        this.newsLst = new LinkedList<>();
    }

    @Override
    public int getItemCount() {
        return preSize + newsLst.size();
    }
}

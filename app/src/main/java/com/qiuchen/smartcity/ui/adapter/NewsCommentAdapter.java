package com.qiuchen.smartcity.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.qiuchen.smartcity.R;
import com.qiuchen.smartcity.bean.response.GetNewsCommentList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NewsCommentAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<GetNewsCommentList.RowsBean> lst = new ArrayList<>();

    public void setLst(List<GetNewsCommentList.RowsBean> lst) {
        this.lst = lst;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_comment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        bindView(holder.itemView, lst.get(position));
    }

    private void bindView(View itemView, GetNewsCommentList.RowsBean rowsBean) {
        TextView c_username = itemView.findViewById(R.id.c_username);
        TextView c_content = itemView.findViewById(R.id.c_content);
        TextView c_likenum = itemView.findViewById(R.id.c_likenum);
        c_likenum.setText("赞 " + rowsBean.likeNum);
        c_username.setText(rowsBean.userName);
        c_content.setText(rowsBean.content);
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }
}

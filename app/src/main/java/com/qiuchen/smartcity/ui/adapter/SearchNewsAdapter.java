package com.qiuchen.smartcity.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.qiuchen.smartcity.R;
import com.qiuchen.smartcity.bean.response.GetNewsResponse;
import com.qiuchen.smartcity.utils.http.Http;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchNewsAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final ItemClick itemClick;

    public interface ItemClick {
        void GoDetails(int newsId);
    }

    private List<GetNewsResponse.RowsBean> lst = new ArrayList<>();

    public void setLst(List<GetNewsResponse.RowsBean> lst) {
        this.lst = lst;
    }

    public SearchNewsAdapter(ItemClick click) {
        this.itemClick = click;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        bindView(holder.itemView, lst.get(position), view -> {
            itemClick.GoDetails(lst.get(position).id);
        });
    }

    public static void bindView(View v, GetNewsResponse.RowsBean data, View.OnClickListener clickListener) {
        ImageView im = v.findViewById(R.id.item_img);
        TextView trdView = v.findViewById(R.id.trd_title);
        TextView new_title = v.findViewById(R.id.new_title);
        TextView new_subtitle = v.findViewById(R.id.new_subtitle);

        trdView.setText(String.format("%s · 评论: %s 点赞: %s 阅读: %s", data.publishDate, data.commentNum, data.likeNum, data.readNum));
        new_title.setText(data.title);
        new_subtitle.setText(data.subTitle);

        v.setOnClickListener(clickListener);

        Glide.with(v)
                .load(Http.baseUrl + data.cover)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(im);
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }
}

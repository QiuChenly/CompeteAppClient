package com.qiuchen.smartcity.ui.act;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.qiuchen.smartcity.R;
import com.qiuchen.smartcity.bean.request.SubmitNews;
import com.qiuchen.smartcity.bean.response.GetNewsCommentList;
import com.qiuchen.smartcity.bean.response.GetNewsDetails;
import com.qiuchen.smartcity.ui.adapter.NewsCommentAdapter;
import com.qiuchen.smartcity.ui.base.BaseAct;
import com.qiuchen.smartcity.ui.base.UIParams;
import com.qiuchen.smartcity.ui.base.view.NewsDetailsView;
import com.qiuchen.smartcity.utils.http.Http;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NewsDetails extends BaseAct implements NewsDetailsView {
    @Override
    public UIParams getConfig(UIParams uiParams) {
        uiParams.layout = R.layout.news_detail;
        return uiParams;
    }

    TextView news_content;
    Toolbar news_detail_toolbar;

    RecyclerView news_comment;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();//关闭页面返回上一级
        return super.onOptionsItemSelected(item);
    }

    NewsCommentAdapter adapter;
    Button publish_comment;

    AppCompatEditText comment_me;

    int newsIds = -1;

    @Override
    protected void init(Bundle savedInstanceState) {
        newsIds = getIntent().getIntExtra("newsId", -1);
        if (newsIds == -1) return;

        getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500));

        news_content = findViewById(R.id.news_content);
        news_comment = findViewById(R.id.news_comment);
        comment_me = findViewById(R.id.comment_me);
        publish_comment = findViewById(R.id.publish_comment);
        news_comment.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NewsCommentAdapter();
        news_comment.setAdapter(adapter);

        news_detail_toolbar = findViewById(R.id.news_detail_toolbar);
        setSupportActionBar(news_detail_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mPresenterImp.getNewsDetails(newsIds, this);
        mPresenterImp.getComments(newsIds, this);

        publish_comment.setOnClickListener(v -> {
            if (comment_me.getText().toString().isEmpty()) {
                comment_me.setError("请输入评论内容！");
                return;
            }
            mPresenterImp.comment(new SubmitNews() {{
                this.newsId = newsIds;
                this.content = comment_me.getText().toString();
            }}, this);
        });
    }

    @Override
    public void getNew(GetNewsDetails.DataBean body) {
        getSupportActionBar().setTitle(body.title);
        getSupportActionBar().setSubtitle(String.format("阅读 %s · 评论 %s", body.readNum, body.commentNum));
        news_content.setText(Html.fromHtml(body.content, source -> {
            final LevelListDrawable mDrawable = new LevelListDrawable();//这是一个Drawable集合类 根据level的不同可以显示不同的drawable对象
            Glide.with(news_content)
                    .asDrawable()
                    .load(Http.baseUrl + source)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull @NotNull Drawable drawable, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Drawable> transition) {
                            mDrawable.addLevel(1, 1, drawable);
                            mDrawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                            mDrawable.setLevel(1);
                            //首先添加级别为1的位图
                            //然后设置位图的上下左右边框
                            //最后设置级别为1的位图显示到界面上
                            news_content.invalidate();//重新绘制并设置字符串让他重新初始化
                            news_content.setText(news_content.getText());
                        }
                    });
            return mDrawable;//让界面先持有绘图对象 然后当我再Glide里面加载的时候他会自动显示出来
        }, null));
    }

    @Override
    public void getCommitList(List<GetNewsCommentList.RowsBean> rows) {
        adapter.setLst(rows);
        adapter.notifyItemRangeInserted(0, rows.size());//告诉列表控件需要显示新的数据
    }

    @Override
    public void commitSuccess() {
        msg("评论成功.");
        comment_me.clearFocus();
        comment_me.setText("");
        mPresenterImp.getComments(newsIds, this);
    }
}

package com.qiuchen.smartcity.ui.act;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputEditText;
import com.qiuchen.smartcity.R;
import com.qiuchen.smartcity.bean.request.SubmitNews;
import com.qiuchen.smartcity.bean.response.GetNewsCommentList;
import com.qiuchen.smartcity.bean.response.GetNewsDetails;
import com.qiuchen.smartcity.ui.adapter.NewsCommentAdapter;
import com.qiuchen.smartcity.ui.base.BaseAct;
import com.qiuchen.smartcity.ui.base.UIParams;
import com.qiuchen.smartcity.ui.base.view.NewsDetailsView;
import com.qiuchen.smartcity.utils.http.Http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashMap;
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

    TextInputEditText comment_me;

    int newsIds = -1;

    @Override
    protected void init(Bundle savedInstanceState) {
        newsIds = getIntent().getIntExtra("newsId", -1);
        if (newsIds == -1) return;

        getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500));

        news_content = findViewById(R.id.news_content);
        news_comment = findViewById(R.id.news_comment);
        comment_me = (TextInputEditText) findViewById(R.id.comment_me);
        publish_comment = (Button) findViewById(R.id.publish_comment);
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
            if (lst.get(source) == null) {
                new Thread(() -> {
                    try {
                        Drawable d = Drawable.createFromStream((InputStream) new URL(Http.baseUrl + source).getContent(), source);
                        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                        lst.put(source, d);

                        runOnUiThread(() -> {
                            getNew(body);//重新设置内容
                        });
                    } catch (IOException e) {
                    }
                }).start();
                return null;
            } else return lst.get(source);
        }, null));
    }

    @Override
    public void getCommitList(List<GetNewsCommentList.RowsBean> rows) {
        adapter.setLst(rows);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void commitSuccess() {
        msg("评论成功.");
        comment_me.clearFocus();
        comment_me.setText("");
        mPresenterImp.getComments(newsIds, this);
    }

    LinkedHashMap<String, Drawable> lst = new LinkedHashMap<>();
}

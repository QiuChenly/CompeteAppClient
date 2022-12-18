package com.qiuchen.smartcity.ui.act;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.qiuchen.smartcity.MyApp;
import com.qiuchen.smartcity.R;
import com.qiuchen.smartcity.bean.response.GetNewsResponse;
import com.qiuchen.smartcity.ui.adapter.SearchNewsAdapter;
import com.qiuchen.smartcity.ui.base.BaseAct;
import com.qiuchen.smartcity.ui.base.UIParams;
import com.qiuchen.smartcity.ui.base.view.SearchView;

import java.util.List;

public class SearchActivity extends BaseAct implements SearchView {
    @Override
    public UIParams getConfig(UIParams uiParams) {
        uiParams.layout = R.layout.search_activity;
        return uiParams;
    }

    AppCompatEditText search_content;
    Toolbar search_toolbar;
    TextView search_state;
    RecyclerView search_list;

    SearchNewsAdapter adapter;

    @Override
    protected void init(Bundle savedInstanceState) {
//        setBlackBar();
        getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500));

        handler = new Handler(getMainLooper());

        search_toolbar = findViewById(R.id.search_toolbar);
        search_state = findViewById(R.id.search_state);
        search_list = findViewById(R.id.search_list);

        setSupportActionBar(search_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        search_content = findViewById(R.id.search_content);
//        search_content.requestFocus();

        //初始化列表
        search_list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchNewsAdapter(id -> {
            MyApp.gotoNewsDetail(this, id);
        });
        search_list.setAdapter(adapter);

        search_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //search news here
                searchNews(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 0) search_state.setText("搜索历史");
                else search_state.setText("搜索结果");
            }
        });

        search_content.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String text = search_content.getEditableText().toString();
                searchNews(text);
            }
            return false;
        });
    }

    Handler handler;
    Runnable runnable = () -> {
    };

    /**
     * 实现延时800毫秒搜索提升用户体验
     *
     * @param key
     */
    private void searchNews(String key) {
        handler.removeCallbacks(runnable);//800秒内触发操作可以取消上一次的任务
        runnable = () -> mPresenterImp.searchNews(key, this);//加入新的搜索任务
        handler.postDelayed(runnable, 800);//等待800毫秒
        //切记一定要在onCreate里面初始化Handler
    }

    @Override
    public void GetNews(List<GetNewsResponse.RowsBean> rows) {
        adapter.setLst(rows);
        adapter.notifyDataSetChanged();
    }
}

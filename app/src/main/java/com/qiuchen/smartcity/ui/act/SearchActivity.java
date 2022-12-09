package com.qiuchen.smartcity.ui.act;

import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import com.qiuchen.smartcity.R;
import com.qiuchen.smartcity.ui.base.BaseAct;
import com.qiuchen.smartcity.ui.base.UIParams;
import com.qiuchen.smartcity.ui.base.view.SearchView;

public class SearchActivity extends BaseAct implements SearchView {
    @Override
    public UIParams getConfig(UIParams uiParams) {
        uiParams.layout = R.layout.search_activity;
        return uiParams;
    }

    TextInputEditText search_content;

    @Override
    protected void init(Bundle savedInstanceState) {
        setBlackBar();

        search_content = findViewById(R.id.search_content);
        search_content.requestFocus();
    }
}

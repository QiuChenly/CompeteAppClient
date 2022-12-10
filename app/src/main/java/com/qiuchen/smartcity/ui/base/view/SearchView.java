package com.qiuchen.smartcity.ui.base.view;

import com.qiuchen.smartcity.bean.response.GetNewsResponse;
import com.qiuchen.smartcity.ui.base.BaseView;

import java.util.List;

public interface SearchView extends BaseView {

    void GetNews(List<GetNewsResponse.RowsBean> rows);
}

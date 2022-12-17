package com.qiuchen.smartcity.ui.base.view;

import com.qiuchen.smartcity.bean.response.GetNewsCategoryList;
import com.qiuchen.smartcity.bean.response.GetNewsResponse;
import com.qiuchen.smartcity.ui.base.BaseView;

import java.util.List;

public interface NewsView extends BaseView {

    void GetNewsCategory(List<GetNewsCategoryList.RowsBean> rows);

    void GetNewsBanner(List<GetNewsResponse.RowsBean> rows);

    void GetNewsByTypesList(List<GetNewsResponse.RowsBean> rows);
}

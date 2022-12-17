package com.qiuchen.smartcity.ui.base.view;

import com.qiuchen.smartcity.bean.response.GetNewsCategoryList;
import com.qiuchen.smartcity.bean.response.GetNewsResponse;
import com.qiuchen.smartcity.ui.base.BaseView;

import java.util.List;

public interface NewsView extends BaseView {

    void GetNewsCategoryData(List<GetNewsCategoryList.RowsBean> rows);

    void GetNewsBannerData(List<GetNewsResponse.RowsBean> rows);

    void GetNewsByTypesListData(List<GetNewsResponse.RowsBean> rows);
}

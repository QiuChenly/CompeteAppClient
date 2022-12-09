package com.qiuchen.smartcity.ui.base.view;

import com.qiuchen.smartcity.bean.response.BannerResponse;
import com.qiuchen.smartcity.ui.base.BaseView;

import java.util.List;

public interface BannerView extends BaseView {
    void GetBannerList(List<BannerResponse.RowsBean> rows);
}

package com.qiuchen.smartcity.ui.base.view;

import com.qiuchen.smartcity.bean.response.GetNewsCommentList;
import com.qiuchen.smartcity.bean.response.GetNewsDetails;
import com.qiuchen.smartcity.ui.base.BaseView;

import java.util.List;

public interface NewsDetailsView extends BaseView {

    void getNew(GetNewsDetails.DataBean body);

    void getCommitList(List<GetNewsCommentList.RowsBean> rows);

    void commitSuccess();
}

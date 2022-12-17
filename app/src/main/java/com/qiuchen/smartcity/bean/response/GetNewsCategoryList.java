package com.qiuchen.smartcity.bean.response;

import com.qiuchen.smartcity.bean.BaseResponse;

import java.util.List;

public class GetNewsCategoryList extends BaseResponse {

    /**
     * rows : [{"appType":"living","commentDate":"2022-12-10 17:01:24","content":"新闻评论测试","id":1,"likeNum":0,"newsId":1,"newsTitle":"测试新闻1标题","userId":4,"userName":"David"}]
     * total : 1
     */

    public int total;
    public List<RowsBean> rows;

    public static class RowsBean {
        public String appType;
        public int id;
        public int sort;
        public String name;
    }
}

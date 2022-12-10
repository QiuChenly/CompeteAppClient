package com.qiuchen.smartcity.bean.response;

import com.qiuchen.smartcity.bean.BaseResponse;

import java.util.List;

public class GetNewsCommentList extends BaseResponse {

    /**
     * rows : [{"appType":"living","commentDate":"2022-12-10 17:01:24","content":"新闻评论测试","id":1,"likeNum":0,"newsId":1,"newsTitle":"测试新闻1标题","userId":4,"userName":"David"}]
     * total : 1
     */

    public int total;
    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * appType : living
         * commentDate : 2022-12-10 17:01:24
         * content : 新闻评论测试
         * id : 1
         * likeNum : 0
         * newsId : 1
         * newsTitle : 测试新闻1标题
         * userId : 4
         * userName : David
         */

        public String appType;
        public String commentDate;
        public String content;
        public int id;
        public int likeNum;
        public int newsId;
        public String newsTitle;
        public int userId;
        public String userName;
    }
}

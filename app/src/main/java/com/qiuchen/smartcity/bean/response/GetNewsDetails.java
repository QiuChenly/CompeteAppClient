package com.qiuchen.smartcity.bean.response;

import com.qiuchen.smartcity.bean.BaseResponse;

public class GetNewsDetails extends BaseResponse {

    /**
     * data : {"appType":"movie","commentNum":0,"content":"<p>内容<img src=\"/010-h-1.jpg\"><\/p>","cover":"/010-h-1.jpg","hot":"N","id":1,"likeNum":0,"publishDate":"2022-12-10","readNum":0,"status":"Y","subTitle":"测试新闻1子标题","tags":"null","title":"测试新闻1标题","top":"N","type":1}
     */
    public DataBean data;

    public static class DataBean {
        /**
         * appType : movie
         * commentNum : 0
         * content : <p>内容<img src="/010-h-1.jpg"></p>
         * cover : /010-h-1.jpg
         * hot : N
         * id : 1
         * likeNum : 0
         * publishDate : 2022-12-10
         * readNum : 0
         * status : Y
         * subTitle : 测试新闻1子标题
         * tags : null
         * title : 测试新闻1标题
         * top : N
         * type : 1
         */

        public String appType;
        public int commentNum;
        public String content;
        public String cover;
        public String hot;
        public int id;
        public int likeNum;
        public String publishDate;
        public int readNum;
        public String status;
        public String subTitle;
        public String tags;
        public String title;
        public String top;
        public int type;
    }
}

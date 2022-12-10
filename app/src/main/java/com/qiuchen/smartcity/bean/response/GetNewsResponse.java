package com.qiuchen.smartcity.bean.response;

import com.qiuchen.smartcity.bean.BaseResponse;

import java.util.List;

public class GetNewsResponse extends BaseResponse {

    /**
     * code : 200
     * msg : 查询成功
     * rows : [{"commentNum":0,"content":"<p>内容<img src=\"/010-h-1.jpg\"><\/p>","cover":"/010-h-1.jpg","hot":"N","id":100,"likeNum":0,"publishDate":"2022-12-10","readNum":0,"status":"Y","subTitle":"测试新闻100子标题","tags":"","title":"测试新闻100标题","top":"N","type":1},{"commentNum":0,"content":"<p>内容<img src=\"/010-h-1.jpg\"><\/p>","cover":"/010-h-1.jpg","hot":"N","id":200,"likeNum":0,"publishDate":"2022-12-10","readNum":0,"status":"Y","subTitle":"测试新闻100子标题","tags":"","title":"测试新闻100标题","top":"N","type":2},{"commentNum":0,"content":"<p>内容<img src=\"/010-h-1.jpg\"><\/p>","cover":"/010-h-1.jpg","hot":"N","id":300,"likeNum":0,"publishDate":"2022-12-10","readNum":0,"status":"Y","subTitle":"测试新闻100子标题","tags":"","title":"测试新闻100标题","top":"N","type":3},{"commentNum":0,"content":"<p>内容<img src=\"/010-h-1.jpg\"><\/p>","cover":"/010-h-1.jpg","hot":"N","id":400,"likeNum":0,"publishDate":"2022-12-10","readNum":0,"status":"Y","subTitle":"测试新闻100子标题","tags":"","title":"测试新闻100标题","top":"N","type":4},{"commentNum":0,"content":"<p>内容<img src=\"/010-h-1.jpg\"><\/p>","cover":"/010-h-1.jpg","hot":"N","id":500,"likeNum":0,"publishDate":"2022-12-10","readNum":0,"status":"Y","subTitle":"测试新闻100子标题","tags":"","title":"测试新闻100标题","top":"N","type":5},{"commentNum":0,"content":"<p>内容<img src=\"/010-h-1.jpg\"><\/p>","cover":"/010-h-1.jpg","hot":"N","id":600,"likeNum":0,"publishDate":"2022-12-10","readNum":0,"status":"Y","subTitle":"测试新闻100子标题","tags":"","title":"测试新闻100标题","top":"N","type":6},{"commentNum":0,"content":"<p>内容<img src=\"/010-h-1.jpg\"><\/p>","cover":"/010-h-1.jpg","hot":"N","id":700,"likeNum":0,"publishDate":"2022-12-10","readNum":0,"status":"Y","subTitle":"测试新闻100子标题","tags":"","title":"测试新闻100标题","top":"N","type":7},{"commentNum":0,"content":"<p>内容<img src=\"/010-h-1.jpg\"><\/p>","cover":"/010-h-1.jpg","hot":"N","id":800,"likeNum":0,"publishDate":"2022-12-10","readNum":0,"status":"Y","subTitle":"测试新闻100子标题","tags":"","title":"测试新闻100标题","top":"N","type":8}]
     * total : 8
     */

    public int total;
    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * commentNum : 0
         * content : <p>内容<img src="/010-h-1.jpg"></p>
         * cover : /010-h-1.jpg
         * hot : N
         * id : 100
         * likeNum : 0
         * publishDate : 2022-12-10
         * readNum : 0
         * status : Y
         * subTitle : 测试新闻100子标题
         * tags :
         * title : 测试新闻100标题
         * top : N
         * type : 1
         */

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

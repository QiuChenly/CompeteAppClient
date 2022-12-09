package com.qiuchen.smartcity.bean.response;

import com.qiuchen.smartcity.bean.BaseResponse;

import java.util.List;

public class BannerResponse extends BaseResponse {

    /**
     * rows : [{"advImg":"/010-1.jpg","advTitle":"测试引导页轮播1","id":3,"servModule":"引导页轮播","sort":1,"targetId":1,"type":1},{"advImg":"/010-2.jpg","advTitle":"测试引导页轮播2","id":6,"servModule":"引导页轮播","sort":2,"targetId":2,"type":1},{"advImg":"/010-3.jpg","advTitle":"测试引导页轮播3","id":9,"servModule":"引导页轮播","sort":3,"targetId":3,"type":1},{"advImg":"/010-4.jpg","advTitle":"测试引导页轮播4","id":12,"servModule":"引导页轮播","sort":4,"targetId":4,"type":1},{"advImg":"/010-5.jpg","advTitle":"测试引导页轮播5","id":15,"servModule":"引导页轮播","sort":5,"targetId":5,"type":1}]
     * total : 10
     */

    public int total;
    public List<RowsBean> rows;

    public static class RowsBean {
        /**
         * advImg : /010-1.jpg
         * advTitle : 测试引导页轮播1
         * id : 3
         * servModule : 引导页轮播
         * sort : 1
         * targetId : 1
         * type : 1
         */

        public String advImg;
        public String advTitle;
        public int id;
        public String servModule;
        public int sort;
        public int targetId;
        public int type;
    }
}

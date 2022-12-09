package com.qiuchen.smartcity.bean.response;

import com.qiuchen.smartcity.bean.BaseResponse;

public class GetUserInfo extends BaseResponse {

    /**
     * user : {"avatar":"/010-h-1.jpg","balance":0,"email":"David@163.com","idCard":"210113199808242137","nickName":"大卫","phonenumber":"15840669812","score":0,"sex":0,"userId":4,"userName":"David"}
     * code : 200
     * msg : 操作成功
     */

    public UserBean user;

    public static class UserBean {
        /**
         * avatar : /010-h-1.jpg
         * balance : 0
         * email : David@163.com
         * idCard : 210113199808242137
         * nickName : 大卫
         * phonenumber : 15840669812
         * score : 0
         * sex : 0
         * userId : 4
         * userName : David
         */

        public String avatar;
        public int balance;
        public String email;
        public String idCard;
        public String nickName;
        public String phonenumber;
        public int score;
        public int sex;
        public int userId;
        public String userName;
    }
}

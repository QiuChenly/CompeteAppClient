package com.qiuchen.smartcity.utils.http;

import com.qiuchen.smartcity.bean.request.LoginBean;
import com.qiuchen.smartcity.bean.response.BannerResponse;
import com.qiuchen.smartcity.bean.response.GetUserInfo;
import com.qiuchen.smartcity.bean.response.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @GET("/prod-api/api/rotation/list?pageNum=1&pageSize=5")
    Call<BannerResponse> getBanner(@Query("type") int type);

    @POST("/prod-api/api/login")
    Call<LoginResponse> login(@Body LoginBean login);

    @GET("/prod-api/api/common/user/getInfo")
    Call<GetUserInfo> getUserInfo();
}

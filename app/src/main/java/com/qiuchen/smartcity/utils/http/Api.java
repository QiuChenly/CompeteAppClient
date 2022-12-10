package com.qiuchen.smartcity.utils.http;

import com.qiuchen.smartcity.bean.BaseResponse;
import com.qiuchen.smartcity.bean.request.LoginBean;
import com.qiuchen.smartcity.bean.request.SubmitNews;
import com.qiuchen.smartcity.bean.response.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface Api {
    @GET("/prod-api/api/rotation/list?pageNum=1&pageSize=5")
    Call<BannerResponse> getBanner(@Query("type") int type);

    @POST("/prod-api/api/login")
    Call<LoginResponse> login(@Body LoginBean login);

    @GET("/prod-api/api/common/user/getInfo")
    Call<GetUserInfo> getUserInfo();

    @GET("/prod-api/press/press/list?hot=&publishDate=&top=")
    Call<GetNewsResponse> getNews(@Query("title") String title, @Query("type") String type);

    @GET("/prod-api/press/press/{newsId}")
    Call<GetNewsDetails> getNewsDetails(@Path("newsId") int newsId);

    @GET("/prod-api/press/comments/list?commentDate=&userId=")
    Call<GetNewsCommentList> getNewsComment(@Query("newsId") int newsId);

    @POST("/prod-api/press/pressComment")
    Call<BaseResponse> submitCommit(@Body SubmitNews news);
}

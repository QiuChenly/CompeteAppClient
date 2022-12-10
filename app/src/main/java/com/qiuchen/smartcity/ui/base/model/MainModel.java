package com.qiuchen.smartcity.ui.base.model;

import com.qiuchen.smartcity.bean.BaseResponse;
import com.qiuchen.smartcity.bean.request.LoginBean;
import com.qiuchen.smartcity.bean.request.SubmitNews;
import com.qiuchen.smartcity.bean.response.*;
import com.qiuchen.smartcity.ui.base.BaseModel;
import com.qiuchen.smartcity.ui.base.imp.Calls;

import static com.qiuchen.smartcity.utils.http.Http.api;

public class MainModel extends BaseModel {
    public void getBanner(int type, Calls<BannerResponse> bannerResponseCalls) {
        api.getBanner(type).enqueue(bannerResponseCalls);
    }

    public void login(LoginBean loginBean, Calls<LoginResponse> loginResponseCalls) {
        api.login(loginBean).enqueue(loginResponseCalls);
    }

    public void getUserInfo(Calls<GetUserInfo> getUserInfoCalls) {
        api.getUserInfo().enqueue(getUserInfoCalls);
    }

    public void searchNews(String key, Calls<GetNewsResponse> getNewsResponseCalls) {
        api.getNews(key, "").enqueue(getNewsResponseCalls);
    }

    public void getNewsInfo(int newsId, Calls<GetNewsDetails> getNewsDetailsCalls) {
        api.getNewsDetails(newsId).enqueue(getNewsDetailsCalls);
    }

    public void submitNewsComment(SubmitNews comment, Calls<BaseResponse> getNewsDetailsCalls) {
        api.submitCommit(comment).enqueue(getNewsDetailsCalls);
    }

    public void getNewsCommits(int newsId, Calls<GetNewsCommentList> getNewsDetailsCalls) {
        api.getNewsComment(newsId).enqueue(getNewsDetailsCalls);
    }
}

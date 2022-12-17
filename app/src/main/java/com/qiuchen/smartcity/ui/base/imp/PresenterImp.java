package com.qiuchen.smartcity.ui.base.imp;

import com.qiuchen.smartcity.bean.BaseResponse;
import com.qiuchen.smartcity.bean.request.LoginBean;
import com.qiuchen.smartcity.bean.request.SubmitNews;
import com.qiuchen.smartcity.bean.response.*;
import com.qiuchen.smartcity.ui.base.BasePresenter;
import com.qiuchen.smartcity.ui.base.view.*;

public class PresenterImp extends BasePresenter {

    public void getBanner(int type, BannerView view) {
        model.getBanner(type, new Calls<BannerResponse>(view) {
            @Override
            public void GetData(BannerResponse body) {
                view.GetBannerList(body.rows);
            }
        });
    }

    public void login(String user, String pwd, LoginView view) {
        model.login(new LoginBean() {{
            username = user;
            password = pwd;
        }}, new Calls<LoginResponse>(view) {
            @Override
            public void GetData(LoginResponse body) {
                view.LoginSuccess(body.token);
            }
        });
    }

    public void getUserInfo(UserInfoView view) {
        model.getUserInfo(new Calls<GetUserInfo>(view) {
            @Override
            public void GetData(GetUserInfo body) {
                view.GetInfoSuccess(body.user);
            }
        });
    }

    public void searchNews(String key, SearchView view) {
        model.searchNews(key, new Calls<GetNewsResponse>(view) {
            @Override
            public void GetData(GetNewsResponse body) {
                view.GetNews(body.rows);
            }
        });
    }

    public void getNewsDetails(int newsId, NewsDetailsView view) {
        model.getNewsInfo(newsId, new Calls<GetNewsDetails>(view) {
            @Override
            public void GetData(GetNewsDetails body) {
                view.getNew(body.data);
            }
        });
    }

    public void comment(SubmitNews newsId, NewsDetailsView view) {
        model.submitNewsComment(newsId, new Calls<BaseResponse>(view) {
            @Override
            public void GetData(BaseResponse body) {
                view.commitSuccess();
            }
        });
    }

    public void getComments(int newsId, NewsDetailsView view) {
        model.getNewsCommits(newsId, new Calls<GetNewsCommentList>(view) {
            @Override
            public void GetData(GetNewsCommentList body) {
                view.getCommitList(body.rows);
            }
        });
    }

    public void getBannerNews(int newsSize, NewsView newsFragment) {
        model.getNewsBanner(newsSize, new Calls<GetNewsResponse>(newsFragment) {
            @Override
            public void GetData(GetNewsResponse body) {
                newsFragment.GetNewsBanner(body.rows);
            }
        });
    }

    public void getNewsCategory(NewsView newsFragment) {
        model.getNewsCategory(new Calls<GetNewsCategoryList>(newsFragment) {
            @Override
            public void GetData(GetNewsCategoryList body) {
                newsFragment.GetNewsCategory(body.rows);
            }
        });
    }

    public void getNewsByType(String type, NewsView newsView) {
        model.getNewsByType(20, type, new Calls<GetNewsResponse>(newsView) {
            @Override
            public void GetData(GetNewsResponse body) {
                newsView.GetNewsByTypesList(body.rows);
            }
        });
    }
}

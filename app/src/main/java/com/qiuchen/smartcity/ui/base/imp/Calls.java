package com.qiuchen.smartcity.ui.base.imp;

import com.qiuchen.smartcity.bean.BaseResponse;
import com.qiuchen.smartcity.ui.base.BaseView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.lang.ref.WeakReference;

public abstract class Calls<T extends BaseResponse> implements Callback<T> {

    protected WeakReference<BaseView> mView;

    public Calls(BaseView view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (mView.get() != null & response.body() != null) {
            if (response.body().code != 200) {
                mView.get().msg(response.body().msg);
                return;
            }
            GetData(response.body());
        }
    }

    public abstract void GetData(T body);

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        if (mView.get() != null && !call.isCanceled()) {//如果是主动取消网络请求则不弹信息
            throwable.printStackTrace();
            mView.get().msg("请求网络错误。");
        }
    }
}

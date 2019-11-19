package com.alex.playandroid.module.mine.presenter;

import com.alex.playandroid.module.main.model.UserBean;
import com.alex.playandroid.module.mine.contract.RegisterContract;
import com.alex.playandroid.mvp.Presenter;
import com.alex.playandroid.net.BaseObserver;
import com.alex.playandroid.net.DataManager;
import com.alex.playandroid.net.Response;


public class RegisterPresenter extends Presenter<RegisterContract.RegisterView> implements RegisterContract.RegisterModel {
    @Override
    public void register(String account, String password, String repassword) {
        DataManager.getInstance().register(account, password, repassword, new BaseObserver<UserBean>() {
            @Override
            protected void onSuccess(Response<UserBean> userBeanResponse) {
                view.registerSuccess(userBeanResponse.getData());
            }

            @Override
            protected void onFailed(String msg) {
                view.registerFailed(msg);
            }
        });

    }
}

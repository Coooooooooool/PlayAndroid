package com.alex.playandroid.module.mine.presenter;

import com.alex.playandroid.module.main.model.UserBean;
import com.alex.playandroid.module.mine.contract.LoginContract;
import com.alex.playandroid.mvp.Presenter;
import com.alex.playandroid.net.BaseObserver;
import com.alex.playandroid.net.DataManager;
import com.alex.playandroid.net.Response;

public class LoginPresenter extends Presenter<LoginContract.LoginView> implements LoginContract.LoginModel {
    @Override
    public void login(String account, String password) {
        DataManager.getInstance().login(account, password, new BaseObserver<UserBean>() {
            @Override
            protected void onSuccess(Response<UserBean> userBeanResponse) {
                view.loginSuccess(userBeanResponse.getData());
            }

            @Override
            protected void onFailed(String msg) {
                view.loginFailed(msg);
            }
        });
    }
}

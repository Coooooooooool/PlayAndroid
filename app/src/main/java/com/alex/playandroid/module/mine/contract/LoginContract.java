package com.alex.playandroid.module.mine.contract;

import com.alex.playandroid.module.main.model.UserBean;
import com.alex.playandroid.mvp.BaseModel;
import com.alex.playandroid.mvp.BaseView;

public interface LoginContract {

    interface LoginView extends BaseView{
        void loginSuccess(UserBean data);
        void loginFailed(String msg);
    }

    interface LoginModel extends BaseModel{
        void login(String account,String password);
    }

}

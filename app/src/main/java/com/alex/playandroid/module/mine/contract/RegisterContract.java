package com.alex.playandroid.module.mine.contract;

import com.alex.playandroid.module.main.model.UserBean;
import com.alex.playandroid.mvp.BaseModel;
import com.alex.playandroid.mvp.BaseView;

public interface RegisterContract {

    interface RegisterView extends BaseView{
        void registerSuccess(UserBean data);
        void registerFailed(String msg);
    }

    interface RegisterModel extends BaseModel{
        void register(String account,String password, String repassword);
    }


}

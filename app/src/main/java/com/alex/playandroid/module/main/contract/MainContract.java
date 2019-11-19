package com.alex.playandroid.module.main.contract;

import com.alex.playandroid.mvp.BaseModel;
import com.alex.playandroid.mvp.BaseView;

public interface MainContract {

    interface MainView extends BaseView{
        void requestCoinSuccess(Integer data);
        void requestCoinFailed(String msg);
    }

    interface MainModel extends BaseModel{
        void requestCoin();
    }

}

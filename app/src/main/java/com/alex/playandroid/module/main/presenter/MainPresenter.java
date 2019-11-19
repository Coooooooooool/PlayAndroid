package com.alex.playandroid.module.main.presenter;

import com.alex.playandroid.module.main.contract.MainContract;
import com.alex.playandroid.mvp.Presenter;
import com.alex.playandroid.net.BaseObserver;
import com.alex.playandroid.net.DataManager;
import com.alex.playandroid.net.Response;

public class MainPresenter extends Presenter<MainContract.MainView> implements MainContract.MainModel {
    @Override
    public void requestCoin() {
        DataManager.getInstance().requestCoin(new BaseObserver<Integer>() {
            @Override
            protected void onSuccess(Response<Integer> integerResponse) {
                view.requestCoinSuccess(integerResponse.getData());
            }

            @Override
            protected void onFailed(String msg) {
                view.requestCoinFailed(msg);
            }
        });
    }
}

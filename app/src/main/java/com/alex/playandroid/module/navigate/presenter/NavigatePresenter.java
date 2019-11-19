package com.alex.playandroid.module.navigate.presenter;

import com.alex.playandroid.module.navigate.contract.NavigateContract;
import com.alex.playandroid.module.navigate.entity.NaviBean;
import com.alex.playandroid.mvp.Presenter;
import com.alex.playandroid.net.BaseObserver;
import com.alex.playandroid.net.DataManager;
import com.alex.playandroid.net.Response;

import java.util.List;

public class NavigatePresenter extends Presenter<NavigateContract.NavigateView> implements NavigateContract.NavigateModel {
    @Override
    public void requestNavigateList() {
        DataManager.getInstance().requestNavigateList(new BaseObserver<List<NaviBean>>() {
            @Override
            protected void onSuccess(Response<List<NaviBean>> listResponse) {
                view.requestNavigateListSuccess(listResponse.getData());
            }

            @Override
            protected void onFailed(String msg) {
                view.requestNavigateListFailed(msg);
            }
        });
    }
}

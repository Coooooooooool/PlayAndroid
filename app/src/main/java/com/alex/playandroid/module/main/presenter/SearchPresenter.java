package com.alex.playandroid.module.main.presenter;

import com.alex.playandroid.module.home.model.HotKeyBean;
import com.alex.playandroid.module.main.contract.SearchContract;
import com.alex.playandroid.module.main.model.ArticleListBean;
import com.alex.playandroid.mvp.Presenter;
import com.alex.playandroid.net.BaseObserver;
import com.alex.playandroid.net.DataManager;
import com.alex.playandroid.net.Response;

import java.util.List;

public class SearchPresenter extends Presenter<SearchContract.SearchView> implements SearchContract.SearchModel {

    @Override
    public void requestHotKeyList() {
        DataManager.getInstance().requestHotKeyList(new BaseObserver<List<HotKeyBean>>() {
            @Override
            protected void onSuccess(Response<List<HotKeyBean>> listResponse) {
                view.requestHotKeyListSuccess(listResponse.getData());
            }

            @Override
            protected void onFailed(String msg) {
                view.requestHotKeyListFailed(msg);
            }
        });
    }

    @Override
    public void search(int page, String key) {
        DataManager.getInstance().search(page, key, new BaseObserver<ArticleListBean>() {
            @Override
            protected void onSuccess(Response<ArticleListBean> articleListBeanResponse) {
                view.searchSuccess(articleListBeanResponse.getData());
            }

            @Override
            protected void onFailed(String msg) {
                view.searchFailed(msg);
            }
        });
    }
}

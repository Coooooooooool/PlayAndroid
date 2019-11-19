package com.alex.playandroid.module.home.presenter;

import com.alex.playandroid.module.home.contract.HomeContract;
import com.alex.playandroid.module.home.model.BannerBean;
import com.alex.playandroid.module.main.model.ArticleBean;
import com.alex.playandroid.module.main.model.ArticleListBean;
import com.alex.playandroid.mvp.Presenter;
import com.alex.playandroid.net.BaseObserver;
import com.alex.playandroid.net.DataManager;
import com.alex.playandroid.net.Response;

import java.util.List;

public class HomePresenter extends Presenter<HomeContract.HomeView> implements HomeContract.HomeModel {


    @Override
    public void requestBanner() {
        DataManager.getInstance().requestBanner(new BaseObserver<List<BannerBean>>() {
            @Override
            protected void onSuccess(Response<List<BannerBean>> listResponse) {
                view.requestBannerSuccess(listResponse);
            }

            @Override
            protected void onFailed(String msg) {
                view.requestBannerFailed(msg);
            }
        });
    }

    @Override
    public void requestArticleList(int page) {
        DataManager.getInstance().requestHomeArticleList(page, new BaseObserver<ArticleListBean>() {
            @Override
            protected void onSuccess(Response<ArticleListBean> articleListBeanResponse) {
                view.requestArticleListSuccess(articleListBeanResponse.getData());
            }

            @Override
            protected void onFailed(String msg) {
                view.requestArticleListFailed(msg);
            }
        });
    }

    @Override
    public void requestTopArticleList() {
        DataManager.getInstance().requestTopArticleList(new BaseObserver<List<ArticleBean>>() {
            @Override
            protected void onSuccess(Response<List<ArticleBean>> listResponse) {
                view.requestTopArticleListSuccess(listResponse.getData());
            }

            @Override
            protected void onFailed(String msg) {
                view.requestTopArticleListFailed(msg);
            }
        });
    }

    @Override
    public void collect(int id) {
        DataManager.getInstance().collect(id, new BaseObserver<String>() {
            @Override
            protected void onSuccess(Response<String> stringResponse) {
                view.collectSuccess(stringResponse.getData());
            }

            @Override
            protected void onFailed(String msg) {
                view.collectFailed(msg);
            }
        });
    }

    @Override
    public void uncollect(int id) {
        DataManager.getInstance().uncollect(id, new BaseObserver<String>() {
            @Override
            protected void onSuccess(Response<String> stringResponse) {
                view.uncollectSuccess(stringResponse.getData());
            }

            @Override
            protected void onFailed(String msg) {
                view.uncollectFailed(msg);
            }
        });
    }
}

package com.alex.playandroid.module.mine.presenter;

import com.alex.playandroid.module.main.model.ArticleListBean;
import com.alex.playandroid.module.mine.contract.CollectionArticleContract;
import com.alex.playandroid.mvp.Presenter;
import com.alex.playandroid.net.BaseObserver;
import com.alex.playandroid.net.DataManager;
import com.alex.playandroid.net.Response;

public class CollectionArticlePresenter extends Presenter<CollectionArticleContract.CollectionArticleView> implements CollectionArticleContract.CollectionArticleModel {
    @Override
    public void requestCollectionArticleList(int page) {
        DataManager.getInstance().requestCollectArticleList(page, new BaseObserver<ArticleListBean>() {
            @Override
            protected void onSuccess(Response<ArticleListBean> articleListBeanResponse) {
                view.requestCollectionArticleListSuccess(articleListBeanResponse.getData());
            }

            @Override
            protected void onFailed(String msg) {
                view.requestCollectionArticleListFailed(msg);
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

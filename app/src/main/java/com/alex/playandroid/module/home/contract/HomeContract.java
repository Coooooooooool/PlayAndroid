package com.alex.playandroid.module.home.contract;

import com.alex.playandroid.module.home.model.BannerBean;
import com.alex.playandroid.module.main.model.ArticleBean;
import com.alex.playandroid.module.main.model.ArticleListBean;
import com.alex.playandroid.mvp.BaseModel;
import com.alex.playandroid.mvp.BaseView;
import com.alex.playandroid.net.Response;

import java.util.List;

public interface HomeContract {

    interface HomeView extends BaseView {

        void requestBannerSuccess(Response<List<BannerBean>> data);

        void requestBannerFailed(String msg);

        void requestArticleListSuccess(ArticleListBean data);

        void requestArticleListFailed(String msg);

        void requestTopArticleListSuccess(List<ArticleBean> data);

        void requestTopArticleListFailed(String msg);

        void collectSuccess(String data);

        void collectFailed(String msg);

        void uncollectSuccess(String data);

        void uncollectFailed(String msg);

    }


    interface HomeModel extends BaseModel {

        void requestBanner();

        void requestArticleList(int page);

        void requestTopArticleList();

        void collect(int id);

        void uncollect(int id);

    }



}

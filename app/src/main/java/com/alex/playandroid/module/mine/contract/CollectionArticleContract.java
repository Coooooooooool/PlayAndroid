package com.alex.playandroid.module.mine.contract;

import com.alex.playandroid.module.main.model.ArticleListBean;
import com.alex.playandroid.mvp.BaseModel;
import com.alex.playandroid.mvp.BaseView;

public interface CollectionArticleContract {

    interface CollectionArticleView extends BaseView{
        void requestCollectionArticleListSuccess(ArticleListBean data);
        void requestCollectionArticleListFailed(String msg);

        void uncollectSuccess(String data);
        void uncollectFailed(String msg);
    }

    interface CollectionArticleModel extends BaseModel{
        void requestCollectionArticleList(int page);

        void uncollect(int id);
    }

}

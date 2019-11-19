package com.alex.playandroid.module.knowledge.presenter;

import com.alex.playandroid.module.knowledge.contract.KnowledgeArticleContract;
import com.alex.playandroid.module.main.model.ArticleListBean;
import com.alex.playandroid.mvp.Presenter;
import com.alex.playandroid.net.BaseObserver;
import com.alex.playandroid.net.DataManager;
import com.alex.playandroid.net.Response;

public class KnowledgeArticlePresenter extends Presenter<KnowledgeArticleContract.KnowledgeArticleView> implements KnowledgeArticleContract.KnowledgeArticleModel {


    @Override
    public void requestKnowledgeArticle(int page, int id) {
        DataManager.getInstance().requestKnowledgeArticleList(page, id, new BaseObserver<ArticleListBean>() {
            @Override
            protected void onSuccess(Response<ArticleListBean> articleListBeanResponse) {
                view.requestKnowledgeArticleSuccess(articleListBeanResponse.getData());
            }

            @Override
            protected void onFailed(String msg) {
                view.requestKnowledgeArticleFailed(msg);
            }
        });
    }
}

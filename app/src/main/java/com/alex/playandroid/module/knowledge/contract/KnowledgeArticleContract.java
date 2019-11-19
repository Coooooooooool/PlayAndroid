package com.alex.playandroid.module.knowledge.contract;

import com.alex.playandroid.module.main.model.ArticleListBean;
import com.alex.playandroid.mvp.BaseModel;
import com.alex.playandroid.mvp.BaseView;

public interface KnowledgeArticleContract {

    interface KnowledgeArticleView extends BaseView{

        void requestKnowledgeArticleSuccess(ArticleListBean data);
        void requestKnowledgeArticleFailed(String msg);
    }

    interface KnowledgeArticleModel extends BaseModel{

        void requestKnowledgeArticle(int page,int id);

    }

}

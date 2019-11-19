package com.alex.playandroid.module.project;

import com.alex.playandroid.module.main.model.ArticleListBean;
import com.alex.playandroid.module.main.model.ChapterBean;
import com.alex.playandroid.mvp.BaseModel;
import com.alex.playandroid.mvp.BaseView;

import java.util.List;

public interface ProjectContract {

    public interface ProjectView extends BaseView{

        void requestProjectChaptersSuccess(List<ChapterBean> data);
        void requestProjectChaptersFailed(String msg);

        void requestProjectArticleListSuccess(ArticleListBean data);
        void requestProjectArticleListFailed(String msg);

    }


    public interface ProjectModel extends BaseModel{

        void requestProjectChapters();

        void requestProjectArticleList(int page,int id);

    }

}

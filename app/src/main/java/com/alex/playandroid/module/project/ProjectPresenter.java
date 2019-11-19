package com.alex.playandroid.module.project;

import com.alex.playandroid.module.main.model.ArticleListBean;
import com.alex.playandroid.module.main.model.ChapterBean;
import com.alex.playandroid.module.project.ProjectContract.ProjectModel;
import com.alex.playandroid.mvp.Presenter;
import com.alex.playandroid.net.DataManager;
import com.alex.playandroid.net.Response;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ProjectPresenter extends Presenter<ProjectContract.ProjectView> implements ProjectModel {

    @Override
    public void requestProjectChapters() {
        DataManager.getInstance().requestProjectChapters(new Observer<Response<List<ChapterBean>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<List<ChapterBean>> listResponse) {
                view.requestProjectChaptersSuccess(listResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                view.requestProjectChaptersFailed(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void requestProjectArticleList(int page, int id) {
        DataManager.getInstance().requestProjectArticleList(page, id, new Observer<Response<ArticleListBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<ArticleListBean> articleListBeanResponse) {
                view.requestProjectArticleListSuccess(articleListBeanResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                view.requestProjectArticleListFailed(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}

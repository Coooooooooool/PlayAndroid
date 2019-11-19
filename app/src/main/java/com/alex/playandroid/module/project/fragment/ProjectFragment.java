package com.alex.playandroid.module.project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alex.playandroid.R;
import com.alex.playandroid.base.BaseMvpFragment;
import com.alex.playandroid.module.main.model.ArticleListBean;
import com.alex.playandroid.module.main.model.ChapterBean;
import com.alex.playandroid.module.project.ProjectContract;
import com.alex.playandroid.module.project.ProjectPresenter;

import java.util.List;

public class ProjectFragment extends BaseMvpFragment<ProjectPresenter> implements ProjectContract.ProjectView {




    public static ProjectFragment newInstance() {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project, container, false);
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_project;
    }

    @Override
    protected ProjectPresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }


    @Override
    public void requestProjectChaptersSuccess(List<ChapterBean> data) {

    }

    @Override
    public void requestProjectChaptersFailed(String msg) {

    }

    @Override
    public void requestProjectArticleListSuccess(ArticleListBean data) {

    }

    @Override
    public void requestProjectArticleListFailed(String msg) {

    }
}

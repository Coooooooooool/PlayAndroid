package com.alex.playandroid.module.mine.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alex.playandroid.R;
import com.alex.playandroid.base.BaseMvpFragment;
import com.alex.playandroid.module.home.adapter.ArticleAdapter;
import com.alex.playandroid.module.main.model.ArticleBean;
import com.alex.playandroid.module.main.model.ArticleListBean;
import com.alex.playandroid.module.mine.contract.CollectionArticleContract;
import com.alex.playandroid.module.mine.presenter.CollectionArticlePresenter;
import com.alex.playandroid.utils.LogUtil;
import com.alex.playandroid.utils.RvAnimUtils;
import com.alex.playandroid.utils.SettingUtils;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionArticleFragment extends BaseMvpFragment<CollectionArticlePresenter> implements CollectionArticleContract.CollectionArticleView {


    @BindView(R.id.rv_article)
    RecyclerView rvArticle;
    @BindView(R.id.layout_refresh)
    SmartRefreshLayout layoutRefresh;

    private ArticleAdapter articleAdapter;
    private int currPage = 0;
    private int uncollectItemPosition;

    public static CollectionArticleFragment newInstance() {
        CollectionArticleFragment fragment = new CollectionArticleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int bindLayout() {
        return R.layout.fragment_collection_article;
    }

    @Override
    protected CollectionArticlePresenter createPresenter() {
        return new CollectionArticlePresenter();
    }

    @Override
    protected void initView() {
        rvArticle.setLayoutManager(new LinearLayoutManager(getActivity()));
        articleAdapter = new ArticleAdapter();
        RvAnimUtils.setAnim(articleAdapter, SettingUtils.getInstance().getRvAnim());
        rvArticle.setAdapter(articleAdapter);
    }

    @Override
    protected void initData() {
        presenter.requestCollectionArticleList(currPage);
    }

    @Override
    protected void initListener() {
        layoutRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.requestCollectionArticleList(currPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.requestCollectionArticleList(0);
            }
        });
        articleAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ArticleBean item = articleAdapter.getItem(position);
            if(item!=null){
                uncollectItemPosition = position;
                presenter.uncollect(item.getId());
            }
        });
    }

    @Override
    public void requestCollectionArticleListSuccess(ArticleListBean data) {
        currPage = data.getCurPage();
        for (ArticleBean item : data.getDatas()) {
            item.setCollect(true);
        }
        if(data.getCurPage()==1){
            articleAdapter.setNewData(data.getDatas());
            layoutRefresh.finishRefresh(true);
        }else{
            if(data.getDatas().size()==0){
                layoutRefresh.finishLoadMoreWithNoMoreData();
            }else{
                articleAdapter.addData(data.getDatas());
                layoutRefresh.finishLoadMore(true);
            }
        }
    }

    @Override
    public void requestCollectionArticleListFailed(String msg) {
        layoutRefresh.finishLoadMore(false);
        layoutRefresh.finishRefresh(false);
        SmartToast.fail(msg);
    }

    @Override
    public void uncollectSuccess(String data) {
        articleAdapter.remove(uncollectItemPosition);
    }

    @Override
    public void uncollectFailed(String msg) {
        SmartToast.fail(msg);
        LogUtil.e(TAG,msg);
    }


}

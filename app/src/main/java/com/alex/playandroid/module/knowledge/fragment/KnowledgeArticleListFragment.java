package com.alex.playandroid.module.knowledge.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alex.playandroid.R;
import com.alex.playandroid.base.BaseMvpFragment;
import com.alex.playandroid.module.home.adapter.ArticleAdapter;
import com.alex.playandroid.module.knowledge.contract.KnowledgeArticleContract;
import com.alex.playandroid.module.knowledge.presenter.KnowledgeArticlePresenter;
import com.alex.playandroid.module.main.activity.WebActivity;
import com.alex.playandroid.module.main.model.ArticleBean;
import com.alex.playandroid.module.main.model.ArticleListBean;
import com.alex.playandroid.module.main.model.ChapterBean;
import com.alex.playandroid.utils.RvAnimUtils;
import com.alex.playandroid.utils.SettingUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;


public class KnowledgeArticleListFragment extends BaseMvpFragment<KnowledgeArticlePresenter> implements KnowledgeArticleContract.KnowledgeArticleView {


    @BindView(R.id.rv_knowledge_article)
    RecyclerView rvKnowledgeArticle;
    @BindView(R.id.layout_refresh)
    SmartRefreshLayout layoutRefresh;

    private ArticleAdapter articleAdapter;
    private ChapterBean chapter;
    private int position = -1;

    private int currPage = 0;

    public static KnowledgeArticleListFragment newInstance(ChapterBean chapterBean, int position) {
        KnowledgeArticleListFragment fragment = new KnowledgeArticleListFragment();
        Bundle args = new Bundle();
        args.putSerializable("chapter", chapterBean);
        args.getInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int bindLayout() {
        return R.layout.fragment_knowledge_article_list;
    }

    @Override
    protected KnowledgeArticlePresenter createPresenter() {
        return new KnowledgeArticlePresenter();
    }

    @Override
    protected void initView() {
        Bundle args = getArguments();
        if (args != null) {
            chapter = (ChapterBean) args.getSerializable("chapter");
            position = args.getInt("position", -1);
        }
        rvKnowledgeArticle.setLayoutManager(new LinearLayoutManager(getActivity()));
        articleAdapter = new ArticleAdapter();
        RvAnimUtils.setAnim(articleAdapter, SettingUtils.getInstance().getRvAnim());
        rvKnowledgeArticle.setAdapter(articleAdapter);

    }

    @Override
    protected void initData() {
//        presenter.requestKnowledgeArticle(currPage,chapter.getId());
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
        presenter.requestKnowledgeArticle(currPage,chapter.getId());
    }

    @Override
    protected void initListener() {
        articleAdapter.setOnItemClickListener((adapter, view, position) -> {
            ArticleBean item = articleAdapter.getItem(position);
            if(item!=null){
                Intent intent = new Intent(getActivity(),WebActivity.class);
                intent.putExtra("url",item.getLink());
                intent.putExtra("title",item.getTitle());
                startActivity(intent);
            }
        });
        articleAdapter.setOnItemChildViewClickListener((helper, view, position) -> {
            ArticleBean item = articleAdapter.getItem(position);
            if(item!=null){
//                if(view.isLiked())
//                    presenter.uncollect(item.getId());
//                else
//                    presenter.collect(item.getId());
            }
        });
        layoutRefresh.setOnLoadMoreListener(refreshLayout -> presenter.requestKnowledgeArticle(currPage,chapter.getId()));
        layoutRefresh.setOnRefreshListener(refreshLayout -> presenter.requestKnowledgeArticle(0,chapter.getId()));
    }


    @Override
    public void requestKnowledgeArticleSuccess(ArticleListBean data) {
        currPage = data.getCurPage();
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
    public void requestKnowledgeArticleFailed(String msg) {
        layoutRefresh.finishRefresh(false);
        layoutRefresh.finishLoadMore(false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

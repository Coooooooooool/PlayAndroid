package com.alex.playandroid.module.home.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alex.playandroid.R;
import com.alex.playandroid.base.BaseMvpFragment;
import com.alex.playandroid.module.home.adapter.ArticleAdapter;
import com.alex.playandroid.module.home.contract.HomeContract;
import com.alex.playandroid.module.home.model.BannerBean;
import com.alex.playandroid.module.home.presenter.HomePresenter;
import com.alex.playandroid.module.main.activity.WebActivity;
import com.alex.playandroid.module.main.model.ArticleBean;
import com.alex.playandroid.module.main.model.ArticleListBean;
import com.alex.playandroid.net.Response;
import com.alex.playandroid.utils.GlideImageLoader;
import com.alex.playandroid.utils.LogUtil;
import com.alex.playandroid.utils.RvAnimUtils;
import com.alex.playandroid.utils.SettingUtils;
import com.alex.playandroid.utils.display.DisplayInfoUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.like.LikeButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.HomeView {


    @BindView(R.id.rv_article)
    RecyclerView rvArticle;
    @BindView(R.id.layout_refresh)
    SmartRefreshLayout layoutRefresh;

    private Banner bannerView;
    private ArticleAdapter articleAdapter;
    private List<BannerBean> bannerList;
    private int curPage = 0;
    private List<View> topArticleViews;
    private List<ArticleBean> topArticles;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int bindLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void initView() {
        rvArticle.setLayoutManager(new LinearLayoutManager(getActivity()));
        articleAdapter = new ArticleAdapter();
//        RvAnimUtils.setAnim(articleAdapter, SettingUtils.getInstance().getRvAnim());
        RvAnimUtils.setAnim(articleAdapter, SettingUtils.getInstance().getRvAnim());
        rvArticle.setAdapter(articleAdapter);
        articleAdapter.addHeaderView(createHeaderBanner());
    }

    private Banner createHeaderBanner() {
            bannerView = new Banner(getContext());
            int height = (int) (DisplayInfoUtils.getInstance().getWidthPixels() * (9F / 16F));
            bannerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
            bannerView.setImageLoader(new GlideImageLoader()); //设置图片加载器
            bannerView.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置（当banner模式中有指示器时）
            bannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);   //设置banner样式
            bannerView.setBannerAnimation(Transformer.Default);    //设置banner动画效果
            bannerView.startAutoPlay();   //设置自动轮播，默认为true
            bannerView.setDelayTime(5000);//设置轮播时间
            bannerView.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    BannerBean bean = bannerList.get(position);
                    Intent intent = new Intent(getActivity(),WebActivity.class);
                    intent.putExtra("url",bean.getUrl());
                    intent.putExtra("title",bean.getTitle());
                    startActivity(intent);
                }
            });
            return bannerView;
    }


    @Override
    protected void initData() {
        presenter.requestBanner();
        presenter.requestArticleList(0);
        presenter.requestTopArticleList();
    }

    @Override
    protected void initListener() {
        layoutRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.requestBanner();
                presenter.requestArticleList(0);
                presenter.requestTopArticleList();
            }
        });
        layoutRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.requestArticleList(curPage);
            }
        });
        articleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(),WebActivity.class);
                intent.putExtra("url",articleAdapter.getItem(position).getLink());
                intent.putExtra("title",articleAdapter.getItem(position).getTitle());
                startActivity(intent);
            }
        });
        articleAdapter.setOnItemChildViewClickListener((helper, view, position) -> {
            ArticleBean item = articleAdapter.getItem(position);
            if(item!=null){
                if(view.isLiked())
                    presenter.collect(item.getId());
                else
                    presenter.uncollect(item.getId());

            }
        });
    }

    private void removeHeaderTopItems() {
        if (topArticleViews != null) {
            for (View view : topArticleViews) {
                articleAdapter.removeHeaderView(view);
            }
            topArticleViews.clear();
            topArticleViews = null;
        }
        if (topArticles != null) {
            topArticles.clear();
            topArticles = null;
        }
    }

    private void createHeaderTopItems(List<ArticleBean> data) {
        removeHeaderTopItems();
        topArticles = data;
        if (topArticles == null || topArticles.isEmpty()) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(getContext());
        topArticleViews = new ArrayList<>();
        for (int i = 0; i < topArticles.size(); i++) {
            View view = inflater.inflate(R.layout.rv_item_article, rvArticle, false);
            topArticleViews.add(view);
        }
        for (int i = 0; i < topArticleViews.size(); i++) {
            final int pos = i;
            View view = topArticleViews.get(i);
            ArticleBean bean = topArticles.get(i);
            bindHeaderTopItem(view, bean);

            view.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(),WebActivity.class);
                intent.putExtra("url",bean.getLink());
                intent.putExtra("title",bean.getTitle());
                startActivity(intent);
            });
            view.setOnLongClickListener(v -> {
//                    showWebDialog(true, pos);
                return true;
            });
            articleAdapter.addHeaderView(view, articleAdapter.getHeaderLayout().getChildCount());
        }
    }

    private void bindHeaderTopItem(View view, ArticleBean item) {
        ArticleAdapter.bindArticle(view, item, new ArticleAdapter.OnCollectListener() {
            @Override
            public void collect(ArticleBean item,LikeButton view) {
                presenter.collect(item.getId());
            }

            @Override
            public void uncollect(ArticleBean item,LikeButton view) {
                presenter.uncollect(item.getId());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (bannerView != null) {
            bannerView.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (bannerView != null) {
            bannerView.stopAutoPlay();
        }
    }

    @Override
    public void requestBannerSuccess(Response<List<BannerBean>> data) {
        bannerList = data.getData();
        List<String> images = new ArrayList<>(data.getData().size());
        List<String> titles = new ArrayList<>(data.getData().size());
        for (int i = 0; i < data.getData().size(); i++) {
            images.add(data.getData().get(i).getImagePath());
            titles.add(data.getData().get(i).getTitle());
        }
        bannerView.setImages(images);
        bannerView.setBannerTitles(titles);
        bannerView.start();
    }

    @Override
    public void requestBannerFailed(String msg) {

    }

    @Override
    public void requestArticleListSuccess(ArticleListBean data) {
        curPage = data.getCurPage();
        if(curPage==1){
            articleAdapter.setNewData(data.getDatas());
            layoutRefresh.finishRefresh(true);
        }else{
            articleAdapter.addData(data.getDatas());
            layoutRefresh.finishLoadMore(true);
        }
    }

    @Override
    public void requestArticleListFailed(String msg) {
        layoutRefresh.finishRefresh(false);
        layoutRefresh.finishLoadMore(false);
    }

    @Override
    public void requestTopArticleListSuccess(List<ArticleBean> data) {
        for (ArticleBean a :data) {
            a.setTop(true);
        }
        createHeaderTopItems(data);
    }

    @Override
    public void requestTopArticleListFailed(String msg) {
        SmartToast.success("请求置顶文章失败"+msg);
    }

    @Override
    public void collectSuccess(String data) {
        SmartToast.success("收藏成功");
    }

    @Override
    public void collectFailed(String msg) {
        SmartToast.success("收藏失败"+msg);
        LogUtil.e(TAG,msg);

    }

    @Override
    public void uncollectSuccess(String data) {
        SmartToast.success("取消收藏成功");
    }

    @Override
    public void uncollectFailed(String msg) {
        SmartToast.success("取消收藏失败"+msg);
        LogUtil.e(TAG,msg);

    }
}

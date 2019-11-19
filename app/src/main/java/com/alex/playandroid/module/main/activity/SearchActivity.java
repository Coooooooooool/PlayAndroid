package com.alex.playandroid.module.main.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.alex.playandroid.R;
import com.alex.playandroid.base.BaseMvpActivity;
import com.alex.playandroid.module.home.adapter.ArticleAdapter;
import com.alex.playandroid.module.home.model.HotKeyBean;
import com.alex.playandroid.module.main.contract.SearchContract;
import com.alex.playandroid.module.main.model.ArticleListBean;
import com.alex.playandroid.module.main.presenter.SearchPresenter;
import com.alex.playandroid.utils.RvAnimUtils;
import com.alex.playandroid.utils.SettingUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseMvpActivity<SearchPresenter> implements SearchContract.SearchView {


    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.layout_hot_keys)
    LinearLayout layoutHotKeys;
    @BindView(R.id.rv_result)
    RecyclerView rvResult;
    @BindView(R.id.layout_refresh)
    SmartRefreshLayout layoutRefresh;
    @BindView(R.id.rv_hot_keys)
    RecyclerView rvHotKeys;

    private BaseQuickAdapter<HotKeyBean,BaseViewHolder> hotKeysAdapter;
    private ArticleAdapter articleAdapter;
    private int currPage = 0;


    @Override
    protected int bindLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected void initView() {
        rvResult.setLayoutManager(new LinearLayoutManager(context));
        articleAdapter = new ArticleAdapter();
        RvAnimUtils.setAnim(articleAdapter, SettingUtils.getInstance().getRvAnim());
        rvResult.setAdapter(articleAdapter);

        rvHotKeys.setNestedScrollingEnabled(false);
        rvHotKeys.setHasFixedSize(true);
        rvHotKeys.setLayoutManager(new FlexboxLayoutManager(context));
        hotKeysAdapter = new BaseQuickAdapter<HotKeyBean, BaseViewHolder>(R.layout.rv_item_search_hot) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, HotKeyBean item) {
                helper.setText(R.id.tv_key,item.getName());
            }
        };
        rvHotKeys.setAdapter(hotKeysAdapter);

        layoutRefresh.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        presenter.requestHotKeyList();
    }

    @Override
    protected void initListener() {
        titlebar.setListener((v, action, extra) -> {
            if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                if(layoutRefresh.getVisibility()==View.VISIBLE){
                    layoutRefresh.setVisibility(View.GONE);
                }else{
                    finish();
                }
            } else if (action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
                presenter.search(0, titlebar.getCenterSearchEditText().getText().toString());
            }
        });
        hotKeysAdapter.setOnItemClickListener((adapter, view, position) -> {
            HotKeyBean key = hotKeysAdapter.getItem(position);
            if(key!=null){
                titlebar.getCenterSearchEditText().setText(key.getName());
                presenter.search(0,key.getName());
            }
        });
        layoutRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.search(currPage, titlebar.getCenterSearchEditText().getText().toString());
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.search(0, titlebar.getCenterSearchEditText().getText().toString());
            }
        });
    }

    @Override
    public void requestHotKeyListSuccess(List<HotKeyBean> data) {
        hotKeysAdapter.setNewData(data);
    }

    @Override
    public void requestHotKeyListFailed(String msg) {

    }

    @Override
    public void searchSuccess(ArticleListBean data) {
        layoutRefresh.setVisibility(View.VISIBLE);
        currPage = data.getCurPage();
        if (data.getCurPage() == 1) {
            articleAdapter.setNewData(data.getDatas());
            layoutRefresh.finishRefresh(true);
        } else {
            articleAdapter.addData(data.getDatas());
            layoutRefresh.finishLoadMore(true);
        }
    }

    @Override
    public void searchFailed(String msg) {
        layoutRefresh.finishRefresh(false);
        layoutRefresh.finishLoadMore(false);
    }


}

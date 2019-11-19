package com.alex.playandroid.module.main.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alex.playandroid.R;
import com.alex.playandroid.base.BaseMvpActivity;
import com.alex.playandroid.module.main.adapter.CoinRankAdapter;
import com.alex.playandroid.module.main.contract.CoinRankContract;
import com.alex.playandroid.module.main.presenter.CoinRankPresenter;
import com.alex.playandroid.module.mine.model.CoinRankBean;
import com.alex.playandroid.utils.RvAnimUtils;
import com.alex.playandroid.utils.SettingUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoinRankActivity extends BaseMvpActivity<CoinRankPresenter> implements CoinRankContract.CoinRankView {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.rv_rank)
    RecyclerView rvRank;
    @BindView(R.id.layout_refresh)
    SmartRefreshLayout layoutRefresh;

    private CoinRankAdapter coinRankAdapter;
    private int currPage = 1;


    @Override
    protected int bindLayout() {
        return R.layout.activity_coin_rank;
    }

    @Override
    protected CoinRankPresenter createPresenter() {
        return new CoinRankPresenter();
    }

    @Override
    protected void initView() {
        rvRank.setLayoutManager(new LinearLayoutManager(context));
        coinRankAdapter = new CoinRankAdapter();
        coinRankAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvRank.setAdapter(coinRankAdapter);
    }

    @Override
    protected void initData() {
        presenter.requestCoinRank(currPage);
    }

    @Override
    protected void initListener() {
        titlebar.setListener((v, action, extra) -> {
            if (action == CommonTitleBar.ACTION_LEFT_TEXT)
                finish();
        });
        layoutRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.requestCoinRank(currPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.requestCoinRank(0);
            }
        });
    }

    @Override
    public void requestCoinRankSuccess(CoinRankBean data) {
        currPage = data.getCurPage()+1;
        if (data.getCurPage() == 1) {
            coinRankAdapter.setNewData(data.getDatas());
            layoutRefresh.finishRefresh(true);
        } else {
            if (data.getDatas().size() == 0) {
                layoutRefresh.finishLoadMoreWithNoMoreData();
            } else {
                coinRankAdapter.addData(data.getDatas());
                layoutRefresh.finishLoadMore(true);
            }
        }
    }

    @Override
    public void requestCoinRankFailed(String msg) {
        layoutRefresh.finishRefresh(false);
        layoutRefresh.finishLoadMore(false);
    }
}

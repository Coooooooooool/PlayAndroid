package com.alex.playandroid.module.navigate.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alex.playandroid.R;
import com.alex.playandroid.base.BaseMvpFragment;
import com.alex.playandroid.module.main.activity.WebActivity;
import com.alex.playandroid.module.navigate.adapter.NavigateLeftAdapter;
import com.alex.playandroid.module.navigate.adapter.NavigateRightAdapter;
import com.alex.playandroid.module.navigate.contract.NavigateContract;
import com.alex.playandroid.module.navigate.entity.NaviBean;
import com.alex.playandroid.module.navigate.presenter.NavigatePresenter;

import java.util.List;

import butterknife.BindView;


public class NavigateFragment extends BaseMvpFragment<NavigatePresenter> implements NavigateContract.NavigateView {


    @BindView(R.id.rv_left)
    RecyclerView rvLeft;
    @BindView(R.id.rv_right)
    RecyclerView rvRight;

    private NavigateLeftAdapter leftAdapter;
    private NavigateRightAdapter rightAdapter;

    public static NavigateFragment newInstance() {
        NavigateFragment fragment = new NavigateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int bindLayout() {
        return R.layout.fragment_navigate;
    }

    @Override
    protected NavigatePresenter createPresenter() {
        return new NavigatePresenter();
    }

    @Override
    protected void initView() {
        rvLeft.setLayoutManager(new LinearLayoutManager(getActivity()));
        leftAdapter = new NavigateLeftAdapter();
        rvLeft.setAdapter(leftAdapter);
        rvRight.setLayoutManager(new LinearLayoutManager(getActivity()));
        rightAdapter = new NavigateRightAdapter();
        rvRight.setAdapter(rightAdapter);
    }

    @Override
    protected void initData() {
        presenter.requestNavigateList();
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
//        presenter.requestNavigateList();
    }

    @Override
    protected void initListener() {
        leftAdapter.setOnItemClickListener((adapter, view, position) -> {
            rvRight.scrollToPosition(position);
        });
        rightAdapter.setOnFlexboxLayoutItemClickListener((bean, position) -> {
            Intent intent = new Intent(getActivity(),WebActivity.class);
            intent.putExtra("title",bean.getTitle());
            intent.putExtra("url",bean.getLink());
            startActivity(intent);
        });
        rvRight.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy<0){
                    //上滑
                    int position = ((LinearLayoutManager)rvRight.getLayoutManager()).findFirstVisibleItemPosition();
                    rvLeft.scrollToPosition(position);
                }else if(dy>0){
                    //下滑
                    int position = ((LinearLayoutManager)rvRight.getLayoutManager()).findLastVisibleItemPosition();
                    rvLeft.scrollToPosition(position);
                }

            }
        });
        rvLeft.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy<0){
                    int position = ((LinearLayoutManager)rvLeft.getLayoutManager()).findFirstVisibleItemPosition();
                    rvRight.scrollToPosition(position);
                }else if(dy>0){
                    int position = ((LinearLayoutManager)rvLeft.getLayoutManager()).findLastVisibleItemPosition();
                    rvRight.scrollToPosition(position);
                }
            }
        });

    }

    @Override
    public void requestNavigateListSuccess(List<NaviBean> data) {
        leftAdapter.setNewData(data);
        rightAdapter.setNewData(data);
    }

    @Override
    public void requestNavigateListFailed(String msg) {

    }


}

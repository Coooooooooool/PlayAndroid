package com.alex.playandroid.base;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alex.playandroid.mvp.BaseView;
import com.alex.playandroid.mvp.Presenter;
import com.alex.playandroid.receiver.NetBroadcastReceiver;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMvpFragment<P extends Presenter> extends Fragment implements BaseView {

    protected final String TAG = this.getClass().getSimpleName();

    //视图
    protected View rootView;

    protected P presenter;

    protected Unbinder unbinder;

    protected NetBroadcastReceiver netBroadcastReceiver;

    private boolean isInitData = false; /*标志位 判断数据是否初始化*/
    private boolean isVisibleToUser = false; /*标志位 判断fragment是否可见*/
    private boolean isPrepareView = false; /*标志位 判断view已经加载完成 避免空指针操作*/




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(bindLayout(),container,false);
        unbinder = ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepareView = true;
        initPresenter();
        initNetworkReceiver();
        initView();
        initData();
        initListener();

//        loadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        lazyload();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyload();
    }

    /*懒加载方法*/
    private void lazyload() {
        if (!isInitData && isVisibleToUser && isPrepareView) {
            isInitData = true;
            lazyLoadData();
        }
    }




    private void initPresenter() {
        presenter = createPresenter();
        if(presenter!=null)
            presenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter!=null)
            presenter.detachView();
        if(netBroadcastReceiver!=null)
            getActivity().unregisterReceiver(netBroadcastReceiver);
        unbinder.unbind();
    }

    protected abstract int bindLayout();
    protected abstract P createPresenter();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initListener();
    protected void lazyLoadData(){};

    private void initNetworkReceiver() {
        netBroadcastReceiver = new NetBroadcastReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(netBroadcastReceiver,filter);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}

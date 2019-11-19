package com.alex.playandroid.base;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alex.playandroid.app.ActivityCollector;
import com.alex.playandroid.mvp.BaseView;
import com.alex.playandroid.mvp.Presenter;
import com.alex.playandroid.receiver.NetBroadcastReceiver;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMvpActivity<P extends Presenter> extends AppCompatActivity implements BaseView {

    protected final String TAG = getClass().getSimpleName();


    protected Unbinder unbinder;

    protected Context context;

    protected NetBroadcastReceiver netBroadcastReceiver;

    protected P presenter;

    protected  void initPresenter(){
        if(presenter == null)
            presenter = createPresenter();
        presenter.attachView(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());
        context = this;
        unbinder = ButterKnife.bind(this);
        ActivityCollector.addActivity(this);

        //屏幕常亮
        getWindow().getDecorView().setKeepScreenOn(true);
        initPresenter();
        initNetworkReceiver();
        initView();
        initData();
        initListener();

    }



    protected abstract int bindLayout();
    protected abstract P createPresenter();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initListener();

    public void startActivity(Class<?> clz){
        startActivity(clz,null);
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActivityAndDestroy(Class<?> clz){
        startActivityAndDestroy(clz,null);
    }

    public void startActivityAndDestroy(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(netBroadcastReceiver !=null)
            unregisterReceiver(netBroadcastReceiver);
        unbinder.unbind();
        ActivityCollector.removeActivity(this);
        if(presenter !=null)
            presenter.detachView();

    }


    private void initNetworkReceiver() {
        netBroadcastReceiver = new NetBroadcastReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netBroadcastReceiver,filter);
    }

}

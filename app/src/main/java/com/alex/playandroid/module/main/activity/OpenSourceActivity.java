package com.alex.playandroid.module.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alex.playandroid.R;
import com.alex.playandroid.base.BaseActivity;
import com.alex.playandroid.base.BaseMvpActivity;
import com.alex.playandroid.module.main.model.OpenSource;
import com.alex.playandroid.mvp.Presenter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OpenSourceActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.rv_open_source)
    RecyclerView rvOpenSource;

    BaseQuickAdapter<OpenSource,BaseViewHolder> adapter;

    @Override
    protected int bindLayout() {
        return R.layout.activity_open_source;
    }



    @Override
    protected void initView() {
        rvOpenSource.setLayoutManager(new LinearLayoutManager(context));
        adapter = new BaseQuickAdapter<OpenSource, BaseViewHolder>(R.layout.rv_item_open_source) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, OpenSource item) {
                helper.setText(R.id.tv_project,item.getName());
                helper.setText(R.id.tv_description,item.getDesc());
            }
        };
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rvOpenSource.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        List<OpenSource> list = new ArrayList<>();
        list.add(new OpenSource("JakeWharton/ButterKnife","Bind Android views and callbacks to fields and methods","https://github.com/JakeWharton/butterknife"));
        list.add(new OpenSource("Justson/AgentWeb","AgentWeb is a powerful library based on Android WebView","https://github.com/Justson/AgentWeb"));
        list.add(new OpenSource("ReactiveX/RxJava","RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM","https://github.com/ReactiveX/RxJava"));
        list.add(new OpenSource("ReactiveX/RxAndroid","RxJava bindings for Android","https://github.com/ReactiveX/RxAndroid"));
        list.add(new OpenSource("square/retrofit","Type-safe HTTP client for Android and Java by Square,",""));
        list.add(new OpenSource("google/gson","A Java serialization/deserialization library to convert Java Objects into JSON and back","https://github.com/google/gson"));
        list.add(new OpenSource("google/flexbox-layout", "Flexbox for Android", "https://github.com/google/flexbox-layout"));
        list.add(new OpenSource("bumptech/glide", "An image loading and caching library for Android focused on smooth scrolling", "https://github.com/bumptech/glide"));

        list.add(new OpenSource("soulqw/SoulPermission","a permission check or request for android","https://github.com/soulqw/SoulPermission"));
        list.add(new OpenSource("the-pig-of-jungle/smart-show","Toast & Snackbar & TopBar & Dialog","https://github.com/the-pig-of-jungle/smart-show"));
        list.add(new OpenSource("wuhenzhizao/android-titlebar","通用，功能全面的自定义标题栏，支持沉浸式标题栏，颜色渐变，miui9","https://github.com/wuhenzhizao/android-titlebar"));
        list.add(new OpenSource("CymChad/BaseRecyclerViewAdapterHelper", "BRVAH:Powerful and flexible RecyclerAdapter", "https://github.com/CymChad/BaseRecyclerViewAdapterHelper"));
        list.add(new OpenSource("youth5201314/banner", "Android广告图片轮播控件，支持无限循环和多种主题，可以灵活设置轮播样式、动画、轮播和切换时间、位置、图片加载框架等！", "https://github.com/youth5201314/banner"));
        list.add(new OpenSource("scwang90/SmartRefreshLayout", "下拉刷新、上拉加载、二级刷新、淘宝二楼、RefreshLayout、OverScroll，Android智能下拉刷新框架，支持越界回弹、越界拖动，具有极强的扩展性，集成了几十种炫酷的Header和 Footer。", "https://github.com/scwang90/SmartRefreshLayout"));
        list.add(new OpenSource("hackware1993/MagicIndicator", "A powerful, customizable and extensible ViewPager indicator framework. As the best alternative of ViewPagerIndicator, TabLayout and PagerSlidingTabStrip —— 强大、可定制、易扩展的 ViewPager 指示器框架。是ViewPagerIndicator、TabLayout、PagerSlidingTabStrip的最佳替代品。支持角标，更支持在非ViewPager场景下使用（使用hide()、show()切换Fragment或使用setVisibility切换FrameLayout里的View等）", "https://github.com/hackware1993/MagicIndicator"));
        list.add(new OpenSource("daimajia/AndroidSwipeLayout", "The Most Powerful Swipe Layout!", "https://github.com/daimajia/AndroidSwipeLayout"));

        adapter.setNewData(list);
    }

    @Override
    protected void initListener() {
        titlebar.setListener((v, action, extra) -> {
            if(action == CommonTitleBar.ACTION_LEFT_TEXT)
                finish();
        });
        adapter.setOnItemClickListener((baseQuickAdapter, view, position) -> {
            OpenSource item = adapter.getItem(position);
            if(item!=null){
                Bundle bundle = new Bundle();
                bundle.putString("title",item.getName());
                bundle.putString("url",item.getLink());
                startActivity(WebActivity.class,bundle);
            }

        });
    }
}

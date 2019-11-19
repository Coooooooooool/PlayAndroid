package com.alex.playandroid.module.main.activity;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alex.playandroid.R;
import com.alex.playandroid.app.ActivityCollector;
import com.alex.playandroid.base.BaseMvpActivity;
import com.alex.playandroid.module.home.fragment.HomeFragment;
import com.alex.playandroid.module.knowledge.fragment.KnowledgeFragment;
import com.alex.playandroid.module.main.adapter.MainPageAdapter;
import com.alex.playandroid.module.main.contract.MainContract;
import com.alex.playandroid.module.main.presenter.MainPresenter;
import com.alex.playandroid.module.mine.activity.CollectionActivity;
import com.alex.playandroid.module.mine.activity.LoginActivity;
import com.alex.playandroid.module.navigate.fragment.NavigateFragment;
import com.alex.playandroid.module.project.fragment.ProjectFragment;
import com.alex.playandroid.utils.AppUtil;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.MainView {


    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.navigationBar)
    BottomNavigationBar navigationBar;
    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.layout_nav_header)
    RelativeLayout layoutNavHeader;
    @BindView(R.id.layout_ranking)
    LinearLayout layoutRanking;
    @BindView(R.id.layout_setting)
    FrameLayout layoutSetting;
    @BindView(R.id.layout_about)
    LinearLayout layoutAbout;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.layout_collection)
    LinearLayout layoutCollection;
    @BindView(R.id.layout_thank)
    LinearLayout layoutThank;
    @BindView(R.id.layout_share)
    LinearLayout layoutShare;
    @BindView(R.id.layout_exit)
    FrameLayout layoutExit;

    private String[] titles = {"首页", "体系", "导航", "项目"};

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void initView() {

        initViewPager();
        initNagivationBar();

        if (AppUtil.isLogged(context)) {
            tvAccount.setText(AppUtil.getUsername(context));
        } else {
            tvAccount.setText("点我即可登录哦");
        }
    }


    @Override
    public void initData() {
        if(AppUtil.isLogged(context))
            presenter.requestCoin();
    }

    @Override
    public void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                navigationBar.selectTab(i);
                titlebar.getCenterTextView().setText(titles[i]);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        navigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                viewPager.setCurrentItem(position);
                titlebar.getCenterTextView().setText(titles[position]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        titlebar.setListener((v, action, extra) -> {
            if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                drawerLayout.openDrawer(GravityCompat.START);
            } else if (action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
                startActivity(SearchActivity.class);
            }
        });
    }

    private void initViewPager() {
        MainPageAdapter mainPageAdapter = new MainPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainPageAdapter);
        mainPageAdapter.setFragments(
                HomeFragment.newInstance(),
                KnowledgeFragment.newInstance(),
                NavigateFragment.newInstance(),
                ProjectFragment.newInstance()
        );
        viewPager.setCurrentItem(0);
    }

    private void initNagivationBar() {
        navigationBar
                .setMode(BottomNavigationBar.MODE_SHIFTING)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
                .setActiveColor(R.color.white) //选中颜色
//                .setInActiveColor("#e9e6e6") //未选中颜色
                .setBarBackgroundColor(R.color.colorPrimary);//导航栏背景色
        navigationBar
                .addItem(new BottomNavigationItem(R.mipmap.icon_bottom_nav_home, "首页"))
                .addItem(new BottomNavigationItem(R.mipmap.icon_nav_type, "体系"))
                .addItem(new BottomNavigationItem(R.mipmap.icon_nav_website, "导航"))
                .addItem(new BottomNavigationItem(R.mipmap.icon_nav_demo, "项目"))
                .initialise();
    }


    @OnClick({R.id.img_avatar, R.id.tv_account, R.id.layout_nav_header, R.id.layout_ranking, R.id.layout_setting, R.id.layout_about, R.id.tv_integral, R.id.layout_collection, R.id.layout_thank,R.id.layout_share, R.id.layout_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_avatar:
                break;
            case R.id.tv_account:
                if (AppUtil.isLogged(context)) {
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.layout_nav_header:

                break;
            case R.id.layout_ranking:
                startActivity(CoinRankActivity.class);
                break;
            case R.id.layout_setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.layout_about:
                startActivity(AboutActivity.class);
                break;
            case R.id.layout_collection:
                startActivity(CollectionActivity.class);
                break;
            case R.id.tv_integral:
                break;
            case R.id.layout_thank:
                startActivity(OpenSourceActivity.class);
                break;
            case R.id.layout_share:
                break;
            case R.id.layout_exit:
                ActivityCollector.finishAllActivity();
                break;
        }
    }


    @Override
    public void requestCoinSuccess(Integer data) {
        tvIntegral.setText(String.valueOf(data));
    }

    @Override
    public void requestCoinFailed(String msg) {

    }

}

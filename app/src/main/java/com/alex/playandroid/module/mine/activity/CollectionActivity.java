package com.alex.playandroid.module.mine.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;

import com.alex.playandroid.R;
import com.alex.playandroid.base.BaseActivity;
import com.alex.playandroid.module.main.adapter.MainPageAdapter;
import com.alex.playandroid.module.mine.fragment.CollectionArticleFragment;
import com.alex.playandroid.module.mine.fragment.CollectionLinkFragment;
import com.alex.playandroid.utils.ResUtils;
import com.alex.playandroid.widget.ScaleTransitionPagerTitleView;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import butterknife.BindView;

public class CollectionActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.mi)
    MagicIndicator mi;
    @BindView(R.id.vp)
    ViewPager vp;

    private String[] titles = {"文章","网站"};



    @Override
    protected int bindLayout() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initView() {
        MainPageAdapter adapter = new MainPageAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        adapter.setFragments(
                CollectionArticleFragment.newInstance(),
                CollectionLinkFragment.newInstance()
        );
        initNavigator();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        titlebar.setListener((v, action, extra) -> {
            if(action == CommonTitleBar.ACTION_LEFT_TEXT)
                finish();
        });
    }

    private void initNavigator() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles == null ? 0 : titles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(titles[index]);
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setNormalColor(ResUtils.getColor(context, R.color.basic_ui_action_bar_text_alpha));
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setOnClickListener(v -> vp.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        mi.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mi, vp);
        vp.setCurrentItem(0);
    }

}

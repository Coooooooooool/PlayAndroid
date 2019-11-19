package com.alex.playandroid.module.knowledge.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;

import com.alex.playandroid.R;
import com.alex.playandroid.base.BaseActivity;
import com.alex.playandroid.module.knowledge.adapter.KnowledgeArticleListFragmentAdapter;
import com.alex.playandroid.module.main.model.ChapterBean;
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

public class KnowledgeActivity extends BaseActivity {


    @BindView(R.id.mi)
    MagicIndicator mi;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;

    private ChapterBean chapterBean;
    private int position;

    private KnowledgeArticleListFragmentAdapter adapter;

    @Override
    protected int bindLayout() {
        return R.layout.activity_knowledge;
    }

    @Override
    protected void initView() {
        chapterBean = (ChapterBean) getIntent().getSerializableExtra("chapter");
        position = getIntent().getIntExtra("position", 0);
        initTitleBar();
        initViewPager();
        initNavigator();
    }

    private void initTitleBar() {
        titlebar.getLeftTextView().setText(chapterBean.getName());
        titlebar.setListener((v, action, extra) -> {
            if(action == CommonTitleBar.ACTION_LEFT_TEXT){
                finish();
            }
        });
    }

    private void initViewPager() {
        //        MultiFragmentPagerAdapter<ChapterBean, KnowledgeArticleListFragment> adapter =
//                new MultiFragmentPagerAdapter<>(
//                        getSupportFragmentManager(),
//                        new MultiFragmentPagerAdapter.FragmentCreator<ChapterBean, KnowledgeArticleListFragment>() {
//                            @Override
//                            public KnowledgeArticleListFragment create(ChapterBean data, int pos) {
//                                return KnowledgeArticleListFragment.newInstance(data, pos);
//                            }
//
//                            @Override
//                            public String getTitle(ChapterBean data) {
//                                return data.getName();
//                            }
//                        });
//        vp.setAdapter(adapter);
//        adapter.setDataList(chapterBean.getChildren());
        adapter = new KnowledgeArticleListFragmentAdapter(getSupportFragmentManager(), chapterBean.getChildren());
        vp.setAdapter(adapter);
    }

    private void initNavigator() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return chapterBean == null ? 0 : chapterBean.getChildren().size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(chapterBean.getChildren().get(index).getName());
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
        vp.setCurrentItem(position);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }


}

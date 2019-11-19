package com.alex.playandroid.module.knowledge.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.alex.playandroid.module.knowledge.fragment.KnowledgeArticleListFragment;
import com.alex.playandroid.module.main.model.ChapterBean;

import java.util.List;

public class KnowledgeArticleListFragmentAdapter extends FragmentStatePagerAdapter {


    private List<ChapterBean> chapterBeans;

    public KnowledgeArticleListFragmentAdapter(FragmentManager fm,List<ChapterBean> chapterBeans) {
        super(fm);
        this.chapterBeans = chapterBeans;
    }

    @Override
    public Fragment getItem(int i) {
        KnowledgeArticleListFragment fragment = KnowledgeArticleListFragment.newInstance(chapterBeans.get(i),i);
        return fragment;
    }

    @Override
    public int getCount() {
        return chapterBeans.size();
    }
}

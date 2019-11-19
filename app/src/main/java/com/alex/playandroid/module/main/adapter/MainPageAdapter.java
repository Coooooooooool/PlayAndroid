package com.alex.playandroid.module.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPageAdapter extends FragmentPagerAdapter {

    private Fragment[] mFragments = null;
    private String[] mTitles = null;




    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments[i];
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.length;
    }

    public void setFragments(Fragment... mFragments) {
        this.mFragments = mFragments;
        notifyDataSetChanged();
    }

    public void setmTitles(String[] mTitles) {
        this.mTitles = mTitles;
    }
}

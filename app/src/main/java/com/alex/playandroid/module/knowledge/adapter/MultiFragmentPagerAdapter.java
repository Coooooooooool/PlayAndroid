package com.alex.playandroid.module.knowledge.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class MultiFragmentPagerAdapter<E,F extends Fragment> extends FragmentStatePagerAdapter {

    private final FragmentCreator<E,F> creator;
    private List<E> dataList;

    public MultiFragmentPagerAdapter(FragmentManager fm,FragmentCreator<E,F> creator) {
        super(fm);
        this.creator = creator;
    }

    public List<E> getDataList() {
        return dataList;
    }

    public void setDataList(List<E> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return creator.create(dataList.get(i),i);
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return creator.getTitle(dataList.get(position));
    }

    public interface FragmentCreator<E,F>{
        F create(E data,int position);
        String getTitle(E data);
    }

}

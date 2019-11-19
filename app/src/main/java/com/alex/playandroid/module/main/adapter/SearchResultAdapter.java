package com.alex.playandroid.module.main.adapter;

import android.support.annotation.NonNull;

import com.alex.playandroid.module.main.model.ArticleBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class SearchResultAdapter extends BaseQuickAdapter<ArticleBean,BaseViewHolder> {



    public SearchResultAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ArticleBean item) {

    }
}

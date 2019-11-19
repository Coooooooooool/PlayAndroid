package com.alex.playandroid.module.navigate.adapter;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.alex.playandroid.R;
import com.alex.playandroid.module.navigate.entity.NaviBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class NavigateLeftAdapter extends BaseQuickAdapter<NaviBean,BaseViewHolder> {

    public NavigateLeftAdapter() {
        super(R.layout.rv_item_navigate_left);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, NaviBean item) {
        TextView tvName = helper.itemView.findViewById(R.id.tv_name);
        tvName.setText(item.getName());

    }
}

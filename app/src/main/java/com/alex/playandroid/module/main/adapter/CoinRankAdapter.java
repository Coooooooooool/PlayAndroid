package com.alex.playandroid.module.main.adapter;

import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alex.playandroid.R;
import com.alex.playandroid.module.mine.model.CoinInfoBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class CoinRankAdapter extends BaseQuickAdapter<CoinInfoBean, BaseViewHolder> {

    private int maxCoinCount = 0;

    public CoinRankAdapter() {
        super(R.layout.rv_item_coin_rank);
    }

    @Override
    public void setNewData(@Nullable List<CoinInfoBean> data) {
        super.setNewData(data);
        if(data!=null && !data.isEmpty()){
            maxCoinCount = data.get(0).getCoinCount();
        }
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CoinInfoBean item) {
        ProgressBar pb = helper.getView(R.id.pb);
        doProgressAnim(pb,item.getCoinCount());

        int index = helper.getAdapterPosition() + 1;
        helper.setText(R.id.tv_index, "" + index);
        helper.setText(R.id.tv_user_name, item.getUsername());
        helper.setText(R.id.tv_coin_count, "" + item.getCoinCount());

        ImageView iv_index = helper.getView(R.id.iv_index);
        TextView tv_index = helper.getView(R.id.tv_index);
        if (index == 1) {
            iv_index.setImageResource(R.drawable.ic_rank_1);
            tv_index.setTextColor(ContextCompat.getColor(tv_index.getContext(), R.color.text_surface_alpha));
            tv_index.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv_index.getContext().getResources().getDimension(R.dimen.text_auxiliary));
        } else if (index == 2) {
            iv_index.setImageResource(R.drawable.ic_rank_2);
            tv_index.setTextColor(ContextCompat.getColor(tv_index.getContext(), R.color.text_surface_alpha));
            tv_index.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv_index.getContext().getResources().getDimension(R.dimen.text_auxiliary));
        } else if (index == 3) {
            iv_index.setImageResource(R.drawable.ic_rank_3);
            tv_index.setTextColor(ContextCompat.getColor(tv_index.getContext(), R.color.text_surface_alpha));
            tv_index.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv_index.getContext().getResources().getDimension(R.dimen.text_auxiliary));
        } else {
            iv_index.setImageResource(R.color.transparent);
            tv_index.setTextColor(ContextCompat.getColor(tv_index.getContext(), R.color.text_second));
            tv_index.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv_index.getContext().getResources().getDimension(R.dimen.text_content));
        }
    }

    private void doProgressAnim(ProgressBar progressBar,int coinCount){
        int f = 1000;
        progressBar.setMax(maxCoinCount * f);
        ValueAnimator animator = ValueAnimator.ofInt(0,coinCount);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> progressBar.setProgress((int)animation.getAnimatedValue()*f));
        animator.start();
    }
}

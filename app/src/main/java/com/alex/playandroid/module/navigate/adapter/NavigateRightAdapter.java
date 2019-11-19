package com.alex.playandroid.module.navigate.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.alex.playandroid.R;
import com.alex.playandroid.module.main.model.ArticleBean;
import com.alex.playandroid.module.navigate.entity.NaviBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayout;

import java.util.LinkedList;
import java.util.Queue;

public class NavigateRightAdapter extends BaseQuickAdapter<NaviBean,BaseViewHolder> {

    private LayoutInflater mInflater = null;
    private Queue<TextView> mFlexItemTextViewCaches = new LinkedList<>();
    private OnFlexboxLayoutItemClickListener onFlexboxLayoutItemClickListener;

    public NavigateRightAdapter() {
        super(R.layout.rv_item_navigate_right);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, NaviBean item) {
        TextView tvName = helper.itemView.findViewById(R.id.tv_name);
        tvName.setText(item.getName());
        FlexboxLayout fbl = helper.itemView.findViewById(R.id.fbl);

        for (int i = 0;i<item.getArticles().size();i++){
            ArticleBean bean = item.getArticles().get(i);
            TextView child = createOrGetCacheFlexTextView(fbl);
            child.setText(bean.getTitle());
            fbl.addView(child);
            int index = i;
            child.setOnClickListener(v -> {
                if(onFlexboxLayoutItemClickListener!=null)
                    onFlexboxLayoutItemClickListener.onClick(bean,index);
            });
        }
    }

    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
        FlexboxLayout fbl = holder.getView(R.id.fbl);
        for(int i = 0;i<fbl.getChildCount();i++){
            mFlexItemTextViewCaches.offer((TextView) fbl.getChildAt(i));
        }
        fbl.removeAllViews();
    }

    private TextView createOrGetCacheFlexTextView(FlexboxLayout flexboxLayout){
        TextView textView = mFlexItemTextViewCaches.poll();
        if(textView!=null)
            return textView;
        return createFlexTextView(flexboxLayout);
    }

    private TextView createFlexTextView(FlexboxLayout flexboxLayout){
        if(mInflater==null)
            mInflater = LayoutInflater.from(flexboxLayout.getContext());
        return (TextView) mInflater.inflate(R.layout.rv_item_navigate_right_child,flexboxLayout,false);
    }


    public void setOnFlexboxLayoutItemClickListener(OnFlexboxLayoutItemClickListener onFlexboxLayoutItemClickListener) {
        this.onFlexboxLayoutItemClickListener = onFlexboxLayoutItemClickListener;
    }

    public interface OnFlexboxLayoutItemClickListener{
        void onClick(ArticleBean bean,int position);
    }

}


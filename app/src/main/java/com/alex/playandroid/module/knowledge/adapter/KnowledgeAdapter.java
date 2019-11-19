package com.alex.playandroid.module.knowledge.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alex.playandroid.R;
import com.alex.playandroid.module.main.model.ChapterBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayout;

import java.util.LinkedList;
import java.util.Queue;

public class KnowledgeAdapter extends BaseQuickAdapter<ChapterBean,BaseViewHolder> {

    private LayoutInflater mInflater = null;
    private Queue<TextView> mFlexItemTextViewCaches = new LinkedList<>();
    private OnFlexboxLayoutItemClickListener onFlexboxLayoutItemClickListener = null;

    public KnowledgeAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, ChapterBean item) {
        helper.setText(R.id.tv_name,item.getName());
        FlexboxLayout fblCategory = helper.itemView.findViewById(R.id.fbl_category);
        for (int i = 0;i<item.getChildren().size();i++){
            final ChapterBean chapterBean = item.getChildren().get(i);
            TextView child = createOrGetCacheFlexItemTextView(fblCategory);
            child.setText(chapterBean.getName());
            fblCategory.addView(child);
            final int index = i;
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onFlexboxLayoutItemClickListener!=null)
                        onFlexboxLayoutItemClickListener.onClick(item,index);
                }
            });
        }
    }

    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
        FlexboxLayout fbl = holder.getView(R.id.fbl_category);
        for (int i = 0; i < fbl.getChildCount(); i++) {
            mFlexItemTextViewCaches.offer((TextView) fbl.getChildAt(i));
        }
        fbl.removeAllViews();
    }

    private TextView createOrGetCacheFlexItemTextView(FlexboxLayout fblCategory) {
        TextView tv = mFlexItemTextViewCaches.poll();
        if (tv != null) {
            return tv;
        }
        return createFlexItemTextView(fblCategory);
    }

    private TextView createFlexItemTextView(FlexboxLayout fblCategory) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(fblCategory.getContext());
        }
        return (TextView) mInflater.inflate(R.layout.rv_item_knowledge_child, fblCategory, false);
    }

    public void setOnFlexboxLayoutItemClickListener(OnFlexboxLayoutItemClickListener onFlexboxLayoutItemClickListener) {
        this.onFlexboxLayoutItemClickListener = onFlexboxLayoutItemClickListener;
    }

    public interface OnFlexboxLayoutItemClickListener{
        void onClick(ChapterBean chapterBean,int position);
    }

}

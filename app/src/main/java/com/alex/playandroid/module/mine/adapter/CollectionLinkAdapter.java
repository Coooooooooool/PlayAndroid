package com.alex.playandroid.module.mine.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;

import com.alex.playandroid.R;
import com.alex.playandroid.module.mine.model.CollectionLinkBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

public class CollectionLinkAdapter extends BaseQuickAdapter<CollectionLinkBean,BaseViewHolder> {


    private List<SwipeLayout> unCloseSwipeLayout  = new ArrayList<>();

    public CollectionLinkAdapter() {
        super(R.layout.rv_item_collection_link);
    }

    public void closeAll(SwipeLayout layout){
        for (SwipeLayout swipeLayout: unCloseSwipeLayout){
            if(layout == swipeLayout)
                continue;
            if(swipeLayout.getOpenStatus() != SwipeLayout.Status.Open)
                continue;
            swipeLayout.close();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING)
                    closeAll(null);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CollectionLinkBean item) {
        SwipeLayout swipeLayout = helper.getView(R.id.sl);
        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
                closeAll(layout);
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                unCloseSwipeLayout.add(layout);
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {
                unCloseSwipeLayout.remove(layout);
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });
        helper.setText(R.id.tv_title,Html.fromHtml(item.getName()));
        helper.setText(R.id.tv_url,item.getLink());
        helper.addOnClickListener(R.id.rl_top, R.id.tv_delete, R.id.tv_edit, R.id.tv_open, R.id.tv_copy);
    }
}

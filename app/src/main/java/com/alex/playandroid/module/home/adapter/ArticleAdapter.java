package com.alex.playandroid.module.home.adapter;

import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alex.playandroid.R;
import com.alex.playandroid.module.main.model.ArticleBean;
import com.alex.playandroid.utils.ImageUtil;
import com.alex.playandroid.utils.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.makeramen.roundedimageview.RoundedImageView;

public class ArticleAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {


    public ArticleAdapter() {
        super(R.layout.rv_item_article);
    }

    private OnItemChildViewClickListener onItemChildViewClickListener = null;

    public void setOnItemChildViewClickListener(OnItemChildViewClickListener mOnItemChildViewClickListener) {
        this.onItemChildViewClickListener = mOnItemChildViewClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ArticleBean item) {
        bindArticle(helper.itemView, item, new OnCollectListener() {
            @Override
            public void collect(ArticleBean item, LikeButton view) {
                if (onItemChildViewClickListener != null) {
                    onItemChildViewClickListener.onCollectClick(helper, view, helper.getAdapterPosition() - getHeaderLayoutCount());
                }
            }

            @Override
            public void uncollect(ArticleBean item, LikeButton view) {
                if (onItemChildViewClickListener != null) {
                    onItemChildViewClickListener.onCollectClick(helper, view, helper.getAdapterPosition() - getHeaderLayoutCount());
                }
            }
        });
    }


    public static void bindArticle(View view, ArticleBean item, OnCollectListener onCollectListener) {
        TextView tvNew = view.findViewById(R.id.tv_new);
        TextView tvTag = view.findViewById(R.id.tv_tag);
        TextView tvTime = view.findViewById(R.id.tv_time);
        TextView tvAuthor = view.findViewById(R.id.tv_author);
        RoundedImageView img = view.findViewById(R.id.iv_img);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvDesc = view.findViewById(R.id.tv_desc);
        TextView tvTop = view.findViewById(R.id.tv_top);
        TextView tvChapter = view.findViewById(R.id.tv_chapter_name);
        LikeButton btnCollect = view.findViewById(R.id.btn_collect);

        //新
        if (item.isFresh())
            tvNew.setVisibility(View.VISIBLE);
        else
            tvNew.setVisibility(View.GONE);
        //作者
        tvAuthor.setText(item.getAuthor());
        //标记
        if (item.getTags() != null && item.getTags().size() > 0) {
            tvTag.setVisibility(View.VISIBLE);
            tvTag.setText(item.getTags().get(0).getName());
        } else {
            tvTag.setVisibility(View.GONE);
        }
        //时间
        tvTime.setText(item.getNiceDate());
        //图片
        if (!TextUtils.isEmpty(item.getEnvelopePic())) {
            ImageUtil.show(img, item.getEnvelopePic());
            img.setVisibility(View.VISIBLE);
        } else {
            img.setVisibility(View.GONE);
        }
        //标题
        tvTitle.setText(Html.fromHtml(item.getTitle()));
        //描述
        if (TextUtils.isEmpty(item.getDesc())) {
            tvDesc.setVisibility(View.GONE);
            tvTitle.setSingleLine(false);
        } else {
            tvDesc.setVisibility(View.VISIBLE);
            tvTitle.setSingleLine(true);
            String desc = Html.fromHtml(item.getDesc()).toString();
            desc = StringUtils.removeAllBank(desc, 2);
            tvDesc.setText(desc);
        }
        //置顶
        if (item.isTop())
            tvTop.setVisibility(View.VISIBLE);
        else
            tvTop.setVisibility(View.GONE);
        //所属类别
        tvChapter.setText(Html.fromHtml(formatChapterName(item.getSuperChapterName(), item.getChapterName())));
        //收藏
//        if (item.isCollect()) {
//            btnCollect.setLiked(true);
//        } else {
//            btnCollect.setLiked(false);
//        }
        btnCollect.setLiked(item.isCollect());
//        btnCollect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(btnCollect.isLiked())
//                    onCollectListener.collect(item,btnCollect);
//                else
//                    onCollectListener
//            }
//        });
        btnCollect.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (onCollectListener != null)
                    onCollectListener.collect(item, likeButton);

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if (onCollectListener != null)
                    onCollectListener.uncollect(item, likeButton);

            }
        });
    }

    private static String formatChapterName(String... names) {
        StringBuilder format = new StringBuilder();
        for (String name : names) {
            if (!TextUtils.isEmpty(name)) {
                if (format.length() > 0) {
                    format.append(" · ");
                }
                format.append(name);
            }
        }
        return format.toString();
    }

    public interface OnItemChildViewClickListener {
        void onCollectClick(BaseViewHolder helper, LikeButton view, int position);
    }

    public interface OnCollectListener {
        void collect(ArticleBean item, LikeButton view);

        void uncollect(ArticleBean item, LikeButton view);
    }
}

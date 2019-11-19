package com.alex.playandroid.utils;

import android.widget.ImageView;

import com.alex.playandroid.R;
import com.bumptech.glide.Glide;

public class ImageUtil {

//    public static void image(ImageView imageView, String url){
//        GlideHelper.with(imageView.getContext())
//                .errorHolder(R.drawable.image_holder)
//                .placeHolder(R.drawable.image_holder)
//                .cache(true)
//                .load(url)
//                .into(imageView);
//    }

    public static void show(ImageView imageView,String url){
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.image_holder)
                .error(R.drawable.image_holder)
                .into(imageView);
    }

}

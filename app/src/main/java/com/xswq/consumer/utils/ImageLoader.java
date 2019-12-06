package com.xswq.consumer.utils;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.xswq.consumer.ConsumerApplication;
import com.xswq.consumer.R;

public class ImageLoader {

    private Activity activity;
    private Fragment fragment;
    private RequestManager manager;

    public ImageLoader(Activity activity) {
        this.activity = activity;
        manager = Glide.with(this.activity);
    }

    public ImageLoader(Fragment fragment) {
        this.fragment = fragment;
        manager = Glide.with(this.fragment);
    }

    /**
     * 获取RequestManager对象
     *
     * @return
     */
    public RequestManager getManager() {
        return manager;
    }

    /**
     * 加载普通图片
     *
     * @param object
     * @param error
     * @param view
     */
    public void loadImage(Object object, int error, ImageView view) {
        manager.load(object)
                .error(R.color.transparent)
                .into(view);
    }

    public void loadErroImage(Object object, int error, ImageView view) {
        manager.load(object)
                .error(error)
                .placeholder(error)
                .into(view);
    }

    /**
     * 加载圆形图片
     *
     * @param object
     * @param view
     */
    public void loadCircularImage(Object object, ImageView view) {
        manager.load(object)
                .error(R.color.transparent)
                .transform(new GlideCircleTransform(ConsumerApplication.getInstance()))
                .into(view);
    }
    public void loadHeadImage(Object object, ImageView view) {
        manager.load(object)
                .error(R.mipmap.tourist_head)
                .transform(new GlideCircleTransform(ConsumerApplication.getInstance()))
                .into(view);
    }
}


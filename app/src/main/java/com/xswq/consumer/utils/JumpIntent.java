package com.xswq.consumer.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import com.xswq.consumer.bean.LoginSignInBean;
import com.xswq.consumer.bean.MultimediaBean;

/**
 * Intent跳转工具类
 */
public class JumpIntent {

    public static void jump(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }

    public static void jump(Activity activity, Class<?> cls, MultimediaBean multimediaBean) {//音视频跳转
        Intent intent = new Intent(activity, cls);
        intent.putExtra("multimediaBean", multimediaBean);
        activity.startActivity(intent);
    }

    public static void jump(Activity activity, Class<?> cls, int number) {//跳微信充值
        Intent intent = new Intent(activity, cls);
        intent.putExtra("number",number);
        activity.startActivity(intent);
    }

    public static void jump(Activity activity, Class<?> cls, boolean isConfirm) {//跳充值页面
        Intent intent = new Intent(activity, cls);
        intent.putExtra("isConfirm",isConfirm);
        activity.startActivity(intent);
    }

    public static void jump(Activity activity, Class<?> cls, String type) {//跳webView
        Intent intent = new Intent(activity, cls);
        intent.putExtra("type",type);
        activity.startActivity(intent);
    }

}

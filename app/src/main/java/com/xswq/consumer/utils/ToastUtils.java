package com.xswq.consumer.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.xswq.consumer.ConsumerApplication;

/**
 * Toast工具类
 *
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ToastUtils {
    public static void toast(String content) {
        Toast.makeText(ConsumerApplication.getInstance(), content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 是否打开Toast显示开关
     */
    private static boolean isShow = true;

    private static Toast sToast;

    /**
     * 最常用的提示文本
     */
    public static void show(String message) {
        if (isShow) {
            if (sToast != null) {
                sToast.cancel();
                sToast = null;
            }
            sToast = Toast.makeText(ConsumerApplication.getInstance().getApplicationContext(), message, Toast.LENGTH_SHORT);
            sToast.show();
        }
    }


    /**
     * 直接显示文本
     *
     * @param messageId 需要显示的文字
     */
    public static void showShort(int messageId) {
        if (isShow) {
            if (sToast != null) {
                sToast.cancel();
                sToast = null;
            }
            sToast = Toast.makeText(ConsumerApplication.getInstance(), messageId, Toast.LENGTH_SHORT);
            sToast.show();
        }
    }

    /**
     * 直接显示文本
     *
     * @param message 需要显示的文字
     */
    public static void showShort(String message) {
        if (isShow) {
            if (sToast != null) {
                sToast.cancel();
                sToast = null;
            }
            sToast = Toast.makeText(ConsumerApplication.getInstance(), message, Toast.LENGTH_SHORT);
            sToast.show();
        }
    }

    /**
     * 直接显示文本
     *
     * @param messageId 需要显示的文字
     */
    public static void showLong(int messageId) {
        if (isShow) {
            if (sToast != null) {
                sToast.cancel();
                sToast = null;
            }
            sToast = Toast.makeText(ConsumerApplication.getInstance(), messageId, Toast.LENGTH_LONG);
            sToast.show();
        }
    }

    /**
     * 直接显示文本
     *
     * @param message 需要显示的文字
     */
    public static void showLong(String message) {
        if (isShow) {
            if (sToast != null) {
                sToast.cancel();
                sToast = null;
            }
            sToast = Toast.makeText(ConsumerApplication.getInstance(), message, Toast.LENGTH_LONG);
            sToast.show();
        }
    }

    /**
     * 显示toast在屏幕中央
     */
    public static void showToastCenter(String message) {
        if (isShow) {
            if (sToast != null) {
                sToast.cancel();
                sToast = null;
            }
            sToast = Toast.makeText(ConsumerApplication.getInstance(), message, Toast.LENGTH_SHORT);
            sToast.setGravity(Gravity.CENTER, 0, 0);
            sToast.show();
        }
    }

    /**
     * 直接显示文本
     *
     * @param messageId 需要显示的文字资源
     * @param duration  自定义显示时间
     */
    public static void show(int messageId, int duration) {
        if (isShow) {
            if (sToast != null) {
                sToast.cancel();
                sToast = null;
            }
            sToast = Toast.makeText(ConsumerApplication.getInstance(), messageId, duration);
            sToast.show();
        }
    }

    /**
     * 直接显示文本
     *
     * @param message  需要显示的文字
     * @param duration 自定义显示时间
     */
    public static void show(String message, int duration) {
        if (isShow) {
            if (sToast != null) {
                sToast.cancel();
                sToast = null;
            }
            sToast = Toast.makeText(ConsumerApplication.getInstance(), message, duration);
            sToast.show();
        }
    }

    /**
     * 带图片消息提示
     *
     * @param imageResourceId 图片资源
     * @param messageId       文字资源
     */
    public static void showImageAndText(int imageResourceId, int messageId) {
        Context context = ConsumerApplication.getInstance();
        showImageAndText(imageResourceId, context.getResources().getString(messageId), Toast.LENGTH_SHORT, Gravity.CENTER);
    }

    /**
     * 带图片消息提示
     *
     * @param imageResourceId 图片资源
     * @param message         文字
     */
    public static void showImageAndText(int imageResourceId, CharSequence message) {
        showImageAndText(imageResourceId, message, Toast.LENGTH_SHORT, Gravity.CENTER);
    }

    /**
     * 带图片消息提示
     *
     * @param imageResourceId 图片资源
     * @param message         文字
     * @param duration        显示时间
     * @param gravity         显示位置
     */
    public static void showImageAndText(int imageResourceId, CharSequence message, int duration, int gravity) {
        Toast toast = Toast.makeText(ConsumerApplication.getInstance(),
                message, duration);
        toast.setGravity(gravity, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(ConsumerApplication.getInstance());
        imageCodeProject.setImageResource(imageResourceId);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }
}

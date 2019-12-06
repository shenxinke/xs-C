package com.xswq.consumer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.xswq.consumer.R;

/**
 * @author Shenxinke
 * @version 4.0.0
 * @data 2018/4/19
 */

public class CommonProgressDialog extends Dialog implements DialogInterface.OnDismissListener{
    private Context context;
    private static CommonProgressDialog dialog;
    private ImageView ivProgress;
    private Animation animation;


    public CommonProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        setOnDismissListener(this);
    }
    /**显示dialog的方法*/
    public static CommonProgressDialog showDialog(Context context){
        //dialog样式
        dialog = new CommonProgressDialog(context, R.style.MyDialog);
        //dialog布局文件
        dialog.setContentView(R.layout.common_progress_dialog_layout);
        //点击外部不允许关闭dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        return dialog;
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus && null != dialog){
            ivProgress = (ImageView) dialog.findViewById(R.id.ivProgress);
            animation = AnimationUtils.loadAnimation(context, R.anim.dialog_progress_anim);
            ivProgress.startAnimation(animation);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        if(null != animation){
            animation = null;
        }
    }
}

package com.xswq.consumer.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xswq.consumer.R;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.utils.ConstUtils;

/**
 * 版本更新弹框提示
 */
public abstract class AbstractVersionCheckDialog {
    protected Dialog dialog;
    private TextView title;
    private Button cancle;
    private Button down;
    private Activity context;
    private boolean flag;
    private String versionContent;
    private boolean isCompel;

    public AbstractVersionCheckDialog(Activity activity, boolean flag, String versionContent,boolean isCompel) {
        this.context = activity;
        this.flag = flag;
        this.versionContent = versionContent;
        this.isCompel = isCompel;
        if (dialog == null) {
            getDialog(activity);
        }
    }

    private Dialog getDialog(Activity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_verson_update, null);
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);

        dialog.setContentView(view, new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
        title = view.findViewById(R.id.txt_msg);
        down = view.findViewById(R.id.but_dialog_down);
        cancle = view.findViewById(R.id.but_dialog_cancle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        if (!TextUtils.isEmpty(versionContent)) {
            String replace = versionContent.replace("&&", "\n");
            title.setText(ConstUtils.getStringNoEmpty(replace));
        } else {
            title.setText("暂无介绍");
        }
        if(isCompel){
            down.setVisibility(View.VISIBLE);
            cancle.setVisibility(View.GONE);
        }else {
            down.setVisibility(View.VISIBLE);
            cancle.setVisibility(View.VISIBLE);
        }
        Window dialogWindow = dialog.getWindow();
        android.view.WindowManager.LayoutParams lp = dialogWindow
                .getAttributes();
        // 设置生效
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);

        down.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onSureClick();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Const.ISGETNEWVERSION = false;
                dialog.dismiss();
            }
        });
        return dialog;
    }

    /**
     * 确定点击事件
     */
    public abstract void onSureClick();

    public void showDialog() {
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}

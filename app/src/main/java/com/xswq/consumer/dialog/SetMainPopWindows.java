package com.xswq.consumer.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.xswq.consumer.ConsumerApplication;
import com.xswq.consumer.R;
import com.xswq.consumer.activity.login.ChessLoginTypeActivity;
import com.xswq.consumer.activity.systemSettings.AboutUsActivity;
import com.xswq.consumer.activity.systemSettings.CDkeyConversionActivity;
import com.xswq.consumer.activity.systemSettings.CustomerFeedbackActivity;
import com.xswq.consumer.activity.systemSettings.HelpCenterActivity;
import com.xswq.consumer.activity.systemSettings.SystemSettingsActivity;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.MediahelperUtil;
import com.xswq.consumer.utils.PreferencesUtil;

import cn.jpush.android.api.JPushInterface;
import cn.udesk.UdeskSDKManager;
import cn.udesk.config.UdeskConfig;

public class SetMainPopWindows implements View.OnClickListener {

    private Activity activity;
    private PopupWindow popupWindow;

    //设置侧边窗口动画
    public void initPopupWindow(Activity activity) {
        try {
            this.activity = activity;
            View popupWindowView = activity.getLayoutInflater().inflate(R.layout.popup_setup_layout, null);
            popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

            //动画效果
            popupWindow.setAnimationStyle(R.style.AnimationRightFade);

            //菜单背景色
            ColorDrawable dw = new ColorDrawable(0xff);
            popupWindow.setBackgroundDrawable(dw);
            //显示位置
            popupWindow.showAtLocation(activity.getLayoutInflater().inflate(R.layout.activity_main_layout, null), Gravity.RIGHT, 0, 5);

            /**
             * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
             *
             */
            class popupDismissListener implements PopupWindow.OnDismissListener {

                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }

            }
            //设置背景半透明
            backgroundAlpha(0.5f);
            //关闭事件
            popupWindow.setOnDismissListener(new popupDismissListener());

            popupWindowView.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });

            ImageView setUpShutDown = popupWindowView.findViewById(R.id.set_up_shut_down);
            setUpShutDown.setOnClickListener(this);
            Button systemSetup = popupWindowView.findViewById(R.id.system_setup);
            systemSetup.setOnClickListener(this);
            Button systemAboutUs = popupWindowView.findViewById(R.id.system_about_us);
            systemAboutUs.setOnClickListener(this);
            Button systemHelper = popupWindowView.findViewById(R.id.system_helper);
            systemHelper.setOnClickListener(this);
            Button systemSwitchuser = popupWindowView.findViewById(R.id.system_switchuser);
            systemSwitchuser.setOnClickListener(this);
            Button checkForUpdates = popupWindowView.findViewById(R.id.check_for_updates);
            checkForUpdates.setOnClickListener(this);
            Button customerFeedback = popupWindowView.findViewById(R.id.customer_feedback);
            customerFeedback.setOnClickListener(this);

            Button cDkeyConversion = popupWindowView.findViewById(R.id.cdkey_button);
            cDkeyConversion.setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_up_shut_down:
                popupWindow.dismiss();
                break;
            case R.id.system_setup:
                JumpIntent.jump(activity, SystemSettingsActivity.class);
                popupWindow.dismiss();
                break;
            case R.id.system_about_us:
                JumpIntent.jump(activity, AboutUsActivity.class);
                popupWindow.dismiss();
                break;
            case R.id.system_helper:
                JumpIntent.jump(activity, HelpCenterActivity.class);
                popupWindow.dismiss();
                break;
            case R.id.system_switchuser:
                //  使用前需要设置的信息:
                UdeskSDKManager.getInstance().initApiKey(ConsumerApplication.getInstance(), Const.UDESK_DOMAIN,
                        Const.UDESK_SECRETKEY, Const.APPID);
                //咨询会话
                UdeskSDKManager.getInstance().entryChat(ConsumerApplication.getInstance(), UdeskConfig.createDefualt(), Const.TOKEN);
                popupWindow.dismiss();
                break;
            case R.id.check_for_updates:
                PreferencesUtil.removeKey(activity, "username");
                PreferencesUtil.removeKey(activity, "orderKey");
                JPushInterface.deleteAlias(ConsumerApplication.getmContext(), Integer.valueOf(Const.UID));
                Intent intent = new Intent(activity, ChessLoginTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                MediahelperUtil.release();
                popupWindow.dismiss();
                break;
            case R.id.customer_feedback:
                JumpIntent.jump(activity, CustomerFeedbackActivity.class);
                popupWindow.dismiss();
                break;
            case R.id.cdkey_button:
                JumpIntent.jump(activity, CDkeyConversionActivity.class);
                popupWindow.dismiss();
                break;
            default:
                popupWindow.dismiss();
                break;
        }
    }
}

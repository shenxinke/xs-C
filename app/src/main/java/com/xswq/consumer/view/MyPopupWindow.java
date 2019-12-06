package com.xswq.consumer.view;

import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import com.xswq.consumer.R;

public class MyPopupWindow {
    static PopupWindow popupWindow = null;

    public static void myPopupWindow(View view, View layout) {
        ImageView viewById = layout.findViewById(R.id.bg_image);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setAnimationStyle(R.style.myPopupAnimation);
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public static void dismissPopWindow() {
        popupWindow.dismiss();
    }
}

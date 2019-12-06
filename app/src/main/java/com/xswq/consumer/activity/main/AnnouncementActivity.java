package com.xswq.consumer.activity.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xswq.consumer.R;
import com.xswq.consumer.bean.AnnouncementBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.ImageLoader;
import com.xswq.consumer.utils.ToastUtils;
import com.xswq.consumer.view.MyPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;

public class AnnouncementActivity extends Activity {
    private int indext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getData();
    }

    //获取公告
    private void getData() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_GET_ANNOUNCEMENT)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    showEreoPopupWindow(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    AnnouncementBean announcementBean = GsonUtil.gsonToBean(response, AnnouncementBean.class, AnnouncementActivity.this);
                    if (announcementBean != null) {
                        if (announcementBean.getData() != null) {
                            int size = announcementBean.getData().size();
                            for (int i = 0; i < size; i++) {
                                AnnouncementBean.DataBean dataBean = announcementBean.getData().get(i);
                                showPopupWindow(dataBean.getImgUrl());
                                indext = size;
                            }
                        }
                    } else {
                        showEreoPopupWindow(Const.CONS_STR_ERROR);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //显示提示框
    private void showPopupWindow(String url) {
        final View layout = LayoutInflater.from(AnnouncementActivity.this).inflate(R.layout.announcement_pop_layout, null, false);
        View view = getWindow().getDecorView();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        final PopupWindow  popupWindow = new PopupWindow(layout, width, height, true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.myPopupAnimation);
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        ImageView imgCancel = layout.findViewById(R.id.shut_down);
        ImageLoader imageLoader = new ImageLoader(AnnouncementActivity.this);
        ImageView imgBg = layout.findViewById(R.id.iamge_backgroud);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.shut_down:
                        if(indext > 1){
                            popupWindow.dismiss();
                            indext--;
                        }else {
                            popupWindow.dismiss();
                            finish();
                            overridePendingTransition(0, 0);
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        //设置popwindow布局控件的点击事件
        imgCancel.setOnClickListener(listener);
        imageLoader.loadErroImage(url, R.mipmap.default_xswq, imgBg);
    }

    //显示错误提示框
    private void showEreoPopupWindow(String str) {
        View layout = LayoutInflater.from(AnnouncementActivity.this).inflate(R.layout.pop_window_layout, null, false);
        View view = getWindow().getDecorView();
        MyPopupWindow.myPopupWindow(view, layout);
        Button btnSava = layout.findViewById(R.id.button_save);
        Button btnCancel = layout.findViewById(R.id.button_cancel);
        TextView texContent = layout.findViewById(R.id.popup_window_content);
        texContent.setText(str);
        btnSava.setText("确认");
        btnCancel.setVisibility(View.GONE);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_save:
                        MyPopupWindow.dismissPopWindow();
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.button_cancel:
                        MyPopupWindow.dismissPopWindow();
                        break;
                    default:
                        break;
                }
            }
        };
        //设置popwindow布局控件的点击事件
        btnSava.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
    }
}

package com.xswq.consumer.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;
import com.xswq.consumer.dialog.CommonProgressDialog;

import butterknife.ButterKnife;

public abstract class BaseAppCompatActivity extends AppCompatActivity {
    private String TAG = "BaseAppCompatActivity";
    private CommonProgressDialog onLineDialog;//进度条显示

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(bindLayout());
        ButterKnife.bind(this);
        initData();
    }

    /**
     * 布局
     */
    public abstract int bindLayout();

    /**
     * 初始化页面数据
     */
    public abstract void initData();

    public void showProgressDialog() {
        if (null == onLineDialog) {
            onLineDialog = CommonProgressDialog.showDialog(BaseAppCompatActivity.this);
        }
        if (!BaseAppCompatActivity.this.isFinishing()) {
            try {
                onLineDialog.show();
            } catch (Exception e) {

            }
        }
    }

    public void dismissProgressDialog() {
        if (null != onLineDialog) {
            onLineDialog.dismiss();
        }
    }

    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}

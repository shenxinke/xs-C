package com.xswq.consumer.activity.main;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.xswq.consumer.R;
import com.xswq.consumer.activity.login.ChessLoginTypeActivity;
import com.xswq.consumer.base.BaseAppCompatActivity;
import com.xswq.consumer.bean.BeasBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.jpushmanager.MyJpushJump;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.PreferencesUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.text.SimpleDateFormat;
import java.util.Date;
import okhttp3.Call;


public class SplashActivity extends BaseAppCompatActivity {

    private String uid;
    private String extras;
    int o = 0;
    private String TAG = "SplashActivity";
    private String token;

    @Override
    public int bindLayout() {
        return R.layout.splash_layout;
    }

    @Override
    public void initData() {
        uid = PreferencesUtil.getString(SplashActivity.this, "uid");
        token = PreferencesUtil.getString(SplashActivity.this, "token");
        extras = PreferencesUtil.getString(SplashActivity.this, "extras");
        handler.sendEmptyMessageDelayed(1000, 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String loginTime = formatter.format(curDate);
        String loginTimer = formatter2.format(curDate);
        String data = loginTime.substring(0, loginTime.indexOf("日"));
        String preferencesData = PreferencesUtil.getString(SplashActivity.this, "logingRecord");
        if (!data.equals(preferencesData) && !TextUtils.isEmpty(uid)) {
            getInsertLoginRecord(loginTimer,data);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (o < 0) {
                if (!TextUtils.isEmpty(uid)) {
                    if (!TextUtils.isEmpty(extras)) {
                        MyJpushJump.getInternt(SplashActivity.this, extras);
                    } else {
                        JumpIntent.jump(SplashActivity.this, MainActivity.class);
                    }
                } else {
                    JumpIntent.jump(SplashActivity.this, ChessLoginTypeActivity.class);
                }
                finish();
            } else {
                handler.sendEmptyMessageDelayed(1000, 1000);
                o--;
            }
        }
    };

    private void getInsertLoginRecord(String loginTime, final String data) {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_INSERT_LOGIN_RECORD)
                    .addParams("token", token)
                    .addParams("uid", uid)
                    .addParams("loginTime", loginTime)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    BeasBean beasBean = GsonUtil.gsonToBean(response, BeasBean.class, SplashActivity.this);
                    if (beasBean != null) {
                        PreferencesUtil.putString(SplashActivity.this, "logingRecord", data);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

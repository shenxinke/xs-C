package com.xswq.consumer.activity.login;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.xswq.consumer.ConsumerApplication;
import com.xswq.consumer.R;
import com.xswq.consumer.activity.main.MainActivity;
import com.xswq.consumer.base.BaseAppCompatActivity;
import com.xswq.consumer.bean.LoginBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.PreferencesUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.xswq.consumer.view.MyPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

public class ChessLoginTypeActivity extends BaseAppCompatActivity implements View.OnClickListener {

    @BindView(R.id.login_we_chat)
    Button mWeChat;
    @BindView(R.id.login_phone)
    Button mPhoneLogin;
    @BindView(R.id.login_tourist)
    Button mTouristLogin;
    private String mobileCode;
    public static ChessLoginTypeActivity instance = null;

    @Override
    public int bindLayout() {
        return R.layout.activity_chess_login_type_layout;
    }

    @Override
    public void initData() {
        instance = this;
        mWeChat.setOnClickListener(this);
        mPhoneLogin.setOnClickListener(this);
        mTouristLogin.setOnClickListener(this);
        getJurisdiction();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_we_chat:
                if (ConstUtils.isClickable()) {
                    return;
                }
                if (Const.WX_API.isWXAppInstalled()) {
                    Const.WX_API.registerApp(Const.APP_ID);
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo";
                    Const.WX_API.sendReq(req);
                    showProgressDialog();
                } else {
                    showPopupWindow("请先安装微信");
                }
                break;
            case R.id.login_phone:
                JumpIntent.jump(ChessLoginTypeActivity.this, LogingActivity.class);
                break;
            case R.id.login_tourist:
                if (ConstUtils.isClickable()) {
                    return;
                }
                mobileCode = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                if (TextUtils.isEmpty(mobileCode)) {
                    showPopupWindow("该设备暂不支持!");
                } else {
                    showProgressDialog();
                    getLogin();
                }
                break;
            default:
                break;
        }
    }

    //登录
    private void getLogin() {
        try {
            PostFormBuilder post = OkHttpUtils.post();
            post.url(ContactUrl.POST_TOURIST_LOGIN_PATH);
            post.addParams("mobileCode", mobileCode);
            post.build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                    dismissProgressDialog();
                }

                @Override
                public void onResponse(String response, int id) {
                    LoginBean loginBean = GsonUtil.gsonToBean(response, LoginBean.class, ChessLoginTypeActivity.this);
                    if (loginBean != null) {
                        LoginBean.ErrorBean error = loginBean.getError();
                        if (Const.STR0.equals(error.getReturnCode())) {
                            LoginBean.DataBean data = loginBean.getData();
                            String uid = data.getUserInfo().getId();
                            String token = data.getUserInfo().getToken();
                            PreferencesUtil.putString(ChessLoginTypeActivity.this, "uid", uid);
                            PreferencesUtil.putString(ChessLoginTypeActivity.this, "token", token);
                            JPushInterface.setAlias(ConsumerApplication.getmContext(), Integer.valueOf(uid), uid);
                            JumpIntent.jump(ChessLoginTypeActivity.this, MainActivity.class);
                            dismissProgressDialog();
                            finish();
                        } else {
                            ToastUtils.show(error.getReturnMessage());
                        }
                    } else {
                        dismissProgressDialog();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //显示提示框
    private void showPopupWindow(String str) {
        View layout = LayoutInflater.from(ChessLoginTypeActivity.this).inflate(R.layout.pop_window_layout, null, false);
        View view = getWindow().getDecorView();
        MyPopupWindow.myPopupWindow(view, layout);
        Button btnSava = layout.findViewById(R.id.button_save);
        Button btnCancel = layout.findViewById(R.id.button_cancel);
        TextView texContent = layout.findViewById(R.id.popup_window_content);
        texContent.setText(str);
        btnSava.setText("确认");
        btnCancel.setVisibility(View.GONE);
        btnCancel.setText("取消");
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_save:
                        MyPopupWindow.dismissPopWindow();
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

    private void getJurisdiction() {
        /**
         * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
         */
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        dismissProgressDialog();
    }
}

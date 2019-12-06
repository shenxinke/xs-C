package com.xswq.consumer.activity.login;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.xswq.consumer.ConsumerApplication;
import com.xswq.consumer.R;
import com.xswq.consumer.activity.main.MainActivity;
import com.xswq.consumer.base.BaseAppCompatActivity;
import com.xswq.consumer.bean.LoginBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.dialog.CommonProgressDialog;
import com.xswq.consumer.jpushmanager.ExampleUtil;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.MD5Util;
import com.xswq.consumer.utils.MediahelperUtil;
import com.xswq.consumer.utils.PhoneUtil;
import com.xswq.consumer.utils.PreferencesUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.xswq.consumer.view.MyPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.android.service.JPushMessageReceiver;
import okhttp3.Call;

public class LogingActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private String TAG = "LogingActivity";

    @BindView(R.id.green_back)
    ImageView mGreenBack;
    @BindView(R.id.login_forget_password)
    TextView mForgetPassword;
    @BindView(R.id.login_register)
    TextView mRegister;
    @BindView(R.id.login_button)
    Button mLogin;
    @BindView(R.id.login_phone)
    EditText mPhone;
    @BindView(R.id.login_password)
    EditText mPassword;
    private String mobile;
    private String md5pass;
    private String mobileCode;
    private int userType = 0;
    public static LogingActivity instance = null;

    @Override
    public int bindLayout() {
        return R.layout.activity_login_layout;
    }

    @Override
    public void initData() {
        instance = this;
        MediahelperUtil.release();
        PreferencesUtil.removeKey(LogingActivity.this, "uid");
        PreferencesUtil.removeKey(LogingActivity.this, "token");
        mGreenBack.setOnClickListener(this);
        mForgetPassword.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        getJurisdiction();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_forget_password:
                ForgetPasswordActivity.openActivity(LogingActivity.this);
                break;
            case R.id.login_register:
                RegisterActivity.openActivity(LogingActivity.this);
                break;
            case R.id.login_button:
                if (ConstUtils.isClickableSuper()) {
                    return;
                }
                if (checkUserInformatiom()) {
                    showProgressDialog();
                    getLogin();
                }
                break;
            case R.id.green_back:
                finish();
                break;
            default:
                break;
        }
    }

    //校验用户名和密码
    public boolean checkUserInformatiom() {
        mobile = mPhone.getText().toString().trim();
        String passWord = mPassword.getText().toString().trim();

        if (!mobile.equals("")) {

            if (!PhoneUtil.isPhoneNumberValid(mobile)) {
                showPopupWindow("手机格式有误，请重新输入！");
                return false;
            }

        } else {
            showPopupWindow("请输入账号，不能为空！");
            return false;
        }

        if (!"".equals(passWord)) {
            if (PhoneUtil.isPasswordNO(passWord)) {
                md5pass = MD5Util.getMD5(passWord);
            } else {
                showPopupWindow("密码不能有特殊字符，长度在6-16位之间!");

            }
        } else {
            showPopupWindow("请输入密码，不能为空！");

            return false;
        }
        return true;
    }

    //登录
    private void getLogin() {
        try {
            PostFormBuilder post = OkHttpUtils.post();
            if (userType == 0) {
                post.url(ContactUrl.POST_LOGIN_PATH);
                post.addParams("mobile", mobile);
                post.addParams("md5pass", md5pass);
            } else {
                post.url(ContactUrl.POST_TOURIST_LOGIN_PATH);
                post.addParams("mobileCode", mobileCode);
            }
            post.build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                    dismissProgressDialog();
                }

                @Override
                public void onResponse(String response, int id) {
                    LoginBean loginBean = GsonUtil.gsonToBean(response, LoginBean.class, LogingActivity.this);
                    if (loginBean != null) {
                        LoginBean.ErrorBean error = loginBean.getError();
                        if (Const.STR0.equals(error.getReturnCode())) {
                            LoginBean.DataBean data = loginBean.getData();
                            String uid = data.getUserInfo().getId();
                            String token = data.getUserInfo().getToken();
                            PreferencesUtil.putString(LogingActivity.this, "uid", uid);
                            PreferencesUtil.putString(LogingActivity.this, "token", token);
                            JPushInterface.setAlias(ConsumerApplication.getmContext(), Integer.valueOf(uid), uid);
                            JumpIntent.jump(LogingActivity.this, MainActivity.class);
                            dismissProgressDialog();
                            if (ChessLoginTypeActivity.instance != null) {
                                ChessLoginTypeActivity.instance.finish();//调用
                            }
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
        View layout = LayoutInflater.from(LogingActivity.this).inflate(R.layout.pop_window_layout, null, false);
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

    @Override
    protected void onStop() {
        super.onStop();
        dismissProgressDialog();
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
}

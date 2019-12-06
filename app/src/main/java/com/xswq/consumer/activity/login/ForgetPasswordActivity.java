package com.xswq.consumer.activity.login;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xswq.consumer.R;
import com.xswq.consumer.base.BaseAppCompatActivity;
import com.xswq.consumer.bean.RegisterCodeBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.MD5Util;
import com.xswq.consumer.utils.LogUtil;
import com.xswq.consumer.utils.PhoneUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.xswq.consumer.view.MyPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 忘记密码
 */
public class ForgetPasswordActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private String TAG = "ForgetPasswordActivity";

    @BindView(R.id.green_back)
    ImageView mGreenBack;
    @BindView(R.id.forget_password_phone)
    EditText mPhone;
    @BindView(R.id.forget_password_new_password)
    EditText mPassword;
    @BindView(R.id.forget_password_again_new_password)
    EditText mAgainPassword;
    @BindView(R.id.forget_password_password)
    EditText mEditTextCode;
    @BindView(R.id.forget_password_button)
    Button mForgetPassword;
    @BindView(R.id.forget_password_button_background)
    Button mForgetPasswordCode;

    private String userId;
    private String md5pass;
    private String verificationCode;
    private String code;
    private String md5String;

    @Override
    public int bindLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initData() {
        mGreenBack.setOnClickListener(this);
        mForgetPassword.setOnClickListener(this);
        mForgetPasswordCode.setOnClickListener(this);
    }

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.green_back:
                finish();
                break;
            case R.id.forget_password_button:
                if (ConstUtils.isClickable()) {
                    return;
                }
                if (checkUserInformatiom(v)) {
                    getData();
                }
                break;
            case R.id.forget_password_button_background:
                if (ConstUtils.isClickable()) {
                    return;
                }
                if (isSendMessage(v)) {
                    getCode();
                }
                break;
            default:
                break;
        }
    }

    //校验手机号名和密码
    public boolean isSendMessage(View view) {
        userId = mPhone.getText().toString().trim();
        if (!userId.equals("")) {
            if (!PhoneUtil.isPhoneNumberValid(userId)) {
                showPopupWindow(view, "手机格式有误，请重新输入！");
                return false;
            }
        } else {
            showPopupWindow(view, "请输入账号，不能为空！");
            return false;
        }
        return true;
    }

    //获取验证码
    private void getCode() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_CODE_PATH)
                    .addParams("idFlg", Const.STR1)
                    .addParams("userType", Const.STR2)
                    .addParams("user_id", userId)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    RegisterCodeBean registerCodeBean = GsonUtil.gsonToBean(response, RegisterCodeBean.class, ForgetPasswordActivity.this);
                    if (registerCodeBean != null) {
                        RegisterCodeBean.ErrorBean error = registerCodeBean.getError();
                        String returnCode = error.getReturnCode();
                        if ("0".equals(returnCode)) {
                            md5String = registerCodeBean.getData();
                            ToastUtils.show(Const.CONS_STR_CODE_SEND);
                            ConstUtils.startCountDown(mForgetPasswordCode);
                        } else {
                            ToastUtils.show(error.getReturnMessage());
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //校验手机号名和密码
    public boolean checkUserInformatiom(View view) {
        userId = mPhone.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        verificationCode = mEditTextCode.getText().toString().trim();
        String againPassword = mAgainPassword.getText().toString().trim();
        md5pass = MD5Util.getMD5(password);
        LogUtil.e(TAG, md5pass);

        if (!userId.equals("")) {
            if (!PhoneUtil.isPhoneNumberValid(userId)) {
                showPopupWindow(view, "手机格式有误，请重新输入！");
                return false;
            }
        } else {
            showPopupWindow(view, "请输入账号，不能为空！");
            return false;
        }

        if (password.equals("")) {
            showPopupWindow(view, "请输入密码，不能为空！");
            return false;
        } else {
            if (!PhoneUtil.isPasswordNO(password)) {
                showPopupWindow(view, "密码不能有特殊字符，长度在6-16位之间！");
                return false;
            }
        }

        if (againPassword.equals("")) {
            showPopupWindow(view, "请输入确认密码，不能为空！");
            return false;

        } else {
            if (!PhoneUtil.isPasswordNO(againPassword)) {
                showPopupWindow(view, "密码不能有特殊字符，长度在6-16位之间！");
                return false;
            }
        }

        if (!password.equals(againPassword)) {
            showPopupWindow(view, "俩次密码输入不一致，请重新输入！");
            return false;
        }

        if (verificationCode.equals("")) {
            showPopupWindow(view, "验证码为空，请重新输入！");
            return false;
        } else {
            code = MD5Util.getMD5(userId + verificationCode).toUpperCase();
        }

        if (!TextUtils.isEmpty(md5String)) {
            if (!md5String.equals(code)) {
                showPopupWindow(view, "验证码错误，请重新输入！");
                return false;
            }
        } else {
            showPopupWindow(view, "请先获取验证码！");
            return false;
        }

        return true;
    }

    //找回密码
    private void getData() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_FORGETPASSWORD_PATH)
                    .addParams("idFlg", Const.STR1)
                    .addParams("userId", userId)
                    .addParams("md5pass", md5pass)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    try {
                        if (!TextUtils.isEmpty(response)) {
                            JSONObject object = new JSONObject(response);
                            JSONObject error = object.getJSONObject("error");
                            int returnCode = error.getInt("returnCode");
                            if (returnCode == 0) {
                                ToastUtils.show("修改成功!");
                                finish();
                            } else if (returnCode == 10048) {
                                ToastUtils.show(error.getString("returnMessage"));
                                Intent intent = new Intent(ForgetPasswordActivity.this,ChessLoginTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                ToastUtils.show(error.getString("returnMessage"));
                            }

                        } else {
                            ToastUtils.show(Const.CONS_STR_ERROR);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //显示提示框
    private void showPopupWindow(View view, String str) {
        View layout = LayoutInflater.from(ForgetPasswordActivity.this).inflate(R.layout.pop_window_layout, null, false);
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

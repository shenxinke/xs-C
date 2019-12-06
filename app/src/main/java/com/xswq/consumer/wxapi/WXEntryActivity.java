package com.xswq.consumer.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xswq.consumer.ConsumerApplication;
import com.xswq.consumer.activity.login.ChessLoginTypeActivity;
import com.xswq.consumer.activity.main.MainActivity;
import com.xswq.consumer.bean.LoginBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.PreferencesUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private String TAG = "WXEntryActivity";
    private SendAuth.Resp resp = null;
    private int WX_LOGIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Const.WX_API = WXAPIFactory.createWXAPI(this, Const.APP_ID, false);
        boolean handleIntent = Const.WX_API.handleIntent(getIntent(), this);
        if (!handleIntent) {
            finish();
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
        finish();
    }

    @Override
    public void onResp(BaseResp baseResp) {
        //微信登录为getType为1，分享为0
        if (baseResp.getType() == WX_LOGIN) {
            //登录回调
            resp = (SendAuth.Resp) baseResp;
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    String code = String.valueOf(resp.code);
                    if (!TextUtils.isEmpty(code)) {
                        getWxregPost(code);
                    }
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                    Toast.makeText(WXEntryActivity.this, "用户拒绝授权", Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                    Toast.makeText(WXEntryActivity.this, "取消登录", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        } else {
            //分享成功回调
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    //分享成功
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    //分享失败
                    Toast.makeText(WXEntryActivity.this, "分享失败", Toast.LENGTH_LONG).show();
                    break;
            }
        }
        finish();
    }

    private void getWxregPost(String code) {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_WX_LOGIN_PATH)
                    .addParams("code", code)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    LoginBean loginBean = GsonUtil.gsonToBean(response, LoginBean.class, WXEntryActivity.this);
                    if (loginBean != null) {
                        LoginBean.ErrorBean error = loginBean.getError();
                        if ("0".equals(error.getReturnCode())) {
                            LoginBean.DataBean data = loginBean.getData();
                            String uid = data.getUserInfo().getId();
                            String token = data.getUserInfo().getToken();
                            PreferencesUtil.putString(WXEntryActivity.this, "uid", uid);
                            PreferencesUtil.putString(WXEntryActivity.this, "token", token);
                            if(ChessLoginTypeActivity.instance != null){
                                ChessLoginTypeActivity.instance.finish();//调用
                            }
                            //别名设置（根据userId）
                            JPushInterface.setAlias(ConsumerApplication.getInstance(), Integer.valueOf(uid), uid);
                            JumpIntent.jump(WXEntryActivity.this, MainActivity.class);
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

}

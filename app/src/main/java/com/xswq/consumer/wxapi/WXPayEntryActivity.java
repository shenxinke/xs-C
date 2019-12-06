package com.xswq.consumer.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xswq.consumer.base.BaseBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.LogUtil;
import com.xswq.consumer.utils.PreferencesUtil;
import com.xswq.consumer.utils.RxCode;
import com.xswq.consumer.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import gorden.rxbus2.RxBus;
import okhttp3.Call;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Const.WX_API = WXAPIFactory.createWXAPI(this, Const.APP_ID, false);
        Const.WX_API.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Const.WX_API.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        LogUtil.e(TAG, "WXPayEntryActivity = " + req.openId);
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) { //支付成功
                String orderKey = PreferencesUtil.getString(WXPayEntryActivity.this, "orderKey");
                getBuyResult(orderKey);
                LogUtil.e(TAG, "aaaa");
            } else if (resp.errCode == -2) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "取消支付", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "支付失败", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            finish();
        }
    }

    //微信支付校验
    private void getBuyResult(String orderKey) {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_SELECT_ORDER_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("PurchaseId", orderKey)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show("支付失败");
                }

                @Override
                public void onResponse(String response, int id) {
                    BaseBean baseBean = GsonUtil.gsonToBean(response, BaseBean.class, WXPayEntryActivity.this);
                    if (baseBean != null) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "支付成功", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        LogUtil.e(TAG, "bbb");
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "支付失败", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    RxBus.get().send(RxCode.CODE_WX_PAY_SUCCEED_MESSAGE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

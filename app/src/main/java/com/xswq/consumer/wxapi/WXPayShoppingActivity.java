package com.xswq.consumer.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xswq.consumer.R;
import com.xswq.consumer.base.BaseAppCompatActivity;
import com.xswq.consumer.bean.WxPayBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.dialog.CommonProgressDialog;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.LogUtil;
import com.xswq.consumer.utils.PreferencesUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class WXPayShoppingActivity extends Activity {

    private String TAG = "WXPayShoppingActivity";
    private TextView mMoneyNumber;
    private Button mPayButton;
    private ImageView mPayLogo;
    private TextView texContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        final int number = intent.getIntExtra("number", 0);
        final String name = intent.getStringExtra("name");
        final String id = intent.getStringExtra("id");
        mMoneyNumber = findViewById(R.id.money_number);
        texContent = findViewById(R.id.popup_window_content);
        mPayLogo = findViewById(R.id.wx_pay_logo);
        mMoneyNumber.setText("¥" + number);
        mPayButton = findViewById(R.id.now_pay);
        mPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWxPay(number, name, id);
                LogUtil.e(TAG, "ccc");
            }
        });
    }

    //微信支付
    private void getWxPay(int number, String name, String id) {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_WX_PAY_PATH)
                    .addParams("totalPrice", String.valueOf(number))
                    .addParams("description", name)
                    .addParams("userId", Const.UID)
                    .addParams("orderStr", id)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    WxPayBean registerCodeBean = GsonUtil.gsonToBean(response, WxPayBean.class, WXPayShoppingActivity.this);
                    if (registerCodeBean != null) {
                        WxPayBean.ErrorBean error = registerCodeBean.getError();
                        String returnCode = error.getReturnCode();
                        if (Const.STR0.equals(returnCode)) {
                            WxPayBean.DataBean data = registerCodeBean.getData();
                            Const.WX_API = WXAPIFactory.createWXAPI(WXPayShoppingActivity.this, data.getAppId());  //应用ID 即微信开放平台审核通过的应用APPID
                            Const.WX_API.registerApp(data.getAppId());    //应用ID
                            PayReq payReq = new PayReq();
                            payReq.appId = data.getAppId();        //应用ID
                            payReq.partnerId = data.getMchId();      //商户号 即微信支付分配的商户号
                            payReq.prepayId = data.getPrepayId();        //预支付交易会话ID
                            payReq.packageValue = "Sign=WXPay";    //扩展字段
                            payReq.nonceStr = data.getNonceStr();        //随机字符串不长于32位。
                            payReq.timeStamp = "" + data.getTimeStamp(); //时间戳
                            payReq.sign = data.getPaySign();             //签名
                            Const.WX_API.sendReq(payReq);
                            String orderKey = data.getOrderKey();
                            LogUtil.e(TAG, "ddd");
                            PreferencesUtil.putString(WXPayShoppingActivity.this, "orderKey", orderKey);
                            finish();
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

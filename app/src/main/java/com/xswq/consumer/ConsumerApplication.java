package com.xswq.consumer;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.utils.AppFrontBackHelper;
import com.xswq.consumer.utils.CookiesManager;
import com.xswq.consumer.utils.MediahelperUtil;
import com.xswq.consumer.utils.OkHttpLog;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

public class ConsumerApplication extends Application {
    private String TAG = "ConsumerApplication";
    private static ConsumerApplication instance;
    public static Context mContext;
    public static Context getmContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mContext = getApplicationContext();
        instance = this;
        initOkClient();
        initWeChat();
        AppFrontBackHelper helper = new AppFrontBackHelper();
        helper.register(ConsumerApplication.this, new AppFrontBackHelper.OnAppStatusListener() {
            @Override
            public void onFront() {
                //应用切到前台处理
                MediahelperUtil.resume();
            }

            @Override
            public void onBack() {
                //应用切到后台处理
                MediahelperUtil.pause();
            }
        });
        JPushInterface.setDebugMode(true);
        JPushInterface.init(ConsumerApplication.this);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE,"");
        X5WebView();
    }

    private void X5WebView(){
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("app", " 开启TBS===X5加速成功 " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }

    private void initOkClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new OkHttpLog("OkHttpUtils"))
                .connectTimeout(15000L, TimeUnit.MILLISECONDS)
                .readTimeout(15000L, TimeUnit.MILLISECONDS)
                .writeTimeout(15000L, TimeUnit.MILLISECONDS)
                //cookie管理
                .cookieJar(new CookiesManager())
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    private void initWeChat() {
        if (Const.WX_API == null) {
            Const.WX_API = WXAPIFactory.createWXAPI(this, Const.APP_ID, true);
        }
    }

    public static ConsumerApplication getInstance() {
        return instance;
    }
}

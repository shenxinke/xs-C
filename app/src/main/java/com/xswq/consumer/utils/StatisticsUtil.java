package com.xswq.consumer.utils;

import android.content.Context;
import android.text.TextUtils;

import com.xswq.consumer.base.BaseBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * 模块使用统计接口
 */
public class StatisticsUtil {

    public static void getStatistics(final Context context, final String module) {
        try {
            OkHttpUtils.get().url(ContactUrl.POST_STATISTICS)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("module", module)
                    .addParams("terminalType", Const.STR2)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(String response, int id) {
                    if (!TextUtils.isEmpty(response)) {
                        BaseBean baseBean = GsonUtil.gsonToBean(response, BaseBean.class, context);
                        if (baseBean != null) {
                           LogUtil.e("StatisticsUtil: ",module);
                        }
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.xswq.consumer.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.xswq.consumer.activity.login.ChessLoginTypeActivity;
import com.xswq.consumer.config.Const;
import org.json.JSONException;
import org.json.JSONObject;

public class GsonUtil {

    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private GsonUtil() {
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls, Context context) {
        T t = null;
        try {
            if (isSucceed(gsonString, context)) {
                if (gson != null && !TextUtils.isEmpty(gsonString)) {
                    t = gson.fromJson(gsonString, cls);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    private static boolean isSucceed(String gsonString, Context context) {
        try {
            if (!TextUtils.isEmpty(gsonString)) {
                JSONObject object = new JSONObject(gsonString);
                JSONObject error = object.getJSONObject("error");
                int returnCode = error.getInt("returnCode");
                if (returnCode == 0) {
                    return true;
                } else if (returnCode == 10048) {
                    ToastUtils.show(error.getString("returnMessage"));
                    Intent intent = new Intent(context,ChessLoginTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else if (returnCode == 1){
                    if(TextUtils.isEmpty(Const.TOKEN)){
                        ToastUtils.show("用户过期，请重新登录!");
                        Intent intent = new Intent(context,ChessLoginTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }else {
                    ToastUtils.show(error.getString("returnMessage"));
                }

            } else {
                ToastUtils.show(Const.CONS_STR_ERROR);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}

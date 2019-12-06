package com.xswq.consumer.activity.systemSettings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.xswq.consumer.R;
import com.xswq.consumer.activity.login.ChessLoginTypeActivity;
import com.xswq.consumer.bean.CDKeyConver;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import okhttp3.Call;

public class CDkeyConversionActivity extends Activity implements View.OnClickListener {

    private TextView cdkeyError;
    private EditText cdkeyText;
    private String productName;
    private String productImg;
    private String productNums;
    private String productIds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdkey_conversion_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        cdkeyText = findViewById(R.id.cdkey_text);
        cdkeyError = findViewById(R.id.cdkey_error);
        Button greenButton = findViewById(R.id.green_button);
        greenButton.setOnClickListener(this);
        ImageView imageBack = findViewById(R.id.image_back);
        imageBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                overridePendingTransition(0, 0);
                break;
            case R.id.green_button:
                String CDK = cdkeyText.getText().toString().trim();
                if (!TextUtils.isEmpty(CDK)) {
                    getCdkeyConversion(CDK);
                }

                break;
            default:
                break;
        }
    }

    private void getCdkeyConversion(String CDK) {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_CDK_CONVERT)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("CDK", CDK)
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
                                Gson gson = new Gson();
                                CDKeyConver cdKeyConver = gson.fromJson(response, CDKeyConver.class);
                                if (cdKeyConver != null) {
                                    List<CDKeyConver.DataBean> data = cdKeyConver.getData();
                                    if (data != null) {
                                        cdkeyError.setVisibility(View.GONE);
                                        productName = data.get(0).getProductName();
                                        productImg = data.get(0).getProductImg();
                                        productNums = data.get(0).getProductNums();
                                        productIds = data.get(0).getProductIds();
                                        Intent intent = new Intent(CDkeyConversionActivity.this, ShowCdKeyConversion.class);
                                        intent.putExtra("productName", productName);
                                        intent.putExtra("productImg", productImg);
                                        intent.putExtra("productNums", productNums);
                                        startActivity(intent);
                                    } else {
                                        ToastUtils.show(Const.CONS_STR_ERROR);
                                    }
                                }
                            } else if (returnCode == 10048) {
                                ToastUtils.show(error.getString("returnMessage"));
                                Intent intent = new Intent(CDkeyConversionActivity.this, ChessLoginTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                cdkeyError.setVisibility(View.VISIBLE);
                                cdkeyError.setText(ConstUtils.getStringNoEmpty(error.getString("returnMessage")));
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
}

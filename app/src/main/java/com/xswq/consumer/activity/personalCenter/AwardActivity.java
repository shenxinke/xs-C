package com.xswq.consumer.activity.personalCenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xswq.consumer.R;
import com.xswq.consumer.base.BaseBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.ImageLoader;
import com.xswq.consumer.utils.RxCode;
import com.xswq.consumer.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.w3c.dom.Text;

import gorden.rxbus2.RxBus;
import okhttp3.Call;

public class AwardActivity extends Activity {

    private ImageView mAchievement, mAward, arrowsImage;
    private Button mSave;
    private TextView congratulationText, congratulation;
    private TextView productNumText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_award_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        congratulationText = findViewById(R.id.congratulation_text);
        congratulation = findViewById(R.id.congratulation);
        mAchievement = findViewById(R.id.achievement_image);
        arrowsImage = findViewById(R.id.arrows_image);
        mAward = findViewById(R.id.award_image);
        mSave = findViewById(R.id.button_save);
        productNumText = findViewById(R.id.text_product_num);

        Intent intent = getIntent();
        String imgUrl = intent.getStringExtra("imgUrl");
        String productImg = intent.getStringExtra("productImg");
        String achieveName = intent.getStringExtra("achieveName");
        final String productId = intent.getStringExtra("productId");
        final int productNum = intent.getIntExtra("productNum", 0);

        if (TextUtils.isEmpty(achieveName)) {
            congratulationText.setVisibility(View.VISIBLE);
            mAchievement.setVisibility(View.VISIBLE);
            arrowsImage.setVisibility(View.VISIBLE);
            mAward.setVisibility(View.VISIBLE);
            ImageLoader imageLoader = new ImageLoader(AwardActivity.this);
            imageLoader.loadImage(imgUrl, 0, mAchievement);
            imageLoader.loadImage(productImg, 0, mAward);
            if(productNum > 1){
                productNumText.setText("x"+productNum);
            }
        } else {
            congratulation.setVisibility(View.VISIBLE);
            congratulation.setText("恭喜获得" + achieveName + "成就");
        }


        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConstUtils.isClickable()) {
                    return;
                }
                getReceiveAward(productId, productNum);
            }
        });
    }

    //领取成就奖励
    private void getReceiveAward(String productId, int productNum) {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_RECEIVE_AWARD_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("productIds", productId)
                    .addParams("productNums",String.valueOf(productNum))
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    BaseBean baseBean = GsonUtil.gsonToBean(response, BaseBean.class, AwardActivity.this);
                    if (baseBean != null) {
                        RxBus.get().send(RxCode.CODE_ACHUEVEMENT_LIST_MESSAGE);
                        finish();
                        overridePendingTransition(android.R.anim.slide_in_left, R.anim.out_to_down);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

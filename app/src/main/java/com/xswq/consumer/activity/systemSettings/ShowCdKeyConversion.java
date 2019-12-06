package com.xswq.consumer.activity.systemSettings;

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
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.ImageLoader;

public class ShowCdKeyConversion extends Activity implements View.OnClickListener {

    private int number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cdkey_conversion_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageLoader imageLoader = new ImageLoader(ShowCdKeyConversion.this);
        TextView shoppingName = findViewById(R.id.shopping_name);
        ImageView productImage = findViewById(R.id.product_image);

        Button button = findViewById(R.id.yellow_button_bg);
        button.setOnClickListener(this);
        Intent intent = getIntent();
        String productName = intent.getStringExtra("productName");
        String productImg = intent.getStringExtra("productImg");
        String productNums = intent.getStringExtra("productNums");
        if (!TextUtils.isEmpty(productNums)) {
            number = Integer.parseInt(productNums);
        }
        if (TextUtils.isEmpty(productName)) {
            shoppingName.setText("水滴 ×50");
        } else {
            if (number > 1) {
                shoppingName.setText(ConstUtils.getStringNoEmpty(productName) + "×" + productNums);
            } else {
                shoppingName.setText(ConstUtils.getStringNoEmpty(productName));
            }
        }
        if (!TextUtils.isEmpty(productImg)) {
            imageLoader.loadImage(productImg, 0, productImage);
        } else {
            productImage.setBackgroundResource(R.mipmap.main_money_image);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yellow_button_bg:
                finish();
                overridePendingTransition(0, 0);
                break;
            default:
                break;
        }
    }
}

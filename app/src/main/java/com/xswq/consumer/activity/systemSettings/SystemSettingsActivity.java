package com.xswq.consumer.activity.systemSettings;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import com.xswq.consumer.R;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.MediahelperUtil;
import com.xswq.consumer.utils.PreferencesUtil;

public class SystemSettingsActivity extends Activity implements View.OnClickListener {

    private ImageView mItemBg1;
    private ImageView mItemCheck1;
    private ImageView mItemBg2;
    private ImageView mItemCheck2;
    private ImageView mItemBg3;
    private ImageView mItemCheck3;
    private ImageView mShutDown;
    private Button mSave;
    private boolean musictoneicon;
    private boolean speakericon;
    private boolean specialEffects;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_settings_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        musictoneicon = PreferencesUtil.getBoolean(SystemSettingsActivity.this, "musictoneicon");
        speakericon = PreferencesUtil.getBoolean(SystemSettingsActivity.this, "speakericon");
        specialEffects = PreferencesUtil.getBoolean(SystemSettingsActivity.this, "special_effects");

        mShutDown = findViewById(R.id.set_up_shut_down);
        mShutDown.setOnClickListener(this);
        mSave = findViewById(R.id.btn_setup_system);
        mSave.setOnClickListener(this);

        mItemBg1 = findViewById(R.id.settings_item_bg_1);
        mItemCheck1 = findViewById(R.id.settings_item_check1);
        mItemBg1.setOnClickListener(this);
        mItemCheck1.setOnClickListener(this);

        mItemBg2 = findViewById(R.id.settings_item_bg_2);
        mItemCheck2 = findViewById(R.id.settings_item_check2);
        mItemBg2.setOnClickListener(this);
        mItemCheck2.setOnClickListener(this);

        mItemBg3 = findViewById(R.id.settings_item_bg_3);
        mItemCheck3 = findViewById(R.id.settings_item_check3);
        mItemBg3.setOnClickListener(this);
        mItemCheck3.setOnClickListener(this);

        if (musictoneicon) {
            mItemCheck1.setBackgroundResource(R.mipmap.settings_item_uncheck);
        } else {
            mItemCheck1.setBackgroundResource(R.mipmap.settings_item_check);
        }

        if (speakericon) {
            mItemCheck2.setBackgroundResource(R.mipmap.settings_item_uncheck);
        } else {
            mItemCheck2.setBackgroundResource(R.mipmap.settings_item_check);
        }

        if (specialEffects) {
            mItemCheck3.setBackgroundResource(R.mipmap.settings_item_uncheck);
        } else {
            mItemCheck3.setBackgroundResource(R.mipmap.settings_item_check);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_item_bg_1:
            case R.id.settings_item_check1:
                if (ConstUtils.isClickable()) {
                    return;
                }
                if (!musictoneicon) {
                    musictoneicon = true;
                    mItemCheck1.setBackgroundResource(R.mipmap.settings_item_uncheck);
                } else {
                    musictoneicon = false;
                    mItemCheck1.setBackgroundResource(R.mipmap.settings_item_check);
                }
                break;
            case R.id.settings_item_bg_2:
            case R.id.settings_item_check2:
                if (ConstUtils.isClickable()) {
                    return;
                }
                if (speakericon) {
                    speakericon = false;
                    mItemCheck2.setBackgroundResource(R.mipmap.settings_item_check);
                } else {
                    speakericon = true;
                    mItemCheck2.setBackgroundResource(R.mipmap.settings_item_uncheck);
                }
                break;
            case R.id.settings_item_bg_3:
            case R.id.settings_item_check3:
                if (ConstUtils.isClickable()) {
                    return;
                }
                if (specialEffects) {
                    specialEffects = false;
                    mItemCheck3.setBackgroundResource(R.mipmap.settings_item_check);
                } else {
                    specialEffects = true;
                    mItemCheck3.setBackgroundResource(R.mipmap.settings_item_uncheck);
                }
                break;
            case R.id.btn_setup_system:
                if (!musictoneicon) {
                    MediahelperUtil.playSound(R.raw.main_bg, SystemSettingsActivity.this);
                } else {
                    MediahelperUtil.release();
                }
                PreferencesUtil.putBoolean(SystemSettingsActivity.this, "musictoneicon", musictoneicon);
                PreferencesUtil.putBoolean(SystemSettingsActivity.this, "speakericon", speakericon);
                PreferencesUtil.putBoolean(SystemSettingsActivity.this, "special_effects", specialEffects);
                finish();
                overridePendingTransition(0, 0);
                break;
            case R.id.set_up_shut_down:
                if (ConstUtils.isClickable()) {
                    return;
                }
                finish();
                overridePendingTransition(0, 0);
                break;
            default:
                break;

        }
    }
}

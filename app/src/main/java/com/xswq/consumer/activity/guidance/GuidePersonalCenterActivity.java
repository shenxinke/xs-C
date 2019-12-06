package com.xswq.consumer.activity.guidance;

import android.view.View;
import android.widget.ImageView;

import com.xswq.consumer.R;
import com.xswq.consumer.activity.personalCenter.PersonalCenterActivity;
import com.xswq.consumer.base.BaseGuideActivity;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.PreferencesUtil;

import butterknife.BindView;

public class GuidePersonalCenterActivity extends BaseGuideActivity implements View.OnClickListener {
    @BindView(R.id.green_back)
    ImageView mBlack;

    @Override
    public int bindLayout() {
        return R.layout.activity_guide_personal_center_layout;
    }

    @Override
    public void initData() {
        mBlack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.green_back:
                if (ConstUtils.isClickable()) {
                    return;
                }
                PreferencesUtil.putBoolean(GuidePersonalCenterActivity.this, "personalInformation", true);
                if (PersonalCenterActivity.instance != null) {
                    PersonalCenterActivity.instance.finish();
                }
                finish();
                break;
            default:
                break;
        }
    }
}
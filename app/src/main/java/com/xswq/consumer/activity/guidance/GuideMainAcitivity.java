package com.xswq.consumer.activity.guidance;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.xswq.consumer.R;
import com.xswq.consumer.activity.main.PlayChessActivity;
import com.xswq.consumer.activity.main.StoryActivity;
import com.xswq.consumer.activity.personalCenter.PersonalCenterActivity;
import com.xswq.consumer.activity.playChessWebView.ActivityPlayChessActivity;
import com.xswq.consumer.base.BaseGuideActivity;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.ImageLoader;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.PreferencesUtil;

import butterknife.BindView;

public class GuideMainAcitivity extends BaseGuideActivity implements View.OnClickListener {
    @BindView(R.id.main_story)
    ImageView mStory;
    @BindView(R.id.main_story_guide_text)
    ImageView mStoryGuideText;
    @BindView(R.id.main_story_guide_head)
    ImageView mStoryGuideHead;

    @BindView(R.id.main_play_chess)
    ImageView mPlayChess;
    @BindView(R.id.main_play_chess_guide_text)
    ImageView getmPlayChessGuideText;
    @BindView(R.id.main_play_chess_guide_head)
    ImageView getmPlayChessGuideHead;

    @BindView(R.id.main_wonderfuk_activity)
    ImageView mWonderfukActivity;
    @BindView(R.id.main_wonderfuk_guide_text)
    ImageView mWonderfukGuideText;
    @BindView(R.id.main_wonderfuk_guide_head)
    ImageView mWonderfukGuideHead;

    @BindView(R.id.image_head)
    ImageView mImageHead;
    @BindView(R.id.main_image_head)
    ImageView mMainImageHead;
    @BindView(R.id.main_personage_guide_head)
    ImageView mPersonageHead;
    @BindView(R.id.main_personage_guide_text)
    ImageView mPersonageText;

    @Override
    public int bindLayout() {
        return R.layout.activity_guide_main_layout;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String imageHead = intent.getStringExtra("type");
        ImageLoader imageLoader = new ImageLoader(GuideMainAcitivity.this);
        boolean oneClassGuide = PreferencesUtil.getBoolean(GuideMainAcitivity.this, "oneClassGuide");
        boolean personalInformation = PreferencesUtil.getBoolean(GuideMainAcitivity.this, "personalInformation");
        boolean twoClassGuide = PreferencesUtil.getBoolean(GuideMainAcitivity.this, "twoClassGuide");
        boolean playChessGuide = PreferencesUtil.getBoolean(GuideMainAcitivity.this, "playChessGuide");
        boolean activityGuide = PreferencesUtil.getBoolean(GuideMainAcitivity.this, "activityGuide");
        if (!oneClassGuide) {
            mStory.setOnClickListener(this);
            mStory.setVisibility(View.VISIBLE);
            mStoryGuideText.setVisibility(View.VISIBLE);
            mStoryGuideHead.setVisibility(View.VISIBLE);
            mStoryGuideText.setBackgroundResource(R.mipmap.guide_main_sotry_game);
        } else if (!personalInformation) {
            mPersonageHead.setOnClickListener(this);
            mImageHead.setOnClickListener(this);
            mImageHead.setVisibility(View.VISIBLE);
            mPersonageHead.setVisibility(View.VISIBLE);
            mPersonageText.setVisibility(View.VISIBLE);
            imageLoader.loadHeadImage(ConstUtils.getStringNoEmpty(imageHead), mImageHead);
        } else if (!twoClassGuide) {
            mStory.setOnClickListener(this);
            mStory.setVisibility(View.VISIBLE);
            mStoryGuideText.setVisibility(View.VISIBLE);
            mStoryGuideHead.setVisibility(View.VISIBLE);
            mStoryGuideText.setBackgroundResource(R.mipmap.guide_main_sotry);
            mStoryGuideText.getLayoutParams().height = 60;
        } else if (!playChessGuide) {
            mPlayChess.setOnClickListener(this);
            mPlayChess.setVisibility(View.VISIBLE);
            getmPlayChessGuideText.setVisibility(View.VISIBLE);
            getmPlayChessGuideHead.setVisibility(View.VISIBLE);
        } else if (!activityGuide) {
            mWonderfukActivity.setOnClickListener(this);
            mWonderfukActivity.setVisibility(View.VISIBLE);
            mWonderfukGuideText.setVisibility(View.VISIBLE);
            mWonderfukGuideHead.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_story:
                if (ConstUtils.isClickable()) {
                    return;
                }
                JumpIntent.jump(GuideMainAcitivity.this, StoryActivity.class);
                finish();
                break;
            case R.id.main_play_chess:
                if (ConstUtils.isClickable()) {
                    return;
                }
                JumpIntent.jump(GuideMainAcitivity.this, PlayChessActivity.class);
                finish();
                break;
            case R.id.main_wonderfuk_activity:
                if (ConstUtils.isClickable()) {
                    return;
                }
                JumpIntent.jump(GuideMainAcitivity.this, ActivityPlayChessActivity.class);
                finish();
                break;
            case R.id.image_head:
            case R.id.main_personage_guide_head:
                if (ConstUtils.isClickable()) {
                    return;
                }
                JumpIntent.jump(GuideMainAcitivity.this, PersonalCenterActivity.class);
                finish();
                break;
            default:
                break;

        }
        finish();
    }
}

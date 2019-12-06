package com.xswq.consumer.activity.personalCenter;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.xswq.consumer.R;
import com.xswq.consumer.adapter.ContentsPagerAdapter;
import com.xswq.consumer.base.BaseAppCompatActivity;
import com.xswq.consumer.fragment.HeadPortraitFragment;
import com.xswq.consumer.fragment.PictureFrameFragment;
import com.xswq.consumer.utils.ConstUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 更换头像页面
 */
public class ChessHeadImageActivity extends BaseAppCompatActivity implements View.OnClickListener {

    @BindView(R.id.check_head_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.achievement_select)
    Button mHeadPortrait;
    @BindView(R.id.achievement_no_select)
    Button mHeadPortraitFrame;
    @BindView(R.id.green_back)
    ImageView mBlack;

    private List<Fragment> listFragment;


    @Override
    public int bindLayout() {
        return R.layout.activity_chess_head_image_layout;
    }

    @Override
    public void initData() {
        mBlack.setOnClickListener(this);
        mHeadPortrait.setOnClickListener(this);
        mHeadPortraitFrame.setOnClickListener(this);
        getTabViewPage();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.achievement_select:
                if (ConstUtils.isClickable()) {
                    return;
                }
                mHeadPortrait.setBackgroundResource(R.mipmap.achievement_select);
                mHeadPortraitFrame.setBackgroundResource(R.mipmap.achievement_no_select);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.achievement_no_select:
                if (ConstUtils.isClickable()) {
                    return;
                }
                mHeadPortrait.setBackgroundResource(R.mipmap.achievement_no_select);
                mHeadPortraitFrame.setBackgroundResource(R.mipmap.achievement_select);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.green_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void getTabViewPage() {
        //初始化fragment
        listFragment = new ArrayList<>();
        HeadPortraitFragment headPortraitFragment = new HeadPortraitFragment();
        PictureFrameFragment pictureFrameFragment = new PictureFrameFragment();
        listFragment.add(headPortraitFragment);
        listFragment.add(pictureFrameFragment);
        mViewPager.setAdapter(new ContentsPagerAdapter(getSupportFragmentManager(), listFragment));
    }
}

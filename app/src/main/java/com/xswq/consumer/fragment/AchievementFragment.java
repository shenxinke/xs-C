package com.xswq.consumer.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xswq.consumer.R;
import com.xswq.consumer.adapter.AchievementAdapter;
import com.xswq.consumer.adapter.ContentsPagerAdapter;
import com.xswq.consumer.base.BaseFragment;
import com.xswq.consumer.bean.AchievementBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.RxCode;
import com.xswq.consumer.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import gorden.rxbus2.RxBus;
import gorden.rxbus2.ThreadMode;
import okhttp3.Call;

/**
 * 个人中心成就
 */
public class AchievementFragment extends BaseFragment implements View.OnClickListener {

    private View mView;
    private ViewPager mViewPager;
    private Button mAchievementSelect;
    private Button mAchievementUnSelect;
    private ImageView redDot;
    private List<Fragment> listFragment;

    @Override
    protected int setContentView() {
        return R.layout.fragment_achievement_layout;
    }

    @Override
    protected void startLoad() {
        RxBus.get().register(this);
    }

    @Override
    protected void initData() {
        if (mView == null) {
            mView = getContentView();
        }
        mViewPager = findViewById(R.id.personal_cente_special_viewpager);
        redDot = findViewById(R.id.red_dot);
        mAchievementSelect = findViewById(R.id.achievement_select);
        mAchievementSelect.setOnClickListener(this);
        mAchievementUnSelect = findViewById(R.id.achievement_no_select);
        mAchievementUnSelect.setOnClickListener(this);
        getTabViewPage();
        if (Const.IS_SHOW_RED_DOT) {
            redDot.setVisibility(View.VISIBLE);
        } else {
            redDot.setVisibility(View.GONE);
        }
    }

    @Override
    protected void stopLoad() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.achievement_select:
                mAchievementSelect.setBackgroundResource(R.mipmap.achievement_select);
                mAchievementUnSelect.setBackgroundResource(R.mipmap.achievement_no_select);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.achievement_no_select:
                mAchievementSelect.setBackgroundResource(R.mipmap.achievement_no_select);
                mAchievementUnSelect.setBackgroundResource(R.mipmap.achievement_select);
                mViewPager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }


    private void getTabViewPage() {
        //初始化fragment
        listFragment = new ArrayList<>();
        AchuevementListFragment achuevementListFragment = new AchuevementListFragment();
        UnAchuevementListFragment unAchuevementListFragment = new UnAchuevementListFragment();
        listFragment.add(achuevementListFragment);
        listFragment.add(unAchuevementListFragment);
        mViewPager.setAdapter(new ContentsPagerAdapter(getChildFragmentManager(), listFragment));
    }

    @gorden.rxbus2.Subscribe(code = RxCode.CODE_ACHIEVEMERNT_MESSAGE, threadMode = ThreadMode.MAIN)
    public void upData() {
        redDot.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }
}

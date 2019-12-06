package com.xswq.consumer.activity.personalCenter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xswq.consumer.R;
import com.xswq.consumer.adapter.ContentsPagerAdapter;
import com.xswq.consumer.base.BaseAppCompatActivity;
import com.xswq.consumer.bean.AchievementBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.fragment.AchievementFragment;
import com.xswq.consumer.fragment.BasicInformationFragment;
import com.xswq.consumer.fragment.LeveInformationFragment;
import com.xswq.consumer.fragment.SpecialEffectsFragment;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.PreferencesUtil;
import com.xswq.consumer.utils.RxCode;
import com.xswq.consumer.utils.StatisticsUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import gorden.rxbus2.RxBus;
import gorden.rxbus2.ThreadMode;
import okhttp3.Call;

public class PersonalCenterActivity extends BaseAppCompatActivity implements View.OnClickListener {

    @BindView(R.id.personal_cente_basic_information2)
    ImageView mBasicInformation2;
    @BindView(R.id.personal_cente_leve_information2)
    ImageView mLeveInformation2;
    @BindView(R.id.personal_cente_my_achievements2)
    ImageView mAchievements2;
    @BindView(R.id.personal_cente_special_effects2)
    ImageView mEffects2;

    @BindView(R.id.personal_cente_basic_information)
    ImageView mBasicInformation;
    @BindView(R.id.personal_cente_leve_information)
    ImageView mLeveInformation;
    @BindView(R.id.personal_cente_my_achievements)
    ImageView mAchievements;
    @BindView(R.id.personal_cente_special_effects)
    ImageView mEffects;

    @BindView(R.id.personal_cente_basic_information_text)
    TextView mBasicInformationText;
    @BindView(R.id.personal_cente_leve_information_text)
    TextView mLeveinformationText;
    @BindView(R.id.personal_cente_my_achievements_text)
    TextView mAchievementsText;
    @BindView(R.id.personal_cente_special_effects_text)
    TextView mEffectsText;
    @BindView(R.id.green_back)
    ImageView mBlack;
    @BindView(R.id.red_dot)
    ImageView mRedDot;


    @BindView(R.id.personal_cente_special_viewpager)
    ViewPager mViewPager;
    private List<Fragment> listFragment;
    private BasicInformationFragment basicInformationFragment;
    private LeveInformationFragment leveInformationFragment;
    private AchievementFragment achievementFragment;
    private SpecialEffectsFragment specialEffectsFragment;
    public static PersonalCenterActivity instance;


    @Override
    public int bindLayout() {
        return R.layout.activity_personal_center_layout;
    }

    @Override
    public void initData() {
        RxBus.get().register(this);
        initView();
    }

    private void initView() {
        instance = this;
        mBlack.setOnClickListener(this);
        mBasicInformation.setOnClickListener(this);
        mBasicInformationText.setOnClickListener(this);
        mLeveInformation.setOnClickListener(this);
        mLeveinformationText.setOnClickListener(this);
        mAchievements.setOnClickListener(this);
        mAchievementsText.setOnClickListener(this);
        mEffects.setOnClickListener(this);
        mEffectsText.setOnClickListener(this);
        getTabViewPage();
        boolean personalInformation = PreferencesUtil.getBoolean(PersonalCenterActivity.this, "personalInformation");
        if (!personalInformation) {
            JumpIntent.jump(PersonalCenterActivity.this, UserInformationActivity.class);
        }
        StatisticsUtil.getStatistics(PersonalCenterActivity.this, Const.STR4);
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_cente_basic_information:
            case R.id.personal_cente_basic_information_text:
                getOneTab();
                break;
            case R.id.personal_cente_leve_information:
            case R.id.personal_cente_leve_information_text:
                getTwoTab();
                break;
            case R.id.personal_cente_my_achievements:
            case R.id.personal_cente_my_achievements_text:
                getThreeTab();
                break;
            case R.id.personal_cente_special_effects:
            case R.id.personal_cente_special_effects_text:
                getFourTab();
                break;
            case R.id.green_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void setPersonalCemterBg(ImageView iamgeView,ImageView unCheckIamgeView, TextView textViewint, boolean isCheck, int color, int imagBg) {
        if(isCheck){
            iamgeView.setVisibility(View.VISIBLE);
            unCheckIamgeView.setVisibility(View.INVISIBLE);
        }else {
            iamgeView.setVisibility(View.INVISIBLE);
            unCheckIamgeView.setVisibility(View.VISIBLE);
        }
        textViewint.setTextColor(color);
        Drawable drawable = PersonalCenterActivity.this.getResources().getDrawable(imagBg);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textViewint.setCompoundDrawables(null, drawable, null, null);
    }

    private void getTabViewPage() {
        //初始化fragment
        listFragment = new ArrayList<>();
        basicInformationFragment = new BasicInformationFragment();
        leveInformationFragment = new LeveInformationFragment();
        achievementFragment = new AchievementFragment();
        specialEffectsFragment = new SpecialEffectsFragment();
        listFragment.add(basicInformationFragment);
        listFragment.add(leveInformationFragment);
        listFragment.add(achievementFragment);
        listFragment.add(specialEffectsFragment);
        mViewPager.setAdapter(new ContentsPagerAdapter(getSupportFragmentManager(), listFragment));
        Intent intent = getIntent();
        int intentType = intent.getIntExtra("intentType", 0);
        switch (intentType) {
            case 8:
                getFourTab();
                break;
            case 7:
                getThreeTab();
                break;
            case 6:
                getTwoTab();
                break;
            default:
                break;
        }

    }

    public void getOneTab() {
        setPersonalCemterBg(mBasicInformation2,mBasicInformation, mBasicInformationText,true, this.getResources().getColor(R.color.color_274701), R.mipmap.personal_cente_basic_information_check);
        setPersonalCemterBg(mLeveInformation2,mLeveInformation, mLeveinformationText, false, this.getResources().getColor(R.color.color_7e2c00), R.mipmap.personal_cente_leve_information_uncheck);
        setPersonalCemterBg(mAchievements2,mAchievements, mAchievementsText, false, this.getResources().getColor(R.color.color_7e2c00), R.mipmap.personal_cente_my_achievements_uncheck);
        setPersonalCemterBg(mEffects2,mEffects, mEffectsText, false, this.getResources().getColor(R.color.color_7e2c00), R.mipmap.personal_cente_special_effects_uncheck);
        mViewPager.setCurrentItem(0);
    }

    public void getTwoTab() {
        setPersonalCemterBg(mBasicInformation2,mBasicInformation, mBasicInformationText, false, this.getResources().getColor(R.color.color_7e2c00), R.mipmap.personal_cente_basic_information_uncheck);
        setPersonalCemterBg(mLeveInformation2,mLeveInformation, mLeveinformationText, true, this.getResources().getColor(R.color.color_274701), R.mipmap.personal_cente_leve_information_check);
        setPersonalCemterBg(mAchievements2,mAchievements, mAchievementsText, false, this.getResources().getColor(R.color.color_7e2c00), R.mipmap.personal_cente_my_achievements_uncheck);
        setPersonalCemterBg(mEffects2,mEffects, mEffectsText, false, this.getResources().getColor(R.color.color_7e2c00), R.mipmap.personal_cente_special_effects_uncheck);
        mViewPager.setCurrentItem(1);
    }

    public void getThreeTab() {
        setPersonalCemterBg(mBasicInformation2,mBasicInformation, mBasicInformationText, false, this.getResources().getColor(R.color.color_7e2c00), R.mipmap.personal_cente_basic_information_uncheck);
        setPersonalCemterBg(mLeveInformation2,mLeveInformation, mLeveinformationText, false, this.getResources().getColor(R.color.color_7e2c00), R.mipmap.personal_cente_leve_information_uncheck);
        setPersonalCemterBg(mAchievements2,mAchievements, mAchievementsText, true, this.getResources().getColor(R.color.color_274701), R.mipmap.personal_cente_my_achievements_check);
        setPersonalCemterBg(mEffects2,mEffects, mEffectsText, false, this.getResources().getColor(R.color.color_7e2c00), R.mipmap.personal_cente_special_effects_uncheck);
        mViewPager.setCurrentItem(2);
    }

    public void getFourTab() {
        setPersonalCemterBg(mBasicInformation2,mBasicInformation, mBasicInformationText, false, this.getResources().getColor(R.color.color_7e2c00), R.mipmap.personal_cente_basic_information_uncheck);
        setPersonalCemterBg(mLeveInformation2,mLeveInformation, mLeveinformationText, false, this.getResources().getColor(R.color.color_7e2c00), R.mipmap.personal_cente_leve_information_uncheck);
        setPersonalCemterBg(mAchievements2,mAchievements, mAchievementsText, false, this.getResources().getColor(R.color.color_7e2c00), R.mipmap.personal_cente_my_achievements_uncheck);
        setPersonalCemterBg(mEffects2,mEffects, mEffectsText, true, this.getResources().getColor(R.color.color_274701), R.mipmap.personal_cente_special_effects_check);
        mViewPager.setCurrentItem(3);
    }

    @gorden.rxbus2.Subscribe(code = RxCode.CODE_ACHIEVEMERNT_MESSAGE, threadMode = ThreadMode.MAIN)
    public void upData() {
        getData();
    }

    private void getData() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_ACHIEVEMENT_LIST_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("type", Const.STR1)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    AchievementBean achievementBean = GsonUtil.gsonToBean(response, AchievementBean.class, PersonalCenterActivity.this);
                    if (achievementBean != null) {
                        List<AchievementBean.DataBean> mData = achievementBean.getData();
                        if (mData.size() > 0) {
                            int state = mData.get(0).getState();
                            if (state == 1) {
                                mRedDot.setVisibility(View.VISIBLE);
                                Const.IS_SHOW_RED_DOT = true;
                            }else {
                                mRedDot.setVisibility(View.GONE);
                                Const.IS_SHOW_RED_DOT = false;
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }
}

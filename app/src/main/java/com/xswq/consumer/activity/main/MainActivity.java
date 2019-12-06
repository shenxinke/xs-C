package com.xswq.consumer.activity.main;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xswq.consumer.R;
import com.xswq.consumer.activity.guidance.GuideMainAcitivity;
import com.xswq.consumer.activity.login.ChessLoginTypeActivity;
import com.xswq.consumer.activity.personalCenter.PersonalCenterActivity;
import com.xswq.consumer.activity.personalCenter.ShoppingMallActivity;
import com.xswq.consumer.activity.playChessWebView.ActivityPlayChessActivity;
import com.xswq.consumer.base.BaseAppCompatActivity;
import com.xswq.consumer.bean.BasicInformaitonBean;
import com.xswq.consumer.bean.NewMobileVersionBean;
import com.xswq.consumer.bean.NowAcitivityBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.dialog.SetMainPopWindows;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.ImageLoader;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.MediahelperUtil;
import com.xswq.consumer.utils.NewMobileVersionUtils;
import com.xswq.consumer.utils.PreferencesUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.xswq.consumer.utils.VersionUtils;
import com.xswq.consumer.view.MyPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import okhttp3.Call;

public class MainActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private String TAG = "MainActivity";
    @BindView(R.id.main_story)
    ImageView mStory;
    @BindView(R.id.main_play_chess)
    ImageView mPlayChess;
    @BindView(R.id.main_wonderfuk_activity)
    ImageView mWonderfukActivity;
    @BindView(R.id.main_set)
    ImageView mSettings;
    @BindView(R.id.main_money_add)
    ImageView mAddMoney;
    @BindView(R.id.main_lock_play_chess)
    ImageView mPlayChessLock;
    @BindView(R.id.main_invite)
    ImageView mImageInvite;
    @BindView(R.id.main_gift_bag)
    ImageView mImageGiftBag;
    @BindView(R.id.main_announcement_bag)
    ImageView mImageAnnouncementBag;

    @BindView(R.id.image_head)
    ImageView mImageHead;
    @BindView(R.id.main_image_head)
    ImageView mImageHeadBg;
    @BindView(R.id.main_picture_frame_image)
    ImageView mPictureFrame;
    @BindView(R.id.text_name)
    TextView mUserName;

    @BindView(R.id.main_lock_wonderfuk_activity)
    ImageView mWonderfukLock;
    @BindView(R.id.text_leve)
    TextView mLeve;
    @BindView(R.id.text_money)
    TextView mMoney;
    private ImageLoader imageLoader;
    private int maxGateNum;

    @Override
    public int bindLayout() {
        return R.layout.activity_main_layout;
    }

    @Override
    public void initData() {
        mImageGiftBag.setOnClickListener(this);
        mImageAnnouncementBag.setOnClickListener(this);
        mImageInvite.setOnClickListener(this);
        mPlayChess.setOnClickListener(this);
        mWonderfukActivity.setOnClickListener(this);
        mSettings.setOnClickListener(this);
        mAddMoney.setOnClickListener(this);
        mStory.setOnClickListener(this);
        mImageHead.setOnClickListener(this);
        mImageHeadBg.setOnClickListener(this);
        mUserName.setOnClickListener(this);
        imageLoader = new ImageLoader(MainActivity.this);
        Const.UID = PreferencesUtil.getString(MainActivity.this, "uid");
        Const.TOKEN = PreferencesUtil.getString(MainActivity.this, "token");
        boolean musictoneicon = PreferencesUtil.getBoolean(MainActivity.this, "musictoneicon");
        if (!musictoneicon) {
            MediahelperUtil.playSound(R.raw.main_bg, MainActivity.this);
        }
        float curTranslationX = mImageInvite.getTranslationX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageInvite, "translationX", curTranslationX, 10f, curTranslationX);
        animator.setDuration(1000);
        animator.setRepeatCount(Animation.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_set:
                if (ConstUtils.isClickable()) {
                    return;
                }
                new SetMainPopWindows().initPopupWindow(MainActivity.this);
                break;
            case R.id.main_story:
                if (ConstUtils.isClickable()) {
                    return;
                }
                JumpIntent.jump(MainActivity.this, StoryActivity.class);
                break;
            case R.id.main_play_chess:
                if (ConstUtils.isClickable()) {
                    return;
                }
                if (maxGateNum > 3) {
                    JumpIntent.jump(MainActivity.this, PlayChessActivity.class);
                } else {
                    showPopupWindow("请先完成故事剧情模块第三课");
                }
                break;
            case R.id.main_wonderfuk_activity:
                if (ConstUtils.isClickable()) {
                    return;
                }
                if (maxGateNum > 3) {
                    getActivityIsLocks(true,2);
                } else {
                    showPopupWindow("请先完成故事剧情模块第三课");
                }
                break;
            case R.id.main_money_add:
                if (ConstUtils.isClickable()) {
                    return;
                }
                JumpIntent.jump(MainActivity.this, ShoppingMallActivity.class, true);
                break;
            case R.id.image_head:
            case R.id.main_image_head:
            case R.id.text_name:
                if (ConstUtils.isClickable()) {
                    return;
                }
                JumpIntent.jump(MainActivity.this, PersonalCenterActivity.class);
                break;
            case R.id.main_invite:
            case R.id.main_gift_bag:
                JumpIntent.jump(MainActivity.this, InvitationActivity.class);
                break;
            case R.id.main_announcement_bag:
                JumpIntent.jump(MainActivity.this, AnnouncementActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getUserInfo();
        //版本更新
        if (Const.ISGETNEWVERSION) {
            getNewMobileVersion();
        }
    }

    //登录奖励
    private void getLoginSignIn() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_GET_SIGN_IN_LIST_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
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
                                JumpIntent.jump(MainActivity.this, LoginSignInActivity.class);

                            } else if (returnCode == 10048) {
                                ToastUtils.show(error.getString("returnMessage"));
                                Intent intent = new Intent(MainActivity.this, ChessLoginTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
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

    private void getUserInfo() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_USER_IN_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    BasicInformaitonBean sekiguchiBygateNumBean = GsonUtil.gsonToBean(response, BasicInformaitonBean.class, MainActivity.this);
                    if (sekiguchiBygateNumBean != null) {
                        BasicInformaitonBean.DataBean data = sekiguchiBygateNumBean.getData();
                        BasicInformaitonBean.DataBean.UserInfoBean userInfo = data.getUserInfo();
                        BasicInformaitonBean.DataBean.UserDetailBean userDetail = data.getUserDetail();
                        Const.SKINID = String.valueOf(userDetail.getSkinId());
                        Const.EFFECTID = String.valueOf(userDetail.getPlayEffectId());
                        String gold = String.valueOf(userDetail.getGold());
                        String username = userInfo.getUsername();
                        String headimg = userInfo.getHeadimg();
                        maxGateNum = data.getMaxGateNum();
                        String chessLevel = ConstUtils.getChessLevel(userDetail.getLevel());
                        mLeve.setText(chessLevel);
                        mMoney.setText(ConstUtils.zeroString(gold));
                        ConstUtils.setTextString(userInfo.getUsername(), mUserName);
                        mUserName.setText(ConstUtils.signString(username));
                        imageLoader.loadHeadImage(ConstUtils.getStringNoEmpty(headimg), mImageHead);
                        imageLoader.loadCircularImage(ConstUtils.getStringNoEmpty(userDetail.getHeadImgBorder()), mPictureFrame);
                        PreferencesUtil.putString(MainActivity.this, "level", chessLevel);
                        PreferencesUtil.putString(MainActivity.this, "username", username);
                        PreferencesUtil.putInt(MainActivity.this, "maxGateNum", maxGateNum);
                        boolean oneClassGuide = PreferencesUtil.getBoolean(MainActivity.this, "oneClassGuide");
                        boolean personalInformation = PreferencesUtil.getBoolean(MainActivity.this, "personalInformation");
                        boolean twoClassGuide = PreferencesUtil.getBoolean(MainActivity.this, "twoClassGuide");
                        boolean playChessGuide = PreferencesUtil.getBoolean(MainActivity.this, "playChessGuide");
                        boolean activityGuide = PreferencesUtil.getBoolean(MainActivity.this, "activityGuide");
                        boolean oneClassGuideIsOver = PreferencesUtil.getBoolean(MainActivity.this, "oneClassGuideIsOver");
                        if (maxGateNum > 3) {
                            mPlayChessLock.setVisibility(View.GONE);
                            if (!playChessGuide) {
                                JumpIntent.jump(MainActivity.this, GuideMainAcitivity.class, ConstUtils.getStringNoEmpty(headimg));
                            }
                            getActivityIsLocks(activityGuide,1);
                        } else {
                            mWonderfukLock.setVisibility(View.VISIBLE);
                            mPlayChessLock.setVisibility(View.VISIBLE);
                            if (!oneClassGuide) {
                                JumpIntent.jump(MainActivity.this, GuideMainAcitivity.class, ConstUtils.getStringNoEmpty(headimg));
                            } else if (oneClassGuideIsOver && !personalInformation) {
                                JumpIntent.jump(MainActivity.this, GuideMainAcitivity.class, ConstUtils.getStringNoEmpty(headimg));
                            } else if (!twoClassGuide && oneClassGuideIsOver) {
                                JumpIntent.jump(MainActivity.this, GuideMainAcitivity.class, ConstUtils.getStringNoEmpty(headimg));
                            }
                        }
                        getLoginSignIn();
                        try {
                            TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                    if (Const.IS_SHOW_ANNOUNCEMENT) {
                                        Const.IS_SHOW_ANNOUNCEMENT = false;
                                        Intent intent = new Intent(MainActivity.this, AnnouncementActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            };
                            Timer timer = new Timer();
                            timer.schedule(task, 500);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getNewMobileVersion() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_NEW_MOBILE_VERSION_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("mobileType", Const.STR2)
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
                                NewMobileVersionBean newMobileVersionBean = GsonUtil.gsonToBean(response, NewMobileVersionBean.class, MainActivity.this);
                                if (newMobileVersionBean != null) {
                                    NewMobileVersionBean.DataBean data = newMobileVersionBean.getData();
                                    if (data != null) {
                                        int version = data.getVersion();
                                        String versionName = VersionUtils.getVersionName(MainActivity.this);
                                        versionName = versionName.replaceAll("\\.", "");
                                        int appVersion = Integer.parseInt(versionName);
                                        if (appVersion < version) {//版本号更低
                                            new NewMobileVersionUtils(MainActivity.this, data, appVersion);
                                        }
                                    }
                                }
                            } else if (returnCode == 10048) {
                                ToastUtils.show(error.getString("returnMessage"));
                                Intent intent = new Intent(MainActivity.this, ChessLoginTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
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

    private void getActivityIsLocks(final boolean activityGuide, final int type) {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_ACTIVITY_NOW_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    NowAcitivityBean activityNowBean = GsonUtil.gsonToBean(response, NowAcitivityBean.class, MainActivity.this);
                    if (activityNowBean != null) {
                        NowAcitivityBean.DataBean data = activityNowBean.getData();
                        if (data != null) {
                            mWonderfukLock.setVisibility(View.GONE);
                            if(type == 1){
                                if (!activityGuide) {
                                    JumpIntent.jump(MainActivity.this, GuideMainAcitivity.class, ConstUtils.getStringNoEmpty(null));
                                }
                            }else {
                                JumpIntent.jump(MainActivity.this, ActivityPlayChessActivity.class, ConstUtils.getStringNoEmpty(null));
                            }
                        } else {
                            if(type == 2){
                                showPopupWindow("暂无活动");
                                mWonderfukLock.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //显示提示框
    private void showPopupWindow(String str) {
        View layout = LayoutInflater.from(MainActivity.this).inflate(R.layout.pop_window_layout, null, false);
        View view = getWindow().getDecorView();
        MyPopupWindow.myPopupWindow(view, layout);
        Button btnSava = layout.findViewById(R.id.button_save);
        Button btnCancel = layout.findViewById(R.id.button_cancel);
        TextView texContent = layout.findViewById(R.id.popup_window_content);
        texContent.setText(str);
        btnSava.setText("确认");
        btnCancel.setVisibility(View.GONE);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_save:
                    case R.id.button_cancel:
                        MyPopupWindow.dismissPopWindow();
                        break;
                    default:
                        break;
                }
            }
        };
        //设置popwindow布局控件的点击事件
        btnSava.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
    }
}

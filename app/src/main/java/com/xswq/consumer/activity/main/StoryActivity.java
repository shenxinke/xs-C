package com.xswq.consumer.activity.main;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.xswq.consumer.R;
import com.xswq.consumer.activity.guidance.GuideStoryActivity;
import com.xswq.consumer.activity.login.ChessLoginTypeActivity;
import com.xswq.consumer.activity.multimedia.StoryChessPlayActivity;
import com.xswq.consumer.activity.multimedia.GamePlayActivity;
import com.xswq.consumer.activity.multimedia.TestPlayActivity;
import com.xswq.consumer.activity.multimedia.VideoPlayActivity;
import com.xswq.consumer.activity.personalCenter.ShoppingMallActivity;
import com.xswq.consumer.activity.multimedia.ErrorQuestionActivity;
import com.xswq.consumer.base.BaseAppCompatActivity;
import com.xswq.consumer.activity.multimedia.StoryPlayActivity;
import com.xswq.consumer.bean.MultimediaBean;
import com.xswq.consumer.bean.SekiguchListBean;
import com.xswq.consumer.bean.SekiguchiBygateNumBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.LogUtil;
import com.xswq.consumer.utils.MediahelperUtil;
import com.xswq.consumer.utils.PreferencesUtil;
import com.xswq.consumer.utils.StatisticsUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.xswq.consumer.view.MyPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import okhttp3.Call;

public class StoryActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private String TAG = "StoryActivity";
    @BindView(R.id.green_back)
    ImageView mGreenBack;
    @BindView(R.id.story_flower_one)
    ImageView mFlowerOne;
    @BindView(R.id.story_flower_two)
    ImageView mFlowerTwo;
    @BindView(R.id.story_flower_three)
    ImageView mFlowerThree;
    @BindView(R.id.story_flower_four)
    ImageView mFlowerFour;
    @BindView(R.id.story_flower_five)
    ImageView mFlowerFive;
    @BindView(R.id.story_flower_six)
    ImageView mFlowerSix;
    @BindView(R.id.story_flower_seven)
    ImageView mFlowerSeven;
    @BindView(R.id.story_flower_eight)
    ImageView mFlowerEight;
    @BindView(R.id.story_flower_nine)
    ImageView mFlowerNine;
    @BindView(R.id.story_flower_ten)
    ImageView mFlowerTen;
    @BindView(R.id.story_flower_eleven)
    ImageView mFlowerEleven;
    @BindView(R.id.story_flower_twelve)
    ImageView mFlowerTwelve;
    @BindView(R.id.story_windmill_image)
    ImageView mWindmillImage;
    @BindView(R.id.story_score)
    Button scoreButton;
    @BindView(R.id.story_score2)
    Button scoreButton2;
    @BindView(R.id.story_score3)
    Button scoreButton3;
    @BindView(R.id.lottie_animation_one)
    LottieAnimationView mLottieAnimationOne;
    @BindView(R.id.lottie_animation_two)
    LottieAnimationView mLottieAnimationTwo;
    @BindView(R.id.lottie_animation_three)
    LottieAnimationView mLottieAnimationThree;
    @BindView(R.id.lottie_animation_four)
    LottieAnimationView mLottieAnimationFour;
    @BindView(R.id.lottie_animation_five)
    LottieAnimationView mLottieAnimationFive;
    @BindView(R.id.lottie_animation_six)
    LottieAnimationView mLottieAnimationSix;
    @BindView(R.id.lottie_animation_seven)
    LottieAnimationView mLottieAnimationSeven;
    @BindView(R.id.lottie_animation_eight)
    LottieAnimationView mLottieAnimationEight;
    @BindView(R.id.lottie_animation_nine)
    LottieAnimationView mLottieAnimationNine;
    @BindView(R.id.lottie_animation_ten)
    LottieAnimationView mLottieAnimationTen;
    @BindView(R.id.lottie_animation_eleven)
    LottieAnimationView mLottieAnimationEleven;
    @BindView(R.id.lottie_animation_twelve)
    LottieAnimationView mLottieAnimationTwelve;


    private List<SekiguchListBean.DataBean.GateAllBean.ListBean> list;
    private MultimediaBean multimediaBean;
    private int maxGateNum;
    private SekiguchListBean.DataBean data;
    private List<Integer> flowerType = new ArrayList<>();


    @Override
    public int bindLayout() {
        return R.layout.activity_story_layout;
    }

    @Override
    public void initData() {
        onClickListener();
        StatisticsUtil.getStatistics(StoryActivity.this, Const.STR1);
    }

    private void onClickListener() {
        mGreenBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mFlowerOne.setOnClickListener(StoryActivity.this);
        mFlowerTwo.setOnClickListener(StoryActivity.this);
        mFlowerThree.setOnClickListener(StoryActivity.this);
        mFlowerFour.setOnClickListener(StoryActivity.this);
        mFlowerFive.setOnClickListener(StoryActivity.this);
        mFlowerSix.setOnClickListener(StoryActivity.this);
        mFlowerSeven.setOnClickListener(StoryActivity.this);
        mFlowerEight.setOnClickListener(StoryActivity.this);
        mFlowerNine.setOnClickListener(StoryActivity.this);
        mFlowerTen.setOnClickListener(StoryActivity.this);
        mFlowerEleven.setOnClickListener(StoryActivity.this);
        mFlowerTwelve.setOnClickListener(StoryActivity.this);
        mWindmillImage.setOnClickListener(StoryActivity.this);
    }

    @Override
    public void onClick(View v) {
        int index = 0;
        switch (v.getId()) {
            case R.id.story_flower_one:
                if (ConstUtils.isClickable()) {
                    return;
                }
                index = 0;
                showPopupWindows(mLottieAnimationOne, mFlowerOne, index);
                break;
            case R.id.story_flower_two:
                if (ConstUtils.isClickable()) {
                    return;
                }
                index = 1;
                showPopupWindows(mLottieAnimationTwo, mFlowerTwo, index);
                break;
            case R.id.story_flower_three:
                if (ConstUtils.isClickable()) {
                    return;
                }
                index = 2;
                showPopupWindows(mLottieAnimationThree, mFlowerThree, index);
                break;
            case R.id.story_flower_four:
                if (ConstUtils.isClickable()) {
                    return;
                }
                index = 3;
                showPopupWindows(mLottieAnimationFour, mFlowerFour, index);
                break;
            case R.id.story_flower_five:
                if (ConstUtils.isClickable()) {
                    return;
                }
                index = 4;
                showPopupWindows(mLottieAnimationFive, mFlowerFive, index);
                break;
            case R.id.story_flower_six:
                if (ConstUtils.isClickable()) {
                    return;
                }
                index = 5;
                showPopupWindows(mLottieAnimationSix, mFlowerSix, index);
                break;
            case R.id.story_flower_seven:
                if (ConstUtils.isClickable()) {
                    return;
                }
                index = 6;
                showPopupWindows(mLottieAnimationSeven, mFlowerSeven, index);
                break;
            case R.id.story_flower_eight:
                if (ConstUtils.isClickable()) {
                    return;
                }
                index = 7;
                showPopupWindows(mLottieAnimationEight, mFlowerEight, index);
                break;
            case R.id.story_flower_nine:
                if (ConstUtils.isClickable()) {
                    return;
                }
                index = 8;
                showPopupWindows(mLottieAnimationNine, mFlowerNine, index);
                break;
            case R.id.story_flower_ten:
                if (ConstUtils.isClickable()) {
                    return;
                }
                index = 9;
                showPopupWindows(mLottieAnimationTen, mFlowerTen, index);
                break;
            case R.id.story_flower_eleven:
                if (ConstUtils.isClickable()) {
                    return;
                }
                index = 10;
                showPopupWindows(mLottieAnimationEleven, mFlowerEleven, index);
                break;
            case R.id.story_flower_twelve:
                if (ConstUtils.isClickable()) {
                    return;
                }
                index = 11;
                showPopupWindows(mLottieAnimationTwelve, mFlowerTwelve, index);
                break;
            case R.id.story_windmill_image:
                try {
                    if (ConstUtils.isClickable()) {
                        return;
                    }
                    int gateNum = data.getMaxSekiguchi().getGateNum();
                    LogUtil.e("StoryActivity", "" + gateNum);
                    if (gateNum <= 0) {
                        JumpIntent.jump(StoryActivity.this, ErrorQuestionActivity.class);
                    } else {
                        String errorQuestionType = list.get(gateNum - 1).getErrorQuestionType();
                        JumpIntent.jump(StoryActivity.this, ErrorQuestionActivity.class, errorQuestionType);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    private void getSekiguchiList() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_SEKIGUCH_LIST_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("pageNum", Const.STR1)
                    .addParams("pageSize", Const.STR12)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    SekiguchListBean sekiguchListBean = GsonUtil.gsonToBean(response, SekiguchListBean.class, StoryActivity.this);
                    if (sekiguchListBean != null) {
                        data = sekiguchListBean.getData();
                        int maxBuy = data.getMaxBuy();
                        int gateNum = data.getMaxSekiguchi().getGateNum();
                        int seat = data.getMaxSekiguchi().getSeat();
                        SekiguchListBean.DataBean.GateAllBean gateAll = data.getGateAll();
                        multimediaBean = new MultimediaBean();
                        maxGateNum = data.getMaxSekiguchi().getGateNum();
                        if (maxGateNum == 0) {
                            maxGateNum = 1;
                        }
                        multimediaBean.setMaxGateNum(maxGateNum);
                        multimediaBean.setMaxSeat(data.getMaxSekiguchi().getSeat());
                        int total = gateAll.getTotal();
                        if (total > 0) {
                            list = gateAll.getList();
                        }

                        showGuide(gateNum, seat);//显示引导
                        showScore();//显示分数
                        showFlower(maxBuy, gateNum, seat);//花状态显示
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSekiguchiBygateNum(String gateNum, final int index) {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_SEKIGUCH_BYGATE_NUM_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("gateNum", gateNum)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    SekiguchiBygateNumBean sekiguchiBygateNumBean = GsonUtil.gsonToBean(response, SekiguchiBygateNumBean.class, StoryActivity.this);
                    if (sekiguchiBygateNumBean != null) {
                        SekiguchiBygateNumBean.DataBean data = sekiguchiBygateNumBean.getData();
                        int seat = data.getMaxTime().getSeat();
                        multimediaBean.setSeat(seat);
                        multimediaBean.setGameId(list.get(index).getGameId());
                        multimediaBean.setStoryId(list.get(index).getStoryId());
                        multimediaBean.setVideoId(list.get(index).getVideoId());
                        multimediaBean.setGateNum(list.get(index).getGateNum());
                        multimediaBean.setQuestionId(list.get(index).getQuestionId());
                        multimediaBean.setTestQuestionId(list.get(index).getTestQuestionId());
                        switch (seat) {
                            case 0:
                                JumpIntent.jump(StoryActivity.this, StoryPlayActivity.class, multimediaBean);
                                break;
                            case 1:
                                JumpIntent.jump(StoryActivity.this, GamePlayActivity.class, multimediaBean);
                                break;
                            case 2:
                                JumpIntent.jump(StoryActivity.this, VideoPlayActivity.class, multimediaBean);
                                break;
                            case 3:
                                JumpIntent.jump(StoryActivity.this, StoryChessPlayActivity.class, multimediaBean);
                                break;
                            case 4:
                                if (TextUtils.isEmpty(list.get(index).getTestQuestionId())) {
                                    JumpIntent.jump(StoryActivity.this, StoryPlayActivity.class, multimediaBean);
                                } else {
                                    JumpIntent.jump(StoryActivity.this, TestPlayActivity.class, multimediaBean);
                                }
                                break;
                            case 5:
                                JumpIntent.jump(StoryActivity.this, StoryPlayActivity.class, multimediaBean);
                                break;
                            default:
                                break;
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //显示提示框
    private void showPopupWindow(final View view, final int type, final int number, final int onclickNum) {
        View layout = LayoutInflater.from(StoryActivity.this).inflate(R.layout.pop_window_layout, null, false);
        MyPopupWindow.myPopupWindow(view, layout);
        Button btnSava = layout.findViewById(R.id.button_save);
        Button btnCancel = layout.findViewById(R.id.button_cancel);
        TextView texContent = layout.findViewById(R.id.popup_window_content);
        if (type == 1) {
            texContent.setText("请完成第" + number + "课");
            btnSava.setText("确认");
            btnCancel.setVisibility(View.GONE);
        } else if (type == 2) {
            texContent.setText("水滴不足，请充值");
            btnSava.setText("去充值");
        } else if (type == 3) {
            texContent.setText("解锁该课程成功");
            btnSava.setText("确认");
            btnCancel.setVisibility(View.GONE);
        } else if (type == 4) {
            texContent.setText("是否花费" + number + "水滴解锁该课程");
            btnSava.setText("确认");
        } else if (type == 5) {
            texContent.setText("请先解锁第" + number + "课");
            btnSava.setText("确认");
            btnCancel.setVisibility(View.GONE);
        }
        btnCancel.setText("取消");
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_save:
                        if (type == 2) {
                            JumpIntent.jump(StoryActivity.this, ShoppingMallActivity.class, true);
                        } else if (type == 3) {
                            getSekiguchiList();
                        } else if (type == 4) {
                            String gateNum = list.get(onclickNum - 1).getGateNum();
                            getBuyUnlock(view, number, gateNum);
                        }
                        MyPopupWindow.dismissPopWindow();
                        break;
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

    private void getBuyUnlock(final View view, int gold, String gateNum) {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_UNLOCK_GATE_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("gold", String.valueOf(gold))
                    .addParams("gateNum", gateNum)
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
                                showPopupWindow(view, 3, 0, 0);

                            } else if (returnCode == 10048) {
                                ToastUtils.show(error.getString("returnMessage"));
                                Intent intent = new Intent(StoryActivity.this, ChessLoginTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                StoryActivity.this.startActivity(intent);
                            } else {
                                showPopupWindow(view, 2, 0, 0);
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

    @Override
    protected void onStart() {
        super.onStart();
        MediahelperUtil.resume();
        getSekiguchiList();
    }

    private void showScore() {
        for (int i = 0; i < list.size(); i++) {
            if (i < 4) {
                String score = list.get(i).getScore();
                if (!TextUtils.isEmpty(score)) {
                    scoreButton.setVisibility(View.VISIBLE);
                    scoreButton.setText(score);
                    i = 3;
                }
            } else if (i < 8) {
                String score = list.get(i).getScore();
                if (!TextUtils.isEmpty(score)) {
                    scoreButton2.setVisibility(View.VISIBLE);
                    scoreButton2.setText(score);
                    i = 7;
                }
            } else {
                String score = list.get(i).getScore();
                if (!TextUtils.isEmpty(score)) {
                    scoreButton3.setVisibility(View.VISIBLE);
                    scoreButton3.setText(score);
                }
            }
        }
    }

    private void showGuide(int gateNum, int seat) {
        int testQuestionIdSzie0;
        int testQuestionIdSzie1;
        if (TextUtils.isEmpty(list.get(0).getTestQuestionId())) {
            testQuestionIdSzie0 = 4;
        } else {
            testQuestionIdSzie0 = 5;
        }
        if (TextUtils.isEmpty(list.get(1).getTestQuestionId())) {
            testQuestionIdSzie1 = 4;
        } else {
            testQuestionIdSzie1 = 5;
        }
        boolean oneClassGuide = PreferencesUtil.getBoolean(StoryActivity.this, "oneClassGuide");
        boolean twoClassGuide = PreferencesUtil.getBoolean(StoryActivity.this, "twoClassGuide");
        boolean oneClassGuideIsOver = PreferencesUtil.getBoolean(StoryActivity.this, "oneClassGuideIsOver");
        boolean twoClassGuideIsOver = PreferencesUtil.getBoolean(StoryActivity.this, "twoClassGuideIsOver");
        boolean mistakesGuide = PreferencesUtil.getBoolean(StoryActivity.this, "mistakesGuide");
        if (gateNum <= 1 && seat < testQuestionIdSzie0) {
            if (!oneClassGuide) {
                JumpIntent.jump(StoryActivity.this, GuideStoryActivity.class);
            } else {
                PreferencesUtil.putBoolean(StoryActivity.this, "oneClassGuide", true);
            }
        } else if (gateNum <= 1 && seat == testQuestionIdSzie0) {
            PreferencesUtil.putBoolean(StoryActivity.this, "oneClassGuideIsOver", true);
            PreferencesUtil.putBoolean(StoryActivity.this, "oneClassGuide", true);
        } else if (gateNum > 1) {
            if (!oneClassGuideIsOver) {
                PreferencesUtil.putBoolean(StoryActivity.this, "oneClassGuideIsOver", true);
            }
            if (!oneClassGuide) {
                PreferencesUtil.putBoolean(StoryActivity.this, "oneClassGuide", true);
            }
        }
        if ((gateNum == 1 && seat == testQuestionIdSzie1) || (gateNum == 2 && seat < testQuestionIdSzie1)) {
            if (!twoClassGuide) {
                JumpIntent.jump(StoryActivity.this, GuideStoryActivity.class);
            } else {
                PreferencesUtil.putBoolean(StoryActivity.this, "twoClassGuide", true);
            }
        } else if (gateNum == 2 && seat == testQuestionIdSzie1) {
            PreferencesUtil.putBoolean(StoryActivity.this, "twoClassGuideIsOver", true);
            PreferencesUtil.putBoolean(StoryActivity.this, "twoClassGuide", true);
        } else if (gateNum > 2) {
            if (!twoClassGuideIsOver) {
                PreferencesUtil.putBoolean(StoryActivity.this, "twoClassGuideIsOver", true);
            }
            if (!twoClassGuide) {
                PreferencesUtil.putBoolean(StoryActivity.this, "twoClassGuide", true);
            }
        }
        boolean twoClassGuideIsOver2 = PreferencesUtil.getBoolean(StoryActivity.this, "twoClassGuideIsOver");
        if (twoClassGuideIsOver2) {
            if (!mistakesGuide) {
                JumpIntent.jump(StoryActivity.this, GuideStoryActivity.class);
            }
        }
    }

    private void showPopupWindows(LottieAnimationView animationView, ImageView imageFlower, int index) {
        String gateNum;
        View view = getWindow().getDecorView();
        if (list != null && data != null) {
            int onclickNum = index + 1;
            int maxBuy = data.getMaxBuy();
            if (list.size() >= onclickNum) {
                gateNum = list.get(index).getGateNum();
                String testQuestionId = list.get(maxGateNum - 1).getTestQuestionId();
                int seat = data.getMaxSekiguchi().getSeat();
                int i;
                if (TextUtils.isEmpty(testQuestionId)) {
                    i = 4;
                } else {
                    i = 5;
                }
                if (onclickNum > maxBuy + 1) {
                    showPopupWindow(view, 5, maxBuy + 1, 0);
                } else if (onclickNum == maxBuy + 1) {
                    int waterDropNum = list.get(maxBuy).getWaterDropNum();
                    showPopupWindow(view, 4, waterDropNum, onclickNum);
                } else if (onclickNum <= maxBuy && onclickNum > maxGateNum) {
                    if (seat == i) {
                        if (onclickNum == maxGateNum + 1) {
                            imageFlower.setVisibility(View.INVISIBLE);
                            animationView.setVisibility(View.VISIBLE);
                            showFlowerAnimation(animationView, gateNum, index);
                        } else {
                            showPopupWindow(view, 1, maxGateNum + 1, 0);
                        }
                    } else {
                        showPopupWindow(view, 1, maxGateNum, 0);
                    }
                } else {
                    if (onclickNum == maxGateNum && seat < i) {
                        imageFlower.setVisibility(View.INVISIBLE);
                        animationView.setVisibility(View.VISIBLE);
                        showFlowerAnimation(animationView, gateNum, index);
                    } else {
                        getSekiguchiBygateNum(gateNum, index);
                    }
                }
            } else {
                ToastUtils.show(Const.CONS_STR_BUG);
            }
        }
    }

    private void showFlowerAnimation(LottieAnimationView animationView, final String gateNum, final int index) {
        if (animationView != null) {
            animationView.useHardwareAcceleration(true);
            animationView.enableMergePathsForKitKatAndAbove(true);
            animationView.setImageAssetsFolder("images/flower_all"); //assets目录下的子目录，存放动画所需的图片
            animationView.setAnimation("flower_all.json");//在assets目录下的动画json文件名。
            animationView.playAnimation();//播放动画
            getSekiguchiBygateNum(gateNum, index);
        }
    }

    //maxBuy 最大购买数 gateNum最大闯过大关数 isOver小关是否闯完 1完成2未完成
    private void showFlower(int maxBuy, int gateNum, int seat) {
        setFlowerVisible(View.VISIBLE);
        setAnimationVisible(View.GONE);
        int notDoSubject = maxBuy - gateNum;
        String testQuestionId = null;
        if (gateNum > 0) {
            testQuestionId = list.get(gateNum - 1).getTestQuestionId();
        }
        int isOver;
        if (!TextUtils.isEmpty(testQuestionId)) {
            if (seat == 5) {
                isOver = 1;
            } else {
                isOver = 2;
            }
        } else {
            if (seat >= 4) {
                isOver = 1;
            } else {
                isOver = 2;
            }
        }

        for (int i = 0; i < 12; i++) {
            flowerType.add(R.mipmap.story_flower_bud);
        }
        if (gateNum < 3) {
            if (gateNum == 1 && isOver == 1) {
                flowerType.set(0, R.mipmap.story_flower_open);
                flowerType.set(1, R.mipmap.story_flower_ajar);
            } else if (gateNum == 1 && isOver == 2) {
                flowerType.set(0, R.mipmap.story_flower_ajar);
                flowerType.set(1, R.mipmap.story_flower_ajar);
            } else if (gateNum == 2 && isOver == 1) {
                flowerType.set(0, R.mipmap.story_flower_open);
                flowerType.set(1, R.mipmap.story_flower_open);
            } else if (gateNum == 2 && isOver == 2) {
                flowerType.set(0, R.mipmap.story_flower_open);
                flowerType.set(1, R.mipmap.story_flower_ajar);
            }
        } else {
            for (int i = 0; i < gateNum; i++) {
                if (isOver == 1) {
                    flowerType.set(i, R.mipmap.story_flower_open);
                } else {
                    if (i == gateNum - 1) {
                        flowerType.set(i, R.mipmap.story_flower_ajar);
                    } else {
                        flowerType.set(i, R.mipmap.story_flower_open);
                    }
                }
            }

        }

        if (notDoSubject > 0) {
            for (int i = 0; i < notDoSubject; i++) {
                flowerType.set(gateNum + i, R.mipmap.story_flower_ajar);
            }
        }
        setFlowerType(flowerType);
    }

    private void setFlowerType(List<Integer> flowerType) {
        mFlowerOne.setBackgroundResource(flowerType.get(0));
        mFlowerTwo.setBackgroundResource(flowerType.get(1));
        mFlowerThree.setBackgroundResource(flowerType.get(2));
        mFlowerFour.setBackgroundResource(flowerType.get(3));
        mFlowerFive.setBackgroundResource(flowerType.get(4));
        mFlowerSix.setBackgroundResource(flowerType.get(5));
        mFlowerSeven.setBackgroundResource(flowerType.get(6));
        mFlowerEight.setBackgroundResource(flowerType.get(7));
        mFlowerNine.setBackgroundResource(flowerType.get(8));
        mFlowerTen.setBackgroundResource(flowerType.get(9));
        mFlowerEleven.setBackgroundResource(flowerType.get(10));
        mFlowerTwelve.setBackgroundResource(flowerType.get(11));
    }


    private void setFlowerVisible(int isFolowerVisible) {
        mFlowerOne.setVisibility(isFolowerVisible);
        mFlowerTwo.setVisibility(isFolowerVisible);
        mFlowerThree.setVisibility(isFolowerVisible);
        mFlowerFour.setVisibility(isFolowerVisible);
        mFlowerFive.setVisibility(isFolowerVisible);
        mFlowerSix.setVisibility(isFolowerVisible);
        mFlowerSeven.setVisibility(isFolowerVisible);
        mFlowerEight.setVisibility(isFolowerVisible);
        mFlowerNine.setVisibility(isFolowerVisible);
        mFlowerTen.setVisibility(isFolowerVisible);
        mFlowerEleven.setVisibility(isFolowerVisible);
        mFlowerTwelve.setVisibility(isFolowerVisible);
    }

    private void setAnimationVisible(int isAnimiationVisible) {
        mLottieAnimationOne.setVisibility(isAnimiationVisible);
        mLottieAnimationTwo.setVisibility(isAnimiationVisible);
        mLottieAnimationThree.setVisibility(isAnimiationVisible);
        mLottieAnimationFour.setVisibility(isAnimiationVisible);
        mLottieAnimationFive.setVisibility(isAnimiationVisible);
        mLottieAnimationSix.setVisibility(isAnimiationVisible);
        mLottieAnimationSeven.setVisibility(isAnimiationVisible);
        mLottieAnimationEight.setVisibility(isAnimiationVisible);
        mLottieAnimationNine.setVisibility(isAnimiationVisible);
        mLottieAnimationTen.setVisibility(isAnimiationVisible);
        mLottieAnimationEleven.setVisibility(isAnimiationVisible);
        mLottieAnimationTwelve.setVisibility(isAnimiationVisible);
    }

}

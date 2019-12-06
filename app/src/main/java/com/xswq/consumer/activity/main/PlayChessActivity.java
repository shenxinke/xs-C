package com.xswq.consumer.activity.main;


import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xswq.consumer.R;
import com.xswq.consumer.activity.guidance.GuidePlayChessActivity;
import com.xswq.consumer.activity.login.ChessLoginTypeActivity;
import com.xswq.consumer.activity.playChessWebView.PlayChessWebActivity;
import com.xswq.consumer.activity.playChessWebView.ProgressBarActivity;
import com.xswq.consumer.base.BaseAppCompatActivity;
import com.xswq.consumer.bean.BasicInformaitonBean;
import com.xswq.consumer.bean.LevelInformationBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.ImageLoader;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.PreferencesUtil;
import com.xswq.consumer.utils.StatisticsUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.xswq.consumer.view.MyPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 对弈
 */
public class PlayChessActivity extends BaseAppCompatActivity implements View.OnClickListener {

    @BindView(R.id.green_back)
    ImageView mGreenBack;

    @BindView(R.id.play_chess_head)
    ImageView mChessHead;
    @BindView(R.id.play_picture_frame_image)
    ImageView mChessHeadImage;


    @BindView(R.id.play_chess_firends_image)
    ImageView mFirendsGameImage;
    @BindView(R.id.play_chess_firends_text)
    TextView mFirendsGameText;
    @BindView(R.id.play_chess_up_down_image)
    ImageView mUpDownImage;
    @BindView(R.id.play_chesss_up_down_text)
    TextView mUpDownText;

    @BindView(R.id.play_chess_nine_image)
    ImageView mNineImage;
    @BindView(R.id.play_chess_nine_text)
    TextView mNineText;
    @BindView(R.id.play_chess_nineteen_image)
    ImageView mNineteenImage;
    @BindView(R.id.play_chesss_nineteen_text)
    TextView mNineteenText;

    @BindView(R.id.play_chess_leve)
    TextView mChessLeve;
    @BindView(R.id.play_chess_number_of_games)
    TextView mChessNumber;
    @BindView(R.id.play_chess_win_rate)
    TextView mPlayChessWinRate;

    @BindView(R.id.play_chess_matching_button)
    Button mPlayChessButton;
    @BindView(R.id.play_chess_nineteen_button)
    Button mPlayNineteenChessButton;

    @BindView(R.id.start1)
    ImageView mStart1;
    @BindView(R.id.start2)
    ImageView mStart2;
    @BindView(R.id.start3)
    ImageView mStart3;
    @BindView(R.id.start4)
    ImageView mStart4;
    @BindView(R.id.start5)
    ImageView mStart5;
    @BindView(R.id.start6)
    ImageView mStart6;
    @BindView(R.id.start7)
    ImageView mStart7;
    @BindView(R.id.start8)
    ImageView mStart8;
    @BindView(R.id.start9)
    ImageView mStart9;
    @BindView(R.id.start10)
    ImageView mStart10;

    @BindView(R.id.one_line)
    LinearLayout mOneLine;
    @BindView(R.id.two_line)
    LinearLayout mTwoLine;

    private String gameType = "2";//友谊赛2 晋级赛1
    private String road = "9";
    private int level;

    @Override
    public int bindLayout() {
        return R.layout.activity_play_chess_layout;
    }

    @Override
    public void initData() {
        mFirendsGameImage.setOnClickListener(this);
        mFirendsGameText.setOnClickListener(this);
        mUpDownImage.setOnClickListener(this);
        mUpDownText.setOnClickListener(this);

        mPlayNineteenChessButton.setOnClickListener(this);
        mPlayChessButton.setOnClickListener(this);
        mNineImage.setOnClickListener(this);
        mNineText.setOnClickListener(this);
        mNineteenImage.setOnClickListener(this);
        mNineteenText.setOnClickListener(this);
        mGreenBack.setOnClickListener(this);
        boolean playChessGuide = PreferencesUtil.getBoolean(PlayChessActivity.this, "playChessGuide");
        if (!playChessGuide) {
            JumpIntent.jump(PlayChessActivity.this, GuidePlayChessActivity.class);
        }
        StatisticsUtil.getStatistics(PlayChessActivity.this,Const.STR2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getLevelInfo();
        getUserInfo();
    }

    private void getLevelInfo() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_LEVEL_IN_FO_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    LevelInformationBean levelInformationBean = GsonUtil.gsonToBean(response, LevelInformationBean.class, PlayChessActivity.this);
                    if (levelInformationBean != null) {
                        LevelInformationBean.DataBean data = levelInformationBean.getData();
                        level = data.getLevel();
                        String chessLevel = ConstUtils.getChessLevel(level);
                        int star = data.getStar();
                        mChessLeve.setText(chessLevel);
                        int way = data.getWay9() + data.getWay19();
                        mChessNumber.setText("总对局数：" + way + "局");
                        mPlayChessWinRate.setText("胜率：" + data.getPlayAccuracy());
                        setData(PlayChessActivity.this.level, star);
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
                    BasicInformaitonBean sekiguchiBygateNumBean = GsonUtil.gsonToBean(response, BasicInformaitonBean.class, PlayChessActivity.this);
                    if (sekiguchiBygateNumBean != null) {
                        BasicInformaitonBean.DataBean data = sekiguchiBygateNumBean.getData();
                        BasicInformaitonBean.DataBean.UserInfoBean userInfo = data.getUserInfo();
                        BasicInformaitonBean.DataBean.UserDetailBean userDetail = data.getUserDetail();
                        ImageLoader imageLoader = new ImageLoader(PlayChessActivity.this);
                        imageLoader.loadHeadImage(ConstUtils.getStringNoEmpty(userInfo.getHeadimg()), mChessHead);
                        imageLoader.loadCircularImage(ConstUtils.getStringNoEmpty(userDetail.getHeadImgBorder()), mChessHeadImage);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.green_back:
                if (ConstUtils.isClickable()) {
                    return;
                }
                finish();
                break;
            case R.id.play_chess_firends_image:
            case R.id.play_chess_firends_text:
                if (ConstUtils.isClickable()) {
                    return;
                }
                mFirendsGameImage.setBackgroundResource(R.mipmap.play_chess_select);
                mUpDownImage.setBackgroundResource(R.mipmap.play_chess_un_select);
                mNineImage.setBackgroundResource(R.mipmap.play_chess_select);
                mNineteenImage.setOnClickListener(this);
                mNineImage.setOnClickListener(this);
                mNineteenText.setOnClickListener(this);
                mNineText.setOnClickListener(this);
                gameType = "2";
                road = "9";
                break;
            case R.id.play_chess_up_down_image:
            case R.id.play_chesss_up_down_text:
                if (ConstUtils.isClickable()) {
                    return;
                }
                mUpDownImage.setBackgroundResource(R.mipmap.play_chess_select);
                mFirendsGameImage.setBackgroundResource(R.mipmap.play_chess_un_select);
                mNineImage.setBackgroundResource(R.mipmap.play_chess_un_select);
                mNineteenImage.setBackgroundResource(R.mipmap.play_chess_un_select);
                mNineteenText.setOnClickListener(null);
                mNineteenImage.setOnClickListener(null);
                mNineText.setOnClickListener(null);
                mNineImage.setOnClickListener(null);
                gameType = "1";
                road = level <= 10 ? "9" : "19";
                break;
            case R.id.play_chess_nine_image:
            case R.id.play_chess_nine_text:
                if (ConstUtils.isClickable()) {
                    return;
                }
                mNineImage.setBackgroundResource(R.mipmap.play_chess_select);
                mNineteenImage.setBackgroundResource(R.mipmap.play_chess_un_select);
                road = "9";
                break;
            case R.id.play_chess_nineteen_image:
            case R.id.play_chesss_nineteen_text:
                if (ConstUtils.isClickable()) {
                    return;
                }
                mNineteenImage.setBackgroundResource(R.mipmap.play_chess_select);
                mNineImage.setBackgroundResource(R.mipmap.play_chess_un_select);
                road = "19";
                break;
            case R.id.play_chess_matching_button:
                if (ConstUtils.isClickable()) {
                    return;
                }
                getRandomBatle(v);
                break;
            case R.id.play_chess_nineteen_button:
                if (ConstUtils.isClickable()) {
                    return;
                }
                Intent intent = new Intent(PlayChessActivity.this, PlayChessWebActivity.class);
                intent.putExtra("type", Const.CONS_STR_LOCAL_PLAY);
                intent.putExtra("road", road);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void setData(int level, int star) {
        int starNumber;
        if (level < Const.INTEGER_6) {
            starNumber = 2 - star;
            if (starNumber == 0) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 1) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else {
                setAllControlsBackgroud(R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            }
            setAllControlsVisiblity(View.GONE, View.VISIBLE, View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE);
        } else if (level < Const.INTEGER_16) {
            starNumber = 3 - star;
            if (starNumber == 0) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 1) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 2) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else {
                setAllControlsBackgroud(R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            }
            setAllControlsVisiblity(View.GONE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE);
        } else if (level < Const.INTEGER_27) {
            starNumber = 5 - star;
            if (starNumber == 0) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 1) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 2) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 3) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 4) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else {
                setAllControlsBackgroud(R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            }
            setAllControlsVisiblity(View.GONE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE);
        } else if (level < Const.INTEGER_29) {
            starNumber = 8 - star;
            if (starNumber == 0) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 1) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 2) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 3) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 4) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 5) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 6) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 7) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else {
                setAllControlsBackgroud(R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            }
            setAllControlsVisiblity(View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.GONE, View.GONE);
        } else {
            starNumber = 10 - star;
            if (starNumber == 0) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star);
            } else if (starNumber == 1) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.blue_star);
            } else if (starNumber == 2) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 3) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 4) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 5) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 6) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.yellow_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 7) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.yellow_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 8) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.yellow_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else if (starNumber == 9) {
                setAllControlsBackgroud(R.mipmap.yellow_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            } else {
                setAllControlsBackgroud(R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star,
                        R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star, R.mipmap.blue_star);
            }
            setAllControlsVisiblity(View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE);
        }
    }


    private void setAllControlsVisiblity(int twoLine, int start1, int start2, int start3,
                                         int start4, int start5, int start6, int start7, int start8, int start9, int start10) {
        mTwoLine.setVisibility(twoLine);
        mStart1.setVisibility(start1);
        mStart2.setVisibility(start2);
        mStart3.setVisibility(start3);
        mStart4.setVisibility(start4);
        mStart5.setVisibility(start5);
        mStart6.setVisibility(start6);
        mStart7.setVisibility(start7);
        mStart8.setVisibility(start8);
        mStart9.setVisibility(start9);
        mStart10.setVisibility(start10);
    }

    private void setAllControlsBackgroud(int start1, int start2, int start3,
                                         int start4, int start5, int start6, int start7, int start8, int start9, int start10) {
        mStart1.setBackgroundResource(start1);
        mStart2.setBackgroundResource(start2);
        mStart3.setBackgroundResource(start3);
        mStart4.setBackgroundResource(start4);
        mStart5.setBackgroundResource(start5);
        mStart6.setBackgroundResource(start6);
        mStart7.setBackgroundResource(start7);
        mStart8.setBackgroundResource(start8);
        mStart9.setBackgroundResource(start9);
        mStart10.setBackgroundResource(start10);
    }

    private void getRandomBatle(final View view) {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_RANDOM_BATTLE_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("road", road)
                    .addParams("gameType", gameType)
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
                                Intent intent = new Intent(PlayChessActivity.this, ProgressBarActivity.class);
                                intent.putExtra("gameType", gameType);
                                intent.putExtra("road", road);
                                startActivity(intent);
                            } else if (returnCode == 1005) {
                                showPopupWindow(view, 1);
                            } else if (returnCode == 1010) {
                                showPopupWindow(view, 2);
                            } else if (returnCode == 10048) {
                                ToastUtils.show(error.getString("returnMessage"));
                                Intent intent = new Intent(PlayChessActivity.this, ChessLoginTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                PlayChessActivity.this.startActivity(intent);
                            } else {
                                ToastUtils.show(error.getString("returnMessage"));
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

    //显示提示框
    private void showPopupWindow(View view, final int type) {
        View layout = LayoutInflater.from(PlayChessActivity.this).inflate(R.layout.pop_window_layout, null, false);
        MyPopupWindow.myPopupWindow(view, layout);
        Button btnSava = layout.findViewById(R.id.button_save);
        Button btnCancel = layout.findViewById(R.id.button_cancel);
        TextView texContent = layout.findViewById(R.id.popup_window_content);
        texContent.setText("当前有未完成棋局");
        btnCancel.setText("取消");
        btnSava.setText("确认");
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_save:
                        if (type == 1) {
                            JumpIntent.jump(PlayChessActivity.this, PlayChessWebActivity.class, Const.CONS_STR_PLAY);
                        } else if (type == 2) {
                            JumpIntent.jump(PlayChessActivity.this, PlayChessWebActivity.class, Const.CONS_STR_ACTIVITY_PLAY);
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
}

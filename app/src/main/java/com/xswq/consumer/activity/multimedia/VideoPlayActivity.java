package com.xswq.consumer.activity.multimedia;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.xswq.consumer.R;
import com.xswq.consumer.base.BaseAppCompatActivity;
import com.xswq.consumer.base.BaseBean;
import com.xswq.consumer.bean.BeasBean;
import com.xswq.consumer.bean.MultimediaBean;
import com.xswq.consumer.bean.VideoPlayBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.AnimationAndMusic;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.LogUtil;
import com.xswq.consumer.utils.MediahelperUtil;
import com.xswq.consumer.utils.StatisticsUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import cn.jzvd.JzViewOutlineProvider;
import cn.jzvd.OneSelfInterface;
import iview.PlayStatus;
import okhttp3.Call;

public class VideoPlayActivity extends BaseAppCompatActivity implements View.OnClickListener, PlayStatus, OneSelfInterface, AnimationAndMusic.AnimationStopInterface {

    @BindView(R.id.green_back)
    ImageView mGreenBack;
    @BindView(R.id.story_play_text_title)
    TextView mTextTitle;
    @BindView(R.id.start_time)
    TextView mTextStartTime;
    @BindView(R.id.story_seekBar)
    SeekBar mSeekBar;
    @BindView(R.id.end_time)
    TextView mTextEndTime;
    @BindView(R.id.story_play_jump)
    ImageView mJump;
    @BindView(R.id.video_play)
    JZVideoPlayerStandard videoplayer;
    @BindView(R.id.story_play_button)
    ImageView mPlayButton;
    @BindView(R.id.video_play_full_screen)
    ImageView mVideoPlayFullScreen;
    @BindView(R.id.lav_show)
    ImageView animationView;

    private String videoId;
    private int isPlay = 1;
    private String gateNum;
    private String questionId;
    private String testQuestionId;
    public static VideoPlayActivity instance = null;
    private int maxGateNum;
    private int maxSeat;
    private int seat;
    private long time;
    private boolean isSaveByVideoId = true;

    @Override
    public int bindLayout() {
        return R.layout.activity_video_play_layout;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initData() {
        initView();
        getVideoData();
        videoplayer.setSeekBarListener(mSeekBar);
        videoplayer.setOneSelfInterface(this);
        videoplayer.TOOL_BAR_EXIST = true;
        videoplayer.setOutlineProvider(new JzViewOutlineProvider(85));
        videoplayer.setClipToOutline(true);
        animationView.setClipToOutline(true);

        StatisticsUtil.getStatistics(VideoPlayActivity.this, Const.STR12);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.green_back:
                finish();
                break;
            case R.id.video_play_full_screen:
                if (ConstUtils.isClickable()) {
                    return;
                }
                videoplayer.startWindowFullscreen();
                break;
            case R.id.story_play_jump:
                if (ConstUtils.isClickable()) {
                    return;
                }
                if (!TextUtils.isEmpty(gateNum)) {
                    getinsertSekiguchiRecord();
                    getJustRecord();
                }
                break;
            case R.id.story_play_button:
                if (ConstUtils.isClickable()) {
                    return;
                }
                if (isPlay == 0) {
                    mPlayButton.setBackgroundResource(R.mipmap.story_play_button);
                    JZMediaManager.instance().mediaPlayer.start();
                    videoplayer.onStatePlaying();
                    isPlay = 1;
                } else if (isPlay == 1) {
                    mPlayButton.setBackgroundResource(R.mipmap.story_pauses_button);
                    JZMediaManager.instance().mediaPlayer.pause();
                    videoplayer.onStatePause();
                    isPlay = 0;
                } else {
                    mPlayButton.setBackgroundResource(R.mipmap.story_play_button);
                    videoplayer.startVideo();
                    isPlay = 1;
                }
                mVideoPlayFullScreen.setOnClickListener(this);
                break;
            default:
                break;
        }
    }

    private void initView() {
        instance = this;
        videoplayer.setPlayStatus(this);
        Date date = new Date(System.currentTimeMillis());
        time = date.getTime();
        MultimediaBean multimediaBean = (MultimediaBean) getIntent().getSerializableExtra("multimediaBean");
        videoId = multimediaBean.getVideoId();
        gateNum = multimediaBean.getGateNum();
        questionId = multimediaBean.getQuestionId();
        testQuestionId = multimediaBean.getTestQuestionId();
        maxGateNum = multimediaBean.getMaxGateNum();
        maxSeat = multimediaBean.getMaxSeat();
        seat = multimediaBean.getSeat();
        if (maxGateNum > Integer.valueOf(gateNum)) {
            mJump.setVisibility(View.VISIBLE);
        } else if (maxGateNum == Integer.valueOf(gateNum)) {
            if (maxSeat > seat) {
                mJump.setVisibility(View.VISIBLE);
            } else if (maxSeat == seat) {
                int i;
                if (TextUtils.isEmpty(testQuestionId)) {
                    i = 4;
                } else {
                    i = 5;
                }
                if (maxSeat >= i) {
                    mJump.setVisibility(View.VISIBLE);
                } else {
                    mJump.setVisibility(View.GONE);
                }
            } else {
                mJump.setVisibility(View.GONE);
            }
        } else {
            mJump.setVisibility(View.GONE);
        }
        mPlayButton.setOnClickListener(this);
        mVideoPlayFullScreen.setOnClickListener(this);
        //将缩略图的scaleType设置为FIT_XY（图片全屏）
        videoplayer.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //设置容器内播放器高,解决黑边（视频全屏）
        JZVideoPlayer.setVideoImageDisplayType(JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT);
        //设置全屏播放
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //横向
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //纵向
    }

    private void getVideoData() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_VIDEO_BY_ID_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("videoId", videoId)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onResponse(String response, int id) {
                    VideoPlayBean videoPlayBean = GsonUtil.gsonToBean(response, VideoPlayBean.class, VideoPlayActivity.this);
                    if (videoPlayBean != null) {
                        VideoPlayBean.DataBean data = videoPlayBean.getData();
                        String videoName = data.getVideoName();
                        String videoUrl = data.getVideoUrl();
                        mTextTitle.setText(ConstUtils.signString(videoName));
                        if (!TextUtils.isEmpty(videoUrl) && videoplayer != null) {
                            videoplayer.setUp(ContactUrl.VIDEO_PATH + videoUrl, JZVideoPlayerStandard.SCROLL_AXIS_HORIZONTAL);
                            new AnimationAndMusic(VideoPlayActivity.this, animationView, "xiao_yuan1.mp3", R.drawable.push_animation_2, 1);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (videoplayer != null) {
                if (isPlay == 0) {
                    mPlayButton.setBackgroundResource(R.mipmap.story_play_button);
                    JZMediaManager.instance().mediaPlayer.start();
                    videoplayer.onStatePlaying();
                } else if (isPlay == 1) {
                    mPlayButton.setBackgroundResource(R.mipmap.story_pauses_button);
                    JZMediaManager.instance().mediaPlayer.pause();
                    videoplayer.onStatePause();
                } else {
                    mPlayButton.setBackgroundResource(R.mipmap.story_play_button);
                    videoplayer.startVideo();
                    isPlay = 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            if (videoplayer != null) {
                if (videoplayer != null) {
                    if (isPlay == 0) {
                        mPlayButton.setBackgroundResource(R.mipmap.story_pauses_button);
                        JZMediaManager.instance().mediaPlayer.pause();
                        videoplayer.onStatePause();
                    } else if (isPlay == 1) {
                        mPlayButton.setBackgroundResource(R.mipmap.story_play_button);
                        JZMediaManager.instance().mediaPlayer.start();
                        videoplayer.onStatePlaying();
                    } else {
                        mPlayButton.setBackgroundResource(R.mipmap.story_play_button);
                        videoplayer.startVideo();
                        isPlay = 1;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getProgressAndText(int progress, int position, int duration) {
        try {
            mSeekBar.setProgress(progress);
            String startTime = JZUtils.stringForTime(position);
            String endTime = JZUtils.stringForTime(duration);
            mTextStartTime.setText(startTime);
            mTextEndTime.setText(endTime);

            Date nowDate = new Date(System.currentTimeMillis());
            long nowTime = nowDate.getTime();
            long watchTtheTime = nowTime - time;
            int savaTimer = duration / 3 * 2;
            if (position > 0 && duration > 0 && isSaveByVideoId) {
                if (position >= savaTimer && watchTtheTime >= savaTimer) {
                    if (!TextUtils.isEmpty(videoId)) {
                        isSaveByVideoId = false;
                        getAddStoryRecord(videoId);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStateAutoComplete() {
        isPlay = 3;
        mPlayButton.setBackgroundResource(R.mipmap.story_pauses_button);
        mTextStartTime.setText(mTextEndTime.getText());
        mVideoPlayFullScreen.setOnClickListener(null);
        mSeekBar.setProgress(100);
        if (!TextUtils.isEmpty(gateNum)) {
            getinsertSekiguchiRecord();
        }
    }

    //添加关卡记录
    private void getinsertSekiguchiRecord() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_INSERT_RECORD_PATH)
                    .addParams("gateNum", gateNum)
                    .addParams("seat", Const.STR3)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    BeasBean beasBean = GsonUtil.gsonToBean(response, BeasBean.class, VideoPlayActivity.this);
                    if (beasBean != null) {
                        MultimediaBean multimediaBean = new MultimediaBean();
                        multimediaBean.setGateNum(gateNum);
                        multimediaBean.setQuestionId(questionId);
                        multimediaBean.setTestQuestionId(testQuestionId);
                        multimediaBean.setMaxSeat(maxSeat);
                        multimediaBean.setMaxGateNum(maxGateNum);
                        multimediaBean.setSeat(seat);
                        JumpIntent.jump(VideoPlayActivity.this, StoryChessPlayActivity.class, multimediaBean);
                        finish();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //跳过记录接口
    private void getJustRecord() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_JUST_BUTTON)
                    .addParams("gateNum", gateNum)
                    .addParams("seat", Const.STR3)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    BeasBean beasBean = GsonUtil.gsonToBean(response, BeasBean.class, VideoPlayActivity.this);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onVideoPause() {
        mPlayButton.setBackgroundResource(R.mipmap.story_pauses_button);
    }

    @Override
    public void onVideoPlaying() {
        mPlayButton.setBackgroundResource(R.mipmap.story_play_button);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MediahelperUtil.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void AnimationStop(int type) {
        videoplayer.startVideo();
        mJump.setOnClickListener(VideoPlayActivity.this);
        mGreenBack.setOnClickListener(VideoPlayActivity.this);
    }

    private void getAddStoryRecord(final String storyId) {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_ADD_STORY_RECORD)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("storyId", storyId)
                    .addParams("terminalType", Const.STR2)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    if (!TextUtils.isEmpty(response)) {
                        BaseBean baseBean = GsonUtil.gsonToBean(response, BaseBean.class, VideoPlayActivity.this);
                        if (baseBean != null) {
                            LogUtil.e("storyId: ", storyId);
                        }
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

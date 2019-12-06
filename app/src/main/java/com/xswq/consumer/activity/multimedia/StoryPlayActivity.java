package com.xswq.consumer.activity.multimedia;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xswq.consumer.R;
import com.xswq.consumer.base.BaseAppCompatActivity;
import com.xswq.consumer.base.BaseBean;
import com.xswq.consumer.bean.BeasBean;
import com.xswq.consumer.bean.MultimediaBean;
import com.xswq.consumer.bean.StoryPlayBean;
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
import com.xswq.consumer.view.WeiQiStoryImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import okhttp3.Call;

public class StoryPlayActivity extends BaseAppCompatActivity implements View.OnClickListener, AnimationAndMusic.AnimationStopInterface {
    @BindView(R.id.green_back)
    ImageView mGreenBack;
    @BindView(R.id.story_play_jump)
    ImageView mJump;
    @BindView(R.id.story_play_text_title)
    TextView mTextTitle;
    @BindView(R.id.story_seekBar)
    SeekBar mSeekBar;
    @BindView(R.id.start_time)
    TextView mStartTime;
    @BindView(R.id.end_time)
    TextView mEndTime;
    @BindView(R.id.nice_image_view)
    WeiQiStoryImageView mNiceImageView;
    @BindView(R.id.story_play_button)
    ImageView mPlayImage;
    @BindView(R.id.lav_show)
    ImageView animationView;

    private String id;
    private String changeTime;
    private String storySound;
    private String storyImg;
    private String storyName;
    private MediaPlayer mediaPlayer;//媒体播放器
    private int currentPosition = 0;//当前音乐播放的进度
    private long soundTime;
    private Timer timer;
    private boolean isSeekBarChanging;//互斥变量，防止进度条与定时器冲突。
    private long changeTimes = 0;
    private long changeTimes2;
    private int inSubscript = 1;
    private String[] storyImgUrl;
    private String[] changeTimer;
    private boolean playType = false;
    private String videoId;
    private String gameId;
    private String gateNum;
    private String questionId;
    private String testQuestionId;
    public static StoryPlayActivity instance = null;
    private int maxGateNum;
    private int maxSeat;
    private int seat;
    private long time;
    private boolean isReading = false;
    private int duration;
    private boolean isSaveByVideoId = true;

    @Override
    public int bindLayout() {
        return R.layout.activity_story_play_layout;
    }

    @Override
    public void initData() {
        instance = this;
        Date date = new Date(System.currentTimeMillis());
        time = date.getTime();
        MultimediaBean multimediaBean = (MultimediaBean) getIntent().getSerializableExtra("multimediaBean");
        MediahelperUtil.setSmallVolume();
        id = multimediaBean.getStoryId();
        videoId = multimediaBean.getVideoId();
        gameId = multimediaBean.getGameId();
        gateNum = multimediaBean.getGateNum();
        questionId = multimediaBean.getQuestionId();
        testQuestionId = multimediaBean.getTestQuestionId();
        maxGateNum = multimediaBean.getMaxGateNum();
        maxSeat = multimediaBean.getMaxSeat();
        seat = multimediaBean.getSeat();
        initView();
        getStoryById();
        StatisticsUtil.getStatistics(StoryPlayActivity.this, Const.STR10);
    }

    private void initView() {
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
        mPlayImage.setOnClickListener(StoryPlayActivity.this);

        //实例化媒体播放器
        mediaPlayer = new MediaPlayer();
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            /*滚动时,应当暂停后台定时器*/
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeekBarChanging = true;
            }

            /*滑动结束后，重新设置值*/
            public void onStopTrackingTouch(SeekBar seekBar) {
                isSeekBarChanging = false;
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                onOver();
                if (!TextUtils.isEmpty(gateNum) && isReading) {
                    new AnimationAndMusic(StoryPlayActivity.this, animationView, "xiao_tian2.mp3", R.drawable.push_animation_1, 2);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.green_back:
                finish();
                break;
            case R.id.story_play_button:
                if (ConstUtils.isClickable()) {
                    return;
                }
                if (playType) {
                    onStarts();
                } else {
                    onPauses();
                }
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
            default:
                break;
        }
    }

    private void getStoryById() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_STORY_BY_ID_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("id", id)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    StoryPlayBean storyPlayBean = GsonUtil.gsonToBean(response, StoryPlayBean.class, StoryPlayActivity.this);
                    if (storyPlayBean != null) {
                        StoryPlayBean.DataBean data = storyPlayBean.getData();
                        storyName = data.getStoryName();
                        storyImg = data.getStoryImg();
                        storySound = data.getStorySound();
                        changeTime = data.getChangeTime();
                        storyImgUrl = new String[]{};
                        if (!TextUtils.isEmpty(storyImg)) {
                            storyImgUrl = storyImg.split(";");
                        }
                        changeTimer = new String[]{};
                        if (!TextUtils.isEmpty(changeTime)) {
                            changeTimer = changeTime.split(";");
                        }
                        Glide.with(StoryPlayActivity.this)
                                .load(storyImgUrl[0])
                                .asBitmap()
                                .error(R.mipmap.default_xswq)
                                .placeholder(R.mipmap.default_xswq)
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(mNiceImageView);
                        mTextTitle.setText(ConstUtils.signString(storyName));
                        new AnimationAndMusic(StoryPlayActivity.this, animationView, "xiao_tian1.mp3", R.drawable.push_animation_5, 1);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //播放器初始化
    private void play() {
        if (!TextUtils.isEmpty(storySound)) {
            try {
                mSeekBar.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
                mediaPlayer.reset();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);//设置音频类型
                mediaPlayer.setDataSource(storySound);//设置mp3数据源
                mediaPlayer.prepareAsync();//数据缓冲
                mediaPlayer.setVolume(1.0f, 1.0f);
                /*监听缓存 事件，在缓冲完毕后，开始播放*/
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    public void onPrepared(MediaPlayer mp) {
                        mSeekBar.setProgress(currentPosition);
                        duration = mediaPlayer.getDuration();
                        soundTime = (long) duration;
                        String endToString = ConstUtils.getDateToString(soundTime, "mm:ss");
                        mEndTime.setText(endToString);
                        mp.start();
                        mp.seekTo(currentPosition);
                        mSeekBar.setMax(mediaPlayer.getDuration());
                        isReading = true;
                    }
                });
                //监听播放时回调函数
                if (timer == null) {
                    timer = new Timer();
                }
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            if (!isSeekBarChanging && mSeekBar != null) {
                                if (mediaPlayer == null) {
                                    mSeekBar.setProgress(0);
                                    currentPosition = 0;
                                } else {
                                    if (null != mediaPlayer) {
                                        if (!mediaPlayer.isPlaying()) {//如果不在播放状态，则停止更新//播放器进度条，防止界面报错
                                            return;
                                        }
                                    }
                                    int currentPosition = mediaPlayer.getCurrentPosition();
                                    mSeekBar.setProgress(currentPosition);
                                    Message msg = new Message();
                                    msg.what = 1;
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("currentPosition", currentPosition);//往Bundle中存放数据
                                    msg.setData(bundle);//mes利用Bundle传递数据
                                    handler.sendMessage(msg);//用activity中的handler发送消息
                                }
                            }
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (SecurityException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            e.getMessage();
                        } catch (NullPointerException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            e.getMessage();
                        }
                    }
                }, 0, 1000);
            } catch (Exception e) {
                if (mediaPlayer != null) {
                    mediaPlayer.reset();
                }
                e.printStackTrace();
                System.out.println(e);
            }
        }
    }

    //继续
    private void onStarts() {
        mSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        isSeekBarChanging = false;
        playType = false;
        mPlayImage.setBackgroundResource(R.mipmap.story_play_button);
        play();
    }

    //暂停
    private void onPauses() {
        mSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        currentPosition = mediaPlayer.getCurrentPosition();//记录播放的位置
        mediaPlayer.stop();//暂停状态
        if (timer != null) {
            timer.purge();//移除所有任务;
        }
        mPlayImage.setBackgroundResource(R.mipmap.story_pauses_button);
        isSeekBarChanging = true;
        playType = true;
    }

    //结束
    private void onOver() {
        currentPosition = 0;
        inSubscript = 1;
        mSeekBar.setProgress(0);
        mStartTime.setText("00:00");
        isSeekBarChanging = true;
        playType = true;
        mPlayImage.setBackgroundResource(R.mipmap.story_pauses_button);
        mSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    //添加关卡记录
    private void getinsertSekiguchiRecord() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_INSERT_RECORD_PATH)
                    .addParams("gateNum", gateNum)
                    .addParams("seat", Const.STR1)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    BeasBean beasBean = GsonUtil.gsonToBean(response, BeasBean.class, StoryPlayActivity.this);
                    if (beasBean != null) {
                        MultimediaBean multimediaBean = new MultimediaBean();
                        multimediaBean.setGameId(gameId);
                        multimediaBean.setGateNum(gateNum);
                        multimediaBean.setVideoId(videoId);
                        multimediaBean.setQuestionId(questionId);
                        multimediaBean.setTestQuestionId(testQuestionId);
                        multimediaBean.setMaxSeat(maxSeat);
                        multimediaBean.setMaxGateNum(maxGateNum);
                        multimediaBean.setSeat(seat);
                        JumpIntent.jump(StoryPlayActivity.this, GamePlayActivity.class, multimediaBean);
                        MediahelperUtil.pause();
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
                    .addParams("seat", Const.STR1)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    BeasBean beasBean = GsonUtil.gsonToBean(response, BeasBean.class, StoryPlayActivity.this);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        MediahelperUtil.setLargeVolume();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        onPauses();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onStarts();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case 1:
                        long currentPositions = (long) msg.getData().getInt("currentPosition");//接受msg传递过来进度条的进度
                        String dateToString = ConstUtils.getDateToString(currentPositions, "mm:ss");
                        mStartTime.setText(dateToString);
                        changeTimes = (currentPositions / 1000);
                        changeTimes2 = Long.parseLong(changeTimer[inSubscript]);
                        if (changeTimes >= changeTimes2) {
                            Glide.with(StoryPlayActivity.this)
                                    .load(storyImgUrl[inSubscript])
                                    .asBitmap()
                                    .error(R.mipmap.default_xswq)
                                    .placeholder(R.mipmap.default_xswq)
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .into(mNiceImageView);
                            if (inSubscript < storyImgUrl.length - 1) {
                                inSubscript++;
                            }
                        }
                        onPrepared(currentPositions);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }
    };

    @Override
    public void AnimationStop(int type) {
        if (type == 1) {
            play();
        } else {
            getinsertSekiguchiRecord();
        }
        mJump.setOnClickListener(StoryPlayActivity.this);
        mGreenBack.setOnClickListener(StoryPlayActivity.this);
    }

    private void onPrepared(long currentPositions) {
        if (!TextUtils.isEmpty(id)) {
            int savaTimer = duration / 3 * 2;
            Date nowDate = new Date(System.currentTimeMillis());
            long nowTime = nowDate.getTime();
            long watchTtheTime = nowTime - time;
            LogUtil.e("aaa", "currentPosition :" + currentPositions);
            LogUtil.e("aaa", "duration :" + duration);
            LogUtil.e("aaa", "savaTimer :" + savaTimer);
            LogUtil.e("aaa", "watchTtheTime :" + watchTtheTime);
            if (currentPositions > 0 && duration > 0 && isSaveByVideoId) {
                if (currentPositions >= savaTimer && watchTtheTime >= savaTimer) {
                    getAddStoryRecord(id);
                    isSaveByVideoId = false;
                }
            }
        }
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
                        BaseBean baseBean = GsonUtil.gsonToBean(response, BaseBean.class, StoryPlayActivity.this);
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

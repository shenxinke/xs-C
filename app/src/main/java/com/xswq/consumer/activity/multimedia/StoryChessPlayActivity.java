package com.xswq.consumer.activity.multimedia;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.xswq.consumer.R;
import com.xswq.consumer.activity.main.MainActivity;
import com.xswq.consumer.base.BaseAppCompatActivity;
import com.xswq.consumer.activity.login.ChessLoginTypeActivity;
import com.xswq.consumer.bean.BeasBean;
import com.xswq.consumer.bean.MultimediaBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.AnimationAndMusic;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.LogUtil;
import com.xswq.consumer.utils.MediahelperUtil;
import com.xswq.consumer.utils.PreferencesUtil;
import com.xswq.consumer.utils.StatisticsUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import butterknife.BindView;
import okhttp3.Call;

public class StoryChessPlayActivity extends BaseAppCompatActivity implements View.OnClickListener, AnimationAndMusic.AnimationStopInterface {
    public MediaPlayer player;
    @BindView(R.id.web_view)
    WebView wvTask;
    @BindView(R.id.green_back)
    ImageView mBlack;
    @BindView(R.id.play_jump)
    ImageView mJump;
    @BindView(R.id.loading_image)
    ImageView mLoadingImage;
    @BindView(R.id.login_bg)
    ImageView mLoadingBg;
    @BindView(R.id.login_text)
    ImageView mLoadingText;
    @BindView(R.id.lav_show)
    ImageView animationView;

    private String questionId;
    private String testQuestionId;
    private String gateNum;
    private String TAG = "StoryChessPlayActivity";
    private int maxGateNum;
    private int maxSeat;
    private int seat;
    int[] imgRes = new int[]{R.mipmap.login_1, R.mipmap.login_2, R.mipmap.login_3, R.mipmap.login_4, R.mipmap.login_5, R.mipmap.login_6};
    private boolean speakericon;

    @Override
    public int bindLayout() {
        return R.layout.activity_chess_play_layout;
    }

    @Override
    public void initData() {
        int randomnum = (int) (Math.random() * 5);
        mLoadingImage.setBackgroundResource(imgRes[randomnum]);
        MultimediaBean multimediaBean = (MultimediaBean) getIntent().getSerializableExtra("multimediaBean");
        questionId = multimediaBean.getQuestionId();
        testQuestionId = multimediaBean.getTestQuestionId();
        gateNum = multimediaBean.getGateNum();
        maxGateNum = multimediaBean.getMaxGateNum();
        maxSeat = multimediaBean.getMaxSeat();
        seat = multimediaBean.getSeat();
        initView();
        new AnimationAndMusic(StoryChessPlayActivity.this, animationView, "xiao_yuan2.mp3", R.drawable.push_animation_4, 1);
        StatisticsUtil.getStatistics(StoryChessPlayActivity.this, Const.STR13);
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
        wvTask.clearHistory();
        wvTask.setBackgroundColor(0); // 设置背景色
        wvTask.setWebContentsDebuggingEnabled(true);
        wvTask.clearCache(true);
        WebSettings webSettings = wvTask.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        String url;
        speakericon = PreferencesUtil.getBoolean(StoryChessPlayActivity.this, "speakericon");
        boolean specialEffects = PreferencesUtil.getBoolean(StoryChessPlayActivity.this, "special_effects");
        if (specialEffects) {
            url = ContactUrl.POST_PLAY_CHESS_PATH + "uid=" + Const.UID + "&token=" + Const.TOKEN + "&gateNum=" + gateNum + "&skinId=" + Const.SKINID + "&effectId=" + Const.STR0;
        } else {
            url = ContactUrl.POST_PLAY_CHESS_PATH + "uid=" + Const.UID + "&token=" + Const.TOKEN + "&gateNum=" + gateNum + "&skinId=" + Const.SKINID + "&effectId=" + Const.STR0;
        }
        wvTask.loadUrl(url);
        LogUtil.e(TAG, url);
        wvTask.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.e("StoryChessPlayActivity","url :"+url);
                if (url.contains("myschema://go?a=14")) {
                    LogUtil.e("StoryChessPlayActivity",url);
                    new AnimationAndMusic(StoryChessPlayActivity.this, animationView, "qian_mo2.mp3", R.drawable.push_animation_3, 0);
                    return true;
                } else if (url.contains("myschema://go?a=11")) {
                    if (!speakericon) {
                        AssetManager assetManager = getAssets();
                        AssetFileDescriptor fileDescriptor;
                        try {
                            if (url.contains(".mp3")) {
                                String musicname = url.split("&music=")[1];
                                fileDescriptor = assetManager.openFd("xswl_local/" + musicname);
                            } else {
                                String musicname = url.split("&music=")[1] + ".mp3";
                                fileDescriptor = assetManager.openFd("xswl_local/music/" + musicname);
                            }
                            midPlayerIsReading();
                            player.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mp.start();
                                }
                            });
                            player.prepareAsync();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //添加完成事件监听器，用于当音乐播放完毕后，重新开始播放音乐
                        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer arg0) {
                                player.release();
                                player = null;
                            }
                        });
                    }
                    return true;
                } else if (url.contains("myschema://go?a=0")) {
                    mLoadingImage.setVisibility(View.GONE);
                    mLoadingBg.setVisibility(View.GONE);
                    mLoadingText.setVisibility(View.GONE);
                    return true;
                } else if (url.contains("myschema://go?a=2")) {
                    ToastUtils.show(Const.CONS_STR_USER_PAST_DUE);
                    Intent intent = new Intent(StoryChessPlayActivity.this, ChessLoginTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return true;
                } else if (url.contains("myschema://go?a=4")) {
                    if (!TextUtils.isEmpty(gateNum)) {
                        new AnimationAndMusic(StoryChessPlayActivity.this, animationView, "qian_mo1.mp3", R.drawable.push_animation_3, 2);
                        LogUtil.e("StoryChessPlayActivity",url);
                    }
                    return true;
                } else if (url.contains("myschema://go?a=8")) {
                    if (!speakericon) {
                        try {
                            String musicname = url.split("&music=")[1] + ".wav";
                            AssetManager assetManager = getAssets();
                            AssetFileDescriptor fileDescriptor = assetManager.openFd("xswl_local/music/" + musicname);
                            midPlayerIsReading();
                            player.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mp.start();
                                }
                            });
                            player.prepareAsync();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //添加完成事件监听器，用于当音乐播放完毕后，重新开始播放音乐
                        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer arg0) {
                                player.release();
                                player = null;
                            }
                        });
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void onReceivedError(WebView var1, int var2, String var3, String var4) {
                ToastUtils.show("页面加载失败,请检查网络!");
            }
        });
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
            case R.id.play_jump:
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

    //添加关卡记录
    private void getinsertSekiguchiRecord() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_INSERT_RECORD_PATH)
                    .addParams("gateNum", gateNum)
                    .addParams("seat", Const.STR4)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    BeasBean beasBean = GsonUtil.gsonToBean(response, BeasBean.class, StoryChessPlayActivity.this);
                    if (beasBean != null) {
                        boolean twoClassGuide = PreferencesUtil.getBoolean(StoryChessPlayActivity.this, "twoClassGuide");
                        if (!TextUtils.isEmpty(testQuestionId)) {
                            MultimediaBean multimediaBean = new MultimediaBean();
                            multimediaBean.setGateNum(gateNum);
                            multimediaBean.setMaxSeat(maxSeat);
                            multimediaBean.setMaxGateNum(maxGateNum);
                            multimediaBean.setSeat(seat);
                            multimediaBean.setTestQuestionId(testQuestionId);
                            JumpIntent.jump(StoryChessPlayActivity.this, TestPlayActivity.class, multimediaBean);
                        } else if (!twoClassGuide) {
                            PreferencesUtil.putBoolean(StoryChessPlayActivity.this,"oneClassGuideIsOver",true);
                            Intent intent = new Intent(StoryChessPlayActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
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
                    .addParams("seat", Const.STR4)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    BeasBean beasBean = GsonUtil.gsonToBean(response, BeasBean.class, StoryChessPlayActivity.this);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //播放器是否准备就绪
    public void midPlayerIsReading() {
        if (player == null) {
            player = new MediaPlayer();
        }
        if (player.isPlaying()) {
            player.stop();
            player.release();
            player = null;
            player = new MediaPlayer();
        }
        player.reset();//每次使用MediaPlarer都是要先初始化和释放资源的，也就是要reset()的，因为java里面的mediaplayer对象的状态和native的对象状态发生了不一致。
    }

    @Override
    protected void onStart() {
        super.onStart();
        MediahelperUtil.resume();
    }

    @Override
    protected void onDestroy() {
        if (wvTask != null) {
            wvTask.removeAllViews();
            wvTask.destroy();
            System.gc();
        }
        super.onDestroy();
    }

    @Override
    public void AnimationStop(int type) {
        if (type == 2) {
            getinsertSekiguchiRecord();
        }
        mJump.setOnClickListener(StoryChessPlayActivity.this);
        mBlack.setOnClickListener(this);
        System.gc();
    }

    @Override
    protected void onPause() {
        if (wvTask != null) {
            wvTask.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wvTask != null) {
            wvTask.onResume();
        }
    }
}
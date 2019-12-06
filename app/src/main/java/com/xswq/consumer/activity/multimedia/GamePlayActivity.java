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
import com.xswq.consumer.activity.login.ChessLoginTypeActivity;
import com.xswq.consumer.base.BaseAppCompatActivity;
import com.xswq.consumer.bean.BeasBean;
import com.xswq.consumer.bean.MultimediaBean;
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
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import butterknife.BindView;
import okhttp3.Call;

public class GamePlayActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private String TAG = "GamePlayActivity";

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
    private String gateNum;
    private String videoId;
    private String questionId;
    private String testQuestionId;
    private int maxGateNum;
    private int maxSeat;
    private int seat;
    private MediaPlayer player;
    private MediaPlayer bgPlayer;
    int[] imgRes = new int[]{R.mipmap.login_1, R.mipmap.login_2, R.mipmap.login_3, R.mipmap.login_4, R.mipmap.login_5, R.mipmap.login_6};
    private boolean isPause = false;
    private String gameId;
    private boolean musictoneicon;
    private boolean speakericon;

    @Override
    public int bindLayout() {
        return R.layout.activity_chess_play_layout;
    }

    @Override
    public void initData() {
        initView();
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
        musictoneicon = PreferencesUtil.getBoolean(GamePlayActivity.this, "musictoneicon");
        speakericon = PreferencesUtil.getBoolean(GamePlayActivity.this, "speakericon");
        String userName = PreferencesUtil.getString(GamePlayActivity.this, "username");
        String url = ContactUrl.POST_GAME_PATH + "userid=" + Const.UID + "&username=" + userName + "&token=" + Const.TOKEN + "&gateid=" + gateNum + "&gameid=" + gameId;
        wvTask.loadUrl(url);
        LogUtil.e(TAG, url);
        wvTask.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final String url) {
                if (url.contains("myschema://go?a=13")) {
                    AssetManager assetManager = getAssets();
                    AssetFileDescriptor fileDescriptor;
                    try {
                        String musicname = url.split("&music=")[1];
                        fileDescriptor = assetManager.openFd("game/" + musicname);
                        if (url.contains("BGMusic")) {
                            if (!musictoneicon) {
                                isPause = false;
                                bgMidPlayerIsReading();
                                bgPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getStartOffset());
                                bgPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mp) {
                                        mp.start();
                                    }
                                });
                                //添加完成事件监听器，用于当音乐播放完毕后，重新开始播放音乐
                                bgPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer arg0) {
                                        arg0.start();
                                    }
                                });
                                bgPlayer.prepareAsync();
                            }
                        } else {
                            if (!speakericon) {
                                midPlayerIsReading();
                                player.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getStartOffset());
                                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mp) {
                                        mp.start();
                                    }
                                });
                                player.prepareAsync();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mLoadingImage.setVisibility(View.GONE);
                    mLoadingBg.setVisibility(View.GONE);
                    mLoadingText.setVisibility(View.GONE);
                    return true;
                } else if (url.contains("myschema://go?a=0")) {
                    mLoadingImage.setVisibility(View.GONE);
                    mLoadingBg.setVisibility(View.GONE);
                    mLoadingText.setVisibility(View.GONE);
                    return true;
                } else if (url.contains("myschema://go?a=2")) {
                    ToastUtils.show(Const.CONS_STR_USER_PAST_DUE);
                    Intent intent = new Intent(GamePlayActivity.this, ChessLoginTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return true;
                } else if (url.contains("myschema://go?a=3")) {
                    if (!TextUtils.isEmpty(gateNum)) {
                        getinsertSekiguchiRecord();
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
        StatisticsUtil.getStatistics(GamePlayActivity.this, Const.STR11);
    }

    private void initView() {
        int randomnum = (int) (Math.random() * 5);
        mLoadingImage.setBackgroundResource(imgRes[randomnum]);
        MultimediaBean multimediaBean = (MultimediaBean) getIntent().getSerializableExtra("multimediaBean");
        videoId = multimediaBean.getVideoId();
        gameId = multimediaBean.getGameId();
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


        mBlack.setOnClickListener(this);
        mJump.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MediahelperUtil.pause();
    }

    //添加关卡记录
    private void getinsertSekiguchiRecord() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_INSERT_RECORD_PATH)
                    .addParams("gateNum", gateNum)
                    .addParams("seat", Const.STR2)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    BeasBean beasBean = GsonUtil.gsonToBean(response, BeasBean.class, GamePlayActivity.this);
                    if (beasBean != null) {
                        MultimediaBean multimediaBean = new MultimediaBean();
                        multimediaBean.setGateNum(gateNum);
                        multimediaBean.setVideoId(videoId);
                        multimediaBean.setQuestionId(questionId);
                        multimediaBean.setTestQuestionId(testQuestionId);
                        multimediaBean.setMaxSeat(maxSeat);
                        multimediaBean.setMaxGateNum(maxGateNum);
                        multimediaBean.setSeat(seat);
                        JumpIntent.jump(GamePlayActivity.this, VideoPlayActivity.class, multimediaBean);
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
                    .addParams("seat", Const.STR2)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    BeasBean beasBean = GsonUtil.gsonToBean(response, BeasBean.class, GamePlayActivity.this);
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

    //播放器是否准备就绪
    public void bgMidPlayerIsReading() {
        if (bgPlayer == null) {
            bgPlayer = new MediaPlayer();
        }
        if (bgPlayer.isPlaying()) {
            bgPlayer.stop();
            bgPlayer.release();
            bgPlayer = null;
            bgPlayer = new MediaPlayer();
        }
        bgPlayer.reset();//每次使用MediaPlarer都是要先初始化和释放资源的，也就是要reset()的，因为java里面的mediaplayer对象的状态和native的对象状态发生了不一致。
    }

    @Override
    protected void onDestroy() {
        try {
            if (player != null) {
                if (player.isPlaying()) {
                    player.stop();//停止音频的播放
                }
                player.release();//释放资源
            }
            if (bgPlayer != null) {
                if (bgPlayer.isPlaying()) {
                    bgPlayer.stop();//停止音频的播放
                }
                bgPlayer.release();//释放资源
            }
            if (wvTask != null) {
                wvTask.removeAllViews();
                wvTask.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bgPlayer != null && bgPlayer.isPlaying()) {
            bgPlayer.pause();
            isPause = true;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (bgPlayer != null && isPause) {
            bgPlayer.start();
            isPause = false;
        }
    }
}

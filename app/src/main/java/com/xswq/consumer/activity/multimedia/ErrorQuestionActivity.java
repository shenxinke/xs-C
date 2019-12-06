package com.xswq.consumer.activity.multimedia;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.view.View;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.xswq.consumer.activity.login.ChessLoginTypeActivity;
import com.xswq.consumer.activity.personalCenter.ShoppingMallActivity;
import com.xswq.consumer.activity.playChessWebView.PlayChessWebActivity;
import com.xswq.consumer.base.BaseWebActivity;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.LogUtil;
import com.xswq.consumer.utils.PreferencesUtil;
import com.xswq.consumer.utils.ToastUtils;

public class ErrorQuestionActivity extends BaseWebActivity {
    private String TAG = "ErrorQuestionActivity";
    private boolean speakericon;
    private String errorQuestionType;

    @Override
    public String initUrlData() {
        String strUrl;
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        try {
            errorQuestionType = java.net.URLEncoder.encode(type, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean mistakesGuide = PreferencesUtil.getBoolean(ErrorQuestionActivity.this, "mistakesGuide");
        speakericon = PreferencesUtil.getBoolean(ErrorQuestionActivity.this, "speakericon");
        boolean specialEffects = PreferencesUtil.getBoolean(ErrorQuestionActivity.this, "special_effects");
        if (!mistakesGuide) {
            strUrl = ContactUrl.POST_WRONG_QUESTION_PATH + "uid=" + Const.UID + "&token=" + Const.TOKEN + "&skinId=" + Const.SKINID + "&effectId=" + Const.STR0 + "&guide=" + Const.STR1 + "&errorQuestionType=" + errorQuestionType;
        } else {
            if (specialEffects) {
                strUrl = ContactUrl.POST_WRONG_QUESTION_PATH + "uid=" + Const.UID + "&token=" + Const.TOKEN + "&skinId=" + Const.SKINID + "&effectId=" + Const.STR0 + "&errorQuestionType=" + errorQuestionType;
            } else {
                strUrl = ContactUrl.POST_WRONG_QUESTION_PATH + "uid=" + Const.UID + "&token=" + Const.TOKEN + "&skinId=" + Const.SKINID + "&effectId=" + Const.STR0 + "&errorQuestionType=" + errorQuestionType;
            }
        }
        PreferencesUtil.putBoolean(ErrorQuestionActivity.this, "mistakesGuide", true);
        LogUtil.e(TAG, strUrl);
        return strUrl;
    }

    @Override
    public void getWebViewClinck() {
        wvTask.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("myschema://go?a=12")) {
                    JumpIntent.jump(ErrorQuestionActivity.this, ShoppingMallActivity.class, true);
                    return true;
                } else if (url.contains("myschema://go?a=11")) {
                    if (!speakericon) {
                        try {
                            if (url.contains(".mp3")) {
                                String musicname = url.split("&music=")[1];
                                musicPlay(musicname);
                            } else {
                                String musicname = url.split("&music=")[1] + ".mp3";
                                musicPlay(musicname);
                            }
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
                } else if (url.contains("myschema://go?a=10")) {
                    JumpIntent.jump(ErrorQuestionActivity.this, PlayChessWebActivity.class, Const.CONS_STR_ACTIVITY_PLAY);
                    return true;
                } else if (url.contains("myschema://go?a=0")) {
                    mLoadingImage.setVisibility(View.GONE);
                    mLoadingBg.setVisibility(View.GONE);
                    mLoadingText.setVisibility(View.GONE);
                    return true;
                } else if (url.contains("myschema://go?a=1")) {
                    finish();
                    return true;
                } else if (url.contains("myschema://go?a=2")) {
                    ToastUtils.show(Const.CONS_STR_USER_PAST_DUE);
                    Intent intent = new Intent(ErrorQuestionActivity.this, ChessLoginTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (url.contains("myschema://go?a=8")) {
                    if (!speakericon) {
                        String musicname = url.split("&music=")[1] + ".wav";
                        musicPlay(musicname);
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
                wvTask.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        puaseWebView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeWebView();
    }

    private void musicPlay(String musicname){
        try {
            AssetManager assetManager = getAssets();
            AssetFileDescriptor fileDescriptor = assetManager.openFd("xswl_local/music/" + musicname);
            midPlayerIsReading();
            player.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getStartOffset());
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

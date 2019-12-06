package com.xswq.consumer.activity.playChessWebView;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.view.View;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.xswq.consumer.activity.login.ChessLoginTypeActivity;
import com.xswq.consumer.base.BaseWebActivity;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.PreferencesUtil;
import com.xswq.consumer.utils.ToastUtils;

/**
 * 我的棋谱
 */
public class MyChessManualWebActivity extends BaseWebActivity {

    private boolean speakericon;

    @Override
    public String initUrlData() {
        Intent intent = getIntent();
        String historyGameId = intent.getStringExtra("historyGameId");
        String skinId = intent.getStringExtra("skinId");
        String effectId = intent.getStringExtra("effectId");
        speakericon = PreferencesUtil.getBoolean(MyChessManualWebActivity.this, "speakericon");
        boolean specialEffects = PreferencesUtil.getBoolean(MyChessManualWebActivity.this, "special_effects");
        if (specialEffects) {
            strUrl = ContactUrl.POST_CHESS_MANUAL_PATH + "uid=" + Const.UID + "&token=" + Const.TOKEN + "&skinId=" + skinId + "&effectId=" + Const.STR0 + "&historyGameId=" + historyGameId;
        } else {
            strUrl = ContactUrl.POST_CHESS_MANUAL_PATH + "uid=" + Const.UID + "&token=" + Const.TOKEN + "&skinId=" + skinId + "&effectId=" + effectId + "&historyGameId=" + historyGameId;
        }
        return strUrl;
    }

    @Override
    public void getWebViewClinck() {
        wvTask.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("myschema://go?a=11")) {
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
                } else if (url.contains("myschema://go?a=1")) {
                    finish();
                    return true;
                } else if (url.contains("myschema://go?a=2")) {
                    ToastUtils.show(Const.CONS_STR_USER_PAST_DUE);
                    Intent intent = new Intent(MyChessManualWebActivity.this, ChessLoginTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
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
                wvTask.setVisibility(View.GONE);
            }
        });
    }

}

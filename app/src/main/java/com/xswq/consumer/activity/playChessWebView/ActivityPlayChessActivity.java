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
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.LogUtil;
import com.xswq.consumer.utils.PreferencesUtil;
import com.xswq.consumer.utils.StatisticsUtil;
import com.xswq.consumer.utils.ToastUtils;

public class ActivityPlayChessActivity extends BaseWebActivity {
    private String TAG = "ActivityPlayChessActivity";

    @Override
    public String initUrlData() {
        StatisticsUtil.getStatistics(ActivityPlayChessActivity.this,Const.STR3);
        String strUrl;
        boolean activityGuide = PreferencesUtil.getBoolean(ActivityPlayChessActivity.this, "activityGuide");
        //1引导 0不引导
        if (!activityGuide) {
            strUrl = ContactUrl.POST_ACTIVITY_PATH + "uid=" + Const.UID + "&token=" + Const.TOKEN + "&skinId=" + Const.SKINID + "&effectId=" + Const.EFFECTID +"&guide=" + Const.STR1;
        } else {
            strUrl = ContactUrl.POST_ACTIVITY_PATH + "uid=" + Const.UID + "&token=" + Const.TOKEN + "&skinId=" + Const.SKINID + "&effectId=" + Const.EFFECTID;
        }
        PreferencesUtil.putBoolean(ActivityPlayChessActivity.this, "activityGuide", true);
        LogUtil.e(TAG, strUrl);
        return strUrl;
    }

    @Override
    public void getWebViewClinck() {
        wvTask.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("myschema://go?a=10")) {
                    JumpIntent.jump(ActivityPlayChessActivity.this, PlayChessWebActivity.class, Const.CONS_STR_ACTIVITY_PLAY);
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
                    Intent intent = new Intent(ActivityPlayChessActivity.this, ChessLoginTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (url.contains("myschema://go?a=6")) {
                    mBlack.setVisibility(View.GONE);
                    return true;
                } else if (url.contains("myschema://go?a=7")) {
                    JumpIntent.jump(ActivityPlayChessActivity.this, PlayChessWebActivity.class, Const.CONS_STR_PLAY);
                    return true;
                } else if (url.contains("myschema://go?a=8")) {
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
                    return true;
                } else if (url.contains("myschema://go?a=9")) {
                    mBlack.setVisibility(View.VISIBLE);
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
}

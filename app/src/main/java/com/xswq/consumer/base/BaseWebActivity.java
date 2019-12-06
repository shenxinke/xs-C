package com.xswq.consumer.base;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.xswq.consumer.R;
import com.xswq.consumer.utils.LogUtil;

import butterknife.BindView;

public abstract class BaseWebActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private String TAG = "BaseWebActivity";
    public MediaPlayer player;
    public String strUrl;
    @BindView(R.id.web_view)
    public WebView wvTask;
    @BindView(R.id.green_back)
    public ImageView mBlack;
    @BindView(R.id.loading_image)
    public ImageView mLoadingImage;
    @BindView(R.id.login_bg)
    public ImageView mLoadingBg;
    @BindView(R.id.login_text)
    public ImageView mLoadingText;
    int[] imgRes = new int[]{R.mipmap.login_1, R.mipmap.login_2, R.mipmap.login_3, R.mipmap.login_4, R.mipmap.login_5, R.mipmap.login_6};

    @Override
    public int bindLayout() {
        return R.layout.activity_play_chess_web_layout;
    }

    @Override
    public void initData() {
        int randomnum = (int) (Math.random() * 5);
        mLoadingImage.setBackgroundResource(imgRes[randomnum]);
        LogUtil.e(TAG, "" + randomnum);
        mBlack.setOnClickListener(this);
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
        strUrl = initUrlData();
        wvTask.loadUrl(strUrl);
        LogUtil.e(TAG, strUrl);
        getWebViewClinck();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.green_back:
                finish();
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

    @Override
    protected void onDestroy() {
        try {
            if (player != null) {
                if (player.isPlaying()) {
                    player.stop();//停止音频的播放
                }
                player.release();//释放资源
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

    public abstract String initUrlData();

    public abstract void getWebViewClinck();

    public void puaseWebView() {
        if (wvTask != null) {
            wvTask.onPause();
        }
    }

    public void resumeWebView() {
        if (wvTask != null) {
            wvTask.onResume();
        }
    }
}

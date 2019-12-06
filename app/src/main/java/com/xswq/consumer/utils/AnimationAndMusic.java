package com.xswq.consumer.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.xswq.consumer.R;
import com.xswq.consumer.activity.multimedia.StoryPlayActivity;

public class AnimationAndMusic {

    private MediaPlayer player;//媒体播放器
    private Context context;
    private AnimationStopInterface animationStopInterface;


    public AnimationAndMusic(Context context, ImageView lottieAnimationView, final String musicUrl, int animationUrl, final int type) {
        try {
            this.context = context;
            if (context instanceof AnimationStopInterface)
                animationStopInterface = (AnimationStopInterface) context;
            midPlayerIsReading(musicUrl);
            showAnimation(lottieAnimationView, animationUrl, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAnimation(final ImageView animationView, int animationUrl, final int type) {
        if (animationView != null) {
            MyAnimationDrawable.animateRawManuallyFromXML(animationUrl,
                    animationView, new Runnable() {

                        @Override
                        public void run() {
                            // TODO onStart
                            // 动画开始时回调
                            animationView.setVisibility(View.VISIBLE);

                        }
                    }, new Runnable() {

                        @Override
                        public void run() {
                            // TODO onComplete
                            // 动画结束时回调
                            clearMediaPlayer(type);
                            animationView.setVisibility(View.GONE);
                        }
                    });
        }

    }

    //播放器是否准备就绪
    private void midPlayerIsReading(String musicUrl) {
        try {
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
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor fileDescriptor = assetManager.openFd("music/" + musicUrl);
            player.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            LogUtil.e("StoryChessPlayActivity",musicUrl);
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
    }

    private void clearMediaPlayer(int type) {
        try {
            if (player != null) {
                if (player.isPlaying()) {
                    player.stop();//停止音频的播放
                }
                player.release();//释放资源
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (animationStopInterface == null) return;
        animationStopInterface.AnimationStop(type);
    }

    public interface AnimationStopInterface {
        /**
         * 动画播放结束回调
         */
        void AnimationStop(int type);
    }
}

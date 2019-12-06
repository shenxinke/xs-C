package com.xswq.consumer.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import org.json.JSONException;

import java.io.IOException;


public class MediahelperUtil {

    private static MediaPlayer mPlayer;
    private static boolean isPause = false;

    public static void playSound(int filePath, Context context) {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
        } else {
            mPlayer.reset();
        }
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mPlayer.reset();
                return false;
            }
        });
        try {
            mPlayer = MediaPlayer.create(context, filePath);
            mPlayer.prepare();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException("读取文件异常：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPlayer.start();
        isPause = false;
        mPlayer.setLooping(true);
    }

    public static void pause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
            isPause = true;
        }
    }

    // 继续
    public static void resume() {
        if (mPlayer != null && isPause) {
            mPlayer.start();
            isPause = false;
        }
    }

    public static void release() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public static boolean isPlaying() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            return true;
        } else {
            return false;
        }
    }

    public static void setSmallVolume() {
        if (mPlayer != null) {
            mPlayer.setVolume(0.5f, 0.5f);
        }
    }

    public static void setLargeVolume() {
        if (mPlayer != null) {
            mPlayer.setVolume(1f, 1f);
        }
    }

}

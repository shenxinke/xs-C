package com.xswq.consumer.jpushmanager;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.xswq.consumer.activity.main.InvitationActivity;
import com.xswq.consumer.activity.main.PlayChessActivity;
import com.xswq.consumer.activity.main.StoryActivity;
import com.xswq.consumer.activity.multimedia.StoryChessPlayActivity;
import com.xswq.consumer.activity.multimedia.GamePlayActivity;
import com.xswq.consumer.activity.multimedia.StoryPlayActivity;
import com.xswq.consumer.activity.multimedia.TestPlayActivity;
import com.xswq.consumer.activity.multimedia.VideoPlayActivity;
import com.xswq.consumer.activity.personalCenter.PersonalCenterActivity;
import com.xswq.consumer.activity.personalCenter.ShoppingMallActivity;
import com.xswq.consumer.activity.playChessWebView.ActivityPlayChessActivity;
import com.xswq.consumer.utils.PreferencesUtil;

public class MyJpushJump {

    private static Intent mIntent;

    public static void getInternt(Context context, String extras) {
        if (TextUtils.isEmpty(extras)) {
            return;
        } else {
            if (extras.indexOf("14") != -1) {
                mIntent = new Intent(context, TestPlayActivity.class);
            } else if (extras.indexOf("13") != -1) {
                mIntent = new Intent(context, StoryChessPlayActivity.class);
            } else if (extras.indexOf("12") != -1) {
                mIntent = new Intent(context, VideoPlayActivity.class);
            } else if (extras.indexOf("11") != -1) {
                mIntent = new Intent(context, GamePlayActivity.class);
            } else if (extras.indexOf("10") != -1) {
                mIntent = new Intent(context, StoryPlayActivity.class);
            } else if (extras.indexOf("9") != -1) {
                mIntent = new Intent(context, InvitationActivity.class);
            } else if (extras.indexOf("8") != -1) {
                mIntent = new Intent(context, PersonalCenterActivity.class);
                mIntent.putExtra("intentType", 8);
            } else if (extras.indexOf("7") != -1) {
                mIntent = new Intent(context, PersonalCenterActivity.class);
                mIntent.putExtra("intentType", 7);
            } else if (extras.indexOf("6") != -1) {
                mIntent = new Intent(context, PersonalCenterActivity.class);
                mIntent.putExtra("intentType", 6);
            } else if (extras.indexOf("5") != -1) {
                mIntent = new Intent(context, ShoppingMallActivity.class);
            } else if (extras.indexOf("4") != -1) {
                mIntent = new Intent(context, PersonalCenterActivity.class);
            } else if (extras.indexOf("3") != -1) {
                mIntent = new Intent(context, ActivityPlayChessActivity.class);
            } else if (extras.indexOf("2") != -1) {
                mIntent = new Intent(context, PlayChessActivity.class);
            } else if (extras.indexOf("1") != -1) {
                mIntent = new Intent(context, StoryActivity.class);
            } else {
                PreferencesUtil.removeKey(context, "extras");
                return;
            }
            PreferencesUtil.removeKey(context, "extras");
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
        }
    }
}

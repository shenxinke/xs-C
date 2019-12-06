package com.xswq.consumer.activity.playChessWebView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import com.xswq.consumer.R;
import com.xswq.consumer.activity.login.ChessLoginTypeActivity;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.ToastUtils;
import com.xswq.consumer.view.MyBarPercentView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Timer;
import java.util.TimerTask;
import okhttp3.Call;

public class ProgressBarActivity extends Activity {
    private MyBarPercentView myProgressBar;
    private int mCurrentProgress = 0;
    private String gameType;
    private String road;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Button button = findViewById(R.id.green_button);
        myProgressBar = findViewById(R.id.my_progress_bar);
        Intent intent = getIntent();
        gameType = intent.getStringExtra("gameType");
        road = intent.getStringExtra("road");
        //主线程中调用：
        timer.schedule(timerTask, 1000, 500);//延时1s
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                timer = null;
                timerTask.cancel();
                timerTask = null;
                finish();
                overridePendingTransition(0, 0);
            }
        });

    }

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            myProgressBar.setPercentage(mCurrentProgress, 10);
            if (mCurrentProgress == 10) {
                getRandomBatle();
            }
            super.handleMessage(msg);
        }
    };

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            mCurrentProgress++;
            message.what = mCurrentProgress;
            handler.sendMessage(message);
        }
    };

    private void getRandomBatle() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_GAME_IN_FOR_BUY_ID_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    try {
                        if (!TextUtils.isEmpty(response)) {
                            JSONObject object = new JSONObject(response);
                            JSONObject error = object.getJSONObject("error");
                            int returnCode = error.getInt("returnCode");
                            if (returnCode == 0) {
                                JumpIntent.jump(ProgressBarActivity.this, PlayChessWebActivity.class, Const.CONS_STR_PLAY);
                                finish();
                            } else if (returnCode == 1002) {
                                if (!TextUtils.isEmpty(road) && !TextUtils.isEmpty(gameType)) {
                                    getCreatAiMatch();
                                }
                            } else if (returnCode == 10048) {
                                ToastUtils.show(error.getString("returnMessage"));
                                Intent intent = new Intent(ProgressBarActivity.this, ChessLoginTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                ProgressBarActivity.this.startActivity(intent);
                            } else {
                                ToastUtils.show(error.getString("returnMessage"));
                            }
                        } else {
                            ToastUtils.show(Const.CONS_STR_ERROR);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getCreatAiMatch() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_AI_MATCH_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("gameType", gameType)
                    .addParams("road", road)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    try {
                        if (!TextUtils.isEmpty(response)) {
                            JSONObject object = new JSONObject(response);
                            JSONObject error = object.getJSONObject("error");
                            int returnCode = error.getInt("returnCode");
                            if (returnCode == 0) {
                                JumpIntent.jump(ProgressBarActivity.this, PlayChessWebActivity.class, Const.CONS_STR_PLAY);
                                finish();
                            } else if (returnCode == 10048) {
                                ToastUtils.show(error.getString("returnMessage"));
                                Intent intent = new Intent(ProgressBarActivity.this, ChessLoginTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                ProgressBarActivity.this.startActivity(intent);
                            } else {
                                ToastUtils.show(error.getString("returnMessage"));
                            }
                        } else {
                            ToastUtils.show(Const.CONS_STR_ERROR);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
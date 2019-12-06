package com.xswq.consumer.activity.guidance;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xswq.consumer.R;
import com.xswq.consumer.activity.login.ChessLoginTypeActivity;
import com.xswq.consumer.activity.playChessWebView.ProgressBarActivity;
import com.xswq.consumer.base.BaseGuideActivity;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.PreferencesUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.Call;

public class GuidePlayChessActivity extends BaseGuideActivity implements View.OnClickListener {
    @BindView(R.id.rating_bar)
    RelativeLayout relativeLayout;
    @BindView(R.id.guide_start_image_text)
    ImageView mGuideStartModeText;
    @BindView(R.id.guide_start_head_image)
    ImageView mGuideStartHeadImage;

    @BindView(R.id.game_mode_bg)
    ImageView mGameModeBg;
    @BindView(R.id.guide_play_chess_mode_text)
    ImageView mGuideModeText;
    @BindView(R.id.guide_head_image)
    ImageView mGuideHeadImage;

    @BindView(R.id.play_chess_firends_image)
    ImageView mFirendsGameImage;
    @BindView(R.id.play_chess_firends_text)
    TextView mFirendsGameText;
    @BindView(R.id.play_chess_up_down_image)
    ImageView mUpDownImage;
    @BindView(R.id.play_chesss_up_down_text)
    TextView mUpDownText;
    @BindView(R.id.play_chess_nine_image)
    ImageView mNineImage;
    @BindView(R.id.play_chess_nine_text)
    TextView mNineText;
    @BindView(R.id.play_chess_nineteen_image)
    ImageView mNineteenImage;
    @BindView(R.id.play_chesss_nineteen_text)
    TextView mNineteenText;
    @BindView(R.id.game_mode)
    TextView mGameMode;
    @BindView(R.id.chessboard)
    TextView mChessBoard;

    @BindView(R.id.play_chess_nineteen_button)
    Button mNineteenButton;
    @BindView(R.id.play_chess_nineteen_text)
    ImageView mGuideNineteenText;
    @BindView(R.id.guide_play_chess_nineteen_head)
    ImageView mGuideNineteenHeadImage;

    @BindView(R.id.play_chess_matching_button)
    Button mMatchingButton;
    @BindView(R.id.play_chess_matching_text)
    ImageView mGuideMatchingText;
    @BindView(R.id.guide_play_chess_matching_head)
    ImageView mGuideMatchingHeadImage;

    @Override
    public int bindLayout() {
        return R.layout.activity_guide_play_chess_layout;
    }

    @Override
    public void initData() {
        mGameModeBg.setOnClickListener(this);
        mGuideModeText.setOnClickListener(this);
        mGuideHeadImage.setOnClickListener(this);

        relativeLayout.setOnClickListener(this);
        mGuideStartModeText.setOnClickListener(this);
        mGuideStartHeadImage.setOnClickListener(this);

        mNineteenButton.setOnClickListener(this);
        mGuideNineteenText.setOnClickListener(this);
        mGuideNineteenHeadImage.setOnClickListener(this);

        mMatchingButton.setOnClickListener(this);
        mGuideMatchingText.setOnClickListener(this);
        mGuideMatchingHeadImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.game_mode_bg:
            case R.id.guide_play_chess_mode_text:
            case R.id.guide_head_image:
                if (ConstUtils.isClickable()) {
                    return;
                }
                mFirendsGameImage.setVisibility(View.INVISIBLE);
                mFirendsGameText.setVisibility(View.INVISIBLE);
                mUpDownImage.setVisibility(View.INVISIBLE);
                mUpDownText.setVisibility(View.INVISIBLE);
                mNineImage.setVisibility(View.INVISIBLE);
                mNineText.setVisibility(View.INVISIBLE);
                mNineteenImage.setVisibility(View.INVISIBLE);
                mNineteenText.setVisibility(View.INVISIBLE);
                mGameMode.setVisibility(View.INVISIBLE);
                mChessBoard.setVisibility(View.INVISIBLE);
                mGameModeBg.setVisibility(View.GONE);
                mGuideModeText.setVisibility(View.GONE);
                mGuideHeadImage.setVisibility(View.GONE);

                relativeLayout.setVisibility(View.VISIBLE);
                mGuideStartModeText.setVisibility(View.VISIBLE);
                mGuideStartHeadImage.setVisibility(View.VISIBLE);
                break;
            case R.id.rating_bar:
            case R.id.guide_start_image_text:
            case R.id.guide_start_head_image:
                if (ConstUtils.isClickable()) {
                    return;
                }
                relativeLayout.setVisibility(View.INVISIBLE);
                mGuideStartModeText.setVisibility(View.GONE);
                mGuideStartHeadImage.setVisibility(View.GONE);

                mNineteenButton.setVisibility(View.VISIBLE);
                mGuideNineteenText.setVisibility(View.VISIBLE);
                mGuideNineteenHeadImage.setVisibility(View.VISIBLE);
                break;

            case R.id.play_chess_nineteen_button:
            case R.id.play_chess_nineteen_text:
            case R.id.guide_play_chess_nineteen_head:
                if (ConstUtils.isClickable()) {
                    return;
                }
                mNineteenButton.setVisibility(View.INVISIBLE);
                mGuideNineteenText.setVisibility(View.GONE);
                mGuideNineteenHeadImage.setVisibility(View.GONE);

                mMatchingButton.setVisibility(View.VISIBLE);
                mGuideMatchingText.setVisibility(View.VISIBLE);
                mGuideMatchingHeadImage.setVisibility(View.VISIBLE);
                break;

            case R.id.play_chess_matching_button:
            case R.id.play_chess_matching_text:
            case R.id.guide_play_chess_matching_head:
                if (ConstUtils.isClickable()) {
                    return;
                }
                mMatchingButton.setVisibility(View.INVISIBLE);
                mGuideMatchingText.setVisibility(View.GONE);
                mGuideMatchingHeadImage.setVisibility(View.GONE);
                getRandomBatle();
                PreferencesUtil.putBoolean(GuidePlayChessActivity.this,"playChessGuide", true);
            default:
                break;
        }
    }

    private void getRandomBatle() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_RANDOM_BATTLE_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("road", "9")
                    .addParams("gameType", "2")
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
                                Intent intent = new Intent(GuidePlayChessActivity.this, ProgressBarActivity.class);
                                intent.putExtra("gameType", "2");
                                intent.putExtra("road", "9");
                                startActivity(intent);
                                finish();
                            } else if (returnCode == 10048) {
                                ToastUtils.show(error.getString("returnMessage"));
                                Intent intent = new Intent(GuidePlayChessActivity.this, ChessLoginTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                GuidePlayChessActivity.this.startActivity(intent);
                            } else {
                                ToastUtils.show(error.getString("returnMessage"));
                                finish();
                            }
                        } else {
                            ToastUtils.show(Const.CONS_STR_ERROR);
                            finish();
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

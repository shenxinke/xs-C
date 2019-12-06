package com.xswq.consumer.activity.guidance;

import android.view.View;
import android.widget.ImageView;

import com.xswq.consumer.R;
import com.xswq.consumer.activity.main.StoryActivity;
import com.xswq.consumer.activity.multimedia.ErrorQuestionActivity;
import com.xswq.consumer.activity.multimedia.StoryPlayActivity;
import com.xswq.consumer.base.BaseGuideActivity;
import com.xswq.consumer.bean.MultimediaBean;
import com.xswq.consumer.bean.SekiguchListBean;
import com.xswq.consumer.bean.SekiguchiBygateNumBean;
import com.xswq.consumer.bean.SelectErrorQuestionBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.PreferencesUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class GuideStoryActivity extends BaseGuideActivity implements View.OnClickListener {
    @BindView(R.id.story_flower_one)
    ImageView mStoryFlowerOne;
    @BindView(R.id.story_one_guide_head)
    ImageView mStoryOnewGuideHead;
    @BindView(R.id.guide_sotry_game_one)
    ImageView mStoryOneGuide;

    @BindView(R.id.story_flower_two)
    ImageView mStoryFlowerTwo;
    @BindView(R.id.story_two_guide_head)
    ImageView mStoryTwoGuideHead;
    @BindView(R.id.guide_two_game_text)
    ImageView mStoryTwoGuide;

    @BindView(R.id.story_windmill)
    ImageView mStoryWindmillGuide;
    @BindView(R.id.story_windmill_head)
    ImageView mStoryWindmillHand;
    @BindView(R.id.guide_windmill_text)
    ImageView mStoryWindmillGuideText;
    private SekiguchListBean.DataBean data;
    private MultimediaBean multimediaBean;
    private int maxGateNum;
    private List<SekiguchListBean.DataBean.GateAllBean.ListBean> list;

    @Override
    public int bindLayout() {
        return R.layout.activity_guide_story_layout;
    }

    @Override
    public void initData() {
        getSekiguchiList();

        boolean oneClassGuide = PreferencesUtil.getBoolean(GuideStoryActivity.this, "oneClassGuide");
        boolean twoClassGuide = PreferencesUtil.getBoolean(GuideStoryActivity.this, "twoClassGuide");
        boolean mistakesGuide = PreferencesUtil.getBoolean(GuideStoryActivity.this, "mistakesGuide");
        if (!oneClassGuide) {
            mStoryFlowerOne.setOnClickListener(this);
            mStoryFlowerOne.setVisibility(View.VISIBLE);
            mStoryOnewGuideHead.setVisibility(View.VISIBLE);
            mStoryOneGuide.setVisibility(View.VISIBLE);
        } else if (!twoClassGuide) {
            mStoryFlowerTwo.setOnClickListener(this);
            mStoryFlowerTwo.setVisibility(View.VISIBLE);
            mStoryTwoGuideHead.setVisibility(View.VISIBLE);
            mStoryTwoGuide.setVisibility(View.VISIBLE);
        } else if (!mistakesGuide) {
            mStoryWindmillGuide.setOnClickListener(this);
            mStoryWindmillGuide.setVisibility(View.VISIBLE);
            mStoryWindmillHand.setVisibility(View.VISIBLE);
            mStoryWindmillGuideText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.story_flower_one:
                if (ConstUtils.isClickable()) {
                    return;
                }
                if (list != null && list.size() > 0) {
                    String gateNum = list.get(0).getGateNum();
                    getSekiguchiBygateNum(gateNum, 0);
                    PreferencesUtil.putBoolean(GuideStoryActivity.this, "oneClassGuide", true);
                }
                break;
            case R.id.story_flower_two:
                if (ConstUtils.isClickable()) {
                    return;
                }
                if (list.size() > 0) {
                    String gateNum = list.get(1).getGateNum();
                    getSekiguchiBygateNum(gateNum, 1);
                    PreferencesUtil.putBoolean(GuideStoryActivity.this, "twoClassGuide", true);
                }
                break;
            case R.id.story_windmill:
                if (ConstUtils.isClickable()) {
                    return;
                }
                int gateNum = data.getMaxSekiguchi().getGateNum();
                if (gateNum <= 0) {
                    JumpIntent.jump(GuideStoryActivity.this, ErrorQuestionActivity.class);
                } else {
                    String errorQuestionType = list.get(gateNum - 1).getErrorQuestionType();
                    JumpIntent.jump(GuideStoryActivity.this, ErrorQuestionActivity.class, errorQuestionType);
                }
                finish();
                break;
            default:
                break;
        }
    }

    private void getSekiguchiList() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_SEKIGUCH_LIST_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("pageNum", Const.STR1)
                    .addParams("pageSize", Const.STR12)
                    .build().execute(new StringCallback() {


                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    SekiguchListBean sekiguchListBean = GsonUtil.gsonToBean(response, SekiguchListBean.class, GuideStoryActivity.this);
                    if (sekiguchListBean != null) {
                        data = sekiguchListBean.getData();
                        SekiguchListBean.DataBean.GateAllBean gateAll = data.getGateAll();
                        multimediaBean = new MultimediaBean();
                        maxGateNum = data.getMaxSekiguchi().getGateNum();
                        if (maxGateNum == 0) {
                            maxGateNum = 1;
                        }
                        multimediaBean.setMaxGateNum(maxGateNum);
                        multimediaBean.setMaxSeat(data.getMaxSekiguchi().getSeat());
                        int total = gateAll.getTotal();
                        if (total > 0) {
                            list = gateAll.getList();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSekiguchiBygateNum(String gateNum, final int indext) {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_SEKIGUCH_BYGATE_NUM_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("gateNum", gateNum)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    SekiguchiBygateNumBean sekiguchiBygateNumBean = GsonUtil.gsonToBean(response, SekiguchiBygateNumBean.class, GuideStoryActivity.this);
                    if (sekiguchiBygateNumBean != null) {
                        SekiguchiBygateNumBean.DataBean data = sekiguchiBygateNumBean.getData();
                        int seat = data.getMaxTime().getSeat();
                        multimediaBean.setSeat(seat);
                        multimediaBean.setGameId(list.get(indext).getGameId());
                        multimediaBean.setStoryId(list.get(indext).getStoryId());
                        multimediaBean.setVideoId(list.get(indext).getVideoId());
                        multimediaBean.setGateNum(list.get(indext).getGateNum());
                        multimediaBean.setQuestionId(list.get(indext).getQuestionId());
                        multimediaBean.setTestQuestionId(list.get(indext).getTestQuestionId());
                        JumpIntent.jump(GuideStoryActivity.this, StoryPlayActivity.class, multimediaBean);
                        finish();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

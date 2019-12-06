package com.xswq.consumer.fragment;


import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.xswq.consumer.R;
import com.xswq.consumer.activity.personalCenter.MyChessManuaActivity;
import com.xswq.consumer.activity.personalCenter.ShoppingMallActivity;
import com.xswq.consumer.adapter.StarItemAdapter;
import com.xswq.consumer.base.BaseFragment;
import com.xswq.consumer.bean.LevelInformationBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.StatisticsUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 个人中心 棋力信息
 */
public class LeveInformationFragment extends BaseFragment {

    private View mView;
    private TextView mCheseLeve;
    private TextView mUserData;
    private TextView mClassNum;
    private TextView mQuestionNum;
    private TextView mAccuracy;
    private TextView mNumOfGames;
    private TextView mWinRate;
    private Button myChessManual;
    private GridView mGridView;
    private List<String> stringList = new ArrayList<>();

    @Override
    protected int setContentView() {
        return R.layout.fragment_level_information_layout;
    }

    @Override
    protected void startLoad() {

    }

    @Override
    protected void initData() {
        if (mView == null) {
            mView = getContentView();
        }
        mCheseLeve = findViewById(R.id.chess_leve);
        mUserData = findViewById(R.id.user_data);
        mClassNum = findViewById(R.id.class_num);
        mQuestionNum = findViewById(R.id.question_num);
        mAccuracy = findViewById(R.id.accuracy);
        mNumOfGames = findViewById(R.id.num_of_games);
        mWinRate = findViewById(R.id.win_rate);
        myChessManual = findViewById(R.id.my_chess_manual);
        mGridView = findViewById(R.id.grid_view);
        getLevelInfo();
        myChessManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpIntent.jump(getActivity(), MyChessManuaActivity.class);
            }
        });
        StatisticsUtil.getStatistics(getActivity(), Const.STR6);
    }

    @Override
    protected void stopLoad() {

    }

    private void getLevelInfo() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_LEVEL_IN_FO_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    LevelInformationBean levelInformationBean = GsonUtil.gsonToBean(response, LevelInformationBean.class, getActivity());
                    if (levelInformationBean != null) {
                        LevelInformationBean.DataBean data = levelInformationBean.getData();
                        int level = data.getLevel();
                        int star = data.getStar();
                        String chessLevel = ConstUtils.getChessLevel(level);
                        ConstUtils.setTextString("棋力水平：" + chessLevel, mCheseLeve);
                        ConstUtils.setTextString("使用天数：" + data.getLoginDay() + "天", mUserData);
                        ConstUtils.setTextString("完成课数：" + data.getMaxGateNum() + "课", mClassNum);
                        ConstUtils.setTextString("做题数： " + data.getUserQuestionRecord() + "道", mQuestionNum);
                        ConstUtils.setTextString("总正确率：" + data.getAccuracy(), mAccuracy);
                        ConstUtils.setTextString("对局数(9/19):" + data.getWay9() + "/" + data.getWay19(), mNumOfGames);
                        ConstUtils.setTextString("胜率(9/19):" + data.getWay9Accuracy() + "/" + data.getWay19Accuracy(), mWinRate);
                        setData(level, star);
                    } else {
                        ConstUtils.setTextString("棋力水平：" + Const.STR0, mCheseLeve);
                        ConstUtils.setTextString("使用天数：" + Const.STR0 + "天", mUserData);
                        ConstUtils.setTextString("完成课数：" + Const.STR0 + "课", mClassNum);
                        ConstUtils.setTextString("做题数： " + Const.STR0 + "道", mQuestionNum);
                        ConstUtils.setTextString("总正确率：" + Const.STR0, mAccuracy);
                        ConstUtils.setTextString("对局数(9/19):" + Const.STR0 + "/" + Const.STR0, mNumOfGames);
                        ConstUtils.setTextString("胜率(9/19):" + Const.STR0 + "/" + Const.STR0, mWinRate);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setData(int level, int star) {
        int starNumber;
        if (level < Const.INTEGER_6) {
            starNumber = 2 - star;
            if (starNumber == 0) {
                stringList.add("1");
                stringList.add("1");
            } else if (starNumber == 1) {
                stringList.add("1");
                stringList.add("0");
            } else {
                stringList.add("0");
                stringList.add("0");
            }
        } else if (level < Const.INTEGER_16) {
            starNumber = 3 - star;
            if (starNumber == 0) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
            } else if (starNumber == 1) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("0");
            } else if (starNumber == 2) {
                stringList.add("1");
                stringList.add("0");
                stringList.add("0");
            } else {
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
            }
        } else if (level < Const.INTEGER_27) {
            starNumber = 5 - star;
            if (starNumber == 0) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
            } else if (starNumber == 1) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("0");
            } else if (starNumber == 2) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("0");
                stringList.add("0");
            } else if (starNumber == 3) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
            } else if (starNumber == 4) {
                stringList.add("1");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
            } else {
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
            }
        } else if (level < Const.INTEGER_29) {
            starNumber = 8 - star;
            if (starNumber == 0) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
            } else if (starNumber == 1) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("0");
            } else if (starNumber == 2) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("0");
                stringList.add("0");
            } else if (starNumber == 3) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
            } else if (starNumber == 4) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
            } else if (starNumber == 5) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
            } else if (starNumber == 6) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
            } else if (starNumber == 7) {
                stringList.add("1");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
            } else {
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
            }

        } else {
            starNumber = 10 - star;
            if (starNumber == 0) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
            } else if (starNumber == 1) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("0");
            } else if (starNumber == 2) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("0");
                stringList.add("0");
            } else if (starNumber == 3) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
            } else if (starNumber == 4) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
            } else if (starNumber == 5) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
            } else if (starNumber == 6) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
            } else if (starNumber == 7) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("1");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
            } else if (starNumber == 8) {
                stringList.add("1");
                stringList.add("1");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
            } else if (starNumber == 9) {
                stringList.add("1");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
            } else {
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
                stringList.add("0");
            }
        }
        StarItemAdapter starItemAdapter = new StarItemAdapter(getActivity(), stringList);
        mGridView.setAdapter(starItemAdapter);
    }
}

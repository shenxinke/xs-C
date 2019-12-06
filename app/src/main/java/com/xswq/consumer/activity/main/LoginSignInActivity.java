package com.xswq.consumer.activity.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;

import com.xswq.consumer.R;
import com.xswq.consumer.adapter.LoginSignInAdapter;
import com.xswq.consumer.adapter.ShoppingMallAdapter;
import com.xswq.consumer.base.BaseBean;
import com.xswq.consumer.bean.LoginSignInBean;
import com.xswq.consumer.bean.SignInBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.SpaceItemDecoration;
import com.xswq.consumer.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 每日奖励
 */
public class LoginSignInActivity extends Activity {

    private RecyclerView mRecyclerView;
    private Button buttonSave;
    private List<SignInBean> strList = new ArrayList<>();
    private int productId;
    private int days;
    private int productNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signln_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mRecyclerView = findViewById(R.id.recycler_view);
        buttonSave = findViewById(R.id.button_save);
        getLoginSignIn();

        //布局管理器对象 参数1.上下文 2.规定一行显示几列的参数常量
        GridLayoutManager gridLayoutManager = new GridLayoutManager(LoginSignInActivity.this, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        //设置布局管理器， 参数gridLayoutManager对象
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10, 3));


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConstUtils.isClickable()) {
                    return;
                }
                getReceiveAward();
            }
        });

    }

    //登录奖励
    private void getLoginSignIn() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_GET_SIGN_IN_LIST_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    LoginSignInBean loginSignInBean = GsonUtil.gsonToBean(response, LoginSignInBean.class, LoginSignInActivity.this);
                    if (loginSignInBean != null) {
                        LoginSignInBean.DataBean.SpecialDaysRewardBean specialDaysReward = loginSignInBean.getData().getSpecialDaysReward();
                        LoginSignInBean.DataBean.TodayRewardBean todayReward = loginSignInBean.getData().getTodayReward();
                        LoginSignInBean.DataBean.TomorrowRewardBean tomorrowReward = loginSignInBean.getData().getTomorrowReward();
                        if (todayReward != null) {
                            days = todayReward.getDays();
                            productId = todayReward.getProductId();
                            String productImg = todayReward.getProductImg();
                            String productName = todayReward.getProductName();
                            productNum = todayReward.getProductNum();

                            SignInBean signInBean = new SignInBean();
                            signInBean.setDays(days);
                            signInBean.setDataName("今日登陆奖励");
                            signInBean.setProductId(productId);
                            signInBean.setProductImg(productImg);
                            signInBean.setProductName(productName);
                            signInBean.setProductNum(productNum);
                            strList.add(signInBean);
                        }

                        if (tomorrowReward != null) {
                            int days = tomorrowReward.getDays();
                            int productId = tomorrowReward.getProductId();
                            String productImg = tomorrowReward.getProductImg();
                            String productName = tomorrowReward.getProductName();
                            int productNum = tomorrowReward.getProductNum();

                            SignInBean signInBean = new SignInBean();
                            signInBean.setDays(days);
                            signInBean.setDataName("明天登陆可获得");
                            signInBean.setProductId(productId);
                            signInBean.setProductImg(productImg);
                            signInBean.setProductName(productName);
                            signInBean.setProductNum(productNum);
                            strList.add(signInBean);
                        }

                        if (specialDaysReward != null) {
                            int days = specialDaysReward.getDays();
                            int productId = specialDaysReward.getProductId();
                            String productImg = specialDaysReward.getProductImg();
                            String productName = specialDaysReward.getProductName();
                            int productNum = specialDaysReward.getProductNum();
                            int difDays = specialDaysReward.getDifDays();

                            SignInBean signInBean = new SignInBean();
                            signInBean.setDays(days);
                            signInBean.setProductId(productId);
                            signInBean.setProductImg(productImg);
                            signInBean.setProductName(productName);
                            signInBean.setProductNum(productNum);
                            signInBean.setDataName("再登陆" + difDays + "天可获得");
                            strList.add(signInBean);
                        }
                        LoginSignInAdapter loginSignInAdapter = new LoginSignInAdapter(LoginSignInActivity.this, strList);
                        mRecyclerView.setAdapter(loginSignInAdapter);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //领取登录奖励
    private void getReceiveAward() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_RECEIVE_AWARD_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("productIds", String.valueOf(productId))
                    .addParams("productNums", String.valueOf(productNum))
                    .addParams("days", String.valueOf(days))
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    BaseBean baseBean = GsonUtil.gsonToBean(response, BaseBean.class, LoginSignInActivity.this);
                    if (baseBean != null) {
                        finish();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

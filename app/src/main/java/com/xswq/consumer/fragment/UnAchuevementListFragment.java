package com.xswq.consumer.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xswq.consumer.R;
import com.xswq.consumer.adapter.AchievementAdapter;
import com.xswq.consumer.base.BaseBean;
import com.xswq.consumer.base.BaseFragment;
import com.xswq.consumer.bean.AchievementBean;
import com.xswq.consumer.bean.UnAchuevementListBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.ImageLoader;
import com.xswq.consumer.utils.RxCode;
import com.xswq.consumer.utils.SpaceItemDecoration;
import com.xswq.consumer.utils.ToastUtils;
import com.xswq.consumer.view.MyPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import gorden.rxbus2.RxBus;
import okhttp3.Call;

/**
 * 未获成就
 */
public class UnAchuevementListFragment extends BaseFragment {

    private List<AchievementBean.DataBean> mData;
    private View mView;
    private RecyclerView mRecyclerView;
    private TextView mNoData;
    private AchievementAdapter achievementAdapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_achuevement_list_layout;
    }

    @Override
    protected void startLoad() {

    }

    @Override
    protected void initData() {
        if (mView == null) {
            mView = getContentView();
        }
        mRecyclerView = findViewById(R.id.recycler_view);
        mNoData = findViewById(R.id.no_data);
        //布局管理器对象 参数1.上下文 2.规定一行显示几列的参数常量
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        //设置布局管理器， 参数gridLayoutManager对象
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(7, 3));
        //0、查询已获成就1、查询未获成就
        getData();
    }

    @Override
    protected void stopLoad() {

    }

    private void getData() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_ACHIEVEMENT_LIST_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("type", Const.STR1)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    AchievementBean achievementBean = GsonUtil.gsonToBean(response, AchievementBean.class, getActivity());
                    if (achievementBean != null) {
                        mData = achievementBean.getData();
                        if (mData.size() > 0) {
                            int state = mData.get(0).getState();
                            if (state == 0) {
                                RxBus.get().send(RxCode.CODE_ACHIEVEMERNT_MESSAGE);
                                Const.IS_SHOW_RED_DOT = false;
                            }
                            mNoData.setVisibility(View.GONE);
                            if (achievementAdapter == null) {
                                achievementAdapter = new AchievementAdapter(getActivity(), mData, 2);
                                mRecyclerView.setAdapter(achievementAdapter);
                            } else {
                                achievementAdapter.upData(mData);
                            }
                            if (achievementAdapter != null) {
                                achievementAdapter.setOnRecyclerViewButtonClickListener(new AchievementAdapter.OnClickListener() {
                                    @Override
                                    public void OnButtonClickListener(int position, int achieveId, String achieveName, String imgUrl) {
                                        String id = String.valueOf(achieveId);
                                        if (!TextUtils.isEmpty(id)) {
                                            getAward(id, achieveName, imgUrl);
                                        }
                                    }

                                    @Override
                                    public void OnItemClickListener(String description) {
                                        showPopupWindow(description);
                                    }
                                });
                            }
                        } else {
                            mNoData.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getAward(final String achieveId, final String achieveName, final String imgUrl) {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_ACHIEVEMENT_AWARD_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("achieveId", achieveId)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    UnAchuevementListBean unAchuevementListBean = GsonUtil.gsonToBean(response, UnAchuevementListBean.class, getActivity());
                    if (unAchuevementListBean != null) {
                        getData();
                        List<UnAchuevementListBean.DataBean> data = unAchuevementListBean.getData();
                        if (data.size() > 0) {
                            String productImg = data.get(0).getProductImg();
                            int productId = data.get(0).getProductId();
                            int productNum = data.get(0).getProductNum();
                            showAchuevePopWindow(imgUrl, productImg, String.valueOf(productId), productNum, null);
                        } else {
                            showAchuevePopWindow(null, null, null, 0, achieveName);
                        }

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //显示提示框
    private void showPopupWindow(String description) {
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.pop_window_layout, null, false);
        MyPopupWindow.myPopupWindow(mView, layout);
        Button btnSava = layout.findViewById(R.id.button_save);
        Button btnCancel = layout.findViewById(R.id.button_cancel);
        TextView texContent = layout.findViewById(R.id.popup_window_content);
        btnSava.setText("确认");
        btnCancel.setText("取消");
        btnCancel.setVisibility(View.GONE);
        texContent.setText(description);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_save:
                    case R.id.button_cancel:
                        MyPopupWindow.dismissPopWindow();
                        break;
                    default:
                        break;
                }
            }
        };
        //设置popwindow布局控件的点击事件
        btnSava.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
    }

    //显示奖励提示框
    private void showAchuevePopWindow(String imageUrl, String productImg, final String productId, final int productNum, String achieveName) {
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.activity_award_layout, null, false);
        final PopupWindow popupWindow = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setAnimationStyle(R.style.myPopupAnimation);
        // 设置好参数之后再show
        popupWindow.showAtLocation(mView, Gravity.CENTER, 0, 0);
        ImageView halfBg = layout.findViewById(R.id.half);
        Button button = layout.findViewById(R.id.button_save);
        TextView congratulationText = layout.findViewById(R.id.congratulation_text);
        TextView congratulation = layout.findViewById(R.id.congratulation);
        ImageView mAchievement = layout.findViewById(R.id.achievement_image);
        ImageView arrowsImage = layout.findViewById(R.id.arrows_image);
        ImageView mAward = layout.findViewById(R.id.award_image);
        TextView productNumText = layout.findViewById(R.id.text_product_num);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_save:
                        getReceiveAward(productId, productNum);
                        popupWindow.dismiss();
                        break;
                    case R.id.half:
                        popupWindow.dismiss();
                        break;
                    default:
                        break;
                }
            }
        };
        if (TextUtils.isEmpty(achieveName)) {
            congratulationText.setVisibility(View.VISIBLE);
            mAchievement.setVisibility(View.VISIBLE);
            arrowsImage.setVisibility(View.VISIBLE);
            mAward.setVisibility(View.VISIBLE);
            ImageLoader imageLoader = new ImageLoader(getActivity());
            imageLoader.loadImage(imageUrl, 0, mAchievement);
            imageLoader.loadImage(productImg, 0, mAward);
            if (productNum > 1) {
                productNumText.setText("x" + productNum);
            }
        } else {
            congratulation.setVisibility(View.VISIBLE);
            congratulation.setText("恭喜获得" + achieveName + "成就");
        }
        button.setOnClickListener(listener);
        halfBg.setOnClickListener(listener);
    }

    //领取成就奖励
    private void getReceiveAward(String productId, int productNum) {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_RECEIVE_AWARD_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("productIds", productId)
                    .addParams("productNums", String.valueOf(productNum))
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    BaseBean baseBean = GsonUtil.gsonToBean(response, BaseBean.class, getActivity());
                    if (baseBean != null) {
                        RxBus.get().send(RxCode.CODE_ACHUEVEMENT_LIST_MESSAGE);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


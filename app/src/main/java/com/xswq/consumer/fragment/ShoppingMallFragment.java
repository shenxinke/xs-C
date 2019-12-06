package com.xswq.consumer.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.xswq.consumer.R;
import com.xswq.consumer.activity.personalCenter.ShoppingMallActivity;
import com.xswq.consumer.adapter.ShoppingMallAdapter;
import com.xswq.consumer.base.BaseBean;
import com.xswq.consumer.base.BaseFragment;
import com.xswq.consumer.bean.ShoppingMallParticularsBean;
import com.xswq.consumer.bean.UserInforBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.ImageLoader;
import com.xswq.consumer.utils.RxCode;
import com.xswq.consumer.utils.SpaceItemDecoration;
import com.xswq.consumer.utils.ToastUtils;
import com.xswq.consumer.view.MyPopupWindow;
import com.xswq.consumer.wxapi.WXPayShoppingActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import gorden.rxbus2.RxBus;
import gorden.rxbus2.ThreadMode;
import okhttp3.Call;

public class ShoppingMallFragment extends BaseFragment implements View.OnClickListener {

    private String productClassify;
    private String classifyName;
    private View mView;
    private RecyclerView mRecyclerView;
    private ImageView mImageLastBg, mImageNextBg;
    private TextView mFlashGiftBag, mShoppingName, mShoppingIntroduce;
    private ImageView mShoppingMallBg, mShoppingLight, mProductImage;
    private ImageView mShoppingChess;
    private Button mShoppingBuy;
    private int pageNum = 1;
    private int pageSize = 6;
    private int dataListPageSize = 7;
    private ImageLoader imageLoader;
    private List<ShoppingMallParticularsBean.DataBean.ListBean> dataList;
    private ShoppingMallAdapter shoppingMallAdapter;
    private TextView mNoData;
    private TextView mChessText;
    private boolean isCheck = false;
    private Handler mHandler;
    private int index = 0;
    private LottieAnimationView mShoppingImage;


    public static ShoppingMallFragment getInstances(String productClassify, String classifyName) {

        ShoppingMallFragment shoppingMallFragment = new ShoppingMallFragment();
        Bundle bundle = new Bundle();
        bundle.putString("productClassify", productClassify);
        bundle.putString("classifyName", classifyName);
        shoppingMallFragment.setArguments(bundle);
        return shoppingMallFragment;
    }

    @Override
    protected int setContentView() {
        Bundle bundle = getArguments();
        productClassify = bundle.getString("productClassify");
        classifyName = bundle.getString("classifyName");
        return R.layout.fragment_shopping_mall_layout;
    }

    @Override
    protected void startLoad() {
        RxBus.get().register(this);
    }

    @Override
    protected void initData() {
        if (mView == null) {
            mView = getContentView();
        }
        mNoData = findViewById(R.id.no_data);
        mRecyclerView = findViewById(R.id.shopping_mall_rv);
        mChessText = findViewById(R.id.shape_shopping_chess_text);
        mImageLastBg = findViewById(R.id.image_last_bg);
        mImageLastBg.setOnClickListener(this);
        mImageNextBg = findViewById(R.id.image_next_bg);
        mImageNextBg.setOnClickListener(this);
        mFlashGiftBag = findViewById(R.id.flash_gift_bag);
        mShoppingImage = findViewById(R.id.shopping_image);
        mProductImage = findViewById(R.id.product_image);
        mShoppingName = findViewById(R.id.shopping_name);
        mShoppingIntroduce = findViewById(R.id.shopping_introduce);
        mShoppingBuy = findViewById(R.id.shopping_button);
        mShoppingBuy.setOnClickListener(this);
        mShoppingMallBg = findViewById(R.id.shape_shopping_mall_bg);
        mShoppingLight = findViewById(R.id.shape_shopping_light);
        mShoppingChess = findViewById(R.id.shape_shopping_chess_image);
        mShoppingChess.setOnClickListener(this);
        //布局管理器对象 参数1.上下文 2.规定一行显示几列的参数常量
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3) {
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
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(7, 3));
        imageLoader = new ImageLoader(getActivity());
    }

    @Override
    protected void stopLoad() {

    }

    private void getData(String belong) {
        try {
            PostFormBuilder post = OkHttpUtils.post();
            post.url(ContactUrl.POST_PRODUCT_LIST_PATH);
            post.addParams("token", Const.TOKEN);
            post.addParams("uid", Const.UID);
            post.addParams("pageNum", String.valueOf(pageNum));
            post.addParams("pageSize", String.valueOf(pageSize));
            post.addParams("productClassify", productClassify);
            if (!TextUtils.isEmpty(belong)) {
                post.addParams("belong", belong);
            }
            post.build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    ShoppingMallParticularsBean shoppingMallParticularsBean = GsonUtil.gsonToBean(response, ShoppingMallParticularsBean.class, mActivity);
                    if (shoppingMallParticularsBean != null) {
                        ShoppingMallParticularsBean.DataBean data = shoppingMallParticularsBean.getData();
                        dataList = data.getList();
                        if (dataList.size() > 0) {
                            if (dataList.size() == 6) {
                                mImageNextBg.setVisibility(View.VISIBLE);
                            } else {
                                mImageNextBg.setVisibility(View.INVISIBLE);
                            }
                            if (pageNum >= 2) {
                                mImageLastBg.setVisibility(View.VISIBLE);
                            } else {
                                mImageLastBg.setVisibility(View.INVISIBLE);
                            }
                            mNoData.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            if (shoppingMallAdapter == null) {
                                shoppingMallAdapter = new ShoppingMallAdapter(mActivity, dataList);
                                mRecyclerView.setAdapter(shoppingMallAdapter);
                            } else {
                                shoppingMallAdapter.updataList(dataList);
                            }
                            shoppingMallAdapter.setShoppingMallOnRecyclerViewItemClickListener(new ShoppingMallAdapter.OnItemClickListener() {
                                @Override
                                public void onClick(int position) {
                                    setView(position);
                                }
                            });
                            setView(0);
                        } else {
                            mNoData.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.INVISIBLE);
                            mShoppingImage.setVisibility(View.GONE);
                            mProductImage.setVisibility(View.GONE);
                            mFlashGiftBag.setVisibility(View.GONE);
                            mShoppingName.setVisibility(View.GONE);
                            mShoppingIntroduce.setVisibility(View.GONE);
                            mShoppingLight.setVisibility(View.GONE);
                            mShoppingMallBg.setVisibility(View.INVISIBLE);
                            mShoppingBuy.setVisibility(View.GONE);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setView(int position) {
        index = position;
        mFlashGiftBag.setVisibility(View.VISIBLE);
        mShoppingImage.setVisibility(View.VISIBLE);
        mProductImage.setVisibility(View.VISIBLE);
        mShoppingName.setVisibility(View.VISIBLE);
        mShoppingIntroduce.setVisibility(View.VISIBLE);
        mShoppingLight.setVisibility(View.VISIBLE);
        mShoppingMallBg.setVisibility(View.VISIBLE);
        mShoppingBuy.setVisibility(View.VISIBLE);

        int belongs = dataList.get(position).getBelong();
        if (belongs > 0) {
            if ("活动券包".equals(classifyName) || "虚拟货币".equals(classifyName)) {
                mShoppingBuy.setText("购买");
            } else {
                mShoppingBuy.setText("使用");
            }
        } else {
            mShoppingBuy.setText("购买");
        }
        mShoppingImage.useHardwareAcceleration(true);
        mShoppingImage.enableMergePathsForKitKatAndAbove(true);
        String showImg = dataList.get(position).getShowImg();
        if (showImg.contains("soldier.json")) {
            mShoppingImage.setVisibility(View.VISIBLE);
            mProductImage.setVisibility(View.GONE);
            mShoppingImage.setAnimation("soldier.json");//在assets目录下的动画json文件名。
            mShoppingImage.setImageAssetsFolder("images/soldier"); //assets目录下的子目录，存放动画所需的图片
            mShoppingImage.playAnimation();//播放动画
        } else if (showImg.contains("shark.json")) {
            mShoppingImage.setVisibility(View.VISIBLE);
            mProductImage.setVisibility(View.GONE);
            mShoppingImage.setAnimation("shark.json");//在assets目录下的动画json文件名。
            mShoppingImage.setImageAssetsFolder("images/shark_images"); //assets目录下的子目录，存放动画所需的图片
            mShoppingImage.playAnimation();//播放动画
        } else {
            mProductImage.setVisibility(View.VISIBLE);
            mShoppingImage.setVisibility(View.GONE);
            imageLoader.loadImage(dataList.get(position).getProductImg(), 0, mProductImage);
        }
        ConstUtils.setTextString(dataList.get(position).getProductName(), mShoppingName);
        ConstUtils.setTextString(dataList.get(position).getProductDescription(), mShoppingIntroduce);
        long endTime = dataList.get(position).getEndTime();
        if (endTime != 0) {
            mFlashGiftBag.setVisibility(View.VISIBLE);
            timerSetView(endTime);
        } else {
            mFlashGiftBag.setVisibility(View.GONE);
        }

        if ("活动券包".equals(classifyName) || "虚拟货币".equals(classifyName)) {
            mShoppingChess.setVisibility(View.GONE);
            mChessText.setVisibility(View.GONE);
        } else {
            mShoppingChess.setVisibility(View.VISIBLE);
            mChessText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shape_shopping_chess_image:
                if (ConstUtils.isClickable()) {
                    return;
                }
                pageNum = 1;
                if (isCheck) {
                    isCheck = false;
                    mShoppingChess.setBackgroundResource(R.mipmap.play_chess_un_select);
                    getData(null);
                    getListData(null);
                } else {
                    isCheck = true;
                    mShoppingChess.setBackgroundResource(R.mipmap.play_chess_select);
                    getData("1");
                    getListData("1");
                }
                break;
            case R.id.shopping_button:
                if (ConstUtils.isClickable()) {
                    return;
                }
                String butStr = mShoppingBuy.getText().toString();
                if ("购买".equals(butStr)) {
                    showPopupWindow(1);
                } else {
                    if (dataList.size() > 0) {
                        String id = String.valueOf(dataList.get(index).getId());
                        getUserProduct(id);
                    }
                }
                break;
            case R.id.image_last_bg:
                if (ConstUtils.isClickable()) {
                    return;
                }
                if (pageNum > 1) {
                    pageNum--;
                    if (isCheck) {
                        getData("1");
                        getListData("1");
                    } else {
                        getData(null);
                        getListData(null);
                    }
                }
                break;
            case R.id.image_next_bg:
                if (ConstUtils.isClickable()) {
                    return;
                }
                pageNum++;
                if (isCheck) {
                    getData("1");
                    getListData("1");
                } else {
                    getData(null);
                    getListData(null);
                }
                break;
            default:
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private void timerSetView(final long endTime) {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    long systemTime = System.currentTimeMillis();
                    long nowTime = (endTime - systemTime) / 1000;
                    String dateToString = ConstUtils.formatSeconds(nowTime);
                    mFlashGiftBag.setText("限时礼包:\n" + "剩余" + dateToString);
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        mHandler.sendEmptyMessage(1);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }).start();
    }

    //显示提示框 type1、确认购买2、水滴不足
    private void showPopupWindow(final int type) {
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.pop_window_layout, null, false);
        MyPopupWindow.myPopupWindow(mView, layout);
        Button btnSava = layout.findViewById(R.id.button_save);
        Button btnCancel = layout.findViewById(R.id.button_cancel);
        TextView texContent = layout.findViewById(R.id.popup_window_content);
        if (type == 1) {
            texContent.setText("确认购买");
            btnSava.setText("确认");
        } else if (type == 2) {
            texContent.setText("购买成功");
            btnSava.setText("确认");
            btnCancel.setVisibility(View.GONE);
        } else if (type == 3) {
            texContent.setText("使用成功");
            btnSava.setText("确认");
            btnCancel.setVisibility(View.GONE);
        } else {
            texContent.setText("水滴不足，请充值");
            btnSava.setText("去充值");
        }
        btnCancel.setText("取消");
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_save:
                        if (type == 1) {
                            if (dataList.size() > 0) {
                                String id = String.valueOf(dataList.get(index).getId());
                                int isRmb = dataList.get(index).getIsRmb();//1、人民币2、水滴
                                int productValue = dataList.get(index).getProductValue();
                                if (isRmb == 1) {
                                    int discountValue = dataList.get(index).getDiscountValue();
                                    String productName = dataList.get(index).getProductName();
                                    Intent intent = new Intent(getActivity(), WXPayShoppingActivity.class);
                                    if (discountValue == 0) {
                                        intent.putExtra("number", productValue);
                                    } else {
                                        intent.putExtra("number", discountValue);
                                    }
                                    intent.putExtra("name", productName);
                                    intent.putExtra("id", id);
                                    startActivity(intent);
                                } else {
                                    getBuyShopping(id, productValue);
                                }
                            }
                        } else if (type == 4) {
                            ShoppingMallActivity shoppingMallActivity = (ShoppingMallActivity) getActivity();
                            shoppingMallActivity.setTabIndext();
                        }
                        MyPopupWindow.dismissPopWindow();
                        break;
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

    //水滴购买商品
    private void getBuyShopping(final String productId, int productValue) {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_PRODUCT_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("productId", productId)
                    .addParams("productNum", "1")
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
                                getUserInfor();
                                getData(null);
                                getListData(null);
                                showPopupWindow(2);
                            } else if (returnCode == 1007) {
                                showPopupWindow(4);
                            } else {
                                ToastUtils.show(error.getString("returnMessage"));
                            }
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

    //获取用户信息
    private void getUserInfor() {
        try {
            PostFormBuilder post = OkHttpUtils.post();
            post.url(ContactUrl.POST_USER_IN_PATH);
            post.addParams("token", Const.TOKEN);
            post.addParams("uid", Const.UID);
            post.build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);

                }

                @Override
                public void onResponse(String response, int id) {
                    UserInforBean userInforBean = GsonUtil.gsonToBean(response, UserInforBean.class, getActivity());
                    if (userInforBean != null) {
                        UserInforBean.DataBean.UserDetailBean userDetail = userInforBean.getData().getUserDetail();
                        String gold = userDetail.getGold();
                        ShoppingMallActivity shoppingMallActivity = (ShoppingMallActivity) getActivity();
                        if (shoppingMallActivity != null) {
                            shoppingMallActivity.upDateMoney(ConstUtils.getStringNoEmpty(gold));
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //使用商品
    private void getUserProduct(String productId) {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_USER_PRODUCT_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("productId", productId)
                    .addParams("productNum", "1")
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);

                }

                @Override
                public void onResponse(String response, int id) {
                    BaseBean baseBean = GsonUtil.gsonToBean(response, BaseBean.class, getActivity());
                    if (baseBean != null) {
                        showPopupWindow(3);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @gorden.rxbus2.Subscribe(code = RxCode.CODE_WX_PAY_SUCCEED_MESSAGE, threadMode = ThreadMode.MAIN)
    public void upData() {
        getData(null);
        getListData(null);
        getUserInfor();
    }

    @Override
    public void onStart() {
        super.onStart();
        getData(null);
        getListData(null);
        getUserInfor();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }

    private void getListData(String belong) {
        try {
            PostFormBuilder post = OkHttpUtils.post();
            post.url(ContactUrl.POST_PRODUCT_LIST_PATH);
            post.addParams("token", Const.TOKEN);
            post.addParams("uid", Const.UID);
            post.addParams("pageNum", String.valueOf(pageNum));
            post.addParams("pageSize", String.valueOf(dataListPageSize));
            post.addParams("productClassify", productClassify);
            if (!TextUtils.isEmpty(belong)) {
                post.addParams("belong", belong);
            }
            post.build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    ShoppingMallParticularsBean shoppingMallParticularsBean = GsonUtil.gsonToBean(response, ShoppingMallParticularsBean.class, mActivity);
                    if (shoppingMallParticularsBean != null) {
                        ShoppingMallParticularsBean.DataBean data = shoppingMallParticularsBean.getData();
                        dataList = data.getList();
                        if (dataList.size() > 0) {
                            if (dataList.size() > 6) {
                                mImageNextBg.setVisibility(View.VISIBLE);
                            } else {
                                mImageNextBg.setVisibility(View.INVISIBLE);
                            }
                            if (pageNum >= 2) {
                                mImageLastBg.setVisibility(View.VISIBLE);
                            } else {
                                mImageLastBg.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

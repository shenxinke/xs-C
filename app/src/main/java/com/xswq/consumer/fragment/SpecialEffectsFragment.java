package com.xswq.consumer.fragment;

import android.support.v4.widget.PopupWindowCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.xswq.consumer.R;
import com.xswq.consumer.activity.personalCenter.ShoppingMallActivity;
import com.xswq.consumer.base.BaseBean;
import com.xswq.consumer.base.BaseFragment;
import com.xswq.consumer.bean.SpecialEffectsBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.ImageLoader;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.StatisticsUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.xswq.consumer.view.MyPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;

/**
 * 棋盘特效页面
 */
public class SpecialEffectsFragment extends BaseFragment implements View.OnClickListener {
    private View mView;
    private TextView mCheckerboardTitle;
    private TextView chessSpeciaTitle;
    private ImageView mCheckerBoard, mCheckerBoard2, mCheckerBoardLock, mCheckerBoard3, mCheckerBoardLock2;
    private ImageView chessSpeciaBoard, chessSpeciaBoard2, chessSpeciaLock, chessSpeciaBoard3, chessSpeciaLock2;
    private ImageLoader imageLoader;
    private PopupWindow mPopupWindow;
    private List<SpecialEffectsBean.DataBean.SkinListBean> skinList;
    private List<SpecialEffectsBean.DataBean.EffectListBean> effectList;
    private SpecialEffectsBean achievementBean;

    @Override
    protected int setContentView() {
        return R.layout.fragment_specoal_effects_layout;
    }

    @Override
    protected void startLoad() {

    }

    @Override
    protected void initData() {
        if (mView == null) {
            mView = getContentView();
        }
        imageLoader = new ImageLoader(getActivity());
        mCheckerboardTitle = findViewById(R.id.checkerboard_title);
        mCheckerBoard = findViewById(R.id.checker_board);
        mCheckerBoard2 = findViewById(R.id.checker_board2);
        mCheckerBoard2.setOnClickListener(this);
        mCheckerBoardLock = findViewById(R.id.checkerboard_background_lock);
        mCheckerBoardLock.setOnClickListener(this);
        mCheckerBoard3 = findViewById(R.id.checker_board3);
        mCheckerBoard3.setOnClickListener(this);
        mCheckerBoardLock2 = findViewById(R.id.checkerboard_background_lock2);
        mCheckerBoardLock2.setOnClickListener(this);

        chessSpeciaTitle = findViewById(R.id.chess_specia_title);
        chessSpeciaBoard = findViewById(R.id.chess_specia_board);
        chessSpeciaBoard2 = findViewById(R.id.chess_specia_board2);
        chessSpeciaBoard2.setOnClickListener(this);
        chessSpeciaLock = findViewById(R.id.chess_specia_lock);
        chessSpeciaLock.setOnClickListener(this);
        chessSpeciaBoard3 = findViewById(R.id.chess_specia_board3);
        chessSpeciaBoard3.setOnClickListener(this);
        chessSpeciaLock2 = findViewById(R.id.chess_specia_lock2);
        chessSpeciaLock2.setOnClickListener(this);

        getDate();
        StatisticsUtil.getStatistics(getActivity(), Const.STR8);
    }

    @Override
    protected void stopLoad() {

    }


    private void getDate() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_SKIN_AND_EFFECT_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    achievementBean = GsonUtil.gsonToBean(response, SpecialEffectsBean.class, getActivity());
                    if (achievementBean != null) {
                        initView(achievementBean);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checker_board2:
                if (skinList != null && skinList.size() > 0) {
                    if (skinList.get(0).getUsed() > 0) {
                        getPopupWindow(mCheckerBoardLock, 1, 1);
                    } else {
                        getPopupWindow(mCheckerBoardLock, 1, 0);
                    }
                }
                break;
            case R.id.checkerboard_background_lock:
                if (skinList != null && skinList.size() > 0) {
                    if (skinList.get(0).getUsed() > 0) {
                        getPopupWindow(mCheckerBoardLock, 1, 1);
                    } else {
                        getPopupWindow(mCheckerBoardLock, 1, 0);
                    }
                }
                break;
            case R.id.checker_board3:
                if (skinList != null && skinList.size() > 1) {
                    if (skinList.get(0).getUsed() > 0) {
                        getPopupWindow(mCheckerBoardLock2, 1, 2);
                    } else {
                        getPopupWindow(mCheckerBoardLock2, 1, 1);
                    }
                }
                break;
            case R.id.checkerboard_background_lock2:
                if (skinList != null && skinList.size() > 1) {
                    if (skinList.get(0).getUsed() > 0) {
                        getPopupWindow(mCheckerBoardLock2, 1, 2);
                    } else {
                        getPopupWindow(mCheckerBoardLock2, 1, 1);
                    }
                }
                break;
            case R.id.chess_specia_board2:
                if (effectList != null && effectList.size() > 0) {
                    if (effectList.get(0).getUsed() > 0) {
                        getPopupWindow(chessSpeciaLock, 2, 1);
                    } else {
                        getPopupWindow(chessSpeciaLock, 2, 0);
                    }
                }
                break;
            case R.id.chess_specia_lock:
                if (effectList != null && effectList.size() > 0) {
                    if (effectList.get(0).getUsed() > 0) {
                        getPopupWindow(chessSpeciaLock, 2, 1);
                    } else {
                        getPopupWindow(chessSpeciaLock, 2, 0);
                    }
                }
                break;
            case R.id.chess_specia_board3:
                if (effectList != null && effectList.size() > 1) {
                    if (effectList.get(0).getUsed() > 0) {
                        getPopupWindow(chessSpeciaLock2, 2, 2);
                    } else {
                        getPopupWindow(chessSpeciaLock2, 2, 1);
                    }
                }
                break;
            case R.id.chess_specia_lock2:
                if (effectList != null && effectList.size() > 1) {
                    if (effectList.get(0).getUsed() > 0) {
                        getPopupWindow(chessSpeciaLock2, 2, 2);
                    } else {
                        getPopupWindow(chessSpeciaLock2, 2, 1);
                    }
                }
                break;
            default:
                break;
        }
    }


    private void getPopupWindow(View view, int type, int index) {
        if (null != mPopupWindow && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            return;
        } else {
            initPopuptWindow(type, index);
            int width = view.getWidth();
            int height = view.getHeight();
            int offsetX = width + width * 1 / 3;
            int offsetY = -(height + height * 2 / 7);
            PopupWindowCompat.showAsDropDown(mPopupWindow, view, offsetX, offsetY, Gravity.END);
        }
    }

    private void initPopuptWindow(final int type, final int index) {//type1、棋盘棋子皮肤2、行棋特效 index1、list下标
        View pop_view = View.inflate(getActivity(), R.layout.popup_window_special_effects_layout, null);
        int windowWidth = mCheckerBoardLock.getHeight() * 2;
        int windowHight = mCheckerBoardLock.getHeight() * 5 / 3;
        TextView moneyText = pop_view.findViewById(R.id.money_text);
        ImageView moneyImage = pop_view.findViewById(R.id.money_image);
        ImageView urlImage = pop_view.findViewById(R.id.url_image);
        LottieAnimationView mLottieAnimation = pop_view.findViewById(R.id.lottie_animation_show);
        Button unLock = pop_view.findViewById(R.id.unlock);
        Button useButton = pop_view.findViewById(R.id.use_button);
        if (type == 1) {
            if (skinList.size() > 0) {
                mLottieAnimation.setVisibility(View.GONE);
                urlImage.setVisibility(View.VISIBLE);
                imageLoader.loadImage(skinList.get(index).getShowImg(), 0, urlImage);
                if (skinList.get(index).getBelong() > 0) {
                    moneyText.setVisibility(View.GONE);
                    moneyImage.setVisibility(View.GONE);
                    unLock.setVisibility(View.GONE);
                    useButton.setVisibility(View.VISIBLE);
                } else {
                    moneyText.setVisibility(View.VISIBLE);
                    moneyImage.setVisibility(View.VISIBLE);
                    moneyText.setText(ConstUtils.getStringNoEmpty(skinList.get(index).getProductValue()));
                    unLock.setVisibility(View.VISIBLE);
                    useButton.setVisibility(View.GONE);
                }
            }
        } else {
            if (effectList.size() > 0) {
                mLottieAnimation.setVisibility(View.VISIBLE);
                urlImage.setVisibility(View.GONE);
                mLottieAnimation.useHardwareAcceleration(true);
                mLottieAnimation.enableMergePathsForKitKatAndAbove(true);
                String showImg = effectList.get(index).getShowImg();
                if (showImg.contains("soldier.json")) {
                    mLottieAnimation.setAnimation("soldier.json");//在assets目录下的动画json文件名。
                    mLottieAnimation.setImageAssetsFolder("images/soldier"); //assets目录下的子目录，存放动画所需的图片
                } else {
                    mLottieAnimation.setAnimation("shark.json");//在assets目录下的动画json文件名。
                    mLottieAnimation.setImageAssetsFolder("images/shark_images"); //assets目录下的子目录，存放动画所需的图片
                }
                mLottieAnimation.playAnimation();//播放动画

                if (effectList.get(index).getBelong() > 0) {
                    moneyText.setVisibility(View.GONE);
                    moneyImage.setVisibility(View.GONE);
                    unLock.setVisibility(View.GONE);
                    useButton.setVisibility(View.VISIBLE);
                } else {
                    moneyText.setVisibility(View.VISIBLE);
                    moneyImage.setVisibility(View.VISIBLE);
                    unLock.setVisibility(View.VISIBLE);
                    useButton.setVisibility(View.GONE);
                    moneyText.setText(ConstUtils.getStringNoEmpty(effectList.get(index).getProductValue()));
                }
            }
        }
        mPopupWindow = new PopupWindow(pop_view, windowWidth, windowHight);
        mPopupWindow.setFocusable(true);//设置pw中的控件能够获取焦点
        mPopupWindow.setOutsideTouchable(true); //设置可以通过点击mPopupWindow外部关闭mPopupWindow
        mPopupWindow.update();//刷新mPopupWindow
        unLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (achievementBean != null) {
                    SpecialEffectsBean.DataBean data = achievementBean.getData();
                    int id = 0;
                    if (type == 1) {
                        if (skinList.size() > 0) {
                            List<SpecialEffectsBean.DataBean.SkinListBean> skinList = data.getSkinList();
                            id = skinList.get(index).getId();
                        }
                    } else {
                        if (effectList.size() > 0) {
                            List<SpecialEffectsBean.DataBean.EffectListBean> effectList = data.getEffectList();
                            id = effectList.get(index).getId();
                        }
                    }
                    String strId = String.valueOf(id);
                    showPopupWindow(1, strId);
                }
                mPopupWindow.dismiss();
            }
        });
        useButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (achievementBean != null) {
                    SpecialEffectsBean.DataBean data = achievementBean.getData();
                    int id = 0;
                    if (type == 1) {
                        if (skinList.size() > 0) {
                            List<SpecialEffectsBean.DataBean.SkinListBean> skinList = data.getSkinList();
                            id = skinList.get(index).getId();
                        }
                    } else {
                        if (effectList.size() > 0) {
                            List<SpecialEffectsBean.DataBean.EffectListBean> effectList = data.getEffectList();
                            id = effectList.get(index).getId();
                        }
                    }
                    String strId = String.valueOf(id);
                    getUserProduct(strId);
                }
                mPopupWindow.dismiss();
            }
        });

    }

    private void initView(SpecialEffectsBean achievementBean) {
        effectList = achievementBean.getData().getEffectList();
        skinList = achievementBean.getData().getSkinList();
        mCheckerBoard2.setVisibility(View.INVISIBLE);
        mCheckerBoardLock.setVisibility(View.INVISIBLE);
        mCheckerBoard3.setVisibility(View.INVISIBLE);
        mCheckerBoardLock2.setVisibility(View.INVISIBLE);

        chessSpeciaBoard2.setVisibility(View.INVISIBLE);
        chessSpeciaLock.setVisibility(View.INVISIBLE);
        chessSpeciaBoard3.setVisibility(View.INVISIBLE);
        chessSpeciaLock2.setVisibility(View.INVISIBLE);

        if (skinList.size() != 0 && skinList.size() == 1) {
            if (skinList.get(0).getUsed() > 0) {
                ConstUtils.setTextString(skinList.get(0).getProductName(), mCheckerboardTitle);
                imageLoader.loadImage(skinList.get(0).getProductImg(), R.mipmap.default_xswq, mCheckerBoard);
            } else {
                mCheckerBoard2.setVisibility(View.VISIBLE);
                imageLoader.loadImage(skinList.get(0).getProductImg(), R.mipmap.default_xswq, mCheckerBoard2);
                if (skinList.get(0).getBelong() > 0) {
                    mCheckerBoardLock.setVisibility(View.INVISIBLE);
                } else {
                    mCheckerBoardLock.setVisibility(View.VISIBLE);
                }
            }
        } else if (skinList.size() != 0 && skinList.size() == 2) {
            mCheckerBoard2.setVisibility(View.VISIBLE);
            if (skinList.get(0).getUsed() > 0) {
                ConstUtils.setTextString(skinList.get(0).getProductName(), mCheckerboardTitle);
                imageLoader.loadImage(skinList.get(0).getProductImg(), R.mipmap.default_xswq, mCheckerBoard);
                imageLoader.loadImage(skinList.get(1).getProductImg(), R.mipmap.default_xswq, mCheckerBoard2);
                if (skinList.get(1).getBelong() > 0) {
                    mCheckerBoardLock.setVisibility(View.INVISIBLE);
                } else {
                    mCheckerBoardLock.setVisibility(View.VISIBLE);
                }
            } else {
                mCheckerBoard3.setVisibility(View.VISIBLE);
                imageLoader.loadImage(skinList.get(0).getProductImg(), R.mipmap.default_xswq, mCheckerBoard2);
                if (skinList.get(0).getBelong() > 0) {
                    mCheckerBoardLock.setVisibility(View.INVISIBLE);
                } else {
                    mCheckerBoardLock.setVisibility(View.VISIBLE);
                }
                imageLoader.loadImage(skinList.get(1).getProductImg(), R.mipmap.default_xswq, mCheckerBoard3);
                if (skinList.get(1).getBelong() > 0) {
                    mCheckerBoardLock2.setVisibility(View.INVISIBLE);
                } else {
                    mCheckerBoardLock2.setVisibility(View.VISIBLE);
                }
            }
        } else if (skinList.size() != 0 && skinList.size() == 3) {
            mCheckerBoard2.setVisibility(View.VISIBLE);
            mCheckerBoard3.setVisibility(View.VISIBLE);
            if (skinList.get(0).getUsed() > 0) {
                ConstUtils.setTextString(skinList.get(0).getProductName(), mCheckerboardTitle);
                imageLoader.loadImage(skinList.get(0).getProductImg(), R.mipmap.default_xswq, mCheckerBoard);
                imageLoader.loadImage(skinList.get(1).getProductImg(), R.mipmap.default_xswq, mCheckerBoard2);
                if (skinList.get(1).getBelong() > 0) {
                    mCheckerBoardLock.setVisibility(View.INVISIBLE);
                } else {
                    mCheckerBoardLock.setVisibility(View.VISIBLE);
                }
                imageLoader.loadImage(skinList.get(2).getProductImg(), R.mipmap.default_xswq, mCheckerBoard3);
                if (skinList.get(2).getBelong() > 0) {
                    mCheckerBoardLock2.setVisibility(View.INVISIBLE);
                } else {
                    mCheckerBoardLock2.setVisibility(View.VISIBLE);
                }
            } else {
                imageLoader.loadImage(skinList.get(0).getProductImg(), R.mipmap.default_xswq, mCheckerBoard2);
                if (skinList.get(0).getBelong() > 0) {
                    mCheckerBoardLock.setVisibility(View.INVISIBLE);
                } else {
                    mCheckerBoardLock.setVisibility(View.VISIBLE);
                }
                imageLoader.loadImage(skinList.get(1).getProductImg(), R.mipmap.default_xswq, mCheckerBoard3);
                if (skinList.get(1).getBelong() > 0) {
                    mCheckerBoardLock2.setVisibility(View.INVISIBLE);
                } else {
                    mCheckerBoardLock2.setVisibility(View.VISIBLE);
                }
            }
        }

        if (effectList.size() != 0 && effectList.size() == 1) {
            if (effectList.get(0).getUsed() > 0) {
                ConstUtils.setTextString(effectList.get(0).getProductName(), chessSpeciaTitle);
                imageLoader.loadImage(effectList.get(0).getProductImg(), R.mipmap.default_xswq, chessSpeciaBoard);
            } else {
                chessSpeciaBoard2.setVisibility(View.VISIBLE);
                imageLoader.loadImage(effectList.get(0).getProductImg(), R.mipmap.default_xswq, chessSpeciaBoard2);
                if (effectList.get(0).getBelong() > 0) {
                    chessSpeciaLock.setVisibility(View.INVISIBLE);
                } else {
                    chessSpeciaLock.setVisibility(View.VISIBLE);
                }
            }
        } else if (effectList.size() != 0 && effectList.size() == 2) {
            chessSpeciaBoard2.setVisibility(View.VISIBLE);
            if (effectList.get(0).getUsed() > 0) {
                ConstUtils.setTextString(effectList.get(0).getProductName(), chessSpeciaTitle);
                imageLoader.loadImage(effectList.get(0).getProductImg(), R.mipmap.default_xswq, chessSpeciaBoard);
                imageLoader.loadImage(effectList.get(1).getProductImg(), R.mipmap.default_xswq, chessSpeciaBoard2);
                if (effectList.get(1).getBelong() > 0) {
                    chessSpeciaLock.setVisibility(View.INVISIBLE);
                } else {
                    chessSpeciaLock.setVisibility(View.VISIBLE);
                }
            } else {
                chessSpeciaBoard3.setVisibility(View.VISIBLE);
                imageLoader.loadImage(effectList.get(0).getProductImg(), R.mipmap.default_xswq, chessSpeciaBoard2);
                if (effectList.get(0).getBelong() > 0) {
                    chessSpeciaLock.setVisibility(View.INVISIBLE);
                } else {
                    chessSpeciaLock.setVisibility(View.VISIBLE);
                }
                imageLoader.loadImage(effectList.get(1).getProductImg(), R.mipmap.default_xswq, chessSpeciaBoard3);
                if (effectList.get(1).getBelong() > 0) {
                    chessSpeciaLock2.setVisibility(View.INVISIBLE);
                } else {
                    chessSpeciaLock2.setVisibility(View.VISIBLE);
                }
            }
        } else if (effectList.size() != 0 && effectList.size() == 3) {
            chessSpeciaBoard2.setVisibility(View.VISIBLE);
            chessSpeciaBoard3.setVisibility(View.VISIBLE);
            if (effectList.get(0).getUsed() > 0) {
                ConstUtils.setTextString(effectList.get(0).getProductName(), chessSpeciaTitle);
                imageLoader.loadImage(effectList.get(0).getProductImg(), R.mipmap.default_xswq, chessSpeciaBoard);
                imageLoader.loadImage(effectList.get(1).getProductImg(), R.mipmap.default_xswq, chessSpeciaBoard2);
                if (effectList.get(1).getBelong() > 0) {
                    chessSpeciaLock.setVisibility(View.INVISIBLE);
                } else {
                    chessSpeciaLock.setVisibility(View.VISIBLE);
                }
                imageLoader.loadImage(effectList.get(2).getProductImg(), R.mipmap.default_xswq, chessSpeciaBoard3);
                if (effectList.get(2).getBelong() > 0) {
                    chessSpeciaLock2.setVisibility(View.INVISIBLE);
                } else {
                    chessSpeciaLock2.setVisibility(View.VISIBLE);
                }
            } else {
                imageLoader.loadImage(effectList.get(0).getProductImg(), R.mipmap.default_xswq, chessSpeciaBoard2);
                if (effectList.get(0).getBelong() > 0) {
                    chessSpeciaLock.setVisibility(View.INVISIBLE);
                } else {
                    chessSpeciaLock.setVisibility(View.VISIBLE);
                }
                imageLoader.loadImage(effectList.get(1).getProductImg(), R.mipmap.default_xswq, chessSpeciaBoard3);
                if (effectList.get(1).getBelong() > 0) {
                    chessSpeciaLock2.setVisibility(View.INVISIBLE);
                } else {
                    chessSpeciaLock2.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    //水滴购买商品
    private void getBuyShopping(final String productId) {
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
                                showPopupWindow(3, null);
                                getDate();
                            } else if (returnCode == 1007) {
                                showPopupWindow(2, null);
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
                        showPopupWindow(4, null);
                        getDate();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //显示提示框 type1、确认购买2、水滴不足3、购买成功4、使用成功 strId商品id
    private void showPopupWindow(final int type, final String strId) {
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.pop_window_layout, null, false);
        MyPopupWindow.myPopupWindow(mView, layout);
        Button btnSava = layout.findViewById(R.id.button_save);
        Button btnCancel = layout.findViewById(R.id.button_cancel);
        TextView texContent = layout.findViewById(R.id.popup_window_content);
        if (type == 1) {
            texContent.setText("确认购买");
            btnSava.setText("确认");
        } else if (type == 2) {
            texContent.setText("水滴不足，请充值");
            btnSava.setText("去充值");
        } else if (type == 3) {
            texContent.setText("购买成功");
            btnSava.setText("确认");
            btnCancel.setVisibility(View.GONE);
        } else {
            texContent.setText("使用成功");
            btnSava.setText("确认");
            btnCancel.setVisibility(View.GONE);
        }
        btnCancel.setText("取消");
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_save:
                        if (type == 1) {
                            if (!TextUtils.isEmpty(strId)) {
                                getBuyShopping(strId);
                            }
                        } else if (type == 2) {
                            JumpIntent.jump(getActivity(), ShoppingMallActivity.class, true);
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
}

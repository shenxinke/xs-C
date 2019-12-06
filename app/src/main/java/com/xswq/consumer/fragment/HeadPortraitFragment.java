package com.xswq.consumer.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xswq.consumer.R;
import com.xswq.consumer.activity.personalCenter.ShoppingMallActivity;
import com.xswq.consumer.adapter.HeadPortraitAdapter;
import com.xswq.consumer.base.BaseBean;
import com.xswq.consumer.base.BaseFragment;
import com.xswq.consumer.bean.HeadPortraitBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.SpaceItemDecoration;
import com.xswq.consumer.utils.ToastUtils;
import com.xswq.consumer.view.MyPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 更换头像页面
 */
public class HeadPortraitFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    private View mView;
    private TextView mNoData;
    private String buyID;
    private ImageView mLastBg;
    private ImageView mNextBg;
    private ViewPager viewPager;
    private List<View> recyclerViews = new ArrayList<>();
    private PagerAdapter pagerAdapter;
    private int mCurrentPosition;
    private int itemPostion;
    /**
     * 数据源
     */
    private List<List<HeadPortraitBean.DataBean>> lists;

    /**
     * 适配器集合
     */
    private List<HeadPortraitAdapter> adaPters = new ArrayList<>();

    @Override
    protected int setContentView() {
        return R.layout.fragment_head_protrait_layout;
    }

    @Override
    protected void startLoad() {

    }

    @Override
    protected void initData() {
        if (mView == null) {
            mView = getContentView();
        }

        mLastBg = findViewById(R.id.image_last_bg);
        mNextBg = findViewById(R.id.image_next_bg);
        viewPager = findViewById(R.id.view_pager);
        pagerAdapter = new PagerAdapter() {

            @Override
            public int getCount() {
                return recyclerViews.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                super.destroyItem(container, position, object);
                container.removeView(recyclerViews.get(position));
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(recyclerViews.get(position));

                return recyclerViews.get(position);
            }
        };
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(this);
        mNoData = findViewById(R.id.no_data);
        getData();
        mNextBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(++mCurrentPosition);
            }
        });
        mLastBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(--mCurrentPosition);
            }
        });
    }

    @Override
    protected void stopLoad() {

    }

    private void getData() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_HEAD_IMG_BRODER_PATH)
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
                    HeadPortraitBean achievementBean = GsonUtil.gsonToBean(response, HeadPortraitBean.class, getActivity());
                    if (achievementBean == null) {
                        return;
                    }
                    List<HeadPortraitBean.DataBean> data = achievementBean.getData();

                    if (data.size() > 0) {
                        mNoData.setVisibility(View.GONE);
                        lists = splitList(data, 12);
                        for (int i = 0; i < lists.size(); i++) {
                            List<HeadPortraitBean.DataBean> list = lists.get(i);
                            LayoutInflater from = LayoutInflater.from(getContext());
                            View inflate = from.inflate(R.layout.head_portrait_recycle_view, null);
                            RecyclerView recyclerView = inflate.findViewById(R.id.recycler_view);
                            //布局管理器对象 参数1.上下文 2.规定一行显示几列的参数常量
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
                            //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
                            gridLayoutManager.setOrientation(GridLayout.VERTICAL);
                            recyclerView.setLayoutManager(gridLayoutManager);
                            recyclerView.addItemDecoration(new SpaceItemDecoration(0,8));
                            recyclerView.setHasFixedSize(true);
                            HeadPortraitAdapter headPortraitAdapter = new HeadPortraitAdapter(getContext(), list);
                            //将适配器集中管理
                            adaPters.add(headPortraitAdapter);
                            recyclerView.setAdapter(headPortraitAdapter);
                            recyclerViews.add(inflate);
                            headPortraitAdapter.setItemLockClickListener(new HeadPortraitAdapter.OnItemLockIdClickListener() {
                                @Override
                                public void onClick(String ID, int position) {
                                    buyID = ID;
                                    itemPostion = position;
                                    showPopupWindow(2);
                                }
                            });
                            headPortraitAdapter.setItemCheckClickListener(new HeadPortraitAdapter.OnItemCheckClickListener() {
                                @Override
                                public void onClick(String id) {
                                    getUserProduct(id);
                                }
                            });
                        }
                        if (data.size() > 1) mNextBg.setVisibility(View.VISIBLE);

                        pagerAdapter.notifyDataSetChanged();
                    } else {
                        mNoData.setVisibility(View.VISIBLE);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分
     *
     * @param list
     * @param len
     * @return
     */
    public static List<List<HeadPortraitBean.DataBean>> splitList(List<HeadPortraitBean.DataBean> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }

        List<List<HeadPortraitBean.DataBean>> result = new ArrayList<>();


        int size = list.size();
        int count = (size + len - 1) / len;


        for (int i = 0; i < count; i++) {
            List<HeadPortraitBean.DataBean> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }


    //显示提示框 type1、头像修改成功2、头像未解锁3、水滴不足4、购买成功
    private void showPopupWindow(final int type) {
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.pop_window_layout, null, false);
        MyPopupWindow.myPopupWindow(mView, layout);
        Button btnSava = layout.findViewById(R.id.button_save);
        Button btnCancel = layout.findViewById(R.id.button_cancel);
        TextView texContent = layout.findViewById(R.id.popup_window_content);
        if (type == 1) {
            texContent.setText("头像修改成功");
            btnCancel.setVisibility(View.GONE);
            btnSava.setText("确认");
        } else if (type == 2) {
            texContent.setText("该头像未解锁，确认解锁?");
            btnSava.setText("确认");
        } else if (type == 3) {
            texContent.setText("水滴不足，请充值");
            btnSava.setText("去充值");
        } else if (type == 4) {
            texContent.setText("购买成功");
            btnCancel.setVisibility(View.GONE);
            btnSava.setText("确认");
        }
        btnCancel.setText("取消");
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_save:
                        if (type == 2) {
                            if (!TextUtils.isEmpty(buyID)) {
                                getBuyShopping();
                            }
                        } else if (type == 3) {
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

    //水滴购买商品
    private void getBuyShopping() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_PRODUCT_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("productId", buyID)
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
                                if (lists.get(mCurrentPosition) != null && lists.get(mCurrentPosition).size() >= itemPostion + 1) {
                                    lists.get(mCurrentPosition).get(itemPostion).setBelong(1);
                                    if (adaPters.get(mCurrentPosition) == null) return;
                                    adaPters.get(mCurrentPosition).notifyDataSetChanged();
                                }
                                showPopupWindow(4);
                            } else if (returnCode == 1007) {
                                showPopupWindow(3);
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
                        showPopupWindow(1);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        // 把当前显示的position传递出去
        mCurrentPosition = i;
        int currentItem = viewPager.getChildCount();
        mLastBg.setVisibility(View.VISIBLE);
        mNextBg.setVisibility(View.VISIBLE);
        if (i == 0) mLastBg.setVisibility(View.GONE);
        if (mCurrentPosition == currentItem - 1) mNextBg.setVisibility(View.GONE);
        if (currentItem == 1) mNextBg.setVisibility(View.GONE);

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}

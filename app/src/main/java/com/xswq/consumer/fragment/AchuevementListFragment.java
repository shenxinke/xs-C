package com.xswq.consumer.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.xswq.consumer.R;
import com.xswq.consumer.adapter.AchievementAdapter;
import com.xswq.consumer.base.BaseFragment;
import com.xswq.consumer.bean.AchievementBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.LogUtil;
import com.xswq.consumer.utils.RxCode;
import com.xswq.consumer.utils.SpaceItemDecoration;
import com.xswq.consumer.utils.StatisticsUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.xswq.consumer.view.MyPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import gorden.rxbus2.RxBus;
import gorden.rxbus2.ThreadMode;
import okhttp3.Call;

/**
 * 已获成就
 */
public class AchuevementListFragment extends BaseFragment {

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
        RxBus.get().register(this);
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
        ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecyclerView.getItemAnimator().setChangeDuration(0);

        //0、查询已获成就1、查询未获成就
        getData();
        StatisticsUtil.getStatistics(getActivity(), Const.STR7);
    }

    @Override
    protected void stopLoad() {

    }

    private void getData() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_ACHIEVEMENT_LIST_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("type", Const.STR0)
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
                            mNoData.setVisibility(View.GONE);
                            achievementAdapter = new AchievementAdapter(getActivity(), mData, 1);
                            mRecyclerView.setAdapter(achievementAdapter);
                        } else {
                            mNoData.setVisibility(View.VISIBLE);
                        }
                        if (achievementAdapter != null) {
                            achievementAdapter.setOnRecyclerViewButtonClickListener(new AchievementAdapter.OnClickListener() {
                                @Override
                                public void OnButtonClickListener(int position, int id, String achieveName, String ImgUrl) {

                                }

                                @Override
                                public void OnItemClickListener(String description) {
                                    showPopupWindow(description);
                                }
                            });
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @gorden.rxbus2.Subscribe(code = RxCode.CODE_ACHUEVEMENT_LIST_MESSAGE, threadMode = ThreadMode.MAIN)
    public void upData() {
        //0、查询已获成就1、查询未获成就
        getData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
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
}

package com.xswq.consumer.activity.personalCenter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.xswq.consumer.R;
import com.xswq.consumer.activity.playChessWebView.MyChessManualWebActivity;
import com.xswq.consumer.base.BaseAppCompatActivity;
import com.xswq.consumer.bean.BasicInformaitonBean;
import com.xswq.consumer.bean.MyChessManualBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 我的棋谱页面
 */

public class MyChessManuaActivity extends BaseAppCompatActivity {
    @BindView(R.id.green_back)
    ImageView mBlack;
    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.not_data)
    TextView notData;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private String skinId;
    private String effectId;
    private int pageNum = 1;
    private int pageSize = 10;
    private List<MyChessManualBean.DataBean.ListBean> listData = new ArrayList<>();
    private MyChessManualAdapter myChessManualAdapter;

    @Override
    public int bindLayout() {
        return R.layout.activity_chess_manual_layout;
    }

    @Override
    public void initData() {
        getData();
        mBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initListener();
        getUserInfo();

    }


    private void getData() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_GET_DETAIL_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("userId", Const.UID)
                    .addParams("pageNum", String.valueOf(pageNum))
                    .addParams("pageSize", String.valueOf(pageSize))
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    MyChessManualBean myChessManualBean = GsonUtil.gsonToBean(response, MyChessManualBean.class, MyChessManuaActivity.this);
                    if (myChessManualBean != null) {
                        List<MyChessManualBean.DataBean.ListBean> list = myChessManualBean.getData().getList();
                        listData.addAll(list);
                        if (listData.size() > 0) {
                            notData.setVisibility(View.GONE);
                            if (myChessManualAdapter == null) {
                                myChessManualAdapter = new MyChessManualAdapter();
                                mListView.setAdapter(myChessManualAdapter);
                            } else {
                                myChessManualAdapter.upListDate();
                            }
                        } else {
                            notData.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class MyChessManualAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return null != listData ? listData.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ComViewHolder comHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MyChessManuaActivity.this).inflate(R.layout.my_chess_manual_item_layout, null);
                comHolder = new ComViewHolder();
                comHolder.typeTitle = convertView.findViewById(R.id.type_title);
                comHolder.typeTime = convertView.findViewById(R.id.type_time);
                comHolder.blackChessman = convertView.findViewById(R.id.black_chessman);
                comHolder.whiteChessman = convertView.findViewById(R.id.white_chessman);
                comHolder.resultTitle = convertView.findViewById(R.id.result_title);
                comHolder.operationTitle = convertView.findViewById(R.id.operation_title);
                convertView.setTag(comHolder);
            } else {
                comHolder = (ComViewHolder) convertView.getTag();
            }
            String whiteUserName = listData.get(position).getWhiteUserName();
            String blackUserName = listData.get(position).getBlackUserName();
            int gameType = listData.get(position).getGameType();
            int playResult = listData.get(position).getPlayResult();
            if (gameType == 1 || gameType == 3) {
                comHolder.typeTitle.setText(ConstUtils.getStringNoEmpty("升降级对局"));
            } else if (gameType == 2) {
                comHolder.typeTitle.setText(ConstUtils.getStringNoEmpty("友谊对局"));
            } else if (gameType == 5) {
                comHolder.typeTitle.setText(ConstUtils.getStringNoEmpty("活动对局"));
            }
            long endTime = listData.get(position).getEndTime();
            String strTime = ConstUtils.getDateToString(endTime, "yyyy-MM-dd");
            comHolder.typeTime.setText(ConstUtils.getStringNoEmpty(strTime));
            comHolder.blackChessman.setText(ConstUtils.getStringNoEmpty(blackUserName));
            comHolder.whiteChessman.setText(ConstUtils.getStringNoEmpty(whiteUserName));

            if (playResult == 1) {
                comHolder.resultTitle.setText(ConstUtils.getStringNoEmpty("黑胜"));
            } else if (playResult == 2) {
                comHolder.resultTitle.setText(ConstUtils.getStringNoEmpty("白胜"));
            } else if (playResult == 3) {
                comHolder.resultTitle.setText(ConstUtils.getStringNoEmpty("平局"));
            } else if (playResult == 4) {
                comHolder.resultTitle.setText(ConstUtils.getStringNoEmpty("无效对局"));
            }
            comHolder.operationTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyChessManuaActivity.this, MyChessManualWebActivity.class);
                    int chessId = listData.get(position).getChessId();
                    intent.putExtra("historyGameId", String.valueOf(chessId));
                    intent.putExtra("skinId", skinId);
                    intent.putExtra("effectId", effectId);
                    startActivity(intent);
                }
            });
            return convertView;
        }

        class ComViewHolder {
            TextView typeTitle;
            TextView typeTime;
            TextView blackChessman;
            TextView whiteChessman;
            TextView resultTitle;
            TextView operationTitle;
        }

        public void upListDate() {
            notifyDataSetChanged();
        }
    }

    /**
     * 上拉加载下拉刷新
     */
    private void initListener() {
        smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNum++;
                getData();
                smartRefreshLayout.finishLoadmore(1000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                if (listData != null) {
                    listData.clear();
                }
                getData();
                smartRefreshLayout.finishRefresh(500);
            }
        });
    }

    private void getUserInfo() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_USER_IN_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    BasicInformaitonBean sekiguchiBygateNumBean = GsonUtil.gsonToBean(response, BasicInformaitonBean.class, MyChessManuaActivity.this);
                    if (sekiguchiBygateNumBean != null) {
                        BasicInformaitonBean.DataBean data = sekiguchiBygateNumBean.getData();
                        BasicInformaitonBean.DataBean.UserDetailBean userDetail = data.getUserDetail();
                        skinId = String.valueOf(userDetail.getSkinId());
                        effectId = String.valueOf(userDetail.getPlayEffectId());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

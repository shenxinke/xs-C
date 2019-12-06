package com.xswq.consumer.activity.personalCenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.xswq.consumer.R;
import com.xswq.consumer.bean.ForRecordBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 兑换记录
 */
public class ForRecordActivity extends Activity {

    private ImageView shutDown;
    private ListView listView;
    private List<ForRecordBean.DataBean.ListBean> list = new ArrayList<>();
    private int pageNum = 1;
    private int pageSize = 10;

    private ForRecordAdapter forRecordAdapter;
    private SmartRefreshLayout smartRefreshLayout;
    private TextView notData;
    private ImageView xiaoTian;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_record_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        listView = findViewById(R.id.list_view);
        notData = findViewById(R.id.not_data);
        xiaoTian = findViewById(R.id.xiaotian);
        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        shutDown = findViewById(R.id.shut_down);
        shutDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getData();

        initListener();
    }

    private void getData() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_GET_ORDER_LIST_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("pageNum", String.valueOf(pageNum))
                    .addParams("pageSize", String.valueOf(pageSize))
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    ForRecordBean forRecordBean = GsonUtil.gsonToBean(response, ForRecordBean.class, ForRecordActivity.this);
                    if (forRecordBean != null) {
                        List<ForRecordBean.DataBean.ListBean> listData = forRecordBean.getData().getList();
                        list.addAll(listData);
                        if (list.size() > 0) {
                            if (forRecordAdapter == null) {
                                forRecordAdapter = new ForRecordAdapter();
                                listView.setAdapter(forRecordAdapter);
                            } else {
                                forRecordAdapter.upListDate();
                            }
                            notData.setVisibility(View.GONE);
                            xiaoTian.setVisibility(View.GONE);
                        } else {
                            notData.setVisibility(View.VISIBLE);
                            xiaoTian.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ForRecordAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return null != list ? list.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ComViewHolder comHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(ForRecordActivity.this).inflate(R.layout.for_record_item_layout, null);
                comHolder = new ComViewHolder();
                comHolder.recordText = convertView.findViewById(R.id.for_record_text);
                convertView.setTag(comHolder);
            } else {
                comHolder = (ComViewHolder) convertView.getTag();
            }
            long createTime = list.get(position).getCreateTime();
            String productName = list.get(position).getProductName();
            int purchaseNum = list.get(position).getPurchaseNum();

            String dateString = ConstUtils.getDateToString(createTime, "yyyy-MM-dd HH:mm:ss");
            comHolder.recordText.setText(ConstUtils.getStringNoEmpty(dateString + "  购买了" + purchaseNum + "个" + productName));

            return convertView;
        }

        class ComViewHolder {
            TextView recordText;
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
                if (list != null) {
                    list.clear();
                }
                getData();
                smartRefreshLayout.finishRefresh(500);
            }
        });
    }
}

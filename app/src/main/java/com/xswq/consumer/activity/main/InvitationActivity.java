package com.xswq.consumer.activity.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xswq.consumer.ConsumerApplication;
import com.xswq.consumer.R;
import com.xswq.consumer.activity.login.ChessLoginTypeActivity;
import com.xswq.consumer.base.BaseAppCompatActivity;
import com.xswq.consumer.base.BaseListAdapter;
import com.xswq.consumer.base.BaseListViewHolder;
import com.xswq.consumer.bean.InvitationBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.StatisticsUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.xswq.consumer.view.MyPopupWindow;
import com.xswq.consumer.wxapi.WXShareAcitivity;
import com.xswq.consumer.wxapi.WxShareUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 分享页面
 */
public class InvitationActivity extends BaseAppCompatActivity implements View.OnClickListener {
    @BindView(R.id.main_invitation_button_bg)
    Button mInvitationButton;
    @BindView(R.id.green_back)
    ImageView mGreenBack;
    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.invitation_number)
    TextView mInvitationNumber;

    @Override
    public int bindLayout() {
        return R.layout.activity_invitation_layout;
    }

    @Override
    public void initData() {
        mInvitationButton.setOnClickListener(InvitationActivity.this);
        mGreenBack.setOnClickListener(InvitationActivity.this);
        getShareInfo();

        StatisticsUtil.getStatistics(InvitationActivity.this, Const.STR9);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_invitation_button_bg:
                JumpIntent.jump(InvitationActivity.this, WXShareAcitivity.class);
                break;
            case R.id.green_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void getShareInfo() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_GET_SHARE_INFO_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    InvitationBean invitationBean = GsonUtil.gsonToBean(response, InvitationBean.class, InvitationActivity.this);
                    if (invitationBean != null) {
                        InvitationBean.DataBean data = invitationBean.getData();
                        List<InvitationBean.DataBean.ShareListBean> shareList = data.getShareList();
                        String totalCount = data.getTotalCount();
                        mInvitationNumber.setText(totalCount);
                        if (shareList.size() > 0) {
                            initShareInfo(shareList);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initShareInfo(List<InvitationBean.DataBean.ShareListBean> shareList) {
        BaseListAdapter baseListAdapter = new BaseListAdapter<InvitationBean.DataBean.ShareListBean>(ConsumerApplication.getInstance(), shareList, R.layout.share_info_item_layout) {

            @Override
            public void convert(BaseListViewHolder holder, int position, final InvitationBean.DataBean.ShareListBean item) {
                holder.setText(R.id.share_info_text, "邀请 " + item.getCount() + " 人，领取 " + item.getGold() + " 水滴");
                if (item.getState() == 1) {
                    holder.setText(R.id.button_get, "领取");
                    holder.settTextBgById(R.id.button_get, R.drawable.shape_invitation_select);
                    holder.setTextColor(R.id.button_get, R.color.color_be631f);
                    holder.setTextListener(R.id.button_get, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ConstUtils.isClickable()) {
                                return;
                            }
                            getShareState(item.getId(),item.getGold());
                        }
                    });
                } else if (item.getState() == 2) {
                    holder.setText(R.id.button_get, "已领取");
                    holder.setTextColor(R.id.button_get, R.color.wai_content_color);
                    holder.settTextBgById(R.id.button_get, R.drawable.shape_invitation_select);
                } else if (item.getState() == 0) {
                    holder.setText(R.id.button_get, "领取");
                    holder.setTextColor(R.id.button_get, R.color.wai_content_color);
                    holder.settTextBgById(R.id.button_get, R.drawable.shape_invitation_no_select);
                }
            }
        };
        mListView.setAdapter(baseListAdapter);
    }

    private void getShareState(String id,String gold) {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_GET_SHARE_STATE_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("id", id)
                    .addParams("gold", gold)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    showPopupWindow("领取失败");
                }

                @Override
                public void onResponse(String response, int id) {
                    try {
                        if (!TextUtils.isEmpty(response)) {
                            JSONObject object = new JSONObject(response);
                            JSONObject error = object.getJSONObject("error");
                            int returnCode = error.getInt("returnCode");
                            if (returnCode == 0) {
                                showPopupWindow("领取成功");
                                getShareInfo();
                            } else if (returnCode == 10048) {
                                ToastUtils.show(error.getString("returnMessage"));
                                Intent intent = new Intent(InvitationActivity.this, ChessLoginTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                showPopupWindow(error.getString("returnMessage"));
                            }
                        } else {
                            showPopupWindow("领取失败");
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

    private void showPopupWindow(String str) {
        View layout = LayoutInflater.from(InvitationActivity.this).inflate(R.layout.pop_window_layout, null, false);
        View view = getWindow().getDecorView();
        MyPopupWindow.myPopupWindow(view, layout);
        Button btnSava = layout.findViewById(R.id.button_save);
        Button btnCancel = layout.findViewById(R.id.button_cancel);
        TextView texContent = layout.findViewById(R.id.popup_window_content);
        texContent.setText(str);
        btnSava.setText("确认");
        btnCancel.setVisibility(View.GONE);
        btnCancel.setText("取消");
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_save:
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

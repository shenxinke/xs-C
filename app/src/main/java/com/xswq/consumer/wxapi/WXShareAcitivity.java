package com.xswq.consumer.wxapi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.xswq.consumer.R;
import com.xswq.consumer.activity.main.MainActivity;
import com.xswq.consumer.base.BaseGuideActivity;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.utils.PreferencesUtil;

import butterknife.BindView;

public class WXShareAcitivity extends BaseGuideActivity implements View.OnClickListener {
    @BindView(R.id.wx_friend)
    ImageView wxFriend;
    @BindView(R.id.wx_circle_of_friends)
    ImageView wxCircleFriends;
    @BindView(R.id.wx_share_cancel)
    Button wxShareCancel;
    private String shareUrl = "https://consumer.xswq361.cn/consumerXSWQ/share.html?uid="+ Const.UID;
    private int maxGateNum;

    @Override
    public int bindLayout() {
        return R.layout.activity_wx_share_layout;
    }

    @Override
    public void initData() {
        maxGateNum = PreferencesUtil.getInt(WXShareAcitivity.this, "maxGateNum");
        if (maxGateNum <= 0) {
            maxGateNum = 1;
        }
        wxFriend.setOnClickListener(this);
        wxCircleFriends.setOnClickListener(this);
        wxShareCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bitmap bitmap;
        switch (v.getId()) {
            case R.id.wx_friend:
                bitmap = BitmapFactory.decodeResource(WXShareAcitivity.this.getResources(), R.mipmap.xswl);
                WxShareUtils.WxUrlShare(WXShareAcitivity.this, shareUrl, "先手秘境有点意思~", "我已经学到第" + maxGateNum + "课了，你敢跟我较量一下吗?", bitmap, WxShareUtils.WECHAT_FRIEND);
                break;
            case R.id.wx_circle_of_friends:
                bitmap = BitmapFactory.decodeResource(WXShareAcitivity.this.getResources(), R.mipmap.xswl);
                WxShareUtils.WxUrlShare(WXShareAcitivity.this, shareUrl, "先手秘境有点意思~", "我已经学到第 " + maxGateNum + "课了，你敢跟我较量一下吗?", bitmap, WxShareUtils.WECHAT_MOMENT);
                break;
            case R.id.wx_share_cancel:
                finish();
                break;
            default:
                break;
        }
    }
}

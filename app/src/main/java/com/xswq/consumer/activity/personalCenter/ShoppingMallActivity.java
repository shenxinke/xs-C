package com.xswq.consumer.activity.personalCenter;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xswq.consumer.R;
import com.xswq.consumer.adapter.TabLayoutFragmentAdapter;
import com.xswq.consumer.base.BaseAppCompatActivity;
import com.xswq.consumer.bean.ShoppingMallBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.fragment.ShoppingMallFragment;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.StatisticsUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 商城页面
 */
public class ShoppingMallActivity extends BaseAppCompatActivity implements View.OnClickListener {
    @BindView(R.id.shopping_tablayout)
    TabLayout mShoppingTab;
    @BindView(R.id.shopping_viewpage)
    ViewPager mViewPage;
    @BindView(R.id.green_back)
    ImageView mGreenBack;
    @BindView(R.id.main_money_add)
    ImageView mMoneyAdd;
    @BindView(R.id.for_record)
    ImageView mForRecord;
    @BindView(R.id.text_money)
    TextView mMoney;
    private List<Fragment> listFragment;
    private List<ShoppingMallBean.DataBean> data = new ArrayList<>();
    private boolean isConfirm;

    @Override
    public int bindLayout() {
        return R.layout.activity_shopping_mall_layout;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        int number = intent.getIntExtra("number", 0);
        isConfirm = intent.getBooleanExtra("isConfirm", false);
        String money = String.valueOf(number);
        upDateMoney(money);
        mGreenBack.setOnClickListener(this);
        mForRecord.setOnClickListener(this);
        mMoneyAdd.setOnClickListener(this);
        getData();
        StatisticsUtil.getStatistics(ShoppingMallActivity.this, Const.STR5);
    }

    private void getData() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_PRODUCT_CLASSIFY_LIST_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    ShoppingMallBean shoppingMallBean = GsonUtil.gsonToBean(response, ShoppingMallBean.class, ShoppingMallActivity.this);
                    if (shoppingMallBean != null) {
                        data = shoppingMallBean.getData();
                        if (data.size() > 0) {
                            getTabViewPage(data);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getTabViewPage(List<ShoppingMallBean.DataBean> dataList) {
        String[] switch_titles = new String[dataList.size()];
        //初始化fragment
        listFragment = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            switch_titles[i] = dataList.get(i).getClassifyName();
            listFragment.add(ShoppingMallFragment.getInstances(dataList.get(i).getProductClassify(), dataList.get(i).getClassifyName()));
        }
        TabLayoutFragmentAdapter tabLayoutFragmentAdapter = new TabLayoutFragmentAdapter(getSupportFragmentManager(), switch_titles, listFragment);
        mViewPage.setAdapter(tabLayoutFragmentAdapter);
        mShoppingTab.setupWithViewPager(mViewPage);
        if (isConfirm) {
            setTabIndext();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.green_back:
                if (ConstUtils.isClickable()) {
                    return;
                }
                finish();
                break;
            case R.id.main_money_add:
                if (ConstUtils.isClickable()) {
                    return;
                }
                setTabIndext();
                break;
            case R.id.for_record:
                if (ConstUtils.isClickable()) {
                    return;
                }
                JumpIntent.jump(ShoppingMallActivity.this, ForRecordActivity.class);
                break;
            default:
                break;

        }
    }

    public void setTabIndext() {
        try {
            if (mShoppingTab != null && data.size() > 0) {
                mShoppingTab.getTabAt(data.size() - 1).select();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void upDateMoney(String money) {
        mMoney.setText(money);
    }

}

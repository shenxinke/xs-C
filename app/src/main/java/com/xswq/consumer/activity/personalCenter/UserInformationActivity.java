package com.xswq.consumer.activity.personalCenter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.xswq.consumer.R;
import com.xswq.consumer.activity.guidance.GuidePersonalCenterActivity;
import com.xswq.consumer.base.BaseBean;
import com.xswq.consumer.bean.BasicInformaitonBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.PreferencesUtil;
import com.xswq.consumer.utils.ToastUtils;
import com.xswq.consumer.utils.customData.CustomDatePicker;
import com.xswq.consumer.view.MyPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zyyoona7.wheel.WheelView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import okhttp3.Call;

/**
 * 完善资料
 */
public class UserInformationActivity extends Activity implements View.OnClickListener {

    private TextView mStartBirthday;
    private TextView mTextPhone;
    private EditText mTextName;
    private TextView mTextAddress;
    private TextView mTextLeveNum;
    private ImageView mManCheck;
    private ImageView mWoManCheck;
    private Button mButtonSave;
    private Button mButtoncancel;
    private String now;
    private CustomDatePicker startCustomDatePicker;
    private int sex;
    private PopupWindow window;
    private int level;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_data_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mTextName = findViewById(R.id.edit_text_name);
        mTextAddress = findViewById(R.id.edit_text_address);
        mTextPhone = findViewById(R.id.text_phone_num);
        mTextAddress.setOnClickListener(this);
        mManCheck = findViewById(R.id.man_check);
        mManCheck.setOnClickListener(this);
        mWoManCheck = findViewById(R.id.woman_check);
        mWoManCheck.setOnClickListener(this);
        mButtonSave = findViewById(R.id.button_save);
        mButtonSave.setOnClickListener(this);
        mButtoncancel = findViewById(R.id.button_cancel);
        mButtoncancel.setOnClickListener(this);
        mStartBirthday = findViewById(R.id.text_start_birthday);
        mStartBirthday.setOnClickListener(this);
        mTextLeveNum = findViewById(R.id.text_leve_num);
        mTextLeveNum.setOnClickListener(this);
        Drawable drawable = getResources().getDrawable(R.mipmap.date);
        drawable.setBounds(0, 0, 50, 50);
        mStartBirthday.setCompoundDrawables(null, null, drawable, null);
        initDatePicker();
        getUserInfo();
        initPopupWindow();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_cancel:
                if (ConstUtils.isClickable()) {
                    return;
                }
                PreferencesUtil.putBoolean(UserInformationActivity.this, "personalInformation", true);
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, R.anim.out_to_down);
                break;
            case R.id.text_start_birthday:
                if (ConstUtils.isClickable()) {
                    return;
                }
                startCustomDatePicker.show(now.split(" ")[0]);
                break;
            case R.id.edit_text_address:
                if (ConstUtils.isClickable()) {
                    return;
                }
                getAddress();
                break;
            case R.id.button_save:
                if (ConstUtils.isClickable()) {
                    return;
                }
                String userName = mTextName.getText().toString().trim();
                double nameLength = ConstUtils.getLength(userName);
                if (TextUtils.isEmpty(userName) || userName.length() == 0) {
                    showPopupWindow(v, 1);
                } else if (TextUtils.isEmpty(mStartBirthday.getText().toString().trim())) {
                    showPopupWindow(v, 2);
                } else if (TextUtils.isEmpty(mTextAddress.getText().toString().trim())) {
                    showPopupWindow(v, 3);
                } else if (nameLength > 5) {
                    showPopupWindow(v, 4);
                } else {
                    getUpdateUserInfo();
                }
                break;
            case R.id.man_check:
                if (ConstUtils.isClickable()) {
                    return;
                }
                sex = 1;
                mManCheck.setBackgroundResource(R.mipmap.settings_item_check);
                mWoManCheck.setBackgroundResource(R.mipmap.settings_item_uncheck);
                break;
            case R.id.woman_check:
                if (ConstUtils.isClickable()) {
                    return;
                }
                sex = 2;
                mManCheck.setBackgroundResource(R.mipmap.settings_item_uncheck);
                mWoManCheck.setBackgroundResource(R.mipmap.settings_item_check);
                break;
            case R.id.text_leve_num:
                window.showAtLocation(mTextLeveNum, Gravity.CENTER, 0, 0);
                break;
            default:
                break;
        }
    }

    private void getAddress() {
        CityPicker cityPicker = new CityPicker
                .Builder(UserInformationActivity.this)
                .textSize(14).title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("北京市").city("北京市").district("昌平区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false).build();
        cityPicker.show();
        //监听事件，获取结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为展示区赋值
                mTextAddress.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
    }

    //日历控件调用
    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        now = sdf.format(new Date());
        startCustomDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                mStartBirthday.setText(time.split(" ")[0]);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        startCustomDatePicker.showSpecificTime(false); // 不显示时和分
        startCustomDatePicker.setIsLoop(false); // 不允许循环滚动
    }

    private void getUpdateUserInfo() {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_UPDATE_USER_IN_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("id", Const.UID)
                    .addParams("level", String.valueOf(level))
                    .addParams("address", mTextAddress.getText().toString())
                    .addParams("birthday", mStartBirthday.getText().toString())
                    .addParams("sex", String.valueOf(sex))
                    .addParams("username", mTextName.getText().toString())
                    .build().execute(new StringCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.show(Const.CONS_STR_ERROR);
                }

                @Override
                public void onResponse(String response, int id) {
                    BaseBean baseBean = GsonUtil.gsonToBean(response, BaseBean.class, UserInformationActivity.this);
                    if (baseBean != null) {
                        boolean personalInformation = PreferencesUtil.getBoolean(UserInformationActivity.this, "personalInformation");
                        if (!personalInformation) {
                            JumpIntent.jump(UserInformationActivity.this, GuidePersonalCenterActivity.class);
                        }
                        finish();
                        ToastUtils.show("修改成功!");
                        overridePendingTransition(android.R.anim.slide_in_left, R.anim.out_to_down);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    BasicInformaitonBean sekiguchiBygateNumBean = GsonUtil.gsonToBean(response, BasicInformaitonBean.class, UserInformationActivity.this);
                    if (sekiguchiBygateNumBean != null) {
                        BasicInformaitonBean.DataBean data = sekiguchiBygateNumBean.getData();
                        BasicInformaitonBean.DataBean.UserInfoBean userInfo = data.getUserInfo();
                        BasicInformaitonBean.DataBean.UserDetailBean userDetail = data.getUserDetail();
                        mTextName.setText(ConstUtils.getStringNoEmpty(userInfo.getUsername()));
                        sex = userInfo.getSex();
                        if (sex == 1) {
                            mManCheck.setBackgroundResource(R.mipmap.settings_item_check);
                            mWoManCheck.setBackgroundResource(R.mipmap.settings_item_uncheck);
                        } else {
                            mManCheck.setBackgroundResource(R.mipmap.settings_item_uncheck);
                            mWoManCheck.setBackgroundResource(R.mipmap.settings_item_check);
                        }
                        long birthday = userInfo.getBirthday();
                        String dateToString = ConstUtils.getDateToString(birthday, "yyyy-MM-dd");
                        mStartBirthday.setText(ConstUtils.getStringNoEmpty(dateToString));
                        mTextAddress.setText(ConstUtils.getStringNoEmpty(userInfo.getAddress()));
                        mTextPhone.setText(ConstUtils.getStringNoEmpty(userInfo.getMobile()));
                        level = userDetail.getLevel();
                        mTextLeveNum.setText(ConstUtils.getChessLevel(level));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //显示提示框 type1、昵称为空2、生日为空3、居住地为空 4、名字长度不能大于5
    private void showPopupWindow(View view, final int type) {
        View layout = LayoutInflater.from(UserInformationActivity.this).inflate(R.layout.pop_window_layout, null, false);
        MyPopupWindow.myPopupWindow(view, layout);
        Button btnSava = layout.findViewById(R.id.button_save);
        Button btnCancel = layout.findViewById(R.id.button_cancel);
        btnCancel.setVisibility(View.GONE);
        TextView texContent = layout.findViewById(R.id.popup_window_content);
        if (type == 1) {
            texContent.setText("请输入昵称");
        } else if (type == 2) {
            texContent.setText("请选择生日");
        } else if (type == 3) {
            texContent.setText("请选择地区");
        } else if (type == 4) {
            texContent.setText("用户名不能超过五个字");
        }
        btnSava.setText("确认");
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_save:
                        MyPopupWindow.dismissPopWindow();
                        break;
                    default:
                        break;
                }
            }
        };
        //设置popwindow布局控件的点击事件
        btnSava.setOnClickListener(listener);
    }

    private void initPopupWindow() {
        // 用于PopupWindow的View
        View contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow_information_layout, null, false);
        final WheelView wheelView = contentView.findViewById(R.id.wheelView);
        //初始化数据
        List<String> list = new ArrayList<>();
        for (int i = 25; i > 17; i--) {
            list.add(i+"K");
        }
        //设置数据
        wheelView.setData(list);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        window = new PopupWindow(contentView, width, height, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(false);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
//        window.showAsDropDown(btn1);
        // 或者也可以调用此方法显示PopupWindow，其中：
        // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
        // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
//        window.showAtLocation(btn1, Gravity.BOTTOM, 0, 0);
        contentView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        contentView.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextLeveNum.setText(String.valueOf(wheelView.getSelectedItemData()));
                window.dismiss();
            }
        });
    }
}
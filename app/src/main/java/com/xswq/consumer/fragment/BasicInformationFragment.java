package com.xswq.consumer.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xswq.consumer.R;
import com.xswq.consumer.activity.personalCenter.ChessHeadImageActivity;
import com.xswq.consumer.activity.personalCenter.ModifiedDataActivity;
import com.xswq.consumer.activity.personalCenter.PersonalCenterActivity;
import com.xswq.consumer.activity.personalCenter.ShoppingMallActivity;
import com.xswq.consumer.activity.personalCenter.UserInformationActivity;
import com.xswq.consumer.base.BaseFragment;
import com.xswq.consumer.bean.AchievementBean;
import com.xswq.consumer.bean.BasicInformaitonBean;
import com.xswq.consumer.config.Const;
import com.xswq.consumer.config.ContactUrl;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.GsonUtil;
import com.xswq.consumer.utils.ImageLoader;
import com.xswq.consumer.utils.JumpIntent;
import com.xswq.consumer.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.List;
import okhttp3.Call;

/**
 * 个人中心 基本信息
 */
public class BasicInformationFragment extends BaseFragment implements View.OnClickListener {

    private String TAG = "BasicInformationFragment";
    private View mView;
    private TextView mUserName, mUserSite, mUserLeve, mTextName, mTextName2, mTextName3;
    private ImageView mChessUserHead, mModifiedData, imageView, imageView2, imageView3, mImageNextBg, mShoppingData;
    private List<AchievementBean.DataBean> mData;
    private ImageLoader imageLoader;
    private int gold;
    private ImageView mPictureFrame, mUserHead;

    public static BasicInformationFragment getInstances() {
        BasicInformationFragment basicInformationFragment = new BasicInformationFragment();
        return basicInformationFragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_basic_information_layout;
    }

    @Override
    protected void startLoad() {

    }

    @Override
    protected void initData() {
        if (mView == null) {
            mView = getContentView();
        }
        mUserName = findViewById(R.id.user_name);
        mUserSite = findViewById(R.id.user_site);
        mUserLeve = findViewById(R.id.user_leve);
        mChessUserHead = findViewById(R.id.chess_head_image);
        mChessUserHead.setOnClickListener(this);
        mPictureFrame = findViewById(R.id.chess_picture_frame_image);
        mUserHead = findViewById(R.id.image_head);
        mShoppingData = findViewById(R.id.personal_cente_shopping);
        mShoppingData.setOnClickListener(this);
        mModifiedData = findViewById(R.id.basic_information_modified_data);
        mModifiedData.setOnClickListener(this);

        imageView = findViewById(R.id.image_view);
        mTextName = findViewById(R.id.text_name);

        imageView2 = findViewById(R.id.image_view2);
        mTextName2 = findViewById(R.id.text_name2);

        imageView3 = findViewById(R.id.image_view3);
        mTextName3 = findViewById(R.id.text_name3);

        mImageNextBg = findViewById(R.id.image_next_bg);
        mImageNextBg.setOnClickListener(this);

        imageLoader = new ImageLoader(getActivity());
    }

    @Override
    protected void stopLoad() {

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
                    BasicInformaitonBean sekiguchiBygateNumBean = GsonUtil.gsonToBean(response, BasicInformaitonBean.class, getActivity());
                    if (sekiguchiBygateNumBean != null) {
                        BasicInformaitonBean.DataBean data = sekiguchiBygateNumBean.getData();
                        BasicInformaitonBean.DataBean.UserInfoBean userInfo = data.getUserInfo();
                        BasicInformaitonBean.DataBean.UserDetailBean userDetail = data.getUserDetail();
                        gold = userDetail.getGold();
                        ConstUtils.setTextString(userInfo.getUsername(), mUserName);
                        if (TextUtils.isEmpty(userInfo.getAddress())) {
                            mUserSite.setText("暂未设置地区");
                        } else {
                            mUserSite.setText(userInfo.getAddress());
                        }
                        String chessLevel = ConstUtils.getChessLevel(userDetail.getLevel());
                        mUserLeve.setText(chessLevel);
                        imageLoader.loadHeadImage(ConstUtils.getStringNoEmpty(userInfo.getHeadimg()), mUserHead);
                        imageLoader.loadCircularImage(ConstUtils.getStringNoEmpty(userDetail.getHeadImgBorder()), mPictureFrame);
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
            case R.id.basic_information_modified_data:
                JumpIntent.jump(getActivity(), ModifiedDataActivity.class);
                break;
            case R.id.image_next_bg:
                PersonalCenterActivity activity = (PersonalCenterActivity) getActivity();
                activity.getThreeTab();
                break;
            case R.id.chess_head_image:
                JumpIntent.jump(getActivity(), ChessHeadImageActivity.class);
                break;
            case R.id.personal_cente_shopping:
                JumpIntent.jump(getActivity(), ShoppingMallActivity.class, gold);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
        getData("0");
    }

    private void getData(final String type) {
        try {
            OkHttpUtils.post().url(ContactUrl.POST_ACHIEVEMENT_LIST_PATH)
                    .addParams("token", Const.TOKEN)
                    .addParams("uid", Const.UID)
                    .addParams("type", type)
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
                        switch (mData.size()) {
                            case 0:
                                getData("1");
                                break;
                            case 1:
                                initView(0, imageView, mTextName, type);
                                break;
                            case 2:
                                initView(0, imageView, mTextName, type);
                                initView(1, imageView2, mTextName2, type);
                                break;
                            case 3:
                                initView(0, imageView, mTextName, type);
                                initView(1, imageView2, mTextName2, type);
                                initView(2, imageView3, mTextName3, type);
                                break;
                            default:
                                initView(0, imageView, mTextName, type);
                                initView(1, imageView2, mTextName2, type);
                                initView(2, imageView3, mTextName3, type);
                                break;
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView(int index, ImageView imageView, TextView mTextName, String type) {
        if (Const.STR0.equals(type)) {
            imageLoader.loadImage(ConstUtils.getStringNoEmpty(mData.get(index).getImgUrl()), R.mipmap.aoyoutree, imageView);
            mTextName.setText(ConstUtils.getStringNoEmpty(mData.get(index).getAchieveName()));
        } else {
            imageLoader.loadImage(ConstUtils.getStringNoEmpty(mData.get(index).getImgUrl2()), R.mipmap.aoyoutree, imageView);
            mTextName.setText(ConstUtils.getStringNoEmpty(mData.get(index).getAchieveName()));
        }

    }
}

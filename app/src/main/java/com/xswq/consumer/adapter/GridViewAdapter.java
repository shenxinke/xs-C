package com.xswq.consumer.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xswq.consumer.R;
import com.xswq.consumer.bean.AchievementBean;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.ImageLoader;
import com.xswq.consumer.view.MyProgressBar;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    //RecyclerView所需的类
    private List<AchievementBean.DataBean> items;
    private int type;
    private AchievementAdapter.OnClickListener onRecyclerViewButtonClickListener;
    private LayoutInflater layoutInflater;


    public interface OnClickListener {
        void OnButtonClickListener(int position, int id, String achieveName, String ImgUrl);

        void OnItemClickListener(String Description);
    }

    public void setOnRecyclerViewButtonClickListener(AchievementAdapter.OnClickListener onItemClickListener) {
        this.onRecyclerViewButtonClickListener = onItemClickListener;
    }

    public GridViewAdapter(Context mContext, List<AchievementBean.DataBean> items, int type) {
        this.mContext = mContext;
        this.items = items;
        this.type = type;
        layoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.succrss_system_grid_item, viewGroup, false);
            viewHolder.textName = view.findViewById(R.id.text_name);
            viewHolder.textTime = view.findViewById(R.id.text_time);
            viewHolder.imageView = view.findViewById(R.id.image_view);
            viewHolder.mProgressBar = view.findViewById(R.id.my_progress_bar);
            viewHolder.mButton = view.findViewById(R.id.but_receive_award);
            viewHolder.mIamgeBackgroud = view.findViewById(R.id.iamge_backgroud);
            viewGroup.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) viewGroup.getTag();
        }


        final AchievementBean.DataBean dataBean = items.get(i);

        viewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecyclerViewButtonClickListener.OnButtonClickListener(i, dataBean.getAchieveId(), dataBean.getAchieveName(), dataBean.getImgUrl());
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewButtonClickListener.OnItemClickListener(dataBean.getDescription());
            }
        });
        setData(dataBean, i, viewHolder);
        return view;
    }


    public void setData(AchievementBean.DataBean item, int position, ViewHolder viewHolder) {
        viewHolder.textName.setText(item.getAchieveName());
        long createTime = item.getCreateTime();
        String imgUrl = item.getImgUrl();
        String imgUrl2 = item.getImgUrl2();
        ImageLoader imageLoader = new ImageLoader((Activity) mContext);
        int maxValue = item.getMaxValue();
        int nowValue = item.getNowValue();
        if (type == 0) {
            int index = position + 1;
            if (index % 2 == 0) {
                viewHolder.mIamgeBackgroud.setBackgroundResource(R.drawable.shape_basic_information_bg2);
            } else {
                viewHolder.mIamgeBackgroud.setBackgroundResource(R.drawable.shape_basic_information_bg);
            }
            String dateToString = ConstUtils.getDateToString(createTime, "yyyy-MM-dd");
            viewHolder.textTime.setText(dateToString + "获得");
            viewHolder.textTime.setVisibility(View.VISIBLE);
            viewHolder.mProgressBar.setVisibility(View.INVISIBLE);
            viewHolder.mButton.setVisibility(View.INVISIBLE);
            imageLoader.loadImage(ConstUtils.getStringNoEmpty(imgUrl), R.mipmap.aoyoutree, viewHolder.imageView);
        } else {
            viewHolder.mIamgeBackgroud.setBackgroundResource(R.drawable.shape_basic_information_bg3);
            if (item.getState() == 1) {
                viewHolder.mButton.setVisibility(View.VISIBLE);
                viewHolder.mProgressBar.setVisibility(View.INVISIBLE);
                viewHolder.textTime.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.mProgressBar.setVisibility(View.VISIBLE);
                viewHolder.textTime.setVisibility(View.VISIBLE);
                viewHolder.mButton.setVisibility(View.INVISIBLE);
                viewHolder.textTime.setText(nowValue + "/" + maxValue);
            }
            imageLoader.loadImage(ConstUtils.getStringNoEmpty(imgUrl2), R.mipmap.aoyoutree, viewHolder.imageView);
        }
        viewHolder.mProgressBar.setTotalAndCurrentCount(maxValue, nowValue);
    }

    class ViewHolder {
        private ImageView imageView, mIamgeBackgroud;
        private TextView textName, textTime;
        private MyProgressBar mProgressBar;
        private Button mButton;
    }

    public void updataList(List<AchievementBean.DataBean> items, int type) {
        this.items = items;
        this.type = type;
        notifyDataSetChanged();
    }

}

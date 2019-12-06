package com.xswq.consumer.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xswq.consumer.R;
import com.xswq.consumer.bean.AchievementBean;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.ImageLoader;
import com.xswq.consumer.view.MyProgressBar;

import java.util.List;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.GridViewHolder> {

    private Context mContext;
    //RecyclerView所需的类
    private List<AchievementBean.DataBean> items;
    private int type;
    private OnClickListener onRecyclerViewButtonClickListener;

    public interface OnClickListener {
        void OnButtonClickListener(int position, int id, String achieveName, String ImgUrl);

        void OnItemClickListener(String Description);
    }

    public void setOnRecyclerViewButtonClickListener(OnClickListener onItemClickListener) {
        this.onRecyclerViewButtonClickListener = onItemClickListener;
    }

    //构造方法，一般需要接收两个参数 1.上下文 2.集合对象（包含了我们所需要的数据）
    public AchievementAdapter(Context mContext, List<AchievementBean.DataBean> items, int type) {
        this.mContext = mContext;
        this.items = items;
        this.type = type;
    }

    @Override
    public AchievementAdapter.GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //转换一个ViewHolder对象，决定了item的样式，参数1.上下文 2.XML布局资源 3.null
        View itemView = View.inflate(mContext, R.layout.succrss_system_grid_item, null);
        //创建一个ViewHodler对象
        GridViewHolder gridViewHolder = new GridViewHolder(itemView);
        //把ViewHolder传出去
        return gridViewHolder;
    }

    @Override
    public void onBindViewHolder(AchievementAdapter.GridViewHolder holder, final int position) {
        final AchievementBean.DataBean dataBean = items.get(position);
        holder.setData(dataBean, position);
        if (onRecyclerViewButtonClickListener != null) {
            holder.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecyclerViewButtonClickListener.OnButtonClickListener(position, dataBean.getAchieveId(), dataBean.getAchieveName(), dataBean.getImgUrl());
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerViewButtonClickListener.OnItemClickListener(dataBean.getDescription());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }


    public void upData(List<AchievementBean.DataBean> datas) {
        items.clear();
        items = datas;
        notifyDataSetChanged();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView, mIamgeBackgroud;
        private TextView textName, textTime;
        private MyProgressBar mProgressBar;
        private Button mButton;

        public GridViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            textTime = itemView.findViewById(R.id.text_time);
            imageView = itemView.findViewById(R.id.image_view);
            mProgressBar = itemView.findViewById(R.id.my_progress_bar);
            mButton = itemView.findViewById(R.id.but_receive_award);
            mIamgeBackgroud = itemView.findViewById(R.id.iamge_backgroud);
        }

        public void setData(AchievementBean.DataBean item, int position) {
            textName.setText(item.getAchieveName());
            long createTime = item.getCreateTime();
            String imgUrl = item.getImgUrl();
            String imgUrl2 = item.getImgUrl2();
            ImageLoader imageLoader = new ImageLoader((Activity) mContext);
            int maxValue = item.getMaxValue();
            int nowValue = item.getNowValue();
            if (type == 1) {
                int index = position + 1;
                if (index % 2 == 0) {
                    mIamgeBackgroud.setBackgroundResource(R.drawable.shape_basic_information_bg2);
                } else {
                    mIamgeBackgroud.setBackgroundResource(R.drawable.shape_basic_information_bg);
                }
                String dateToString = ConstUtils.getDateToString(createTime, "yyyy-MM-dd");
                textTime.setText(dateToString + "获得");
                textTime.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.INVISIBLE);
                mButton.setVisibility(View.INVISIBLE);
                imageLoader.loadImage(ConstUtils.getStringNoEmpty(imgUrl), R.mipmap.aoyoutree, imageView);
            } else {
                mIamgeBackgroud.setBackgroundResource(R.drawable.shape_basic_information_bg3);
                if (item.getState() == 1) {
                    mButton.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.INVISIBLE);
                    textTime.setVisibility(View.INVISIBLE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    textTime.setVisibility(View.VISIBLE);
                    mButton.setVisibility(View.INVISIBLE);
                    textTime.setText(nowValue + "/" + maxValue);
                }
                imageLoader.loadImage(ConstUtils.getStringNoEmpty(imgUrl2), R.mipmap.aoyoutree, imageView);
            }
            mProgressBar.setTotalAndCurrentCount(maxValue, nowValue);
        }
    }
}

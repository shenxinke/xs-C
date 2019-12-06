package com.xswq.consumer.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xswq.consumer.R;
import com.xswq.consumer.bean.HeadPortraitBean;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.ImageLoader;

import java.util.List;

/**
 * 头像框适配器
 */
public class HeadPortraitAdapter extends RecyclerView.Adapter<HeadPortraitAdapter.GridViewHolder> {

    private Context mContext;
    //RecyclerView所需的类
    private List<HeadPortraitBean.DataBean> items;
    private OnItemLockIdClickListener onItemLockClickListener;
    private OnItemCheckClickListener onItemCheckClickListener;

    public interface OnItemLockIdClickListener {
        void onClick(String ID, int position);
    }

    public interface OnItemCheckClickListener {
        void onClick(String id);
    }

    public void setItemLockClickListener(OnItemLockIdClickListener onItemClickListener) {
        this.onItemLockClickListener = onItemClickListener;
    }

    public void setItemCheckClickListener(OnItemCheckClickListener onImageClickListener) {
        this.onItemCheckClickListener = onImageClickListener;
    }


    //构造方法，一般需要接收两个参数 1.上下文 2.集合对象（包含了我们所需要的数据）
    public HeadPortraitAdapter(Context mContext, List<HeadPortraitBean.DataBean> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //转换一个ViewHolder对象，决定了item的样式，参数1.上下文 2.XML布局资源 3.null
        View itemView = View.inflate(mContext, R.layout.head_portrait_item_layout, null);
        //创建一个ViewHodler对象
        GridViewHolder gridViewHolder = new GridViewHolder(itemView);
        //把ViewHolder传出去
        return gridViewHolder;
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, final int position) {
        HeadPortraitBean.DataBean dataBean = items.get(position);
        holder.setData(dataBean, position);
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView, headPortraitLock, headPortraitCheck, headPortraitConfirm;

        public GridViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            headPortraitLock = itemView.findViewById(R.id.head_portrait_lock);
            headPortraitCheck = itemView.findViewById(R.id.head_portrait_check);
            headPortraitConfirm = itemView.findViewById(R.id.head_portrait_confirm);
        }

        public void setData(final HeadPortraitBean.DataBean item, final int position) {
            String imgUrl = item.getProductImg();
            ImageLoader imageLoader = new ImageLoader((Activity) mContext);
            imageLoader.loadImage(ConstUtils.getStringNoEmpty(imgUrl), R.mipmap.tourist_head, imageView);
            if (item.getBelong() > 0) {
                headPortraitLock.setVisibility(View.GONE);
            } else {
                headPortraitLock.setVisibility(View.VISIBLE);
            }
            if (item.isCheck()) {
                headPortraitCheck.setVisibility(View.VISIBLE);
                headPortraitConfirm.setVisibility(View.VISIBLE);
            } else {
                headPortraitCheck.setVisibility(View.GONE);
                headPortraitConfirm.setVisibility(View.GONE);
            }

            headPortraitLock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemLockClickListener != null) {
                        onItemLockClickListener.onClick(String.valueOf(item.getID()), position);
                    }
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < items.size(); i++) {
                        if (i != position) {
                            items.get(i).setCheck(false);
                        } else {
                            items.get(i).setCheck(true);
                        }
                    }
                    notifyDataSetChanged();
                }
            });

            headPortraitConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemCheckClickListener != null) {
                        String id = String.valueOf(items.get(position).getID());
                        onItemCheckClickListener.onClick(id);
                    }
                }
            });
        }
    }
}
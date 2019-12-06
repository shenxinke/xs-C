package com.xswq.consumer.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xswq.consumer.R;
import com.xswq.consumer.bean.AchievementBean;
import com.xswq.consumer.bean.ShoppingMallParticularsBean;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.ImageLoader;
import com.xswq.consumer.utils.LogUtil;
import com.xswq.consumer.utils.ToastUtils;

import java.util.List;

public class ShoppingMallAdapter extends RecyclerView.Adapter<ShoppingMallAdapter.GridViewHolder> {
    private Context mContext;
    //RecyclerView所需的类
    private List<ShoppingMallParticularsBean.DataBean.ListBean> items;
    private OnItemClickListener onRecyclerViewButtonClickListener;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setShoppingMallOnRecyclerViewItemClickListener(OnItemClickListener onItemClickListener) {
        this.onRecyclerViewButtonClickListener = onItemClickListener;
    }

    //构造方法，一般需要接收两个参数 1.上下文 2.集合对象（包含了我们所需要的数据）
    public ShoppingMallAdapter(Context mContext, List<ShoppingMallParticularsBean.DataBean.ListBean> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //转换一个ViewHolder对象，决定了item的样式，参数1.上下文 2.XML布局资源 3.null
        View itemView = View.inflate(mContext, R.layout.shopping_mall_item_layout, null);
        //创建一个ViewHodler对象
        GridViewHolder gridViewHolder = new GridViewHolder(itemView);
        //把ViewHolder传出去
        return gridViewHolder;
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, final int position) {
        final ShoppingMallParticularsBean.DataBean.ListBean listBean = items.get(position);
        holder.setData(listBean, position);
        if (onRecyclerViewButtonClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecyclerViewButtonClickListener.onClick(position);
                    for (int i = 0; i < items.size(); i++) {
                        if (i != position) {
                            items.get(i).setIsSelect(false);
                        } else {
                            items.get(i).setIsSelect(true);
                        }
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }


    public class GridViewHolder extends RecyclerView.ViewHolder {
        private final TextView shoppingMoney;
        private final ImageView shoppingImage;
        private final ImageView timeLimit;
        private final ImageView shoppingDiscounts;
        private final ImageView whaterImage;
        private final ImageView shoppingSelect;

        public GridViewHolder(View itemView) {
            super(itemView);
            shoppingMoney = itemView.findViewById(R.id.shopping_money_image);
            shoppingImage = itemView.findViewById(R.id.shopping_image);
            timeLimit = itemView.findViewById(R.id.time_limit_bg);
            shoppingDiscounts = itemView.findViewById(R.id.shopping_discounts_bg);
            whaterImage = itemView.findViewById(R.id.whate_image);
            shoppingSelect = itemView.findViewById(R.id.shape_shopping_mall_bg);
        }

        public void setData(ShoppingMallParticularsBean.DataBean.ListBean item, final int position) {
            ImageLoader imageLoader = new ImageLoader((Activity) mContext);
            imageLoader.loadImage(item.getProductImg(), R.color.white, shoppingImage);
            int discountValues = item.getDiscountValue();
            if (discountValues == 0) {
                String productValue = String.valueOf(item.getProductValue());
                ConstUtils.setTextString(productValue, shoppingMoney);
                shoppingDiscounts.setVisibility(View.GONE);
            } else {
                shoppingDiscounts.setVisibility(View.VISIBLE);
                String discountValue = String.valueOf(discountValues);
                ConstUtils.setTextString(discountValue, shoppingMoney);
            }
            long endTime = item.getEndTime();
            if (endTime > 0) {
                timeLimit.setVisibility(View.VISIBLE);
            } else {
                timeLimit.setVisibility(View.GONE);
            }
            int belong = item.getBelong();
            if (belong > 0) {
                ConstUtils.setTextString("已拥有", shoppingMoney);
                whaterImage.setVisibility(View.GONE);
            } else {
                if (item.getIsRmb() == 1) {
                    if (discountValues == 0) {
                        String productValue = String.valueOf(item.getProductValue()) + " 元";
                        ConstUtils.setTextString(productValue, shoppingMoney);
                    } else {
                        String discountValue = String.valueOf(discountValues) + " 元";
                        ConstUtils.setTextString(discountValue, shoppingMoney);
                    }
                } else {
                    whaterImage.setVisibility(View.VISIBLE);
                }
            }
            if (items.get(position).getIsSelect()) {
                shoppingSelect.setBackgroundResource(R.drawable.shape_shopping_select_bg);
            } else {
                shoppingSelect.setBackgroundResource(R.drawable.shape_shopping_mall_bg);
            }
        }
    }

    public void updataList(List<ShoppingMallParticularsBean.DataBean.ListBean> data) {
        this.items.clear();
        this.items = data;
        notifyDataSetChanged();
    }
}
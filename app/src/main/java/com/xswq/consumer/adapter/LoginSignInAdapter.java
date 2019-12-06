package com.xswq.consumer.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xswq.consumer.R;
import com.xswq.consumer.bean.ShoppingMallParticularsBean;
import com.xswq.consumer.bean.SignInBean;
import com.xswq.consumer.utils.ConstUtils;
import com.xswq.consumer.utils.ImageLoader;

import java.util.List;

public class LoginSignInAdapter extends RecyclerView.Adapter<LoginSignInAdapter.GridViewHolder> {
    private Context mContext;
    //RecyclerView所需的类
    private List<SignInBean> items;

    //构造方法，一般需要接收两个参数 1.上下文 2.集合对象（包含了我们所需要的数据）
    public LoginSignInAdapter(Context mContext, List<SignInBean> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //转换一个ViewHolder对象，决定了item的样式，参数1.上下文 2.XML布局资源 3.null
        View itemView = View.inflate(mContext, R.layout.login_signin_item_layout, null);
        //创建一个ViewHodler对象
        GridViewHolder gridViewHolder = new GridViewHolder(itemView);
        //把ViewHolder传出去
        return gridViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder viewHolder, int i) {
        SignInBean signInBean = items.get(i);
        viewHolder.setData(signInBean);
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        private TextView textName;
        private TextView textNumber;
        private ImageView shoppingImage;

        public GridViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            textNumber = itemView.findViewById(R.id.text_number);
            shoppingImage = itemView.findViewById(R.id.shopping_iamge);
        }

        public void setData(SignInBean item) {
            ImageLoader imageLoader = new ImageLoader((Activity) mContext);
            textName.setText(ConstUtils.getStringNoEmpty(item.getDataName()));
            if (item.getProductNum() > 1) {
                textNumber.setText(ConstUtils.getStringNoEmpty(item.getProductName() + "×" + item.getProductNum()));
            } else {
                textNumber.setText(ConstUtils.getStringNoEmpty(item.getProductName()));
            }
            imageLoader.loadImage(ConstUtils.getStringNoEmpty(item.getProductImg()), 0, shoppingImage);
        }
    }
}

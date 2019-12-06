package com.xswq.consumer.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.Visibility;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * 通用的ViewHolder
 *
 */
public class BaseListViewHolder {
    private SparseArray<View> mViews;
    private static boolean conver = true;
    private View mConverView;

    /**
     * @param context
     * @param parent
     * @param layoutId Item的布局文件
     */
    private BaseListViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mViews = new SparseArray<>();
        this.mConverView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.mConverView.setTag(this);
    }

    /**
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @return
     */
    public static BaseListViewHolder get(Context context, View convertView,
                                         ViewGroup parent, int layoutId, int position) {
        BaseListViewHolder viewHolder = null;
        if (conver) {
            if (convertView == null) {
                viewHolder = new BaseListViewHolder(context, parent, layoutId, position);
            } else {
                viewHolder = (BaseListViewHolder) convertView.getTag();
            }
        } else {
            viewHolder = new BaseListViewHolder(context, parent, layoutId, position);
        }
        return viewHolder;
    }

    /**
     * 通过id获取组件 注意：不要将组件声明到全局，否则获取到的永远是最后一个组件
     *
     * @param viewId 控件ID
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConverView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return this.mConverView;
    }


    /**
     * TextView
     *
     * @param viewId
     * @param text   string
     * @return
     */
    public BaseListViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public BaseListViewHolder setTextGone(int viewId, int type) {
        TextView tv = getView(viewId);
        if (type == 1) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public BaseListViewHolder setEtextGone(int viewId, int type) {
        EditText tv = getView(viewId);
        if (type == 1) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public String getEtext(int viewId) {
        EditText tv = getView(viewId);
        tv.getText().toString();
        return tv.getText().toString();
    }

    public String getEtextOnclik(int viewId) {
        final EditText tv = getView(viewId);
        final String[] s1 = {};
        tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                s1[0] = tv.getText().toString();
            }
        });
        return s1[0];
    }

    public String getText(int viewId) {
        TextView tv = getView(viewId);
        tv.getText().toString();
        return tv.getText().toString();
    }

    public BaseListViewHolder setVisiable(int viewId, int flag) {
        TextView tv = getView(viewId);
        LinearLayout linearLayout = getView(viewId);
        tv.setVisibility(flag);
        return this;
    }

    public BaseListViewHolder setTvVisiable(int viewId, int flag) {
        TextView tv = getView(viewId);
        tv.setVisibility(flag);
        return this;
    }

    public BaseListViewHolder setRlVisiable(int viewId, int flag) {
        LinearLayout rl = getView(viewId);
        rl.setVisibility(flag);
        return this;
    }

    public BaseListViewHolder setTextColor(int viewId, int textColor) {
        TextView tv = getView(viewId);
        tv.setTextColor(textColor);
        return this;
    }


    /**
     * TextView
     *
     * @param viewId
     * @param number float
     * @return
     */
    public BaseListViewHolder setText(int viewId, float number) {
        StringBuilder builder = new StringBuilder();
        TextView tv = getView(viewId);
        tv.setText(builder.append(number));
        return this;
    }

    /**
     * TextView
     *
     * @param viewId
     * @param resId  float
     * @return
     */
    public BaseListViewHolder settTextBgById(int viewId, int resId) {
        TextView textView = getView(viewId);
        textView.setBackgroundResource(resId);
        return this;
    }

    /**
     * TextView
     *
     * @param viewId
     * @param number int
     * @return
     */
    public BaseListViewHolder setText(int viewId, int number) {
        StringBuilder builder = new StringBuilder();
        TextView tv = getView(viewId);
        tv.setText(builder.append(number));
        return this;
    }

    /**
     * TextView
     *
     * @param viewId
     * @param number double
     * @return
     */
    public BaseListViewHolder setText(int viewId, Double number) {
        StringBuilder builder = new StringBuilder();
        TextView tv = getView(viewId);
        tv.setText(builder.append(number));
        return this;
    }

    /**
     * Button
     *
     * @param viewId
     * @param text   string
     * @return
     */
    public BaseListViewHolder setButton(int viewId, String text) {
        Button btn = getView(viewId);
        btn.setText(text);
        return this;
    }

    /**
     * Button
     *
     * @param viewId
     * @param resId  资源ID
     * @return
     */
    public BaseListViewHolder setButtonBgById(int viewId, int resId) {
        Button btn = getView(viewId);
        btn.setBackgroundResource(resId);
        return this;
    }

    public BaseListViewHolder setButtonColor(int viewId, int textColor) {
        Button btn = getView(viewId);
        btn.setTextColor(textColor);
        return this;
    }


    /**
     * 通过资源ID设置ImageView
     *
     * @param viewId
     * @param resId  资源ID
     * @return
     */
    public BaseListViewHolder setImageById(int viewId, int resId) {
        ImageView image = getView(viewId);
        image.setImageResource(resId);
        return this;
    }

    /**
     * 通过bimap设置ImageView
     *
     * @param viewId
     * @param bitmap
     * @return
     */
    public BaseListViewHolder setImageByBitmap(int viewId, Bitmap bitmap) {
        ImageView image = getView(viewId);
        image.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 通过url设置ImageView
     *
     * @param viewId
     * @param url    图片地址
     * @param resId  默认资源图片
     * @return
     */
    public BaseListViewHolder setImageByUrl(Context context, int viewId, String url, int resId) {
        ImageView image = getView(viewId);
        Glide.with(context).load(url).placeholder(resId).into(image);
        return this;
    }

    public BaseListViewHolder setImageGone(int viewId, int type) {
        ImageView image = getView(viewId);
        if (type == 1) {
            image.setVisibility(View.GONE);
        } else {
            image.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public BaseListViewHolder setImageByUrl(Context context, int viewId, String url) {
        ImageView image = getView(viewId);
        Glide.with(context).load(url).into(image);
        return this;
    }

    /**
     * Button点击事件
     *
     * @param viewId
     * @param listener
     */
    public BaseListViewHolder setButtonListener(int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    /**
     * Button长按监听
     *
     * @param viewId
     * @param listener
     * @return
     */
    public BaseListViewHolder setButtonLongListener(int viewId, View.OnLongClickListener listener) {
        getView(viewId).setOnLongClickListener(listener);
        return this;
    }

    /**
     * TextView点击事件
     *
     * @param viewId
     * @param listener
     */
    public BaseListViewHolder setTextListener(int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    /**
     * ImageView点击事件
     *
     * @param viewId
     * @param listener
     * @return
     */
    public BaseListViewHolder setImageListener(int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    public BaseListViewHolder setViewListener(int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    public BaseListViewHolder setRatingBar(int viewId, int rating) {
        RatingBar ratingBar = getView(viewId);
        ratingBar.setRating(rating);
        return this;
    }

    /**
     * 设置GridView item  监听
     *
     * @param viewId
     * @param listener
     * @return
     */
    public BaseListViewHolder setOnItemClickListener(int viewId, AdapterView.OnItemClickListener listener) {
        GridView gridView = getView(viewId);
        gridView.setOnItemClickListener(listener);
        return this;
    }

    public BaseListViewHolder setImageError(Context context, int viewId, int res, String url) {
        ImageView image = getView(viewId);
        Glide.with(context).load(url).error(res).into(image);
        return this;
    }
}

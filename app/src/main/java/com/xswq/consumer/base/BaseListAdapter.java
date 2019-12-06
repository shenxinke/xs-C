package com.xswq.consumer.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用适配器
 *
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected boolean conver = true;
    protected int mItemLayoutId;
    protected List<T> mList;
    protected Context context;


    public BaseListAdapter(Context context, List<T> list, int itemLayoutId) {
        this.mItemLayoutId = itemLayoutId;
        this.context = context;
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
    }

    public BaseListAdapter(ArrayList<T> listBeen, int itemGoodslistGridview) {

    }

    public void onRefresh(List<T> mList) {
        if (mList != null) {
            this.mList.clear();
            this.mList.addAll(mList);
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> mList) {
        if (mList != null && mList.size() != 0) {
            this.mList.addAll(mList);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return IGNORE_ITEM_VIEW_TYPE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseListViewHolder viewHolder = BaseListViewHolder.get(context,
                convertView, parent, mItemLayoutId, position);
        convert(viewHolder, position, getItem(position));
        return viewHolder.getConvertView();
    }

    /**
     * 设置数据
     *
     * @param holder   holder对象
     * @param position 下标
     * @param item     实体对象
     */
    public abstract void convert(BaseListViewHolder holder, int position, T item);

}

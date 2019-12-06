package com.xswq.consumer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.xswq.consumer.R;

import java.util.List;

public class StarItemAdapter extends BaseAdapter {
    private Context context;
    private List<String> strings;

    public StarItemAdapter(Context context, List<String> strings) {
        this.context = context;
        this.strings = strings;
    }


    @Override
    public int getCount() {
        return null != strings ? strings.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ComViewHolder comHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.start_item_layout, null);
            comHolder = new ComViewHolder();
            comHolder.startImage = convertView.findViewById(R.id.start_image);
            convertView.setTag(comHolder);
        } else {
            comHolder = (ComViewHolder) convertView.getTag();
        }
        int starNum = Integer.parseInt(strings.get(position));
        if (starNum == 1) {
            comHolder.startImage.setBackgroundResource(R.mipmap.yellow_star);
        } else {
            comHolder.startImage.setBackgroundResource(R.mipmap.blue_star);
        }
        return convertView;
    }

    class ComViewHolder {
        ImageView startImage;
    }
}

package com.cyf.cyfimageselector.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cyf.cyfimageselector.R;
import com.cyf.cyfimageselector.utils.SDCardImageLoader;
import com.cyf.cyfimageselector.utils.ScreenUtils;

import java.util.List;

/**
 * 预览查看
 * Created by caoyingfu on 2017/8/7.
 */

public class GrapeGridAdapter0 extends BaseAdapter {

    private Context context;
    private List<String> list;

    public GrapeGridAdapter0(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        ScreenUtils.initScreen((Activity) context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.photo_wall_item, null);
            holder = new ViewHolder();

            holder.photo_wall_item_photo = convertView.findViewById(R.id.photo_wall_item_photo);
            holder.view_top = convertView.findViewById(R.id.view_top);
            holder.photo_wall_item_cb = convertView.findViewById(R.id.photo_wall_item_cb);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SDCardImageLoader.setImgThumbnail(context,list.get(position),holder.photo_wall_item_photo);
        holder.view_top.setVisibility(View.GONE);
        holder.photo_wall_item_cb.setVisibility(View.GONE);
        return convertView;
    }

    private class ViewHolder {
        ImageView photo_wall_item_photo;
        View view_top;
        AppCompatCheckBox photo_wall_item_cb;
    }

}

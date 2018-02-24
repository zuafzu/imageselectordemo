package com.cyf.cyfimageselector.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.cyf.cyfimageselector.R;
import com.cyf.cyfimageselector.utils.SDCardImageLoader;

import java.util.List;

/**
 * Created by caoyingfu on 2018/2/24.
 */

public class MyNewAdapter extends DragBaseAdapter {

    private Context context;
    private List<String> list;
    private boolean isdelete;

    public MyNewAdapter(Context context, List<String> list, boolean isdelete, int num) {
        super(context, list, num);
        this.context = context;
        this.list = list;
        this.isdelete = isdelete;

        if (this.list.size() > 0) {
            for (int i = 0; i < this.list.size(); i++) {
                if (this.list.get(i).equals("add")) {
                    this.list.remove(i);
                    break;
                }
            }
        }
        if (this.list.size() < num) {
            this.list.add("add");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.photo_wall_item;
    }

    @Override
    protected void initView(ViewHolder holder) {
        holder.addView(R.id.photo_wall_item_photo);
        holder.addView(R.id.view_top);
        holder.addView(R.id.photo_wall_item_cb);
        holder.addView(R.id.btn_delete);
    }

    @Override
    protected void setViewValue(final ViewHolder holder, int position) {
        final int a = position;
        holder.getView(R.id.view_top).setVisibility(View.GONE);
        holder.getView(R.id.photo_wall_item_cb).setVisibility(View.GONE);
        if (list.get(position).equals("add")) {
            ((View) holder.getView(R.id.photo_wall_item_photo).getParent()).setTag(R.string.app_name, "add");
            holder.getView(R.id.btn_delete).setVisibility(View.GONE);
            ((ImageView) holder.getView(R.id.photo_wall_item_photo)).setImageResource(R.mipmap.ic_add_image);
        } else {
            ((View) holder.getView(R.id.photo_wall_item_photo).getParent()).setTag(R.string.app_name, list.get(position));
            if (isdelete) {
                holder.getView(R.id.btn_delete).setVisibility(View.VISIBLE);
                holder.getView(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        list.remove(a);
                        // notifyDataSetInvalidated();
                        notifyDataSetChanged();
                    }
                });
            } else {
                holder.getView(R.id.btn_delete).setVisibility(View.GONE);
            }
            if (holder.getView(R.id.photo_wall_item_photo).getTag(R.string.app_name) != list.get(position)) {
                holder.getView(R.id.photo_wall_item_photo).setTag(R.string.app_name, list.get(position));
                ((ImageView) holder.getView(R.id.photo_wall_item_photo)).setImageResource(android.R.color.white);
                SDCardImageLoader.setImgThumbnail(context, list.get(position), ((ImageView) holder.getView(R.id.photo_wall_item_photo)));
            }
        }
    }

    public static class ViewHolder {

        private SparseArray<View> array;
        private View view;

        public ViewHolder(View view) {
            this.view = view;
            this.array = new SparseArray<>();
        }

        public void addView(int id) {
            array.append(id, view.findViewById(id));
        }

        public View getView(int id) {
            return (View) array.get(id);
        }

    }

}
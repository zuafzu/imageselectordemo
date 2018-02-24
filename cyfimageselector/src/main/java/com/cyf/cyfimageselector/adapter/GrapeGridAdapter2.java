//package com.cyf.cyfimageselector.adapter;
//
//import android.app.Activity;
//import android.content.Context;
//import android.support.v7.widget.AppCompatCheckBox;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//
//import com.cyf.cyfimageselector.R;
//import com.cyf.cyfimageselector.utils.SDCardImageLoader;
//import com.cyf.cyfimageselector.utils.ScreenUtils;
//
//import java.util.List;
//
///**
// * 编辑
// * Created by caoyingfu on 2017/8/7.
// */
//
//public class GrapeGridAdapter2 extends BaseAdapter {
//
//    private Context context;
//    private List<String> list;
//    private int num = 0;
//    private boolean isdelete;
//
//    private int mPoistion = -1;//拖拽时需要隐藏的位置
//
//    public GrapeGridAdapter2(Context context, List<String> list, boolean isdelete, int num) {
//        this.context = context;
//        this.list = list;
//        this.isdelete = isdelete;
//        this.num = num;
//        ScreenUtils.initScreen((Activity) context);
//    }
//
//    public int getmPoistion() {
//        return mPoistion;
//    }
//
//    public void setmPoistion(int mPoistion) {
//        this.mPoistion = mPoistion;
//    }
//
//    @Override
//    public int getCount() {
//        if (list.size() > 0) {
//            for (int i = 0; i < list.size(); i++) {
//                if (list.get(i).equals("add")) {
//                    list.remove(i);
//                    break;
//                }
//            }
//        }
//        if (list.size() < num) {
//            list.add("add");
//        }
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return list.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup viewGroup) {
//        ViewHolder holder;
//        // 防止图片重复
////        if (convertView == null) {
//        convertView = LayoutInflater.from(context).inflate(R.layout.photo_wall_item, viewGroup, false);
//        holder = new ViewHolder();
//
//        holder.photo_wall_item_photo = convertView.findViewById(R.id.photo_wall_item_photo);
//        holder.view_top = convertView.findViewById(R.id.view_top);
//        holder.photo_wall_item_cb = convertView.findViewById(R.id.photo_wall_item_cb);
//        holder.btn_delete = convertView.findViewById(R.id.btn_delete);
//        convertView.setTag(holder);
////        } else {
////            holder = (ViewHolder) convertView.getTag();
////        }
//        final int a = position;
//        holder.view_top.setVisibility(View.GONE);
//        holder.photo_wall_item_cb.setVisibility(View.GONE);
//        holder.photo_wall_item_photo.setImageResource(android.R.color.transparent);
//        if (list.get(position).equals("add")) {
//            convertView.setTag(R.string.app_name,"add");
//            holder.btn_delete.setVisibility(View.GONE);
//            holder.photo_wall_item_photo.setImageResource(R.mipmap.ic_add_image);
//        } else {
//            convertView.setTag(R.string.app_name,"");
//            if (isdelete) {
//                holder.btn_delete.setVisibility(View.VISIBLE);
//                holder.btn_delete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        list.remove(a);
//                        notifyDataSetInvalidated();
//                        notifyDataSetChanged();
//                    }
//                });
//            } else {
//                holder.btn_delete.setVisibility(View.GONE);
//            }
//            // holder.photo_wall_item_photo.setTag(list.get(position));
//            if (list.get(position) == null || "".equals(list.get(position))) {
//                holder.photo_wall_item_photo.setImageResource(R.mipmap.ic_err_img);
//            } else {
//                // loader.loadImage(4, list.get(position), holder.photo_wall_item_photo);
//                SDCardImageLoader.setImgThumbnail(context, list.get(position), holder.photo_wall_item_photo);
//            }
//        }
//        if (mPoistion == position) {
//            convertView.setVisibility(View.INVISIBLE);
//        }
//        return convertView;
//    }
//
//    private class ViewHolder {
//        ImageView photo_wall_item_photo;
//        View view_top;
//        AppCompatCheckBox photo_wall_item_cb;
//        ImageView btn_delete;
//    }
//
//    @Override
//    public void notifyDataSetChanged() {
//
//        super.notifyDataSetChanged();
//    }
//}

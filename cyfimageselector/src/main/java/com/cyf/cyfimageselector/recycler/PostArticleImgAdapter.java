package com.cyf.cyfimageselector.recycler;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cyf.cyfimageselector.R;
import com.cyf.cyfimageselector.model.PhotoConfigure;
import com.cyf.cyfimageselector.ui.PhotoPreviewActivity;
import com.cyf.cyfimageselector.ui.PhotoWallActivity2;
import com.cyf.cyfimageselector.utils.SDCardImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caoyingfu on 2018/2/26.
 */

public class PostArticleImgAdapter extends RecyclerView.Adapter<PostArticleImgAdapter.MyViewHolder> {

    private List<String> mDatas;
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private boolean isdelete;
    private int num;
    private PhotoConfigure photoConfigure;
    private View.OnClickListener listener;
    private boolean isClick = true;

    private int type = 0;// 0带保存按钮，3不带保存按钮
    private int mType = 0;//0查看，1编辑

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public PostArticleImgAdapter(Context context, List<String> datas, boolean isdelete, int num, PhotoConfigure photoConfigure) {
        this.mDatas = datas;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.isdelete = isdelete;
        this.num = num;
        this.photoConfigure = photoConfigure;

        if (this.mDatas.size() > 0) {
            for (int i = 0; i < this.mDatas.size(); i++) {
                if (this.mDatas.get(i).equals("add")) {
                    this.mDatas.remove(i);
                    break;
                }
            }
        }
        if (this.mDatas.size() < num) {
            this.mDatas.add("add");
        }
        mType = 1;
    }

    public PostArticleImgAdapter(Context context, List<String> datas, int type,boolean isClick) {
        this.mDatas = datas;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.type = type;
        mType = 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.inflate(R.layout.photo_wall_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.view_top.setVisibility(View.GONE);
        holder.photo_wall_item_cb.setVisibility(View.GONE);
        if (mType == 0) {
            SDCardImageLoader.setImgThumbnail(mContext, mDatas.get(position), ((ImageView) holder.photo_wall_item_photo));
            holder.photo_wall_item_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isClick){
                        if (listener != null) {
                            listener.onClick(holder.photo_wall_item_photo);
                        } else {
                            PhotoPreviewActivity.openPhotoPreview((Activity) mContext, position, mDatas.size(), type, (ArrayList<String>) mDatas, new PhotoPreviewActivity.OnHanlderResultCallback() {
                                @Override
                                public void onHanlderSuccess(List<String> resultList) {

                                }
                            });
                        }
                    }
                }
            });
        } else {
            if (mDatas.get(position).equals("add")) {
                ((View) holder.photo_wall_item_photo.getParent()).setTag(R.string.app_name, "add");
                holder.btn_delete.setVisibility(View.GONE);
                ((ImageView) holder.photo_wall_item_photo).setImageResource(R.mipmap.ic_add_image);
            } else {
                if (isdelete) {
                    holder.btn_delete.setVisibility(View.VISIBLE);
                    holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDatas.remove(position);
                            setmList();
                            notifyDataSetChanged();
                        }
                    });
                } else {
                    holder.btn_delete.setVisibility(View.GONE);
                }
                SDCardImageLoader.setImgThumbnail(mContext, mDatas.get(position), ((ImageView) holder.photo_wall_item_photo));
            }
            holder.photo_wall_item_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mDatas.get(position).equals("add")) {
                        for (int j = 0; j < mDatas.size(); j++) {
                            if (mDatas.get(j).equals("add")) {
                                mDatas.remove(j);
                                break;
                            }
                        }
                        PhotoWallActivity2.openImageSelecter((Activity) mContext, photoConfigure, new PhotoWallActivity2.OnHanlderResultCallback() {
                            @Override
                            public void onHanlderSuccess(List<String> resultList) {
                                mDatas.clear();
                                mDatas.addAll(resultList);
                                setmList();
                                notifyDataSetChanged();
                            }
                        });
                    } else {
                        for (int j = 0; j < mDatas.size(); j++) {
                            if (mDatas.get(j).equals("add")) {
                                mDatas.remove(j);
                                break;
                            }
                        }
                        PhotoPreviewActivity.openPhotoPreview((Activity) mContext, position, num, 2, (ArrayList<String>) mDatas, new PhotoPreviewActivity.OnHanlderResultCallback() {
                            @Override
                            public void onHanlderSuccess(List<String> resultList) {
                                mDatas.clear();
                                mDatas.addAll(resultList);
                                setmList();
                                notifyDataSetChanged();
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    private void setmList() {
        if (mDatas.size() > 0) {
            for (int i = 0; i < mDatas.size(); i++) {
                if (mDatas.get(i).equals("add")) {
                    mDatas.remove(i);
                    break;
                }
            }
        }
        if (mDatas.size() < num) {
            mDatas.add("add");
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView photo_wall_item_photo;
        View view_top;
        AppCompatCheckBox photo_wall_item_cb;
        ImageView btn_delete;

        public MyViewHolder(View itemView) {
            super(itemView);
            photo_wall_item_photo = itemView.findViewById(R.id.photo_wall_item_photo);
            view_top = itemView.findViewById(R.id.view_top);
            photo_wall_item_cb = itemView.findViewById(R.id.photo_wall_item_cb);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }


}
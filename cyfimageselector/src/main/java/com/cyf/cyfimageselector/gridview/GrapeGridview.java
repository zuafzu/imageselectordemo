package com.cyf.cyfimageselector.gridview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import com.cyf.cyfimageselector.adapter.GrapeGridAdapter0;
import com.cyf.cyfimageselector.adapter.MyNewAdapter;
import com.cyf.cyfimageselector.model.PhotoConfigure;
import com.cyf.cyfimageselector.ui.PhotoPreviewActivity;
import com.cyf.cyfimageselector.ui.PhotoWallActivity2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class GrapeGridview extends DragGridView {

    private List<String> mList = new ArrayList<>();
    // private GrapeGridAdapter2 grapeGridAdapter2;
    private GrapeGridAdapter0 grapeGridAdapter0;
    private MyNewAdapter grapeGridAdapter2;

    private boolean isClick = true;//缩略图是否可以点击（仅限查看时有效,初始化之前调用）

    public GrapeGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GrapeGridview(Context context) {
        super(context);
    }

    public GrapeGridview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }

    /**
     * 预览图片
     * 编辑添加移除图片
     *
     * @param context
     * @param list
     * @param type    （0带保存按钮，3不带保存按钮），（6带删除按钮，9不带删除按钮）
     */
    public void setWatchEditImg(Context context, final List<String> list, int type, final PhotoConfigure photoConfigure) {
        if (type == 0 || type == 3) {
            setWatchImg(context, list, type);
        } else if (type == 6 || type == 9) {
            setEditImg(context, list, (6 == type), photoConfigure);
        }
    }

    /**
     * 预览图片
     *
     * @param context
     * @param list
     * @param type    0带保存按钮，3不带保存按钮
     */
    public void setWatchImg(Context context, final List<String> list, final int type) {
        mList = list;
        grapeGridAdapter0 = new GrapeGridAdapter0(context, list);
        setAdapter(grapeGridAdapter0);
        if (isClick) {
            setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    PhotoPreviewActivity.openPhotoPreview((Activity) GrapeGridview.this.getContext(), i, list.size(), type, (ArrayList<String>) list, new PhotoPreviewActivity.OnHanlderResultCallback() {
                        @Override
                        public void onHanlderSuccess(List<String> resultList) {

                        }
                    });
                }
            });
        }
        //this.setCanDrag(false);
    }

    /**
     * 编辑添加移除图片
     *
     * @param context
     * @param list           已选中的图片
     * @param isDelete       true带删除按钮，false不带
     * @param photoConfigure
     */
    public void setEditImg(Context context, final List<String> list, boolean isDelete, final PhotoConfigure photoConfigure) {
        mList = list;
//        photoConfigure.setSingle(false);
        photoConfigure.setList((ArrayList<String>) list);
        if (photoConfigure.isSingle()) {
            photoConfigure.setNum(1);
        }
        //grapeGridAdapter2 = new GrapeGridAdapter2(context, list, isDelete, photoConfigure.getNum());
        grapeGridAdapter2 = new MyNewAdapter(context, list, isDelete, photoConfigure.getNum());
        setAdapter(grapeGridAdapter2);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (list.get(i).equals("add")) {
                    for (int j = 0; j < photoConfigure.getList().size(); j++) {
                        if (photoConfigure.getList().get(j).equals("add")) {
                            photoConfigure.getList().remove(j);
                            break;
                        }
                    }
                    PhotoWallActivity2.openImageSelecter((Activity) GrapeGridview.this.getContext(), photoConfigure, new PhotoWallActivity2.OnHanlderResultCallback() {
                        @Override
                        public void onHanlderSuccess(List<String> resultList) {
                            list.clear();
                            list.addAll(resultList);
                            grapeGridAdapter2.notifyDataSetChanged();
                        }
                    });
                } else {
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).equals("add")) {
                            list.remove(j);
                            break;
                        }
                    }
                    PhotoPreviewActivity.openPhotoPreview((Activity) GrapeGridview.this.getContext(), i, photoConfigure.getNum(), 2, (ArrayList<String>) list, new PhotoPreviewActivity.OnHanlderResultCallback() {
                        @Override
                        public void onHanlderSuccess(List<String> resultList) {
                            list.clear();
                            list.addAll(resultList);
                            grapeGridAdapter2.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
        this.setSelector(new ColorDrawable(Color.TRANSPARENT));
//        this.setCanDrag(false);
//        this.setOnItemChangeListener(new XGridView.OnItemChangeListener() {
//            @Override
//            public void onChange(int from, int to) {
//                if (from < mList.size() && to < mList.size() && from >= 0 && to >= 0) {
//                    grapeGridAdapter2.setmPoistion(to);
//
//                    String temp = mList.get(from);
//                    //直接交互
//                    //Collections.swap(dataSourceList,from,to);
//
//                    //非直接交互 这里的处理需要注意下 排序交换
//                    if (from < to) {
//                        for (int i = from; i < to; i++) {
//                            Collections.swap(mList, i, i + 1);
//                        }
//                    } else if (from > to) {
//                        for (int i = from; i > to; i--) {
//                            Collections.swap(mList, i, i - 1);
//                        }
//                    }
//                    mList.set(to, temp);
//                }
//                grapeGridAdapter2.notifyDataSetChanged();
//            }
//        });
    }

    /**
     * @deprecated 已废弃，建议使用setWatchImg
     */
    public void setOnMyItemClickListener(Context context, final List<String> list, final int type) {
        setWatchImg(context, list, type);
    }

    /**
     * @deprecated 已废弃，建议使用setEditImg
     */
    public void setOnMyItemClickListener2(Context context, final List<String> list, boolean isDelete, final PhotoConfigure photoConfigure) {
        setEditImg(context, list, isDelete, photoConfigure);
    }

    /**
     * 获取添加移除后的数据
     *
     * @return
     */
    public List<String> getSelectList() {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).equals("add")) {
                mList.remove(i);
                break;
            }
        }
        return mList;
    }

    public void resetPosition(){
        // grapeGridAdapter2.setmPoistion(-1);
    }

}
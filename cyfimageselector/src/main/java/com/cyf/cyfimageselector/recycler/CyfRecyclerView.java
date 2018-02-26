package com.cyf.cyfimageselector.recycler;

import android.app.Service;
import android.content.Context;
import android.graphics.Rect;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.View;

import com.cyf.cyfimageselector.model.PhotoConfigure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caoyingfu on 2018/2/26.
 */

public class CyfRecyclerView extends RecyclerView {

    private List<String> mList = new ArrayList<>();
    private PostArticleImgAdapter grapeGridAdapter;
    private ItemTouchHelper itemTouchHelper;
    private OnClickListener listener;

    private boolean isClick = true;//缩略图是否可以点击（仅限查看时有效,初始化之前调用）

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    public CyfRecyclerView(Context context) {
        super(context);
    }

    public CyfRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CyfRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setClick(boolean click) {
        isClick = click;
        grapeGridAdapter.setClick(click);
    }

    public void setOnItemClickListener(OnClickListener listener) {
        this.listener = listener;
        grapeGridAdapter.setListener(listener);
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
        mList = list;
        if (type == 0 || type == 3) {
            setWatchImg(context, mList, type);
        } else if (type == 6 || type == 9) {
            setEditImg(context, mList, (6 == type), photoConfigure);
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
        grapeGridAdapter = new PostArticleImgAdapter(context, mList, type, isClick);
        initRecyclerView();
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
        photoConfigure.setList((ArrayList<String>) mList);
        if (photoConfigure.isSingle()) {
            photoConfigure.setNum(1);
        }
        grapeGridAdapter = new PostArticleImgAdapter(context, mList, isDelete, photoConfigure.getNum(), photoConfigure);
        initRecyclerView();
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

    /**
     * 初始化显示界面
     */
    private void initRecyclerView() {
        this.addItemDecoration(new MyItemDecoration());
        this.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        setAdapter(grapeGridAdapter);

        MyCallBack myCallBack = new MyCallBack(grapeGridAdapter, mList);
        itemTouchHelper = new ItemTouchHelper(myCallBack);
        itemTouchHelper.attachToRecyclerView(this);//绑定RecyclerView

        this.addOnItemTouchListener(new OnRecyclerItemClickListener(this) {

            @Override
            public void onItemLongClick(ViewHolder vh) {
                //如果item不是最后一个，则执行拖拽
                if (!mList.get(vh.getLayoutPosition()).equals("add")) {
                    ((Vibrator) getContext().getSystemService(Service.VIBRATOR_SERVICE)).vibrate(60);
                    itemTouchHelper.startDrag(vh);
                }
            }
        });
        myCallBack.setDragListener(new MyCallBack.DragListener() {
            @Override
            public void deleteState(boolean delete) {

            }

            @Override
            public void dragState(boolean start) {

            }

            @Override
            public void clearView() {

            }
        });
    }

    class MyItemDecoration extends RecyclerView.ItemDecoration {
        /**
         * * @param outRect 边界 * @param view recyclerView ItemView * @param parent recyclerView * @param state recycler 内部数据管理
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //设定底部边距为1px
            outRect.set(4, 4, 4, 4);
        }
    }

}


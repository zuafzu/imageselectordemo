package com.cyf.cyfimageselector.recycler;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.cyf.cyfimageselector.R;
import com.cyf.cyfimageselector.model.PhotoConfigure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caoyingfu on 2018/2/26.
 */

public class CyfRecyclerView extends RecyclerView {

    private PhotoConfigure photoConfigure;
    private TextView tv_delete = null;// 删除文字item，针对编辑添加有效
    private OnCyfItemClickListener listener = null;// 缩略图点击事件
    private PostArticleImgAdapter.OnUpdateData onUpdateData;// 监听视图更新，针对编辑添加有效

    private PostArticleImgAdapter grapeGridAdapter;
    private ItemTouchHelper itemTouchHelper;

    public CyfRecyclerView(Context context) {
        super(context);
    }

    public CyfRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CyfRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (photoConfigure.isCanDrag()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            int expandSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        }
    }

    public CyfRecyclerView setListener(OnCyfItemClickListener listener) {
        this.listener = listener;
        return this;
    }

    public CyfRecyclerView setTv_delete(TextView tv_delete) {
        this.tv_delete = tv_delete;
        return this;
    }

    public CyfRecyclerView setOnUpdateData(PostArticleImgAdapter.OnUpdateData onUpdateData) {
        this.onUpdateData = onUpdateData;
        return this;
    }

    public OnCyfItemClickListener getListener() {
        return listener;
    }

    public PostArticleImgAdapter.OnUpdateData getOnUpdateData() {
        return onUpdateData;
    }

    /**
     * 预览图片
     * 编辑添加移除图片
     */
    public void show(PhotoConfigure photoConfigure) {
        this.photoConfigure = photoConfigure;
        grapeGridAdapter = new PostArticleImgAdapter(getContext(), this, photoConfigure);
        if (photoConfigure.getType() == PhotoConfigure.WatchImg) {
            initRecyclerView(false);
        } else if (photoConfigure.getType() == PhotoConfigure.EditImg) {
            if (tv_delete != null) {
                tv_delete.setGravity(Gravity.CENTER);
                tv_delete.setTextColor(Color.WHITE);
                tv_delete.setTextSize(14f);
                tv_delete.setAlpha(0.9f);
                tv_delete.setPadding(320, 0, 320, 0);
            }
            initRecyclerView(photoConfigure.isCanDrag());
        }
    }

    /**
     * 初始化显示界面
     */
    private void initRecyclerView(boolean isMcanDrag) {
        this.setClipChildren(false);
        this.setClipToPadding(false);
        this.setOverScrollMode(OVER_SCROLL_NEVER);
        this.setFadingEdgeLength(0);
        this.setHasFixedSize(true);
        this.setNestedScrollingEnabled(false);
        this.addItemDecoration(new MyItemDecoration());
        // this.setLayoutManager(new StaggeredGridLayoutManager(colnum, StaggeredGridLayoutManager.VERTICAL));
        this.setLayoutManager(new MyGridLayoutManager(getContext(), photoConfigure.getColnum()));
        setAdapter(grapeGridAdapter);
        MyCallBack myCallBack = new MyCallBack(grapeGridAdapter, photoConfigure.getList(), this);
        itemTouchHelper = new ItemTouchHelper(myCallBack);
        itemTouchHelper.attachToRecyclerView(this);//绑定RecyclerView
        if (isMcanDrag) {
            this.addOnItemTouchListener(new OnRecyclerItemClickListener(this) {

                @Override
                public void onItemLongClick(ViewHolder vh) {
                    if (photoConfigure.isCanDrag()) {
                        //如果item不是最后一个，则执行拖拽
                        if (!photoConfigure.getList().get(vh.getLayoutPosition()).equals("add")) {
                            ((Vibrator) getContext().getSystemService(Service.VIBRATOR_SERVICE)).vibrate(70);
                            itemTouchHelper.startDrag(vh);
                        }
                    }
                }
            });
            myCallBack.setCanDelete(tv_delete != null);
            myCallBack.setDragListener(new MyCallBack.DragListener() {
                @Override
                public void deleteState(boolean delete) {
                    if (tv_delete != null) {
                        if (delete) {
                            Drawable drawable = getResources().getDrawable(
                                    R.mipmap.an4);
                            tv_delete.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                            tv_delete.setBackgroundResource(android.R.color.holo_red_dark);
                            tv_delete.setText("松手即可删除");
                            tv_delete.setGravity(Gravity.CENTER);
                        } else {
                            Drawable drawable = getResources().getDrawable(
                                    R.mipmap.an2);
                            tv_delete.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                            tv_delete.setText("拖到此处删除");
                            tv_delete.setBackgroundResource(android.R.color.holo_red_light);
                            tv_delete.setGravity(Gravity.CENTER);
                        }
                    }
                }

                @Override
                public void dragState(boolean start) {
                    if (tv_delete != null) {
                        if (start) {
                            tv_delete.setVisibility(VISIBLE);
                        } else {
                            tv_delete.setVisibility(GONE);
                        }
                    }
                }

                @Override
                public void clearView() {

                }
            }, tv_delete);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(photoConfigure.isAutoDelThm()){
            clearThumbnailsList();
        }
    }

    /**
     * item点击事件监听
     */
    public interface OnCyfItemClickListener {
        void onClick(int index);
    }

    /**
     * 生成缩略图的数据的监听
     */
    public interface OnCyfThumbnailsListener {
        void onStart();

        void onEnd(List<String> list, List<String> thumbnailsList);
    }

    // 获取添加移除后的数据(带add)
    public List<String> getSelectList2() {
        return photoConfigure.getList();
    }

    /**
     * 获取添加移除后的真实原始数据
     *
     * @return
     */
    public List<String> getSelectList() {
        for (int i = 0; i < photoConfigure.getList().size(); i++) {
            if (photoConfigure.getList().get(i).equals("add")) {
                photoConfigure.getList().remove(i);
                break;
            }
        }
        return photoConfigure.getList();
    }

    /**
     * 获取添加移除后的真实压缩数据
     * 配合clearThumbnailsList方法使用
     *
     * @return
     */
    public void getSelectThumbnailsList(final OnCyfThumbnailsListener listener) {
        new AsyncTask<String, Void, String>() {

            List<String> list;
            List<String> thumbnailsList;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                listener.onStart();
                list = getSelectList();
                thumbnailsList = new ArrayList<>();
            }

            @Override
            protected String doInBackground(String... strings) {
                // 生成缩略图文件夹
                String filesName = CyfRecyclerView.this.getContext().getPackageName();//文件夹名称
                // https://www.jianshu.com/p/9465170d6806
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                listener.onEnd(list, thumbnailsList);
            }
        }.execute();
    }

    /**
     * 清除生成的缩略图
     */
    public void clearThumbnailsList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String filesName = CyfRecyclerView.this.getContext().getPackageName();//文件夹名称
                // 删除缩略图文件夹

            }
        }).start();
    }

}


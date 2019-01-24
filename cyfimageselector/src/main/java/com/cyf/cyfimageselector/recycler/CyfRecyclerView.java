package com.cyf.cyfimageselector.recycler;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cyf.cyfimageselector.R;
import com.cyf.cyfimageselector.model.PhotoConfigure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by caoyingfu on 2018/2/26.
 */

public class CyfRecyclerView extends RecyclerView {

    public static boolean isShow = true;// 是否加载无缓存的图片
    private AbsListView absListView;// 优化在listView中的滑动

    private PhotoConfigure photoConfigure;
    private TextView tv_delete = null;// 删除文字item，针对编辑添加有效
    private OnCyfItemClickListener listener = null;// 缩略图点击事件
    private PostArticleImgAdapter.OnUpdateData onUpdateData;// 监听视图更新，针对编辑添加有效

    public static OnCyfThumbnailsListener onCyfThumbnailsListener;// 生成缩略图的监听
    public static boolean isOriginalShow = false;// 判断原始图开关是否显示
    public static boolean isOriginalDrawing = true;// 判断是否是原始图

    public static RecycledViewPool recycledViewPool = null;

    private PostArticleImgAdapter grapeGridAdapter;
    private ItemTouchHelper itemTouchHelper;

    private List<String> thumbnailsList = new ArrayList<>();

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
        if (photoConfigure == null || photoConfigure.isCanDrag()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            int expandSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        }
    }

    public CyfRecyclerView setOnCyfThumbnailsListener(OnCyfThumbnailsListener onCyfThumbnailsListener) {
        this.onCyfThumbnailsListener = onCyfThumbnailsListener;
        return this;
    }

    public CyfRecyclerView setListener(OnCyfItemClickListener listener) {
        this.listener = listener;
        return this;
    }

    private static int oldFirstVisibleItem = 0;
    private static long oldTime = 0;

    // 主动优化，在listView中
    public CyfRecyclerView setAbsListView(AbsListView listView) {
        this.absListView = listView;
        if (this.absListView != null) {
            this.absListView.setFriction(ViewConfiguration.getScrollFriction() * 1.5f);//滑动摩擦阻力
            this.absListView.setOnScrollListener(new AbsListView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(AbsListView absListView, int i) {
                    try {
                        // 不卡顿，但是停止滑动后显示图片
//                        if (i == SCROLL_STATE_FLING) {
//                            isShow = false;
//                            if (getContext() != null) {
//                                Glide.with(getContext()).pauseRequests();
//                            }
//                        } else if (i == SCROLL_STATE_TOUCH_SCROLL) {
////                            isShow = true;
////                            if (getContext() != null) {
////                                Glide.with(getContext()).resumeRequests();
////                            }
////                            ((BaseAdapter) absListView.getAdapter()).notifyDataSetChanged();
//                        } else if (i == SCROLL_STATE_IDLE) {
//                            isShow = true;
//                            if (getContext() != null) {
//                                Glide.with(getContext()).resumeRequests();
//                            }
//                            ((BaseAdapter) absListView.getAdapter()).notifyDataSetChanged();
//                        }
                        // 卡顿，但是一直显示图片
//                    isShow = true;
//                    if (getContext() != null) {
//                        Glide.with(getContext()).resumeRequests();
//                    }
                        // 配合onScroll方法
                        if (i == SCROLL_STATE_TOUCH_SCROLL) {
                            if (oldTime == 0) {
                                oldTime = System.currentTimeMillis();
                                isShow = false;
                                if (getContext() != null) {
                                    Glide.with(getContext()).pauseRequests();
                                }
                                Log.e("cyf111", "滑动开始");
                            }
                        } else if (i == SCROLL_STATE_IDLE) {
                            Log.e("cyf111", "滑动停止");
                            // oldTime = 0;
                            isShow = true;
                            if (getContext() != null) {
                                Glide.with(getContext()).resumeRequests();
                            }
                            if (absListView instanceof ExpandableListView) {
                                // 折叠ListView
                                ((BaseExpandableListAdapter) ((ExpandableListView) absListView).getExpandableListAdapter()).notifyDataSetChanged();
                                int num = ((ExpandableListView) absListView).getExpandableListAdapter().getGroupCount();
                                for (int j = 0; j < num; j++) {
                                    if (((ExpandableListView) absListView).isGroupExpanded(j)) {
                                        ((ExpandableListView) absListView).collapseGroup(j);
                                        ((ExpandableListView) absListView).expandGroup(j);
                                    }
                                }
                            } else {
                                // 普通ListView
                                if (absListView.getAdapter() instanceof HeaderViewListAdapter) {
                                    // 带头（下拉刷新的ListView）
                                    ((BaseAdapter) ((HeaderViewListAdapter) absListView.getAdapter()).getWrappedAdapter()).notifyDataSetChanged();
                                } else {
                                    // 普通（ListView）
                                    ((BaseAdapter) absListView.getAdapter()).notifyDataSetChanged();
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("cyf111", "Exception ： " + e.getMessage());
                    }
                }

                @Override
                public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    try {
                        if (oldTime > 0 && System.currentTimeMillis() - oldTime >= 300) {
                            oldTime = System.currentTimeMillis();
                            if (oldFirstVisibleItem == firstVisibleItem) {
                                isShow = true;
                                if (getContext() != null) {
                                    Glide.with(getContext()).resumeRequests();
                                }
                                if (absListView instanceof ExpandableListView) {
                                    // 折叠ListView
                                    ((BaseExpandableListAdapter) ((ExpandableListView) absListView).getExpandableListAdapter()).notifyDataSetChanged();
                                    // 防止滑动中不断打开关闭影响流畅度（没测试过）
//                                    int num = ((ExpandableListView) absListView).getExpandableListAdapter().getGroupCount();
//                                    for (int j = 0; j < num; j++) {
//                                        if (((ExpandableListView) absListView).isGroupExpanded(j)) {
//                                            ((ExpandableListView) absListView).collapseGroup(j);
//                                            ((ExpandableListView) absListView).expandGroup(j);
//                                        }
//                                    }
                                } else {
                                    // 普通ListView
                                    if (absListView.getAdapter() instanceof HeaderViewListAdapter) {
                                        // 带头（下拉刷新的ListView）
                                        ((BaseAdapter) ((HeaderViewListAdapter) absListView.getAdapter()).getWrappedAdapter()).notifyDataSetChanged();
                                    } else {
                                        // 普通（ListView）
                                        ((BaseAdapter) absListView.getAdapter()).notifyDataSetChanged();
                                    }
                                }
                                oldTime = 0;
                            } else {
                                oldFirstVisibleItem = firstVisibleItem;
                            }
                        }
                    } catch (Exception e) {
                        Log.e("cyf111", "Exception2 ： " + e.getMessage());
                    }
                }
            });
        }
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
        if (this.getAdapter() == null) {
            // 缩略图监听为空isOriginalDrawing=true
            isOriginalDrawing = onCyfThumbnailsListener == null;
            isOriginalShow = photoConfigure.isOriginalShow();

            this.setClipChildren(false);
            this.setClipToPadding(false);
            this.setOverScrollMode(OVER_SCROLL_NEVER);
            this.setFadingEdgeLength(0);
            this.setHasFixedSize(true);
            this.setNestedScrollingEnabled(false);
            this.addItemDecoration(new MyItemDecoration());
            if (recycledViewPool == null) {
                recycledViewPool = new RecycledViewPool();
            }
            this.setRecycledViewPool(recycledViewPool);
        }
        // this.setLayoutManager(new StaggeredGridLayoutManager(colnum, StaggeredGridLayoutManager.VERTICAL));
        if (photoConfigure.getType() == PhotoConfigure.EditImg ||
                (photoConfigure.getType() == PhotoConfigure.WatchImg && PhotoConfigure.autoColNum != photoConfigure.getColnum())) {
            this.setLayoutManager(new MyGridLayoutManager(getContext(), photoConfigure.getColnum()));
        } else {
            if (photoConfigure.getList().size() == 1) {
                this.setLayoutManager(new MyGridLayoutManager(getContext(), 1));
            } else if (photoConfigure.getList().size() == 2) {
                this.setLayoutManager(new MyGridLayoutManager(getContext(), 3));
            } else {
                this.setLayoutManager(new MyGridLayoutManager(getContext(), 3));
            }
        }
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
        onCyfThumbnailsListener = null;
        recycledViewPool = null;
        isOriginalShow = false;
        isOriginalDrawing = true;
        if (photoConfigure != null && photoConfigure.isAutoDelThm()) {
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

        void onError(Throwable e);

        void onEnd(List<String> list, List<String> thumbnailsList, boolean isOriginalDrawing);
    }

    private void thumbnailsImg(final OnCyfThumbnailsListener listener) {
        if (getSelectList().size() == 0) {
            Toast.makeText(getContext(), "请先选择图片", Toast.LENGTH_SHORT).show();
            return;
        }
        String filesName = CyfRecyclerView.this.getContext().getPackageName();//文件夹名称
        File appDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + filesName);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        Luban.with(getContext()).
                load(new File(getSelectList().get(thumbnailsList.size()))) //传人要压缩的图片
                .setTargetDir(appDir.getAbsolutePath())
                .setCompressListener(new OnCompressListener() {
                    //设置回调
                    @Override
                    public void onStart() {
                        if (thumbnailsList.size() == 0) {
                            listener.onStart();
                        }
                    }

                    @Override
                    public void onSuccess(File file) {
                        thumbnailsList.add(file.getAbsolutePath());
                        if (thumbnailsList.size() == getSelectList().size()) {
                            listener.onEnd(getSelectList(), thumbnailsList, isOriginalDrawing);
                            // 恢复显示数据的加号
                            grapeGridAdapter.setmList();
                        } else {
                            thumbnailsImg(listener);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        thumbnailsList.clear();
                        listener.onError(e);
                    }
                }).launch(); //启动压缩
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
    public void getSelectThumbnailsList() {
        thumbnailsList.clear();
        if (onCyfThumbnailsListener != null) {
            thumbnailsImg(onCyfThumbnailsListener);
        }
    }

    /**
     * 清除生成的缩略图
     */
    public void clearThumbnailsList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String filesName = CyfRecyclerView.this.getContext().getPackageName();//文件夹名称
                File appDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + filesName);
                deleteDirWihtFile(appDir);
            }
        }).start();
    }

    private static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    // 自动优化，在listView中（有待优化）
    @Override
    public void onScrolled(int dx, int dy) {
        if (absListView == null && grapeGridAdapter != null) {
            isShow = false;
            Glide.with(this).pauseRequests();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isShow = true;
                    if (getContext() != null) {
                        Glide.with(CyfRecyclerView.this).resumeRequests();
                    }
                }
            }, 500);
        }
        super.onScrolled(dx, dy);
    }
}


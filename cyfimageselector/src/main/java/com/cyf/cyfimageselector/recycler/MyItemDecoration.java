package com.cyf.cyfimageselector.recycler;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by caoyingfu on 2018/3/1.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    /**
     * * @param outRect 边界 * @param view recyclerView ItemView * @param parent recyclerView * @param state recycler 内部数据管理
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //设定底部边距为1px
        outRect.set(4, 4, 4, 4);
    }
}
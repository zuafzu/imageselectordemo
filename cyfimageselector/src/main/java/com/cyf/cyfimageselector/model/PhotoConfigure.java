package com.cyf.cyfimageselector.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 相册选择器配置器
 * Created by Administrator on 2016/8/24.
 */
public class PhotoConfigure implements Serializable {

    public final static int WatchImg = 0;// 查看模式
    public final static int EditImg = 1;// 编辑模式

    private int type = 0;//查看模式or编辑模式

    private ArrayList<String> list = new ArrayList<>();// 已选图片

    private int colnum = 3;// 每行显示数量，针对CyfRecyclerView有效
    private boolean isSave = false;// 是否有保存按钮，仅限查看时有效

    private boolean isClick = true;// 缩略图是否可以点击，针对编辑添加有效
    private boolean isSingle = false;// 是否单选，针对编辑添加有效
    private boolean isCamera = true;// 是否有相机，针对编辑添加有效
    private int num = 9;// 多选最大张数，针对编辑添加有效
    private boolean isStartWithCamera = false;// 是否直接打开相机，针对编辑添加有效
    private boolean isDelete = true;// 是否带右上角删除按钮，针对编辑添加有效
    private boolean isCanDrag = false;// 是否可以拖拽，针对编辑添加有效
    private boolean isAutoDelThm = true;// 是否自动删除生成的缩略图，针对编辑添加有效

    public PhotoConfigure() {
        super();
    }

    public static int getWatchImg() {
        return WatchImg;
    }

    public static int getEditImg() {
        return EditImg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public int getColnum() {
        return colnum;
    }

    public void setColnum(int colnum) {
        this.colnum = colnum;
    }

    public boolean isSave() {
        return isSave;
    }

    public void setSave(boolean save) {
        isSave = save;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }

    public boolean isCamera() {
        return isCamera;
    }

    public void setCamera(boolean camera) {
        isCamera = camera;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isStartWithCamera() {
        return isStartWithCamera;
    }

    public void setStartWithCamera(boolean startWithCamera) {
        isStartWithCamera = startWithCamera;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public boolean isCanDrag() {
        return isCanDrag;
    }

    public void setCanDrag(boolean canDrag) {
        isCanDrag = canDrag;
    }

    public boolean isAutoDelThm() {
        return isAutoDelThm;
    }

    public void setAutoDelThm(boolean autoDelThm) {
        isAutoDelThm = autoDelThm;
    }

    @Override
    public String toString() {
        return "PhotoConfigure{" +
                "type=" + type +
                ", list=" + list +
                ", colnum=" + colnum +
                ", isSave=" + isSave +
                ", isClick=" + isClick +
                ", isSingle=" + isSingle +
                ", isCamera=" + isCamera +
                ", num=" + num +
                ", isStartWithCamera=" + isStartWithCamera +
                ", isDelete=" + isDelete +
                ", isCanDrag=" + isCanDrag +
                ", isAutoDelThm=" + isAutoDelThm +
                '}';
    }
}
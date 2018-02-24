package com.cyf.cyfimageselector.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 相册选择器配置器
 * Created by Administrator on 2016/8/24.
 */
public class PhotoConfigure implements Serializable {
    private boolean isSingle = false;// 是否单选
    private ArrayList<String> list = new ArrayList<>();// 已选图片
    private boolean isCamera = true;// 是否有相机
    private int num = 9;// 多选张数
    private boolean isStartWithCamera = false;// 是否直接打开相机

    public PhotoConfigure() {
        super();
    }

    public PhotoConfigure(boolean isSingle, ArrayList<String> list, boolean isCamera, int num, boolean isStartWithCamera) {
        this.isSingle = isSingle;
        this.list = list;
        this.isCamera = isCamera;
        this.num = num;
        this.isStartWithCamera = isStartWithCamera;
    }

    public boolean isStartWithCamera() {
        return isStartWithCamera;
    }

    public void setStartWithCamera(boolean startWithCamera) {
        isStartWithCamera = startWithCamera;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
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

    @Override
    public String toString() {
        return "PhotoConfigure{" +
                "isSingle=" + isSingle +
                ", list=" + list +
                ", isCamera=" + isCamera +
                ", num=" + num +
                ", isStartWithCamera=" + isStartWithCamera +
                '}';
    }
}
package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.BBSCategory;

import java.util.ArrayList;

/**
 * Created by yuandi on 2016/6/16.
 */
public class BbsCategoryListModel extends Entity {

    private int code;
    private String uuid;
    private ArrayList<BBSCategory> value;
    private boolean success;

    public void setCode(int code) {
        this.code = code;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setValue(ArrayList<BBSCategory> value) {
        this.value = value;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public String getUuid() {
        return uuid;
    }

    public ArrayList<BBSCategory> getValue() {
        return value;
    }

    public boolean isSuccess() {
        return success;
    }
}
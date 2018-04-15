package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.CategoryLeftBean;

import java.util.List;

/**
 * Created by ning on 2016/3/24.
 */
public class FindCategoryModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private List<CategoryLeftBean> value;

    public void setCode(int code) {
        this.code = code;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setValue(List<CategoryLeftBean> value) {
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getUuid() {
        return uuid;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<CategoryLeftBean> getValue() {
        return value;
    }

}

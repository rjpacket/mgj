package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.SecondHandCategoryBean;

import java.util.List;

/**
 * Created by rjp on 2016/7/8.
 * Email:rjpgoodjob@gmail.com
 */
public class SecondHandCategoryModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;

    private List<SecondHandCategoryBean> value;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getServertime() {
        return servertime;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }

    public List<SecondHandCategoryBean> getValue() {
        return value;
    }

    public void setValue(List<SecondHandCategoryBean> value) {
        this.value = value;
    }
}

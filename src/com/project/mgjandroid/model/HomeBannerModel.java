package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.Banner;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuandi on 2016/3/24.
 */
public class HomeBannerModel implements Serializable {

    private int code;
    private String uuid;
    private List<Banner> value;
    private boolean success;

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

    public List<Banner> getValue() {
        return value;
    }

    public void setValue(List<Banner> value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

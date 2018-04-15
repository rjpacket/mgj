package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.FulltimeCategoryBean;

import java.util.List;

/**
 * Created by rjp on 2016/6/21.
 * Email:rjpgoodjob@gmail.com
 */
public class FulltimeListModel extends Entity {
    private int code;
    private String uuid;
    private boolean success;
    private String servertime;

    private List<FulltimeCategoryBean> value;

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

    public List<FulltimeCategoryBean> getValue() {
        return value;
    }

    public void setValue(List<FulltimeCategoryBean> value) {
        this.value = value;
    }

}

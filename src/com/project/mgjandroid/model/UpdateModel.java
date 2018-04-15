package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.AppVersion;

/**
 * Created by rjp on 2016/6/17.
 * Email:rjpgoodjob@gmail.com
 */
public class UpdateModel extends Entity {

    private int code;
    private String uuid;
    private AppVersion value;
    private boolean success;
    private String servertime;

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

    public AppVersion getValue() {
        return value;
    }

    public void setValue(AppVersion value) {
        this.value = value;
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
}

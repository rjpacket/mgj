package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.SecondhandInformation;

/**
 * Created by yuandi on 2016/7/11.
 */
public class PublishSecondHandInfoModel extends Entity {

    private int code;
    private String uuid;

    private SecondhandInformation value;
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

    public SecondhandInformation getValue() {
        return value;
    }

    public void setValue(SecondhandInformation value) {
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

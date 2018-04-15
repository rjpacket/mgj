package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.RedBag;

import java.util.Date;

/**
 * Created by yuandi on 2016/7/4.
 *
 */
public class MerchantRedBagModel extends Entity {

    private int code;
    private String uuid;
    private RedBag value;
    private boolean success;
    private Date servertime;

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

    public RedBag getValue() {
        return value;
    }

    public void setValue(RedBag value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Date getServertime() {
        return servertime;
    }

    public void setServertime(Date servertime) {
        this.servertime = servertime;
    }
}

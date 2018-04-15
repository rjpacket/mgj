package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.UserFavorites;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yuandi on 2016/4/27.
 */
public class UserFavoritesModel implements Serializable{

    private int code;

    private String uuid;

    private ArrayList<UserFavorites> value;

    private boolean success;

    private Date createTime;

    private Date modifyTime;

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

    public ArrayList<UserFavorites> getValue() {
        return value;
    }

    public void setValue(ArrayList<UserFavorites> value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getServertime() {
        return servertime;
    }

    public void setServertime(Date servertime) {
        this.servertime = servertime;
    }
}

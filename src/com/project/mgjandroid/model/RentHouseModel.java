package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.HouseLeaseInformation;

import java.util.List;

/**
 * Created by rjp on 2016/7/20.
 * Email:rjpgoodjob@gmail.com
 */
public class RentHouseModel extends Entity {
    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private List<HouseLeaseInformation> value;

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

    public List<HouseLeaseInformation> getValue() {
        return value;
    }

    public void setValue(List<HouseLeaseInformation> value) {
        this.value = value;
    }
}

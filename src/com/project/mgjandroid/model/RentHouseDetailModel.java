package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.HouseLeaseInformation;

/**
 * Created by rjp on 2016/7/20.
 * Email:rjpgoodjob@gmail.com
 */
public class RentHouseDetailModel extends Entity{
    private int code;
    private String uuid;
    private HouseLeaseInformation value;
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

    public HouseLeaseInformation getValue() {
        return value;
    }

    public void setValue(HouseLeaseInformation value) {
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

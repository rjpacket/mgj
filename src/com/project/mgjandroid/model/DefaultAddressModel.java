package com.project.mgjandroid.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yuandi on 2016/5/3.
 */
public class DefaultAddressModel extends Entity{

    private int code;

    private String uuid;

    private boolean success;

    private String servertime;

    private ArrayList<DefaultAddress> value;

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

    public ArrayList<DefaultAddress> getValue() {
        return value;
    }

    public void setValue(ArrayList<DefaultAddress> value) {
        this.value = value;
    }

    public static class DefaultAddress extends Entity{

        private Long id;

        private String address;

        private double latitude;

        private double longitude;

        private boolean hasDel;

        private Date createTime;

        private Date modifyTime;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public boolean isHasDel() {
            return hasDel;
        }

        public void setHasDel(boolean hasDel) {
            this.hasDel = hasDel;
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
    }
}

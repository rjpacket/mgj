package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.UserAddress;

import java.io.Serializable;

/**
 * Created by thinkpad on 2016/3/13.
 */
public class EditAddressModel implements Serializable {

    private static final long serialVersionUID = 111L;
    private int code;
    private String uuid;
    private UserAddress value;

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

    public UserAddress getValue() {
        return value;
    }

    public void setValue(UserAddress value) {
        this.value = value;
    }
}
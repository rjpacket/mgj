package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.UserAddress;

import java.io.Serializable;
import java.util.List;

public class AddressManageModel implements Serializable {
    private static final long serialVersionUID = 1234L;
    private int code;
    private String uuid;
    private List<UserAddress> value;

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

    public List<UserAddress> getValue() {
        return value;
    }

    public void setValue(List<UserAddress> value) {
        this.value = value;
    }
}

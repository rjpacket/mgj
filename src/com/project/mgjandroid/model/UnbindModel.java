package com.project.mgjandroid.model;

/**
 * Created by Cjh on 2016/3/26.
 */
public class UnbindModel extends Entity{

    /*
    "code": 0,
    "uuid": "867994023030857",
    "value": 1,
    "success": true
    */
    private int code;
    private String uuid;
    private int value;
    private boolean success;

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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

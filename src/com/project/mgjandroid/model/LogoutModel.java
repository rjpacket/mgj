package com.project.mgjandroid.model;

/**
 * Created by ning on 2016/3/21.
 */
public class LogoutModel extends Entity {

    /**
     * code : 0
     * uuid : A00000552D1815
     * value : 2
     * success : true
     */

    private int code;
    private String uuid;
    private int value;
    private boolean success;

    public void setCode(int code) {
        this.code = code;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public String getUuid() {
        return uuid;
    }

    public int getValue() {
        return value;
    }

    public boolean isSuccess() {
        return success;
    }
}

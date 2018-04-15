package com.project.mgjandroid.model;

/**
 * Created by User_Cjh on 2016/3/30.
 */
public class ErrorModel extends Entity{
    /**
     * code : 502
     * uuid : null
     * value : 已被其他用户绑定
     * success : false
     */

    private int code;
    private Object uuid;
    private Object value;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getUuid() {
        return uuid;
    }

    public void setUuid(Object uuid) {
        this.uuid = uuid;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

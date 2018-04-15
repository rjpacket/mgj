package com.project.mgjandroid.model;

/**
 * Created by ning on 2016/3/12.
 */
public class SendSmsModel extends Entity {

    /**
     * code : 501
     * uuid : null
     * value : 手机号码格式错误
     * success : false
     */

    private int code;
    private String uuid;
    private String value;
    private boolean success;
    private String servertime;

    public void setCode(int code) {
        this.code = code;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setValue(String value) {
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

    public String getValue() {
        return value;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getServertime() {
        return servertime;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }
}

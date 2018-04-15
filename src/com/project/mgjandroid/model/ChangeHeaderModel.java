package com.project.mgjandroid.model;

/**
 * Created by ning on 2016/3/16.
 */
public class ChangeHeaderModel extends Entity {

    /**
     * code : 0
     * uuid : 865543020573963
     * value : /storage/sdcard0/maguanjia/image20160316083525281.jpg
     * success : true
     */

    private int code;
    private String uuid;
    private String value;
    private boolean success;

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
}

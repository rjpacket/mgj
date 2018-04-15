package com.project.mgjandroid.model;

/**
 * Created by ning on 2016/5/27.
 */
public class WithdrawLimit extends Entity {
    private int code;
    private String uuid;
    /**
     * count : 3
     * minAmt : 1
     */

    private ValueEntity value;
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

    public ValueEntity getValue() {
        return value;
    }

    public void setValue(ValueEntity value) {
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

    public static class ValueEntity {
        private int count;
        private int minAmt;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getMinAmt() {
            return minAmt;
        }

        public void setMinAmt(int minAmt) {
            this.minAmt = minAmt;
        }
    }
}

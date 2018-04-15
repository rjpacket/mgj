package com.project.mgjandroid.model;

import java.math.BigDecimal;

/**
 * Created by ning on 2016/3/16.
 */
public class UserAccountModel extends Entity {

    /**
     * code : 0
     * uuid : 865543020573963
     * value : {"id":2,"createTime":"2016-03-16 16:44:44","modifyTime":"2016-03-16 16:44:44","balance":4.3,"drawBalance":0}
     * success : true
     */

    private int code;
    private String uuid;
    /**
     * id : 2
     * createTime : 2016-03-16 16:44:44
     * modifyTime : 2016-03-16 16:44:44
     * balance : 4.3
     * drawBalance : 0.0
     */

    private ValueEntity value;
    private boolean success;

    public void setCode(int code) {
        this.code = code;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setValue(ValueEntity value) {
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

    public ValueEntity getValue() {
        return value;
    }

    public boolean isSuccess() {
        return success;
    }

    public static class ValueEntity {
        private int id;
        private String createTime;
        private String modifyTime;
        private BigDecimal balance;
        private int userBankCount;
        private int redBagCount;

        public void setId(int id) {
            this.id = id;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public void setBalance(BigDecimal balance) {
            this.balance = balance;
        }

        public int getId() {
            return id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public BigDecimal getBalance() {
            return balance;
        }

        public int getUserBankCount() {
            return userBankCount;
        }

        public void setUserBankCount(int userBankCount) {
            this.userBankCount = userBankCount;
        }

        public int getRedBagCount() {
            return redBagCount;
        }

        public void setRedBagCount(int redBagCount) {
            this.redBagCount = redBagCount;
        }
    }
}

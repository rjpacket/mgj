package com.project.mgjandroid.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by User_Cjh on 2016/4/7.
 */
public class AccountDetailsModel extends Entity{
    /**
     * code : 0
     * uuid : 867994023030857
     * value : [{"id":4,"createTime":"2016-04-07 11:38:40","modifyTime":"2016-04-07 11:38:40","userId":12,"amt":29.2,"type":1,"typeName":"消费","signSymbol":-1,"memo":"消费","balance":1000,"orderId":"1604070000007807","transactionId":null}]
     * success : true
     * servertime : 2016-04-07 11:39:17
     */

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    /**
     * id : 4
     * createTime : 2016-04-07 11:38:40
     * modifyTime : 2016-04-07 11:38:40
     * userId : 12
     * amt : 29.2
     * type : 1
     * typeName : 消费
     * signSymbol : -1
     * memo : 消费
     * balance : 1000.0
     * orderId : 1604070000007807
     * transactionId : null
     */

    private List<ValueEntity> value;

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

    public List<ValueEntity> getValue() {
        return value;
    }

    public void setValue(List<ValueEntity> value) {
        this.value = value;
    }

    public static class ValueEntity extends Entity{
        private int id;
        private String createTime;
        private String modifyTime;
        private int userId;
        private BigDecimal amt;
        private int type;
        private String typeName;
        private int signSymbol;
        private String memo;
        private BigDecimal balance;
        private String orderId;
        private String transactionId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public BigDecimal getAmt() {
            return amt;
        }

        public void setAmt(BigDecimal amt) {
            this.amt = amt;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getSignSymbol() {
            return signSymbol;
        }

        public void setSignSymbol(int signSymbol) {
            this.signSymbol = signSymbol;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public BigDecimal getBalance() {
            return balance;
        }

        public void setBalance(BigDecimal balance) {
            this.balance = balance;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }
    }
}

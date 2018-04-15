package com.project.mgjandroid.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ning on 2016/3/28.
 */
public class PayWaysModel extends Entity {

    /**
     * code : 0
     * uuid : A00000552D1815
     * value : {"userBalance":4.17,"chargeTypes":[{"channel":"alipay","name":"支付宝手机支付","tip":""},{"channel":"wx","name":"微信支付","tip":""}],"totalPrice":35}
     * success : true
     * servertime : 2016-04-07 14:07:59
     */

    private int code;
    private String uuid;
    /**
     * userBalance : 4.17
     * chargeTypes : [{"channel":"alipay","name":"支付宝手机支付","tip":""},{"channel":"wx","name":"微信支付","tip":""}]
     * totalPrice : 35.0
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

    public static class ValueEntity extends Entity{
        private BigDecimal userBalance;
        private BigDecimal totalPrice;
        /**
         * channel : alipay
         * name : 支付宝手机支付
         * tip :
         */

        private List<ChargeTypesEntity> chargeTypes;

        public BigDecimal getUserBalance() {
            return userBalance;
        }

        public void setUserBalance(BigDecimal userBalance) {
            this.userBalance = userBalance;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }

        public List<ChargeTypesEntity> getChargeTypes() {
            return chargeTypes;
        }

        public void setChargeTypes(List<ChargeTypesEntity> chargeTypes) {
            this.chargeTypes = chargeTypes;
        }

        public static class ChargeTypesEntity extends Entity{
            private String channel;
            private String name;
            private String tip;

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTip() {
                return tip;
            }

            public void setTip(String tip) {
                this.tip = tip;
            }
        }
    }
}

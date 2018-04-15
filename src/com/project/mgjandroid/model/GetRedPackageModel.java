package com.project.mgjandroid.model;

import java.math.BigDecimal;

/**
 * Created by rjp on 2016/7/4.
 * Email:rjpgoodjob@gmail.com
 */
public class GetRedPackageModel extends Entity {

    private int code;
    private String uuid;

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
        private int id;
        private String createTime;
        private String modifyTime;
        private int userId;
        private int promotionId;
        private int activityId;
        private String fromOrderId;
        private Object useOrderId;
        private String name;
        private BigDecimal amt;
        private int restrictAmt;
        private int promotionType;
        private Object redeemCode;
        private int type;
        private int couponType;
        private int merchantId;
        private Object agentId;
        private long expirationTime;
        private int isRestrictTime;
        private Object restrictTime;
        private Object usingTime;
        private Object sendBackTime;
        private int isCumulate;
        private int isExclusive;
        private Object mobile;
        private int status;
        private Object merchantName;
        private Object merchantLogo;

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

        public int getPromotionId() {
            return promotionId;
        }

        public void setPromotionId(int promotionId) {
            this.promotionId = promotionId;
        }

        public int getActivityId() {
            return activityId;
        }

        public void setActivityId(int activityId) {
            this.activityId = activityId;
        }

        public String getFromOrderId() {
            return fromOrderId;
        }

        public void setFromOrderId(String fromOrderId) {
            this.fromOrderId = fromOrderId;
        }

        public Object getUseOrderId() {
            return useOrderId;
        }

        public void setUseOrderId(Object useOrderId) {
            this.useOrderId = useOrderId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getAmt() {
            return amt;
        }

        public void setAmt(BigDecimal amt) {
            this.amt = amt;
        }

        public int getRestrictAmt() {
            return restrictAmt;
        }

        public void setRestrictAmt(int restrictAmt) {
            this.restrictAmt = restrictAmt;
        }

        public int getPromotionType() {
            return promotionType;
        }

        public void setPromotionType(int promotionType) {
            this.promotionType = promotionType;
        }

        public Object getRedeemCode() {
            return redeemCode;
        }

        public void setRedeemCode(Object redeemCode) {
            this.redeemCode = redeemCode;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getCouponType() {
            return couponType;
        }

        public void setCouponType(int couponType) {
            this.couponType = couponType;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public Object getAgentId() {
            return agentId;
        }

        public void setAgentId(Object agentId) {
            this.agentId = agentId;
        }

        public long getExpirationTime() {
            return expirationTime;
        }

        public void setExpirationTime(long expirationTime) {
            this.expirationTime = expirationTime;
        }

        public int getIsRestrictTime() {
            return isRestrictTime;
        }

        public void setIsRestrictTime(int isRestrictTime) {
            this.isRestrictTime = isRestrictTime;
        }

        public Object getRestrictTime() {
            return restrictTime;
        }

        public void setRestrictTime(Object restrictTime) {
            this.restrictTime = restrictTime;
        }

        public Object getUsingTime() {
            return usingTime;
        }

        public void setUsingTime(Object usingTime) {
            this.usingTime = usingTime;
        }

        public Object getSendBackTime() {
            return sendBackTime;
        }

        public void setSendBackTime(Object sendBackTime) {
            this.sendBackTime = sendBackTime;
        }

        public int getIsCumulate() {
            return isCumulate;
        }

        public void setIsCumulate(int isCumulate) {
            this.isCumulate = isCumulate;
        }

        public int getIsExclusive() {
            return isExclusive;
        }

        public void setIsExclusive(int isExclusive) {
            this.isExclusive = isExclusive;
        }

        public Object getMobile() {
            return mobile;
        }

        public void setMobile(Object mobile) {
            this.mobile = mobile;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(Object merchantName) {
            this.merchantName = merchantName;
        }

        public Object getMerchantLogo() {
            return merchantLogo;
        }

        public void setMerchantLogo(Object merchantLogo) {
            this.merchantLogo = merchantLogo;
        }
    }
}

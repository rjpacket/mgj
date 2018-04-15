package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuandi on 2016/5/24.
 */
public class RedBag extends Entity {

    private Long id;
    /** 用户编号 **/
    private Long userId;
    /** 活动类型id **/
    private Long promotionId;
    /** 活动编号 **/
    private Long activityId;
    /** 来源订单编号 **/
    private String fromOrderId;
    /** 使用订单编号 **/
    private String useOrderId;
    /** 名称 **/
    private String name;
    /** 红包金额 **/
    private BigDecimal amt;
    /** 满减限制金额 **/
    private BigDecimal restrictAmt;
    /** 促销类型 {PromotionType} **/
    private int promotionType;
    /** 兑换码 **/
    private String redeemCode;
    /** 类型 {RedBagType} **/
    private int type;
    /** 优惠类型 {RedBagCouponType} **/
    private int couponType;
    /** 商家编号 **/
    private Long merchantId;
    /** 代理商编号 **/
    private Long agentId;
    /** 过期时间 **/
    private Long expirationTime;
    /** 是否限制使用时间0:否,1:是 */
    private int isRestrictTime;
    /** 限制使用时间 */
    private String restrictTime;
    /** 使用时间 */
    private Date usingTime;
    /** 退还时间 */
    private Date sendBackTime;
    /** 是否叠加 0:否,1:是 **/
    private int isCumulate;
    /** 是否排它0:否;1:排它（不能与其他活动一起使用） **/
    private int isExclusive;
    /** 手机号 **/
    private String mobile;
    /** 状态 0:未使用,1:已使用 **/
    private int status;

    private String merchantName;

    private String merchantLogo;

    private Date createTime;

    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getFromOrderId() {
        return fromOrderId;
    }

    public void setFromOrderId(String fromOrderId) {
        this.fromOrderId = fromOrderId;
    }

    public String getUseOrderId() {
        return useOrderId;
    }

    public void setUseOrderId(String useOrderId) {
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

    public BigDecimal getRestrictAmt() {
        return restrictAmt;
    }

    public void setRestrictAmt(BigDecimal restrictAmt) {
        this.restrictAmt = restrictAmt;
    }

    public int getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(int promotionType) {
        this.promotionType = promotionType;
    }

    public String getRedeemCode() {
        return redeemCode;
    }

    public void setRedeemCode(String redeemCode) {
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

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public int getIsRestrictTime() {
        return isRestrictTime;
    }

    public void setIsRestrictTime(int isRestrictTime) {
        this.isRestrictTime = isRestrictTime;
    }

    public String getRestrictTime() {
        return restrictTime;
    }

    public void setRestrictTime(String restrictTime) {
        this.restrictTime = restrictTime;
    }

    public Date getUsingTime() {
        return usingTime;
    }

    public void setUsingTime(Date usingTime) {
        this.usingTime = usingTime;
    }

    public Date getSendBackTime() {
        return sendBackTime;
    }

    public void setSendBackTime(Date sendBackTime) {
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantLogo() {
        return merchantLogo;
    }

    public void setMerchantLogo(String merchantLogo) {
        this.merchantLogo = merchantLogo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}

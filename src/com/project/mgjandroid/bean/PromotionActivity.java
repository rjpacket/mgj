package com.project.mgjandroid.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by yuandi on 2016/5/20.
 */
public class PromotionActivity implements Serializable {

    private Long activityId;

    private Long promotionId;

    private Long agentId;

    private String promoName;

    private Integer promoType;

    private String rule;

    private BigDecimal discountAmt;

    private String promoTip;

    private String promoImg;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public Integer getPromoType() {
        return promoType;
    }

    public void setPromoType(Integer promoType) {
        this.promoType = promoType;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public BigDecimal getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(BigDecimal discountAmt) {
        this.discountAmt = discountAmt;
    }

    public String getPromoTip() {
        return promoTip;
    }

    public void setPromoTip(String promoTip) {
        this.promoTip = promoTip;
    }

    public String getPromoImg() {
        return promoImg;
    }

    public void setPromoImg(String promoImg) {
        this.promoImg = promoImg;
    }
}

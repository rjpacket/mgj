package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;

/**
 * Created by yuandi on 2016/7/5.
 *
 */
public class MerchantRedBag extends Entity {

    /** 编号 **/
    private Long id;
    /** 名称 **/
    private String name;
    /** 红包金额 **/
    private BigDecimal amt;
    /** 促销类型 {PromotionType} **/
    private int promotionType;
    /** 使用规则 **/
    private String useRule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(int promotionType) {
        this.promotionType = promotionType;
    }

    public String getUseRule() {
        return useRule;
    }

    public void setUseRule(String useRule) {
        this.useRule = useRule;
    }

}

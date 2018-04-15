package com.project.mgjandroid.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by yuandi on 2016/3/9.
 */
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /** 订单编号 */
    private Long orderId;
    /** 商品编号 */
    private Long goodsId;
    /** 名称 */
    private String name;
    /** 描述 */
    private String description;
    /** 商品单位 */
    private String goodsUnit;
    /** 购买商品数量 */
    private Integer goodsQuantity;
    /** 商品价格 */
    private BigDecimal goodsPrice = BigDecimal.ZERO;
    /** 最终价格 */
    private BigDecimal finalPrice = BigDecimal.ZERO;
    /** 是否促销 */
    private int isPromo;
    /** 包装袋价格 */
    private BigDecimal packPrice = BigDecimal.ZERO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public Integer getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(Integer goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public int getIsPromo() {
        return isPromo;
    }

    public void setIsPromo(int isPromo) {
        this.isPromo = isPromo;
    }

    public BigDecimal getPackPrice() {
        return packPrice;
    }

    public void setPackPrice(BigDecimal packPrice) {
        this.packPrice = packPrice;
    }

}

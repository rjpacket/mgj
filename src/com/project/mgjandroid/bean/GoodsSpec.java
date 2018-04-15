package com.project.mgjandroid.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by yuandi on 2016/3/9.
 */
public class GoodsSpec implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /** 商品编号 */
    private Long goodsId;
    /** 规格名称 */
    private String spec = "";
    /** 商品价格 */
    private BigDecimal price = BigDecimal.ZERO;
    /** 包装袋数量 */
    private Integer boxNum = 0;
    /** 包装袋价格 */
    private BigDecimal boxPrice = BigDecimal.ZERO;
    /** 每单最小数量 */
    private Integer minOrderNum;
    /** 每单限量数 */
    private Integer orderLimit;
    /** 购买数量 */
    private int buyCount;
    /** 库存 */
    private Integer stock;
    /** 是否删除 */
    private int hasDel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(Integer boxNum) {
        this.boxNum = boxNum;
    }

    public BigDecimal getBoxPrice() {
        return boxPrice;
    }

    public void setBoxPrice(BigDecimal boxPrice) {
        this.boxPrice = boxPrice;
    }

    public Integer getMinOrderNum() {
        return minOrderNum;
    }

    public void setMinOrderNum(Integer minOrderNum) {
        this.minOrderNum = minOrderNum;
    }

    public Integer getOrderLimit() {
        return orderLimit;
    }

    public void setOrderLimit(Integer orderLimit) {
        this.orderLimit = orderLimit;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

}

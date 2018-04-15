package com.project.mgjandroid.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yuandi on 2016/3/9.
 */
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /** 商家编号 */
    private Long merchantId;
    /** 分类编号 */
    private Long categoryId;
    /** 名称 */
    private String name;
    /** 描述 */
    private String description;
    /** 商品单位 */
    private String goodsUnit;

    private String imgs;
    /** 商品属性 */
    private String attributes;
    /** 排序号 */
    private Integer sortNo;
    private String month;
    private int monthSaled;
    /** 总售 */
    private int totalSaled;
    /** 赞数量 */
    private int praiseNum;
    /** 点评数 */
    private int commentNum;
    /** 点评分 */
    private BigDecimal commentScore;
    /** 售卖状态 */
    private int status;
    /** 是否删除 */
    private int hasDel;
    /** 删除时间 */
    private Date delTime;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date modifyTime;

    private List<GoodsSpec> goodsSpecList;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public int getTotalSaled() {
        return totalSaled;
    }

    public void setTotalSaled(int totalSaled) {
        this.totalSaled = totalSaled;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public BigDecimal getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(BigDecimal commentScore) {
        this.commentScore = commentScore;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
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

    public Date getDelTime() {
        return delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    public List<GoodsSpec> getGoodsSpecList() {
        return goodsSpecList;
    }

    public void setGoodsSpecList(List<GoodsSpec> goodsSpecList) {
        this.goodsSpecList = goodsSpecList;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Goods){
            Goods p = (Goods) o;
            if(p.getId() == getId()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    /** 月售 */
    public int getMonthSaled() {
        return monthSaled;
    }

    public void setMonthSaled(int monthSaled) {
        this.monthSaled = monthSaled;
    }
}


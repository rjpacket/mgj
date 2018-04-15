package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.util.Date;

/**
 * Created by yuandi on 2016/7/20.
 *
 */
public class HouseLeaseCategory extends Entity {

    private Long id;
    /** 分类名称 */
    private String name;

    private Long houseLeaseServiceCategoryId;
    /** 排序编号，默认为0 */
    private int sortNo;

    private int hasDel;
    /** 是否热门0:否；1：是 */
    private int isHot;
    /** 设置热门时间 */
    private Date isHotTime;

    private String houseLeaseServiceCategoryName;

    private boolean isCheck;

    private Date createTime;

    private Date modifyTime;

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

    public Long getHouseLeaseServiceCategoryId() {
        return houseLeaseServiceCategoryId;
    }

    public void setHouseLeaseServiceCategoryId(Long houseLeaseServiceCategoryId) {
        this.houseLeaseServiceCategoryId = houseLeaseServiceCategoryId;
    }

    public String getHouseLeaseServiceCategoryName() {
        return houseLeaseServiceCategoryName;
    }

    public void setHouseLeaseServiceCategoryName(String houseLeaseServiceCategoryName) {
        this.houseLeaseServiceCategoryName = houseLeaseServiceCategoryName;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public Date getIsHotTime() {
        return isHotTime;
    }

    public void setIsHotTime(Date isHotTime) {
        this.isHotTime = isHotTime;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
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

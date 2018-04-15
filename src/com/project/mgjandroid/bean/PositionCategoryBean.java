package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.util.Date;

/**
 * Created by rjp on 2016/6/21.
 * Email:rjpgoodjob@gmail.com
 */
public class PositionCategoryBean extends Entity {

    private long id;
    private Date createTime;
    private Date modifyTime;
    private String name;
    private int positionServiceCategoryId;
    private int sortNo;
    private int hasDel;
    private int isHot;
    private Date isHotTime;

    private boolean isCheck;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPositionServiceCategoryId() {
        return positionServiceCategoryId;
    }

    public void setPositionServiceCategoryId(int positionServiceCategoryId) {
        this.positionServiceCategoryId = positionServiceCategoryId;
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

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
}

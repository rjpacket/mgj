package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

/**
 * Created by rjp on 2016/7/8.
 * Email:rjpgoodjob@gmail.com
 */
public class SecondHandCategoryBean extends Entity {

    private long id;
    private String createTime;
    private String modifyTime;
    private String name;
    private long secondhandServiceCategoryId;
    private int sortNo;
    private int hasDel;
    private int isHot;
    private String isHotTime;
    private Object secondhandServiceCategoryName;
    private boolean isCheck;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSecondhandServiceCategoryId() {
        return secondhandServiceCategoryId;
    }

    public void setSecondhandServiceCategoryId(long secondhandServiceCategoryId) {
        this.secondhandServiceCategoryId = secondhandServiceCategoryId;
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

    public String getIsHotTime() {
        return isHotTime;
    }

    public void setIsHotTime(String isHotTime) {
        this.isHotTime = isHotTime;
    }

    public Object getSecondhandServiceCategoryName() {
        return secondhandServiceCategoryName;
    }

    public void setSecondhandServiceCategoryName(Object secondhandServiceCategoryName) {
        this.secondhandServiceCategoryName = secondhandServiceCategoryName;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}

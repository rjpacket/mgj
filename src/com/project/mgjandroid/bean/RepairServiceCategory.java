package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.util.Date;
import java.util.List;

/**
 * Created by yuandi on 2016/7/25.
 *
 */
public class RepairServiceCategory extends Entity{

    private Long id;

    private Date createTime;

    private Date modifyTime;
    /** 分类名称 */
    private String name;
    /** 排序编号，默认为0 */
    private int sortNo;

    private int hasDel;

    private List<RepairCategory> repairCategoryList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<RepairCategory> getRepairCategoryList() {
        return repairCategoryList;
    }

    public void setRepairCategoryList(List<RepairCategory> repairCategoryList) {
        this.repairCategoryList = repairCategoryList;
    }
}


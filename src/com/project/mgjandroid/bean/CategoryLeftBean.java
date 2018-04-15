package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.util.List;

/**
 * Created by ning on 2016/5/20.
 */
public class CategoryLeftBean extends Entity{
    private int id;
    private String createTime;
    private String modifyTime;
    private String name;
    private int parentId;
    private String icon;
    private int level;
    private int sortNo;
    private int hasDel;
    private List<CategoryRightBean> childTagCategoryList;
    private boolean isChecked;

    public void setId(int id) {
        this.id = id;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public void setChildTagCategoryList(List<CategoryRightBean> childTagCategoryList) {
        this.childTagCategoryList = childTagCategoryList;
    }

    public int getId() {
        return id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public String getName() {
        return name;
    }

    public int getParentId() {
        return parentId;
    }

    public String getIcon() {
        return icon;
    }

    public int getLevel() {
        return level;
    }

    public int getSortNo() {
        return sortNo;
    }

    public int getHasDel() {
        return hasDel;
    }

    public List<CategoryRightBean> getChildTagCategoryList() {
        return childTagCategoryList;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}

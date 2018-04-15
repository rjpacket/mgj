package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.util.Date;
import java.util.List;

/**
 * Created by thinkpad on 2016/7/9.
 */
public class SecondhandServiceCategory extends Entity{

    private Long id;
    /** 分类名称 */
    private String name;
    /** 排序编号，默认为0 */
    private int sortNo;

    private int hasDel;

    private List<SecondHandCategoryBean> secondhandCategoryList;

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

    public List<SecondHandCategoryBean> getSecondhandCategoryList() {
        return secondhandCategoryList;
    }

    public void setSecondhandCategoryList(List<SecondHandCategoryBean> secondhandCategoryList) {
        this.secondhandCategoryList = secondhandCategoryList;
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

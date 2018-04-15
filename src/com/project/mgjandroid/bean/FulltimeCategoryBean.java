package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.util.List;

/**
 * Created by rjp on 2016/6/21.
 * Email:rjpgoodjob@gmail.com
 */
public class FulltimeCategoryBean extends Entity {

    private int id;
    private String createTime;
    private String modifyTime;
    private String name;
    private int sortNo;
    private int hasDel;
    private int agentId;

    private List<PositionCategoryBean> positionCategoryList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public List<PositionCategoryBean> getPositionCategoryList() {
        return positionCategoryList;
    }

    public void setPositionCategoryList(List<PositionCategoryBean> positionCategoryList) {
        this.positionCategoryList = positionCategoryList;
    }

}

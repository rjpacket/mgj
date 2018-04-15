package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.util.List;

/**
 * Created by rjp on 2016/6/23.
 * Email:rjpgoodjob@gmail.com
 */
public class ProvinceBean extends Entity {

    private String baiduCityCode;
    private int id;
    private int parentId;
    private String name;

    private List<CityBean> childCityList;

    public String getBaiduCityCode() {
        return baiduCityCode;
    }

    public void setBaiduCityCode(String baiduCityCode) {
        this.baiduCityCode = baiduCityCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getChildCityList() {
        return childCityList;
    }

    public void setChildCityList(List<CityBean> childCityList) {
        this.childCityList = childCityList;
    }
}

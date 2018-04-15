package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

/**
 * Created by rjp on 2016/6/24.
 * Email:rjpgoodjob@gmail.com
 */
public class HotCityBean extends Entity {

    private long id;
    private String createTime;
    private String modifyTime;
    private int level;
    private long province;
    private long city;
    private long district;
    private String cityName;
    private int sortNo;
    private int hasDel;

    private String cityCode = "";

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getProvince() {
        return province;
    }

    public void setProvince(long province) {
        this.province = province;
    }

    public long getCity() {
        return city;
    }

    public void setCity(long city) {
        this.city = city;
    }

    public long getDistrict() {
        return district;
    }

    public void setDistrict(long district) {
        this.district = district;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof HotCityBean){
            HotCityBean hotCityBean = (HotCityBean) o;
            if(!"".equals(cityCode) && cityCode.equals(hotCityBean.getCityCode())){
                return true;
            }

            if(district != 0 && district == hotCityBean.getDistrict()){
                return true;
            }

            if(district == 0 && hotCityBean.getDistrict() == 0){
                if(city != 0 && city == hotCityBean.getCity()){
                    return true;
                }

                if(city == 0 && hotCityBean.getCity() == 0){
                    if(province != 0 && hotCityBean.getProvince() == province){
                        return true;
                    }
                }
            }

            if(cityName.equals(hotCityBean.getCityName())){
                return true;
            }
        }
        return false;
    }
}

package com.project.mgjandroid.model;

/**
 * Created by yuandi on 2016/6/24.
 *
 */
public class ChooseCityModel {

    private static ChooseCityModel mInstance;
    
    private String cityName;

    private long cityCode = 0;

    private long provinceId = 0;

    private long cityId = 0;

    private long districtId = 0;
    /** 1:求职;2:招聘 */
    private int type;

    /** 1:收购;2:出售 */
    private int secondHandType;

    private boolean hasChanged = false;

    private ChooseCityModel() {
    }

    public static ChooseCityModel getInstance() {
        if (mInstance == null) {
            synchronized (ChooseCityModel.class) {
                if (mInstance == null) {
                    mInstance = new ChooseCityModel();
                }
            }
        }
        return mInstance;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getCityCode() {
        return cityCode;
    }

    public void setCityCode(long cityCode) {
        this.cityCode = cityCode;
        provinceId = 0;
        cityId = 0;
        districtId = 0;
    }

    public long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(long provinceId) {
        this.provinceId = provinceId;
        cityCode = 0;
        cityId = 0;
        districtId = 0;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(long districtId) {
        this.districtId = districtId;
    }

    public boolean isHasChanged() {
        return hasChanged;
    }

    public void setHasChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSecondHandType() {
        return secondHandType;
    }

    public void setSecondHandType(int secondHandType) {
        this.secondHandType = secondHandType;
    }
}

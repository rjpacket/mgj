package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.util.Date;

/**
 * Created by rjp on 2016/6/22.
 * Email:rjpgoodjob@gmail.com
 */
public class RecruitBean extends Entity {

    private Long id;
    /** 用户编号 */
    private Long userId;
    /** 省 */
    private Long province;
    /** 市 */
    private Long city;
    /** 区 */
    private Long district;
    /** 职位名称 */
    private String name;
    /** 职位分类编号 */
    private Long positionCategoryId;
    /** 手机号 */
    private String mobile;
    /** 岗位描述 */
    private String description;
    /** 刷新日期 */
    private String refreshDate;
    /** 当日刷新次数 */
    private int refreshCount;
    /** 0正常;1删除 */
    private int hasDel;
    /** 0正常;1置顶 */
    private int isTop;
    /** 1:求职;2:招聘 */
    private int type;

    private String positionCategoryName;

    private Date createTime;

    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProvince() {
        return province;
    }

    public void setProvince(Long province) {
        this.province = province;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public Long getDistrict() {
        return district;
    }

    public void setDistrict(Long district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPositionCategoryId() {
        return positionCategoryId;
    }

    public void setPositionCategoryId(Long positionCategoryId) {
        this.positionCategoryId = positionCategoryId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRefreshDate() {
        return refreshDate;
    }

    public void setRefreshDate(String refreshDate) {
        this.refreshDate = refreshDate;
    }

    public int getRefreshCount() {
        return refreshCount;
    }

    public void setRefreshCount(int refreshCount) {
        this.refreshCount = refreshCount;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPositionCategoryName() {
        return positionCategoryName;
    }

    public void setPositionCategoryName(String positionCategoryName) {
        this.positionCategoryName = positionCategoryName;
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

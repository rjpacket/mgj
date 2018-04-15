package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.util.Date;

/**
 * Created by yuandi on 2016/6/16.
 *
 */
public class BBSInformation extends Entity{

    private Long id;
    /** 用户编号 */
    private Long userId;
    /** 发布者 */
    private String author;
    /** 性别 0:女;1:男 */
    private int sex;
    /** 头像 */
    private String headImg;
    /** 手机号 */
    private String mobile;
    /** 分类编号 */
    private Long categoryId;
    /** 内容 */
    private String content;
    /** 图片 */
    private String images;
    /** 经度 */
    private Double longitude;
    /** 维度 */
    private Double latitude;
    /** 失败原因 */
    private String reason;
    /** 举报原因 */
    private String informReason;
    /** 点赞数 */
    private int favourCount;
    /** 状态 */
    private int status;
    /** 0正常;1删除 */
    private int hasDel;

    private String categoryName;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getInformReason() {
        return informReason;
    }

    public void setInformReason(String informReason) {
        this.informReason = informReason;
    }

    public int getFavourCount() {
        return favourCount;
    }

    public void setFavourCount(int favourCount) {
        this.favourCount = favourCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

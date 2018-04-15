package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuandi on 2016/7/25.
 *
 */
public class RepairInformation extends Entity{

    private Long id;

    private Date createTime;

    private Date modifyTime;
    /** 用户编号 */
    private Long userId;
    /** 省 */
    private Long province;
    /** 市 */
    private Long city;
    /** 区 */
    private Long district;
    /** 标题 */
    private String title;
    /** 服务区域 */
    private String serviceArea;
    /** 服务特色 */
    private String serviceFeature;
    /** 服务分类编号 */
    private Long repairServiceCategoryId;
    /** 分类编号 */
    private Long repairCategoryId;
    /** 手机号 */
    private String mobile;
    /** 详情描述 */
    private String description;
    /** 图片 */
    private String imgs;
    /** 评分 */
    private BigDecimal score = new BigDecimal(0.0);
    /** 刷新日期 */
    private String refreshDate;
    /** 当日刷新次数 */
    private int refreshCount;
    /** 0正常;1删除 */
    private int hasDel;
    /** 0正常;1置顶 */
    private int isTop;
    /** 类型(身份) 1:个人;2:商家 */
    private int type;
    /** 0未审核;1已审核 ;2审核失败 */
    private int status;

    private int reportCount;

    private String repairCategoryName;

    private String baiduCityCode = "";

    private String shareUrl;

    private String imageView;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    public String getServiceFeature() {
        return serviceFeature;
    }

    public void setServiceFeature(String serviceFeature) {
        this.serviceFeature = serviceFeature;
    }

    public Long getRepairServiceCategoryId() {
        return repairServiceCategoryId;
    }

    public void setRepairServiceCategoryId(Long repairServiceCategoryId) {
        this.repairServiceCategoryId = repairServiceCategoryId;
    }

    public Long getRepairCategoryId() {
        return repairCategoryId;
    }

    public void setRepairCategoryId(Long repairCategoryId) {
        this.repairCategoryId = repairCategoryId;
    }

    public String getRepairCategoryName() {
        return repairCategoryName;
    }

    public void setRepairCategoryName(String repairCategoryName) {
        this.repairCategoryName = repairCategoryName;
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

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getReportCount() {
        return reportCount;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }

    public String getBaiduCityCode() {
        return baiduCityCode;
    }

    public void setBaiduCityCode(String baiduCityCode) {
        this.baiduCityCode = baiduCityCode;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

}

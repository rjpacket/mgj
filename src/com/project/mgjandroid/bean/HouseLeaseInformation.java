package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuandi on 2016/7/20.
 *
 */
public class HouseLeaseInformation extends Entity {

    private Long id;
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
    /** 室 */
    private int bedRoom;
    /** 厅 */
    private int livingRoom;
    /** 卫 */
    private int restRoom;
    /** 地址 */
    private String address;

    private String houseType;
    private String houseArea;
    /** 服务分类编号 */
    private Long houseLeaseServiceCategoryId;
    /** 分类编号 */
    private Long houseLeaseCategoryId;
    /** 手机号 */
    private String mobile;
    /** 详情描述 */
    private String description;
    /** 价格 */
    private BigDecimal amt = BigDecimal.ZERO;
    /** 最小价格 */
    private BigDecimal minAmt = BigDecimal.ZERO;
    /** 最大价格 */
    private BigDecimal maxAmt = BigDecimal.ZERO;
    /** 图片 */
    private String imgs;
    /** 刷新日期 */
    private String refreshDate;
    /** 当日刷新次数 */
    private int refreshCount;
    /** 0正常;1删除 */
    private int hasDel;
    /** 0正常;1置顶 */
    private int isTop;
    /** 类型 1:求租; 2:出租 */
    private int type;
    /** 0未审核;1已审核 ;2审核失败 */
    private int status;

    private int reportCount;

    private String houseLeaseCategoryName;

    private String baiduCityCode = "";

    private String showAmt;

    private String shareUrl;

    private String imageView;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBedRoom() {
        return bedRoom;
    }

    public void setBedRoom(int bedRoom) {
        this.bedRoom = bedRoom;
    }

    public int getLivingRoom() {
        return livingRoom;
    }

    public void setLivingRoom(int livingRoom) {
        this.livingRoom = livingRoom;
    }

    public int getRestRoom() {
        return restRoom;
    }

    public void setRestRoom(int restRoom) {
        this.restRoom = restRoom;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public BigDecimal getMinAmt() {
        return minAmt;
    }

    public void setMinAmt(BigDecimal minAmt) {
        this.minAmt = minAmt;
    }

    public BigDecimal getMaxAmt() {
        return maxAmt;
    }

    public void setMaxAmt(BigDecimal maxAmt) {
        this.maxAmt = maxAmt;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public Long getHouseLeaseServiceCategoryId() {
        return houseLeaseServiceCategoryId;
    }

    public void setHouseLeaseServiceCategoryId(Long houseLeaseServiceCategoryId) {
        this.houseLeaseServiceCategoryId = houseLeaseServiceCategoryId;
    }

    public Long getHouseLeaseCategoryId() {
        return houseLeaseCategoryId;
    }

    public void setHouseLeaseCategoryId(Long houseLeaseCategoryId) {
        this.houseLeaseCategoryId = houseLeaseCategoryId;
    }

    public String getHouseLeaseCategoryName() {
        return houseLeaseCategoryName;
    }

    public void setHouseLeaseCategoryName(String houseLeaseCategoryName) {
        this.houseLeaseCategoryName = houseLeaseCategoryName;
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

    public String getBaiduCityCode() {
        return baiduCityCode;
    }

    public void setBaiduCityCode(String baiduCityCode) {
        this.baiduCityCode = baiduCityCode;
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

    public String getShowAmt() {
        return showAmt;
    }

    public void setShowAmt(String showAmt) {
        this.showAmt = showAmt;
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

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(String houseArea) {
        this.houseArea = houseArea;
    }
}

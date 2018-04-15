package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuandi on 2016/7/11.
 *
 */
public class SecondhandInformation extends Entity {

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
    /** 服务分类编号 */
    private Long secondhandServiceCategoryId;
    /** 分类编号 */
    private Long secondhandCategoryId;
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
    /** 创建日期 */
    private Date createTime;
    /** 修改日期 */
    private Date modifyTime;
    /** 当日刷新次数 */
    private int refreshCount;
    /** 0正常;1删除 */
    private int hasDel;
    /** 0正常;1置顶 */
    private int isTop;
    /** 类型 1:收购;2:出售 */
    private int type;
    /** 0未审核;1已审核 */
    private int status;

    private String secondhandCategoryName;

    private String baiduCityCode = "";

    private String showAmt;

    private String showRangeAmt;

    private String shareUrl;
    private String imageView;

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

    public Long getSecondhandServiceCategoryId() {
        return secondhandServiceCategoryId;
    }

    public void setSecondhandServiceCategoryId(Long secondhandServiceCategoryId) {
        this.secondhandServiceCategoryId = secondhandServiceCategoryId;
    }

    public Long getSecondhandCategoryId() {
        return secondhandCategoryId;
    }

    public void setSecondhandCategoryId(Long secondhandCategoryId) {
        this.secondhandCategoryId = secondhandCategoryId;
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

    public String getSecondhandCategoryName() {
        return secondhandCategoryName;
    }

    public void setSecondhandCategoryName(String secondhandCategoryName) {
        this.secondhandCategoryName = secondhandCategoryName;
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

    public String getShowAmt() {
        return showAmt;
    }

    public void setShowAmt(String showAmt) {
        this.showAmt = showAmt;
    }

    public String getShowRangeAmt() {
        return showRangeAmt;
    }

    public void setShowRangeAmt(String showRangeAmt) {
        this.showRangeAmt = showRangeAmt;
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


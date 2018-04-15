package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yuandi on 2016/7/22.
 *
 */
public class EducationInformation extends Entity {

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
    /** 教师身份 **/
    private String teacherType;
    /** 辅导阶段 **/
    private String tutorshipStage;
    /** 名称 */
    private String name;
    /** 地址 */
    private String address;
    /** 服务分类编号 */
    private Long educationServiceCategoryId;
    /** 分类编号 */
    private Long educationCategoryId;
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
    /** 类型 1:教育培训;2:家教 */
    private int type;
    /** 0未审核;1已审核 ;2审核失败 */
    private int status;

    private int reportCount;

    private String educationCategoryName;

    private String baiduCityCode = "";

    private String shareUrl;

    private String imageView;

    private Date createTime;

    private Date modifyTime;

    private String educationTeacherTypeName;

    private String educationTutorshipStageName;

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

    public String getTeacherType() {
        return teacherType;
    }

    public void setTeacherType(String teacherType) {
        this.teacherType = teacherType;
    }

    public String getTutorshipStage() {
        return tutorshipStage;
    }

    public void setTutorshipStage(String tutorshipStage) {
        this.tutorshipStage = tutorshipStage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Long getEducationServiceCategoryId() {
        return educationServiceCategoryId;
    }

    public void setEducationServiceCategoryId(Long educationServiceCategoryId) {
        this.educationServiceCategoryId = educationServiceCategoryId;
    }

    public Long getEducationCategoryId() {
        return educationCategoryId;
    }

    public void setEducationCategoryId(Long educationCategoryId) {
        this.educationCategoryId = educationCategoryId;
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

    public String getEducationCategoryName() {
        return educationCategoryName;
    }

    public void setEducationCategoryName(String educationCategoryName) {
        this.educationCategoryName = educationCategoryName;
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

    public String getEducationTeacherTypeName() {
        return educationTeacherTypeName;
    }

    public void setEducationTeacherTypeName(String educationTeacherTypeName) {
        this.educationTeacherTypeName = educationTeacherTypeName;
    }

    public String getEducationTutorshipStageName() {
        return educationTutorshipStageName;
    }

    public void setEducationTutorshipStageName(String educationTutorshipStageName) {
        this.educationTutorshipStageName = educationTutorshipStageName;
    }
}

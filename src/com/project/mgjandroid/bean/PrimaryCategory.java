package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuandi on 2016/6/22.
 *
 */
public class PrimaryCategory extends Entity {

    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 代理商编号
     */
    private Long agentId;
    /**
     * 图片地址
     */
    private String picUrl = "";
    /**
     * 灰色图片地址
     */
    private String grayUrl = "";
    /**
     * 标签[商家]分类ID
     */
    private Long tagCategoryId;
    /**
     * 跳转分类所属类型
     */
    private Integer tagCategoryType;
    /**
     * 排序编号
     */
    private int sortNo;
    /**
     * 是否可用 0可用1不可用
     */
    private int graySwitch;
    /**
     * 是否删除
     */
    private int hasDel;

    private TagCategory tagCategory;

    private String tagCategoryName;

    private String tagCategoryTypeName;
    /**
     * 跳转类型1：链接，2：分类
     **/
    private int gotoType;
    /**
     * 跳转地址
     */
    private String gotoUrl = "";
    /**
     * 服务分类编号
     */
    private Long serviceCategoryId;

    private String serviceCategoryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public TagCategory getTagCategory() {
        return tagCategory;
    }

    public void setTagCategory(TagCategory tagCategory) {
        this.tagCategory = tagCategory;
    }

    public String getTagCategoryName() {
        return tagCategoryName;
    }

    public void setTagCategoryName(String tagCategoryName) {
        this.tagCategoryName = tagCategoryName;
    }

    public String getTagCategoryTypeName() {
        return tagCategoryTypeName;
    }

    public void setTagCategoryTypeName(String tagCategoryTypeName) {
        this.tagCategoryTypeName = tagCategoryTypeName;
    }

    public int getGotoType() {
        return gotoType;
    }

    public void setGotoType(int gotoType) {
        this.gotoType = gotoType;
    }

    public String getGotoUrl() {
        return gotoUrl;
    }

    public void setGotoUrl(String gotoUrl) {
        this.gotoUrl = gotoUrl;
    }

    public Long getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceCategoryId(Long serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    public String getServiceCategoryName() {
        return serviceCategoryName;
    }

    public void setServiceCategoryName(String serviceCategoryName) {
        this.serviceCategoryName = serviceCategoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getGrayUrl() {
        return grayUrl;
    }

    public void setGrayUrl(String grayUrl) {
        this.grayUrl = grayUrl;
    }

    public Long getTagCategoryId() {
        return tagCategoryId;
    }

    public void setTagCategoryId(Long tagCategoryId) {
        this.tagCategoryId = tagCategoryId;
    }

    public Integer getTagCategoryType() {
        return tagCategoryType;
    }

    public void setTagCategoryType(Integer tagCategoryType) {
        this.tagCategoryType = tagCategoryType;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public int getGraySwitch() {
        return graySwitch;
    }

    public void setGraySwitch(int graySwitch) {
        this.graySwitch = graySwitch;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public class TagCategory implements Serializable {

        private Long id;
        /**
         * 分类名称
         */
        private String name;
        /**
         * 上级分类名称。最高级分类的parentId为0
         */
        private long parentId;

        private String icon = "";
        /**
         * 1：一级，2：二级
         */
        private int level;
        /**
         * 排序编号，默认为0
         */
        private int sortNo;

        private int hasDel;
        /**
         * 代理商编号
         */
        private long agentId;
        /**
         * 分类所属类型 TagCategoryType
         */
        private Integer tagCategoryType;

        List<TagCategory> childTagCategoryList;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getParentId() {
            return parentId;
        }

        public void setParentId(long parentId) {
            this.parentId = parentId;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
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

        public long getAgentId() {
            return agentId;
        }

        public void setAgentId(long agentId) {
            this.agentId = agentId;
        }

        public Integer getTagCategoryType() {
            return tagCategoryType;
        }

        public void setTagCategoryType(Integer tagCategoryType) {
            this.tagCategoryType = tagCategoryType;
        }

        public List<TagCategory> getChildTagCategoryList() {
            return childTagCategoryList;
        }

        public void setChildTagCategoryList(List<TagCategory> childTagCategoryList) {
            this.childTagCategoryList = childTagCategoryList;
        }
    }
}

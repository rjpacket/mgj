package com.project.mgjandroid.model;

import java.util.Date;
import java.util.List;
import com.project.mgjandroid.bean.PrimaryCategory;

/**
 * Created by yuandi on 2016/6/21.
 *
 */
public class MorePrimaryCategoryModel extends Entity {

    private int code;
    private String uuid;
    private List<ServiceCategory> value;
    private boolean success;
    private Date servertime;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<ServiceCategory> getValue() {
        return value;
    }

    public void setValue(List<ServiceCategory> value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Date getServertime() {
        return servertime;
    }

    public void setServertime(Date servertime) {
        this.servertime = servertime;
    }

    public class ServiceCategory extends Entity{

        private Long id;
        /** 名称 */
        private String name;
        /** 描述 */
        private String description;
        /** 代理商编号 */
        private Long agentId;
        /** 排序编号 */
        private int sortNo;
        /** 是否删除 */
        private int hasDel;

        private List<PrimaryCategory> primaryCategoryList;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Long getAgentId() {
            return agentId;
        }

        public void setAgentId(Long agentId) {
            this.agentId = agentId;
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

        public List<PrimaryCategory> getPrimaryCategoryList() {
            return primaryCategoryList;
        }

        public void setPrimaryCategoryList(List<PrimaryCategory> primaryCategoryList) {
            this.primaryCategoryList = primaryCategoryList;
        }
    }
}

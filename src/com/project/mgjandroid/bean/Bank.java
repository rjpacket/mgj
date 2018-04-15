package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

/**
 * Created by yuandi on 2016/5/24.
 */
public class Bank extends Entity{

    private Long id;
    /** 银行名称 **/
    private String bankName;
    /** 银行logo **/
    private String bankLogo;
    /** 是否删除0：未删除，1：已删除 **/
    private Integer hasDel;
    /** 排序 **/
    private Integer sortNo;

    private boolean isCheck;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }

    public Integer getHasDel() {
        return hasDel;
    }

    public void setHasDel(Integer hasDel) {
        this.hasDel = hasDel;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    /** 编号 **/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

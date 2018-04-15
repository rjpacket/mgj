package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

/**
 * Created by yuandi on 2016/5/24.
 */
public class BankCard extends Entity{

    private Long id;
    /** 用户编号 */
    private Long userId;
    private Long bankId;
    /** 开户行 */
    private String bankName = "";
    /** 银行logo **/
    private String bankLogo = "";
    /** 银行卡号 */
    private String bankCard = "";
    /** 开户人 */
    private String bankPerson = "";
    /** 手机号 */
    private String mobile = "";
    /** 是否删除 */
    private int hasDel;

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

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankPerson() {
        return bankPerson;
    }

    public void setBankPerson(String bankPerson) {
        this.bankPerson = bankPerson;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getHasDel() {
        return hasDel;
    }

    public void setHasDel(int hasDel) {
        this.hasDel = hasDel;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }
}

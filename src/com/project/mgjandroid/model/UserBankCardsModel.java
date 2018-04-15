package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.BankCard;

import java.util.ArrayList;

/**
 * Created by yuandi on 2016/5/24.
 */
public class UserBankCardsModel extends Entity {

    private int code;

    private String uuid;

    private ArrayList<BankCard> value;

    private boolean success;

    private String servertime;

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

    public ArrayList<BankCard> getValue() {
        return value;
    }

    public void setValue(ArrayList<BankCard> value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getServertime() {
        return servertime;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }
}

package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.MerchantTakeAwayMenu;

import java.io.Serializable;

/**
 * Created by yuandi on 2016/3/9.
 */
public class GoodsListModel implements Serializable {

    private static final long serialVersionUID = 1111111L;
    private int code;
    private String uuid;
    private MerchantTakeAwayMenu value;
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

    public MerchantTakeAwayMenu getValue() {
        return value;
    }

    public void setValue(MerchantTakeAwayMenu value) {
        this.value = value;
    }
}

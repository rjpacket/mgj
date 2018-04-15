package com.project.mgjandroid.constants;

/**
 * Created by yuandi on 2016/3/16.
 */
public enum OrderFlowStatus {

    Cancel(-1, "订单取消"),

    Init(0, "订单创建"),

    WaitPay(1, "等待支付"),

    WaitConfirm(2, "等待商家确认"),

    ACCEPTED(3, "商家已接单"),

    WaitTake(4, "配送员取货中"),

    HasTake(5, "配送员已取货"),

    WaitDelivery(6, "等待送达"),

    Done(7, "订单完成");

    private int value;

    private String memo;

    OrderFlowStatus(int value, String memo) {
        this.value = value;
        this.memo = memo;
    }

    public int getValue() {
        return value;
    }

    public String getMemo() {
        return memo;
    }

    public static OrderFlowStatus getOrderStatusByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (OrderFlowStatus type : OrderFlowStatus.values()) {
            if (type.getValue() == value.intValue()) {
                return type;
            }
        }
        return null;
    }

    public static String getMemoByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (OrderFlowStatus type : OrderFlowStatus.values()) {
            if (type.getValue() == value.intValue()) {
                return type.getMemo();
            }
        }
        return null;
    }
}


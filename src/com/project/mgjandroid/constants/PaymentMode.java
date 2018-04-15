package com.project.mgjandroid.constants;

/**
 * Created by yuandi on 2016/3/16.
 */
public enum PaymentMode {
    Online(1, "在线支付"), Cash(2, "货到付款");

    private int value;

    private String memo;

    PaymentMode(int value, String memo) {
        this.value = value;
        this.memo = memo;
    }

    public int getValue() {
        return value;
    }

    public String getMemo() {
        return memo;
    }

    public static PaymentMode getPaymentModeByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (PaymentMode type : PaymentMode.values()) {
            if (type.getValue() == value.intValue()) {
                return type;
            }
        }
        return null;
    }

}

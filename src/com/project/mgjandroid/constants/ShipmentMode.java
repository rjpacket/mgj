package com.project.mgjandroid.constants;

/**
 * Created by yuandi on 2016/3/16.
 */
public enum ShipmentMode {
    Merchant(1, "商家配送"), Express(2, "第三方配送");

    private int value;

    private String memo;

    ShipmentMode(int value, String memo) {
        this.value = value;
        this.memo = memo;
    }

    public int getValue() {
        return value;
    }

    public String getMemo() {
        return memo;
    }

    public static ShipmentMode getShipmentModeByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (ShipmentMode type : ShipmentMode.values()) {
            if (type.getValue() == value.intValue()) {
                return type;
            }
        }
        return null;
    }

}

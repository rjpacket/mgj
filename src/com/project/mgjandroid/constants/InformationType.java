package com.project.mgjandroid.constants;

/**
 * Created by yuandi on 2016/7/25.
 *
 */
public enum InformationType {
    Recruit(1, "招聘"),

    Lease(2, "房屋租赁"),

    Repair(3, "维修"),

    Education(4, "教育培训"),

    Housekeeping(5, "家政"),

    Secondhand(6, "二手市场"),

    Divination(7, "风水"),

    Law(8, "法律咨询"),

    Health(9, "健康咨询");

    private int value;

    private String name;

    InformationType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static InformationType getInformationTypeByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (InformationType type : InformationType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }

}

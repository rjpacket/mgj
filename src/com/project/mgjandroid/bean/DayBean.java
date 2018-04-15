package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ning on 2016/3/19.
 */
public class DayBean extends Entity {

    public DayBean(String day){
        this.setDay(day);
    }

    private String day;
    private boolean isChecked;
    private ArrayList<TimeBean> timeList;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public ArrayList<TimeBean> getTimeList() {
        return timeList;
    }

    public void setTimeList(ArrayList<TimeBean> timeList) {
        this.timeList = timeList;
    }
}

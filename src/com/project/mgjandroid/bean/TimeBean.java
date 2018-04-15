package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

/**
 * Created by ning on 2016/3/21.
 */
public class TimeBean extends Entity {
    public TimeBean(String id,String day){
        this.setDay(day);
        this.setId(id);
    }

    private String day;
    private String id;
    private boolean isChecked;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

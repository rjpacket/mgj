package com.project.mgjandroid.bean;

import com.project.mgjandroid.model.Entity;

/**
 * Created by ning on 2016/3/30.
 */
public class HomeBean extends Entity {
    private int icon;
    private String name;
    private boolean isCheck;
    private int id;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

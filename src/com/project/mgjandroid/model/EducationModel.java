package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.EducationInformation;

import java.util.List;

/**
 * Created by rjp on 2016/7/22.
 * Email:rjpgoodjob@gmail.com
 */
public class EducationModel extends Entity {
    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private List<EducationInformation> value;

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

    public List<EducationInformation> getValue() {
        return value;
    }

    public void setValue(List<EducationInformation> value) {
        this.value = value;
    }
}

package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.RecruitBean;

/**
 * Created by rjp on 2016/6/22.
 * Email:rjpgoodjob@gmail.com
 */
public class PublishJobModel extends Entity {

    private int code;
    private String uuid;

    private RecruitBean value;
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

    public RecruitBean getValue() {
        return value;
    }

    public void setValue(RecruitBean value) {
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

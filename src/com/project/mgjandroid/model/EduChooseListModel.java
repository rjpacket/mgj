package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.EducationServiceCategory;
import com.project.mgjandroid.bean.EducationTeacherTypeOrTutorshipStage;

import java.util.List;

/**
 * Created by yuandi on 2016/7/22.
 *
 */
public class EduChooseListModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    private List<EducationTeacherTypeOrTutorshipStage> value;

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

    public List<EducationTeacherTypeOrTutorshipStage> getValue() {
        return value;
    }

    public void setValue(List<EducationTeacherTypeOrTutorshipStage> value) {
        this.value = value;
    }
}
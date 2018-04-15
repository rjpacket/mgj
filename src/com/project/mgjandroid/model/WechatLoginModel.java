package com.project.mgjandroid.model;

/**
 * Created by User_Cjh on 2016/3/25.
 */
public class WechatLoginModel extends Entity {
    /*
    "code":0,
    "uuid":"A00000552D1815",
    "value":{"id":15,"createTime":"2016-03-25 16:10:19","modifyTime":"2016-03-25 16:10:19","name":"Cz35iK9Ykbwh","mobile":"","pwd":"00a5156b9be46be472e143abb568b4eb4626da208d64993cd08a11c3e628a216d4bad7bcbbc0d497","headerImg":"","regTime":"2016-03-25 16:10:19","lastLoginTime":"2016-03-25 16:10:19","channel":"1","token":"52896485bf774a3f8d1801b9aaa31a2f","userToken":null},
    "success":true
    */
    private int code;
    private String uuid;

    /*
    "id":15,
    "createTime":"2016-03-25 16:10:19",
    "modifyTime":"2016-03-25 16:10:19",
    "name":"Cz35iK9Ykbwh",
    "mobile":"",
    "pwd":"00a5156b9be46be472e143abb568b4eb4626da208d64993cd08a11c3e628a216d4bad7bcbbc0d497",
    "headerImg":"",
    "regTime":"2016-03-25 16:10:19",
    "lastLoginTime":"2016-03-25 16:10:19",
    "channel":"1",
    "token":"52896485bf774a3f8d1801b9aaa31a2f",
    "userToken":null,
    */
    private SmsLoginModel.ValueEntity.AppUserEntity value;
    private  boolean success;

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

    public SmsLoginModel.ValueEntity.AppUserEntity getValue() {
        return value;
    }

    public void setValue(SmsLoginModel.ValueEntity.AppUserEntity value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

package com.project.mgjandroid.model;

import java.util.List;

/**
 * Created by Cjh on 2016/3/25.
 */
public class CheckBindModel extends Entity{
    /*
    "code": 0,
    "uuid": "867994023030857",
    "value": [{"id": 54,"createTime": "2016-03-25 16:10:20","modifyTime": "2016-03-25 16:10:20","openId": "oZNJ2vwf3iyM-rUfpj9rb6LGNLT4","userId": 15,"type": 1}],
    "success": true
    */
    private int code;
    private String uuid;
    private List<CheckWechatBindEntity> value;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<CheckWechatBindEntity> getValue() {
        return value;
    }

    public void setValue(List<CheckWechatBindEntity> value) {
        this.value = value;
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

    public static class CheckWechatBindEntity {
        private int id;
        private String createTime;
        private String modifyTime;
        private String openId;
        private int userId;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}

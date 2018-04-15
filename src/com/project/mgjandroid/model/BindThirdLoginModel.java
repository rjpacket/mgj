package com.project.mgjandroid.model;

/**
 * Created by User_Cjh on 2016/4/1.
 */
public class BindThirdLoginModel extends Entity{

    /**
     * code : 0
     * uuid : 867994023030857
     * value : {"id":72,"createTime":"2016-04-01 16:31:10","modifyTime":"2016-04-01 16:31:10","openId":"oZNJ2vwf3iyM-rUfpj9rb6LGNLT4","userId":15,"type":1}
     * success : true
     * servertime : 2016-04-01 16:31:10
     */

    private int code;
    private String uuid;
    /**
     * id : 72
     * createTime : 2016-04-01 16:31:10
     * modifyTime : 2016-04-01 16:31:10
     * openId : oZNJ2vwf3iyM-rUfpj9rb6LGNLT4
     * userId : 15
     * type : 1
     */

    private ValueEntity value;
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

    public ValueEntity getValue() {
        return value;
    }

    public void setValue(ValueEntity value) {
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

    public static class ValueEntity {
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

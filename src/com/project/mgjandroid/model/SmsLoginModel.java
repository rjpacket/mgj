package com.project.mgjandroid.model;

/**
 * Created by ning on 2016/3/12.
 */
public class SmsLoginModel extends Entity {

    /**
     * code : 0
     * uuid : 866329020175994
     * value : {"newregister":true,"appUser":{"id":1,"createTime":"2016-03-12 17:50:50","modifyTime":"2016-03-12 17:50:50","name":null,"mobile":"18501967780","pwd":"413973","headerImg":null,"token":"e3d993cde342455cbe5bbd0323dc2591","regTime":"2016-03-12 17:50:50","lastLoginTime":"2016-03-12 17:50:50","channel":"1","ip":"61.49.178.18","client":"android","clientId":null,"apnsProduction":null,"app":null,"imei":"866329020175994","clientVersion":"1.0.0","regionId":null}}
     * success : true
     */

    private int code;
    private String uuid;
    /**
     * newregister : true
     * appUser : {"id":1,"createTime":"2016-03-12 17:50:50","modifyTime":"2016-03-12 17:50:50","name":null,"mobile":"18501967780","pwd":"413973","headerImg":null,"token":"e3d993cde342455cbe5bbd0323dc2591","regTime":"2016-03-12 17:50:50","lastLoginTime":"2016-03-12 17:50:50","channel":"1","ip":"61.49.178.18","client":"android","clientId":null,"apnsProduction":null,"app":null,"imei":"866329020175994","clientVersion":"1.0.0","regionId":null}
     */

    private ValueEntity value;
    private boolean success;

    public void setCode(int code) {
        this.code = code;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setValue(ValueEntity value) {
        this.value = value;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public String getUuid() {
        return uuid;
    }

    public ValueEntity getValue() {
        return value;
    }

    public boolean isSuccess() {
        return success;
    }

    public static class ValueEntity {
        private boolean newregister;
        /**
         * id : 1
         * createTime : 2016-03-12 17:50:50
         * modifyTime : 2016-03-12 17:50:50
         * name : null
         * mobile : 18501967780
         * pwd : 413973
         * headerImg : null
         * token : e3d993cde342455cbe5bbd0323dc2591
         * regTime : 2016-03-12 17:50:50
         * lastLoginTime : 2016-03-12 17:50:50
         * channel : 1
         * ip : 61.49.178.18
         * client : android
         * clientId : null
         * apnsProduction : null
         * app : null
         * imei : 866329020175994
         * clientVersion : 1.0.0
         * regionId : null
         */

        private AppUserEntity appUser;

        public void setNewregister(boolean newregister) {
            this.newregister = newregister;
        }

        public void setAppUser(AppUserEntity appUser) {
            this.appUser = appUser;
        }

        public boolean isNewregister() {
            return newregister;
        }

        public AppUserEntity getAppUser() {
            return appUser;
        }

        public static class AppUserEntity {
            private int id;
            private String createTime;
            private String modifyTime;
            private String name;
            private String mobile;
            private String pwd;
            private String headerImg;
            private String token;
            private String regTime;
            private String lastLoginTime;
            private String channel;
            private String userToken;

            public void setId(int id) {
                this.id = id;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public void setModifyTime(String modifyTime) {
                this.modifyTime = modifyTime;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public void setPwd(String pwd) {
                this.pwd = pwd;
            }

            public void setHeaderImg(String headerImg) {
                this.headerImg = headerImg;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public void setRegTime(String regTime) {
                this.regTime = regTime;
            }

            public void setLastLoginTime(String lastLoginTime) {
                this.lastLoginTime = lastLoginTime;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public int getId() {
                return id;
            }

            public String getCreateTime() {
                return createTime;
            }

            public String getModifyTime() {
                return modifyTime;
            }

            public String getName() {
                return name;
            }

            public String getMobile() {
                return mobile;
            }

            public String getPwd() {
                return pwd;
            }

            public String getHeaderImg() {
                return headerImg;
            }

            public String getToken() {
                return token;
            }

            public String getRegTime() {
                return regTime;
            }

            public String getLastLoginTime() {
                return lastLoginTime;
            }

            public String getChannel() {
                return channel;
            }

            public String getUserToken() {
                return userToken;
            }

            public void setUserToken(String userToken) {
                this.userToken = userToken;
            }
        }
    }
}

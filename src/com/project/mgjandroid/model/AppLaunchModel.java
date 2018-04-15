package com.project.mgjandroid.model;

/**
 * Created by ning on 2016/3/21.
 */
public class AppLaunchModel extends Entity {

    /**
     * code : 0
     * uuid : 866329020175994
     * value : {"id":2,"createTime":"2016-03-16 09:58:01","modifyTime":"2016-03-21 14:33:35","name":"考虑考虑","mobile":"18501967780","pwd":"342832","headerImg":"20160318084927510.jpg","regTime":"2016-03-16 09:58:01","lastLoginTime":"2016-03-21 14:33:35","channel":"1","token":"ba771a9a8b0f4767b2283956acb3a9c4","userToken":{"id":8,"createTime":"2016-03-16 21:09:53","modifyTime":"2016-03-21 14:33:35","token":"ba771a9a8b0f4767b2283956acb3a9c4","userId":2,"mac":"58:2a:f7:47:b5:c2","brand":"HUAWEI","model":"CHM-TL00H","imei":"866329020175994","ip":"61.49.178.18","client":"android","clientId":null,"apnsProduction":null,"app":"mgjUser","clientVersion":"1.0.0","isAble":1}}
     * success : true
     */

    private int code;
    private String uuid;
    /**
     * id : 2
     * createTime : 2016-03-16 09:58:01
     * modifyTime : 2016-03-21 14:33:35
     * name : 考虑考虑
     * mobile : 18501967780
     * pwd : 342832
     * headerImg : 20160318084927510.jpg
     * regTime : 2016-03-16 09:58:01
     * lastLoginTime : 2016-03-21 14:33:35
     * channel : 1
     * token : ba771a9a8b0f4767b2283956acb3a9c4
     * userToken : {"id":8,"createTime":"2016-03-16 21:09:53","modifyTime":"2016-03-21 14:33:35","token":"ba771a9a8b0f4767b2283956acb3a9c4","userId":2,"mac":"58:2a:f7:47:b5:c2","brand":"HUAWEI","model":"CHM-TL00H","imei":"866329020175994","ip":"61.49.178.18","client":"android","clientId":null,"apnsProduction":null,"app":"mgjUser","clientVersion":"1.0.0","isAble":1}
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
        private int id;
        private String createTime;
        private String modifyTime;
        private String name;
        private String mobile;
        private String pwd;
        private String headerImg;
        private String regTime;
        private String lastLoginTime;
        private String channel;
        private String token;
        /**
         * id : 8
         * createTime : 2016-03-16 21:09:53
         * modifyTime : 2016-03-21 14:33:35
         * token : ba771a9a8b0f4767b2283956acb3a9c4
         * userId : 2
         * mac : 58:2a:f7:47:b5:c2
         * brand : HUAWEI
         * model : CHM-TL00H
         * imei : 866329020175994
         * ip : 61.49.178.18
         * client : android
         * clientId : null
         * apnsProduction : null
         * app : mgjUser
         * clientVersion : 1.0.0
         * isAble : 1
         */

        private UserTokenEntity userToken;

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

        public void setRegTime(String regTime) {
            this.regTime = regTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setUserToken(UserTokenEntity userToken) {
            this.userToken = userToken;
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

        public String getRegTime() {
            return regTime;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public String getChannel() {
            return channel;
        }

        public String getToken() {
            return token;
        }

        public UserTokenEntity getUserToken() {
            return userToken;
        }

        public static class UserTokenEntity {
            private int id;
            private String createTime;
            private String modifyTime;
            private String token;
            private int userId;
            private String mac;
            private String brand;
            private String model;
            private String imei;
            private String ip;
            private String client;
            private Object clientId;
            private Object apnsProduction;
            private String app;
            private String clientVersion;
            private int isAble;

            public void setId(int id) {
                this.id = id;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public void setModifyTime(String modifyTime) {
                this.modifyTime = modifyTime;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public void setMac(String mac) {
                this.mac = mac;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public void setImei(String imei) {
                this.imei = imei;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public void setClient(String client) {
                this.client = client;
            }

            public void setClientId(Object clientId) {
                this.clientId = clientId;
            }

            public void setApnsProduction(Object apnsProduction) {
                this.apnsProduction = apnsProduction;
            }

            public void setApp(String app) {
                this.app = app;
            }

            public void setClientVersion(String clientVersion) {
                this.clientVersion = clientVersion;
            }

            public void setIsAble(int isAble) {
                this.isAble = isAble;
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

            public String getToken() {
                return token;
            }

            public int getUserId() {
                return userId;
            }

            public String getMac() {
                return mac;
            }

            public String getBrand() {
                return brand;
            }

            public String getModel() {
                return model;
            }

            public String getImei() {
                return imei;
            }

            public String getIp() {
                return ip;
            }

            public String getClient() {
                return client;
            }

            public Object getClientId() {
                return clientId;
            }

            public Object getApnsProduction() {
                return apnsProduction;
            }

            public String getApp() {
                return app;
            }

            public String getClientVersion() {
                return clientVersion;
            }

            public int getIsAble() {
                return isAble;
            }
        }
    }
}

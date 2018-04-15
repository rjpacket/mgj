package com.project.mgjandroid.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by User_Cjh on 2016/4/1.
 */
public class GoodsEvaluateModel extends Entity{

    /**
     * code : 0
     * uuid : 867994023030857
     * value : [{"id":13,"createTime":"2016-03-31 20:21:16","modifyTime":"2016-03-31 20:21:16","orderId":"2016033100004803","goodsId":4,"userId":5,"orderCommentsId":4,"goodsScore":5,"goodsScoreComments":"","appUser":{"id":5,"createTime":null,"modifyTime":null,"name":"国栋","mobile":"13256975859","pwd":"","headerImg":null,"regTime":null,"lastLoginTime":null,"channel":null,"token":null,"userToken":null,"city":null}},{"id":12,"createTime":"2016-03-31 20:01:34","modifyTime":"2016-03-31 20:01:34","orderId":"2016033100004704","goodsId":4,"userId":12,"orderCommentsId":4,"goodsScore":5,"goodsScoreComments":"","appUser":{"id":12,"createTime":null,"modifyTime":null,"name":"080y0pesAggV","mobile":"18810718147","pwd":"","headerImg":"20160323034759741.jpg","regTime":null,"lastLoginTime":null,"channel":null,"token":null,"userToken":null,"city":null}},{"id":9,"createTime":"2016-03-31 14:28:56","modifyTime":"2016-03-31 14:28:56","orderId":"2016033100004206","goodsId":4,"userId":5,"orderCommentsId":4,"goodsScore":2,"goodsScoreComments":"好吃各地不信任","appUser":{"id":5,"createTime":null,"modifyTime":null,"name":"国栋","mobile":"13256975859","pwd":"","headerImg":null,"regTime":null,"lastLoginTime":null,"channel":null,"token":null,"userToken":null,"city":null}},{"id":8,"createTime":"2016-03-31 14:27:56","modifyTime":"2016-03-31 14:27:56","orderId":"2016033100004208","goodsId":4,"userId":5,"orderCommentsId":4,"goodsScore":2,"goodsScoreComments":"","appUser":{"id":5,"createTime":null,"modifyTime":null,"name":"国栋","mobile":"13256975859","pwd":"","headerImg":null,"regTime":null,"lastLoginTime":null,"channel":null,"token":null,"userToken":null,"city":null}},{"id":4,"createTime":"2016-03-31 11:47:55","modifyTime":"2016-03-31 11:47:55","orderId":"2016033000004002","goodsId":4,"userId":5,"orderCommentsId":4,"goodsScore":0,"goodsScoreComments":"","appUser":{"id":5,"createTime":null,"modifyTime":null,"name":"国栋","mobile":"13256975859","pwd":"","headerImg":null,"regTime":null,"lastLoginTime":null,"channel":null,"token":null,"userToken":null,"city":null}},{"id":1,"createTime":"2016-03-31 11:41:36","modifyTime":"2016-03-31 11:41:36","orderId":"2016033000004003","goodsId":4,"userId":5,"orderCommentsId":4,"goodsScore":0,"goodsScoreComments":"","appUser":{"id":5,"createTime":null,"modifyTime":null,"name":"国栋","mobile":"13256975859","pwd":"","headerImg":null,"regTime":null,"lastLoginTime":null,"channel":null,"token":null,"userToken":null,"city":null}}]
     * success : true
     * servertime : 2016-04-01 11:37:29
     */

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    /**
     * id : 13
     * createTime : 2016-03-31 20:21:16
     * modifyTime : 2016-03-31 20:21:16
     * orderId : 2016033100004803
     * goodsId : 4
     * userId : 5
     * orderCommentsId : 4
     * goodsScore : 5.0
     * goodsScoreComments :
     * appUser : {"id":5,"createTime":null,"modifyTime":null,"name":"国栋","mobile":"13256975859","pwd":"","headerImg":null,"regTime":null,"lastLoginTime":null,"channel":null,"token":null,"userToken":null,"city":null}
     */

    private List<ValueEntity> value;

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

    public List<ValueEntity> getValue() {
        return value;
    }

    public void setValue(List<ValueEntity> value) {
        this.value = value;
    }

    public static class ValueEntity extends Entity{
        private int id;
        private String createTime;
        private String modifyTime;
        private String orderId;
        private int goodsId;
        private int userId;
        private int orderCommentsId;
        private BigDecimal goodsScore;
        private String goodsScoreComments;
        /**回复内容**/
        private String replyContent;
        /**
         * id : 5
         * createTime : null
         * modifyTime : null
         * name : 国栋
         * mobile : 13256975859
         * pwd :
         * headerImg : null
         * regTime : null
         * lastLoginTime : null
         * channel : null
         * token : null
         * userToken : null
         * city : null
         */

        private AppUserEntity appUser;

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

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getOrderCommentsId() {
            return orderCommentsId;
        }

        public void setOrderCommentsId(int orderCommentsId) {
            this.orderCommentsId = orderCommentsId;
        }

        public BigDecimal getGoodsScore() {
            return goodsScore;
        }

        public void setGoodsScore(BigDecimal goodsScore) {
            this.goodsScore = goodsScore;
        }

        public String getGoodsScoreComments() {
            return goodsScoreComments;
        }

        public void setGoodsScoreComments(String goodsScoreComments) {
            this.goodsScoreComments = goodsScoreComments;
        }

        public String getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }

        public AppUserEntity getAppUser() {
            return appUser;
        }

        public void setAppUser(AppUserEntity appUser) {
            this.appUser = appUser;
        }

        public static class AppUserEntity {
            private int id;
            private Object createTime;
            private Object modifyTime;
            private String name;
            private String mobile;
            private String pwd;
            private String headerImg;
            private Object regTime;
            private Object lastLoginTime;
            private Object channel;
            private Object token;
            private Object userToken;
            private Object city;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public Object getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(Object modifyTime) {
                this.modifyTime = modifyTime;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getPwd() {
                return pwd;
            }

            public void setPwd(String pwd) {
                this.pwd = pwd;
            }

            public String getHeaderImg() {
                return headerImg;
            }

            public void setHeaderImg(String headerImg) {
                this.headerImg = headerImg;
            }

            public Object getRegTime() {
                return regTime;
            }

            public void setRegTime(Object regTime) {
                this.regTime = regTime;
            }

            public Object getLastLoginTime() {
                return lastLoginTime;
            }

            public void setLastLoginTime(Object lastLoginTime) {
                this.lastLoginTime = lastLoginTime;
            }

            public Object getChannel() {
                return channel;
            }

            public void setChannel(Object channel) {
                this.channel = channel;
            }

            public Object getToken() {
                return token;
            }

            public void setToken(Object token) {
                this.token = token;
            }

            public Object getUserToken() {
                return userToken;
            }

            public void setUserToken(Object userToken) {
                this.userToken = userToken;
            }

            public Object getCity() {
                return city;
            }

            public void setCity(Object city) {
                this.city = city;
            }
        }
    }
}

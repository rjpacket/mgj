package com.project.mgjandroid.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ning on 2016/4/1.
 */
public class MerchantEvaluateModel extends Entity {

    /**
     * code : 0
     * uuid : 866329020175994
     * value : [{"id":17,"createTime":"2016-03-31 20:21:16","modifyTime":"2016-03-31 20:21:16","orderId":"2016033100004803","merchantId":1,"userId":5,"merchantScore":1,"merchantComments":"","deliveryCost":10,"deliverymanId":null,"deliverymanScore":3,"deliverymanComments":"","goodsCommentsList":null,"appUser":{"id":5,"createTime":null,"modifyTime":null,"name":"国栋","mobile":"13256975859","pwd":"","headerImg":null,"regTime":null,"lastLoginTime":null,"channel":null,"token":null,"userToken":null,"city":null}},{"id":16,"createTime":"2016-03-31 20:01:34","modifyTime":"2016-03-31 20:01:34","orderId":"2016033100004704","merchantId":1,"userId":12,"merchantScore":5,"merchantComments":"","deliveryCost":20,"deliverymanId":null,"deliverymanScore":5,"deliverymanComments":"","goodsCommentsList":null,"appUser":{"id":12,"createTime":null,"modifyTime":null,"name":"080y0pesAggV","mobile":"18810718147","pwd":"","headerImg":"20160323034759741.jpg","regTime":null,"lastLoginTime":null,"channel":null,"token":null,"userToken":null,"city":null}},{"id":15,"createTime":"2016-03-31 17:00:09","modifyTime":"2016-03-31 17:00:09","orderId":"2016033000004102","merchantId":1,"userId":2,"merchantScore":5,"merchantComments":"很好","deliveryCost":20,"deliverymanId":null,"deliverymanScore":5,"deliverymanComments":null,"goodsCommentsList":null,"appUser":{"id":2,"createTime":null,"modifyTime":null,"name":"123","mobile":"18501967780","pwd":"","headerImg":"20160318084927510.jpg","regTime":null,"lastLoginTime":null,"channel":null,"token":null,"userToken":null,"city":null}},{"id":14,"createTime":"2016-03-31 16:53:28","modifyTime":"2016-03-31 16:53:28","orderId":"2016033000004104","merchantId":1,"userId":2,"merchantScore":5,"merchantComments":"","deliveryCost":20,"deliverymanId":null,"deliverymanScore":5,"deliverymanComments":null,"goodsCommentsList":null,"appUser":{"id":2,"createTime":null,"modifyTime":null,"name":"123","mobile":"18501967780","pwd":"","headerImg":"20160318084927510.jpg","regTime":null,"lastLoginTime":null,"channel":null,"token":null,"userToken":null,"city":null}},{"id":13,"createTime":"2016-03-31 14:28:56","modifyTime":"2016-03-31 14:28:56","orderId":"2016033100004206","merchantId":1,"userId":5,"merchantScore":5,"merchantComments":"完美","deliveryCost":20,"deliverymanId":null,"deliverymanScore":5,"deliverymanComments":null,"goodsCommentsList":null,"appUser":{"id":5,"createTime":null,"modifyTime":null,"name":"国栋","mobile":"13256975859","pwd":"","headerImg":null,"regTime":null,"lastLoginTime":null,"channel":null,"token":null,"userToken":null,"city":null}}]
     * success : true
     * servertime : 2016-04-01 11:28:59
     */

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;
    /**
     * id : 17
     * createTime : 2016-03-31 20:21:16
     * modifyTime : 2016-03-31 20:21:16
     * orderId : 2016033100004803
     * merchantId : 1
     * userId : 5
     * merchantScore : 1.0
     * merchantComments :
     * deliveryCost : 10
     * deliverymanId : null
     * deliverymanScore : 3.0
     * deliverymanComments :
     * goodsCommentsList : null
     * appUser : {"id":5,"createTime":null,"modifyTime":null,"name":"国栋","mobile":"13256975859","pwd":"","headerImg":null,"regTime":null,"lastLoginTime":null,"channel":null,"token":null,"userToken":null,"city":null}
     */

    private ArrayList<ValueEntity> value;

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

    public ArrayList<ValueEntity> getValue() {
        return value;
    }

    public void setValue(ArrayList<ValueEntity> value) {
        this.value = value;
    }

    public static class ValueEntity {
        private int id;
        private String createTime;
        private String modifyTime;
        private String orderId;
        private int merchantId;
        private int userId;
        private float merchantScore;
        private String merchantComments;
        private int deliveryCost;
        private Object deliverymanId;
        private double deliverymanScore;
        private String deliverymanComments;
        private Object goodsCommentsList;
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

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public float getMerchantScore() {
            return merchantScore;
        }

        public void setMerchantScore(float merchantScore) {
            this.merchantScore = merchantScore;
        }

        public String getMerchantComments() {
            return merchantComments;
        }

        public void setMerchantComments(String merchantComments) {
            this.merchantComments = merchantComments;
        }

        public int getDeliveryCost() {
            return deliveryCost;
        }

        public void setDeliveryCost(int deliveryCost) {
            this.deliveryCost = deliveryCost;
        }

        public Object getDeliverymanId() {
            return deliverymanId;
        }

        public void setDeliverymanId(Object deliverymanId) {
            this.deliverymanId = deliverymanId;
        }

        public double getDeliverymanScore() {
            return deliverymanScore;
        }

        public void setDeliverymanScore(double deliverymanScore) {
            this.deliverymanScore = deliverymanScore;
        }

        public String getDeliverymanComments() {
            return deliverymanComments;
        }

        public void setDeliverymanComments(String deliverymanComments) {
            this.deliverymanComments = deliverymanComments;
        }

        public Object getGoodsCommentsList() {
            return goodsCommentsList;
        }

        public void setGoodsCommentsList(Object goodsCommentsList) {
            this.goodsCommentsList = goodsCommentsList;
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
            private String createTime;
            private String modifyTime;
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

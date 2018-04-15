package com.project.mgjandroid.model;

import java.util.List;

/**
 * Created by User_Cjh on 2016/3/31.
 */
public class OrderEvaluateModel extends Entity{
    /**
     * code : 0
     * uuid : 867994023030857
     * value : {"id":15,"createTime":"2016-03-31 17:00:08","modifyTime":"2016-03-31 17:00:08","orderId":"2016033000004102","merchantId":1,"userId":2,"merchantScore":5,"merchantComments":"很好","deliveryCost":20,"deliverymanId":null,"deliverymanScore":5,"deliverymanComments":null,"goodsCommentsList":[{"id":11,"createTime":"2016-03-31 17:00:08","modifyTime":"2016-03-31 17:00:08","orderId":"2016033000004102","goodsId":8,"userId":2,"orderCommentsId":8,"goodsScore":5,"goodsScoreComments":"","appUser":null}],"appUser":null}
     * success : true
     */

    private int code;
    private String uuid;
    /**
     * id : 15
     * createTime : 2016-03-31 17:00:08
     * modifyTime : 2016-03-31 17:00:08
     * orderId : 2016033000004102
     * merchantId : 1
     * userId : 2
     * merchantScore : 5
     * merchantComments : 很好
     * deliveryCost : 20
     * deliverymanId : null
     * deliverymanScore : 5
     * deliverymanComments : null
     * goodsCommentsList : [{"id":11,"createTime":"2016-03-31 17:00:08","modifyTime":"2016-03-31 17:00:08","orderId":"2016033000004102","goodsId":8,"userId":2,"orderCommentsId":8,"goodsScore":5,"goodsScoreComments":"","appUser":null}]
     * appUser : null
     */

    private ValueBean value;
    private boolean success;

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

    public ValueBean getValue() {
        return value;
    }

    public void setValue(ValueBean value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class ValueBean {
        private int id;
        private String createTime;
        private String modifyTime;
        private String orderId;
        private int merchantId;
        private int userId;
        private int merchantScore;
        private String merchantComments;
        private int deliveryCost;
        private Object deliverymanId;
        private int deliverymanScore;
        private Object deliverymanComments;
        private Object appUser;
        /**
         * id : 11
         * createTime : 2016-03-31 17:00:08
         * modifyTime : 2016-03-31 17:00:08
         * orderId : 2016033000004102
         * goodsId : 8
         * userId : 2
         * orderCommentsId : 8
         * goodsScore : 5
         * goodsScoreComments :
         * appUser : null
         */

        private List<GoodsCommentsListBean> goodsCommentsList;

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

        public int getMerchantScore() {
            return merchantScore;
        }

        public void setMerchantScore(int merchantScore) {
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

        public int getDeliverymanScore() {
            return deliverymanScore;
        }

        public void setDeliverymanScore(int deliverymanScore) {
            this.deliverymanScore = deliverymanScore;
        }

        public Object getDeliverymanComments() {
            return deliverymanComments;
        }

        public void setDeliverymanComments(Object deliverymanComments) {
            this.deliverymanComments = deliverymanComments;
        }

        public Object getAppUser() {
            return appUser;
        }

        public void setAppUser(Object appUser) {
            this.appUser = appUser;
        }

        public List<GoodsCommentsListBean> getGoodsCommentsList() {
            return goodsCommentsList;
        }

        public void setGoodsCommentsList(List<GoodsCommentsListBean> goodsCommentsList) {
            this.goodsCommentsList = goodsCommentsList;
        }

        public static class GoodsCommentsListBean {
            private int id;
            private String createTime;
            private String modifyTime;
            private String orderId;
            private int goodsId;
            private int userId;
            private int orderCommentsId;
            private int goodsScore;
            private String goodsScoreComments;
            private Object appUser;

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

            public int getGoodsScore() {
                return goodsScore;
            }

            public void setGoodsScore(int goodsScore) {
                this.goodsScore = goodsScore;
            }

            public String getGoodsScoreComments() {
                return goodsScoreComments;
            }

            public void setGoodsScoreComments(String goodsScoreComments) {
                this.goodsScoreComments = goodsScoreComments;
            }

            public Object getAppUser() {
                return appUser;
            }

            public void setAppUser(Object appUser) {
                this.appUser = appUser;
            }
        }
    }
}

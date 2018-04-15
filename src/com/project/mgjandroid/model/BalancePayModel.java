package com.project.mgjandroid.model;

import java.util.List;

/**
 * Created by ning on 2016/3/28.
 */
public class BalancePayModel extends Entity {

    /**
     * code : 0
     * uuid : A00000552D1815
     * value : {"createTime":"2016-03-28 11:58:14","modifyTime":"2016-03-28 12:01:52","id":"2016032800002823","merchantId":1,"merchant":null,"agentId":1,"userId":2,"userAddressId":3,"userAddress":"华杰大厦A","userHouseNumber":"北京市海淀区西道口北街","userName":"金","userGender":"先生","userMobile":"18501967780","userBackupMobile":null,"userLongitude":116.34799064921874,"userLatitude":39.97045128307467,"promoInfoJson":"[]","shipmentType":1,"caution":null,"itemsPrice":0.01,"boxPrice":0,"shippingFee":0,"originalTotalPrice":0.01,"totalPrice":0.01,"expectArrivalTime":"1","transactionId":null,"paymentType":1,"paymentState":1,"hasPayed":0.01,"orderFlowStatus":1,"hasDel":0,"orderItems":[]}
     * success : true
     */

    private int code;
    private String uuid;
    /**
     * createTime : 2016-03-28 11:58:14
     * modifyTime : 2016-03-28 12:01:52
     * id : 2016032800002823
     * merchantId : 1
     * merchant : null
     * agentId : 1
     * userId : 2
     * userAddressId : 3
     * userAddress : 华杰大厦A
     * userHouseNumber : 北京市海淀区西道口北街
     * userName : 金
     * userGender : 先生
     * userMobile : 18501967780
     * userBackupMobile : null
     * userLongitude : 116.34799064921874
     * userLatitude : 39.97045128307467
     * promoInfoJson : []
     * shipmentType : 1
     * caution : null
     * itemsPrice : 0.01
     * boxPrice : 0.0
     * shippingFee : 0.0
     * originalTotalPrice : 0.01
     * totalPrice : 0.01
     * expectArrivalTime : 1
     * transactionId : null
     * paymentType : 1
     * paymentState : 1
     * hasPayed : 0.01
     * orderFlowStatus : 1
     * hasDel : 0
     * orderItems : []
     */

    private ValueEntity value;
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

    public static class ValueEntity {
        private String createTime;
        private String modifyTime;
        private String id;
        private int merchantId;
        private Object merchant;
        private int agentId;
        private int userId;
        private int userAddressId;
        private String userAddress;
        private String userHouseNumber;
        private String userName;
        private String userGender;
        private String userMobile;
        private Object userBackupMobile;
        private double userLongitude;
        private double userLatitude;
        private String promoInfoJson;
        private int shipmentType;
        private Object caution;
        private double itemsPrice;
        private double boxPrice;
        private double shippingFee;
        private double originalTotalPrice;
        private double totalPrice;
        private String expectArrivalTime;
        private Object transactionId;
        private int paymentType;
        private int paymentState;
        private double hasPayed;
        private int orderFlowStatus;
        private int hasDel;
        private List<?> orderItems;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public Object getMerchant() {
            return merchant;
        }

        public void setMerchant(Object merchant) {
            this.merchant = merchant;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getUserAddressId() {
            return userAddressId;
        }

        public void setUserAddressId(int userAddressId) {
            this.userAddressId = userAddressId;
        }

        public String getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public String getUserHouseNumber() {
            return userHouseNumber;
        }

        public void setUserHouseNumber(String userHouseNumber) {
            this.userHouseNumber = userHouseNumber;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserGender() {
            return userGender;
        }

        public void setUserGender(String userGender) {
            this.userGender = userGender;
        }

        public String getUserMobile() {
            return userMobile;
        }

        public void setUserMobile(String userMobile) {
            this.userMobile = userMobile;
        }

        public Object getUserBackupMobile() {
            return userBackupMobile;
        }

        public void setUserBackupMobile(Object userBackupMobile) {
            this.userBackupMobile = userBackupMobile;
        }

        public double getUserLongitude() {
            return userLongitude;
        }

        public void setUserLongitude(double userLongitude) {
            this.userLongitude = userLongitude;
        }

        public double getUserLatitude() {
            return userLatitude;
        }

        public void setUserLatitude(double userLatitude) {
            this.userLatitude = userLatitude;
        }

        public String getPromoInfoJson() {
            return promoInfoJson;
        }

        public void setPromoInfoJson(String promoInfoJson) {
            this.promoInfoJson = promoInfoJson;
        }

        public int getShipmentType() {
            return shipmentType;
        }

        public void setShipmentType(int shipmentType) {
            this.shipmentType = shipmentType;
        }

        public Object getCaution() {
            return caution;
        }

        public void setCaution(Object caution) {
            this.caution = caution;
        }

        public double getItemsPrice() {
            return itemsPrice;
        }

        public void setItemsPrice(double itemsPrice) {
            this.itemsPrice = itemsPrice;
        }

        public double getBoxPrice() {
            return boxPrice;
        }

        public void setBoxPrice(double boxPrice) {
            this.boxPrice = boxPrice;
        }

        public double getShippingFee() {
            return shippingFee;
        }

        public void setShippingFee(double shippingFee) {
            this.shippingFee = shippingFee;
        }

        public double getOriginalTotalPrice() {
            return originalTotalPrice;
        }

        public void setOriginalTotalPrice(double originalTotalPrice) {
            this.originalTotalPrice = originalTotalPrice;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getExpectArrivalTime() {
            return expectArrivalTime;
        }

        public void setExpectArrivalTime(String expectArrivalTime) {
            this.expectArrivalTime = expectArrivalTime;
        }

        public Object getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(Object transactionId) {
            this.transactionId = transactionId;
        }

        public int getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(int paymentType) {
            this.paymentType = paymentType;
        }

        public int getPaymentState() {
            return paymentState;
        }

        public void setPaymentState(int paymentState) {
            this.paymentState = paymentState;
        }

        public double getHasPayed() {
            return hasPayed;
        }

        public void setHasPayed(double hasPayed) {
            this.hasPayed = hasPayed;
        }

        public int getOrderFlowStatus() {
            return orderFlowStatus;
        }

        public void setOrderFlowStatus(int orderFlowStatus) {
            this.orderFlowStatus = orderFlowStatus;
        }

        public int getHasDel() {
            return hasDel;
        }

        public void setHasDel(int hasDel) {
            this.hasDel = hasDel;
        }

        public List<?> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<?> orderItems) {
            this.orderItems = orderItems;
        }
    }
}

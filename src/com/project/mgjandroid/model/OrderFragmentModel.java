package com.project.mgjandroid.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ning on 2016/3/15.
 */
public class OrderFragmentModel extends Entity {

    private int code;
    private String uuid;
    private boolean success;
    private String servertime;

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
        private String createTime;
        private String modifyTime;
        private String id;
        private int merchantId;

        private MerchantEntity merchant;
        private int agentId;
        private int userId;
        private int userAddressId;
        private String userAddress;
        private String userHouseNumber;
        private String userName;
        private String userGender;
        private String userMobile;
        private String userBackupMobile;
        private double userLongitude;
        private double userLatitude;
        private Object promoInfoJson;
        private int shipmentType;
        private Object caution;
        private BigDecimal itemsPrice;
        private BigDecimal boxPrice;
        private BigDecimal shippingFee;
        private BigDecimal originalTotalPrice;
        private BigDecimal totalPrice;
        private String expectArrivalTime;
        private Object transactionId;
        private int paymentType;
        private int paymentState;
        private String paymentExpireTime;
        private BigDecimal hasPayed;
        private int orderFlowStatus;
        private int hasDel;
        private Object deliveryTaskId;
        private Object deliveryTask;
        private int hasComments;
        private String serverTime;

        private List<OrderItemsEntity> orderItems;

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

        public MerchantEntity getMerchant() {
            return merchant;
        }

        public void setMerchant(MerchantEntity merchant) {
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

        public String getUserBackupMobile() {
            return userBackupMobile;
        }

        public void setUserBackupMobile(String userBackupMobile) {
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

        public Object getPromoInfoJson() {
            return promoInfoJson;
        }

        public void setPromoInfoJson(Object promoInfoJson) {
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

        public BigDecimal getItemsPrice() {
            return itemsPrice;
        }

        public void setItemsPrice(BigDecimal itemsPrice) {
            this.itemsPrice = itemsPrice;
        }

        public BigDecimal getBoxPrice() {
            return boxPrice;
        }

        public void setBoxPrice(BigDecimal boxPrice) {
            this.boxPrice = boxPrice;
        }

        public BigDecimal getShippingFee() {
            return shippingFee;
        }

        public void setShippingFee(BigDecimal shippingFee) {
            this.shippingFee = shippingFee;
        }

        public BigDecimal getOriginalTotalPrice() {
            return originalTotalPrice;
        }

        public void setOriginalTotalPrice(BigDecimal originalTotalPrice) {
            this.originalTotalPrice = originalTotalPrice;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
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

        public String getPaymentExpireTime() {
            return paymentExpireTime;
        }

        public void setPaymentExpireTime(String paymentExpireTime) {
            this.paymentExpireTime = paymentExpireTime;
        }

        public BigDecimal getHasPayed() {
            return hasPayed;
        }

        public void setHasPayed(BigDecimal hasPayed) {
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

        public Object getDeliveryTaskId() {
            return deliveryTaskId;
        }

        public void setDeliveryTaskId(Object deliveryTaskId) {
            this.deliveryTaskId = deliveryTaskId;
        }

        public Object getDeliveryTask() {
            return deliveryTask;
        }

        public void setDeliveryTask(Object deliveryTask) {
            this.deliveryTask = deliveryTask;
        }

        public int getHasComments() {
            return hasComments;
        }

        public void setHasComments(int hasComments) {
            this.hasComments = hasComments;
        }

        public List<OrderItemsEntity> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<OrderItemsEntity> orderItems) {
            this.orderItems = orderItems;
        }

        public String getServerTime() {
            return serverTime;
        }

        public void setServerTime(String serverTime) {
            this.serverTime = serverTime;
        }

        public static class MerchantEntity extends Entity{
            private long id;
            private String createTime;
            private String modifyTime;
            private long agentId;
            private String name;
            private String description;
            private String contacts;
            private int merchantStatus;
            private String payments;
            private int shipmentMode;
            private int limitDistance;
            private String province;
            private String city;
            private String district;
            private String address;
            private double longitude;
            private double latitude;
            private String geohash;
            private String logo;
            private String imgs;
            private String workingTime;
            private String broadcast;
            private BigDecimal minPrice;
            private BigDecimal shipFee;
            private int invoiceSupport;
            private BigDecimal minInvoicePrice;
            private int reserveSupport;
            private int reserveDays;
            private int prepareTime;
            private int avgDeliveryTime;
            private int hasTerminal;
            private int showPraise;
            private int showHotsale;
            private int commentNum;
            private BigDecimal averageScore;
            private double shipScore;
            private int orderQuantity;
            private int status;
            private double agentRate;
            private Object sysRate;
            private Object distance;
            private Object promotions;
            private Object merchantUser;

            public long getId() {
                return id;
            }

            public void setId(long id) {
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

            public long getAgentId() {
                return agentId;
            }

            public void setAgentId(long agentId) {
                this.agentId = agentId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getContacts() {
                return contacts;
            }

            public void setContacts(String contacts) {
                this.contacts = contacts;
            }

            public int getMerchantStatus() {
                return merchantStatus;
            }

            public void setMerchantStatus(int merchantStatus) {
                this.merchantStatus = merchantStatus;
            }

            public String getPayments() {
                return payments;
            }

            public void setPayments(String payments) {
                this.payments = payments;
            }

            public int getShipmentMode() {
                return shipmentMode;
            }

            public void setShipmentMode(int shipmentMode) {
                this.shipmentMode = shipmentMode;
            }

            public int getLimitDistance() {
                return limitDistance;
            }

            public void setLimitDistance(int limitDistance) {
                this.limitDistance = limitDistance;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public String getGeohash() {
                return geohash;
            }

            public void setGeohash(String geohash) {
                this.geohash = geohash;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public String getWorkingTime() {
                return workingTime;
            }

            public void setWorkingTime(String workingTime) {
                this.workingTime = workingTime;
            }

            public String getBroadcast() {
                return broadcast;
            }

            public void setBroadcast(String broadcast) {
                this.broadcast = broadcast;
            }

            public BigDecimal getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(BigDecimal minPrice) {
                this.minPrice = minPrice;
            }

            public BigDecimal getShipFee() {
                return shipFee;
            }

            public void setShipFee(BigDecimal shipFee) {
                this.shipFee = shipFee;
            }

            public int getInvoiceSupport() {
                return invoiceSupport;
            }

            public void setInvoiceSupport(int invoiceSupport) {
                this.invoiceSupport = invoiceSupport;
            }

            public BigDecimal getMinInvoicePrice() {
                return minInvoicePrice;
            }

            public void setMinInvoicePrice(BigDecimal minInvoicePrice) {
                this.minInvoicePrice = minInvoicePrice;
            }

            public int getReserveSupport() {
                return reserveSupport;
            }

            public void setReserveSupport(int reserveSupport) {
                this.reserveSupport = reserveSupport;
            }

            public int getReserveDays() {
                return reserveDays;
            }

            public void setReserveDays(int reserveDays) {
                this.reserveDays = reserveDays;
            }

            public int getPrepareTime() {
                return prepareTime;
            }

            public void setPrepareTime(int prepareTime) {
                this.prepareTime = prepareTime;
            }

            public int getAvgDeliveryTime() {
                return avgDeliveryTime;
            }

            public void setAvgDeliveryTime(int avgDeliveryTime) {
                this.avgDeliveryTime = avgDeliveryTime;
            }

            public int getHasTerminal() {
                return hasTerminal;
            }

            public void setHasTerminal(int hasTerminal) {
                this.hasTerminal = hasTerminal;
            }

            public int getShowPraise() {
                return showPraise;
            }

            public void setShowPraise(int showPraise) {
                this.showPraise = showPraise;
            }

            public int getShowHotsale() {
                return showHotsale;
            }

            public void setShowHotsale(int showHotsale) {
                this.showHotsale = showHotsale;
            }

            public int getCommentNum() {
                return commentNum;
            }

            public void setCommentNum(int commentNum) {
                this.commentNum = commentNum;
            }

            public BigDecimal getAverageScore() {
                return averageScore;
            }

            public void setAverageScore(BigDecimal averageScore) {
                this.averageScore = averageScore;
            }

            public double getShipScore() {
                return shipScore;
            }

            public void setShipScore(double shipScore) {
                this.shipScore = shipScore;
            }

            public int getOrderQuantity() {
                return orderQuantity;
            }

            public void setOrderQuantity(int orderQuantity) {
                this.orderQuantity = orderQuantity;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public double getAgentRate() {
                return agentRate;
            }

            public void setAgentRate(double agentRate) {
                this.agentRate = agentRate;
            }

            public Object getSysRate() {
                return sysRate;
            }

            public void setSysRate(Object sysRate) {
                this.sysRate = sysRate;
            }

            public Object getDistance() {
                return distance;
            }

            public void setDistance(Object distance) {
                this.distance = distance;
            }

            public Object getPromotions() {
                return promotions;
            }

            public void setPromotions(Object promotions) {
                this.promotions = promotions;
            }

            public Object getMerchantUser() {
                return merchantUser;
            }

            public void setMerchantUser(Object merchantUser) {
                this.merchantUser = merchantUser;
            }
        }

        public static class OrderItemsEntity extends Entity{
            private int id;
            private String createTime;
            private String modifyTime;
            private String orderId;
            private int goodsId;
            private int goodsSpecId;
            private String name;
            private String spec;
            private String unit;
            private int quantity;
            private BigDecimal originPrice;
            private BigDecimal price;
            private BigDecimal totalPrice;
            private BigDecimal boxPrice;
            private BigDecimal totalBoxPrice;
            private int isPromo;
            private String tip;
            private int rating;
            private boolean isShow;
            private String content;

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

            public int getGoodsSpecId() {
                return goodsSpecId;
            }

            public void setGoodsSpecId(int goodsSpecId) {
                this.goodsSpecId = goodsSpecId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSpec() {
                return spec;
            }

            public void setSpec(String spec) {
                this.spec = spec;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public BigDecimal getOriginPrice() {
                return originPrice;
            }

            public void setOriginPrice(BigDecimal originPrice) {
                this.originPrice = originPrice;
            }

            public BigDecimal getPrice() {
                return price;
            }

            public void setPrice(BigDecimal price) {
                this.price = price;
            }

            public BigDecimal getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(BigDecimal totalPrice) {
                this.totalPrice = totalPrice;
            }

            public BigDecimal getBoxPrice() {
                return boxPrice;
            }

            public void setBoxPrice(BigDecimal boxPrice) {
                this.boxPrice = boxPrice;
            }

            public BigDecimal getTotalBoxPrice() {
                return totalBoxPrice;
            }

            public void setTotalBoxPrice(BigDecimal totalBoxPrice) {
                this.totalBoxPrice = totalBoxPrice;
            }

            public int getIsPromo() {
                return isPromo;
            }

            public void setIsPromo(int isPromo) {
                this.isPromo = isPromo;
            }

            public String getTip() {
                return tip;
            }

            public void setTip(String tip) {
                this.tip = tip;
            }

            public int getRating() {
                return rating;
            }

            public void setRating(int rating) {
                this.rating = rating;
            }

            public boolean isShow() {
                return isShow;
            }

            public void setIsShow(boolean isShow) {
                this.isShow = isShow;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}

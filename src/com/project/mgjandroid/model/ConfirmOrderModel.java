package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.PromotionActivity;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.bean.UserAddress;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by ning on 2016/3/14.
 */
public class ConfirmOrderModel extends Entity {

    /**
     * code : 0
     * uuid : 866329020175994
     * value : {"userId":null,"merchantId":1,"merchantName":"肯德基(中鼎餐厅)","itemsPrice":33.5,"boxPrice":0,"shippingFee":10,"originalTotalPrice":43.5,"totalPrice":43.5,"tip":"","caution":null,"cautionlist":[],"promoMutexInfo":"","promoList":[],"addressInfo":null,"orderItems":[{"id":4,"specId":4,"name":"香辣鸡腿堡薯条套餐","spec":"","unit":"份","quantity":1,"originPrice":33.5,"price":33.5,"totalPrice":33.5,"boxNum":0,"boxPrice":0,"totalBoxNum":0,"totalBoxPrice":0,"labelUrl":"http://7xpvkm.com1.z0.glb.clouddn.com/1603091844239637195.jpg","isPromo":0,"tip":""}],"expectedArrivalTime":null,"expectedArrival":{"clickable":0,"viewTime":"立即送出(大约17:32到达)","time":"1","selectViewTime":"立即送出(大约17:32到达)","deliveryTimeTip":""},"deliveryTimes":[{"day":"今天(星期六)","times":[{"1":"尽快送达(预计17:32送达)"},{"1458380700000":"17:45"},{"1458381600000":"18:00"},{"1458382500000":"18:15"},{"1458383400000":"18:30"},{"1458384300000":"18:45"},{"1458385200000":"19:00"},{"1458386100000":"19:15"},{"1458387000000":"19:30"},{"1458387900000":"19:45"},{"1458388800000":"20:00"},{"1458389700000":"20:15"},{"1458390600000":"20:30"},{"1458391500000":"20:45"},{"1458392400000":"21:00"},{"1458393300000":"21:15"},{"1458394200000":"21:30"},{"1458395100000":"21:45"}]}],"defaultPayType":1,"payments":[{"paymentName":"在线支付","paymentType":1,"paymentTip":"","paymentExtraInfo":""},{"paymentName":"货到付款","paymentType":2,"paymentTip":"","paymentExtraInfo":""}],"shipmentType":1,"shipmentTip":"本订单由肯德基(中鼎餐厅)提供配送服务","shippingFeeDiscountTip":"","invoiceSupport":null,"minInvoicePrice":null}
     * success : true
     */

    private int code;
    private String uuid;
    /**
     * userId : null
     * merchantId : 1
     * merchantName : 肯德基(中鼎餐厅)
     * itemsPrice : 33.5
     * boxPrice : 0.0
     * shippingFee : 10.0
     * originalTotalPrice : 43.5
     * totalPrice : 43.5
     * tip :
     * caution : null
     * cautionlist : []
     * promoMutexInfo :
     * promoList : []
     * addressInfo : null
     * orderItems : [{"id":4,"specId":4,"name":"香辣鸡腿堡薯条套餐","spec":"","unit":"份","quantity":1,"originPrice":33.5,"price":33.5,"totalPrice":33.5,"boxNum":0,"boxPrice":0,"totalBoxNum":0,"totalBoxPrice":0,"labelUrl":"http://7xpvkm.com1.z0.glb.clouddn.com/1603091844239637195.jpg","isPromo":0,"tip":""}]
     * expectedArrivalTime : null
     * expectedArrival : {"clickable":0,"viewTime":"立即送出(大约17:32到达)","time":"1","selectViewTime":"立即送出(大约17:32到达)","deliveryTimeTip":""}
     * deliveryTimes : [{"day":"今天(星期六)","times":[{"1":"尽快送达(预计17:32送达)"},{"1458380700000":"17:45"},{"1458381600000":"18:00"},{"1458382500000":"18:15"},{"1458383400000":"18:30"},{"1458384300000":"18:45"},{"1458385200000":"19:00"},{"1458386100000":"19:15"},{"1458387000000":"19:30"},{"1458387900000":"19:45"},{"1458388800000":"20:00"},{"1458389700000":"20:15"},{"1458390600000":"20:30"},{"1458391500000":"20:45"},{"1458392400000":"21:00"},{"1458393300000":"21:15"},{"1458394200000":"21:30"},{"1458395100000":"21:45"}]}]
     * defaultPayType : 1
     * payments : [{"paymentName":"在线支付","paymentType":1,"paymentTip":"","paymentExtraInfo":""},{"paymentName":"货到付款","paymentType":2,"paymentTip":"","paymentExtraInfo":""}]
     * shipmentType : 1
     * shipmentTip : 本订单由肯德基(中鼎餐厅)提供配送服务
     * shippingFeeDiscountTip :
     * invoiceSupport : null
     * minInvoicePrice : null
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

    public static class ValueEntity extends Entity {
        private Object userId;
        private long merchantId;
        private String merchantName;
        private BigDecimal itemsPrice;
        private BigDecimal boxPrice;
        private BigDecimal shippingFee;
        private BigDecimal originalTotalPrice;
        private BigDecimal totalPrice;
        private String tip;
        private String caution;
        private String promoMutexInfo;
        private UserAddress addressInfo;
        private Object expectedArrivalTime;
        /**
         * clickable : 0
         * viewTime : 立即送出(大约17:32到达)
         * time : 1
         * selectViewTime : 立即送出(大约17:32到达)
         * deliveryTimeTip :
         */

        private ExpectedArrivalEntity expectedArrival;
        private int defaultPayType;
        private int shipmentType;
        private String shipmentTip;
        private String shippingFeeDiscountTip;
        private Object invoiceSupport;
        private Object minInvoicePrice;
        private List<?> cautionlist;
        private List<PromotionActivity> promoList;
        private List<RedBag> redBags;
        private int redBagUsableCount;
        private Integer againOrderStatus;//0 商家已下线 1 商品已下线
        private String againOrderTip;

        /**
         * id : 4
         * specId : 4
         * name : 香辣鸡腿堡薯条套餐
         * spec :
         * unit : 份
         * quantity : 1
         * originPrice : 33.5
         * price : 33.5
         * totalPrice : 33.5
         * boxNum : 0
         * boxPrice : 0.0
         * totalBoxNum : 0
         * totalBoxPrice : 0.0
         * labelUrl : http://7xpvkm.com1.z0.glb.clouddn.com/1603091844239637195.jpg
         * isPromo : 0
         * tip :
         */

        private List<OrderItemsEntity> orderItems;
        /**
         * day : 今天(星期六)
         * times : [{"1":"尽快送达(预计17:32送达)"},{"1458380700000":"17:45"},{"1458381600000":"18:00"},{"1458382500000":"18:15"},{"1458383400000":"18:30"},{"1458384300000":"18:45"},{"1458385200000":"19:00"},{"1458386100000":"19:15"},{"1458387000000":"19:30"},{"1458387900000":"19:45"},{"1458388800000":"20:00"},{"1458389700000":"20:15"},{"1458390600000":"20:30"},{"1458391500000":"20:45"},{"1458392400000":"21:00"},{"1458393300000":"21:15"},{"1458394200000":"21:30"},{"1458395100000":"21:45"}]
         */

        private List<DeliveryTimesEntity> deliveryTimes;
        /**
         * paymentName : 在线支付
         * paymentType : 1
         * paymentTip :
         * paymentExtraInfo :
         */

        private List<PaymentsEntity> payments;

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public void setMerchantId(long merchantId) {
            this.merchantId = merchantId;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public void setItemsPrice(BigDecimal itemsPrice) {
            this.itemsPrice = itemsPrice;
        }

        public void setBoxPrice(BigDecimal boxPrice) {
            this.boxPrice = boxPrice;
        }

        public void setShippingFee(BigDecimal shippingFee) {
            this.shippingFee = shippingFee;
        }

        public void setOriginalTotalPrice(BigDecimal originalTotalPrice) {
            this.originalTotalPrice = originalTotalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public void setCaution(String caution) {
            this.caution = caution;
        }

        public void setPromoMutexInfo(String promoMutexInfo) {
            this.promoMutexInfo = promoMutexInfo;
        }

        public void setAddressInfo(UserAddress addressInfo) {
            this.addressInfo = addressInfo;
        }

        public void setExpectedArrivalTime(Object expectedArrivalTime) {
            this.expectedArrivalTime = expectedArrivalTime;
        }

        public void setExpectedArrival(ExpectedArrivalEntity expectedArrival) {
            this.expectedArrival = expectedArrival;
        }

        public void setDefaultPayType(int defaultPayType) {
            this.defaultPayType = defaultPayType;
        }

        public void setShipmentType(int shipmentType) {
            this.shipmentType = shipmentType;
        }

        public void setShipmentTip(String shipmentTip) {
            this.shipmentTip = shipmentTip;
        }

        public void setShippingFeeDiscountTip(String shippingFeeDiscountTip) {
            this.shippingFeeDiscountTip = shippingFeeDiscountTip;
        }

        public void setInvoiceSupport(Object invoiceSupport) {
            this.invoiceSupport = invoiceSupport;
        }

        public void setMinInvoicePrice(Object minInvoicePrice) {
            this.minInvoicePrice = minInvoicePrice;
        }

        public void setCautionlist(List<?> cautionlist) {
            this.cautionlist = cautionlist;
        }

        public void setPromoList(List<PromotionActivity> promoList) {
            this.promoList = promoList;
        }

        public List<RedBag> getRedBags() {
            return redBags;
        }

        public void setRedBags(List<RedBag> redBags) {
            this.redBags = redBags;
        }

        public int getRedBagUsableCount() {
            return redBagUsableCount;
        }

        public void setRedBagUsableCount(int redBagUsableCount) {
            this.redBagUsableCount = redBagUsableCount;
        }

        public void setOrderItems(List<OrderItemsEntity> orderItems) {
            this.orderItems = orderItems;
        }

        public void setDeliveryTimes(List<DeliveryTimesEntity> deliveryTimes) {
            this.deliveryTimes = deliveryTimes;
        }

        public void setPayments(List<PaymentsEntity> payments) {
            this.payments = payments;
        }

        public Object getUserId() {
            return userId;
        }

        public long getMerchantId() {
            return merchantId;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public BigDecimal getItemsPrice() {
            return itemsPrice;
        }

        public BigDecimal getBoxPrice() {
            return boxPrice;
        }

        public BigDecimal getShippingFee() {
            return shippingFee;
        }

        public BigDecimal getOriginalTotalPrice() {
            return originalTotalPrice;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public String getTip() {
            return tip;
        }

        public String getCaution() {
            return caution;
        }

        public String getPromoMutexInfo() {
            return promoMutexInfo;
        }

        public UserAddress getAddressInfo() {
            return addressInfo;
        }

        public Object getExpectedArrivalTime() {
            return expectedArrivalTime;
        }

        public ExpectedArrivalEntity getExpectedArrival() {
            return expectedArrival;
        }

        public int getDefaultPayType() {
            return defaultPayType;
        }

        public int getShipmentType() {
            return shipmentType;
        }

        public String getShipmentTip() {
            return shipmentTip;
        }

        public String getShippingFeeDiscountTip() {
            return shippingFeeDiscountTip;
        }

        public Object getInvoiceSupport() {
            return invoiceSupport;
        }

        public Object getMinInvoicePrice() {
            return minInvoicePrice;
        }

        public Integer getAgainOrderStatus() {
            return againOrderStatus;
        }

        public void setAgainOrderStatus(Integer againOrderStatus) {
            this.againOrderStatus = againOrderStatus;
        }

        public String getAgainOrderTip() {
            return againOrderTip;
        }

        public void setAgainOrderTip(String againOrderTip) {
            this.againOrderTip = againOrderTip;
        }

        public List<?> getCautionlist() {
            return cautionlist;
        }

        public List<PromotionActivity> getPromoList() {
            return promoList;
        }

        public List<OrderItemsEntity> getOrderItems() {
            return orderItems;
        }

        public List<DeliveryTimesEntity> getDeliveryTimes() {
            return deliveryTimes;
        }

        public List<PaymentsEntity> getPayments() {
            return payments;
        }

        public static class ExpectedArrivalEntity extends Entity{
            private int clickable;
            private String viewTime;
            private String time;
            private String selectViewTime;
            private String deliveryTimeTip;

            public void setClickable(int clickable) {
                this.clickable = clickable;
            }

            public void setViewTime(String viewTime) {
                this.viewTime = viewTime;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public void setSelectViewTime(String selectViewTime) {
                this.selectViewTime = selectViewTime;
            }

            public void setDeliveryTimeTip(String deliveryTimeTip) {
                this.deliveryTimeTip = deliveryTimeTip;
            }

            public int getClickable() {
                return clickable;
            }

            public String getViewTime() {
                return viewTime;
            }

            public String getTime() {
                return time;
            }

            public String getSelectViewTime() {
                return selectViewTime;
            }

            public String getDeliveryTimeTip() {
                return deliveryTimeTip;
            }
        }

        public static class OrderItemsEntity extends Entity{
            private int id;
            private int specId;
            private int categoryId;
            private String name;
            private String spec;
            private String unit;
            private int quantity;
            private BigDecimal originPrice;
            private BigDecimal price;
            private BigDecimal totalPrice;
            private int boxNum;
            private BigDecimal boxPrice;
            private int totalBoxNum;
            private BigDecimal totalBoxPrice;
            private String labelUrl;
            private int isPromo;
            private String tip;

            public int getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setSpecId(int specId) {
                this.specId = specId;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setSpec(String spec) {
                this.spec = spec;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public void setOriginPrice(BigDecimal originPrice) {
                this.originPrice = originPrice;
            }

            public void setPrice(BigDecimal price) {
                this.price = price;
            }

            public void setTotalPrice(BigDecimal totalPrice) {
                this.totalPrice = totalPrice;
            }

            public void setBoxNum(int boxNum) {
                this.boxNum = boxNum;
            }

            public void setBoxPrice(BigDecimal boxPrice) {
                this.boxPrice = boxPrice;
            }

            public void setTotalBoxNum(int totalBoxNum) {
                this.totalBoxNum = totalBoxNum;
            }

            public void setTotalBoxPrice(BigDecimal totalBoxPrice) {
                this.totalBoxPrice = totalBoxPrice;
            }

            public void setLabelUrl(String labelUrl) {
                this.labelUrl = labelUrl;
            }

            public void setIsPromo(int isPromo) {
                this.isPromo = isPromo;
            }

            public void setTip(String tip) {
                this.tip = tip;
            }

            public int getId() {
                return id;
            }

            public int getSpecId() {
                return specId;
            }

            public String getName() {
                return name;
            }

            public String getSpec() {
                return spec;
            }

            public String getUnit() {
                return unit;
            }

            public int getQuantity() {
                return quantity;
            }

            public BigDecimal getOriginPrice() {
                return originPrice;
            }

            public BigDecimal getPrice() {
                return price;
            }

            public BigDecimal getTotalPrice() {
                return totalPrice;
            }

            public int getBoxNum() {
                return boxNum;
            }

            public BigDecimal getBoxPrice() {
                return boxPrice;
            }

            public int getTotalBoxNum() {
                return totalBoxNum;
            }

            public BigDecimal getTotalBoxPrice() {
                return totalBoxPrice;
            }

            public String getLabelUrl() {
                return labelUrl;
            }

            public int getIsPromo() {
                return isPromo;
            }

            public String getTip() {
                return tip;
            }
        }

        public static class DeliveryTimesEntity extends Entity{
            private String day;
            private List<Map<String,String>> times;

            public void setDay(String day) {
                this.day = day;
            }

            public String getDay() {
                return day;
            }

            public List<Map<String, String>> getTimes() {
                return times;
            }

            public void setTimes(List<Map<String, String>> times) {
                this.times = times;
            }
        }

        public static class PaymentsEntity extends Entity{
            private String paymentName;
            private int paymentType;
            private String paymentTip;
            private String paymentExtraInfo;

            public void setPaymentName(String paymentName) {
                this.paymentName = paymentName;
            }

            public void setPaymentType(int paymentType) {
                this.paymentType = paymentType;
            }

            public void setPaymentTip(String paymentTip) {
                this.paymentTip = paymentTip;
            }

            public void setPaymentExtraInfo(String paymentExtraInfo) {
                this.paymentExtraInfo = paymentExtraInfo;
            }

            public String getPaymentName() {
                return paymentName;
            }

            public int getPaymentType() {
                return paymentType;
            }

            public String getPaymentTip() {
                return paymentTip;
            }

            public String getPaymentExtraInfo() {
                return paymentExtraInfo;
            }
        }
    }
}

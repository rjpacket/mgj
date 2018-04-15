package com.project.mgjandroid.model;

import java.util.List;

/**
 * Created by ning on 2016/3/25.
 */
public class MerchantFilterModel extends Entity {

    /**
     * code : 0
     * uuid : 866329020175994
     * value : {"shipmentList":[{"id":"","name":"不限"},{"id":"1","name":"商家配送"},{"id":"2","name":"第三方配送"}],"merchantPropertyList":[{"id":1,"name":"品牌馆","icon":"http://7xpvkm.com1.z0.glb.clouddn.com/1603251023595913678.png"},{"id":2,"name":"外卖保","icon":"http://7xpvkm.com1.z0.glb.clouddn.com/1603251023593294739.png"},{"id":3,"name":"新店","icon":"http://7xpvkm.com1.z0.glb.clouddn.com/1603251023595913678.png"},{"id":4,"name":"开发票","icon":"http://7xpvkm.com1.z0.glb.clouddn.com/1603251023596553688.png"},{"id":5,"name":"在线支付","icon":"http://7xpvkm.com1.z0.glb.clouddn.com/1603251023597227703.png"},{"id":6,"name":"超时赔付","icon":"http://7xpvkm.com1.z0.glb.clouddn.com/1603251023595003314.png"}],"promotionList":[{"id":1,"createTime":"2016-03-10 16:03:31","modifyTime":"2016-03-10 17:16:59","name":"首次使用满减","icon":"http://7xpvkm.com1.z0.glb.clouddn.com/1603091850305173314.png","sortNo":1,"type":1,"hasDel":0},{"id":2,"createTime":"2016-03-10 16:05:51","modifyTime":"2016-03-10 16:05:51","name":"满减活动","icon":"http://7xpvkm.com1.z0.glb.clouddn.com/1603091850304025292.png","sortNo":2,"type":2,"hasDel":0},{"id":3,"createTime":"2016-03-10 16:06:18","modifyTime":"2016-03-10 16:06:18","name":"超时赔偿","icon":"http://7xpvkm.com1.z0.glb.clouddn.com/1603091850304582659.png","sortNo":3,"type":3,"hasDel":0}]}
     * success : true
     */

    private int code;
    private String uuid;
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
        /**
         * id :
         * name : 不限
         */

        private List<ShipmentListEntity> shipmentList;
        /**
         * id : 1
         * name : 品牌馆
         * icon : http://7xpvkm.com1.z0.glb.clouddn.com/1603251023595913678.png
         */

        private List<MerchantPropertyListEntity> merchantPropertyList;
        /**
         * id : 1
         * createTime : 2016-03-10 16:03:31
         * modifyTime : 2016-03-10 17:16:59
         * name : 首次使用满减
         * icon : http://7xpvkm.com1.z0.glb.clouddn.com/1603091850305173314.png
         * sortNo : 1
         * type : 1
         * hasDel : 0
         */

        private List<PromotionListEntity> promotionList;

        public List<ShipmentListEntity> getShipmentList() {
            return shipmentList;
        }

        public void setShipmentList(List<ShipmentListEntity> shipmentList) {
            this.shipmentList = shipmentList;
        }

        public List<MerchantPropertyListEntity> getMerchantPropertyList() {
            return merchantPropertyList;
        }

        public void setMerchantPropertyList(List<MerchantPropertyListEntity> merchantPropertyList) {
            this.merchantPropertyList = merchantPropertyList;
        }

        public List<PromotionListEntity> getPromotionList() {
            return promotionList;
        }

        public void setPromotionList(List<PromotionListEntity> promotionList) {
            this.promotionList = promotionList;
        }

        public static class ShipmentListEntity {
            private String id;
            private String name;
            private boolean isCheck;
            private boolean isConfirm;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public boolean isCheck() {
                return isCheck;
            }

            public void setIsCheck(boolean isCheck) {
                this.isCheck = isCheck;
            }

            public boolean isConfirm() {
                return isConfirm;
            }

            public void setIsConfirm(boolean isConfirm) {
                this.isConfirm = isConfirm;
            }
        }

        public static class MerchantPropertyListEntity {
            private int id;
            private String name;
            private String icon;
            private boolean isCheck;
            private boolean isConfirm;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public boolean isCheck() {
                return isCheck;
            }

            public void setIsCheck(boolean isCheck) {
                this.isCheck = isCheck;
            }

            public boolean isConfirm() {
                return isConfirm;
            }

            public void setIsConfirm(boolean isConfirm) {
                this.isConfirm = isConfirm;
            }
        }

        public static class PromotionListEntity {
            private int id;
            private String createTime;
            private String modifyTime;
            private String name;
            private String icon;
            private int sortNo;
            private int type;
            private int hasDel;
            private boolean isCheck;
            private boolean isConfirm;

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

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getSortNo() {
                return sortNo;
            }

            public void setSortNo(int sortNo) {
                this.sortNo = sortNo;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getHasDel() {
                return hasDel;
            }

            public void setHasDel(int hasDel) {
                this.hasDel = hasDel;
            }

            public boolean isCheck() {
                return isCheck;
            }

            public void setIsCheck(boolean isCheck) {
                this.isCheck = isCheck;
            }

            public boolean isConfirm() {
                return isConfirm;
            }

            public void setIsConfirm(boolean isConfirm) {
                this.isConfirm = isConfirm;
            }
        }
    }
}

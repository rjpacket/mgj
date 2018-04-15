package com.project.mgjandroid.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2016/3/10.
 */

public class MerchantPickGoods{
    private long merchantId;
    private List<PickGoods> pickGoods = new ArrayList<>();
    private int goodsCount;

    public long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }

    public List<PickGoods> getPickGoods() {
        return pickGoods;
    }

    public void setPickGoods(List<PickGoods> pickGoods) {
        this.pickGoods = pickGoods;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }
}



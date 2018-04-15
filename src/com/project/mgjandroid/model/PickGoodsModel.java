package com.project.mgjandroid.model;


import com.project.mgjandroid.bean.MerchantPickGoods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2016/3/10.
 */
public class PickGoodsModel {

    private static PickGoodsModel mInstance;

    private List<MerchantPickGoods> merchantPickGoodsList = new ArrayList<>();

    private boolean hasChange;
    private boolean isRemove;

    public PickGoodsModel(){

    }

    public List<MerchantPickGoods> getMerchantPickGoodsList() {
        return merchantPickGoodsList;
    }

    public void setMerchantPickGoodsList(List<MerchantPickGoods> merchantPickGoodsList) {
        this.merchantPickGoodsList = merchantPickGoodsList;
    }

    public boolean isHasChange() {
        return hasChange;
    }

    public void setHasChange(boolean hasChange) {
        this.hasChange = hasChange;
    }

    public static PickGoodsModel getInstance() {
        if (mInstance == null) {
            synchronized (PickGoodsModel.class) {
                if (mInstance == null) {
                    mInstance = new PickGoodsModel();
                }
            }
        }
        return mInstance;
    }

    public boolean isRemove() {
        return isRemove;
    }

    public void setIsRemove(boolean isRemove) {
        this.isRemove = isRemove;
    }
}

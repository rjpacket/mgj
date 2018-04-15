package com.project.mgjandroid.bean;

/**
 * Created by yuandi on 2016/3/10.
 */
public class PickGoods{
    private long menuId;
    private long goodsId;
    private long goodsSpecId;
    private Goods goods;
    private int pickCount;

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public long getGoodsSpecId() {
        return goodsSpecId;
    }

    public void setGoodsSpecId(long goodsSpecId) {
        this.goodsSpecId = goodsSpecId;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int getPickCount() {
        return pickCount;
    }

    public void setPickCount(int pickCount) {
        this.pickCount = pickCount;
    }


}

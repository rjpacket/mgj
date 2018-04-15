package com.project.mgjandroid.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuandi on 2016/3/9.
 */
public class MerchantTakeAwayMenu implements Serializable{

    private static final long serialVersionUID = 1L;

    private List<Menu> menu;
    private List<Order> orderList;

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}

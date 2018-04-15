package com.project.mgjandroid.ui.listener;

import com.project.mgjandroid.bean.Goods;

import java.util.List;

/**
 * 底部购物车监听
 * Created by rjp on 2016/3/9.
 */
public interface BottomCartListener {
    void productHasChange(Goods goods, long categoryId, long goodsId, long goodsSpecId, int pickCount, boolean isRemove, boolean isSetAnim);
}

package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.model.ConfirmOrderModel;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.StringUtils;

/**
 * Created by ning on 2016/3/14.
 */
public class ConfirmOrderListAdapter extends BaseListAdapter<ConfirmOrderModel.ValueEntity.OrderItemsEntity> {

    public ConfirmOrderListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, ConfirmOrderModel.ValueEntity.OrderItemsEntity bean, int position, View convertView, ViewGroup parent) {
        TextView tv_name = holder.getView(R.id.name);
        TextView tv_num = holder.getView(R.id.num);
        TextView tv_price = holder.getView(R.id.price);

        tv_num.setText("x" + bean.getQuantity());
        tv_price.setText("¥" + StringUtils.BigDecimal2Str(bean.getTotalPrice()));
        String spec = bean.getSpec();
        if(CheckUtils.isNoEmptyStr(spec)) {
            tv_name.setText(bean.getName() + " / " + spec);
        } else {
            tv_name.setText(bean.getName());
        }
    }
}

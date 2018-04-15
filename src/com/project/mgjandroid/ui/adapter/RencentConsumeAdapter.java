package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.model.AccountDetailsModel.ValueEntity;

import java.math.BigDecimal;

/**
 * Created by User_Cjh on 2016/3/24.
 */
public class RencentConsumeAdapter extends BaseListAdapter<ValueEntity>{

    public RencentConsumeAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, ValueEntity bean, int position, View convertView, ViewGroup parent) {
        TextView consumeMethod= holder.getView(R.id.consume_method);
        TextView consumeDate= holder.getView(R.id.consume_date);
        TextView consumeMoney= holder.getView(R.id.consume_money);
        TextView consumeMoneyDiff= holder.getView(R.id.consume_money_diff);
        TextView tvUnit = holder.getView(R.id.unit);

        BigDecimal balance =bean.getBalance();
        BigDecimal amt =bean.getAmt();
        BigDecimal signSymbol =new BigDecimal(bean.getSignSymbol());
        BigDecimal balanceDiff =amt.multiply(signSymbol);
        consumeMethod.setText(bean.getMemo());
        consumeDate.setText(bean.getCreateTime().split(" ")[0]);
        consumeMoney.setText(balance.add(balanceDiff)+"元");
        if(balanceDiff.doubleValue() > 0){
            consumeMoneyDiff.setText("+" + balanceDiff);
            consumeMoneyDiff.setTextColor(mActivity.getResources().getColor(R.color.consume_up));
            tvUnit.setTextColor(mActivity.getResources().getColor(R.color.consume_up));
        }else {
            consumeMoneyDiff.setText(balanceDiff + "");
            consumeMoneyDiff.setTextColor(mActivity.getResources().getColor(R.color.orange_f7));
            tvUnit.setTextColor(mActivity.getResources().getColor(R.color.orange_f7));
        }
    }
}

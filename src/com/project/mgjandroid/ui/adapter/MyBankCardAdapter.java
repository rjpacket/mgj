package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.BankCard;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;

/**
 * Created by yuandi on 2016/5/24.
 */
public class MyBankCardAdapter extends BaseListAdapter<BankCard> {

    public MyBankCardAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, BankCard bean, int position, View convertView, ViewGroup parent) {
        ImageView iv_icon = holder.getView(R.id.bank_icon);
        TextView tv_name = holder.getView(R.id.bank_name);
        TextView tv_cardNo = holder.getView(R.id.bank_card_no);

        if (CheckUtils.isNoEmptyStr(bean.getBankLogo())) {
            ImageUtils.loadBitmap(mActivity, bean.getBankLogo(), iv_icon, R.drawable.home_default , "");
        } else {
            iv_icon.setImageResource(R.drawable.home_default);
        }

        if (CheckUtils.isNoEmptyStr(bean.getBankName())) {
            tv_name.setText(bean.getBankName());
        } else {
            tv_name.setText("");
        }

        if (CheckUtils.isNoEmptyStr(bean.getBankCard())) {
            String no = bean.getBankCard().substring(bean.getBankCard().length()-5, bean.getBankCard().length()-1); //取最后四位
            tv_cardNo.setText(String.format(mActivity.getString(R.string.my_card_no), no));
        } else {
            tv_cardNo.setText("");
        }
    }
}

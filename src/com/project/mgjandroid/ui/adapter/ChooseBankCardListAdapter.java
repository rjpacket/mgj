package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Bank;
import com.project.mgjandroid.utils.ImageUtils;

/**
 * Created by ning on 2016/5/25.
 */
public class ChooseBankCardListAdapter extends BaseListAdapter<Bank> {
    public ChooseBankCardListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, Bank bean, int position, View convertView, ViewGroup parent) {
        holder.setText(R.id.bank_name,bean.getBankName());
        ImageView imgLogo = holder.getView(R.id.bank_logo);
        ImageUtils.loadBitmap(mActivity,bean.getBankLogo(),imgLogo,R.drawable.home_default,"");
        LinearLayout llLabel = holder.getView(R.id.bank_label);
        llLabel.setSelected(bean.isCheck());
    }
}

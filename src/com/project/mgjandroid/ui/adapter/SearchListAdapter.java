package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Merchant;

import java.text.DecimalFormat;

/**
 * Created by ning on 2016/4/29.
 */
public class SearchListAdapter extends BaseListAdapter<Merchant> {

    private DecimalFormat df = new DecimalFormat("0.0");

    public SearchListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, Merchant bean, int position, View convertView, ViewGroup parent) {
        holder.setText(R.id.search_name,bean.getName());
        holder.setText(R.id.search_time,bean.getAvgDeliveryTime() + "分钟送达");
        if(bean.getDistance()!=null){
            String distance = "";
            if (bean.getDistance() > 1000) {
                Double d = bean.getDistance() / 1000;
                distance = df.format(d) + "km";
            } else {
                distance = bean.getDistance().intValue() + "m";
            }
            holder.setText(R.id.search_distance, distance);
        }
    }
}

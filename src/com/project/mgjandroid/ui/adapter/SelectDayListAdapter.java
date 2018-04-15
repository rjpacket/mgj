package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.DayBean;
import com.project.mgjandroid.model.Entity;

/**
 * Created by ning on 2016/3/19.
 */
public class SelectDayListAdapter extends BaseListAdapter<DayBean> {
    public SelectDayListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, DayBean bean, int position, View convertView, ViewGroup parent) {
        LinearLayout background = holder.getView(R.id.day_list);
        TextView tv_name = holder.getView(R.id.day_list_text);
        tv_name.setText(bean.getDay());
        if(!bean.isChecked()){
            tv_name.setTextColor(mActivity.getResources().getColor(R.color.common_gray_text));
            background.setBackgroundColor(Color.parseColor("#ffefefef"));
        }else{
            tv_name.setTextColor(mActivity.getResources().getColor(R.color.common_black_text));
            background.setBackgroundColor(Color.parseColor("#ffffffff"));
        }
    }
}

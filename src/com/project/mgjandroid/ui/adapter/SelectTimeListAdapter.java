package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.DayBean;
import com.project.mgjandroid.bean.TimeBean;
import com.project.mgjandroid.model.Entity;

/**
 * Created by ning on 2016/3/19.
 */
public class SelectTimeListAdapter extends BaseListAdapter<TimeBean> {
    public SelectTimeListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, TimeBean bean, int position, View convertView, ViewGroup parent) {
        TextView tv_name = holder.getView(R.id.time_text);
        ImageView cb_check = holder.getView(R.id.time_check);
        tv_name.setText(bean.getDay());
        if(!bean.isChecked()){
            cb_check.setSelected(false);
        }else{
            cb_check.setSelected(true);
        }
    }
}

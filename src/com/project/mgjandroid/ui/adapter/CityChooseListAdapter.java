package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.CityBean;

/**
 * Created by rjp on 2016/6/23.
 * Email:rjpgoodjob@gmail.com
 */
public class CityChooseListAdapter extends BaseListAdapter<CityBean> {
    public CityChooseListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, CityBean bean, int position, View convertView, ViewGroup parent) {
        TextView tvName = holder.getView(R.id.area_name);
        tvName.setText(bean.getName());
    }
}

package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.HotCityBean;

/**
 * Created by rjp on 2016/6/24.
 * Email:rjpgoodjob@gmail.com
 */
public class HotCityGridAdapter extends BaseListAdapter<HotCityBean> {

    public HotCityGridAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, HotCityBean bean, int position, View convertView, ViewGroup parent) {
        TextView tvName = holder.getView(R.id.tag_name_position);
        tvName.setText(bean.getCityName());
    }
}

package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.HistoryEntity;

/**
 * Created by ning on 2016/5/5.
 */
public class HistoryListAdapter extends BaseListAdapter<HistoryEntity> {
    public HistoryListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, HistoryEntity bean, int position, View convertView, ViewGroup parent) {
        holder.setText(R.id.text_name,bean.getName());
    }
}

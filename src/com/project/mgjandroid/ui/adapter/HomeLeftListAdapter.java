package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.project.mgjandroid.model.Entity;

/**
 * Created by ning on 2016/3/24.
 */
public class HomeLeftListAdapter extends BaseListAdapter<Entity> {
    public HomeLeftListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, Entity bean, int position, View convertView, ViewGroup parent) {

    }
}

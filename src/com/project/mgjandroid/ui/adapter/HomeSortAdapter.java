package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.HomeBean;
import com.project.mgjandroid.model.Entity;

/**
 * Created by ning on 2016/3/30.
 */
public class HomeSortAdapter extends BaseListAdapter<HomeBean> {
    private View.OnClickListener listener;

    public HomeSortAdapter(int layoutId, Activity mActivity,View.OnClickListener listener) {
        super(layoutId, mActivity);
        this.listener = listener;
    }

    @Override
    protected void getRealView(ViewHolder holder, HomeBean bean, int position, View convertView, ViewGroup parent) {
        ImageView icon = holder.getView(R.id.head);
        TextView name = holder.getView(R.id.name);
        ImageView check = holder.getView(R.id.check);

        icon.setImageResource(bean.getIcon());
        name.setText(bean.getName());
        check.setSelected(bean.isCheck());
        LinearLayout view = holder.getView(R.id.mid_sort);
        view.setTag(position);
        view.setOnClickListener(listener);
    }
}

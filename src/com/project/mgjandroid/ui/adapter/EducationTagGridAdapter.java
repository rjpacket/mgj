package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.EducationCategory;

/**
 * Created by yuandi on 2016/7/22.
 *
 */
public class EducationTagGridAdapter extends BaseListAdapter<EducationCategory> {
    private View.OnClickListener mListener;
    private int parentPos;

    public EducationTagGridAdapter(int layoutId, Activity mActivity , View.OnClickListener listener , int parentPos) {
        super(layoutId, mActivity);
        mListener = listener;
        this.parentPos = parentPos;
    }

    @Override
    protected void getRealView(ViewHolder holder, EducationCategory bean, int position, View convertView, ViewGroup parent) {
        TextView tvName = holder.getView(R.id.tag_name_position);
        tvName.setText(bean.getName());

        final RelativeLayout rlLabel = holder.getView(R.id.item_grid_label);
        rlLabel.setTag(parentPos + ";" + position);
        rlLabel.setOnClickListener(mListener);

        rlLabel.setSelected(bean.isCheck());
    }
}
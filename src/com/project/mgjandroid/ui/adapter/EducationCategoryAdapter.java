package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.EducationServiceCategory;
import com.project.mgjandroid.ui.view.NoScrollGridView;

/**
 * Created by yuandi on 2016/7/22.
 *
 */
public class EducationCategoryAdapter extends BaseListAdapter<EducationServiceCategory> {
    private View.OnClickListener mListener;

    public EducationCategoryAdapter(int layoutId, Activity mActivity , View.OnClickListener listener) {
        super(layoutId, mActivity);
        mListener = listener;
    }

    @Override
    protected void getRealView(ViewHolder holder, EducationServiceCategory bean, int position, View convertView, ViewGroup parent) {
        TextView tvName = holder.getView(R.id.tag_name);
        tvName.setText(bean.getName());

        NoScrollGridView noScrollGridView = holder.getView(R.id.tag_grid_view);
        EducationTagGridAdapter tagGridAdapter = new EducationTagGridAdapter(R.layout.item_tag_grid_view, mActivity , mListener , position);
        tagGridAdapter.setData(bean.getEducationCategoryList());
        noScrollGridView.setAdapter(tagGridAdapter);
    }
}




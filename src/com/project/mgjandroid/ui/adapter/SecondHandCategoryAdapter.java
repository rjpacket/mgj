package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.SecondhandServiceCategory;
import com.project.mgjandroid.ui.view.NoScrollGridView;

/**
 * Created by thinkpad on 2016/7/9.
 */
public class SecondHandCategoryAdapter extends BaseListAdapter<SecondhandServiceCategory> {
    private View.OnClickListener mListener;

    public SecondHandCategoryAdapter(int layoutId, Activity mActivity , View.OnClickListener listener) {
        super(layoutId, mActivity);
        mListener = listener;
    }

    @Override
    protected void getRealView(ViewHolder holder, SecondhandServiceCategory bean, int position, View convertView, ViewGroup parent) {
        TextView tvName = holder.getView(R.id.tag_name);
        tvName.setText(bean.getName());

        NoScrollGridView noScrollGridView = holder.getView(R.id.tag_grid_view);
        SecondHandTagGridAdapter tagGridAdapter = new SecondHandTagGridAdapter(R.layout.item_tag_grid_view, mActivity , mListener , position);
        tagGridAdapter.setData(bean.getSecondhandCategoryList());
        noScrollGridView.setAdapter(tagGridAdapter);
    }
}


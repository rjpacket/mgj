package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.FulltimeCategoryBean;
import com.project.mgjandroid.ui.view.NoScrollGridView;

/**
 * Created by rjp on 2016/6/21.
 * Email:rjpgoodjob@gmail.com
 */
public class FulltimeJobListAdapter extends BaseListAdapter<FulltimeCategoryBean> {
    private View.OnClickListener mListener;

    public FulltimeJobListAdapter(int layoutId, Activity mActivity , View.OnClickListener listener) {
        super(layoutId, mActivity);
        mListener = listener;
    }

    @Override
    protected void getRealView(ViewHolder holder, FulltimeCategoryBean bean, int position, View convertView, ViewGroup parent) {
        TextView tvName = holder.getView(R.id.tag_name);
        tvName.setText(bean.getName());

        NoScrollGridView noScrollGridView = holder.getView(R.id.tag_grid_view);
        FulltimeTagGridAdapter tagGridAdapter = new FulltimeTagGridAdapter(R.layout.item_tag_grid_view, mActivity , mListener , position);
        tagGridAdapter.setData(bean.getPositionCategoryList());
        noScrollGridView.setAdapter(tagGridAdapter);
    }
}

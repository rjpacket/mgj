package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.PrimaryCategory;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.utils.ImageUtils;

/**
 * Created by yuandi on 2016/6/22.
 *
 */
public class PrimaryCategoryListAdapter extends BaseListAdapter<PrimaryCategory>{

    public PrimaryCategoryListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    public int getCount() {
        int yu = getDataCount() % 4;
        if(yu > 0){
            return getDataCount() - yu + 4;
        }
        return getDataCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(position, convertView, parent, layoutId);
        TextView tvName = holder.getView(R.id.tv_primary_category_name);
        ImageView ivIcon = holder.getView(R.id.iv_primary_category_icon);
        View topLight = holder.getView(R.id.top_line);
        View rightLight = holder.getView(R.id.right_line);
        if (position > 3) {
            topLight.setVisibility(View.VISIBLE);
        } else {
            topLight.setVisibility(View.GONE);
        }
        if ((position + 1) % 4 == 0) {
            rightLight.setVisibility(View.GONE);
        } else {
            rightLight.setVisibility(View.VISIBLE);
        }
        if(position < getDataCount()){
            tvName.setText(mDatas.get(position).getName());
            if (mDatas.get(position).getGraySwitch() == 0) {
                ImageUtils.loadBitmap(mActivity, mDatas.get(position).getPicUrl() + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER, ivIcon, R.drawable.category_default, "");
            } else {
                ImageUtils.loadBitmap(mActivity, mDatas.get(position).getGrayUrl() + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER, ivIcon, R.drawable.category_default, "");
            }
        }else{
            tvName.setText("");
            ivIcon.setImageDrawable(null);
        }
        return holder.getConvertView();
    }

    @Override
    protected void getRealView(ViewHolder holder, PrimaryCategory bean, int position, View convertView, ViewGroup parent) {

    }
}

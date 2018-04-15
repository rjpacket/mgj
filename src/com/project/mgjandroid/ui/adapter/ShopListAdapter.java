package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.MerchantPromotion;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.utils.ImageUtils;

/**
 * Created by ning on 2016/3/17.
 */
public class ShopListAdapter extends BaseListAdapter<MerchantPromotion> {
    public ShopListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, MerchantPromotion bean, int position, View convertView, ViewGroup parent) {
        ImageView imageView = holder.getView(R.id.image);
        TextView textView = holder.getView(R.id.name);

        textView.setText(bean.getName());
        ImageUtils.loadBitmap(mActivity , bean.getIcon(),imageView,R.drawable.pic_jixiang , Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
    }
}

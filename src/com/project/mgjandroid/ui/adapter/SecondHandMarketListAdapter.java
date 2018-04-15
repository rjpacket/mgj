package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.SecondhandInformation;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.ImageUtils;

import java.text.SimpleDateFormat;

/**
 * Created by rjp on 2016/7/7.
 * Email:rjpgoodjob@gmail.com
 */
public class SecondHandMarketListAdapter extends BaseListAdapter<SecondhandInformation> {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public SecondHandMarketListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, SecondhandInformation bean, int position, View convertView, ViewGroup parent) {
        String imgs = bean.getImgs();
        CornerImageView cornerImageView = holder.getView(R.id.second_market_image_view);
        if(TextUtils.isEmpty(imgs)){
            cornerImageView.setVisibility(View.GONE);
        }else{
            ImageUtils.loadBitmap(mActivity,bean.getImgs().split(";")[0],cornerImageView,R.drawable.home_default, Constants.getEndThumbnail(86,66));
        }

        TextView tvName = holder.getView(R.id.second_market_name);
        tvName.setText(bean.getTitle());

        TextView tvTag = holder.getView(R.id.second_market_tag);
        tvTag.setText(bean.getSecondhandCategoryName());

        TextView tvPrice = holder.getView(R.id.second_hand_price);
        String showAmt = bean.getShowAmt();
        tvPrice.setText(showAmt);

        TextView tvTime = holder.getView(R.id.second_hand_time);
        tvTime.setText("发布时间：" + format.format(bean.getCreateTime()));

        TextView tvStatus = holder.getView(R.id.second_hand_status);
        if(bean.getIsTop() == 0){
            tvStatus.setVisibility(View.GONE);
        }else if(bean.getIsTop() == 1){
            tvStatus.setVisibility(View.VISIBLE);
        }
    }
}

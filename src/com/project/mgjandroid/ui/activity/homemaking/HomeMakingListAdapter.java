package com.project.mgjandroid.ui.activity.homemaking;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.HomemakingInformation;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.adapter.BaseListAdapter;
import com.project.mgjandroid.ui.adapter.ViewHolder;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.text.SimpleDateFormat;

/**
 * Created by rjp on 2016/7/25.
 * Email:rjpgoodjob@gmail.com
 */
public class HomeMakingListAdapter extends BaseListAdapter<HomemakingInformation> {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public HomeMakingListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, HomemakingInformation bean, int position, View convertView, ViewGroup parent) {
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
        tvTag.setText(bean.getHomemakingCategoryName());

        RatingBar rbEvaluate = holder.getView(R.id.education_rating_bar);
        rbEvaluate.setRating(bean.getScore().floatValue());
        TextView tvScore = holder.getView(R.id.education_score);
        if(bean.getScore().floatValue() == 0){
            tvScore.setTextColor(Color.parseColor("#999999"));
            tvScore.setText("暂无评分");
        }else{
            tvScore.setTextColor(Color.parseColor("#ff6634"));
            tvScore.setText(bean.getScore().floatValue() + "分");
        }

        TextView tvStatus = holder.getView(R.id.second_hand_status);
//        if(bean.getIsTop() == 0){
            tvStatus.setVisibility(View.GONE);
//        }else if(bean.getIsTop() == 1){
//            tvStatus.setVisibility(View.VISIBLE);
//        }

        TextView tvType = holder.getView(R.id.education_address);
        int type = bean.getType();
        if(type == 1){
            tvType.setText("个人");
        }else if(type == 2){
            tvType.setText("商家");
        }
    }
}

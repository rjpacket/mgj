package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.GoodsEvaluateModel;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;

/**
 * Created by ning on 2016/3/11.
 *
 */
public class GoodsDetailListAdapter extends BaseListAdapter<GoodsEvaluateModel.ValueEntity> {

    public GoodsDetailListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, GoodsEvaluateModel.ValueEntity bean, int position, View convertView, ViewGroup parent) {
        RatingBar ratingBar=holder.getView(R.id.goods_item_rat_score);
        TextView evaluateTime=holder.getView(R.id.goods_item_evaluate_time);
        TextView evaluateContent =holder.getView(R.id.goods_item_evaluate_content);
        RoundImageView evaluateAvatar = holder.getView(R.id.goods_item_evaluate_avatar);
        TextView evaluateUsername = holder.getView(R.id.goods_item_evaluate_username);
        TextView tvReply = holder.getView(R.id.tv_merchant_reply);

        ratingBar.setRating(bean.getGoodsScore().floatValue());
        evaluateTime.setText(bean.getCreateTime().split(" ")[0]);
        if(TextUtils.isEmpty(bean.getGoodsScoreComments())) {
            evaluateContent.setText("该用户没有做具体的评价哦！");
            evaluateContent.setTextColor(mActivity.getResources().getColor(R.color.gray_4));
        }else {
            evaluateContent.setText(bean.getGoodsScoreComments());
            evaluateContent.setTextColor(mActivity.getResources().getColor(R.color.gray_1));
        }
        String name = bean.getAppUser().getName();
        if(name == null){
            name = bean.getAppUser().getMobile();
        }
        if(name.length() <= 1){
            name = name.substring(0,1) + "***";
        }else if(name.length() > 1){
            name = name.substring(0,1) + "***" + name.substring(name.length() - 1,name.length());
        }
        evaluateUsername.setText(name);
        String headerImg=bean.getAppUser().getHeaderImg();
        if(!TextUtils.isEmpty(headerImg)){
            ImageUtils.loadBitmap(mActivity , headerImg+ Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_EVALUATE, evaluateAvatar, R.drawable.comment_defaut_head , "");
        }
        if (CheckUtils.isNoEmptyStr(bean.getReplyContent())) {
            tvReply.setVisibility(View.VISIBLE);
            tvReply.setText("商家回复：" + bean.getReplyContent());
        } else {
            tvReply.setVisibility(View.GONE);
        }
    }
}

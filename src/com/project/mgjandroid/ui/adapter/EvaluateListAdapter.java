package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.model.Entity;
import com.project.mgjandroid.model.OrderFragmentModel;
import com.project.mgjandroid.model.OrderFragmentModel.ValueEntity.*;
import com.project.mgjandroid.ui.view.RatingBarView;
import com.project.mgjandroid.utils.CommonUtils;

import java.util.List;

/**
 * Created by ning on 2016/3/22.
 */
public class EvaluateListAdapter extends BaseListAdapter<OrderItemsEntity> {
    private RatingBarView.OnRatingListener onRatingListener;
    private View.OnFocusChangeListener onFocusChangeListener;
    public EvaluateListAdapter(int layoutId, Activity mActivity,RatingBarView.OnRatingListener onRatingListener,View.OnFocusChangeListener onFocusChangeListener) {
        super(layoutId, mActivity);
        this.onRatingListener = onRatingListener;
        this.onFocusChangeListener = onFocusChangeListener;
    }

    @Override
    protected void getRealView(ViewHolder holder,final OrderItemsEntity bean, int position, View convertView, ViewGroup parent) {
        RatingBarView rbRating = holder.getView(R.id.driver_rat_score);
        TextView goodsName = holder.getView(R.id.list_item_name);
        final EditText etEvaluate = holder.getView(R.id.evaluate_edit_text);
        etEvaluate.setTag(position);
        goodsName.setText(bean.getName());
        rbRating.setRating(bean.getRating());
        rbRating.setBindObject(position);
        int rating = bean.getRating();
        if(rating>3){
            rbRating.setStarFillDrawable(mActivity.getResources().getDrawable(R.drawable.evaluate_goods_weel));
            etEvaluate.setHint("说说哪里满意，帮大家选择");
        }else if(rating>1){
            rbRating.setStarFillDrawable(mActivity.getResources().getDrawable(R.drawable.evaluate_goods_normal));
            etEvaluate.setHint("说说哪里不满意，帮商家改进");
        }else{
            rbRating.setStarFillDrawable(mActivity.getResources().getDrawable(R.drawable.evaluate_goods_bad));
            etEvaluate.setHint("说说哪里不满意，帮商家改进");
        }
        etEvaluate.setText(bean.getContent());
        rbRating.setStar(rating);
        if(bean.isShow()){
//            rbRating.setFocusable(false);
            etEvaluate.setVisibility(View.VISIBLE);
            etEvaluate.requestFocus();
            etEvaluate.setSelection(etEvaluate.length());
            CommonUtils.showKeyBoard(etEvaluate);
        }else {
            etEvaluate.setVisibility(View.GONE);
        }

        /*rbRating.setOnRatingBarChangeListener(new RatingBarView.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                etEvaluate.setVisibility(View.VISIBLE);
                etEvaluate.requestFocus();
                etEvaluate.setSelection(etEvaluate.length());
                CommonUtils.showKeyBoard(etEvaluate);
                bean.setRating(rbRating.getRating());
            }
        });*/
        etEvaluate.setOnFocusChangeListener(onFocusChangeListener);
        rbRating.setOnRatingListener(onRatingListener);
    }
}

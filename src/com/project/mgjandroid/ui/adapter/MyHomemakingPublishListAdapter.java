package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.HomemakingInformation;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.ImageUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/**
 * Created by rjp on 2016/7/14.
 * Email:rjpgoodjob@gmail.com
 */
public class MyHomemakingPublishListAdapter extends BaseListAdapter<HomemakingInformation> implements View.OnClickListener {

    private PopupWindow mPopupWindow;
    private int mPos;
    private View.OnClickListener mListener;

    public MyHomemakingPublishListAdapter(int layoutId, Activity mActivity, View.OnClickListener listener) {
        super(layoutId, mActivity);
        mListener = listener;
    }

    @Override
    protected void getRealView(ViewHolder holder, HomemakingInformation bean, int position, View convertView, ViewGroup parent) {
        String imgs = bean.getImgs();
        CornerImageView cornerImageView = holder.getView(R.id.education_image_view);
        if(TextUtils.isEmpty(imgs)){
            cornerImageView.setVisibility(View.GONE);
        }else{
            cornerImageView.setVisibility(View.VISIBLE);
            ImageUtils.loadBitmap(mActivity,bean.getImgs().split(";")[0],cornerImageView,R.drawable.home_default, Constants.getEndThumbnail(86,66));
        }

        TextView tvName = holder.getView(R.id.education_name);
        tvName.setText(bean.getTitle());

        RatingBar ratingBar = holder.getView(R.id.education_score);
        ratingBar.setRating(bean.getScore().floatValue());
        BigDecimal score = bean.getScore();
        if(score.compareTo(BigDecimal.ZERO) == 0){
            holder.setText(R.id.education_score_price, "暂无评分");
        }else {
            holder.setText(R.id.education_score_price, bean.getScore().floatValue() + "分");
        }

        TextView tvTime = holder.getView(R.id.education_time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        tvTime.setText("发布时间：" + format.format(bean.getCreateTime()));

        TextView tvStatus = holder.getView(R.id.education_status);
        if(bean.getIsTop() == 0){
            tvStatus.setVisibility(View.GONE);
        }else if(bean.getIsTop() == 1){
            tvStatus.setVisibility(View.GONE);
        }else{
            tvStatus.setVisibility(View.GONE);
        }
        ImageView imgMore = holder.getView(R.id.education_tag);
        imgMore.setTag(position);

        RelativeLayout rlCover = holder.getView(R.id.item_cover);
        TextView tvTip = holder.getView(R.id.item_state);
        TextView tvTipDelete = holder.getView(R.id.item_state_delete);
        int status = bean.getStatus();
        if(status == 0){
            rlCover.setVisibility(View.VISIBLE);
            tvTip.setText("提交审核中");
            tvTipDelete.setVisibility(View.GONE);
        }else if(status == 1){
            rlCover.setVisibility(View.GONE);
        }else if(status == 2){
            rlCover.setVisibility(View.VISIBLE);
            tvTip.setText("涉及非法字符，审核失败");
            tvTipDelete.setVisibility(View.VISIBLE);
        }
        imgMore.setOnClickListener(this);
        tvTipDelete.setTag(position);
        tvTipDelete.setOnClickListener(mListener);
        rlCover.setTag(position);
        rlCover.setOnClickListener(mListener);

    }

    private void initPopup() {
        View view = mInflater.inflate(R.layout.popup_score_setting,null);
        TextView tvRefresh = (TextView) view.findViewById(R.id.tv_refresh);
        TextView tvSticky = (TextView) view.findViewById(R.id.tv_sticky);
        TextView tvDelete = (TextView) view.findViewById(R.id.tv_delete);
        tvRefresh.setTag(mPos);
        tvSticky.setTag(mPos);
        tvDelete.setTag(mPos);
        tvRefresh.setOnClickListener(mListener);
        tvSticky.setOnClickListener(mListener);
        tvDelete.setOnClickListener(mListener);
        mPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopupWindow.setBackgroundDrawable(cd);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setClippingEnabled(false);
        mPopupWindow.setFocusable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.education_tag:
                mPos = (int) v.getTag();
                initPopup();
                if(mPos != 0 && mPos == getData().size() - 1){
                    mPopupWindow.showAsDropDown(v, -(int) mActivity.getResources().getDimension(R.dimen.x100), -(int) mActivity.getResources().getDimension(R.dimen.x126));
                }else {
                    mPopupWindow.showAsDropDown(v, -(int) mActivity.getResources().getDimension(R.dimen.x100), -(int) mActivity.getResources().getDimension(R.dimen.x20));
                }
                break;
        }
    }

    public void hidePopup() {
        if(mPopupWindow != null && mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
    }
}

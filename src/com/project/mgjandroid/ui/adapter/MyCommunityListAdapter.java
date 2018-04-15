package com.project.mgjandroid.ui.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.CommunityBean;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;

/**
 * Created by rjp on 2016/6/12.
 * Email:rjpgoodjob@gmail.com
 */
public class MyCommunityListAdapter extends BaseListAdapter<CommunityBean> {
    private View.OnClickListener mListener;
    private boolean isFromMy;

    public MyCommunityListAdapter(int layoutId, Activity mActivity,View.OnClickListener listener , boolean isFromMy) {
        super(layoutId, mActivity);
        mListener = listener;
        this.isFromMy = isFromMy;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void getRealView(ViewHolder holder, CommunityBean bean, int position, View convertView, ViewGroup parent) {
        ImageView imgHead = holder.getView(R.id.com_head_view);
        ImageView imgSex = holder.getView(R.id.com_sex);
        TextView tvUserName = holder.getView(R.id.com_user);
        TextView tvTime = holder.getView(R.id.com_time);

        ImageView imgContentIcon = holder.getView(R.id.com_content_icon);
        TextView tvContent = holder.getView(R.id.com_content);
        TextView tvContentAll = holder.getView(R.id.com_content_all);
        imgContentIcon.setTag(position);
        imgContentIcon.setOnClickListener(mListener);

        if(bean.isOpen()){
            tvContent.setMaxLines(Integer.MAX_VALUE);
            tvContent.setMinLines(0);
            imgContentIcon.setImageResource(R.drawable.content_close);
        }else{
            tvContent.setMaxLines(2);
            imgContentIcon.setImageResource(R.drawable.content_open);
        }

        ImageUtils.loadBitmap(mActivity, bean.getHeadImg(), imgHead, R.drawable.head_default, "");
        if(bean.getSex() == 1){
            imgSex.setImageResource(R.drawable.male);
        }else {
            imgSex.setImageResource(R.drawable.female);
        }
        tvUserName.setText(bean.getAuthor());
        String createTime = bean.getCreateTime();
        tvTime.setText(createTime.split(" ")[0]);

        ImageView imgMore = holder.getView(R.id.com_more);
        imgMore.setOnClickListener(mListener);
        TextView tvReport = holder.getView(R.id.com_report);

        if(isFromMy){
            tvReport.setVisibility(View.GONE);
        }else{
            imgMore.setVisibility(View.GONE);
        }

        LinearLayout llTagContainer = holder.getView(R.id.com_tag_container);
        llTagContainer.removeAllViews();
        TextView tag = new TextView(mActivity);
        tag.setTextColor(0xffF85188);
        tag.setPadding(DipToPx.dip2px(mActivity,9),DipToPx.dip2px(mActivity,6),DipToPx.dip2px(mActivity,9),DipToPx.dip2px(mActivity,6));
        tag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        tag.setBackgroundResource(R.drawable.community_red_shape);
        tag.setText(bean.getCategoryName());
        llTagContainer.addView(tag);

        LinearLayout llZanLabel = holder.getView(R.id.com_zan_label);
        final ImageView imgZan = holder.getView(R.id.com_zan);
        llZanLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.com_button_scale_alpha);
                imgZan.startAnimation(animation);
                notifyDataSetChanged();
            }
        });

        LinearLayout llImageContainer = holder.getView(R.id.com_image_container);
        llImageContainer.removeAllViews();
        String images = bean.getImages();
        if(images != null){
            String[] split = images.split(";");
            for (int i = 0; i < split.length; i++) {
                ImageView imageView = new ImageView(mActivity);
                ImageUtils.loadBitmap(mActivity,split[i],imageView,R.drawable.home_default, Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
                int imageAll = CommonUtils.getScreenWidth(mActivity.getWindowManager()) - DipToPx.dip2px(mActivity,38);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(imageAll / 4,imageAll / 4);
                layoutParams.setMargins(DipToPx.dip2px(mActivity,1),0,DipToPx.dip2px(mActivity,1),0);
                imageView.setLayoutParams(layoutParams);
                llImageContainer.addView(imageView);
            }
        }
    }
}

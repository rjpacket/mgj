package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.activity.CommercialActivity;
import com.project.mgjandroid.ui.activity.ConfirmOrderActivity;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yuandi on 2016/5/27.
 */
public class RedBagAdapter extends BaseListAdapter<RedBag> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    private boolean isFromOrderPre;
    private boolean canUse;

    public RedBagAdapter(int layoutId, Activity mActivity, boolean isFromOrderPre, boolean canUse) {
        super(layoutId, mActivity);
        this.isFromOrderPre = isFromOrderPre;
        this.canUse = canUse;
    }

    @Override
    protected void getRealView(ViewHolder holder, final RedBag bean, int position, View convertView, ViewGroup parent) {
        LinearLayout rootView = holder.getView(R.id.item_content_view);
        CornerImageView iv_merchant_icon = holder.getView(R.id.merchant_icon);
        TextView tv_merchant_name = holder.getView(R.id.merchant_name);
        TextView tv_effect_period = holder.getView(R.id.effect_period);
        TextView tv_discount_amt = holder.getView(R.id.discount_amt);
        TextView tv_restrict = holder.getView(R.id.tv_red_bag_restrict);
        RelativeLayout layoutToUseRedBag = holder.getView(R.id.to_use_red_bag);

        if (!TextUtils.isEmpty(bean.getMerchantName())) {
            tv_merchant_name.setText(bean.getMerchantName());
        } else {
            tv_merchant_name.setText("");
        }
        if (CheckUtils.isNoEmptyStr(bean.getMerchantLogo())) {
            ImageUtils.loadBitmap(mActivity, bean.getMerchantLogo(), iv_merchant_icon, R.drawable.home_default , Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
        } else {
            iv_merchant_icon.setImageResource(R.drawable.home_default);
        }

        if (bean.getExpirationTime() != null) {
            tv_effect_period.setText(sdf.format(bean.getCreateTime()) + "-" + sdf.format(new Date(bean.getExpirationTime())));
        } else {
            tv_effect_period.setText("");
        }

        if (bean.getAmt() != null) {
            tv_discount_amt.setText(StringUtils.BigDecimal2Str(bean.getAmt()));
        } else {
            tv_discount_amt.setText("0");
        }

        if (canUse) {
            layoutToUseRedBag.setBackgroundResource(R.drawable.use_now);
            tv_restrict.setTextColor(mActivity.getResources().getColor(R.color.title_bar_bg));
        } else if (bean.getStatus() == 1) {
            layoutToUseRedBag.setBackgroundResource(R.drawable.has_used);
            tv_restrict.setTextColor(mActivity.getResources().getColor(R.color.title_bar_bg));
        } else {
            layoutToUseRedBag.setBackgroundResource(R.drawable.lose_efficacy);
            tv_restrict.setTextColor(mActivity.getResources().getColor(R.color.color_9));
        }

        if (bean.getRestrictAmt() != null && bean.getRestrictAmt().compareTo(BigDecimal.ZERO) > 0) {
            tv_restrict.setText("满" + StringUtils.BigDecimal2Str(bean.getRestrictAmt()) + "元可用");
        } else {
            tv_restrict.setText("下单即可使用");
        }

        if (canUse && !isFromOrderPre) {
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, CommercialActivity.class);
                    intent.putExtra(CommercialActivity.MERCHANT_ID, bean.getMerchantId().intValue());
                    mActivity.startActivity(intent);
                }
            });
        }else if (isFromOrderPre && canUse) {
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("red_bag", bean);
                    mActivity.setResult(ConfirmOrderActivity.RESPONSE_CHOOSE_RED_BAG, intent);
                    mActivity.finish();
                }
            });
        }
    }
}

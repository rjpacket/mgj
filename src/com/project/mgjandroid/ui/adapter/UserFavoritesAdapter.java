package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.bean.MerchantPromotion;
import com.project.mgjandroid.bean.UserFavorites;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.activity.CommercialActivity;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UserFavoritesAdapter extends BaseAdapter {
    private ArrayList<UserFavorites> list;
    private Context context;
    private CustomDialog dialog, mDialog;
    private LongClickListener clickListener;
    private LayoutInflater mInflater;
    private DecimalFormat df = new DecimalFormat("0.0");

    public UserFavoritesAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = new ArrayList<>();
    }

    public ArrayList<UserFavorites> getList() {
        return list;
    }

    public void setList(ArrayList<UserFavorites> list) {
        if(list == null){
            list = new ArrayList<>();
        }
        this.list = list;
        notifyDataSetChanged();
    }

    public void setClickListener(LongClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.commercial_list_item, null);
            holder.promotionLine = convertView.findViewById(R.id.promotion_line);
            holder.img = (ImageView) convertView.findViewById(R.id.restaurant_list_item_img);
            holder.imgStatus = (ImageView) convertView.findViewById(R.id.restaurant_list_item_img_status);
            holder.tvPickGoodsCount = (TextView) convertView.findViewById(R.id.pick_goods_count);
            holder.tvName = (TextView) convertView.findViewById(R.id.restaurant_list_item_tv_name);
            holder.tvSendPrice = (TextView) convertView.findViewById(R.id.restaurant_list_item_tv_sendprice);
            holder.scoreBar = (RatingBar) convertView.findViewById(R.id.restaurant_list_item_rat_score);
            holder.tvScore = (TextView) convertView.findViewById(R.id.restaurant_list_item_tv_score);
            holder.tvInSales = (TextView) convertView.findViewById(R.id.restaurant_list_item_tv_insales);
            holder.tvShippingFee = (TextView) convertView.findViewById(R.id.restaurant_list_item_tv_shipping_fee);
            holder.tvMianShippingFee = (TextView) convertView.findViewById(R.id.restaurant_list_item_tv_mian_shipping_fee);
            holder.tvTips = (TextView) convertView.findViewById(R.id.restaurant_list_item_tv_tips);
            holder.layoutImgs = (LinearLayout) convertView.findViewById(R.id.restaurant_list_item_layout_img);
            holder.layoutActive = (LinearLayout) convertView.findViewById(R.id.restaurant_list_item_layout_active);
            holder.layoutActiveHide = (LinearLayout) convertView.findViewById(R.id.restaurant_list_item_layout_active_hide);
            holder.tvActiveCount = (TextView) convertView.findViewById(R.id.restaurant_list_item_tv_active_count);
            holder.imgActive = (ImageView) convertView.findViewById(R.id.restaurant_list_item_iv_active);
            holder.tvTimeDistance = (TextView) convertView.findViewById(R.id.restaurant_list_item_tv_time_distance);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (CheckUtils.isNoEmptyList(list) && list.size() > position) {
            showItem(convertView, list.get(position).getMerchant(), holder, position, list.get(position).getHasDel());
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView img, imgActive,imgStatus;
        TextView tvName, tvSendPrice, tvScore, tvInSales, tvShippingFee, tvMianShippingFee, tvTips, tvActiveCount, tvTimeDistance, tvPickGoodsCount;
        RatingBar scoreBar;
        LinearLayout layoutImgs, layoutActive, layoutActiveHide;
        View promotionLine;
    }

    private void showItem(View view, final Merchant merchant, final ViewHolder holder, final int position, final int hasDel) {
        if (merchant != null) {
            if(merchant.getPickGoodsCount()>0){
                holder.tvPickGoodsCount.setVisibility(View.VISIBLE);
                holder.tvPickGoodsCount.setText(merchant.getPickGoodsCount()+"");
            }else{
                holder.tvPickGoodsCount.setVisibility(View.INVISIBLE);
            }
            holder.img.setImageResource(R.drawable.home_default);
            if(merchant.getStatus()==0){
                holder.imgStatus.setVisibility(View.VISIBLE);
            }else{
                holder.imgStatus.setVisibility(View.GONE);
            }
            if(!TextUtils.isEmpty(merchant.getLogo()))
                ImageUtils.loadBitmap(context , merchant.getLogo(), holder.img, R.drawable.home_default , Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
            if(!TextUtils.isEmpty(merchant.getName()))
                holder.tvName.setText(merchant.getName());
            if(merchant.getMinPrice()!=null)
                holder.tvSendPrice.setText(StringUtils.BigDecimal2Str(merchant.getMinPrice()) + "");
            else{
                holder.tvSendPrice.setText("0");
            }
            holder.tvInSales.setText("月售" + merchant.getMonthSaled() + "单");
            if(merchant.getAverageScore()!=null){
                holder.scoreBar.setRating(merchant.getAverageScore().floatValue());
                holder.tvScore.setText(CommonUtils.BigDecimal2Str(merchant.getAverageScore()));
            }else{
                holder.scoreBar.setRating(0);
                holder.tvScore.setText("");
            }
            if (merchant.getShipFee().compareTo(BigDecimal.ZERO) == 0) {
                holder.tvShippingFee.setVisibility(View.GONE);
                holder.tvMianShippingFee.setVisibility(View.VISIBLE);
            } else {
                holder.tvShippingFee.setText("¥" + StringUtils.BigDecimal2Str(merchant.getShipFee()));
                holder.tvShippingFee.setVisibility(View.VISIBLE);
                holder.tvMianShippingFee.setVisibility(View.GONE);
            }
//			if (CheckUtils.isNoEmptyStr(merchant.getBroadcast())) {
//				holder.tvTips.setVisibility(View.VISIBLE);
//				holder.tvTips.setText(merchant.getBroadcast());
//				holder.tvTimeDistance.setVisibility(View.GONE);
//			} else {
            holder.tvTips.setVisibility(View.GONE);
            holder.tvTimeDistance.setVisibility(View.VISIBLE);
            StringBuffer buffer = new StringBuffer("");
            if(merchant.getAvgDeliveryTime()!=null&&merchant.getAvgDeliveryTime()!=0){
                buffer.append(merchant.getAvgDeliveryTime() + "分钟 / ");
            }
            if(merchant.getDistance()!=null){
                if (merchant.getDistance() > 1000) {
                    Double d = merchant.getDistance() / 1000;
                    buffer.append(df.format(d) + "千米");
                } else {
                    buffer.append(merchant.getDistance().intValue() + "米");
                }
            }
            holder.tvTimeDistance.setText(buffer.toString());
//			}
            if (CheckUtils.isNoEmptyStr(merchant.getPayments())) {
                String[] payments = merchant.getPayments().split(",");
                holder.layoutImgs.removeAllViews();
                holder.layoutImgs.setVisibility(View.VISIBLE);
                for (int i=0; i<payments.length; i++) {
                    if(Integer.parseInt(payments[i]) == 1) {
                        ImageView icon = new ImageView(context);
                        int resId = chooseIcon(3);
                        icon.setBackgroundResource(resId);
                        LayoutParams params = new LayoutParams(DipToPx.dip2px(context, 12f), DipToPx.dip2px(context, 12f));
                        params.leftMargin = DipToPx.dip2px(context, 2);
                        holder.layoutImgs.addView(icon, params);
                    }
                }
            } else {
                holder.layoutImgs.setVisibility(View.GONE);
            }
            holder.tvActiveCount.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (holder.layoutActiveHide.getVisibility() == View.VISIBLE) {
                        holder.layoutActiveHide.setVisibility(View.GONE);
                        AnimatorUtils.rotating(holder.imgActive, 180, 360);
                    } else {
                        holder.layoutActiveHide.setVisibility(View.VISIBLE);
                        AnimatorUtils.rotating(holder.imgActive, 0, 180);
                    }
                }
            });
            holder.imgActive.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (holder.layoutActiveHide.getVisibility() == View.VISIBLE) {
                        holder.layoutActiveHide.setVisibility(View.GONE);
                        AnimatorUtils.rotating(holder.imgActive, 180, 360);
                    } else {
                        holder.layoutActiveHide.setVisibility(View.VISIBLE);
                        AnimatorUtils.rotating(holder.imgActive, 0, 180);
                    }
                }
            });
//			holder.layoutActive.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					if(holder.tvActiveCount.getVisibility() == View.VISIBLE){
//						if (holder.layoutActiveHide.getVisibility() == View.VISIBLE) {
//							holder.layoutActiveHide.setVisibility(View.GONE);
//							AnimatorUtils.rotating(holder.imgActive, 180, 360);
//						} else {
//							holder.layoutActiveHide.setVisibility(View.VISIBLE);
//							AnimatorUtils.rotating(holder.imgActive, 0, 180);
//						}
//					}
//				}
//			});
//			holder.layoutActiveHide.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					holder.layoutActiveHide.setVisibility(View.GONE);
//					AnimatorUtils.rotating(holder.imgActive, 180, 360);
//				}
//			});
//            if (CheckUtils.isNoEmptyList(merchant.getPromotions())) {
//                holder.promotionLine.setVisibility(View.VISIBLE);
//                if (merchant.getPromotions().size() > 2) {
//                    holder.tvActiveCount.setVisibility(View.VISIBLE);
//                    holder.tvActiveCount.setText(merchant.getPromotions().size() + "个活动");
//                    holder.imgActive.setVisibility(View.VISIBLE);
//                } else {
//                    holder.tvActiveCount.setVisibility(View.GONE);
//                    holder.imgActive.setVisibility(View.GONE);
//                }
//                holder.layoutActive.setVisibility(View.VISIBLE);
//                holder.layoutActive.removeAllViews();
//                holder.layoutActiveHide.removeAllViews();
//                for (int i = 0; i < merchant.getPromotions().size(); i++) {
//                    MerchantPromotion promotion = merchant.getPromotions().get(i);
//                    if (i > 1) {
//                        addPromotion(holder.layoutActiveHide, promotion);
//                    } else {
//                        addPromotion(holder.layoutActive, promotion);
//                    }
//                }
//            } else {
                holder.promotionLine.setVisibility(View.GONE);
                holder.tvActiveCount.setVisibility(View.GONE);
                holder.imgActive.setVisibility(View.GONE);
                holder.layoutActive.setVisibility(View.GONE);
//            }

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (hasDel == 1) {
                        showMyDialog();
                        return;
                    }
                    Intent intent = new Intent(context, CommercialActivity.class);
                    intent.putExtra(CommercialActivity.MERCHANT_ID, merchant.getId().intValue());
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
                }
            });


            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showMyDialog(position);
                    return true;
                }
            });

        }
    }

    private void showMyDialog() {
        if (mDialog == null){
            mDialog = new CustomDialog(context, new CustomDialog.onBtnClickListener() {
                @Override
                public void onSure() {
                    mDialog.dismiss();
                }

                @Override
                public void onExit() {
                    mDialog.dismiss();
                }

            }, "我知道了", "", "", "该商家已下架");
            mDialog.show();
        }else {
            mDialog.show();
        }
    }

    private void showMyDialog(final int position) {
        if (dialog == null){
            dialog = new CustomDialog(context, new CustomDialog.onBtnClickListener() {
                @Override
                public void onSure() {
                    if(clickListener != null) clickListener.doClick(position);
                    dialog.dismiss();
                }

                @Override
                public void onExit() {
                    dialog.dismiss();
                }

            }, "确定", "取消", "", "您确定要取消对此商家的收藏吗");
            dialog.show();
        }else {
            dialog.show();
        }
    }

    private int chooseIcon(int id) {
        switch (id) {
            case 1:
                return R.drawable.piao;
            case 2:
                return R.drawable.bao;
            case 3:
                return R.drawable.fu;
            default:
                return R.drawable.pei;
        }
    }

    private void addPromotion(LinearLayout layout, MerchantPromotion promotion) {
        LinearLayout childLayout = new LinearLayout(context);
        childLayout.setOrientation(LinearLayout.HORIZONTAL);
        childLayout.setGravity(Gravity.CENTER_VERTICAL);
        if (CheckUtils.isNoEmptyStr(promotion.getIcon())) {
            ImageView image = new ImageView(context);
            ImageUtils.loadBitmap(context , promotion.getIcon(), image, R.drawable.jian , "");
            LayoutParams params = new LayoutParams(DipToPx.dip2px(context,12), DipToPx.dip2px(context,12));
            childLayout.addView(image, params);
        }
        if (CheckUtils.isNoEmptyStr(promotion.getName())) {
            TextView tv = new TextView(context);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = DipToPx.dip2px(context, 4);
            tv.setText(promotion.getName());
            tv.setSingleLine();
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setTextColor(context.getResources().getColor(R.color.gray_2));
            tv.setTextSize(10);
            childLayout.addView(tv, params);
        }
        LayoutParams paramsChild = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        paramsChild.topMargin = DipToPx.dip2px(context, 4);
        layout.addView(childLayout, paramsChild);
    }

    public interface LongClickListener{
        void doClick(int position);
    }
}


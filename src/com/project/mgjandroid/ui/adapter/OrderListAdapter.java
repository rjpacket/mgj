package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.constants.OrderFlowStatus;
import com.project.mgjandroid.model.OrderFragmentModel;
import com.project.mgjandroid.model.OrderFragmentModel.ValueEntity;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.TimeTextView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderListAdapter extends BaseAdapter {
    private ArrayList<OrderFragmentModel.ValueEntity> list;
    private Context context;
    private LayoutInflater mInflater;
    private View.OnClickListener listener;
    private SimpleDateFormat sdf;

    public OrderListAdapter(Context context, View.OnClickListener listener) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = new ArrayList<>();
        this.listener = listener;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public ArrayList<OrderFragmentModel.ValueEntity> getList() {
        return list;
    }

    public void setList(ArrayList<OrderFragmentModel.ValueEntity> list) {
        this.list = list;
        notifyDataSetChanged();
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
            convertView = mInflater.inflate(R.layout.order_list_item, null);
            holder.tvOrderState = (TextView) convertView.findViewById(R.id.order_list_item_tv_state);
            holder.tvOrderTime = (TextView) convertView.findViewById(R.id.order_list_item_tv_time);
            holder.tvOrderName = (TextView) convertView.findViewById(R.id.order_list_item_tv_name);
            holder.imgArrow = (ImageView) convertView.findViewById(R.id.order_list_right_arrow);
            holder.tvOrderMoney = (TextView) convertView.findViewById(R.id.order_list_item_tv_money);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.order_list_item_img_delet);
            holder.img = (CornerImageView) convertView.findViewById(R.id.order_list_item_img);
            holder.llImg = (LinearLayout) convertView.findViewById(R.id.order_list_item_img_father);
            holder.tvMoreOrder = (TextView) convertView.findViewById(R.id.order_state_more_one);
            holder.tvTakePhoto = (TextView) convertView.findViewById(R.id.order_state_take_photo);
            holder.tvEvaluate = (TextView) convertView.findViewById(R.id.order_state_evaluate);
            holder.tvConfirm = (TextView) convertView.findViewById(R.id.order_state_confirm);
            holder.tvPay = (TimeTextView) convertView.findViewById(R.id.order_state_go_pay);
            holder.rlGODetail = (RelativeLayout) convertView.findViewById(R.id.go_to_detail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvMoreOrder.setTag(position);
        holder.tvTakePhoto.setTag(position);
        holder.tvEvaluate.setTag(position);
        holder.tvConfirm.setTag(position);
        holder.tvPay.setTag(position);
        holder.imgDelete.setTag(position);
        holder.tvOrderName.setTag(position);
        holder.imgArrow.setTag(position);

        holder.llImg.setTag(position);
        holder.llImg.setOnClickListener(listener);
        holder.rlGODetail.setTag(position);
        holder.rlGODetail.setOnClickListener(listener);
        holder.tvConfirm.setOnClickListener(listener);
        holder.tvOrderName.setOnClickListener(listener);
        holder.imgArrow.setOnClickListener(listener);
        if (CheckUtils.isNoEmptyList(list) && list.size() > position) {
            showItem(list.get(position), holder);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tvOrderState, tvOrderTime, tvOrderName, tvOrderMoney;
        TextView tvMoreOrder, tvTakePhoto, tvEvaluate, tvConfirm;
        TimeTextView tvPay;
        ImageView imgDelete, imgArrow;
        RelativeLayout rlGODetail;
        LinearLayout llImg;
        CornerImageView img;
    }

    private void showItem(ValueEntity valueEntity, ViewHolder holder) {
        if (valueEntity != null) {
            int status = valueEntity.getOrderFlowStatus();
            if(status == 1){
                holder.tvOrderState.setTextColor(Color.parseColor("#ffff6633"));
            }else if(status == -1 || status == 7){
                holder.tvOrderState.setTextColor(Color.parseColor("#ff333333"));
            }else{
                holder.tvOrderState.setTextColor(Color.parseColor("#ffff9a00"));
            }
            holder.tvOrderState.setText(OrderFlowStatus.getOrderStatusByValue(valueEntity.getOrderFlowStatus()).getMemo());
            holder.tvOrderTime.setText("下单时间：" + valueEntity.getCreateTime());
            holder.img.setImageResource(R.drawable.home_default);
            if (valueEntity.getMerchantId() > 0) {
                holder.tvOrderName.setText(valueEntity.getMerchant().getName());
                ImageUtils.loadBitmap(context , valueEntity.getMerchant().getLogo(), holder.img, R.drawable.home_default , Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
            }
            holder.tvOrderMoney.setText(StringUtils.BigDecimal2Str(valueEntity.getTotalPrice()));
            holder.imgDelete.setOnClickListener(listener);
            holder.tvMoreOrder.setOnClickListener(listener);
            holder.tvPay.setOnClickListener(listener);
            holder.tvEvaluate.setOnClickListener(listener);
            switch (valueEntity.getOrderFlowStatus()) {
                case -1://取消
                    changeButtonShowState(holder, true, false, false, false, false);
                    break;
                case 0://已创建
                    changeButtonShowState(holder, true, false, false, true, false);
                    break;
                case 1://等待付款
                    changeButtonShowState(holder, false, false, false, false, true);
                    break;
                case 2://等待商家确认
                    changeButtonShowState(holder, false, false, false, true, false);
                    break;
                case 3://商家已接单
                    changeButtonShowState(holder, true, false, false, true, false);
                    break;
                case 4://配送员取货中
                    changeButtonShowState(holder, true, false, false, true, false);
                    break;
                case 5://配送员已取货
                    changeButtonShowState(holder, true, false, false, true, false);
                    break;
                case 6://等待送达
                    changeButtonShowState(holder, true, false, false, true, false);
                    break;
                case 7://完成
                    if(valueEntity.getHasComments()==0)
                        changeButtonShowState(holder, true, true, true, false, false);
                    else
                        changeButtonShowState(holder, true, true, false, false, false);
                    break;
            }
            String paymentExpireTime = valueEntity.getPaymentExpireTime();
            if (paymentExpireTime != null) {
                holder.tvPay.setTimes(getTimeBetween(valueEntity.getServerTime(), paymentExpireTime));
            } else {
                holder.tvPay.setVisibility(View.GONE);
            }
        }
    }

    private void changeButtonShowState(ViewHolder holder, boolean tvMoreOrderShow,
                                       boolean tvTakePhotoShow, boolean tvEvaluateShow, boolean tvConfirmShow, boolean tvPayShow) {
        if (tvMoreOrderShow) {
            holder.tvMoreOrder.setVisibility(View.VISIBLE);
        } else {
            holder.tvMoreOrder.setVisibility(View.GONE);
        }
        if (tvTakePhotoShow) {
            holder.tvTakePhoto.setVisibility(View.GONE);
        } else {
            holder.tvTakePhoto.setVisibility(View.GONE);
        }
        if (tvEvaluateShow) {
            holder.tvEvaluate.setVisibility(View.VISIBLE);
        } else {
            holder.tvEvaluate.setVisibility(View.GONE);
        }
        if (tvConfirmShow) {
            holder.tvConfirm.setVisibility(View.VISIBLE);
        } else {
            holder.tvConfirm.setVisibility(View.GONE);
        }
        if (tvPayShow) {
            holder.tvPay.setVisibility(View.VISIBLE);
        } else {
            holder.tvPay.setVisibility(View.GONE);
        }
    }

    private long getTimeBetween(String serverTime, String laterTime) {

        try {
            Date date1 = sdf.parse(serverTime);
            long time1 = date1.getTime();
            Date date2 = sdf.parse(laterTime);
            long time2 = date2.getTime();
            return time2 - time1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

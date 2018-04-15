package com.project.mgjandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.DayBean;
import com.project.mgjandroid.bean.PromotionActivity;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.bean.TimeBean;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ConfirmOrderModel;
import com.project.mgjandroid.model.PickGoodsModel;
import com.project.mgjandroid.model.SmsLoginModel;
import com.project.mgjandroid.model.SubmitOrderModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.ConfirmOrderListAdapter;
import com.project.mgjandroid.ui.adapter.SelectDayListAdapter;
import com.project.mgjandroid.ui.adapter.SelectTimeListAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.NoScrollListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener {
    public static final int REQUEST_GET_ADDRESS = 10001;
    public static final int RESPONSE_GET_ADDRESS = 10002;
    public static final int REQUEST_SET_CAUTION = 10003;
    public static final int RESPONSE_SET_CAUTION = 10004;
    public static final int REQUEST_CHOOSE_RED_BAG = 10005;
    public static final int RESPONSE_CHOOSE_RED_BAG = 10006;
    @InjectView(R.id.confirm_order_back)
    private ImageView img_back;
    @InjectView(R.id.top_address_tips)
    private TextView tv_noAddressTips;
    @InjectView(R.id.top_address_panel)
    private RelativeLayout rl_addressPanel;
    @InjectView(R.id.top_address)
    private RelativeLayout rl_topAddress;
    @InjectView(R.id.address_name)
    private TextView tv_name;
    @InjectView(R.id.address_sex)
    private TextView tv_sex;
    @InjectView(R.id.address_mobile)
    private TextView tv_mobile;
    @InjectView(R.id.address_description)
    private TextView tv_address;
    @InjectView(R.id.confirm_order_list_view)
    private NoScrollListView listView;
    @InjectView(R.id.receive_time)
    private TextView tv_receiveTime;
    @InjectView(R.id.payment_online)
    private RelativeLayout rl_onlinePay;
    @InjectView(R.id.payment_outline)
    private RelativeLayout rl_outlinePay;
    @InjectView(R.id.online_checkbox)
    private CheckBox cb_online;
    @InjectView(R.id.outline_checkbox)
    private CheckBox cb_outline;
    @InjectView(R.id.shipment_tip)
    private TextView tv_shipmentTip;
    @InjectView(R.id.shipping_fee)
    private TextView tv_shippingFee;
    @InjectView(R.id.use_red_bag)
    private RelativeLayout redBagLayout;
    @InjectView(R.id.use_red_bag_checkbox)
    private CheckBox cb_redBag;
    @InjectView(R.id.use_red_bag_margin)
    private View redBagMargin;
    @InjectView(R.id.tv_red_bag)
    private TextView tvRedbag;
    @InjectView(R.id.confirm_order_bottom_money)
    private TextView tv_totalPrice;
    @InjectView(R.id.box_fee_label)
    private RelativeLayout rl_boxFee;
    @InjectView(R.id.box_fee)
    private TextView tv_boxFee;
    @InjectView(R.id.confirm_order)
    private TextView tv_confirm;
    @InjectView(R.id.confirm_order_select_time)
    private RelativeLayout rl_selectTime;
    @InjectView(R.id.relative_cover)
    private RelativeLayout relativeCover;
    @InjectView(R.id.confirm_order_caution)
    private RelativeLayout rl_caution;
    @InjectView(R.id.tv_order_caution)
    private TextView tv_caution;
    @InjectView(R.id.confir_order_act_shipping_fee_label)
    private LinearLayout ll_shippingFeeLabel;
    @InjectView(R.id.confir_order_act_shipping_fee_change)
    private TextView tv_shippingFeeChangeRemind;
    @InjectView(R.id.confir_order_act_promotion_label)
    private LinearLayout ll_promotionLabel;
    @InjectView(R.id.confir_order_act_promotion_change)
    private TextView tv_promotionChangeRemind;
    @InjectView(R.id.promotion_layout)
    private LinearLayout promotionLayout;

    private double longitude;
    private double latitude;
    private JSONObject previewJsonData;
    private UserAddress userAddress;
    private ConfirmOrderModel confirmOrderModel;
    private ArrayList<ConfirmOrderModel.ValueEntity.OrderItemsEntity> data;
    private ConfirmOrderListAdapter listAdapter;
    private LayoutInflater inflater;
    private ListView timeListView;
    private SelectDayListAdapter dayListAdapter;
    private SelectTimeListAdapter timeListAdapter;
    private ArrayList<DayBean> dayList;
    private PopupWindow popupWindow;
    private int dayPosition = 0;
    private TimeBean selectTime;
    private int selectType = -1;
    private String errorMsg;
    private String caution = "";
    private MLoadingDialog mLoadingDialog;
    private boolean needPostDiscountAmt = false;
    private RedBag redBag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        Context context = this;
        inflater = LayoutInflater.from(context);
        Injector.get(this).inject();
        setListener();
        latitude = Double.parseDouble(PreferenceUtils.getLocation(this)[0]);
        longitude = Double.parseDouble(PreferenceUtils.getLocation(this)[1]);
        initBottomDialog();
        mLoadingDialog = new MLoadingDialog();
        initViews();
    }

    /**
     * 底部弹框
     */
    private void initBottomDialog() {
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.layout_confirm_order_select_time, null);
        ListView dayListView = (ListView) view.findViewById(R.id.day_list_view);
        dayListAdapter = new SelectDayListAdapter(R.layout.item_select_day_list_view, this);
        dayList = new ArrayList<>();
        dayListAdapter.setData(dayList);
        dayListView.setAdapter(dayListAdapter);
        dayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                timeListView.setSelection(0);
                dayList.get(dayPosition).setIsChecked(false);
                dayPosition = position;
                DayBean dayBean = dayList.get(position);
                dayBean.setIsChecked(true);
                dayListAdapter.notifyDataSetChanged();
                timeListAdapter.setData(dayBean.getTimeList());
            }
        });
        timeListView = (ListView) view.findViewById(R.id.time_list_view);
        timeListAdapter = new SelectTimeListAdapter(R.layout.item_select_time_list_view, this);
        ArrayList<TimeBean> timeList = new ArrayList<>();
        timeListAdapter.setData(timeList);
        timeListView.setAdapter(timeListAdapter);
        timeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (DayBean dayBean : dayList) {
                    ArrayList<TimeBean> timeList = dayBean.getTimeList();
                    for (TimeBean ti : timeList) {
                        ti.setIsChecked(false);
                    }
                }
                List<TimeBean> timeBeans = timeListAdapter.getData();
                selectTime = timeBeans.get(position);
                selectTime.setIsChecked(true);
                timeListAdapter.notifyDataSetChanged();
                String selectDay = null;
                for (DayBean dayBean : dayList) {
                    if (dayBean.isChecked()) {
                        selectDay = dayBean.getDay();
                    }
                }
                if (selectDay != null && !selectDay.contains("今天")) {
                    tv_receiveTime.setText(selectDay + " " + selectTime.getDay());
                } else {
                    tv_receiveTime.setText(selectTime.getDay());
                }
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    relativeCover.setVisibility(View.GONE);
                }
            }
        });
        TextView tv_cancel = (TextView) view.findViewById(R.id.select_time_cancel);
        tv_cancel.setOnClickListener(this);
        View popupOutside = view.findViewById(R.id.popup_outside);
        popupOutside.setOnClickListener(this);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.MenuDialogAnimation);
        popupWindow.setOutsideTouchable(true);
    }

    private void initViews() {
        listAdapter = new ConfirmOrderListAdapter(R.layout.item_confirm_order_list_view, this);
        data = new ArrayList<>();
        listAdapter.setData(data);
        listView.setAdapter(listAdapter);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("confirmOrderModel")) {
            confirmOrderModel = (ConfirmOrderModel) intent.getSerializableExtra("confirmOrderModel");
            setViewData();
        }
        if(intent != null && intent.hasExtra("onceMoreOrder")){
            previewJsonData = new JSONObject((Map<String, Object>) intent.getSerializableExtra("onceMoreOrder"));
        }
        if(intent != null && intent.hasExtra("caution")){
            caution = intent.getStringExtra("caution");
            tv_caution.setText(caution);
        }
    }

    private void setListener() {
        img_back.setOnClickListener(this);
        rl_topAddress.setOnClickListener(this);
        rl_onlinePay.setOnClickListener(this);
        cb_online.setOnClickListener(this);
        rl_outlinePay.setOnClickListener(this);
        cb_outline.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        rl_selectTime.setOnClickListener(this);
        rl_caution.setOnClickListener(this);
        cb_redBag.setOnClickListener(this);
        redBagLayout.setOnClickListener(this);
    }

    /**
     * 订单提交
     */
    private void submitOrder() {
        SmsLoginModel.ValueEntity.AppUserEntity userInfo = App.getUserInfo();
        try {
            if (userInfo != null) {
                previewJsonData.put("userId", userInfo.getId());
                previewJsonData.put("loginToken", userInfo.getToken());
            }
            if(!TextUtils.isEmpty(caution)){
                previewJsonData.put("caution", caution);
            }
            previewJsonData.put("userAddressId", userAddress.getId());
            previewJsonData.put("orderPayType", selectType);//1在线支付，2货到付款
            if (selectTime == null) {
                selectTime = dayList.get(0).getTimeList().get(0);//默认第0条
            }
            previewJsonData.put("expectedArrivalTime", selectTime.getId());//送达时间

            if (redBag == null && previewJsonData.containsKey("redBags")) {
                previewJsonData.remove("redBags");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mLoadingDialog.show(getFragmentManager(), "");
//        MLog.s("----json data--->" + previewJsonData.toString());
        Map<String, Object> map = new HashMap<>();
        map.put("data", previewJsonData.toString());
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        VolleyOperater<SubmitOrderModel> operater = new VolleyOperater<>(ConfirmOrderActivity.this);
        operater.doRequest(Constants.URL_SUBMIT_ORDER, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    SubmitOrderModel submitOrderModel = (SubmitOrderModel) obj;
                    if (submitOrderModel.getCode() == 0) {
                        //TODO 下单成功操作。
                        if (selectType == 2) {
//                            ToastUtils.displayMsg("下单成功，订单编号：" + submitOrderModel.getValue().getId(), mContext);
                            Intent intent = new Intent(ConfirmOrderActivity.this, OrderDetailActivity.class);
                            intent.putExtra(OrderDetailActivity.SUBMIT_ORDER_ENTITY, submitOrderModel.getValue());
//                            intent.putExtra("hasRedPackage",true);
                            startActivity(intent);
                            clearThisMerchantCart();
                            finish();
                        } else if (selectType == 1) {
                            Intent intent = new Intent(ConfirmOrderActivity.this, OnlinePayActivity.class);
                            intent.putExtra("orderId", submitOrderModel.getValue().getId());
                            startActivity(intent);
                            clearThisMerchantCart();
                        }
                    }
                }
            }
        }, SubmitOrderModel.class);
    }

    /**
     * 订单预览刷新
     */
    private void getOrderPreview() {
        mLoadingDialog.show(getFragmentManager(), "");
        if(userAddress!=null){
            previewJsonData.put("userAddressId", userAddress.getId());
        }
        if (needPostDiscountAmt) {
            previewJsonData.put("discountAmt", confirmOrderModel.getValue().getOriginalTotalPrice().doubleValue() - confirmOrderModel.getValue().getTotalPrice().doubleValue());
            needPostDiscountAmt = false;
        } else if (previewJsonData.containsKey("discountAmt")) {
            previewJsonData.remove("discountAmt");
        }
        previewJsonData.put("orderPayType", selectType);
        if (selectTime == null) {
            selectTime = dayList.get(0).getTimeList().get(0);
        }
        if (redBag != null) {
            ArrayList<Map<String, Object>> redBagRequestDTOs = new ArrayList<>();
            HashMap<String, Object> m = new HashMap<>();
            m.put("id", redBag.getId());
            m.put("name", redBag.getName());
            m.put("amt", redBag.getAmt());
            m.put("promotionType", redBag.getPromotionType());
            redBagRequestDTOs.add(m);
            previewJsonData.put("redBags", redBagRequestDTOs);
        } else if (previewJsonData.containsKey("redBags")) {
            previewJsonData.remove("redBags");
        }
        previewJsonData.put("expectedArrivalTime", selectTime.getId());

        Map<String, Object> params = new HashMap<>();
        params.put("data", previewJsonData.toString());
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        VolleyOperater<ConfirmOrderModel> operater = new VolleyOperater<>(ConfirmOrderActivity.this);
        operater.doRequest(Constants.URL_GET_ORDER_PREVIEW, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        errorMsg = (String) obj;
                        ToastUtils.displayMsg(errorMsg, mActivity);
                    } else {
                        errorMsg = null;
                        confirmOrderModel = (ConfirmOrderModel) obj;
                        setViewData();
                    }
                }
            }
        }, ConfirmOrderModel.class);
    }

    private void clearThisMerchantCart() {
        PickGoodsModel.getInstance().setHasChange(true);
        int position = -1;
        for (int i = 0; i < PickGoodsModel.getInstance().getMerchantPickGoodsList().size(); i++) {
            if (PickGoodsModel.getInstance().getMerchantPickGoodsList().get(i).getMerchantId() == confirmOrderModel.getValue().getMerchantId()) {
                position = i;
                break;
            }
        }
        if (position != -1) {
            PickGoodsModel.getInstance().getMerchantPickGoodsList().remove(position);
        }
    }

    /**
     * 设置界面数据
     */
    private void setViewData() {
        ConfirmOrderModel.ValueEntity valueEntity = confirmOrderModel.getValue();
        userAddress = valueEntity.getAddressInfo();
        if(userAddress != null){
            showAddress(userAddress);
            tv_noAddressTips.setVisibility(View.INVISIBLE);
            latitude = userAddress.getLatitude();
            longitude = userAddress.getLongitude();
            rl_addressPanel.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(valueEntity.getShippingFeeDiscountTip())) {
            ll_shippingFeeLabel.setVisibility(View.VISIBLE);
            tv_shippingFeeChangeRemind.setText(valueEntity.getShippingFeeDiscountTip());
        } else {
            ll_shippingFeeLabel.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(valueEntity.getPromoMutexInfo())){
            ll_promotionLabel.setVisibility(View.VISIBLE);
            tv_promotionChangeRemind.setText(valueEntity.getPromoMutexInfo());
        } else {
            ll_promotionLabel.setVisibility(View.GONE);
        }
        if(TextUtils.isEmpty(tv_receiveTime.getText())) {
            String time="";
            Map<String,String> map =valueEntity.getDeliveryTimes().get(0).getTimes().get(0);
            Set<String> keySet =map.keySet();
            for(String key:keySet){
                time=map.get(key);
            }

            tv_receiveTime.setText(time);
        }
        List<ConfirmOrderModel.ValueEntity.PaymentsEntity> payments = valueEntity.getPayments();
        for (ConfirmOrderModel.ValueEntity.PaymentsEntity payment : payments) {
            int paymentType = payment.getPaymentType();
            if (paymentType == 1) {
                rl_onlinePay.setVisibility(View.VISIBLE);
                if(!cb_outline.isChecked()) {
                    cb_online.setChecked(true);
                    selectType = 1;
                }
            } else if (paymentType == 2) {
                if(!cb_online.isChecked()) {
                    cb_outline.setChecked(true);
                    selectType = 2;
                }
                rl_outlinePay.setVisibility(View.VISIBLE);
            }
        }
        String shipmentTip = valueEntity.getShipmentTip();
        tv_shipmentTip.setText("(" + shipmentTip + ")");
        List<ConfirmOrderModel.ValueEntity.OrderItemsEntity> orderItems = valueEntity.getOrderItems();
        data.clear();
        data.addAll(orderItems);
        listAdapter.notifyDataSetChanged();

        tv_shippingFee.setText("¥" + StringUtils.BigDecimal2Str(valueEntity.getShippingFee()));
        tv_totalPrice.setText("总计:¥" + StringUtils.BigDecimal2Str(valueEntity.getTotalPrice()));

        double boxPrice = valueEntity.getBoxPrice().doubleValue();
        if (boxPrice == 0) {
            rl_boxFee.setVisibility(View.GONE);
        } else {
            rl_boxFee.setVisibility(View.VISIBLE);
            tv_boxFee.setText("¥" + boxPrice);
        }

        if (userAddress != null) {
            showAddress(userAddress);
            latitude = userAddress.getLatitude();
            longitude = userAddress.getLongitude();
        }
        dayList.clear();
        List<ConfirmOrderModel.ValueEntity.DeliveryTimesEntity> deliveryTimes = valueEntity.getDeliveryTimes();
        for (ConfirmOrderModel.ValueEntity.DeliveryTimesEntity deliveryTime : deliveryTimes) {
            DayBean dayBean = new DayBean(deliveryTime.getDay());
            List<Map<String, String>> times = deliveryTime.getTimes();
            ArrayList<TimeBean> timeBeans = new ArrayList<>();
            for (Map<String, String> time : times) {
                Set<String> strings = time.keySet();
                for (String str : strings) {
                    timeBeans.add(new TimeBean(str, time.get(str)));
                }
            }
            dayBean.setTimeList(timeBeans);
            dayList.add(dayBean);
        }
        if (dayList.size() > 0) {
            DayBean dayBean = dayList.get(0);
            dayBean.setIsChecked(true);
            ArrayList<TimeBean> timeList = dayBean.getTimeList();
            if (timeList.size() > 0) {
                timeList.get(0).setIsChecked(true);
            }
        }
        dayListAdapter.notifyDataSetChanged();

        promotionLayout.removeAllViews();
        if (CheckUtils.isNoEmptyList(valueEntity.getPromoList())) {
            View view = new View(mActivity);
            view.setBackgroundColor(getResources().getColor(R.color.common_gray_line));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.x10);
            promotionLayout.addView(view, params);

            for (PromotionActivity promotion : valueEntity.getPromoList()) {
                addPromotion(promotionLayout, promotion);
            }
            promotionLayout.setVisibility(View.VISIBLE);
        } else {
            promotionLayout.setVisibility(View.GONE);
        }

        if (valueEntity.getRedBagUsableCount() > 0) {
            redBagLayout.setVisibility(View.VISIBLE);
            redBagMargin.setVisibility(View.VISIBLE);
            if (redBag != null && redBag.getAmt() != null) {
                cb_redBag.setChecked(true);
                tvRedbag.setVisibility(View.VISIBLE);
                tvRedbag.setText("红包金额为" + StringUtils.BigDecimal2Str(redBag.getAmt()) + "元");
            } else {
                cb_redBag.setChecked(false);
                tvRedbag.setVisibility(View.GONE);
            }
        } else {
            redBag = null;
            redBagLayout.setVisibility(View.GONE);
            redBagMargin.setVisibility(View.GONE);
            tvRedbag.setVisibility(View.GONE);
            cb_redBag.setChecked(false);
        }
    }

    private void addPromotion(LinearLayout layout, PromotionActivity promotion) {
        LinearLayout childLayout = new LinearLayout(mActivity);
        childLayout.setOrientation(LinearLayout.HORIZONTAL);
        childLayout.setGravity(Gravity.CENTER_VERTICAL);
        if (CheckUtils.isNoEmptyStr(promotion.getPromoImg())) {
            ImageView image = new ImageView(mActivity);
            ImageUtils.loadBitmap(mActivity, promotion.getPromoImg(), image, R.drawable.jian , "");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DipToPx.dip2px(mActivity, 12), DipToPx.dip2px(mActivity, 12));
            childLayout.addView(image, params);
        }
        if (CheckUtils.isNoEmptyStr(promotion.getPromoName())) {
            TextView tv = new TextView(mActivity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            params.leftMargin = DipToPx.dip2px(mActivity, 5);
            tv.setText(promotion.getPromoName());
            tv.setSingleLine();
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setTextColor(mActivity.getResources().getColor(R.color.gray_text_3));
            tv.setTextSize(12);
            childLayout.addView(tv, params);
        }
        if (promotion.getDiscountAmt() != null) {
            TextView tv = new TextView(mActivity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = getResources().getDimensionPixelOffset(R.dimen.x15);
            tv.setText("- ¥" + promotion.getDiscountAmt());
            tv.setTextColor(0xffff9900);
            tv.setTextSize(12);
            childLayout.addView(tv, params);
        }
        LinearLayout.LayoutParams paramsChild = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsChild.topMargin = DipToPx.dip2px(mActivity, 4);
        layout.addView(childLayout, paramsChild);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_order_back:
                finish();
                break;
            case R.id.top_address:
                if(!TextUtils.isEmpty(errorMsg)){
                    ToastUtils.displayMsg(errorMsg,mActivity);
                    return;
                }
                if (!App.isLogin()) {
                    startActivity(new Intent(mActivity, SmsLoginActivity.class));
                    break;
                }
                Intent intent = new Intent(this, AddressManageActivity.class);
                intent.putExtra("MERCHANT_ID", confirmOrderModel.getValue().getMerchantId());
                if (userAddress != null) {
                    intent.putExtra("USER_ADDRESS_ID", userAddress.getId());
                }
                startActivityForResult(intent, REQUEST_GET_ADDRESS);
                break;
            case R.id.online_checkbox:
            case R.id.payment_online:
                cb_outline.setChecked(false);
                cb_online.setChecked(true);
                selectType = 1;
                getOrderPreview();
                break;
            case R.id.outline_checkbox:
            case R.id.payment_outline:
                cb_online.setChecked(false);
                cb_outline.setChecked(true);
                selectType = 2;
                getOrderPreview();
                break;
            case R.id.confirm_order:
                if(!TextUtils.isEmpty(errorMsg)){
                    ToastUtils.displayMsg(errorMsg,mActivity);
                    return;
                }
                if(userAddress == null){
                    ToastUtils.displayMsg("请选择配送地址",mActivity);
                    break;
                }
                if(selectType == -1){
                    ToastUtils.displayMsg("请选择支付方式",mActivity);
                    break;
                }
                submitOrder();
                break;
            case R.id.confirm_order_select_time:
                if(!TextUtils.isEmpty(errorMsg)){
                    ToastUtils.displayMsg(errorMsg,mActivity);
                    return;
                }
                if (popupWindow != null && !popupWindow.isShowing()) {
                    for (DayBean dayBean : dayList) {
                        if (dayBean.isChecked()) {
                            timeListAdapter.setData(dayBean.getTimeList());
                        }
                    }
                    popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                    relativeCover.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.confirm_order_caution :
                Intent intentCaution = new Intent(ConfirmOrderActivity.this, AddCautionActivity.class);
                intentCaution.putExtra("caution", caution);
                ConfirmOrderActivity.this.startActivityForResult(intentCaution, REQUEST_SET_CAUTION);
                break;
            case R.id.select_time_cancel:
            case R.id.popup_outside:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    relativeCover.setVisibility(View.GONE);
                }
                break;
            case R.id.use_red_bag_checkbox:
                if (redBag == null && cb_redBag.isChecked()) {
                    cb_redBag.setChecked(false);
                }
                if(userAddress == null) return;
                if (redBag != null) {
                    redBag = null;
                    cb_redBag.setChecked(false);
                    getOrderPreview();
                    break;
                }
            case R.id.use_red_bag:
                Intent intentRedBag = new Intent(ConfirmOrderActivity.this, MyRedBagActivity.class);
                intentRedBag.putExtra("longitude", userAddress.getLongitude());
                intentRedBag.putExtra("latitude", userAddress.getLatitude());
                intentRedBag.putExtra("itemsPrice", confirmOrderModel.getValue().getItemsPrice().doubleValue());
                intentRedBag.putExtra("merchantId", confirmOrderModel.getValue().getMerchantId());
                try {
                    intentRedBag.putExtra("PromoInfoJson", JSONArray.toJSONString(confirmOrderModel.getValue().getPromoList()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ConfirmOrderActivity.this.startActivityForResult(intentRedBag, REQUEST_CHOOSE_RED_BAG);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            switch (resultCode) {
                case RESPONSE_GET_ADDRESS:
                    if (userAddress == null || userAddress.getId() != ((UserAddress) data.getSerializableExtra("address")).getId()) {
                        needPostDiscountAmt = true;
                    }
                    userAddress = (UserAddress) data.getSerializableExtra("address");
                    tv_noAddressTips.setVisibility(View.INVISIBLE);
                    showAddress(userAddress);
                    latitude = userAddress.getLatitude();
                    longitude = userAddress.getLongitude();
                    rl_addressPanel.setVisibility(View.VISIBLE);
                    getOrderPreview();
                    break;
                case RESPONSE_SET_CAUTION :
                    caution = data.getStringExtra("caution");
                    if(caution != null) tv_caution.setText(caution);
                    break;
            }
        }
        if (requestCode == REQUEST_CHOOSE_RED_BAG) {
            if (data != null && resultCode == RESPONSE_CHOOSE_RED_BAG) {
                RedBag bag = (RedBag) data.getSerializableExtra("red_bag");
                if (null == redBag || (bag != null && !redBag.equals(bag))) {
                    redBag = bag;
                    cb_redBag.setChecked(true);
                    getOrderPreview();
                }
            }
        }
    }

    private void showAddress(UserAddress userAddress) {
        tv_name.setText(userAddress.getName());
        tv_sex.setText(userAddress.getGender());
        tv_mobile.setText(userAddress.getMobile());
        tv_address.setText(userAddress.getAddress() + " " + userAddress.getHouseNumber());
    }
}

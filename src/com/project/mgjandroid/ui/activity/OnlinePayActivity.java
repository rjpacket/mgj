package com.project.mgjandroid.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pingplusplus.android.PaymentActivity;
import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.ChargeType;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.BalancePayModel;
import com.project.mgjandroid.model.GetAlipayInfoModel;
import com.project.mgjandroid.model.PayWaysModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.ViewFindUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlinePayActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.online_pay_back)
    private ImageView onlineBack;
    @InjectView(R.id.pay_money)
    private TextView tvPayMoney;
    @InjectView(R.id.third_money)
    private TextView tvThirdMoney;
    @InjectView(R.id.account_extra_money)
    private TextView tvAccountExtraMoney;
    @InjectView(R.id.pay_extra_check)
    private ImageView ivExtra;
    @InjectView(R.id.online_pay_confirm)
    private TextView tvConfirm;
    @InjectView(R.id.pay_extra)
    private RelativeLayout rlExtra;
    @InjectView(R.id.third_pay_pannel)
    private LinearLayout thirdPanel;
    @InjectView(R.id.pay_container)
    private LinearLayout payLabelContainer;

    private MLoadingDialog loadingDialog;
    private String orderId;
    private PayWaysModel payWaysModel;
    private static final int REQUEST_CODE_PAYMENT = 1;
    private String payChannel;
    private int preTag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_pay);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        loadingDialog = new MLoadingDialog();
        onlineBack.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        rlExtra.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("orderId")) {
            orderId = intent.getStringExtra("orderId");
            getPayWays(orderId);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.online_pay_back:
                finish();
                break;
            case R.id.pay_extra:
                ivExtra.setSelected(!ivExtra.isSelected());
                if(ivExtra.isSelected()){
                    BigDecimal userBalance = payWaysModel.getValue().getUserBalance();
                    BigDecimal totalPrice = payWaysModel.getValue().getTotalPrice();
                    if(userBalance != null) {
                        if (userBalance.compareTo(totalPrice) >= 0) {
                            thirdPanel.setVisibility(View.GONE);
                            clearThirdCheck();
                            payChannel = "extra";
                            preTag = -1;
                        }else{
                            tvThirdMoney.setText("¥" + (totalPrice.subtract(userBalance)));
                        }
                    }
                }else{
                    tvThirdMoney.setText("¥" + payWaysModel.getValue().getTotalPrice());
                    if(thirdPanel.getVisibility() == View.GONE){
                        thirdPanel.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.online_pay_confirm:
                if(payChannel == null){
                    ToastUtils.displayMsg("请选择支付方式",mActivity);
                    return ;
                }
                if("extra".equals(payChannel)){
                    payExtraMoney(orderId);
                }else{
                    if(ivExtra.isSelected()) {
                        getPayInfo(orderId, payWaysModel.getValue().getUserBalance(), payChannel);
                    }else{
                        getPayInfo(orderId, BigDecimal.ZERO, payChannel);
                    }
                }
                break;
            case R.id.pay_alipay:
                int tag = (int) v.getTag();
                changeLabel(tag);
                if(preTag != -1){
                    changeLabel(preTag);
                }
                List<PayWaysModel.ValueEntity.ChargeTypesEntity> chargeTypes = payWaysModel.getValue().getChargeTypes();
                payChannel = chargeTypes.get(tag).getChannel();
                preTag = tag;
                break;
        }
    }

    private void clearThirdCheck() {
        for (int i = 0; i < payLabelContainer.getChildCount(); i++) {
            LinearLayout childAt = (LinearLayout) payLabelContainer.getChildAt(i);
            RelativeLayout childAt1 = (RelativeLayout) childAt.getChildAt(0);
            childAt1.getChildAt(2).setSelected(false);
        }
    }

    private void changeLabel(int tag) {
        LinearLayout childAt = (LinearLayout) payLabelContainer.getChildAt(tag);
        RelativeLayout childAt1 = (RelativeLayout) childAt.getChildAt(0);
        childAt1.getChildAt(2).setSelected(!childAt1.getChildAt(2).isSelected());
    }

    /**
     * 余额支付
     */
    private void payExtraMoney(String orderId) {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        VolleyOperater<BalancePayModel> operater = new VolleyOperater<BalancePayModel>(this);
        operater.doRequest(Constants.URL_EXTRA, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    BalancePayModel balancePayModel = (BalancePayModel) obj;
                    Intent intent = new Intent(OnlinePayActivity.this, OrderDetailActivity.class);
                    intent.putExtra(OrderDetailActivity.ORDER_ID, balancePayModel.getValue().getId());
                    intent.putExtra("hasRedPackage",true);
                    startActivity(intent);
                    finish();
                }
                loadingDialog.dismiss();
            }
        }, BalancePayModel.class);
    }

    /**
     * alipay获取payinfo
     */
    private void getPayInfo(String orderId, BigDecimal cost,String channel) {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("channel", channel);
        map.put("balanceCost", cost);
        VolleyOperater<GetAlipayInfoModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_PING_PAY, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    GetAlipayInfoModel getAlipayInfoModel = (GetAlipayInfoModel) obj;
                    String data = getAlipayInfoModel.getValue();
                    if(null == data) {
                        ToastUtils.displayMsg("请求出错" + "请检查URL" + "URL无法获取charge",mActivity);
                        return;
                    }

                    Intent intent = new Intent(mActivity, PaymentActivity.class);
                    intent.putExtra(PaymentActivity.EXTRA_CHARGE, data);
                    startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                }
                loadingDialog.dismiss();
            }
        }, GetAlipayInfoModel.class);
    }

    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                if("success".equals(result)){
                    Intent intent = new Intent(OnlinePayActivity.this, OrderDetailActivity.class);
                    intent.putExtra("orderId", orderId);
                    intent.putExtra("hasRedPackage",true);
                    startActivity(intent);
                    finish();
                }else if("fail".equals(result)){
                    String errorMsg = data.getExtras().getString("error_msg");
                    ToastUtils.displayMsg(errorMsg,mActivity);
                }else if("cancel".equals(result)){
                    ToastUtils.displayMsg("取消支付",mActivity);
                }
            }
        }
    }

    /**
     * 获取充值方式
     */
    private void getPayWays(String orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        VolleyOperater<PayWaysModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_PAY_WAYS, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    payWaysModel = (PayWaysModel) obj;
                    String price = payWaysModel.getValue().getTotalPrice().toString();
                    tvPayMoney.setText("¥" + price);
                    tvThirdMoney.setText("¥" + price);
                    BigDecimal userBalance = payWaysModel.getValue().getUserBalance();
                    tvAccountExtraMoney.setText("(账户余额：" + (userBalance == null ? 0:userBalance) + "元)");
                    creatPayContainer();
                }
            }
        }, PayWaysModel.class);
    }

    private void creatPayContainer() {
        PayWaysModel.ValueEntity payWaysModelValue = payWaysModel.getValue();
        List<PayWaysModel.ValueEntity.ChargeTypesEntity> chargeTypes = payWaysModelValue.getChargeTypes();
        for (int i = 0; i < chargeTypes.size(); i++) {
            PayWaysModel.ValueEntity.ChargeTypesEntity chargeTypesEntity = chargeTypes.get(i);
            LinearLayout item = (LinearLayout) mInflater.inflate(R.layout.layout_pay_item, null);
            RelativeLayout rlPayLabel = ViewFindUtils.find(item,R.id.pay_alipay);
            rlPayLabel.setTag(i);
            rlPayLabel.setOnClickListener(this);
            ImageView payIcon = ViewFindUtils.find(item,R.id.pay_icon);
            TextView tvName = ViewFindUtils.find(item,R.id.pay_name);
            TextView tvTip = ViewFindUtils.find(item,R.id.pay_tip);
            payIcon.setImageResource(ChargeType.findChargeTypeByValue(chargeTypesEntity.getChannel()).getIcon());
            tvName.setText(chargeTypesEntity.getName());
            tvTip.setText(chargeTypesEntity.getTip());
            payLabelContainer.addView(item);
        }
    }
}

package com.project.mgjandroid.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pingplusplus.android.PaymentActivity;
import com.project.mgjandroid.R;
import com.project.mgjandroid.alipay.PayController;
import com.project.mgjandroid.alipay.PayResult;
import com.project.mgjandroid.constants.ChargeType;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.PayWaysModel;
import com.project.mgjandroid.model.RechargeModel;
import com.project.mgjandroid.model.UserAccountModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.ViewFindUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cjh on 2016/3/23.充值页面
 */
public class RechargeActivity extends BaseActivity implements View.OnClickListener {
    private static final int CHANGE_RECHARGE = 0;
    private static final int REQUEST_CODE_PAYMENT = 1;
    @InjectView(R.id.recharge_back)
    private ImageView ivRechargeBack;
    @InjectView(R.id.recharge_money_selector)
    private RelativeLayout rlRechargeMoneySelector;
    @InjectView(R.id.recharge_more_method)
    private RelativeLayout rlRechargeMoreMethod;
    @InjectView(R.id.recharge_expect)
    private RelativeLayout rlRechargeExpect;
    @InjectView(R.id.recharge_money)
    private TextView tvRechargeMoney;
    @InjectView(R.id.recharge_confirm)
    private TextView rechargeConfirm;
    @InjectView(R.id.pay_label)
    private LinearLayout payLabelContainer;

    private int rechargeMethod= -1;
    private BigDecimal rechargeNumber;

    private PopupWindow rechargeNumberPopupwindow;
    private PayWaysModel payWaysModel;
    private int preTag = -1;
    private String payChannel;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_recharge);
        Injector.get(this).inject();
        rechargeNumber = BigDecimal.valueOf(50);
        getPayWays();
        initPopup();
        ivRechargeBack.setOnClickListener(this);
        rlRechargeMoneySelector.setOnClickListener(this);
        rlRechargeMoreMethod.setOnClickListener(this);
        rechargeConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.recharge_back:
                onBackPressed();
                break;
            case R.id.recharge_money_selector:
                /*
                点击弹出窗口
                 */
                popupWindow();
                break;
            case R.id.recharge_more_method:
                rlRechargeMoreMethod.setVisibility(View.GONE);
                rlRechargeExpect.setVisibility(View.VISIBLE);
                break;
            case R.id.recharge_50:
                resetPopup();
                tvRechargeMoney.setText("50.00元");
                tv1.setBackgroundResource(R.drawable.shap_top_corner_org_bg);
                rechargeNumberPopupwindow.dismiss();
                rechargeNumber = BigDecimal.valueOf(50);
                break;
            case R.id.recharge_100:
                resetPopup();
                tvRechargeMoney.setText("100.00元");
                tv2.setBackgroundColor(getResources().getColor(R.color.org_light));
                rechargeNumberPopupwindow.dismiss();
                rechargeNumber=BigDecimal.valueOf(100);
                break;
            case R.id.recharge_200:
                resetPopup();
                tvRechargeMoney.setText("200.00元");
                tv3.setBackgroundColor(getResources().getColor(R.color.org_light));
                rechargeNumberPopupwindow.dismiss();
                rechargeNumber=BigDecimal.valueOf(200);
                break;
            case R.id.recharge_300:
                resetPopup();
                tvRechargeMoney.setText("300.00元");
                tv4.setBackgroundColor(getResources().getColor(R.color.org_light));
                rechargeNumberPopupwindow.dismiss();
                rechargeNumber=BigDecimal.valueOf(300);
                break;
            case R.id.recharge_500:
                resetPopup();
                tvRechargeMoney.setText("500.00元");
                tv5.setBackgroundResource(R.drawable.shap_bottom_corner_org_bg);
                rechargeNumberPopupwindow.dismiss();
                rechargeNumber=BigDecimal.valueOf(500);
                break;
            case R.id.recharge_confirm:
                /*
                充值接口的调用 及获得返回值之后的回掉操作
                 */
                if(payChannel == null){
                    ToastUtils.displayMsg("请选择支付方式",mActivity);
                    return ;
                }
                chargeExtras(payChannel);
                break;
            case R.id.pay_alipay:
                int tag = (int) view.getTag();
                changeLabel(tag);
                if(preTag != -1){
                    changeLabel(preTag);
                }
                List<PayWaysModel.ValueEntity.ChargeTypesEntity> chargeTypes = payWaysModel.getValue().getChargeTypes();
                payChannel = chargeTypes.get(tag).getChannel();
                preTag = tag;
                break;
            default:
                break;
        }
    }

    private void changeLabel(int tag) {
        LinearLayout childAt = (LinearLayout) payLabelContainer.getChildAt(tag);
        RelativeLayout childAt1 = (RelativeLayout) childAt.getChildAt(0);
        childAt1.getChildAt(2).setSelected(!childAt1.getChildAt(2).isSelected());
    }

    TextView tv1,tv2,tv3,tv4,tv5;
    private void popupWindow() {
        // 产生背景变暗效果
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
//        rechargeNumberPopupwindow.showAsDropDown(rlRechargeMoneySelector);
        rechargeNumberPopupwindow.showAtLocation(rlRechargeExpect, Gravity.CENTER,0,0);
    }

    public void initPopup(){
        View contentView = LayoutInflater.from(RechargeActivity.this).inflate(R.layout.view_select_recharge_number, null);
        rechargeNumberPopupwindow= new PopupWindow(contentView, RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT, true);
        ColorDrawable cd = new ColorDrawable(0x000000);
        rechargeNumberPopupwindow.setBackgroundDrawable(cd);
        rechargeNumberPopupwindow.setOutsideTouchable(true);
        rechargeNumberPopupwindow.setFocusable(true);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                rechargeNumberPopupwindow.dismiss();
            }
        });
        tv1= (TextView) contentView.findViewById(R.id.recharge_50);
        tv2= (TextView) contentView.findViewById(R.id.recharge_100);
        tv3= (TextView) contentView.findViewById(R.id.recharge_200);
        tv4= (TextView) contentView.findViewById(R.id.recharge_300);
        tv5= (TextView) contentView.findViewById(R.id.recharge_500);

        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);

        rechargeNumberPopupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    public void resetPopup(){
        tv1.setBackgroundResource(R.drawable.shap_top_corner_white_bg);
        tv2.setBackgroundColor(getResources().getColor(R.color.white));
        tv3.setBackgroundColor(getResources().getColor(R.color.white));
        tv4.setBackgroundColor(getResources().getColor(R.color.white));
        tv5.setBackgroundResource(R.drawable.shap_bottom_corner_white_bg);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK, new Intent());
    }

    /**
     * 充值余额
     */
    private void chargeExtras(String channel) {
        Map<String,Object> map = new HashMap<>();
        map.put("amt", rechargeNumber);
        map.put("channel", channel);
        VolleyOperater<RechargeModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_ALIPAY_CHARGE, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    RechargeModel rechargeModel = (RechargeModel) obj;
                    String data = rechargeModel.getValue();
                    if (null == data) {
                        ToastUtils.displayMsg("请求出错" + "请检查URL" + "URL无法获取charge", mActivity);
                        return;
                    }

                    Intent intent = new Intent(mActivity, PaymentActivity.class);
                    intent.putExtra(PaymentActivity.EXTRA_CHARGE, data);
                    startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                }
            }
        }, RechargeModel.class);
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
                    Intent intent = new Intent(RechargeActivity.this, BalanceOperateActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
    private void getPayWays() {
        VolleyOperater<PayWaysModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_PAY_WAYS, null, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    payWaysModel = (PayWaysModel) obj;
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
            RelativeLayout rlPayLabel = ViewFindUtils.find(item, R.id.pay_alipay);
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

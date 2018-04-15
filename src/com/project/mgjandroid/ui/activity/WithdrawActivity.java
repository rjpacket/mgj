package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.UserAccountModel;
import com.project.mgjandroid.model.WithdrawLimit;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;

/**
 * Created by Cjh on 2016/3/23. 提现页面
 */
public class WithdrawActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.withdraw_back)
    private ImageView ivWithdrawBack;
    @InjectView(R.id.withdraw_current_balance)
    private TextView tvWithdrawCurrentBalance;
    @InjectView(R.id.withdraw_confirm)
    private TextView tvWithdrawConfirm;
    @InjectView(R.id.withdraw_number)
    private EditText withdrawNumber;
    @InjectView(R.id.error_tip)
    private TextView tvError;
    //可提取余额
    private double currentBalance;
    private double mMinAmt = -1;
    private MLoadingDialog mDialog;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_withdraw);
        Injector.get(this).inject();

        Intent intent = getIntent();
        currentBalance = intent.getDoubleExtra("currentBalance", 0.00);
        tvWithdrawCurrentBalance.setText(String.valueOf(currentBalance));

        ivWithdrawBack.setOnClickListener(this);
        tvWithdrawConfirm.setOnClickListener(this);
        mDialog = new MLoadingDialog();
        getExtraMoney();
        withdrawNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString().trim();
                int len = temp.length();
                if(s.length() > 0) {
                    tvWithdrawConfirm.setEnabled(true);
                    if(!temp.equals(".")&& new BigDecimal(Double.parseDouble(temp)).compareTo(new BigDecimal(1000000)) >= 0){
                        s.delete(len - 1, len);
                    }
                    if(len > 1 && temp.charAt(0) == "0".charAt(0) && temp.charAt(1) != ".".charAt(0)) {
                        s.delete(0, 1);
                    }

                    if(!temp.equals(".")){
                        if (Double.parseDouble(temp) < mMinAmt) {
                            tvError.setVisibility(View.VISIBLE);
                            tvError.setText("每次提现金额最小为" + mMinAmt + "元");
                            withdrawNumber.setBackgroundResource(R.drawable.shape_red_range_bg);
                            tvWithdrawConfirm.setEnabled(false);
                        } else if(Double.parseDouble(temp) > currentBalance){
                            tvError.setVisibility(View.VISIBLE);
                            tvError.setText(getString(R.string.withdraw_tip1));
                            withdrawNumber.setBackgroundResource(R.drawable.shape_red_range_bg);
                            tvWithdrawConfirm.setEnabled(false);
                        }else{
                            tvError.setVisibility(View.GONE);
                            withdrawNumber.setBackgroundResource(R.drawable.shap_gray_range_bg);
                            tvWithdrawConfirm.setEnabled(true);
                        }
                    }
                } else {
                    tvError.setVisibility(View.GONE);
                    withdrawNumber.setBackgroundResource(R.drawable.shap_gray_range_bg);
                    tvWithdrawConfirm.setEnabled(false);
                }

                int d = temp.indexOf(".");
                if (d >= 0) {
                    if (temp.length() - d - 1 > 2) {
                        s.delete(d + 3, d + 4);
                    } else if (d == 0) {
                        s.delete(d, d + 1);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.withdraw_back:
                onBackPressed();
                break;
            case R.id.withdraw_confirm:
                String money = withdrawNumber.getText().toString();
                if(money.endsWith(".")||money.startsWith(".")||money.matches("0[0-9]*")|| TextUtils.isEmpty(money)){
                    ToastUtils.displayMsg("输入金额有误", WithdrawActivity.this);
                    break;
                }
                /*
                此处请求服务器 并获得返回值 返回我的页面 并进行刷新
                 */
                Intent intent = new Intent();
                intent.putExtra("cash",money);
                setResult(33,intent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK, new Intent());
    }

    /**
     * 获取余额
     */
    private void getExtraMoney() {
        mDialog.show(getFragmentManager(),"");
        mDialog.setCancelable(false);
        VolleyOperater<UserAccountModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CHECK_EXTRA_MONEY, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    UserAccountModel userAccountModel = (UserAccountModel) obj;
                    UserAccountModel.ValueEntity value = userAccountModel.getValue();
                    currentBalance = value.getBalance().doubleValue();
                    tvWithdrawCurrentBalance.setText(String.valueOf(currentBalance));
                    getExtraMoneyLimit();
                }
            }
        }, UserAccountModel.class);
    }

    /**
     * 获取限制
     */
    private void getExtraMoneyLimit() {
        VolleyOperater<WithdrawLimit> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CASH_LIMIT, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mDialog.dismiss();
                if (isSucceed && obj != null) {
                    WithdrawLimit withdrawLimit = (WithdrawLimit) obj;
                    mMinAmt = withdrawLimit.getValue().getMinAmt();
                }
            }
        }, WithdrawLimit.class);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CommonUtils.hideKeyBoard2(withdrawNumber);
        return super.onTouchEvent(event);
    }
}

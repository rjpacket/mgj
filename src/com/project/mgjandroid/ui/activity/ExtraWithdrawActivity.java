package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.text.DecimalFormat;

public class ExtraWithdrawActivity extends BaseActivity {
    public static final int WITHDRAW_SUCCESS = 102;
    public static final int WITHDRAW_FAIL = 201;
    @InjectView(R.id.common_back) private ImageView imgBack;
    @InjectView(R.id.common_title) private TextView tvTitle;
    @InjectView(R.id.withdraw_icon) private ImageView imgIcon;
    @InjectView(R.id.withdraw_tip1) private TextView tvTip;
    @InjectView(R.id.withdraw_tip2) private TextView tvMoney;
    @InjectView(R.id.withdraw_tip3) private TextView tvPhone;
    @InjectView(R.id.withdraw_back) private TextView tvBack;
    private DecimalFormat mDf;
    private int mState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_withdraw);
        Injector.get(this).inject();
        tvTitle.setText("余额提现");
        imgBack.setOnClickListener(this);
        mDf = new DecimalFormat("0.00");
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("state")){
            mState = intent.getIntExtra("state", -1);
            String cash = intent.getStringExtra("cash");
            switch (mState){
                case WITHDRAW_SUCCESS:
                    imgIcon.setImageResource(R.drawable.withdraw_success);
                    tvTip.setText("提现申请已提交");
                    tvMoney.setText(String.format(getString(R.string.extra_tip),mDf.format(Double.parseDouble(cash))));
                    tvPhone.setText(getString(R.string.extra_two_day) + getString(R.string.sale_phone));
                    break;
                case WITHDRAW_FAIL:
                    imgIcon.setImageResource(R.drawable.withdraw_fail);
                    tvTip.setText("提现申请提交失败");
                    tvMoney.setText(String.format(getString(R.string.extra_tip),mDf.format(Double.parseDouble(cash))));
                    tvPhone.setText(getString(R.string.extra_tip2));
                    tvBack.setText("返回重新提交");
                    break;
            }
        }
        tvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.common_back:
            case R.id.withdraw_back:
                switch (mState){
                    case WITHDRAW_SUCCESS:
                        Intent intent = new Intent(this,BalanceOperateActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        break;
                    case WITHDRAW_FAIL:
                        onBackPressed();
                        break;
                }
                break;
        }
    }
}

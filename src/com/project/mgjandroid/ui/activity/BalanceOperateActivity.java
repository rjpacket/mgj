package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.AccountDetailsModel;
import com.project.mgjandroid.model.UserAccountModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.RencentConsumeAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rjp on 2016/3/23.
 * Email:rjpgoodjob@gmail.com
 */
public class BalanceOperateActivity extends BaseActivity implements View.OnClickListener {
    private final static int OPERATE_WITHDRAW=0;
    private final static int OPERATE_RECHARGE=1;

    @InjectView(R.id.balance_operate_back)
    private ImageView imgBalanceOperateBack;
    @InjectView(R.id.balance_operate_withdraw)
    private TextView tvBalanceOperateWithdraw;
    @InjectView(R.id.balance_operate_recharge)
    private TextView tvBalanceOperateRecharge;
    @InjectView(R.id.balance_operate_current_balance)
    private TextView tvBalance;
    @InjectView(R.id.recent_consume_listView)
    private ListView recentConsumeListView;
    @InjectView(R.id.recent_consume_noView)
    private LinearLayout recentConsumeNoView;
    @InjectView(R.id.balance_operate_balance_explanation)
    private TextView balanceExplanation;

    private static final int maxResults = 10;
    private int currentSection = 0;
    private View footerView;
    private RencentConsumeAdapter adapter;
    private boolean isBottom = false;
    private boolean hasMoreData = false;
    ArrayList<AccountDetailsModel.ValueEntity> data = new ArrayList<>();
    private MLoadingDialog loadingDialog;
    private double mCash;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_balance_operate);
        Injector.get(this).inject();

        imgBalanceOperateBack.setOnClickListener(this);
        tvBalanceOperateWithdraw.setOnClickListener(this);
        tvBalanceOperateRecharge.setOnClickListener(this);
        balanceExplanation.setOnClickListener(this);
        loadingDialog = new MLoadingDialog();
        footerView = LayoutInflater.from(this).inflate(R.layout.view_footer_load_more,null);
        recentConsumeListView.addFooterView(footerView);
        adapter=new RencentConsumeAdapter(R.layout.item_recent_consume,BalanceOperateActivity.this);
        recentConsumeListView.setAdapter(adapter);
        recentConsumeListView.setEmptyView(recentConsumeNoView);
        recentConsumeListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (isBottom) {
                        if (hasMoreData) {
                            footerView.setVisibility(View.VISIBLE);
                            getAccountDetails();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom = firstVisibleItem + visibleItemCount == totalItemCount;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getExtraMoney();
        currentSection = 0;
        getAccountDetails();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.balance_operate_back:
                onBackPressed();
                break;
            case R.id.balance_operate_withdraw:
                //暂时不跳转，做弹窗提示拨打客服
                Intent intent= new Intent(BalanceOperateActivity.this, WithdrawWayActivity.class);
                intent.putExtra("cash",mCash);
                startActivityForResult(intent, OPERATE_WITHDRAW);
                break;
            case R.id.balance_operate_recharge:
                startActivityForResult(new Intent(BalanceOperateActivity.this,RechargeActivity.class),OPERATE_RECHARGE );
                break;
            case R.id.balance_operate_balance_explanation:
                startActivityForResult(new Intent(BalanceOperateActivity.this,BalanceExplanationActivity.class),0);
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
        loadingDialog.show(getFragmentManager(), "");
        VolleyOperater<UserAccountModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CHECK_EXTRA_MONEY, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    UserAccountModel userAccountModel = (UserAccountModel) obj;
                    UserAccountModel.ValueEntity value = userAccountModel.getValue();
                    mCash = value.getBalance().doubleValue();
                    tvBalance.setText(String.valueOf(value.getBalance()));
                }
            }
        }, UserAccountModel.class);
    }

    /**
     * 获得收支明细
     */
    private void getAccountDetails() {
        Map<String,Object> map = new HashMap<>();
        map.put("start",currentSection);
        map.put("size",maxResults);
        VolleyOperater<AccountDetailsModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_ACCOUNT_DETAILS, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    AccountDetailsModel accountDetailsModel= (AccountDetailsModel) obj;
                    List<AccountDetailsModel.ValueEntity> list= accountDetailsModel.getValue();
                    hasMoreData = list.size() >= maxResults;
                    if(currentSection == 0) {
                        data.clear();
                    }
                    data.addAll(list);
                    currentSection += maxResults;
                    adapter.setData(data);
                    footerView.setVisibility(View.GONE);
                }
            }
        }, AccountDetailsModel.class);
    }
}

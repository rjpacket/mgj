package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Bank;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ChooseBankListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.ChooseBankCardListAdapter;
import com.project.mgjandroid.ui.view.CommonDialog;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.List;

public class BankCardInfoActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @InjectView(R.id.common_back) private ImageView imgBack;
    @InjectView(R.id.common_title) private TextView tvTitle;
    @InjectView(R.id.bank_name) private TextView tvBankName;
    @InjectView(R.id.user_name) private EditText etUserName;
    @InjectView(R.id.choose_bank_card) private LinearLayout llChooseCard;
    @InjectView(R.id.add_card_next) private TextView tvNext;
    private CommonDialog mCommonDialog;
    private ChooseBankCardListAdapter mAdapter;
    private Bank mSelectBank;
    private String mCardNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_info);
        Injector.get(this).inject();
        tvTitle.setText("银行卡信息");
        initView();
    }

    private void initView() {
        imgBack.setOnClickListener(this);
        llChooseCard.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        newDialog();
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("cardNumber")){
            mCardNumber = intent.getStringExtra("cardNumber");
        }
        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkState();
            }
        });
        checkState();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.choose_bank_card:
                if(mCommonDialog == null) {
                    newDialog().show();
                }else{
                    if(!mCommonDialog.isShowing()){
                        mCommonDialog.show();
                    }
                }
                break;
            case R.id.add_card_next:
                Intent intent = new Intent(this,WithdrawWayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("cardNumber",mCardNumber);
                intent.putExtra("userName",etUserName.getText().toString().trim());
                intent.putExtra("bank",mSelectBank);
                startActivity(intent);
                break;
            case R.id.delete_dialog:
                if(mCommonDialog != null && mCommonDialog.isShowing()){
                    mCommonDialog.dismiss();
                }
                break;
        }
    }

    private CommonDialog newDialog() {
        View view = mInflater.inflate(R.layout.choose_bank_card, null ,false);
        mCommonDialog = new CommonDialog(mActivity,view, this);
        mCommonDialog.setCanceledOnTouchOutside(false);
        ListView dialogListView = (ListView) view.findViewById(R.id.list_dialog_list_view);
        view.findViewById(R.id.delete_dialog).setOnClickListener(this);
        mAdapter = new ChooseBankCardListAdapter(R.layout.item_dialog_list_view,mActivity);
        dialogListView.setAdapter(mAdapter);
        dialogListView.setOnItemClickListener(this);
        getBank();
        return mCommonDialog;
    }

    /**
     * 获取开户行类型
     */
    private void getBank() {
        VolleyOperater<ChooseBankListModel> operater = new VolleyOperater<>(BankCardInfoActivity.this);
        operater.doRequest(Constants.URL_FIND_BANK_LIST, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    ChooseBankListModel chooseBankListModel = (ChooseBankListModel) obj;
                    mAdapter.setData(chooseBankListModel.getValue());
                }
            }
        }, ChooseBankListModel.class);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<Bank> data = mAdapter.getData();
        for (Bank bank : data) {
            bank.setIsCheck(false);
        }
        mSelectBank = data.get(position);
        mSelectBank.setIsCheck(true);
        tvBankName.setText(mSelectBank.getBankName());
        mCommonDialog.dismiss();
        checkState();
    }

    public void checkState(){
        if(!TextUtils.isEmpty(etUserName.getText().toString().trim()) && mSelectBank != null){
            tvNext.setEnabled(true);
        }else{
            tvNext.setEnabled(false);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CommonUtils.hideKeyBoard2(etUserName);
        return super.onTouchEvent(event);
    }
}

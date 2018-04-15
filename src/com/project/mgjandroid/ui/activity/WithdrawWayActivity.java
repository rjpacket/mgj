package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Bank;
import com.project.mgjandroid.bean.BankCard;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.UserBankCardsModel;
import com.project.mgjandroid.model.WithdrawCashModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WithdrawWayActivity extends BaseActivity {
    @InjectView(R.id.common_back)
    private ImageView imgBack;
    @InjectView(R.id.withdraw_cash)
    private TextView tvCash;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.withdraw_way_confirm)
    private TextView tvConfirm;
    @InjectView(R.id.add_bank_card)
    private LinearLayout llAddBankCard;
    @InjectView(R.id.card_container)
    private LinearLayout llCardContainer;
    @InjectView(R.id.withdraw_cash_layout) private LinearLayout drawCashLayout;
    @InjectView(R.id.withdraw_cash) private TextView tvDrawCash;
    private MLoadingDialog dialog;
    private ArrayList<BankCard> mBankCards;
    private BankCard mSelectCard;
    private String mCash = "0.00";
    private DecimalFormat mDf;
    private MLoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_way);
        Injector.get(this).inject();
        tvTitle.setText("提现方式");
        imgBack.setOnClickListener(this);
        initView();
    }

    private void initView() {
        tvConfirm.setOnClickListener(this);
        drawCashLayout.setOnClickListener(this);
        llAddBankCard.setOnClickListener(this);
        dialog = new MLoadingDialog();
        mBankCards = new ArrayList<>();
        getData();
        mDf = new DecimalFormat("0.00");
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("cash")){
            mCash = mDf.format(intent.getDoubleExtra("cash",0));
            tvCash.setText(mCash + "元");
        }
        checkState();
        loadingDialog = new MLoadingDialog();
        loadingDialog.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.withdraw_way_confirm:
//                if(Double.parseDouble(mCash) <= 0){
//                    ToastUtils.displayMsg("请填写提现金额",mActivity);
//                    return ;
//                }
//
//                if(mSelectCard == null){
//                    ToastUtils.displayMsg("请选择银行卡",mActivity);
//                    return ;
//                }
                loadingDialog.show(getFragmentManager(),"");
                drawCash();
                break;
            case R.id.withdraw_cash_layout:
                Intent withdraw = new Intent(this,WithdrawActivity.class);
                startActivityForResult(withdraw, 22);
                break;
            case R.id.add_bank_card:
                Intent intent1 = new Intent(this, AddBankCardActivity.class);
                startActivity(intent1);
                break;
            case R.id.bank_label:
                int tag = (int) v.getTag();
                clickPosition(tag);
                break;
        }
    }

    private void clickPosition(int tag) {
        for (int i = 0; i < mBankCards.size(); i++) {
            View view = llCardContainer.getChildAt(i);
            view.setSelected(false);
        }
        View view = llCardContainer.getChildAt(tag);
        view.setSelected(true);
        mSelectCard = mBankCards.get(tag);
        checkState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null){
            switch (resultCode){
                case 33:
                    mCash = data.getStringExtra("cash");
                    tvCash.setText(mDf.format(Double.parseDouble(mCash)) + "元");
                    checkState();
                    break;
            }
        }
    }

    private void checkState() {
        if(Double.parseDouble(mCash) <= 0 || mSelectCard == null){
            tvConfirm.setEnabled(false);
        }else{
            tvConfirm.setEnabled(true);
        }
    }

    private void getData() {
        dialog.show(getFragmentManager(), "");
        VolleyOperater<UserBankCardsModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_USER_DRAW_CASH_BANK_LIST, null, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                dialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    UserBankCardsModel userBankCardsModel = (UserBankCardsModel) obj;
                    if (userBankCardsModel.getCode() == 0 && CheckUtils.isNoEmptyList(userBankCardsModel.getValue())) {
                        mBankCards = userBankCardsModel.getValue();
                        for (int i = 0; i < mBankCards.size(); i++) {
                            addCardInContainer(mBankCards.get(i), i);
                        }
                        if(mBankCards.size() == 1){
                            clickPosition(0);
                        }
                    }
                }
            }
        }, UserBankCardsModel.class);
    }

    private void addCardInContainer(BankCard bankCard, int position) {
        View view = mInflater.inflate(R.layout.item_withdraw_way_list, null, false);
        LinearLayout llLabel = (LinearLayout) view.findViewById(R.id.bank_label);
        ImageView imgLogo = (ImageView) view.findViewById(R.id.bank_logo);
        TextView tvName = (TextView) view.findViewById(R.id.bank_name);
        TextView tvNumber = (TextView) view.findViewById(R.id.bank_number);
        llLabel.setTag(position);
        llLabel.setOnClickListener(this);
        ImageUtils.loadBitmap(mActivity,bankCard.getBankLogo(),imgLogo,R.drawable.home_default , "");
        tvName.setText("【" + bankCard.getBankName() + "】");
        String card = bankCard.getBankCard();
        tvNumber.setText(card.substring(0,4) + "*******" + card.substring(card.length() - 4 , card.length()));
        llCardContainer.addView(view);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.hasExtra("bank")) {
            String cardNumber = intent.getStringExtra("cardNumber");
            String userName = intent.getStringExtra("userName");
            Bank selectBank = (Bank) intent.getSerializableExtra("bank");
            BankCard bankCard = new BankCard();
            bankCard.setBankCard(cardNumber);
            bankCard.setBankName(selectBank.getBankName());
            bankCard.setBankPerson(userName);
            bankCard.setBankId(selectBank.getId());
            bankCard.setBankLogo(selectBank.getBankLogo());
            addCardInContainer(bankCard, mBankCards.size());
            mBankCards.add(bankCard);
            clickPosition(mBankCards.size() - 1);
        }
    }

    /**
     * 提现
     */
    private void drawCash() {
        Map<String, Object> map = new HashMap<>();
        if(mCash != null){
            map.put("amt", Double.parseDouble(mCash));
        }else{
            map.put("amt", 0);
        }
        map.put("bankName", mSelectCard.getBankName());
        map.put("bankId", mSelectCard.getBankId());
        map.put("bankCard", mSelectCard.getBankCard());
        map.put("bankPerson", mSelectCard.getBankPerson());
        map.put("type", "bank");
        VolleyOperater<WithdrawCashModel> operater = new VolleyOperater<>(WithdrawWayActivity.this);
        operater.doRequest(Constants.URL_DRAW_CASH, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                Intent intent = new Intent(mActivity,ExtraWithdrawActivity.class);
                intent.putExtra("cash", mCash);
                if (isSucceed && obj != null) {
                    if(obj instanceof String){
                        loadingDialog.dismiss();
                        ToastUtils.displayMsg((String)obj,mActivity);
                        return ;
                    }
                    WithdrawCashModel withdrawCashModel = (WithdrawCashModel) obj;
                    if(withdrawCashModel.getCode() == 0) {
                        intent.putExtra("state", ExtraWithdrawActivity.WITHDRAW_SUCCESS);
                        startActivity(intent);
                    }
                }else{
                    intent.putExtra("state", ExtraWithdrawActivity.WITHDRAW_FAIL);
                    startActivity(intent);
                }
                loadingDialog.dismiss();
            }
        }, WithdrawCashModel.class);
    }
}

package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.BaiduGeocoderModel;
import com.project.mgjandroid.model.DeleteAddressModel;
import com.project.mgjandroid.model.EditAddressModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener{
    public static final String KEY_POI_INFO = "POI_INFO";
    public static final int SET_POI_INFO_REQUEST = 0;
    @InjectView(R.id.add_address_act_back)
    private ImageView ivBack;
    @InjectView(R.id.add_address_act_tv_title)
    private TextView tvTitle;
    @InjectView(R.id.add_address_act_delete)
    private ImageView ivDelete;
    @InjectView(R.id.add_address_edt_name)
    private EditText etName;
    @InjectView(R.id.add_address_name_iv_delete)
    private ImageView ivNameDel;
    @InjectView(R.id.add_address_cb_sir)
    private CheckBox cbMale;
    @InjectView(R.id.add_address_cb_miss)
    private CheckBox cbFemale;
    @InjectView(R.id.add_address_edt_mobile)
    private EditText etMobile;
    @InjectView(R.id.add_address_mobile_iv_delete)
    private ImageView ivMobileDel;
    @InjectView(R.id.add_address_phone_option_iv_add)
    private ImageView ivAddPhoneOption;
    @InjectView(R.id.add_address_edt_phone_option_layout)
    private LinearLayout llPhoneOption;
    @InjectView(R.id.add_address_edt_phone_option)
    private EditText etPhoneOption;
    @InjectView(R.id.add_address_phone_option_iv_delete)
    private ImageView ivPhoneOptionDel;
    @InjectView(R.id.location_address)
    private TextView tvLocation;
    @InjectView(R.id.add_address_edt_house)
    private EditText etHouseNum;
    @InjectView(R.id.add_address_house_iv_delete)
    private ImageView ivHouseNumDel;
    @InjectView(R.id.add_address_act_tv_save)
    private TextView tvSaveAddress;

    private UserAddress userAddress;
    private long merchantId;
    private Double latitude;
    private Double longitude;
    private PopupWindow phoneWindow;
    private MLoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.add_address_act);
        Injector.get(this).inject();
        userAddress = (UserAddress) getIntent().getSerializableExtra("USER_ADDRESS");
        merchantId = getIntent().getLongExtra("MERCHANT_ID", -1);
        initView();
    }

    private void initView(){
        if(userAddress != null){
            tvTitle.setText(getString(R.string.modify_address));
            ivDelete.setVisibility(View.VISIBLE);
            etName.setText(userAddress.getName());
            etMobile.setText(userAddress.getMobile());
            etHouseNum.setText(userAddress.getHouseNumber());
            tvLocation.setText(userAddress.getAddress());
            latitude = userAddress.getLatitude();
            longitude = userAddress.getLongitude();
            if(userAddress.getGender().equals(getString(R.string.sir))){
                cbMale.setChecked(true);
            }else if(userAddress.getGender().equals(getString(R.string.miss))){
                cbFemale.setChecked(true);
            }
            if(!TextUtils.isEmpty(userAddress.getBackupMobile())){
                ivAddPhoneOption.setVisibility(View.GONE);
                llPhoneOption.setVisibility(View.VISIBLE);
                etPhoneOption.setText(userAddress.getBackupMobile());
            }
        }else{
            tvTitle.setText(getString(R.string.add_address));
        }
        loadingDialog = new MLoadingDialog();
        ivBack.setOnClickListener(this);
        tvSaveAddress.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        ivNameDel.setOnClickListener(this);
        ivMobileDel.setOnClickListener(this);
        ivHouseNumDel.setOnClickListener(this);
        ivAddPhoneOption.setOnClickListener(this);
        ivPhoneOptionDel.setOnClickListener(this);
        cbMale.setOnClickListener(this);
        cbFemale.setOnClickListener(this);
        tvLocation.setOnClickListener(this);
        etName.setOnFocusChangeListener(this);
        etMobile.setOnFocusChangeListener(this);
        etHouseNum.setOnFocusChangeListener(this);
        etPhoneOption.setOnFocusChangeListener(this);
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivNameDel.setVisibility(View.VISIBLE);
                } else {
                    ivNameDel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    ivMobileDel.setVisibility(View.VISIBLE);
                    String mobile = App.getUserInfo().getMobile();
                    if(mobile != null) {
                        if (mobile.startsWith(s.toString()) && s.length() < 11) {
                            if (phoneWindow == null) {
                                showPhonePop(etMobile);
                            } else if (!phoneWindow.isShowing()) {
                                phoneWindow.showAsDropDown(etMobile, 0, DipToPx.dip2px(AddAddressActivity.this, 10));
                            }
                        } else {
                            if (phoneWindow != null && phoneWindow.isShowing()) {
                                phoneWindow.dismiss();
                            }
                        }
                    }
                }else{
                    ivMobileDel.setVisibility(View.GONE);
                    if(phoneWindow==null){
                        showPhonePop(etMobile);
                    }else if(!phoneWindow.isShowing()){
                        phoneWindow.showAsDropDown(etMobile, 0, DipToPx.dip2px(AddAddressActivity.this, 10));
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etHouseNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivHouseNumDel.setVisibility(View.VISIBLE);
                } else {
                    ivHouseNumDel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPhoneOption.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivPhoneOptionDel.setVisibility(View.VISIBLE);
                } else {
                    ivPhoneOptionDel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_address_act_back:
                back();
                break;
            case R.id.add_address_act_tv_save:
                saveUserAddress();
                break;
            case R.id.add_address_act_delete://删除地址
                doDeleteAddress();
                break;
            case R.id.add_address_name_iv_delete:
                etName.setText("");
                break;
            case R.id.add_address_mobile_iv_delete:
                etMobile.setText("");
                break;
            case R.id.add_address_house_iv_delete:
                etHouseNum.setText("");
                break;
            case R.id.add_address_cb_sir:
                if(cbMale.isChecked()){
                    cbFemale.setChecked(false);
                }else{
                    cbMale.setChecked(true);
                }
                break;
            case R.id.add_address_cb_miss:
                if(cbFemale.isChecked()){
                    cbMale.setChecked(false);
                }else{
                    cbFemale.setChecked(true);
                }
                break;
            case R.id.location_address:
                Intent intent = new Intent(AddAddressActivity.this, SetAddressActivity.class);
                intent.putExtra("has_data", "1");
                startActivityForResult(intent, SET_POI_INFO_REQUEST);
                break;
            case R.id.add_address_phone_option_iv_add:
                llPhoneOption.setVisibility(View.VISIBLE);
                ivAddPhoneOption.setVisibility(View.GONE);
                break;
            case R.id.add_address_phone_option_iv_delete:
                etPhoneOption.setText("");
                break;
            default:
                break;
        }
    }

    private void doDeleteAddress() {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("id", userAddress.getId());
        VolleyOperater<DeleteAddressModel> operater = new VolleyOperater<>(AddAddressActivity.this);
        operater.doRequest(Constants.URL_DELETE_ADDRESS, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if(isSucceed && obj!=null){
                    DeleteAddressModel deleteAddressModel = (DeleteAddressModel) obj;
                    if(deleteAddressModel.getCode() == 0){
//                        setResult(AddressManagerListAdapter.EDITED_ADDRESS,new Intent());
                        finish();
                    }
                }
                loadingDialog.dismiss();
            }
        }, DeleteAddressModel.class);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.add_address_edt_name:
                if(hasFocus && etName.getText().toString().length()>0){
                    ivNameDel.setVisibility(View.VISIBLE);
                }else{
                    ivNameDel.setVisibility(View.GONE);
                }
                break;
            case R.id.add_address_edt_mobile:
                if(hasFocus && etMobile.getText().toString().length()>0){
                    ivMobileDel.setVisibility(View.VISIBLE);
                    ivMobileDel.setVisibility(View.VISIBLE);
                    if(App.getUserInfo().getMobile().startsWith(etMobile.getText().toString())&&etMobile.getText().toString().length()<11){
                        if(phoneWindow==null){
                            showPhonePop(etMobile);
                        }else if(!phoneWindow.isShowing()){
                            phoneWindow.showAsDropDown(etMobile, 0, DipToPx.dip2px(AddAddressActivity.this, 10));
                        }
                    }else{
                        if(phoneWindow!=null&&phoneWindow.isShowing()){
                            phoneWindow.dismiss();
                        }
                    }
                }else if(hasFocus){
                    ivMobileDel.setVisibility(View.GONE);
                    if(phoneWindow==null){
                        showPhonePop(etMobile);
                    }else if(!phoneWindow.isShowing()){
                        phoneWindow.showAsDropDown(etMobile, 0, DipToPx.dip2px(this, 10));
                    }
                }else{
                    ivMobileDel.setVisibility(View.GONE);
                    if(phoneWindow!=null&&phoneWindow.isShowing()){
                        phoneWindow.dismiss();
                    }
                }
                break;
            case R.id.add_address_edt_house:
                if(hasFocus && etHouseNum.getText().toString().length()>0){
                    ivHouseNumDel.setVisibility(View.VISIBLE);
                }else{
                    ivHouseNumDel.setVisibility(View.GONE);
                }
                break;
            case R.id.add_address_edt_phone_option:
                if(hasFocus && etPhoneOption.getText().toString().length()>0){
                    ivPhoneOptionDel.setVisibility(View.VISIBLE);
                }else{
                    ivPhoneOptionDel.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SET_POI_INFO_REQUEST) {
            if(resultCode == RESULT_OK) {
                BaiduGeocoderModel.ResultBean.PoisBean poiInfo = (BaiduGeocoderModel.ResultBean.PoisBean) data.getSerializableExtra(KEY_POI_INFO);
                tvLocation.setText(poiInfo.getName());
                etHouseNum.setText(poiInfo.getAddr());
                etHouseNum.requestFocus();
                Editable spannable = etHouseNum.getText();
                Selection.setSelection(spannable, spannable.length());
                longitude = poiInfo.getPoint().getX();
                latitude = poiInfo.getPoint().getY();
            }else if(resultCode == SetAddressActivity.SUGGESTION_RESULT){
                SuggestionResult.SuggestionInfo poiInfo = data.getParcelableExtra(KEY_POI_INFO);
                tvLocation.setText(poiInfo.city + poiInfo.district);
                etHouseNum.setText(poiInfo.key);
                etHouseNum.requestFocus();
                Editable spannable = etHouseNum.getText();
                Selection.setSelection(spannable, spannable.length());
                longitude = poiInfo.pt.longitude;
                latitude = poiInfo.pt.latitude;
            }
        }
    }

    private void saveUserAddress() {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        if(userAddress!=null)
            map.put("id", userAddress.getId());
        if(!TextUtils.isEmpty(etName.getText().toString())){
            map.put("name", etName.getText().toString());
        }else {
            ToastUtils.displayMsg(R.string.set_your_name, this);
            return;
        }
        if(StringUtils.isPhoneNumRight(etMobile.getText().toString())){
            map.put("mobile", etMobile.getText().toString());
        }else{
            ToastUtils.displayMsg(R.string.set_your_mobile, this);
            return;
        }
        if(!TextUtils.isEmpty(etPhoneOption.getText().toString())){
            map.put("backupMobile", etPhoneOption.getText().toString());
        }
        if(cbMale.isChecked()){
            map.put("gender", getString(R.string.sir));
        }else if(cbFemale.isChecked()){
            map.put("gender", getString(R.string.miss));
        }else{
            ToastUtils.displayMsg(R.string.choose_gender, this);
            return;
        }
        if(!TextUtils.isEmpty(tvLocation.getText())){
            map.put("address", tvLocation.getText());
        }else{
            ToastUtils.displayMsg(R.string.set_your_address, this);
            return;
        }
        if(!TextUtils.isEmpty(etHouseNum.getText().toString())){
            map.put("houseNumber", etHouseNum.getText().toString());
        }else{
            ToastUtils.displayMsg(R.string.set_your_house_number, this);
            return;
        }
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        if(merchantId != -1)
            map.put("merchantId", merchantId);
        VolleyOperater<EditAddressModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_EDIT_ADDRESS, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    ToastUtils.displayMsg(R.string.save_success, AddAddressActivity.this);
                    Log.e("UserAddress", ((EditAddressModel) obj).getValue().toString());
                    back();
                } else {
                    ToastUtils.displayMsg(R.string.save_fail, AddAddressActivity.this);
                }
                loadingDialog.dismiss();
            }
        }, EditAddressModel.class);
    }

    private void showPhonePop(View mView) {
        TextView phoneNumber = (TextView) getLayoutInflater().inflate(R.layout.mobile_textview, null);
        phoneNumber.setText(App.getUserInfo().getMobile());

        phoneWindow = new PopupWindow(phoneNumber, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        phoneWindow.setOutsideTouchable(true);
        phoneWindow.showAsDropDown(mView, 0, DipToPx.dip2px(this, 10));

        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneWindow.dismiss();
                etMobile.setText(App.getUserInfo().getMobile());
            }
        });
    }
}

package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.AreaBean;
import com.project.mgjandroid.bean.CityBean;
import com.project.mgjandroid.bean.HotCityBean;
import com.project.mgjandroid.bean.ProvinceBean;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ChooseCityModel;
import com.project.mgjandroid.model.PublishJobModel;
import com.project.mgjandroid.model.SendSmsModel;
import com.project.mgjandroid.model.SmsLoginModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.Map;

public class JobMessageActivity extends BaseActivity {
    public static final int GET_JOB_TYPE = 100;
    public static final int GET_JOB_TYPE_SUCCESS = 101;
    public static final int GET_JOB_AREA = 102;
    public static final int GET_JOB_AREA_SUCCESS = 103;
    public static final int GET_HOT_SUCCESS = 104;
    public static final int GET_CITY_CODE_SUCCESS = 105;
    public static final String FROM_JOB_MESSAGE = "job_message_activity";


    @InjectView(R.id.common_back) private ImageView ivBack;
    @InjectView(R.id.common_title) private TextView tvTitle;
    @InjectView(R.id.job_name) private EditText etName;
    @InjectView(R.id.job_type) private TextView etType;
    @InjectView(R.id.job_area) private TextView etArea;
    @InjectView(R.id.job_content) private EditText etContent;
    @InjectView(R.id.tv_content_length) private TextView tvContentLength;
    @InjectView(R.id.job_phone) private EditText etPhone;
    @InjectView(R.id.job_get_code) private TextView tvGetCode;
    @InjectView(R.id.job_code) private EditText etCode;
    @InjectView(R.id.job_publish) private TextView tvPublish;
    @InjectView(R.id.job_change) private TextView tvChange;
    @InjectView(R.id.job_get_code_line) private View vBottomLine;
    @InjectView(R.id.job_code_label) private LinearLayout llBottomLabel;
    @InjectView(R.id.job_choose_type) private RelativeLayout rlChooseType;
    @InjectView(R.id.job_choose_area) private RelativeLayout rlChooseArea;

    private long mPositionCategory = -1;
    private long mProvince = 0;
    private long mCity = 0;
    private long mDistrict = 0;

    private String mCityCode = "";
    private String mPhone;
    private String mPrePhone;
    private boolean timeTick = false;
    private int type;
    private int mCharAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_message);
        Injector.get(this).inject();
        type = ChooseCityModel.getInstance().getType();
        initView();
    }

    private void initView() {
        SmsLoginModel.ValueEntity.AppUserEntity userInfo = App.getInstance().getUserInfo();
        if(userInfo != null) {
            mPhone = userInfo.getMobile();
            changeGetCodeLabel(mPhone);
        }else{
            changeGetCodeLabel(null);
        }

        ivBack.setOnClickListener(this);
        if (type == 1) {
            tvTitle.setText(getString(R.string.full_time_publish));
            etContent.setHint("请填写个人简介(不少于50字)");
        } else if(type == 2) {
            tvTitle.setText(getString(R.string.full_time_publish_1));
            etContent.setHint("请填写职位描述(不少于50字)");
        }
        tvPublish.setOnClickListener(this);
        tvChange.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        rlChooseType.setOnClickListener(this);
        rlChooseArea.setOnClickListener(this);
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvContentLength.setText(s.toString().trim().length() + "/500字");
            }
        });

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!timeTick) {
                    tvGetCode.setEnabled(s.length() == 11);
                }
                if (!TextUtils.isEmpty(mPhone) && s.toString().equals(mPhone)) {
                    tvGetCode.setVisibility(View.GONE);
                    tvChange.setVisibility(View.VISIBLE);
                    llBottomLabel.setVisibility(View.GONE);
                } else {
                    tvGetCode.setVisibility(View.VISIBLE);
                    tvChange.setVisibility(View.GONE);
                    llBottomLabel.setVisibility(View.VISIBLE);
                }

                if (s.length() != 11 || !s.toString().equals(mPrePhone)) {
                    etCode.setText("");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.job_publish:
                if(checkCanPublish()) {
                    publishJob();
                }
                break;

            case R.id.job_change:
                changeGetCodeLabel("");
                break;

            case R.id.job_get_code:
                mPrePhone = etPhone.getText().toString().trim();
                if (!CheckUtils.isMobileNO(etPhone.getText().toString().trim())) {
                    ToastUtils.displayMsg("请输入手机号", this);
                    return;
                }
                getCheckCode();
                break;

            case R.id.job_choose_type:
                Intent intent = new Intent(JobMessageActivity.this,FulltimeJobActivity.class);
                intent.putExtra("from",FROM_JOB_MESSAGE);
                startActivityForResult(intent,GET_JOB_TYPE);
                break;

            case R.id.job_choose_area:
                Intent intent1 = new Intent(JobMessageActivity.this,ChooseCityActivity.class);
                intent1.putExtra("from",FROM_JOB_MESSAGE);
                startActivityForResult(intent1,GET_JOB_AREA);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null){
            switch (resultCode){
                case GET_JOB_TYPE_SUCCESS:
                    mPositionCategory = data.getLongExtra("positionCategory",-1);
                    String name = data.getStringExtra("name");
                    etType.setText(name);
                    break;

                case GET_JOB_AREA_SUCCESS:
                    ProvinceBean province = (ProvinceBean) data.getSerializableExtra(ChooseCityActivity.PROVINCE);
                    CityBean city = (CityBean) data.getSerializableExtra(ChooseCityActivity.CITY);
                    if(data.hasExtra(ChooseCityActivity.AREA)) {
                        AreaBean area = (AreaBean) data.getSerializableExtra(ChooseCityActivity.AREA);
                        mDistrict = area.getId();
                        etArea.setText(area.getName());
                    }else{
                        mDistrict = 0;
                        etArea.setText(city.getName());
                    }
                    mProvince = province.getId();
                    mCity = city.getId();
                    mCityCode = "";
                    break;

                case GET_HOT_SUCCESS:
                    HotCityBean hotCityBean = (HotCityBean) data.getSerializableExtra(ChooseCityActivity.HOT_CITY);
                    mProvince = hotCityBean.getProvince();
                    mCity = hotCityBean.getCity();
                    mDistrict = hotCityBean.getDistrict();
                    etArea.setText(hotCityBean.getCityName());
                    mCityCode = hotCityBean.getCityCode();
                    break;

                case GET_CITY_CODE_SUCCESS:
                    mCityCode = data.getStringExtra(ChooseCityActivity.CITY_CODE);
                    etArea.setText(data.getStringExtra(ChooseCityActivity.CITY_NAME));
                    break;
            }
        }
    }

    private boolean checkCanPublish() {
        if(TextUtils.isEmpty(etName.getText().toString().trim())){
            ToastUtils.displayMsg("请填写职位名称",mActivity);
            return false;
        }else{
            if(!CommonUtils.checkJobname(etName.getText().toString().trim())){
                ToastUtils.displayMsg("职位名称只能是汉字、字母或数字",mActivity);
                return false;
            }
        }
        if(mPositionCategory == -1){
            ToastUtils.displayMsg("请选择职位类别",mActivity);
            return false;
        }
        if(TextUtils.isEmpty(mCityCode) && (mProvince == 0 || mCity == 0)){
            ToastUtils.displayMsg("请选择地区",mActivity);
            return false;
        }
        if(TextUtils.isEmpty(etContent.getText().toString().trim())){
            if(type == 1){
                ToastUtils.displayMsg("请填写个人简介", mActivity);
            }else {
                ToastUtils.displayMsg("请填写职位描述", mActivity);
            }
            return false;
        }
        if(etContent.getText().toString().length() < 50){
            if(type == 1) {
                ToastUtils.displayMsg("个人简介不得少于50字", mActivity);
            }else{
                ToastUtils.displayMsg("职位描述不得少于50字", mActivity);
            }
            return false;
        }
        if(TextUtils.isEmpty(etPhone.getText().toString().trim())){
            ToastUtils.displayMsg("请填写联系电话",mActivity);
            return false;
        }
        if(llBottomLabel.getVisibility() == View.VISIBLE && TextUtils.isEmpty(etCode.getText().toString().trim())){
            ToastUtils.displayMsg("请填写验证码",mActivity);
            return false;
        }
        return true;
    }

    private void changeGetCodeLabel(String phone) {
        if(!TextUtils.isEmpty(phone)){
            etPhone.setEnabled(false);
            etPhone.setText(phone);
            tvGetCode.setVisibility(View.GONE);
            tvChange.setVisibility(View.VISIBLE);
            vBottomLine.setVisibility(View.GONE);
            llBottomLabel.setVisibility(View.GONE);
        }else {
            etPhone.setEnabled(true);
            etPhone.setText("");
            tvGetCode.setVisibility(View.VISIBLE);
            tvGetCode.setEnabled(false);
            tvChange.setVisibility(View.GONE);
            vBottomLine.setVisibility(View.VISIBLE);
            llBottomLabel.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 发布招聘信息
     */
    private void publishJob() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("name",etName.getText().toString().trim());
        map.put("positionCategoryId", mPositionCategory );
        map.put("mobile",etPhone.getText().toString().trim());
        map.put("description",etContent.getText().toString().trim());
        if(TextUtils.isEmpty(mCityCode)) {
            if(mProvince != 0) {
                map.put("province", mProvince);
            }
            if(mCity != 0) {
                map.put("city", mCity);
            }
            if(mDistrict != 0) {
                map.put("district", mDistrict);
            }
        }else{
            try {
                map.put("cityCode", Long.parseLong(mCityCode));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if(llBottomLabel.getVisibility() == View.VISIBLE) {
            map.put("code", etCode.getText().toString().trim());
        }
        VolleyOperater<PublishJobModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREATE_JOB, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if(obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    PublishJobModel publishJobModel = (PublishJobModel) obj;
                    if (publishJobModel.getCode() == 0) {
                        ChooseCityModel.getInstance().setHasChanged(true);
                        finish();
                    }
                }
            }
        }, PublishJobModel.class);
    }

    /**
     * 获取验证码
     */
    private void getCheckCode() {
        timeTick = true;
        etPhone.setEnabled(false);
        tvGetCode.setEnabled(false);
        Map<String, Object> map = new HashMap<>();
        map.put("mobile",etPhone.getText().toString().trim());
        VolleyOperater<SendSmsModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_GET_CODE_JOB, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    SendSmsModel sendSmsModel = (SendSmsModel) obj;
                    if(sendSmsModel.getCode() == 0){
//                        ToastUtils.displayMsg(getString(R.string.send_code_success), mActivity);
                        new SmsCountDown(60000, 1000).start();
                    }else{
                        tvGetCode.setEnabled(true);
                        ToastUtils.displayMsg(getString(R.string.send_code_failed),mActivity);
                    }
                } else {
                    tvGetCode.setEnabled(true);
                }
            }
        }, SendSmsModel.class);
    }

    private class SmsCountDown extends CountDownTimer {

        public SmsCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvGetCode.setText("重新获取(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            timeTick = false;
            etPhone.setEnabled(true);
            tvGetCode.setEnabled(true);
            tvGetCode.setText("重新获取");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CommonUtils.hideKeyBoard(mActivity);
        return super.onTouchEvent(event);
    }
}

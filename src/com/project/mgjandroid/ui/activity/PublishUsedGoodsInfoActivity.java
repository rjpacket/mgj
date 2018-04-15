package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.AreaBean;
import com.project.mgjandroid.bean.CityBean;
import com.project.mgjandroid.bean.HotCityBean;
import com.project.mgjandroid.bean.ProvinceBean;
import com.project.mgjandroid.chooseimage.ChoosePhotoModel;
import com.project.mgjandroid.chooseimage.UploadPhoto;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ChooseCityModel;
import com.project.mgjandroid.model.PublishSecondHandInfoModel;
import com.project.mgjandroid.model.SendSmsModel;
import com.project.mgjandroid.model.SmsLoginModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.view.materialdialog.MaterialDialog;
import com.project.mgjandroid.utils.BitmapUtil;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuandi on 2016/7/8.
 *
 */
public class PublishUsedGoodsInfoActivity extends BaseActivity {
    @InjectView(R.id.common_back) private ImageView ivBack;
    @InjectView(R.id.common_title) private TextView tvTitle;
    @InjectView(R.id.photo_layout) private RelativeLayout photoLayout;
    @InjectView(R.id.used_goods_img) private ImageView ivImage;
    @InjectView(R.id.used_goods_photo) private ImageView ivPhoto;
    @InjectView(R.id.used_goods_photo_2) private ImageView ivPhoto2;
    @InjectView(R.id.tv_take_photo) private TextView tvPhoto;
    @InjectView(R.id.used_goods_name) private EditText etName;
    @InjectView(R.id.used_goods_type) private TextView etType;
    @InjectView(R.id.tv_price) private TextView tvPrice;
    @InjectView(R.id.used_goods_price) private TextView etPrice;
    @InjectView(R.id.goods_price_layout) private LinearLayout priceLayout;
    @InjectView(R.id.used_goods_price_max) private EditText etPriceMax;
    @InjectView(R.id.used_goods_area) private TextView etArea;
    @InjectView(R.id.used_goods_content) private EditText etContent;
    @InjectView(R.id.tv_content_length) private TextView tvContentLength;
    @InjectView(R.id.used_goods_phone) private EditText etPhone;
    @InjectView(R.id.used_goods_get_code) private TextView tvGetCode;
    @InjectView(R.id.used_goods_code) private EditText etCode;
    @InjectView(R.id.used_goods_publish) private TextView tvPublish;
    @InjectView(R.id.used_goods_change) private TextView tvChange;
    @InjectView(R.id.used_goods_get_code_line) private View vBottomLine;
    @InjectView(R.id.used_goods_code_label) private LinearLayout llBottomLabel;
    @InjectView(R.id.used_goods_choose_type) private RelativeLayout rlChooseType;
    @InjectView(R.id.used_goods_choose_area) private RelativeLayout rlChooseArea;

    private long mPositionCategory = -1;
    private long mProvince = 0;
    private long mCity = 0;
    private long mDistrict = 0;

    private String mCityCode = "";
    private String mPhone;
    private String mPrePhone;
    private boolean timeTick = false;

    private MaterialDialog mMaterialDialog;
    private CustomDialog mDialog;
    private PopupWindow mPopupWindow;
    private ArrayList<UploadPhoto> mSelectFiles = new ArrayList<>();
    private StringBuffer imageUrls = new StringBuffer();
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_used_goods_info);
        Injector.get(this).inject();
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

        type = ChooseCityModel.getInstance().getSecondHandType();
        if(type == 1){
            tvTitle.setText("发布求购信息");
            photoLayout.setVisibility(View.GONE);
            priceLayout.setVisibility(View.VISIBLE);
            tvPrice.setText("价格区间");
        }else{
            tvTitle.setText("发布二手信息");
            ChoosePhotoModel.getInstance().setMaxCount(12);
            ChoosePhotoModel.getInstance().setCurrentActivity(this.getClass());
        }

        ivImage.setOnClickListener(this);
        ivPhoto.setOnClickListener(this);
        ivPhoto2.setOnClickListener(this);
        tvPhoto.setOnClickListener(this);
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

        etPrice.addTextChangedListener(new TextWatcher() {
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
                    if(!temp.equals(".")&& new BigDecimal(Double.parseDouble(temp)).compareTo(new BigDecimal(1000000000)) >= 0){
                        s.delete(len - 1, len);
                    }
                    if(len > 1 && temp.charAt(0) == "0".charAt(0) && temp.charAt(1) != ".".charAt(0)) {
                        s.delete(0, 1);
                    }
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

        etPriceMax.addTextChangedListener(new TextWatcher() {
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
                    if(!temp.equals(".")&& new BigDecimal(Double.parseDouble(temp)).compareTo(new BigDecimal(1000000000)) >= 0){
                        s.delete(len - 1, len);
                    }
                    if(len > 1 && temp.charAt(0) == "0".charAt(0) && temp.charAt(1) != ".".charAt(0)) {
                        s.delete(0, 1);
                    }
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
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.used_goods_img:
                CommonUtils.hideKeyBoard(mActivity);
                break;
            case R.id.used_goods_photo_2:
                Intent dealPhoto = new Intent(PublishUsedGoodsInfoActivity.this, UploadPhotoActivity.class);
                dealPhoto.putExtra("from", mActivity.getClass().toString());
                startActivity(dealPhoto);
                break;

            case R.id.used_goods_photo:
            case R.id.tv_take_photo:
                CommonUtils.hideKeyBoard(mActivity);
                showPopupWindow();
                break;

            case R.id.used_goods_publish:
                CommonUtils.hideKeyBoard(mActivity);
                if(checkCanPublish()) {
                    publish();
                }
                break;

            case R.id.used_goods_change:
                changeGetCodeLabel("");
                break;

            case R.id.used_goods_get_code:
                mPrePhone = etPhone.getText().toString().trim();
                if (!CheckUtils.isMobileNO(etPhone.getText().toString().trim())) {
                    ToastUtils.displayMsg("请输入手机号", this);
                    return;
                }
                getCheckCode();
                break;

            case R.id.used_goods_choose_type:
                Intent intent = new Intent(PublishUsedGoodsInfoActivity.this, SecondHandCategoryActivity.class);
                intent.putExtra("from", JobMessageActivity.FROM_JOB_MESSAGE);
                startActivityForResult(intent, JobMessageActivity.GET_JOB_TYPE);
                break;

            case R.id.used_goods_choose_area:
                Intent intent1 = new Intent(PublishUsedGoodsInfoActivity.this, ChooseCityActivity.class);
                intent1.putExtra("from", JobMessageActivity.FROM_JOB_MESSAGE);
                startActivityForResult(intent1, JobMessageActivity.GET_JOB_AREA);
                break;

            case R.id.take_photo:
                if(!CheckUtils.hasCamera(PublishUsedGoodsInfoActivity.this)) {
                    toast("您的手机上没有检测到相机");
                    return;
                }

                Intent camera = new Intent(PublishUsedGoodsInfoActivity.this, CameraActivity.class);
                ChoosePhotoModel.getInstance().setFrom(mActivity.getClass().getName());
                startActivity(camera);
                dismissPopupWindow();
                break;
            case R.id.select_photo:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + ActivitySchemeManager.URL_GET_IMAGE_LIST);
                ChoosePhotoModel.getInstance().setFrom(mActivity.getClass().getName());
                dismissPopupWindow();
                break;
            case R.id.feed_back_cancel:
                dismissPopupWindow();
                break;
        }
    }

    private void showUploadDialog(){
        if(mMaterialDialog==null){
            mMaterialDialog = new MaterialDialog(mActivity);
            mMaterialDialog.setCanceledOnTouchOutside(true);
        }
        mMaterialDialog.setMessage("正在提交...");
        mMaterialDialog.show();
    }

    private void showPopupWindow() {
        if(mPopupWindow == null){
            initPopupWindow();
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }else if(!mPopupWindow.isShowing()){
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    private void initPopupWindow() {
        LinearLayout linearLayout = (LinearLayout) mInflater.inflate(R.layout.layout_select_photo,null);
        TextView tvTakePhoto = (TextView) linearLayout.findViewById(R.id.take_photo);
        TextView tvSelectPhoto = (TextView) linearLayout.findViewById(R.id.select_photo);
        TextView tvCancel = (TextView) linearLayout.findViewById(R.id.feed_back_cancel);
        tvTakePhoto.setOnClickListener(this);
        tvSelectPhoto.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        mPopupWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(false);
    }

    private void dismissPopupWindow() {
        if(mPopupWindow != null && mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(type == 2){
            mSelectFiles = ChoosePhotoModel.getInstance().getmUploadPhotoList();
            if(mSelectFiles.size() > 0) {
                Bitmap bitmap = BitmapUtil.compressBitmap(mSelectFiles.get(0).getPath(), 1280);
                ivImage.setImageBitmap(bitmap);
                ivPhoto.setVisibility(View.GONE);
                ivPhoto2.setVisibility(View.VISIBLE);
                tvPhoto.setVisibility(View.GONE);
            }else{
                ivImage.setImageResource(R.drawable.photo_default);
                ivPhoto.setVisibility(View.VISIBLE);
                ivPhoto2.setVisibility(View.GONE);
                tvPhoto.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        ChoosePhotoModel.getInstance().clear();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null){
            switch (resultCode){
                case JobMessageActivity.GET_JOB_TYPE_SUCCESS:
                    mPositionCategory = data.getLongExtra("positionCategory",-1);
                    String name = data.getStringExtra("name");
                    etType.setText(name);
                    break;

                case JobMessageActivity.GET_JOB_AREA_SUCCESS:
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

                case JobMessageActivity.GET_HOT_SUCCESS:
                    HotCityBean hotCityBean = (HotCityBean) data.getSerializableExtra(ChooseCityActivity.HOT_CITY);
                    mProvince = hotCityBean.getProvince();
                    mCity = hotCityBean.getCity();
                    mDistrict = hotCityBean.getDistrict();
                    etArea.setText(hotCityBean.getCityName());
                    mCityCode = hotCityBean.getCityCode();
                    break;

                case JobMessageActivity.GET_CITY_CODE_SUCCESS:
                    mCityCode = data.getStringExtra(ChooseCityActivity.CITY_CODE);
                    etArea.setText(data.getStringExtra(ChooseCityActivity.CITY_NAME));
                    break;
            }
        }
    }

    private boolean checkCanPublish() {
        if(TextUtils.isEmpty(etName.getText().toString().trim())){
            ToastUtils.displayMsg("请填写标题",mActivity);
            return false;
        }
//        else if(!CommonUtils.checkJobname(etName.getText().toString().trim())){
//            ToastUtils.displayMsg("标题只能是汉字、字母或数字",mActivity);
//            return false;
//        }
        if(mPositionCategory == -1){
            ToastUtils.displayMsg("请选择宝贝类别",mActivity);
            return false;
        }
        if(type == 2 && TextUtils.isEmpty(etPrice.getText().toString().trim())){
            ToastUtils.displayMsg("请填写价格",mActivity);
            return false;
        } else if(type == 1 && (TextUtils.isEmpty(etPrice.getText().toString().trim()) || TextUtils.isEmpty(etPriceMax.getText().toString().trim()))) {
            ToastUtils.displayMsg("请填写价格区间",mActivity);
            return false;
        }
        if(TextUtils.isEmpty(mCityCode) && (mProvince == 0 || mCity == 0)){
            ToastUtils.displayMsg("请选择发布区域",mActivity);
            return false;
        }
        if(TextUtils.isEmpty(etContent.getText().toString().trim())){
            ToastUtils.displayMsg("请填写详细信息", mActivity);
            return false;
        }
        if(etContent.getText().toString().length() < 50){
            ToastUtils.displayMsg("详细信息不得少于50字", mActivity);
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
//        if(type == 2 && TextUtils.isEmpty(imageUrls.toString())){
//            ToastUtils.displayMsg("请选择照片",mActivity);
//            return false;
//        }else
        if(type == 2){
            imageUrls.setLength(0);
            for(int i = 0, size = mSelectFiles.size(); i<size; i++){
                if(TextUtils.isEmpty(mSelectFiles.get(i).getUrl())) continue;
                if(i == size-1) {
                    imageUrls.append(mSelectFiles.get(i).getUrl());
                }else{
                    imageUrls.append(mSelectFiles.get(i).getUrl() +  ";");
                }
            }
            if(TextUtils.isEmpty(imageUrls.toString())){
                ToastUtils.displayMsg("请选择照片",mActivity);
                return false;
            }
            String[] urls = imageUrls.toString().split(";");
            if(urls.length == 0) {
                toast("您选取的照片还没有上传，快去上传吧！");
                return false;
            }
            if(urls.length < mSelectFiles.size()){
                showMyDialog();
                return false;
            }
        }
        return true;
    }

    private void showMyDialog() {
        if (mDialog == null){
            mDialog = new CustomDialog(mActivity, new CustomDialog.onBtnClickListener() {
                @Override
                public void onSure() {
                    Intent dealPhoto = new Intent(PublishUsedGoodsInfoActivity.this, UploadPhotoActivity.class);
                    startActivity(dealPhoto);
                    mDialog.dismiss();
                }

                @Override
                public void onExit() {
                    publish();
                    mDialog.dismiss();
                }

            }, "去上传", "仍要发布", "", "您选取的照片还有没上传的哦~~");
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.show();
        }else {
            mDialog.show();
        }
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
            etPhone.requestFocus();
            tvGetCode.setVisibility(View.VISIBLE);
            tvGetCode.setEnabled(false);
            tvChange.setVisibility(View.GONE);
            vBottomLine.setVisibility(View.VISIBLE);
            llBottomLabel.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 发布信息
     */
    private void publish() {
        showUploadDialog();
        Map<String, Object> map = new HashMap<>();
        map.put("title",etName.getText().toString().trim());
        map.put("secondhandCategoryId", mPositionCategory );
        map.put("mobile",etPhone.getText().toString().trim());
        map.put("description",etContent.getText().toString().trim());
        map.put("type", type);
        if(type == 1){
            if(new BigDecimal(Double.parseDouble(etPrice.getText().toString())).compareTo(new BigDecimal(Double.parseDouble(etPriceMax.getText().toString()))) <= 0){
                map.put("minAmt", etPrice.getText().toString());
                map.put("maxAmt", etPriceMax.getText().toString());
            } else {
                map.put("minAmt", etPriceMax.getText().toString());
                map.put("maxAmt", etPrice.getText().toString());
            }
        }else{
            map.put("amt", etPrice.getText().toString());
        }
        map.put("imgs", imageUrls.toString());
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
        VolleyOperater<PublishSecondHandInfoModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREATE_SECOND_HAND_INFOMATION, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMaterialDialog.dismiss();
                if (isSucceed && obj != null) {
                    if(obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    PublishSecondHandInfoModel publishModel = (PublishSecondHandInfoModel) obj;
                    if (publishModel.getCode() == 0) {
                        ChooseCityModel.getInstance().setHasChanged(true);
                        finish();
                    }
                }
            }
        }, PublishSecondHandInfoModel.class);
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
        operater.doRequest(Constants.URL_SEND_SMS_SECOND_HAND, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    SendSmsModel sendSmsModel = (SendSmsModel) obj;
                    if(sendSmsModel.getCode() == 0){
//                        ToastUtils.displayMsg(getString(R.string.send_code_success), mActivity);
                        new SmsCountDown(60000, 1000).start();
                    }else{
                        tvGetCode.setEnabled(true);
                        ToastUtils.displayMsg(getString(R.string.send_code_failed), mActivity);
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
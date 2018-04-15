package com.project.mgjandroid.ui.activity.homemaking;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
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

import com.github.mzule.activityrouter.annotation.Router;
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
import com.project.mgjandroid.model.GetQiNiuTokenModel;
import com.project.mgjandroid.model.HomemakingInformationDetailModel;
import com.project.mgjandroid.model.SendSmsModel;
import com.project.mgjandroid.model.SmsLoginModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.CameraActivity;
import com.project.mgjandroid.ui.activity.ChooseCityActivity;
import com.project.mgjandroid.ui.activity.JobMessageActivity;
import com.project.mgjandroid.ui.activity.UploadPhotoActivity;
import com.project.mgjandroid.ui.activity.homemaking.HomemakingCategoryActivity;
import com.project.mgjandroid.ui.activity.homemaking.HomemakingTypeActivity;
import com.project.mgjandroid.ui.view.materialdialog.MaterialDialog;
import com.project.mgjandroid.utils.BitmapUtil;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.FileUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.StreamUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuandi on 2016/7/23.
 *
 */

public class PublishHomeMakingInfoActivity extends BaseActivity {

    private static final int REQUEST_TAKE_PICTURE = 1001;
    private static final int REQUEST_PICK_PICTURE = 1002;
    private static final int REQUEST_CHOOSE_TYPE = 1003;

    @InjectView(R.id.common_back) private ImageView ivBack;
    @InjectView(R.id.common_title) private TextView tvTitle;
    @InjectView(R.id.home_making_img) private ImageView ivImage;
    @InjectView(R.id.home_making_photo) private ImageView ivPhoto;
    @InjectView(R.id.home_making_photo_2) private ImageView ivPhoto2;
    @InjectView(R.id.tv_take_photo) private TextView tvPhoto;
    @InjectView(R.id.home_making_title) private EditText etTitle;
    @InjectView(R.id.home_making_category) private TextView tvCategory;
    @InjectView(R.id.home_making_type) private TextView tvType;
    @InjectView(R.id.home_making_feature) private EditText etFeature;
    @InjectView(R.id.home_making_location) private EditText etLocation;
    @InjectView(R.id.home_making_area) private TextView etArea;
    @InjectView(R.id.home_making_content) private EditText etContent;
    @InjectView(R.id.tv_content_length) private TextView tvContentLength;
    @InjectView(R.id.home_making_phone) private EditText etPhone;
    @InjectView(R.id.home_making_get_code) private TextView tvGetCode;
    @InjectView(R.id.home_making_code) private EditText etCode;
    @InjectView(R.id.home_making_publish) private TextView tvPublish;
    @InjectView(R.id.home_making_change) private TextView tvChange;
    @InjectView(R.id.home_making_get_code_line) private View vBottomLine;
    @InjectView(R.id.home_making_code_label) private LinearLayout llBottomLabel;
    @InjectView(R.id.home_making_choose_category) private RelativeLayout rlChooseCategory;
    @InjectView(R.id.home_making_choose_area) private RelativeLayout rlChooseArea;
    @InjectView(R.id.home_making_choose_type) private RelativeLayout rlChooseType;

    private long mPositionCategory = -1;
    private long mProvince = 0;
    private long mCity = 0;
    private long mDistrict = 0;

    private String mCityCode = "";
    private String mPhone;
    private String mPrePhone;
    private boolean timeTick = false;

    private MaterialDialog mMaterialDialog;
    private PopupWindow mPopupWindow;
    private int type = -1;
    private File file;
    private String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_home_making);
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

        tvTitle.setText("发布家政信息");

        ivImage.setOnClickListener(this);
        ivPhoto.setOnClickListener(this);
        ivPhoto2.setOnClickListener(this);
        tvPhoto.setOnClickListener(this);
        tvPublish.setOnClickListener(this);
        tvChange.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        rlChooseType.setOnClickListener(this);
        rlChooseCategory.setOnClickListener(this);
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
            case R.id.home_making_img:
                CommonUtils.hideKeyBoard(mActivity);
                break;
            case R.id.home_making_photo_2:
            case R.id.home_making_photo:
            case R.id.tv_take_photo:
                CommonUtils.hideKeyBoard(mActivity);
                showPopupWindow();
                break;

            case R.id.home_making_publish:
                CommonUtils.hideKeyBoard(mActivity);
                if(checkCanPublish()) {
                    getQiniuUploadToken();
                }
                break;

            case R.id.home_making_change:
                changeGetCodeLabel("");
                break;

            case R.id.home_making_get_code:
                mPrePhone = etPhone.getText().toString().trim();
                if (!CheckUtils.isMobileNO(etPhone.getText().toString().trim())) {
                    ToastUtils.displayMsg("请输入手机号", this);
                    return;
                }
                getCheckCode();
                break;

            case R.id.home_making_choose_type:
                Intent type = new Intent(mActivity, HomemakingTypeActivity.class);
                startActivityForResult(type, REQUEST_CHOOSE_TYPE);
                break;

            case R.id.home_making_choose_category:
                Intent intent = new Intent(mActivity, HomemakingCategoryActivity.class);
                intent.putExtra("from", JobMessageActivity.FROM_JOB_MESSAGE);
                startActivityForResult(intent, JobMessageActivity.GET_JOB_TYPE);
                break;

            case R.id.home_making_choose_area:
                Intent intent1 = new Intent(mActivity, ChooseCityActivity.class);
                intent1.putExtra("from", JobMessageActivity.FROM_JOB_MESSAGE);
                startActivityForResult(intent1, JobMessageActivity.GET_JOB_AREA);
                break;

            case R.id.take_photo:
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                FileUtils.createDirFile(Constants.IMG_PATH);
                photoPath = Constants.IMG_PATH + CommonUtils.generateUUID() + ".jpg";
                file = FileUtils.createNewFile(photoPath);
                if (file == null){
                    photoPath = "";
                    return;
                }
                Uri cameraUri = Uri.fromFile(file);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                try {
                    startActivityForResult(openCameraIntent, REQUEST_TAKE_PICTURE);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                dismissPopupWindow();
                break;
            case R.id.select_photo:
                Intent pick = new Intent(Intent.ACTION_PICK, null);
                pick.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(pick, REQUEST_PICK_PICTURE);
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

    private void showPhoto(String path){
        if(CheckUtils.isNoEmptyStr(path)) {
            Bitmap bitmap = BitmapUtil.compressBitmap(path, 1280);
            ivImage.setImageBitmap(bitmap);
            ivPhoto.setVisibility(View.GONE);
            ivPhoto2.setVisibility(View.VISIBLE);
            tvPhoto.setVisibility(View.GONE);
        }else{
            ivImage.setImageResource(R.drawable.home_making_default);
            ivPhoto.setVisibility(View.VISIBLE);
            ivPhoto2.setVisibility(View.GONE);
            tvPhoto.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_TAKE_PICTURE){
            if(resultCode == RESULT_OK){
                showPhoto(photoPath);
            }else{
                photoPath = "";
            }
            return;
        }
        if(data != null){
            if(requestCode == REQUEST_CHOOSE_TYPE && resultCode == RESULT_OK) {
                type = data.getIntExtra(HomemakingTypeActivity.TYPE_ID, -1);
                tvType.setText(data.getStringExtra(HomemakingTypeActivity.TYPE_NAME));
                return;
            }
            if(requestCode == REQUEST_PICK_PICTURE && resultCode == RESULT_OK){
                Uri uri = data.getData();
                photoPath = FileUtils.getRealFilePath(mActivity, uri);
                showPhoto(photoPath);
                return;
            }
            switch (resultCode){
                case JobMessageActivity.GET_JOB_TYPE_SUCCESS:
                    mPositionCategory = data.getLongExtra("positionCategory",-1);
                    String name = data.getStringExtra("name");
                    tvCategory.setText(name);
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
        if(CheckUtils.isEmptyStr(photoPath)){
            ToastUtils.displayMsg("请选择照片",mActivity);
            return false;
        }
        if(TextUtils.isEmpty(etTitle.getText().toString().trim())){
            ToastUtils.displayMsg("请填写标题",mActivity);
            return false;
        }
        if(etTitle.getText().toString().trim().length() < 6){
            ToastUtils.displayMsg("标题不得少于6个字",mActivity);
            return false;
        }
        if(mPositionCategory == -1){
            ToastUtils.displayMsg("请选择服务类别",mActivity);
            return false;
        }
        if(TextUtils.isEmpty(etFeature.getText().toString().trim())){
            ToastUtils.displayMsg("请填写服务特色",mActivity);
            return false;
        }
        if(etFeature.getText().toString().trim().length() < 6){
            ToastUtils.displayMsg("服务特色不得少于6个字",mActivity);
            return false;
        }
        if(TextUtils.isEmpty(etLocation.getText().toString().trim())){
            ToastUtils.displayMsg("请填写服务区域",mActivity);
            return false;
        }
        if(etLocation.getText().toString().trim().length() < 2){
            ToastUtils.displayMsg("服务区域不得少于2个字",mActivity);
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
        if(type == -1){
            ToastUtils.displayMsg("请选择身份",mActivity);
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
        Map<String, Object> map = new HashMap<>();
        map.put("title",etTitle.getText().toString().trim());
        map.put("homemakingCategoryId", mPositionCategory);
        map.put("mobile",etPhone.getText().toString().trim());
        map.put("description",etContent.getText().toString().trim());
        map.put("serviceArea",etLocation.getText().toString().trim());
        map.put("serviceFeature", etFeature.getText().toString().trim());
        map.put("type", type);
        map.put("imgs", photoPath);
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
        VolleyOperater<HomemakingInformationDetailModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREATE_HOME_MAKING_MESSAGE, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMaterialDialog.dismiss();
                if (isSucceed && obj != null) {
                    if(obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    HomemakingInformationDetailModel publishModel = (HomemakingInformationDetailModel) obj;
                    if (publishModel.getCode() == 0) {
                        ChooseCityModel.getInstance().setHasChanged(true);
                        finish();
                    }
                }
            }
        }, HomemakingInformationDetailModel.class);
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
        operater.doRequest(Constants.URL_SEND_CODE_HOME_MAKING_DETAIL, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if(obj instanceof String){
                        ToastUtils.displayMsg((String)obj, mActivity);
                        return;
                    }

                    SendSmsModel sendSmsModel = (SendSmsModel) obj;
                    if(sendSmsModel.getCode() == 0){
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

    protected void getQiniuUploadToken() {
        showUploadDialog();
        VolleyOperater<GetQiNiuTokenModel> operater = new VolleyOperater<GetQiNiuTokenModel>(this);
        operater.doRequest(Constants.URL_HOME_MAKING_OBTAIN_QINIU_TOKEN, null, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    GetQiNiuTokenModel getQiNiuTokenModel = (GetQiNiuTokenModel) obj;
                    uploadPicture(getQiNiuTokenModel.getValue().getToken(),getQiNiuTokenModel.getValue().getPath());
                }else {
                    mMaterialDialog.dismiss();
                }
            }
        }, GetQiNiuTokenModel.class);
    }

    protected void uploadPicture(String token, String path) {
        UploadOptions uploadOptions = new UploadOptions(null, null, false, new UpProgressHandler() {
            public void progress(String key, double percent) {
                MLog.i("qiniu:" + key + ": " + percent);
            }
        }, null);
        startUpload(token, photoPath, uploadOptions,path);
    }

    protected void startUpload(String token, final String picturePath, UploadOptions uploadOptions, final String path) {
        if (BitmapUtil.needCompressBitmap(picturePath, 800)) {
            new UploadManager().put(StreamUtils.Bitmap2Bytes(BitmapUtil.compressBitmap(picturePath, 800)), getImgUUID(picturePath), token, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject response) {
                    if(info.isOK()){
                        photoPath = path + key;
                        publish();
                        MLog.i("qiniu:" + info.toString());
                    }else{
                        toast("图片上传失败");
                        mMaterialDialog.dismiss();
                    }
                }
            }, uploadOptions);
        }else{
            new UploadManager().put(picturePath, getImgUUID(picturePath), token, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject response) {
                    if(info.isOK()){
                        photoPath = path + key;
                        publish();
                        MLog.i("qiniu:" + info.toString());
                    }else{
                        toast("图片上传失败");
                    }
                }
            }, uploadOptions);
        }
    }

    private String getImgUUID(String picturePath) {
        String picType;
        String substring = picturePath.substring(picturePath.lastIndexOf("."));
        if (!TextUtils.isEmpty(substring)) {
            picType = substring;
        } else {
            picType = ".jpg";
        }
        return CommonUtils.generateImgID() + picType;
    }
}

package com.project.mgjandroid.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.BBSCategory;
import com.project.mgjandroid.bean.BBSInformation;
import com.project.mgjandroid.chooseimage.ChoosePhotoModel;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.BaiduGeocoderModel;
import com.project.mgjandroid.model.BbsCategoryListModel;
import com.project.mgjandroid.model.GetQiNiuTokenModel;
import com.project.mgjandroid.model.SendSmsModel;
import com.project.mgjandroid.model.SmsLoginModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.FeedbackGridAdapter;
import com.project.mgjandroid.ui.view.FlowLayout;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.ui.view.materialdialog.MaterialDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.FileUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PublishInfomationActivity extends BaseActivity{
    private static final int MY_TAKE_PICTURE = 1001;
    private static final int MY_PICK_PICTURE = 1002;
    private static final int MY_CROP_PICTURE = 1003;
    private static final int MY_TAKE_PICTURE_2 = 1004;
    private static final int SET_POI_INFO_REQUEST = 1005;

    @InjectView(R.id.root_view)
    private LinearLayout rootView;
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.tv_publish)
    private TextView tvPublish;
    @InjectView(R.id.publish_act_et_name)
    private EditText etName;
    @InjectView(R.id.publish_act_tv_male)
    private TextView tvMale;
    @InjectView(R.id.publish_act_tv_female)
    private TextView tvFemale;
    @InjectView(R.id.publish_act_civ_avatar)
    private RoundImageView ivAvatar;
    @InjectView(R.id.publish_act_et_phone)
    private EditText etPhone;
    @InjectView(R.id.publish_act_tv_send_sms)
    private TextView tvSendSms;
    @InjectView(R.id.sms_code_layout)
    private LinearLayout smsCodeLayout;
    @InjectView(R.id.publish_act_et_pwd)
    private EditText etPwd;
    @InjectView(R.id.publish_act_tv_tags)
    private TextView tvTags;
    @InjectView(R.id.publish_act_flow_layout_tags)
    private FlowLayout flowLayoutTags;
    @InjectView(R.id.publish_act_et_content)
    private EditText etContent;
    @InjectView(R.id.publish_act_tv_content_length)
    private TextView tvContentLength;
    @InjectView(R.id.publish_act_iv_del_content)
    private ImageView ivDelContent;
    @InjectView(R.id.publish_act_tv_address)
    private TextView tvAddress;
    @InjectView(R.id.publish_act_gv_photo)
    private NoScrollGridView gvPhoto;

    private Dialog avatarDialog;
    private File file;
    private File mFile;
    private UploadManager uploadManager;
    private UploadOptions uploadOptions;
    private Bitmap bitmap;
    private String avatarUrl;
    private double longitude;
    private double latitude;
    private int gender = 1;
    private ArrayList<String> mSelectFiles = new ArrayList<>();
    private ArrayList<Long> categoryList = new ArrayList<>();
    private ArrayList<String> mobiles = new ArrayList<>();
    private PopupWindow mPopupWindow;
    private FeedbackGridAdapter mGridAdapter;
    private int mPosition = 0;
    private StringBuffer imageUrls = new StringBuffer();
    private MaterialDialog mMaterialDialog;

    private BBSInformation bbsInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_infomation);
        Injector.get(this).inject();
        initView();
        initData();
    }

    private void initView() {
        rootView.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvPublish.setVisibility(View.VISIBLE);
        tvPublish.setOnClickListener(this);
        tvTitle.setText("发布信息");

        tvTags.setSelected(false);
        tvMale.setSelected(true);
        tvMale.setOnClickListener(this);
        tvFemale.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        tvTags.setOnClickListener(this);
        tvSendSms.setOnClickListener(this);
        ivDelContent.setOnClickListener(this);
        tvAddress.setOnClickListener(this);

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    ivDelContent.setVisibility(View.VISIBLE);
                } else {
                    ivDelContent.setVisibility(View.GONE);
                }
                tvContentLength.setText(s.length() + "/500");
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
                if (s.length() == 11) {
                    if(!mobiles.contains(s.toString().trim())){
                        smsCodeLayout.setVisibility(View.VISIBLE);
                        tvSendSms.setVisibility(View.VISIBLE);
                    }else{
                        smsCodeLayout.setVisibility(View.GONE);
                        tvSendSms.setVisibility(View.GONE);
                    }
                }
            }
        });

        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 6){
                    checkSMSCode();
                }
            }
        });
    }

    private void initData() {
        if (App.isLogin()) {
            SmsLoginModel.ValueEntity.AppUserEntity userInfo = App.getUserInfo();
            String name = userInfo.getName();
            String mobile = userInfo.getMobile();
//            if (!TextUtils.isEmpty(name)) {
//                etName.setText(name);
//            }
            if (!TextUtils.isEmpty(mobile)){
                etPhone.setText(mobile);
                smsCodeLayout.setVisibility(View.GONE);
                tvSendSms.setVisibility(View.GONE);
                mobiles.add(mobile);
            }
            String headerImg = userInfo.getHeaderImg();
            if (!TextUtils.isEmpty(headerImg)) {
                avatarUrl = headerImg;
                ImageUtils.loadBitmap(mActivity, headerImg + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER, ivAvatar, R.drawable.icon, "");
            }
        }
        ChoosePhotoModel.getInstance().setCurrentActivity(this.getClass());
        ChoosePhotoModel.getInstance().setMaxCount(8);
        mGridAdapter = new FeedbackGridAdapter(mActivity, mSelectFiles, 8);
        gvPhoto.setAdapter(mGridAdapter);
        gvPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mSelectFiles.size()) {
                    showPopupWindow();
                } else {
                    skipToPhotoActivity(position);
                }
            }
        });

        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)
                .putThreshhold(512 * 1024)
                .connectTimeout(10)
                .responseTimeout(60)
                .build();
        uploadManager = new UploadManager(config);
        uploadOptions = new UploadOptions(null, null, false, new UpProgressHandler() {
            public void progress(String key, double percent) {
                MLog.i("qiniu:" + key + ": " + percent);
            }
        }, null);

        getBBSCategories();

        String lat = PreferenceUtils.getLocation(mActivity)[0];
        String lng = PreferenceUtils.getLocation(mActivity)[1];
        try {
            latitude = Double.parseDouble(lat);
            longitude = Double.parseDouble(lng);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String address = PreferenceUtils.getAddressName(mActivity);
        if (CheckUtils.isNoEmptyStr(address)) {
            tvAddress.setText(address);
        }
    }

    private void skipToPhotoActivity(int position) {
        Routers.open(mActivity, ActivitySchemeManager.SCHEME + ActivitySchemeManager.URL_PHOTO_VIEW + "/" + position);
    }

    /**
     * 显示选择框
     */
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.root_view:
                CommonUtils.hideKeyBoard(mActivity);
                break;
            case R.id.common_back:
                onBackPressed();
                break;
            case R.id.tv_publish:
                CommonUtils.hideKeyBoard(mActivity);
                publishInfo();
                break;
            case R.id.publish_act_tv_male:
                CommonUtils.hideKeyBoard(mActivity);
                tvMale.setSelected(true);
                tvFemale.setSelected(false);
                gender = 1;
                break;
            case R.id.publish_act_tv_female:
                CommonUtils.hideKeyBoard(mActivity);
                tvMale.setSelected(false);
                tvFemale.setSelected(true);
                gender = 0;
                break;
            case R.id.publish_act_civ_avatar:
                CommonUtils.hideKeyBoard(mActivity);
                initAvatarDialog();
                break;
            case R.id.publish_act_tv_send_sms:
                CommonUtils.hideKeyBoard(mActivity);
                if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
                    ToastUtils.displayMsg("手机号码为空", this);
                    return;
                }
                if (!CheckUtils.isMobileNO(etPhone.getText().toString().trim())) {
                    ToastUtils.displayMsg("手机号码格式不正确", this);
                    return;
                }
                getSMSCode();
                break;
            case R.id.publish_act_tv_tags:
                CommonUtils.hideKeyBoard(mActivity);
                if(tvTags.isSelected()){
                    tvTags.setSelected(false);
                    tvTags.setText("添加推荐标签");
                    flowLayoutTags.setVisibility(View.GONE);
                }else{
                    tvTags.setSelected(true);
                    tvTags.setText("收起推荐标签");
                    flowLayoutTags.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.publish_act_iv_del_content:
                etContent.setText("");
                break;
            case R.id.publish_act_tv_address:
                Intent address = new Intent(mActivity, SetAddressActivity.class);
                startActivityForResult(address, SET_POI_INFO_REQUEST);
                break;
            case R.id.btn_pick_photo:
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, MY_PICK_PICTURE);
                avatarDialog.dismiss();
                break;
            case R.id.btn_take_photo:
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                FileUtils.createDirFile(Constants.IMG_PATH);
                String path = Constants.IMG_PATH + CommonUtils.generateUUID() + ".jpg";
                file = FileUtils.createNewFile(path);
                if (file==null){
                    return;
                }
                Uri cameraUri = Uri.fromFile(file);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                startActivityForResult(openCameraIntent, MY_TAKE_PICTURE);
                avatarDialog.dismiss();
                break;
            case R.id.btn_cancel:
                avatarDialog.dismiss();
                break;
            case R.id.take_photo:
                Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                FileUtils.createDirFile(Constants.IMG_PATH);
                String path1 = Constants.IMG_PATH + CommonUtils.generateUUID() + ".jpg";
                file = FileUtils.createNewFile(path1);
                if (file ==null){
                    return;
                }
                Uri camera = Uri.fromFile(file);
                openCamera.putExtra(MediaStore.EXTRA_OUTPUT, camera);
                startActivityForResult(openCamera, MY_TAKE_PICTURE_2);
                dismissPopupWindow();
                break;
            case R.id.select_photo:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + ActivitySchemeManager.URL_GET_IMAGE_LIST);
                dismissPopupWindow();
                break;
            case R.id.feed_back_cancel:
                dismissPopupWindow();
                break;
        }
    }

    /**
     * 取消popupwindow
     */
    private void dismissPopupWindow() {
        if(mPopupWindow != null && mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSelectFiles = ChoosePhotoModel.getInstance().getmCurrentPhotoList();
        mGridAdapter.setData(mSelectFiles);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ChoosePhotoModel.getInstance().clear();
    }

    /**
     * 选择从相册还是拍照获取头像
     */
    private void initAvatarDialog() {
        if(avatarDialog == null){
            avatarDialog = new Dialog(this, R.style.fullDialog);
            RelativeLayout contentView = (RelativeLayout) View.inflate(this, R.layout.pick_or_take_photo_dialog, null);
            Button dialog_bt_pick_photo = (Button) contentView.findViewById(R.id.btn_pick_photo);
            Button dialog_bt_take_photo = (Button) contentView.findViewById(R.id.btn_take_photo);
            Button dialog_bt_cancel = (Button) contentView.findViewById(R.id.btn_cancel);
            dialog_bt_pick_photo.setOnClickListener(this);
            dialog_bt_take_photo.setOnClickListener(this);
            dialog_bt_cancel.setOnClickListener(this);
            avatarDialog.setContentView(contentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }
        avatarDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = null;
        switch (requestCode) {
            case MY_TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    uri = Uri.fromFile(file);
                    startImageAction(uri, 120, 120, MY_CROP_PICTURE, true);
                }
                break;
            case MY_TAKE_PICTURE_2:
                if (!file.exists() || file.length() == 0) {
                    return;
                }
                uri = Uri.fromFile(file);
                mSelectFiles.add(0, file.getAbsolutePath());
                mGridAdapter.notifyDataSetChanged();
                break;
            case MY_PICK_PICTURE:
                if (data == null) {
                    return;
                }
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        toast("SD不可用");
                        return;
                    }
                    uri = data.getData();
                    startImageAction(uri, 200, 200, MY_CROP_PICTURE, true);
                } else {
                    toast("照片获取失败");
                }
                break;
            case MY_CROP_PICTURE:
                if (data == null) {
                    return;
                } else {
                    saveCropAvator(data);
                }
                break;
            case SET_POI_INFO_REQUEST:
                if(resultCode == RESULT_OK) {
                    BaiduGeocoderModel.ResultBean.PoisBean poiInfo = (BaiduGeocoderModel.ResultBean.PoisBean) data.getSerializableExtra("POI_INFO");
                    tvAddress.setText(poiInfo.getName());
                    longitude = poiInfo.getPoint().getX();
                    latitude = poiInfo.getPoint().getY();
                }else if(resultCode == SetAddressActivity.SUGGESTION_RESULT){
                    SuggestionResult.SuggestionInfo poiInfo = data.getParcelableExtra("POI_INFO");
                    tvAddress.setText(poiInfo.key);
                    longitude = poiInfo.pt.longitude;
                    latitude = poiInfo.pt.latitude;
                }
                break;
            default:
                break;
        }
    }

    private void startImageAction(Uri uri, int outputX, int outputY,
                                  int requestCode, boolean isCrop) {
        Intent intent;
        if (isCrop) {
            intent = new Intent("com.android.camera.action.CROP");
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        }
        String path = Constants.IMG_PATH + CommonUtils.generateImgID() +".jpg";
        mFile = FileUtils.createNewFile(path);
        if (mFile==null){
            return;
        }
        Uri cameraUri = Uri.fromFile(mFile);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, requestCode);
    }

    private void saveCropAvator(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            bitmap = extras.getParcelable("data");
            if (bitmap != null){
                //在这上传照片
                getQiniuUploadToken(true);
            }
            if (bitmap != null&& bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    protected void getQiniuUploadToken(final boolean isUploadAvatar) {
        Map<String,Object> map = new HashMap<>();
        VolleyOperater<GetQiNiuTokenModel> operater = new VolleyOperater<GetQiNiuTokenModel>(this);
        operater.doRequest(Constants.URL_OBTIAN_QINIU_TOKEN, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        if (mMaterialDialog != null) mMaterialDialog.dismiss();
                        return;
                    }
                    GetQiNiuTokenModel getQiNiuTokenModel = (GetQiNiuTokenModel) obj;
                    if (isUploadAvatar) {
                        uploadAvatar(getQiNiuTokenModel.getValue().getToken(), mFile.getAbsolutePath(), uploadOptions, getQiNiuTokenModel.getValue().getPath());
                    } else {
                        uploadPicture(getQiNiuTokenModel.getValue().getToken(), getQiNiuTokenModel.getValue().getPath());
                    }
                } else {
                    if (mMaterialDialog != null) mMaterialDialog.dismiss();
                }
            }
        }, GetQiNiuTokenModel.class);
    }

    protected void uploadPicture(String token, String path) {
        mPosition = 0;
        imageUrls.setLength(0);
        startUpload(token, mSelectFiles.get(mPosition), path);
    }

    private void startUpload(final String token, String picturePath, final String path) {
        uploadManager.put(picturePath, getImgUUID(picturePath), token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                mPosition++;
                if (!info.isOK()) {
                    mMaterialDialog.dismiss();
                    ToastUtils.displayMsg("上传照片失败", mActivity);
                } else {
                    try {
                        mMaterialDialog.setMessage("已上传图片 " + mPosition + " / " + mSelectFiles.size());
                        if (mPosition == mSelectFiles.size()) {
                            imageUrls.append(path + key);
                            submitPublish();
                            return;
                        } else {
                            imageUrls.append(path + key + ";");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (mPosition < mSelectFiles.size())
                        startUpload(token, mSelectFiles.get(mPosition), path);
                }
            }
        }, uploadOptions);
    }

    protected void uploadAvatar(String token, final String picturePath, UploadOptions uploadOptions, final String path) {
        uploadManager.put(picturePath, getImgUUID(picturePath), token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if(info.isOK()){
                    avatarUrl = path + key;
                    ImageUtils.loadBitmap(mActivity, avatarUrl + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER, ivAvatar, R.drawable.icon, "");
                    MLog.i("qiniu:" + info.toString());
                }else {
                    toast("头像上传失败");
                }
            }
        }, uploadOptions);
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

    private void showUploadDialog(){
        if(mMaterialDialog==null){
            mMaterialDialog = new MaterialDialog(mActivity);
            mMaterialDialog.setCanceledOnTouchOutside(true);
        }
        mMaterialDialog.setMessage("开始上传...");
        mMaterialDialog.show();
    }

    private void publishInfo() {
        if(TextUtils.isEmpty(etName.getText().toString().trim())){
            toast("发布者姓名需填写完整");
            return;
        }
        if (TextUtils.isEmpty(etContent.getText().toString().trim())){
            toast("发布信息不能为空");
            return;
        }
        if(categoryList.size() == 0){
            toast("请选择标签");
            return;
        }
        if(CheckUtils.isNoEmptyList(mSelectFiles)) {
            showUploadDialog();
            getQiniuUploadToken(false);
        } else {
            showUploadDialog();
            submitPublish();
        }
    }

    private void submitPublish() {
        Map<String, Object> params = new HashMap<>();
        if (bbsInformation != null) {
            params.put("id", bbsInformation.getId());
        }
        params.put("author", etName.getText().toString().trim());
        params.put("sex", gender);
        params.put("headImg", avatarUrl);
        if (mobiles.contains(etPhone.getText().toString().trim())) {
            params.put("mobile", etPhone.getText().toString().trim());
        }
        params.put("content",etContent.getText().toString().trim());
        params.put("categoryId", categoryList.get(0));
        params.put("images", imageUrls.toString());
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("address", tvAddress.getText());
        VolleyOperater<Object> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_RELAESE_INFOMATION, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMaterialDialog.dismiss();
                if (isSucceed && obj != null) {

                }
            }
        }, Object.class);
    }

    /**
     * 请求短信验证码
     */
    private void getSMSCode() {
        tvSendSms.setEnabled(false);
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", etPhone.getText().toString().trim());
        VolleyOperater<SendSmsModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_SEND_RELEASE_INFOMATION_SMS, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    SendSmsModel sendSmsModel = (SendSmsModel) obj;
                    if (sendSmsModel.getCode() != 0) {
                        tvSendSms.setEnabled(true);
                        return;
                    }
                    ToastUtils.displayMsg("发送成功", mActivity);
                    tvSendSms.setText("验证码已发送");
                    new SmsCountDown(60000, 1000).start();
                } else {
                    tvSendSms.setEnabled(true);
                }
            }
        }, SendSmsModel.class);
    }

    /**
     * 验证短信验证码
     */
    private void checkSMSCode() {
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", etPhone.getText().toString().trim());
        map.put("code", etPwd.getText().toString().trim());
        VolleyOperater<SendSmsModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_CHECK_RELEASE_INFOMATION_SMS, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    SendSmsModel sendSmsModel = (SendSmsModel) obj;
                    if(sendSmsModel.getCode() != 0){
                        toast("验证码不正确");
                        return;
                    }
                    if(!mobiles.contains(etPhone.getText().toString().trim())){
                        mobiles.add(etPhone.getText().toString().trim());
                    }
                    toast("验证通过");
                    smsCodeLayout.setVisibility(View.GONE);
                    tvSendSms.setVisibility(View.GONE);
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
            tvSendSms.setText("重新获取(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            tvSendSms.setEnabled(true);
            tvSendSms.setText("获取验证码");
        }
    }

    private void getBBSCategories() {
        VolleyOperater<BbsCategoryListModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_FIND_BBS_CATEGORY_LIST, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if(obj instanceof String) {
                        return;
                    }
                    BbsCategoryListModel model = (BbsCategoryListModel) obj;
                    showBbsCategory(model.getValue());
                }
            }

        }, BbsCategoryListModel.class);
    }

    private void showBbsCategory(ArrayList<BBSCategory> bbsCategoryList) {
        flowLayoutTags.removeAllViews();
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(DipToPx.dip2px(mActivity, 15), DipToPx.dip2px(mActivity, 15), 0, 0);// 设置边距
        for (int i = 0; i < bbsCategoryList.size(); i++) {
            final TextView textView = new TextView(mActivity);
            textView.setTag(bbsCategoryList.get(i).getId());
            textView.setTextSize(12);
            textView.setText(bbsCategoryList.get(i).getName());
            textView.setPadding(DipToPx.dip2px(mActivity,9), DipToPx.dip2px(mActivity,6), DipToPx.dip2px(mActivity,9), DipToPx.dip2px(mActivity,6));
            textView.setTextColor(getResources().getColor(R.color.color_6));
            textView.setBackgroundResource(R.drawable.bbs_category_bg);
            flowLayoutTags.addView(textView, layoutParams);
            // 标签点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(textView.isSelected()){
                        categoryList.remove((Long) textView.getTag());
                        textView.setSelected(false);
                        textView.setTextColor(getResources().getColor(R.color.color_6));
                    }else if(categoryList.size() < 3){
                        categoryList.add((Long) textView.getTag());
                        textView.setSelected(true);
                        textView.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        toast("最多选择3个标签");
                    }
                }
            });
        }
    }
}

package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.chooseimage.ChoosePhotoModel;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.GetQiNiuTokenModel;
import com.project.mgjandroid.model.SubmitFeedbackModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.FeedbackGridAdapter;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.ui.view.materialdialog.MaterialDialog;
import com.project.mgjandroid.utils.FileUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.ta.utdid2.android.utils.StringUtils;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;


@Router("feed_back_activity")
public class FeedBackActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, TextWatcher {
    @InjectView(R.id.order_detail_act_iv_back) private ImageView ivBack;
    @InjectView(R.id.photo_container) private NoScrollGridView mGridView;
    @InjectView(R.id.feed_back_content) private EditText etContent;
    @InjectView(R.id.opinion_mobile) private EditText etMobile;
    @InjectView(R.id.feed_back_submit) private TextView tvSubmit;
    @InjectView(R.id.feed_back_count) private TextView tvCount;
    @InjectView(R.id.feed_back_title) private TextView tvTitle;
    private UploadManager uploadManager;
    public static final int MY_TAKE_PICTURE = 101;
    public static final int MY_PICK_PICTURE = 201;
    public static final int MY_CROP_PICTURE = 301;
    private File file;
    private File mFile;
    private ArrayList<String> mSelectFiles;
    private PopupWindow mPopupWindow;
    private FeedbackGridAdapter mGridAdapter;
    private int mPosition = 0;
    private StringBuffer imageUrls;
    private View mDecorView;
    private MaterialDialog mMaterialDialog;
    private UploadOptions mUploadOptions;
//    private LoadingWindow loadingWindow;
    private String mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        Injector.get(this).inject();
        initData();
    }

    protected void initData() {
        ivBack.setOnClickListener(this);
        tvTitle.setText(getResources().getString(R.string.feed_back_title));
        imageUrls = new StringBuffer();
        mSelectFiles = new ArrayList<>();
        mGridAdapter = new FeedbackGridAdapter(mActivity, mSelectFiles, 3);
        mGridView.setAdapter(mGridAdapter);
        mGridView.setOnItemClickListener(this);
        etContent.addTextChangedListener(this);
        tvSubmit.setOnClickListener(this);
        initQiNiu();
        mDecorView = getWindow().getDecorView();
        ChoosePhotoModel.getInstance().setCurrentActivity(this.getClass());
        ChoosePhotoModel.getInstance().setMaxCount(3);
    }

    private void initQiNiu() {
        uploadManager = new UploadManager();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position == mSelectFiles.size()){
            showPopupWindow();
        }else{
            skipToPhotoActivity(position);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.order_detail_act_iv_back:
                back();
                break;
            case R.id.take_photo:
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                FileUtils.createDirFile(Constants.IMG_PATH);
                String path = Constants.IMG_PATH + generateUUID() + ".jpg";
                file = FileUtils.createNewFile(path);
                if (file ==null){
                    return;
                }
                Uri cameraUri = Uri.fromFile(file);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                startActivityForResult(openCameraIntent, MY_TAKE_PICTURE);
                dismissPopupWindow();
                break;
            case R.id.select_photo:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + ActivitySchemeManager.URL_GET_IMAGE_LIST);
                dismissPopupWindow();
                break;
            case R.id.feed_back_cancel:
                dismissPopupWindow();
                break;
            case R.id.feed_back_submit:
                if(checkIfSubmit()) {
                    if(mSelectFiles != null && mSelectFiles.size() > 0) {
                        getQiniuUploadToken();
                    }else{
                        submitAdviceWithNoPicture();
                    }
                }
                break;
        }
    }

    private boolean checkIfSubmit() {
        if(TextUtils.isEmpty(etContent.getText().toString().trim())){
            ToastUtils.displayMsg("您还没有发表意见", mActivity);
        }else {
            return true;
        }
        return false;
    }

    public static boolean isValidPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        if (!(Pattern.matches(("^((13[0-9])|(15[^4,\\D])|(17[0,6-8])|(18[0-9]))\\d{8}$"), phone))) {
            return false;
        }
        return true;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = null;
        switch (requestCode) {
            case MY_TAKE_PICTURE:
                if (!file.exists() || file.length() == 0) {
                    return;
                }
                uri = Uri.fromFile(file);
                mSelectFiles.add(0,file.getAbsolutePath());
                mGridAdapter.notifyDataSetChanged();
//                startImageAction(uri, 120, 120, MY_CROP_PICTURE, true);
                break;
            case MY_PICK_PICTURE:
                if (data == null) {
                    return;
                }
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        ToastUtils.displayMsg("SD不可用", mActivity);
                        return;
                    }
                    uri = data.getData();
//                    startImageAction(uri, 120, 120, MY_CROP_PICTURE, true);
                } else {
                    ToastUtils.displayMsg("照片获取失败",mActivity);
                }
                break;
            default:
                break;
        }
    }

//    }

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

    protected void getQiniuUploadToken() {
        showUploadDialog();
        VolleyOperater<GetQiNiuTokenModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_GET_QINIU_TOKEN, null, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        mMaterialDialog.dismiss();
                        ToastUtils.displayMsg("获取七牛token失败", mActivity);
                    }
                    GetQiNiuTokenModel qiNiuModel = (GetQiNiuTokenModel) obj;
                    if (qiNiuModel.isSuccess()) {
                        mPath = qiNiuModel.getValue().getPath();
                        uploadPicture(qiNiuModel.getValue().getToken());
                    }
                } else {
                    mMaterialDialog.dismiss();
                }
            }
        }, GetQiNiuTokenModel.class);
    }

    protected void uploadPicture(String token) {
        mUploadOptions = new UploadOptions(null, null, false, new UpProgressHandler() {
            public void progress(String key, double percent) {

            }
        }, null);

        mPosition = 0;
        //清空
        imageUrls.setLength(0);
        startUpload(token, mSelectFiles.get(mPosition), mUploadOptions);
    }

    protected void startUpload(final String token, final String picturePath, UploadOptions uploadOptions) {
//        loadingWindow = new LoadingWindow(this);
//        loadingWindow.show();
        uploadManager.put(picturePath, getImgUUID(picturePath), token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                mPosition++;

                if (!info.isOK()) {
                    mMaterialDialog.dismiss();
                    ToastUtils.displayMsg("上传失败", mActivity);
                    return;
                } else {
                    try {
                        if (mPosition == mSelectFiles.size()) {
//                            if(loadingWindow.isShowing()){
//                                loadingWindow.dismiss();
//                            }
                            imageUrls.append(mPath + key);
                            submitAdvice();
                            return;
                        } else {
                            imageUrls.append(mPath + key + ";");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (mPosition < mSelectFiles.size())
                        startUpload(token, mSelectFiles.get(mPosition), mUploadOptions);
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
        return generateImgID() + picType;
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成图片随机id
     *
     * @return
     */
    public static String generateImgID() {
        return new StringBuilder().
                append(new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())).
                append(generateThreeRandomNum()).toString();
    }


    /**
     * 生成三位随机数
     *
     * @return
     */
    public static int generateThreeRandomNum() {
        Random random = new Random();
        int num = (int) (random.nextDouble() * (1000 - 100) + 100);
        return num;
    }

    /**
     * 提交意见
     */
    private void submitAdvice() {
        Map<String,Object> map = new HashMap<>();
        map.put("content", etContent.getText().toString().trim());
        map.put("imgs", imageUrls.toString());
//        map.put("contacts", "185");
        VolleyOperater<SubmitFeedbackModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_FEED_BACK, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMaterialDialog.dismiss();
                if (isSucceed && obj != null) {
                    if(obj instanceof String){
                        ToastUtils.displayMsg((String) obj,mActivity);
                        return ;
                    }

                    SubmitFeedbackModel submitFeedbackModel = (SubmitFeedbackModel) obj;
                    if(submitFeedbackModel.isSuccess()){
                        ToastUtils.displayMsg("提交成功，感谢您的反馈", mActivity);
                        finish();
                    }
                }
            }
        }, SubmitFeedbackModel.class);
    }

    /**
     * 提交意见并不上传图片
     */
    private void submitAdviceWithNoPicture() {
        showUploadDialog();
        Map<String,Object> map = new HashMap<>();
        map.put("content", etContent.getText().toString().trim());
//        map.put("contacts", etMobile.getText().toString().trim());
        VolleyOperater<SubmitFeedbackModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_FEED_BACK, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMaterialDialog.dismiss();
                if (isSucceed && obj != null) {
                    if(obj instanceof String){
                        ToastUtils.displayMsg((String) obj,mActivity);
                        return ;
                    }

                    SubmitFeedbackModel submitFeedbackModel = (SubmitFeedbackModel) obj;
                    if(submitFeedbackModel.isSuccess()){
                        ToastUtils.displayMsg("提交成功，感谢您的反馈", mActivity);
                        finish();
                    }
                }
            }
        }, SubmitFeedbackModel.class);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        tvCount.setText(s.toString().trim().length() + "/500字");
        if(s.toString().trim().length() > 0) {
            tvSubmit.setEnabled(true);
        } else {
            tvSubmit.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void showUploadDialog(){
        if(mMaterialDialog==null){
            mMaterialDialog = new MaterialDialog(mActivity);
            mMaterialDialog.setCanceledOnTouchOutside(true);
            mMaterialDialog.setMessage("上传中...");
        }
        mMaterialDialog.show();
    }
}

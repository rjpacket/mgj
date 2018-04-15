package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.chooseimage.ChoosePhotoModel;
import com.project.mgjandroid.chooseimage.UploadPhoto;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.GetQiNiuTokenModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.homemaking.PublishHomeMakingInfoActivity;
import com.project.mgjandroid.ui.activity.renthouse.PublishHouseLeaseInfoActivity;
import com.project.mgjandroid.ui.activity.repair.PublishRepairInfoActivity;
import com.project.mgjandroid.ui.adapter.PhotoRecyclerViewAdapter;
import com.project.mgjandroid.ui.view.ItemTouchHelperCallback;
import com.project.mgjandroid.ui.view.ProgressImageView;
import com.project.mgjandroid.ui.view.RecyclerViewItemSpace;
import com.project.mgjandroid.utils.BitmapUtil;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.StreamUtils;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkpad on 2016/7/9.
 *
 */
public class UploadPhotoActivity extends BaseActivity{

    @InjectView(R.id.common_back) private ImageView ivBack;
    @InjectView(R.id.common_title) private TextView tvTitle;
    @InjectView(R.id.tv_confirm) private TextView tvUplaod;
    @InjectView(R.id.recycler_view) private RecyclerView recyclerView;

    private PhotoRecyclerViewAdapter adapter;
    private PopupWindow mPopupWindow;
    private ArrayList<UploadPhoto> uploadPhotos = new ArrayList<>();
    private UploadManager uploadManager;
    private boolean isUploading = false;
    private boolean isUploadingOnlyOne = false;
    private int hasUploadCount = 0;
    private String qiniuTokenUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
        Injector.get(this).inject();
        String from = getIntent().getStringExtra("from");
        if(!TextUtils.isEmpty(from) && from.equals(PublishUsedGoodsInfoActivity.class.toString())){
            qiniuTokenUrl = Constants.URL_OBTAIN_QINIU_TOKEN_SECOND_HAND;
        } else if(!TextUtils.isEmpty(from) && from.equals(PublishHouseLeaseInfoActivity.class.toString())) {
            qiniuTokenUrl = Constants.URL_RENT_HOUSE_OBTAIN_QINIU_TOKEN;
        } else if(!TextUtils.isEmpty(from) && from.equals(PublishEducationInfoActivity.class.toString())) {
            qiniuTokenUrl = Constants.URL_EDUCATION_OBTAIN_QINIU_TOKEN;
        } else if(!TextUtils.isEmpty(from) && from.equals(PublishHomeMakingInfoActivity.class.toString())) {
            qiniuTokenUrl = Constants.URL_HOME_MAKING_OBTAIN_QINIU_TOKEN;
        } else if(!TextUtils.isEmpty(from) && from.equals(PublishRepairInfoActivity.class.toString())) {
            qiniuTokenUrl = Constants.URL_REPAIR_OBTAIN_QINIU_TOKEN;
        } else {
            qiniuTokenUrl = Constants.URL_GET_QINIU_TOKEN;
        }
        initView();
    }

    private void initView() {
        tvUplaod.setOnClickListener(this);
        ivBack.setOnClickListener(this);

        adapter = new PhotoRecyclerViewAdapter(this, uploadPhotos, true);
        adapter.setListener(new AddPhotoClickListenerImpl());
        recyclerView.addItemDecoration(new RecyclerViewItemSpace(getResources().getDimensionPixelSize(R.dimen.x5), 3));
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        ChoosePhotoModel.getInstance().setCurrentActivity(this.getClass());
        ChoosePhotoModel.getInstance().setMaxCount(12);
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)
                .putThreshhold(512 * 1024)
                .connectTimeout(10)
                .responseTimeout(60)
                .build();
        uploadManager = new UploadManager(config);

    }

    @Override
    protected void onResume() {
        super.onResume();
        uploadPhotos = ChoosePhotoModel.getInstance().updateUploadPhotoList();
        tvTitle.setText(uploadPhotos.size() + "张");
        adapter.setUploadPhotos(uploadPhotos);
        adapter.setCanDelete(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                back();
                break;
            case R.id.tv_confirm:
                if(checkUploading()) break;
                uploadPhotos();
                break;
            case R.id.take_photo:
                if(!CheckUtils.hasCamera(UploadPhotoActivity.this)) {
                    toast("您的手机上没有检测到相机");
                    return;
                }
                Intent camera = new Intent(UploadPhotoActivity.this, CameraActivity.class);
                startActivity(camera);
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

    private class AddPhotoClickListenerImpl implements PhotoRecyclerViewAdapter.AddPhotoClickListener {
        @Override
        public void delPhoto() {
            tvTitle.setText(uploadPhotos.size() + "张");
        }

        @Override
        public void addPhoto() {
            if(!checkUploading()){
                showPopupWindow();
            }
        }

        @Override
        public void uploadPhoto(int position) {
            if(!checkUploading()){
                isUploading = true;
                isUploadingOnlyOne = true;
                getQiniuUploadToken(position);
            }
        }
    }

    private void uploadPhotos() {
        int finishedPos = 0;
        for(UploadPhoto uploadPhoto:uploadPhotos){
            if(TextUtils.isEmpty(uploadPhoto.getUrl())) {
                isUploading = true;
                adapter.setCanDelete(false);
                getQiniuUploadToken(-1);
                break;
            }else{
                finishedPos ++;
            }
        }
        if(uploadPhotos.size() != 0){
            if(finishedPos == uploadPhotos.size()) {
                ToastUtils.displayMsg("已全部上传成功", mActivity);
                finish();
            }
        }else{
            ToastUtils.displayMsg("您还未添加图片", mActivity);
        }
    }

    protected void getQiniuUploadToken(final int position) {
        Map<String,Object> map = new HashMap<>();
        VolleyOperater<GetQiNiuTokenModel> operater = new VolleyOperater<>(this);
        operater.doRequest(qiniuTokenUrl, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        isUploading = false;
                        adapter.setCanDelete(true);
                        return;
                    }
                    GetQiNiuTokenModel getQiNiuTokenModel = (GetQiNiuTokenModel) obj;
                    uploadPicture(getQiNiuTokenModel.getValue().getToken(), getQiNiuTokenModel.getValue().getPath(), position);
                } else {
                    isUploading = false;
                    adapter.setCanDelete(true);
                }
            }
        }, GetQiNiuTokenModel.class);
    }

    protected void uploadPicture(String token, String path, int position) {
        if (position == -1) {
            for(int i = 0, size = uploadPhotos.size(); i < size; i++){
                if(TextUtils.isEmpty(uploadPhotos.get(i).getUrl())) {
                    startUpload(token, uploadPhotos.get(i).getPath(), path, i);
                } else {
                    hasUploadCount++;
                }
            }
        } else {
            startUpload(token, uploadPhotos.get(position).getPath(), path, position);
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    tvUplaod.setText("确认");
                    break;
            }
        }
    };

    private void startUpload(final String token, String picturePath, final String path, final int position) {
        if (BitmapUtil.needCompressBitmap(picturePath, 1280)) {
            uploadManager.put(StreamUtils.Bitmap2Bytes(BitmapUtil.compressBitmap(picturePath, 1280)),
                    getImgUUID(picturePath), token, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject response) {
                    if(isUploadingOnlyOne) {
                        isUploading = false;
                        isUploadingOnlyOne = false;
                    } else {
                        hasUploadCount++;
                        if(hasUploadCount == uploadPhotos.size()){
                            hasUploadCount = 0;
                            isUploading = false;
                            adapter.setCanDelete(true);
                            mHandler.obtainMessage(1).sendToTarget();
                        }
                    }
                    if (!info.isOK()) {
                        uploadPhotos.get(position).setUploaded(false);
                        adapter.setUploadPhotos(uploadPhotos);
                    } else {
                        uploadPhotos.get(position).setUploadFinish(true);
                        uploadPhotos.get(position).setUrl(path + key);
                    }
                }
            }, new UploadOptions(null, null, false, new UpProgressHandler() {
                public void progress(String key, double percent) {
                    MLog.i(position + "qiniu:"+ key + ": " + percent);
                    ((ProgressImageView) recyclerView.getChildAt(position).findViewById(R.id.photo)).setProgress(percent);
                }
            }, null));
        } else {
            uploadManager.put(picturePath, getImgUUID(picturePath), token, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject response) {
                    if(isUploadingOnlyOne) {
                        isUploading = false;
                        isUploadingOnlyOne = false;
                    } else {
                        hasUploadCount++;
                        if(hasUploadCount == uploadPhotos.size()){
                            hasUploadCount = 0;
                            isUploading = false;
                            adapter.setCanDelete(true);
                            mHandler.obtainMessage(1).sendToTarget();
                        }
                    }
                    if (!info.isOK()) {
                        uploadPhotos.get(position).setUploaded(false);
                        adapter.setUploadPhotos(uploadPhotos);
                    } else {
                        uploadPhotos.get(position).setUploadFinish(true);
                        uploadPhotos.get(position).setUrl(path + key);
                    }
                }
            }, new UploadOptions(null, null, false, new UpProgressHandler() {
                public void progress(String key, double percent) {
                    MLog.i(position + "qiniu:"+ key + ": " + percent);
                    ((ProgressImageView) recyclerView.getChildAt(position).findViewById(R.id.photo)).setProgress(percent);
                }
            }, null));
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

    private boolean checkUploading(){
        if(isUploading){
            toast("正在上传照片，请稍后再进行操作");
            return true;
        }
        return false;
    }
}


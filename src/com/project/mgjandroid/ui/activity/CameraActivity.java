package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.chooseimage.ChoosePhotoModel;
import com.project.mgjandroid.chooseimage.UploadPhoto;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.ui.activity.homemaking.PublishHomeMakingInfoActivity;
import com.project.mgjandroid.ui.activity.renthouse.PublishHouseLeaseInfoActivity;
import com.project.mgjandroid.ui.activity.repair.PublishRepairInfoActivity;
import com.project.mgjandroid.ui.adapter.PhotoRecyclerViewAdapter;
import com.project.mgjandroid.utils.BitmapUtil;
import com.project.mgjandroid.utils.FileUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by yuandi on 2016/7/8.
 *
 */

public class CameraActivity extends BaseActivity implements SurfaceHolder.Callback{

    @InjectView(R.id.act_camera_sv) private SurfaceView surfaceView;
    @InjectView(R.id.act_camera_recycler_view) private RecyclerView recyclerView;
    @InjectView(R.id.act_camera_tv_cancel) private TextView tvCancel;
    @InjectView(R.id.act_camera_iv_take_photo) private ImageView ivTakePhoto;
    @InjectView(R.id.act_camera_tv_confirm) private TextView tvConfirm;
    @InjectView(R.id.tv_photo_count) private TextView tvPhotoCount;
    @InjectView(R.id.flash_layout) private LinearLayout flashLayout;
    @InjectView(R.id.iv_flash_mode) private ImageView ivFlashMode;
    @InjectView(R.id.tv_flash_mode) private TextView tvFlashMode;
    @InjectView(R.id.change_camera) private ImageView ivChangeCamera;

    private SurfaceHolder sh;
    private Camera mCamera;
    private int cameraPosition = 1;//0代表前置摄像头，1代表后置摄像头
    private ArrayList<UploadPhoto> uploadPhotos = new ArrayList<>();
    private PhotoRecyclerViewAdapter adapter;
    private AddPhotoClickListenerImpl listener = new AddPhotoClickListenerImpl();
    private int MAX_COUNT;
    private boolean firstIn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//拍照过程屏幕一直处于高亮
        //设置手机屏幕朝向,忽略物理感应器——即显示方向与物理感应器无关，不管用户如何旋转设备显示方向都不会随着改变
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        Injector.get(this).inject();
        MAX_COUNT = ChoosePhotoModel.getInstance().getMaxCount() - ChoosePhotoModel.getInstance().getmCurrentPhotoList().size() + ChoosePhotoModel.getInstance().getmCameraPhotoList().size();
        initView();
        initData();
    }

    private void initView() {
        tvCancel.setOnClickListener(this);
        ivTakePhoto.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        flashLayout.setOnClickListener(this);
        ivChangeCamera.setOnClickListener(this);

        sh = surfaceView.getHolder();
        sh.addCallback(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PhotoRecyclerViewAdapter(this, uploadPhotos, false);
        adapter.setListener(listener);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        ArrayList<String> mCameraPhotoList = ChoosePhotoModel.getInstance().getmCameraPhotoList();
        for(String path:mCameraPhotoList){
            uploadPhotos.add(new UploadPhoto(path));
        }
        adapter.setUploadPhotos(uploadPhotos);
        listener.delPhoto();
    }

    private class AddPhotoClickListenerImpl implements PhotoRecyclerViewAdapter.AddPhotoClickListener {
        @Override
        public void delPhoto() {
            if(uploadPhotos.size() > 0){
                tvPhotoCount.setVisibility(View.VISIBLE);
                tvPhotoCount.setText(String.valueOf(uploadPhotos.size()));
            } else {
                tvPhotoCount.setVisibility(View.GONE);
            }
        }

        @Override
        public void addPhoto() {
        }

        @Override
        public void uploadPhoto(int position) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_camera_tv_cancel:
                back();
                break;

            case R.id.act_camera_iv_take_photo:
                if (uploadPhotos.size() < MAX_COUNT){
                    takePhoto();
                } else {
                    toast("照片数量已达上限");
                }
                break;

            case R.id.act_camera_tv_confirm:
                confirmPhoto();
                break;

            case R.id.flash_layout:
                Parameters parameters = mCamera.getParameters();
                if (mCamera == null || parameters == null || cameraPosition == 2) return;
                if (parameters.getFlashMode() == null) {
                    tvFlashMode.setText("关闭");
                    ivFlashMode.setImageResource(R.drawable.flash_off);
                    toast("检测到手机不支持闪光灯");
                    return;
                }
                if(Parameters.FLASH_MODE_OFF.equals(parameters.getFlashMode())){
                    parameters.setFlashMode(Parameters.FLASH_MODE_ON);
                    tvFlashMode.setText("开启");
                    ivFlashMode.setImageResource(R.drawable.flash_on);
                }else if(Parameters.FLASH_MODE_ON.equals(parameters.getFlashMode())){
                    parameters.setFlashMode(Parameters.FLASH_MODE_AUTO);
                    tvFlashMode.setText("自动");
                    ivFlashMode.setImageResource(R.drawable.flash_auto);
                }else{
                    parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
                    tvFlashMode.setText("关闭");
                    ivFlashMode.setImageResource(R.drawable.flash_off);
                }
                mCamera.setParameters(parameters);
                break;

            case R.id.change_camera:
                if (mCamera == null) return;
                //切换前后摄像头
                int cameraCount;
                CameraInfo cameraInfo = new CameraInfo();
                cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数

                for(int i = 0; i < cameraCount; i++) {
                    Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
                    if(cameraPosition == 1) {
                        //现在是后置，变更为前置
                        if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                            mCamera.stopPreview();//停掉原来摄像头的预览
                            mCamera.release();//释放资源
                            mCamera = null;//取消原来摄像头
                            mCamera = Camera.open(i);//打开当前选中的摄像头
                            if (mCamera == null) return;
                            showViews(mCamera, sh);
                            cameraPosition = 0;
                            break;
                        }
                    } else {
                        //现在是前置， 变更为后置
                        if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                            mCamera.stopPreview();
                            mCamera.release();
                            mCamera = null;
                            mCamera = Camera.open(i);
                            if (mCamera == null) return;
                            showViews(mCamera, sh);
                            cameraPosition = 1;
                            break;
                        }
                    }

                }
                break;
        }
    }

    private void confirmPhoto() {
        ArrayList<String> filePaths = new ArrayList<>();
        for(UploadPhoto uploadPhoto:uploadPhotos){
            filePaths.add(uploadPhoto.getPath());
        }
        ChoosePhotoModel.getInstance().delCameraPhotoList();
        ChoosePhotoModel.getInstance().addmCurrentPhotoList(filePaths);
        ChoosePhotoModel.getInstance().setmCameraPhotoList(filePaths);
        if(PublishUsedGoodsInfoActivity.class.getName().equals(ChoosePhotoModel.getInstance().getFrom())
                || PublishHouseLeaseInfoActivity.class.getName().equals(ChoosePhotoModel.getInstance().getFrom())
                || PublishEducationInfoActivity.class.getName().equals(ChoosePhotoModel.getInstance().getFrom())
                || PublishHomeMakingInfoActivity.class.getName().equals(ChoosePhotoModel.getInstance().getFrom())
                || PublishRepairInfoActivity.class.getName().equals(ChoosePhotoModel.getInstance().getFrom())) {
            ChoosePhotoModel.getInstance().setFrom("");
            Intent intent = new Intent(this, UploadPhotoActivity.class);
            startActivity(intent);
            finish();
        } else {
            back();
        }
    }

    private void takePhoto() {
        ivTakePhoto.setEnabled(false);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                // 判断是否对焦成功
                if (success) {
                    // 拍照 第三个参数为拍照回调
                    try {
                        mCamera.takePicture(null, null, pc);
                    } catch (Exception e) {
                        ivTakePhoto.setEnabled(true);
                        e.printStackTrace();
                    }
                }else{
                    ivTakePhoto.setEnabled(true);
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null) {
            mCamera = getCamera();
            if (sh != null) {
                showViews(mCamera, sh);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        clearCamera();
    }

    /**
     * 获取系统相机
     *
     * @return camera
     */
    private Camera getCamera() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.displayMsg("请在手机应用权限管理中打开摄像头权限",mActivity);
            finish();
        }
        return camera;
    }

    /**
     * 与SurfaceView传播图像
     */
    private void showViews(Camera camera, SurfaceHolder holder) {
        // 预览相机,绑定
        if (camera == null || holder == null) return;
        try {

            if(firstIn){
                Parameters parameters = camera.getParameters();
                parameters.setPictureFormat(ImageFormat.JPEG);// 设置相片格式

                parameters.setFlashMode(Parameters.FLASH_MODE_AUTO);

                List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();// 获取支持预览照片的尺寸
                if(supportedPreviewSizes != null) {
                    for(Camera.Size size:supportedPreviewSizes){
                        MLog.d("supportedPreviewSize:" + size.width + "-----" + size.height);
                    }
                    Camera.Size previewSize = supportedPreviewSizes.get(0);
                    parameters.setPreviewSize(previewSize.width, previewSize.height);// 设置预览照片的大小

                    parameters.setPictureSize(1280, 720);
                }
//            List<Size> supportedPictureSizes = parameters.getSupportedPictureSizes();// 获取支持保存图片的尺寸
//            if(supportedPictureSizes != null) {
//                for(Size size:supportedPictureSizes){
//                    MLog.d("supportedPictureSize:" + size.width + "-----" + size.height);
//                }
//                Size pictureSize = supportedPictureSizes.get(0);
//                parameters.setPictureSize(pictureSize.width, pictureSize.height);// 设置照片的大小
//            }

                parameters.setFocusMode(Parameters.FOCUS_MODE_AUTO);// 设置对焦方式，这里设置自动对焦

                camera.setParameters(parameters);

                firstIn = false;
            }

            camera.setPreviewDisplay(holder);

            camera.setDisplayOrientation(90);// 系统相机默认是横屏的，我们要旋转90°

            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放相机的内存
     */
    private void clearCamera() {

        // 释放hold资源
        if (mCamera != null) {
            // 停止预览
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            // 释放相机资源
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        showViews(mCamera, sh);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
        showViews(mCamera, sh);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        clearCamera();
    }


    private Camera.PictureCallback pc = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // data为完整数据
            mCamera.stopPreview();
            Bitmap bitmap;
            if(cameraPosition == 1){
                bitmap = BitmapUtil.rotateBitmapByDegree(BitmapUtil.Bytes2Bimap(data), 90);
            } else {
                bitmap = BitmapUtil.rotateBitmapByDegree(BitmapUtil.Bytes2Bimap(data), -90);
            }
            FileUtils.createDirFile(Constants.IMG_PATH);
            String path = Constants.IMG_PATH + File.separator + generateImgUUID();
            File file = FileUtils.createNewFile(path);
            if (BitmapUtil.saveBitmapToFileSuccess(bitmap, file)) {
                MLog.d(data.length + "------------->>>" + file.getAbsolutePath());
                uploadPhotos.add(new UploadPhoto(file.getAbsolutePath()));
                adapter.setUploadPhotos(uploadPhotos);
                recyclerView.scrollToPosition(uploadPhotos.size() - 1);
                if(uploadPhotos.size() > 0){
                    tvPhotoCount.setVisibility(View.VISIBLE);
                    tvPhotoCount.setText(String.valueOf(uploadPhotos.size()));
                } else {
                    tvPhotoCount.setVisibility(View.GONE);
                }
            }
            ivTakePhoto.setEnabled(true);
            showViews(mCamera, sh);
        }
    };

    public static String generateImgUUID() {
        return UUID.randomUUID().toString().replace("-", "")  + ".jpg";
    }
}

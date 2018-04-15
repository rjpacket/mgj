package com.project.mgjandroid.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.BindThirdLoginModel;
import com.project.mgjandroid.model.ChangeHeaderModel;
import com.project.mgjandroid.model.CheckBindModel;
import com.project.mgjandroid.model.GetQiNiuTokenModel;
import com.project.mgjandroid.model.SmsLoginModel.ValueEntity.AppUserEntity;
import com.project.mgjandroid.model.UnbindModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.FileUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuandi on 2016/3/14.
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener{
    private static final int MY_TAKE_PICTURE = 1001;
    private static final int MY_PICK_PICTURE = 1002;
    private static final int MY_CROP_PICTURE = 1003;
    public static final int EDIT_USER_NAME = 1004;
    public static final int EDIT_USER_NAME_SUCCESS = 1005;

    @InjectView(R.id.user_info_act_back)
    private ImageView ivBack;
    @InjectView(R.id.user_info_act_layout_avatar)
    private RelativeLayout rlAvatar;
    @InjectView(R.id.user_info_act_avatar)
    private RoundImageView ivAvatar;
    @InjectView(R.id.user_info_act_layout_user_name)
    private RelativeLayout rlUserName;
    @InjectView(R.id.user_info_act_user_name)
    private TextView tvName;
    @InjectView(R.id.user_info_act_layout_bind_mobile)
    private RelativeLayout rlBindMobile;
    @InjectView(R.id.user_info_act_mobile)
    private TextView tvMobile;
    @InjectView(R.id.user_info_act_layout_bind_weixin)
    private RelativeLayout rlBindWeiChat;
    @InjectView(R.id.user_info_act_weixin)
    private TextView tvWeiChat;
    @InjectView(R.id.user_info_act_layout_bind_qq)
    private RelativeLayout rlBindQQ;
    @InjectView(R.id.user_info_act_qq)
    private TextView tvQQ;
    @InjectView(R.id.user_info_act_layout_bind_weibo)
    private RelativeLayout rlBindWeibo;
    @InjectView(R.id.user_info_act_weibo)
    private TextView tvWeibo;
    @InjectView(R.id.user_info_act_layout_login_password)
    private RelativeLayout rlLoginPassword;
    @InjectView(R.id.user_info_act_login_password)
    private TextView tvLoginPassword;
    @InjectView(R.id.user_info_act_layout_pay_password)
    private RelativeLayout rlPayPassword;
    @InjectView(R.id.user_info_act_pay_password)
    private TextView tvPayPassword;
    @InjectView(R.id.user_info_act_layout_pay_limit)
    private RelativeLayout rlPayLimit;
    @InjectView(R.id.user_info_act_pay_limit)
    private TextView tvPayLimit;

    private Dialog avatarDialog;
    private File file;
    private File mFile;
    private Context mContext;
    private UploadManager uploadManager;
    private Bitmap bitmap;
    private IWXAPI api;

    private boolean isBindPhone,isBindWechat,isBindWeibo,isBindQQ;
    private static final int BIND_STATE=800;
    private static final int WECHAT_BIND_SUCCESS=810;
    private static final int WECHAT_BIND_FAIL=811;
    public static UserInfoActivity instance;
    private CustomDialog dialog;

    private boolean canBind=true;
    private int count = 2;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_user_info);
        Injector.get(this).inject();
        mContext = this;
        initView();
//        final Intent intent = new Intent(this,MusicService.class);
//        Timer timer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                while (count > 0) {
//                    startService(intent);
//                    try {
//                        Thread.sleep(3000);
//                        stopService(intent);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    count--;
//                }
//            }
//        };
//        timer.schedule(task,1000);
    }

    private void checkBindState() {
        VolleyOperater<CheckBindModel> operater = new VolleyOperater<CheckBindModel>(UserInfoActivity.this);
        operater.doRequest(Constants.URL_CHECK_THIRD_LOGIN, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if(isSucceed){
                    CheckBindModel checkWechatBindModel= (CheckBindModel) obj;
                    List<CheckBindModel.CheckWechatBindEntity> value =checkWechatBindModel.getValue();
                    for(int i=0;i<value.size();i++){
                        switch (value.get(i).getType()){
                            case 1:
                                isBindWechat = true;
                                break;
                            case 2:
                                isBindQQ = true;
                                break;
                        }
                    }
                    handler.sendEmptyMessage(BIND_STATE);
                }
            }
        }, CheckBindModel.class);
    }

    private void initView(){
        checkBindState();
        api = WXAPIFactory.createWXAPI(this, Constants.WE_CHAT_APP_ID);
        instance=UserInfoActivity.this;
        ivBack.setOnClickListener(this);
        rlAvatar.setOnClickListener(this);
        rlUserName.setOnClickListener(this);
        rlBindMobile.setOnClickListener(this);
        rlBindWeiChat.setOnClickListener(this);
        rlBindQQ.setOnClickListener(this);
        rlBindWeibo.setOnClickListener(this);
        rlLoginPassword.setOnClickListener(this);
        rlPayPassword.setOnClickListener(this);
        rlPayLimit.setOnClickListener(this);

        AppUserEntity userInfo = App.getUserInfo();
        ImageUtils.loadBitmap(mContext , userInfo.getHeaderImg() + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER, ivAvatar, R.drawable.head_default , "");
        tvName.setText(userInfo.getName() == null ? userInfo.getMobile() : userInfo.getName());

        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认 256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认 512K
                .connectTimeout(10) // 链接超时。默认 10秒
                .responseTimeout(60) // 服务器响应超时。默认 60秒
//                .recorder(recorder)  // recorder 分片上传时，已上传片记录器。默认 null
//                .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
//                .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();
        // 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
        uploadManager = new UploadManager(config);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_info_act_back:
                back();
                break;
            case R.id.user_info_act_layout_avatar:
                initAvatarDialog();
                break;
            case R.id.user_info_act_layout_user_name:
                Intent editUserNameIntent = new Intent(this, EditUserNameActivity.class);
                editUserNameIntent.putExtra("name",tvName.getText().toString().trim());
                startActivityForResult(editUserNameIntent, EDIT_USER_NAME);
                break;
            case R.id.user_info_act_layout_bind_mobile:
                ToastUtils.displayMsg("暂不支持，敬请期待",mContext);
                break;
            case R.id.user_info_act_layout_bind_weixin:
                // send oauth request
                if(!isBindWechat) {
                    if (!api.isWXAppInstalled()) {
                        //提醒用户没有按照微信
                        ToastUtils.displayMsg("请先安装微信客户端",UserInfoActivity.this);
                        return;
                    }
                    if(canBind) {
                        canBind=false;
                        SendAuth.Req req = new SendAuth.Req();
                        req.scope = "snsapi_userinfo";
                        req.state = "wechat_bind";
                        api.sendReq(req);
                    }
                }else{
                    //解绑操作
                    dialog = new CustomDialog(UserInfoActivity.this, new CustomDialog.onBtnClickListener() {
                        @Override
                        public void onSure() {
                            //解除绑定请求
                            unbind(1);
                            dialog.dismiss();
                        }
                        @Override
                        public void onExit() {
                            dialog.dismiss();
                        }
                    },"解除绑定","取消","解除绑定","确定要解除账号与微信的关联吗?");
                    dialog.show();
                }
                break;
            case R.id.user_info_act_layout_bind_qq:
                if(!isBindQQ) {
                    bindQQ();
                }else{
                    //解绑操作
                    dialog = new CustomDialog(UserInfoActivity.this, new CustomDialog.onBtnClickListener() {
                        @Override
                        public void onSure() {
                            //解除绑定请求
                            unbind(2);
                            dialog.dismiss();
                        }
                        @Override
                        public void onExit() {
                            dialog.dismiss();
                        }
                    },"解除绑定","取消","解除绑定","确定要解除账号与QQ的关联吗?");
                    dialog.show();
                }
                break;
            case R.id.user_info_act_layout_bind_weibo:
                ToastUtils.displayMsg("暂不支持，敬请期待",mContext);
                break;
            case R.id.user_info_act_layout_login_password:
                ToastUtils.displayMsg("敬请期待",mContext);
                break;
            case R.id.user_info_act_layout_pay_password:
                ToastUtils.displayMsg("敬请期待",mContext);
                break;
            case R.id.user_info_act_layout_pay_limit:
                ToastUtils.displayMsg("敬请期待",mContext);
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
        }
    }

    public void bindQQ() {
        if (!App.getmTencent().isSessionValid()) {
//            App.getmTencent().login(this, "all", loginListener);
            App.getmTencent().loginServerSide(this, "get_user_info,add_t", loginListener);
        }else{
            ToastUtils.displayMsg("请先安装QQ客户端", UserInfoActivity.this);
        }
    }

    private BaseUiListener loginListener = new BaseUiListener();

    /**
     * 选择从相册还是拍照获取头像
     */
    private void initAvatarDialog() {
        avatarDialog = new Dialog(this, R.style.fullDialog);
        RelativeLayout contentView = (RelativeLayout) View.inflate(this, R.layout.pick_or_take_photo_dialog, null);
        Button dialog_bt_pick_photo = (Button) contentView.findViewById(R.id.btn_pick_photo);
        Button dialog_bt_take_photo = (Button) contentView.findViewById(R.id.btn_take_photo);
        Button dialog_bt_cancel = (Button) contentView.findViewById(R.id.btn_cancel);
        dialog_bt_pick_photo.setOnClickListener(this);
        dialog_bt_take_photo.setOnClickListener(this);
        dialog_bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarDialog.dismiss();
            }
        });
        avatarDialog.setContentView(contentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        avatarDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == com.tencent.connect.common.Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = null;
        switch (requestCode) {
            case MY_TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    uri = Uri.fromFile(file);
                    startImageAction(uri, 120, 120, MY_CROP_PICTURE, true);
                }
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
                    startImageAction(uri, 120, 120, MY_CROP_PICTURE, true);
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
            case EDIT_USER_NAME:
                if(data != null && data.hasExtra("newName")){
                    tvName.setText(data.getStringExtra("newName"));
                }
                break;
            default:
                break;
        }
    }

    private void startImageAction(Uri uri, int outputX, int outputY,
                                  int requestCode, boolean isCrop) {
        Intent intent = null;
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
                getQiniuUploadToken();
            }
            if (bitmap != null&& bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    protected void getQiniuUploadToken() {
        Map<String,Object> map = new HashMap<>();
//        map.put("headerImg",path);
        VolleyOperater<GetQiNiuTokenModel> operater = new VolleyOperater<GetQiNiuTokenModel>(this);
        operater.doRequest(Constants.URL_GET_QINIU_TOKEN, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    GetQiNiuTokenModel getQiNiuTokenModel = (GetQiNiuTokenModel) obj;
                    uploadPicture(getQiNiuTokenModel.getValue().getToken(),getQiNiuTokenModel.getValue().getPath());
                }
            }
        }, GetQiNiuTokenModel.class);
    }

    protected void uploadPicture(String token,String path) {
        UploadOptions uploadOptions = new UploadOptions(null, null, false, new UpProgressHandler() {
            public void progress(String key, double percent) {
                MLog.i("qiniu:" + key + ": " + percent);
            }
        }, null);

        startUpload(token, mFile.getAbsolutePath(), uploadOptions,path);
    }

    protected void startUpload(String token, final String picturePath, UploadOptions uploadOptions, final String path) {
        uploadManager.put(picturePath, getImgUUID(picturePath), token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if(info.isOK()){
                    changeHeaderImage(path + key);
                    MLog.i("qiniu:" + info.toString());
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

    /**
     * 更改头像
     */
    private void changeHeaderImage(final String path) {
        Map<String,Object> map = new HashMap<>();
        map.put("headerImg",path);
        VolleyOperater<ChangeHeaderModel> operater = new VolleyOperater<ChangeHeaderModel>(this);
        operater.doRequest(Constants.URL_CHANGE_HEAD, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    ivAvatar.setImageBitmap(bitmap);
                    AppUserEntity userInfo = App.getUserInfo();
                    userInfo.setHeaderImg(path);
                    App.setIsUserInfoChange(true);
                }
            }
        }, ChangeHeaderModel.class);
    }

    //绑定情况显示
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BIND_STATE:
                    if (isBindPhone) {
                        tvMobile.setText("已绑定");
                        tvMobile.setTextColor(getResources().getColor(R.color.is_bind));
                    }else {
                        tvMobile.setText("未绑定");
                        tvMobile.setTextColor(getResources().getColor(R.color.is_unopened));
                    }
                    if (isBindWechat) {
                        tvWeiChat.setText("已绑定");
                        tvWeiChat.setTextColor(getResources().getColor(R.color.is_bind));
                    }else {
                        tvWeiChat.setText("未绑定");
                        tvWeiChat.setTextColor(getResources().getColor(R.color.is_unbind));
                    }
                    if (isBindQQ) {
                        tvQQ.setText("已绑定");
                        tvQQ.setTextColor(getResources().getColor(R.color.is_bind));
                    }else {
                        tvQQ.setText("未绑定");
                        tvQQ.setTextColor(getResources().getColor(R.color.is_unbind));
                    }
                    if (isBindWeibo) {
                        tvWeibo.setText("已绑定");
                        tvWeibo.setTextColor(getResources().getColor(R.color.is_bind));
                    }else {
                        tvWeibo.setText("未绑定");
                        tvWeibo.setTextColor(getResources().getColor(R.color.is_unopened));
                    }
                    break;
                case 0:
                    tvWeiChat.setText("已绑定");
                    tvWeiChat.setTextColor(getResources().getColor(R.color.is_bind));
                    break;
                case WECHAT_BIND_SUCCESS:
                    checkBindState();
                    break;
            }
        }
    };

    public void unbind(int type){
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        VolleyOperater<UnbindModel> operater = new VolleyOperater<>(UserInfoActivity.this);
        operater.doRequest(Constants.URL_UNBIND_THIRD_LOGIN, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed) {
                    UnbindModel unbindModel = (UnbindModel) obj;
                    switch (unbindModel.getValue()) {
                        case 1:
                            isBindWechat = false;
                            break;
                        case 2:
                            isBindQQ = false;
                            break;
                    }
                    handler.sendEmptyMessage(BIND_STATE);
                }
            }
        }, UnbindModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        canBind=true;
    }

    public void setIsBindWechat(boolean isBindSuccess,String msg){
        canBind=true;
        if(isBindSuccess)
            handler.sendEmptyMessage(WECHAT_BIND_SUCCESS);
        else {
            ToastUtils.displayMsg(msg,UserInfoActivity.this);
        }
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            MLog.i("onComplete:" + response.toString());
            ToastUtils.displayMsg("绑定成功", UserInfoActivity.this);
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject response) {
            String openId = response.optString("openid");
            bind(openId);
        }

        @Override
        public void onError(UiError e) {
            MLog.i("onError:"+"code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
            ToastUtils.displayMsg("绑定失败", UserInfoActivity.this);
        }

        @Override
        public void onCancel() {
            MLog.i("onCancel");
        }
    }

    private void bind(String openId) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", 2);
        map.put("token", openId);
        VolleyOperater<BindThirdLoginModel> operater = new VolleyOperater<>(UserInfoActivity.this);
        operater.doRequest(Constants.URL_BIND_THIRD_LOGIN, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed) {
                    isBindQQ = true;
                    handler.sendEmptyMessage(BIND_STATE);
                }
            }
        }, BindThirdLoginModel.class);
    }
}

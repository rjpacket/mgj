package com.project.mgjandroid.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.model.MerchantEvaluateTopModel.ValueEntity.ShareInfoEntity;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;

/**
 * Created by yuandi on 2016/4/25.
 */
public class ShareActivity extends BaseActivity implements View.OnClickListener {

    public static final String ACTION_SHARE_COMPLETE = "com.project.mgjandroid.share.complete";

    @InjectView(R.id.share_to_wechat)
    private LinearLayout shareToWechat;
    @InjectView(R.id.share_to_wx_circle)
    private LinearLayout shareToWXCircle;
    @InjectView(R.id.share_to_qq)
    private LinearLayout shareToQQ;
    @InjectView(R.id.share_to_qzone)
    private LinearLayout shareToQzone;
    @InjectView(R.id.tv_cancel)
    private TextView tvCancel;

    private String title = "";
    private String summary = "";
    private String tagUrl = "";
    private String imgUrl = "";

    private Receiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Injector.get(this).inject();
        setListener();
        initData();
        registerReceiver();
    }

    private void initData() {
        Merchant merchant = (Merchant) getIntent().getExtras().getSerializable("Merchant");
        Goods goods = (Goods) getIntent().getExtras().getSerializable("Goods");
        if (merchant != null){
            title = merchant.getName();
            ShareInfoEntity shareInfo = (ShareInfoEntity) getIntent().getExtras().getSerializable("shareInfo");
            if(shareInfo != null && !TextUtils.isEmpty(shareInfo.getUrl())) tagUrl = shareInfo.getUrl();
            if(shareInfo != null && !TextUtils.isEmpty(shareInfo.getMemo()) && !shareInfo.getMemo().equals(title)){
                summary = shareInfo.getMemo();
            }else{
                summary = String.format(getString(R.string.share_merchant), getString(R.string.app_name));
            }
            if(shareInfo != null && !TextUtils.isEmpty(shareInfo.getImg())){
                imgUrl = shareInfo.getImg();
            }else{
                imgUrl = merchant.getLogo();
            }
        } else if (goods != null) {
            title = goods.getName();
            summary = String.format(getString(R.string.share_goods), getIntent().getStringExtra("MerchantName") + goods.getName());
            imgUrl = goods.getImgs();
        }
    }

    private void setListener() {
        shareToWechat.setOnClickListener(this);
        shareToWXCircle.setOnClickListener(this);
        shareToQQ.setOnClickListener(this);
        shareToQzone.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_to_wechat :
                onClickShareToWechat(0, title, summary, tagUrl, imgUrl);
                break;

            case R.id.share_to_wx_circle :
                onClickShareToWechat(1, title, summary, tagUrl, imgUrl);
                break;

            case R.id.share_to_qq :
                onClickShareToQQ(title, summary, tagUrl, imgUrl);
                break;

            case R.id.share_to_qzone :
                onClickShareToQzone(title, summary, tagUrl, imgUrl);
                break;

            case R.id.tv_cancel :
                finish();
                break;
        }
    }

    /**
     * param flag 0 好友，1 朋友圈
     *
     */
    private void onClickShareToWechat(int flag, String title, String summary, String tagUrl, String imgUrl) {
        if (!App.getApi().isWXAppInstalled()) {
            ToastUtils.displayMsg("请先安装微信客户端", ShareActivity.this);
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = tagUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = title;
        msg.description = summary;
        Bitmap thumb = null;
        if(CheckUtils.isNoEmptyStr(imgUrl)){
            if(imgUrl.contains(";")){
                String[] strings = imgUrl.split(";");
                imgUrl = strings[0];
            }
            thumb = ImageUtils.downLoadImageUrl(imgUrl);
            if(ImageUtils.getBmpSize(thumb) > 32*1024) { //weixin要求图片小于32K
                thumb = ImageUtils.compressImg(thumb, 32);
            }
        }
        if (thumb == null) {
            thumb = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_launcher);
        }
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag;
        App.getApi().sendReq(req);
    }

    private void onClickShareToQQ(String title, String summary, String tagUrl, String imgUrl) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  tagUrl);
        if(CheckUtils.isNoEmptyStr(imgUrl) && imgUrl.contains(";")){
            String[] strings = imgUrl.split(";");
            imgUrl = strings[0];
        }
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getString(R.string.app_name));

        App.getmTencent().shareToQQ(mActivity, params, listener);
    }

    private void onClickShareToQzone(String title, String summary, String tagUrl, String imgUrl) {
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, tagUrl);
        if(CheckUtils.isNoEmptyStr(imgUrl) && imgUrl.contains(";")){
            String[] strings = imgUrl.split(";");
            imgUrl = strings[0];
        }
        ArrayList<String> imgUrls = new ArrayList<>();
        imgUrls.add(imgUrl);
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrls);

        App.getmTencent().shareToQzone(mActivity, params, listener);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_QQ_SHARE || requestCode == Constants.REQUEST_QZONE_SHARE){
            Tencent.onActivityResultData(requestCode, resultCode, data, listener);
        }
    }

    private BaseUiListener listener = new BaseUiListener();
    /**
     * QQ分享回调
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            MLog.i("onComplete:" + response.toString());
            ToastUtils.displayMsg("分享成功", ShareActivity.this);
            mActivity.finish();
        }

        @Override
        public void onError(UiError e) {
            MLog.i("onError:"+"code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
            ToastUtils.displayMsg("分享失败", ShareActivity.this);
        }

        @Override
        public void onCancel() {
            MLog.i("onCancel");
            mActivity.finish();
//            ToastUtils.displayMsg("分享已取消", ShareActivity.this);
        }
    }

    class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null && intent.getAction().equals(ACTION_SHARE_COMPLETE)){
                mActivity.finish();
            }
        }
    }

    private void registerReceiver() {
        receiver = new Receiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_SHARE_COMPLETE);
        mActivity.registerReceiver(receiver, filter);
    }

    private void unregisterReceiver() {
        try {
            if (mActivity != null && receiver != null) {
                mActivity.unregisterReceiver(receiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver();
        super.onDestroy();
    }
}

package com.horsegj.company.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.project.mgjandroid.base.App;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.BindThirdLoginModel;
import com.project.mgjandroid.model.SmsLoginModel;
import com.project.mgjandroid.model.WechatLoginModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.ShareActivity;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.activity.UserInfoActivity;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rjp on 2016/3/21.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        if(resp instanceof SendAuth.Resp){
            if (resp.errCode == BaseResp.ErrCode.ERR_OK) {//用户同意
                String operate = ((SendAuth.Resp) resp).state;
                code = ((SendAuth.Resp) resp).code;
                if ("login_by_weichat".equals(operate))
                    login();
                if ("wechat_bind".equals(operate))
                    bind();
            }else{
                this.finish();
            }
        } else if (resp instanceof SendMessageToWX.Resp) {
            String result;
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = "分享成功";
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "分享已取消";
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = "分享被拒绝 ";
                    break;
                default:
                    result = "分享失败";
                    break;
            }
            ToastUtils.displayMsg(result, WXEntryActivity.this);
            if("分享成功".equals(result)) {
                Intent intent = new Intent(ShareActivity.ACTION_SHARE_COMPLETE);
                sendBroadcast(intent);
            }
            finish();
        }
    }

    private void bind() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", 1);
        map.put("token", code);
        VolleyOperater<BindThirdLoginModel> operater = new VolleyOperater<BindThirdLoginModel>(WXEntryActivity.this);
        operater.doRequest(Constants.URL_BIND_THIRD_LOGIN, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed) {
                    //绑定微信和账号
                    if(obj instanceof String) {
                        UserInfoActivity.instance.setIsBindWechat(false, obj.toString());
                    }else{
                        UserInfoActivity.instance.setIsBindWechat(true,"success");
                    }
                }
                finish();
            }
        }, BindThirdLoginModel.class);
//        this.finish();
    }

    private void login() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", 1);
        map.put("token", code);
        VolleyOperater<WechatLoginModel> operater = new VolleyOperater<>(WXEntryActivity.this);
        operater.doRequest(Constants.URL_THIRD_PARTY_LOGIN, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed) {
                    WechatLoginModel wechatLoginModel = (WechatLoginModel) obj;
                    SmsLoginModel.ValueEntity.AppUserEntity value = wechatLoginModel.getValue();
                    App.setUserInfo(value);
                    App.setIsLogin(true);
                    PreferenceUtils.saveStringPreference("token", wechatLoginModel.getValue().getToken(), WXEntryActivity.this);
                    SmsLoginActivity.instance.thirdLoginSuccess(false);
                }
                finish();
            }
        }, WechatLoginModel.class);
//        this.finish();
    }

    private void handleIntent(Intent paramIntent) {
        WXAPIFactory.createWXAPI(this, Constants.WE_CHAT_APP_ID, false).handleIntent(paramIntent, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}

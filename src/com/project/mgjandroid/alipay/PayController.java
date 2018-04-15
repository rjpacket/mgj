package com.project.mgjandroid.alipay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.constants.Constants;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;

/**
 * Created by ning on 2016/3/25.
 */
public class PayController {
    public static final int SDK_PAY_FLAG = 1;
    private static IWXAPI api;

    public static void alipay(final Activity activity,final String payInfo,final Handler mHandler){
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public static void wechatPay(Context context,JSONObject json){
        api = WXAPIFactory.createWXAPI(context, Constants.WE_CHAT_APP_ID);
        PayReq req = new PayReq();
        //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
        req.appId = json.optString("appid");
        req.partnerId = json.optString("partnerid");
        req.prepayId = json.optString("prepayid");
        req.nonceStr = json.optString("noncestr");
        req.timeStamp = json.optString("timestamp");
        req.packageValue = json.optString("package");
        req.sign = json.optString("sign");
        req.extData = "app data"; // optional
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(req);
    }
}

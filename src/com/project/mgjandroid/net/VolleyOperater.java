package com.project.mgjandroid.net;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.model.ErrorModel;
import com.project.mgjandroid.model.SmsLoginModel;
import com.project.mgjandroid.net.volley.AuthFailureError;
import com.project.mgjandroid.net.volley.DefaultRetryPolicy;
import com.project.mgjandroid.net.volley.NetworkError;
import com.project.mgjandroid.net.volley.NetworkResponse;
import com.project.mgjandroid.net.volley.NoConnectionError;
import com.project.mgjandroid.net.volley.Request;
import com.project.mgjandroid.net.volley.RequestQueue;
import com.project.mgjandroid.net.volley.Response.ErrorListener;
import com.project.mgjandroid.net.volley.Response.Listener;
import com.project.mgjandroid.net.volley.ServerError;
import com.project.mgjandroid.net.volley.TimeoutError;
import com.project.mgjandroid.net.volley.VolleyError;
import com.project.mgjandroid.net.volley.toolbox.FastJsonObjectRequest;
import com.project.mgjandroid.net.volley.toolbox.Volley;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DeviceParameter;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.PreferenceUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyOperater<T> {
	private static final int TIME_OUT = 60000;
	private static final int RETRIE_TIMES = 2;
	private Context context;
	private ResponseListener listener;
	private String body;

	public VolleyOperater(Context context) {
		this.context = context;
	}

	public String setBody(Map<String, Object> mapParams) {

		Map<String, Object> map = new HashMap<String, Object>();
		SmsLoginModel.ValueEntity.AppUserEntity userInfo = App.getUserInfo();
		if(userInfo != null) {
			map.put("token", userInfo.getToken());
		}else{
			String token = PreferenceUtils.getStringPreference("token", "", context);
			if(!"".equals(token)) {
				map.put("token", token);
			}
		}
		map.put("app", context.getString(R.string.app_nick));
		map.put("uuid", DeviceParameter.getUUID());
		map.put("clientVersion", CommonUtils.getCurrentVersionName());
//		map.put("versionCode", CommonUtils.getCurrentVersionCode());
//		map.put("apiVersion", "1.0.0");
		map.put("ip", DeviceParameter.getIp());
		map.put("client", "android");
//		map.put("brand", DeviceParameter.getDeviceProduct());
		map.put("brand", "android");
		map.put("model", DeviceParameter.getDeviceModel());
		map.put("osVersion", DeviceParameter.getOSVersion());
		map.put("channel", getChannel());
		map.put("networkType", DeviceParameter.getNetWorkType());

		String localMacAddress = DeviceParameter.getLocalMacAddress(context);
		if(localMacAddress == null || "".equals(localMacAddress)){
			localMacAddress = DeviceParameter.getMacAddress();
		}
		if(localMacAddress == null || "".equals(localMacAddress)){
			localMacAddress = DeviceParameter.getLocalMacAddressFromIp(context);
		}
		map.put("mac", localMacAddress);
		map.put("imei", DeviceParameter.getIMEI());
		map.put("params", mapParams);
		MLog.d("----params---" + map.toString());
		return com.alibaba.fastjson.JSONObject.toJSONString(map);
	}

	/**
	 * 获得渠道号
	 */
	public String getChannel() {
		String channel_id = "horsegj";
		try {
			ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			channel_id = appInfo.metaData.getString("UMENG_CHANNEL");
		} catch (Exception e) {
			//
		}
		return channel_id;
	}

	public void doRequest(String url, Map<String, Object> mapJson, ResponseListener listener, Class<T> clazz) {
		this.listener = listener;
		this.body = setBody(mapJson);
		if (!CommonUtils.isNetworkConnected()) {
			listener.onRsp(false, "无网络连接，请检查网络");
		} else {
			FastJsonObjectRequest<T> req = new FastJsonObjectRequest<T>(url, body, clazz, new OperaterListener(), new OperaterErrorListener());
			DefaultRetryPolicy policy = new DefaultRetryPolicy(TIME_OUT, RETRIE_TIMES, Float.valueOf(1));
			req.setRetryPolicy(policy);
			OperateManager.getInstance(context).onReq(req);

		}
	}

	public void doRequest(String url, ResponseListener listener, Class<T> clazz) {
		this.listener = listener;
		if (!CommonUtils.isNetworkConnected()) {
			listener.onRsp(false, "无网络连接，请检查网络");
		} else {
			FastJsonObjectRequest<T> req = new FastJsonObjectRequest<T>(url, "", clazz, new OperaterListener(), new OperaterErrorListener());
			DefaultRetryPolicy policy = new DefaultRetryPolicy(TIME_OUT, RETRIE_TIMES, Float.valueOf(1));
			req.setRetryPolicy(policy);
			OperateManager.getInstance(context).onReq(req);

		}
	}

	public interface ResponseListener {
		public void onRsp(boolean isSucceed, Object obj);
	}

	public static class OperateManager {
		private static OperateManager manager = null;
		private RequestQueue queue;

		synchronized public static OperateManager getInstance(Context context) {
			if (manager == null) {
				manager = new OperateManager(context);
			}
			return manager;
		}

		private OperateManager(Context context) {
			queue = Volley.newRequestQueue(context);
			if (queue != null) {
				queue.cancelAll(10);
			}
		}

		synchronized public void onReq(Request<? extends Object> req) {
			queue.add(req);
		}

		public void onDestroy() {
			if (queue != null) {
				queue.cancelAll(10);
				queue.stop();
			}
			manager = null;
		}
	}

	private class OperaterErrorListener implements ErrorListener {
		@Override
		public void onErrorResponse(VolleyError error) {
			String errorMsg = VolleyErrorHelper.getMessage(error, context);
			listener.onRsp(false, errorMsg);
		}
	}

	private class OperaterListener implements Listener<T> {

		@Override
		public void onResponse(T response) {
			try {
				if (!JSONObject.NULL.equals(response)) {
					if(response instanceof ErrorModel){
						listener.onRsp(true, ((ErrorModel) response).getValue());
					}else {
						listener.onRsp(true, response);
					}
				} else {
					listener.onRsp(true, null);
				}
			} catch (Exception e) {
				e.printStackTrace();
				listener.onRsp(false, e.toString());
			}
		}
	}


	public static class VolleyErrorHelper {
		/**
		 * 
		 * @param error
		 * @param context
		 * @return
		 */
		public static String getMessage(Object error, Context context) {
			if (error instanceof TimeoutError) {
				return "TimeoutError";
			} else if (isServerProblem(error)) {
				return handleServerError(error, context);
			} else if (isNetworkProblem(error)) {
				return "NetworkError";
			}
			return error.toString();
		}

		/**
		 * 
		 * @param error
		 * @return
		 */
		private static boolean isNetworkProblem(Object error) {
			return (error instanceof NetworkError) || (error instanceof NoConnectionError);
		}

		/**
		 * 
		 * @param error
		 * @return
		 */
		private static boolean isServerProblem(Object error) {
			return (error instanceof ServerError) || (error instanceof AuthFailureError);
		}

		/**
		 * 
		 * @param err
		 * @param context
		 * @return
		 */
		private static String handleServerError(Object err, Context context) {
			VolleyError error = (VolleyError) err;
			NetworkResponse response = error.networkResponse;
			if (response != null) {
				return response.statusCode + "";
			}
			return null;
		}
	}

}

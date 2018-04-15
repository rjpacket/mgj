package com.project.mgjandroid.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import com.project.mgjandroid.base.App;
import com.project.mgjandroid.base.DeviceTypes;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;

import org.apache.http.conn.util.InetAddressUtils;

/**
 * 适配
 * 
 * @author Administrator
 */
public class DeviceParameter {
	private static DeviceTypes deviceType = null;
	private static String deviceId = null;
	private static float xdpi = -1f;

	public static final String NETWORKTYPE_INVALID = "null";
	/** wap网络 */
	public static final String NETWORKTYPE_WAP = "wap";
	/** 2G网络 */
	public static final String NETWORKTYPE_2G = "2G";
	/** 3G */
	public static final String NETWORKTYPE_3G = "3G";
	/** 4G */
	public static final String NETWORKTYPE_4G = "4G";
	/** wifi网络 */
	public static final String NETWORKTYPE_WIFI = "wifi";

	/**
	 * @author penghaitao.misa Date 2011-2-16
	 */
	public static DisplayMetrics getDisplayMetrics() {
		return App.getInstance().getResources().getDisplayMetrics();
	}

	public static void initDisplayHeight(Activity activity) {
		int statusBarHeight = 0;
		int height = 0;
		int width = 0;
		Window window = activity.getWindow();
		Class<?> c;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			Object obj = c.newInstance();
			Field field = c.getField("status_bar_height");
			int x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = activity.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		DisplayMetrics dm = getDisplayMetrics();
		height = dm.heightPixels;
		width = dm.widthPixels;

		// MLog.i("initDisplayHeight height=" + height + ",width=" + width);
		Rect rect = new Rect();
		window.getDecorView().getWindowVisibleDisplayFrame(rect);
		if (rect.top == 0 && getDeviceType() != DeviceTypes.NORMAL) {
			height = height + statusBarHeight;
		} else if (((rect.top + rect.bottom) < height) || ((rect.top + rect.bottom) > height && getDeviceType() == DeviceTypes.XLARGE)) {
			statusBarHeight += height - rect.bottom;
		}
		if (statusBarHeight <= 0) {
			if (isDeviceXLarge()) {
				statusBarHeight = 48;
			}
			height = height + statusBarHeight;
		}
		PreferenceUtils.saveStatusBarHeight(statusBarHeight, activity);
		if (DeviceParameter.isDeviceXLarge()) {
			if (height > width) {
				PreferenceUtils.saveIntPreference(PreferenceUtils.PHYSICAL_HEIGHT_XLARGE, height);
				PreferenceUtils.saveIntPreference(PreferenceUtils.PHYSICAL_WIDTH_XLARGE, width);
			} else {
				PreferenceUtils.saveIntPreference(PreferenceUtils.PHYSICAL_HEIGHT_XLARGE, width);
				PreferenceUtils.saveIntPreference(PreferenceUtils.PHYSICAL_WIDTH_XLARGE, height);
			}
		} else {
			if (height > width) {
				PreferenceUtils.saveIntPreference(PreferenceUtils.PHYSICAL_HEIGHT, height);
				PreferenceUtils.saveIntPreference(PreferenceUtils.PHYSICAL_WIDTH, width);
			} else {
				PreferenceUtils.saveIntPreference(PreferenceUtils.PHYSICAL_HEIGHT, width);
				PreferenceUtils.saveIntPreference(PreferenceUtils.PHYSICAL_WIDTH, height);
			}
		}
	}

	public static float getXDPI() {
		if (xdpi == -1f) {
			DisplayMetrics dm = getDisplayMetrics();
			xdpi = dm.xdpi;
		}
		return xdpi;
	}

	public static int getStatusBarHeight(Context context) {
		return PreferenceUtils.getStatusBarHeight(context);
	}

	public static DeviceTypes getDeviceType() {
		if (deviceType != null) {
			return deviceType;
		}
		App appContext = App.getInstance();
		if ((appContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
			deviceType = DeviceTypes.XLARGE;
		} else if ((appContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
			deviceType = DeviceTypes.LARGE;
		} else {
			deviceType = DeviceTypes.NORMAL;
		}
		return deviceType;
	}

	// api调用devicetype参数
	public static int getDeviceTypeInt() {
		if (getDeviceType() == DeviceTypes.XLARGE) {
			return 2;
		} else {
			return 5;
		}
	}

	public static int getDisplayWidth(Context context, int orientation) {
		int width = 0;
		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			width = getPhysicalWidth(context);
		} else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
			width = getPhysicalHeight(context);
		}
		return width;
	}

	public static int getDisplayHeight(Context context, int orientation) {
		int height = 0;
		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			height = getPhysicalHeight(context) - getStatusBarHeight(context);
		} else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
			height = getPhysicalWidth(context) - getStatusBarHeight(context);
		}
		return height;
	}

	public static int getPhysicalWidth(Context context) {
		if (DeviceParameter.isDeviceXLarge()) {
			return PreferenceUtils.getIntPreference(PreferenceUtils.PHYSICAL_WIDTH_XLARGE, 0, context);
		} else {
			return PreferenceUtils.getIntPreference(PreferenceUtils.PHYSICAL_WIDTH, 0, context);
		}
	}

	public static int getPhysicalHeight(Context context) {
		if (DeviceParameter.isDeviceXLarge()) {
			return PreferenceUtils.getIntPreference(PreferenceUtils.PHYSICAL_HEIGHT_XLARGE, 0, context);
		} else {
			return PreferenceUtils.getIntPreference(PreferenceUtils.PHYSICAL_HEIGHT, 0, context);
		}
	}

	public static float getScreenHeight() {
		DisplayMetrics dm = getDisplayMetrics();
		return dm.heightPixels;
	}

	public static float getScreenWidth() {
		DisplayMetrics dm = getDisplayMetrics();
		return dm.widthPixels;
	}

	public static int getIntScreenWidth() {
		DisplayMetrics dm = getDisplayMetrics();
		return dm.widthPixels;
	}

	public static float getDensity() {
		DisplayMetrics dm = getDisplayMetrics();
		return dm.density;
	}

	/**
	 * 获取手机唯一标识
	 * 
	 * @param appContext
	 * @return
	 */
	public static String getDeviceId() {
		if (deviceId != null) {
			return deviceId;
		}
		Context appContext = App.getInstance();
		TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
		deviceId = telephonyManager.getDeviceId();
		if (TextUtils.isEmpty(deviceId)) {
			deviceId = System.getString(appContext.getContentResolver(), Secure.ANDROID_ID);
		}
		return deviceId;
	}

	public static String getAndroid_ID() {
		return System.getString(App.getInstance().getContentResolver(), Secure.ANDROID_ID);
	}

	public static String getIMEI() {
		Context appContext = App.getInstance();
		return ((TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}

	public static String getIMSI() {
		Context appContext = App.getInstance();
		TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getSubscriberId();
	}

	public static boolean isDeviceXLarge() {
		return getDeviceType() == DeviceTypes.XLARGE;
	}

	/**
	 * 获取sim卡运营商名称（SP）
	 * 
	 * @param context
	 * @return
	 */
	public static String getProvidersName() {
		Context appContext = App.getInstance();
		TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
		String ProvidersName = null;
		// 返回唯一的用户ID;就是这张卡的编号神马的
		String IMSI = telephonyManager.getSubscriberId();
		// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。

		if (IMSI != null) {
			if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
				ProvidersName = "中国移动";
			} else if (IMSI.startsWith("46001")) {
				ProvidersName = "中国联通";
			} else if (IMSI.startsWith("46003")) {
				ProvidersName = "中国电信";
			}
		} else {
			ProvidersName = "";
		}

		return ProvidersName;
	}

	/*
  * 
  */
	public static String getNetWorkType() {
		Context appContext = App.getInstance();
		ConnectivityManager cm = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm != null) {
			NetworkInfo info = cm.getActiveNetworkInfo();
			if (info != null) {
				int networkInfoType = info.getType();
				if (networkInfoType == ConnectivityManager.TYPE_WIFI || networkInfoType == ConnectivityManager.TYPE_ETHERNET) {
					return "wifi";
				} else if (networkInfoType == ConnectivityManager.TYPE_MOBILE) {
					return "mobile";
				}

			}

		}
		return "wifi";
	}

	public static String getIp() {
		String ip = "";
		Context appContext = App.getInstance();
		if (getNetWorkType().equals("wifi")) {
			WifiManager wifiManager = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);
			// 判断wifi是否开启
			if (!wifiManager.isWifiEnabled()) {
				wifiManager.setWifiEnabled(true);
			}
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			int ipAddress = wifiInfo.getIpAddress();
			ip = intToIp(ipAddress);
		} else if (getNetWorkType().equals("mobile")) {
			try {
				for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
					NetworkInterface intf = en.nextElement();
					for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
						InetAddress inetAddress = enumIpAddr.nextElement();
						if (!inetAddress.isLoopbackAddress()) {
							ip = inetAddress.getHostAddress().toString();
						}
					}
				}
			} catch (SocketException ex) {
				return null;
			}
		}

		return ip;
	}

	private static String intToIp(int i) {

		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
	}

	public static String getNetWorkType(Context context) {
		try {
			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = manager.getActiveNetworkInfo();

			String mNetWorkType = NETWORKTYPE_INVALID;
			if (networkInfo != null && networkInfo.isConnected()) {
				String type = networkInfo.getTypeName();
				if (type.equalsIgnoreCase("WIFI")) {
					mNetWorkType = NETWORKTYPE_WIFI;
				} else if (type.equalsIgnoreCase("MOBILE")) {
					mNetWorkType = mobileNetworkType(context);
				}
			} else {
				mNetWorkType = NETWORKTYPE_INVALID;
			}

			return mNetWorkType;
		} catch (Exception e) {
		}
		return NETWORKTYPE_INVALID;
	}

	private static String mobileNetworkType(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		switch (telephonyManager.getNetworkType()) {
		case TelephonyManager.NETWORK_TYPE_1xRTT:
			return NETWORKTYPE_2G;// ~ 50-100 kbps
		case TelephonyManager.NETWORK_TYPE_CDMA:
			return NETWORKTYPE_2G;// ~ 14-64 kbps
		case TelephonyManager.NETWORK_TYPE_EDGE:
			return NETWORKTYPE_2G;// ~ 50-100 kbps
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
			return NETWORKTYPE_3G;// ~ 400-1000 kbps
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
			return NETWORKTYPE_3G;// ~ 600-1400 kbps
		case TelephonyManager.NETWORK_TYPE_GPRS:
			return NETWORKTYPE_2G;// ~ 100 kbps
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			return NETWORKTYPE_3G;// ~ 2-14 Mbps
		case TelephonyManager.NETWORK_TYPE_HSPA:
			return NETWORKTYPE_3G;// ~ 700-1700 kbps
		case TelephonyManager.NETWORK_TYPE_HSUPA:
			return NETWORKTYPE_3G;// ~ 1-23 Mbps
		case TelephonyManager.NETWORK_TYPE_UMTS:
			return NETWORKTYPE_3G;// ~ 400-7000 kbps
		case TelephonyManager.NETWORK_TYPE_EHRPD:
			return NETWORKTYPE_3G;// ~ 1-2 Mbps
		case TelephonyManager.NETWORK_TYPE_EVDO_B:
			return NETWORKTYPE_3G;// ~ 5 Mbps
		case TelephonyManager.NETWORK_TYPE_HSPAP:
			return NETWORKTYPE_3G;// ~ 10-20 Mbps
		case TelephonyManager.NETWORK_TYPE_IDEN:
			return NETWORKTYPE_2G;// ~25 kbps
		case TelephonyManager.NETWORK_TYPE_LTE:
			return NETWORKTYPE_4G;// ~ 10+ Mbps
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
			return NETWORKTYPE_INVALID;
		default:
			return NETWORKTYPE_INVALID;
		}
	}

	/**
	 * 获取手机系统版本号
	 * 
	 * @return
	 */
	public static String getOSVersion() {
		String version = "0";
		try {
			version = android.os.Build.VERSION.RELEASE;
		} catch (Exception e) {
		}
		return version;
	}

	// public static LocationClient initCurrentLocation(final Context context,
	// final LocationListener listener) {
	//
	// final String latitudeStr =
	// PreferenceUtils.getStringPreference("latitude", "4.9E-324", context);
	// final String longitudeStr =
	// PreferenceUtils.getStringPreference("longitude", "4.9E-324", context);
	// LocationClient locationClient = null;
	// final int UPDATE_TIME = 5000;
	// locationClient = new LocationClient(context);
	//
	// if (latitudeStr.length() > 0 && longitudeStr.length() > 0 &&
	// !latitudeStr.equals("4.9E-324")) {
	// listener.success(latitudeStr, longitudeStr);
	// return locationClient;
	// }
	//
	// // 设置定位条件
	// LocationClientOption option = new LocationClientOption();
	// option.setOpenGps(true);
	// option.setCoorType("bd09ll");
	// option.setPriority(LocationClientOption.NetWorkFirst);
	// option.setProdName("Location");
	// option.setScanSpan(UPDATE_TIME);
	// locationClient.setLocOption(option);
	//
	// locationClient.registerLocationListener(new BDLocationListener() {
	//
	// @Override
	// public void onReceiveLocation(BDLocation location) {
	// if (location == null) {
	// return;
	// }
	//
	// String latitude = String.valueOf(location.getLatitude()); // 精度
	// String longitude = String.valueOf(location.getLongitude()); // 维度
	//
	// if (latitude.length() > 0 && longitude.length() > 0 &&
	// !latitude.equals("4.9E-324")) {
	//
	// PreferenceUtils.saveStringPreference("latitude", latitude, context);
	// PreferenceUtils.saveStringPreference("longitude", longitude, context);
	// listener.success(latitude, longitude);
	//
	// if (App.getInstance().getLocaClient() != null) {
	// destoryLocation(App.getInstance().getLocaClient());
	//
	// return;
	// }
	// }
	//
	// Log.d("wdp", "latitude" + latitude + "_" + longitude);
	// }
	//
	// @Override
	// public void onReceivePoi(BDLocation location) {
	// }
	//
	// });
	//
	// locationClient.start();
	// locationClient.requestLocation();
	//
	// return locationClient;
	// }

	// public static void destoryLocation(LocationClient locationClient) {
	//
	// if (locationClient != null) {
	// locationClient.stop();
	// locationClient = null;
	// }
	//
	// }

	// public interface LocationListener {
	//
	// public void success(String latitude, String longitude);
	// }

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static float sp2px(Context context, float sp) {
		final float scale = context.getResources().getDisplayMetrics().scaledDensity;
		return sp * scale;
	}

	/**
	 * 获取手机型号
	 * 
	 * @return
	 */
	public static String getDeviceModel() {
		try {
			return encodeURL(android.os.Build.MODEL);
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 获取手机厂商
	 * 
	 * @return
	 */
	public static String getDeviceProduct() {
		try {
			return encodeURL(android.os.Build.MANUFACTURER);
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * URL编码
	 * 
	 * @param url
	 */
	public static String encodeURL(String url) {
		if (null == url) {
			return "";
		}
		try {
			url = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return url;
	}

	/**
	 * 获取sim卡ISOCC码
	 * 
	 * @param context
	 * @return
	 */
	public static String getIsoCCName(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		// 返回唯一的用户ID;就是这张卡的编号神马的
		String iso = telephonyManager.getSimCountryIso();

		if (isNoEmptyStr(iso)) {
			try {
				return iso;
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		return "";
	}

	/**
	 * 获取手机的mac
	 * 
	 * @return
	 */
	public static String getLocalMacAddress(Context appContext) {

		WifiManager wifi = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}

	//根据IP获取本地Mac
	public static String getLocalMacAddressFromIp(Context context) {
		String mac_s= "";
		try {
			byte[] mac;
			NetworkInterface ne=NetworkInterface.getByInetAddress(InetAddress.getByName(getLocalIpAddress()));
			mac = ne.getHardwareAddress();
			mac_s = byte2hex(mac);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mac_s;
	}

	public static String getMacAddress() {
		String result = "";
		String Mac = "";
		result = callCmd("busybox ifconfig", "HWaddr");

		if (result == null) {
			return "网络出错，请检查网络";
		}
		if (result.length() > 0 && result.contains("HWaddr")) {
			Mac = result.substring(result.indexOf("HWaddr") + 6, result.length() - 1);
			if (Mac.length() > 1) {
				result = Mac.toLowerCase();
			}
		}
		return result.trim();
	}

	public static String callCmd(String cmd,String filter) {
		String result = "";
		String line = "";
		try {
			Process proc = Runtime.getRuntime().exec(cmd);
			InputStreamReader is = new InputStreamReader(proc.getInputStream());
			BufferedReader br = new BufferedReader (is);

//执行命令cmd，只取结果中含有filter的这一行
			while ((line = br.readLine ()) != null && line.contains(filter)== false) {
//result += line;
			}
			result = line;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//获取本地IP
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
		}
		return null;
	}

	public static  String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer(b.length);
		String stmp = "";
		int len = b.length;
		for (int n = 0; n < len; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs = hs.append("0").append(stmp);
			else {
				hs = hs.append(stmp);
			}
		}
		return String.valueOf(hs);
	}

	/**
	 * 获取mac地址
	 */
	public static String getMacAddress(String interfaceName) {
		try {
			List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				if (interfaceName != null) {
					if (!intf.getName().equalsIgnoreCase(interfaceName))
						continue;
				}
				byte[] mac = intf.getHardwareAddress();
				if (mac == null)
					return "";
				StringBuilder buf = new StringBuilder();
				for (int idx = 0; idx < mac.length; idx++)
					buf.append(String.format("%02X:", mac[idx]));
				if (buf.length() > 0)
					buf.deleteCharAt(buf.length() - 1);
				return buf.toString();
			}
		} catch (Exception e) {
		} // for now eat exceptions
		return "";
	}


	/**
	 * 获取手机MCC码
	 * 
	 * @param appContext
	 * @return
	 */
	public static String getMCCName(Context appContext) {
		TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
		// 返回唯一的用户ID;就是这张卡的编号神马的
		String IMSI = telephonyManager.getSubscriberId();
		// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。

		if (IMSI != null && IMSI.length() > 0) {
			try {
				return IMSI.substring(0, 3);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		return "";
	}

	/**
	 * 获取手机MNC码
	 * 
	 * @param appContext
	 * @return
	 */
	public static String getMNCName(Context appContext) {
		TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
		// 返回唯一的用户ID;就是这张卡的编号神马的
		String IMSI = telephonyManager.getSubscriberId();
		// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。

		if (IMSI != null && IMSI.length() > 0) {
			try {
				return IMSI.substring(3, 5);
			} catch (Exception e) {
				// TODO: handle exception

			}

		}

		return "";
	}

	public static boolean isNoEmptyStr(String str) {
		return !isEmptyStr(str);
	}

	public static boolean isEmptyStr(String str) {
		if (TextUtils.isEmpty(str)) {
			return true;
		}
		// 过滤空格
		str = str.trim();
		if (str.equals(" ") || str.equals("") || "NULL".equals(str) || "null".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @author Johnson
	 * 获取手机唯一标示
	 * 
	 * */
	public static String getUUID() {
		String UUID = getIMEI();
		if(isUUIDValid(UUID)){
			return UUID;
		}else{
			
			UUID = getLocalMacAddress(App.getInstance());	
			if(isUUIDValid(UUID)){
				return MD5Util.getMD5(UUID.getBytes());
			}else{
				return CreateUUID();
			}
		}
//		if (CheckUtils.isNoEmptyStr(UUID)) {
//			return UUID;
//		} else {
//			UUID = getLocalMacAddress(App.getInstance());
//			if (CheckUtils.isNoEmptyStr(UUID)) {
//				return MD5Util.getMD5(UUID.getBytes());
//			} else {
//				return CreateUUID();
//			}
//		}
		// return CreateUUID();
	}

	
	/**
	 * 
	 * @author Johnson
	 * 创建UUID
	 * 
	 * */
	public static String CreateUUID() {
		String uuidStr = null;
		try {
			uuidStr = PreferenceUtils.getStringPreference(PreferenceUtils.UUID, "", App.getInstance());
			if (CheckUtils.isEmptyStr(uuidStr)) {
				uuidStr = "S_" + UUID.randomUUID().toString().replaceAll("-", "");
				PreferenceUtils.saveStringPreference(PreferenceUtils.UUID, uuidStr, App.getInstance());// 存时间戳
			}
		} catch (Exception e) {
			// TODO: handle exception
			MLog.printStackTrace(e);
		}
		return uuidStr;
	}
	
	
	/**
	 * 
	 * @author Johnson
	 * 判断串号是否相同数字
	 * 
	 * */
	public static boolean equalStr(String numOrStr){
		boolean flag = true;
		char str = numOrStr.charAt(0);
		for (int i = 0; i < numOrStr.length(); i++) {
		if (str != numOrStr.charAt(i)) {
		flag = false;
		break;
		}
		}
		return flag;
		}
	
	
	public static boolean isUUIDValid(String UUID){
		if (CheckUtils.isEmptyStr(UUID)) {
			return false;
		}
	
		if (UUID.equals("Unknown")) {
			return false;
		}
		
		if (equalStr(UUID)) {
			return false;
		}
		return true;
	}

}

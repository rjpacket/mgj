package com.project.mgjandroid.manager;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.project.mgjandroid.ui.listener.MyLocationListener;

/**
 * 定位Manager
 * @author jian
 *
 */
public class LocationManager {
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener;
	private static class SingletonHolder {
		static final LocationManager INSTANCE = new LocationManager();
	}

	public static LocationManager getIManager() {
		return SingletonHolder.INSTANCE;
	}

	public LocationManager() {

	}
	public void registeLocationAuto(Context context) {
		mLocationClient = new LocationClient(context); // 声明LocationClient类
		myListener = new MyLocationListener(context);
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		initLocation();
		mLocationClient.start();
	}
	
	public void registeLocation(Context context, BDLocationListener listener) {
		mLocationClient = new LocationClient(context); // 声明LocationClient类
		myListener = new MyLocationListener(context);
		mLocationClient.registerLocationListener(listener); // 注册监听函数
		initLocation();
		mLocationClient.start();
	}
	
	
	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 0;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(false);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(false);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(false);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(true);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
	}
	
	public void stopLocation() {
		if(mLocationClient != null) {
			mLocationClient.stop();
		}
	}
}

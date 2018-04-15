package com.project.mgjandroid.ui.listener;


import java.util.List;

import android.content.Context;
import android.content.Intent;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.project.mgjandroid.broadcast.LocationBroadCast;
import com.project.mgjandroid.manager.LocationManager;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.PreferenceUtils;

public class MyLocationListener implements BDLocationListener {
	private Context context;
	public final static String STATE = "state";
	public MyLocationListener(Context context) {
		this.context = context;
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		Intent intent = new Intent(LocationBroadCast.ACTION);
		if (location != null) {
			PreferenceUtils.saveLocation(location.getLatitude() + "", location.getLongitude() + "", context);
			PreferenceUtils.saveAddressName(location.getAddrStr(), context);
			
			if(CheckUtils.isNoEmptyList(location.getPoiList())) {
				List<Poi> list = location.getPoiList();
				PreferenceUtils.saveAddressDes(list.get(0).getName(), context);
			}
			context.sendStickyBroadcast(intent);
		}
		LocationManager.getIManager().stopLocation();
	}
}

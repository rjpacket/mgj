package com.project.mgjandroid.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 定位broadcast
 * 
 * @author jian
 * 
 */
public class LocationBroadCast extends BroadcastReceiver {
	public static final String ACTION = "com.project.mgjandroid.broadcast.LOCATION";
	private ReceiveListener listener;

	public LocationBroadCast(ReceiveListener listener) {
		this.listener = listener;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		listener.onReceiveListener();
	}

	public interface ReceiveListener {
		public void onReceiveListener();
	}

}

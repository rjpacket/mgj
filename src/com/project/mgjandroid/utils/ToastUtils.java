package com.project.mgjandroid.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 自定义Toast
 * 
 * @author jian
 * 
 */
public class ToastUtils {
	private static Toast mToast;

	// 显示提示消息
	public static void displayMsg(int resId, Context context) {
		Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
	}

	public static void displayMsg(String msg, Context context) {
		if(mToast == null) {
			mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		}else{
			mToast.setText(msg);
		}
		mToast.show();
	}
}

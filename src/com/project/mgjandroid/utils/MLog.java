package com.project.mgjandroid.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.project.mgjandroid.BuildConfig;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/***
 * @author jian
 * 
 *  处理app的log输出
 * */

public final class MLog {
	
	
	 /* log开关 */
	public static boolean DEBUG = true;
	/* log的tag */
	public static final String TAG = "MGJ";
	/* 是否用AlertDialog输出log */
	public static final boolean LOG_ALERT = true;
	/* 是否用toast输出log */
	public static final boolean LOG_TOAST = true;
	public static final boolean ISSERVERLOG = true;


	static {
		if (BuildConfig.DEBUG) {
			DEBUG = true;
		} else {
			DEBUG = false;
		}
	}

	private MLog() {
	}

	/********************** Custom *******************/

	public static void s(String msg) {
		if (DEBUG && !isEmpty(msg)) {
			System.out.println(msg);
		}
	}

	public static void printStackTrace(Throwable e) {
		if (DEBUG) {
			e.printStackTrace();
		}
	}

	public static void t(Context mContext, String msg) {
		if (DEBUG && LOG_TOAST && !isEmpty(msg)) {
			Toast.makeText(mContext, msg, 3 * 1000).show();
		}
	}

	public static void toast(Context mContext, String msg) {
		if (!isEmpty(msg)) {
			Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
		}
	}


	private static String cutLastChar(String str) {
		if (TextUtils.isEmpty(str)) {
			return "";
		} else {
			return str.substring(0, str.length() - 1);
		}
	}

	public static String urlPost2Get(String url, Map<String, String> map) {
		String getUrl = url + "?";
		Set<String> set = map.keySet();
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = map.get(key);
			String item = key + "=" + value + "&";
			getUrl += item;
		}
		getUrl = cutLastChar(getUrl);
		return getUrl;
	}


	public static void showRrequestParam(String url, Map<String, String> map) {
		if (DEBUG && !isEmpty(url)) {
			s("Url::" + url);
			if (map != null) {
				s("Param------");
				Set<String> set = map.keySet();
				Iterator<String> iterator = set.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					String value = map.get(key);
					s(key + "=" + value);
				}
			}

		}

	}

	public static void list(String pre, List list) {
		if (list == null) {
			i(pre + "list is null!");
			return;
		}
		String message = "";
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			Object object = iterator.next();
			message += object.toString() + ",";
		}
		message = cutLastChar(pre + message);
		i(message);
	}

	public static void map(String pre, Map map) {
		if (map == null) {
			i(pre + "map is null!");
			return;
		}
		String message = "";
		Set<String> set = map.keySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			message += "key=" + key.toString() + "," + map.get(key);
		}
		message = cutLastChar(pre + message);
		i(message);
	}

	public static void dialog(Context mContext, String msg) {
		if (DEBUG && LOG_ALERT && !isEmpty(msg)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			ScrollView s = new ScrollView(mContext);
			TextView t = new TextView(mContext);
			t.setText(msg);
			s.addView(t);
			builder.setView(s);
			builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			builder.create().show();
		}
	}

	public static String getStackTraceString(Throwable tr) {
		if (tr == null) {
			return "";
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		tr.printStackTrace(pw);
		return sw.toString();
	}

	public static void Logger(String msg) {
		if ((ISSERVERLOG || DEBUG) && !TextUtils.isEmpty(msg)) {
			Log.v(TAG, msg);
		}
	}

	/********************** Default *******************/
	public static void v(String tag, String msg) {
		if (DEBUG && !isEmpty(msg)) {
			Log.v(tag, msg);
		}
	}

	public static void v(String msg) {
		if (DEBUG && !isEmpty(msg)) {
			Log.v(TAG, msg);
		}
	}

	public static void v(String msg, Throwable tr) {
		if (DEBUG && !isEmpty(msg)) {
			Log.v(TAG, msg, tr);
		}
	}

	public static void d(String msg) {
		if (DEBUG && !isEmpty(msg)) {
			Log.d(TAG, msg);
		}
	}

	public static void d(String msg, Throwable tr) {
		if (DEBUG && !isEmpty(msg)) {
			Log.d(TAG, msg, tr);
		}
	}

	public static void i(String msg) {
		if (DEBUG && !isEmpty(msg)) {
			Log.i(TAG, msg);
		}
	}

	public static void i(String msg, Throwable tr) {
		if (DEBUG && !isEmpty(msg)) {
			Log.i(TAG, msg, tr);
		}
	}

	public static void w(String msg) {
		if (DEBUG && !isEmpty(msg)) {
			Log.w(TAG, msg);
		}
	}

	public static void w(String tag, String msg, Throwable tr) {
		if (DEBUG && !isEmpty(msg)) {
			Log.w(TAG, msg, tr);
		}
	}

	public static void w(String msg, Throwable tr) {
		if (DEBUG && !isEmpty(msg)) {
			Log.w(TAG, msg, tr);
		}
	}

	public static void w(Throwable tr) {
		if (DEBUG) {
			Log.w(TAG, tr);
		}
	}

	public static void e(String msg) {
		if (DEBUG && !isEmpty(msg)) {
			Log.e(TAG, msg);
		}
	}

	public static void e(String msg, Throwable tr) {
		if (DEBUG && !isEmpty(msg)) {
			Log.e(TAG, msg, tr);
		}
	}

	public static boolean isEmpty(String str) {
		if (TextUtils.isEmpty(str)) {
			return true;
		}
		if (str.equals(" ") || str.trim().equals("")) {
			return true;
		}
		return false;
	}

}

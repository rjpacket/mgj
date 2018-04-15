package com.project.mgjandroid.utils;

import com.project.mgjandroid.base.App;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class IntentUtils {

	public static void callTelephone(String address) {
		Uri uri = Uri.parse("tel:" + address);
		Intent it = new Intent(Intent.ACTION_DIAL, uri);
		App.getInstance().startActivity(it);
	}

	public static void sendEmail(String shareContent, final Activity context) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.putExtra(Intent.EXTRA_SUBJECT, "分享文字");
		i.putExtra(Intent.EXTRA_TEXT, shareContent);
		i.setType("application/xhtml+xml");
		try {
			context.startActivity(Intent.createChooser(i, "全文分享"));
		} catch (ActivityNotFoundException e) {
		}
	}

	public static void callSystemBrowser(String url, String title, Context context) {
		try {
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		} catch (Exception e) {
			return;
		}
	}

	public static void callBrowserDownload(String url, Context context) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));

		Intent chooser = Intent.createChooser(i, "下载应用:" + "");
		context.startActivity(chooser);
	}

	// 显示提示消息
	public static void displayMsg(int resId, Context context) {
		Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
	}

	public static void displayMsg(String msg, Context context) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static void galleryAddPic(Context context, Uri contentUri) {
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		mediaScanIntent.setData(contentUri);
		context.sendBroadcast(mediaScanIntent);
	}

}

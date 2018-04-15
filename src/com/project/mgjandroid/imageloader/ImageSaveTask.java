package com.project.mgjandroid.imageloader;

import java.io.File;

import com.project.mgjandroid.R;
import com.project.mgjandroid.base.FileCache;
import com.project.mgjandroid.base.ImageCache;
import com.project.mgjandroid.net.HttpBot;
import com.project.mgjandroid.utils.IntentUtils;
import com.project.mgjandroid.utils.StreamUtils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

/**
 * 图片下载Task
 * 
 * @author gaoj
 */
public class ImageSaveTask extends AsyncTask<String, Integer, String> {
	private String url;
	private Context context;

	private OnFinishListener onFinishListener;

	public interface OnFinishListener {
		public void onFinish(String path);
		
		
	}

	public void setOnFinishListener(OnFinishListener onFinishListener) {
		this.onFinishListener = onFinishListener;
	}

	public ImageSaveTask(String url, Context context, OnFinishListener onFinishListener) {
		this.url = url;
		this.context = context;
		this.onFinishListener = onFinishListener;
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected String doInBackground(String... params) {

		String filename = System.currentTimeMillis() + ".jpg";
		String path = FileCache.getSystemCameraDirectory();

		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File file = new File(path, filename);
		boolean flag = false;// 是否原图下载成功
		flag = HttpBot.getInstance().downloadImage(url);
		File file_tmp;
		if (!flag) {// 本地缓存 代理图片
			file_tmp = ImageCache.getInstance().getFileForKey(params[0]);
		} else {// 原图片
			file_tmp = ImageCache.getInstance().getFileForKey(url);
		}

		if (file_tmp.exists() && file_tmp.length() > 0) {
			StreamUtils.copyFileToAimFile(file_tmp, file);
		}
		if (file.exists()) {
			IntentUtils.galleryAddPic(context, Uri.fromFile(file));
		} else {
			return null;
		}
		return path;
	}

	@Override
	protected void onPostExecute(String path) {
		if (onFinishListener != null) {
			onFinishListener.onFinish(path);
		}
		if (path == null) {
			IntentUtils.displayMsg(context.getString(R.string.picture_save_fail), context);
		} else {
			IntentUtils.displayMsg(context.getString(R.string.picture_save_success), context);
		}
	}
}
package com.project.mgjandroid.base;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;

import android.content.Context;
import android.os.Environment;

/**
 * 
 * 文件缓存
 * 
 * @author Administrator
 */
public class FileCache {

	static {
		mkDirs(getAppCacheDirectory());
		mkDirs(getAppImageCacheDirectory());
		mkDirs(getAppImageDownloadDirectory());
		mkDirs(getImageDirectory());
		mkDirs(getAppCollectCacheDirectory());
	}

	public static void mkDirs(String dirPath) {
		File file = new File(dirPath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static String getRootDirectory() {
		Context context = App.getInstance();
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			final String cacheDir = "/Android/data/" + context.getPackageName()
					+ "/cache/";
			return Environment.getExternalStorageDirectory().getPath()
					+ cacheDir;
		} else {
			return context.getCacheDir() + "";
		}
	}

	/** 应用 缓存'根'目录 */
	public static String getAppCacheDirectory() {
		return getRootDirectory() + "data/";
	}

	/** 图片 缓存目录 */
	public static String getAppImageCacheDirectory() {
		return getAppCacheDirectory() + "image_cache/";
	}

	/** 图片 下载目录 */
	public static String getAppImageDownloadDirectory() {
		return getAppCacheDirectory() + "image_download/";
	}

	/** 数据 缓存目录 */
	public static String getAppDataCacheDirectory() {
		return getAppCacheDirectory() + "data_cache/";
	}

	/** 频道 缓存目录（清除缓存时不可以清除频道数据） */
	public static String getAppChannelCacheDirectory() {
		return getRootDirectory() + "channel_cache_2";
	}

	/** 收藏 缓存目录 （清除缓存时不可以清除收藏数据） */
	public static String getAppCollectCacheDirectory() {
		return getRootDirectory() + "collect_cache";
	}

	/** 收藏 评论目录 （清除缓存时不可以清除评论数据） */
	public static String getAppMyCommentCacheDirectory() {
		return getRootDirectory() + "myComment_cache";
	}

	public static String getAppSubscribeCacheDirectory() {

		return getRootDirectory() + "subscribe_cache2";
	}

	public static String getAppCollect1CacheDirectory() {

		return getRootDirectory() + "subscribe_cache";
	}
	
	public static String getAppPraiseCacheDirectory() {

		return getRootDirectory() + "praise_cache";
	}

	public static String getAppInputHistoryCacheDirectory() {

		return getRootDirectory() + "input_history";
	}

	public static String getAppSubscribeKeywordCacheDirectory() {

		return getAppCacheDirectory() + "keyword";
	}

	public static String getAppInstitutionSystemCacheDirectory() {

		return getAppCacheDirectory() + "system";
	}

	public static String getAppInstitutionAreaCacheDirectory() {

		return getAppCacheDirectory() + "area";
	}

	public static String getAppInstitutionAboutCacheDirectory() {

		return getAppCacheDirectory() + "inabout";
	}

	public static String getAppInstitutionSysChildCacheDirectory() {
		return getAppCacheDirectory() + "syschild";
	}

	public static String getAppInstitutionAreaChildCacheDirectory() {
		return getAppCacheDirectory() + "areachild";
	}

	public static String getAppLocalWeatherCacheDirectory() {
		return getAppCacheDirectory() + "localweather";
	}

	public static String getAppLocalCityCacheDirectory() {
		return getAppCacheDirectory() + "localcity";
	}

	public static void clear(String diskCacheDirectory) {
		File outFile = new File(diskCacheDirectory);
		outFile.mkdirs();
		boolean isDiskCacheEnabled = outFile.exists();
		if (isDiskCacheEnabled) {
			File cachedFiles[] = (new File(diskCacheDirectory)).listFiles();
			if (cachedFiles == null)
				return;
			File afile[];
			int j = (afile = cachedFiles).length;
			for (int i = 0; i < j; i++) {
				File f = afile[i];
				f.delete();
			}

		}
	}

	public static String getSDPath() {
		String sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory().toString();// 获取跟目录
		} else {
			sdDir = getRootDirectory();
		}
		return sdDir;

	}

	public static String getSystemCameraDirectory() {

		return getSDPath() + "/DCIM/Camera";
	}

	public static String getImageDirectory() {
		return getRootDirectory() + "images/";
	}

	/**
	 * 清除SD卡图片(3天前的图片)
	 */
	public static void autoClearSDCardImage() {
		clearSDCardImage(3);
	}

	private static void deleteFile(File f) {
		// String name=f.getName();
		// if(name.matches(XG_AVATAR_FLAG) || name.matches(XG_AVATAR_FLAG1) ||
		// name.matches(SINA_AVATAR_FLAG) ||
		// name.matches(QQ_AVATAR_FLAG) || name.matches(NETEASE_AVATAR_FLAG)){
		// return;
		// }
		f.delete();
	}

	/**
	 * @param daysBefore
	 *            清理daysBefore天前的图片缓存
	 */
	public static void clearSDCardImage(int daysBefore) {
		String imageCachePath = getAppImageCacheDirectory();

		Calendar time = Calendar.getInstance();
		time.add(Calendar.DAY_OF_MONTH, -daysBefore);

		long someDaysBefore = time.getTimeInMillis();

		File[] cachedFiles = new File(imageCachePath).listFiles();
		if (cachedFiles == null) {
			return;
		}
		for (File f : cachedFiles) {
			if (f.lastModified() < someDaysBefore) {
				deleteFile(f);
			}
		}

	}

	/**
	 * 清除SD卡所有可以删除的图片
	 * 
	 * @param context
	 */
	public static void clearAllSDCardImage(Context context) {
		String imageCachePath = getAppImageCacheDirectory();

		File[] cachedFiles = new File(imageCachePath).listFiles();
		if (cachedFiles == null) {
			return;
		}
		for (File f : cachedFiles) {
			deleteFile(f);
		}
	}

	public static boolean isSDCardNeedClearCache(Context context) {
		boolean flag = false;
		String imageCachePath = getAppImageCacheDirectory();
		double size = getFileSize(new File(imageCachePath));
		if (size >= 2 * 100 * 1024 * 1024) {
			flag = true;
		}
		return flag;
	}

	// 获取当前缓存大小
	public static String getCurrentCacheSize(Context context) {
		StringBuilder sb = new StringBuilder(6);
		String imageCachePath = getAppImageCacheDirectory();
		double size = getFileSize(new File(imageCachePath));
		// String webViewCachePath = context.getCacheDir().getAbsolutePath() +
		// "/" + "webviewCache";
		// size+=getFileSize(new File(webViewCachePath));
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("0.00");
		if (size < (1024 * 1024)) {
			sb.append(df.format(size / (1024))).append("KB");
		} else if (size < (1024 * 1024 * 1024)) {
			sb.append(df.format(size / (1024 * 1024))).append("MB");
		} else {
			sb.append(df.format(size / (1024 * 1024 * 1024))).append("GB");
		}
		return sb.toString();
	}

	// 递归
	public static long getFileSize(File f)// 取得文件夹大小
	{
		long size = 0;
		File flist[] = f.listFiles();
		if (flist == null) {
			return 0;
		}
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}
}

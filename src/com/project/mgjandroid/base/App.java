package com.project.mgjandroid.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Intent;

import com.baidu.mapapi.SDKInitializer;
import com.project.mgjandroid.BuildConfig;
import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.SmsLoginModel.ValueEntity.AppUserEntity;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.HomeActivity;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import org.litepal.LitePalApplication;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


public class App extends LitePalApplication{
	private List<WeakReference<Activity>> mActivities = new LinkedList<>();
	private HashMap<String, Object> cache = new HashMap<String, Object>();
	private static App instance = null;
	public static final String KEY_SYSTEM_MAX_MEMORY = "system_max_memory";
	public static final String KEY_COVER_IMAGE_PATH = "CoverImagePath";
	private boolean isAppRunning;
	private static AppUserEntity userInfo;
	private static boolean isLogin = false;
	private static boolean isUserInfoChange = false;
	private static IWXAPI api;
	private static Tencent mTencent;

	public static boolean isLogin() {
		return isLogin;
	}

	public static void setIsLogin(boolean isLogin) {
		App.isLogin = isLogin;
	}

	public static boolean isUserInfoChange() {
		return isUserInfoChange;
	}

	public static void setIsUserInfoChange(boolean isUserInfoChange) {
		App.isUserInfoChange = isUserInfoChange;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		SDKInitializer.initialize(getApplicationContext());
//		LocationManager.getIManager().registeLocationAuto(getApplicationContext());

		api = WXAPIFactory.createWXAPI(getApplicationContext(), Constants.WE_CHAT_APP_ID, true);
		// 将该app注册到微信
		api.registerApp(Constants.WE_CHAT_APP_ID);
		// 创建该app的QQ实例
		mTencent = Tencent.createInstance(getString(R.string.tencent_appid), getApplicationContext());
		//JPush注册
		JPushInterface.setDebugMode(false);
		JPushInterface.init(this);

		if(!BuildConfig.IS_DEBUG) {
			Thread.setDefaultUncaughtExceptionHandler(new MyUnCaughtExceptionHandle());
		}
		appLaunch();
	}

	public static IWXAPI getApi() {
		return api;
	}

	public static Tencent getmTencent() {
		return mTencent;
	}

	public WeakReference<Activity> addActivity(Activity activity) {
		synchronized (mActivities) {
			WeakReference<Activity> reference = new WeakReference<Activity>(activity);
			mActivities.add(reference);
			return reference;
		}
	}
    
	public int getCurrentRunningActivitySize() {

		return mActivities.size();
	}


	public String getTopActivity() {

		ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

		if (runningTaskInfos != null)
			return (runningTaskInfos.get(0).topActivity).getClassName().toString();
		else
			return null;
	}

	public void removeActivity(WeakReference<Activity> reference) {
		synchronized (mActivities) {
			mActivities.remove(reference);
		}
	}

	public void exit() {
		synchronized (mActivities) {
			for (WeakReference<Activity> activityRef : mActivities) {
				Activity activity = activityRef.get();
				if (activity != null) {
					activity.finish();
				}
			}
			mActivities.clear();
		}
	}

	public static App getInstance() {
		if (instance == null) {
			throw new NullPointerException("app not create should be terminated!");
		}
		return instance;
	}

	public Object get(String key) {
		return cache.get(key);
	}

	public Object remove(String key) {
		return cache.remove(key);
	}

	public void put(String key, Object value) {
		cache.put(key, value);
	}

	public void clearCache() {
		cache.clear();
	}

	public int getSystemMaxMemory() {
		Integer memory = (Integer) cache.get(KEY_SYSTEM_MAX_MEMORY);
		if (memory == null) {
			return 16;
		}
		return memory;
	}

	public void saveSystemMaxMemory(int maxMemory) {
		cache.put(KEY_SYSTEM_MAX_MEMORY, maxMemory);
	}

	public synchronized void init() {
	}

	public boolean isAppRunning() {
		return isAppRunning;
	}

	public void setAppRunning(boolean isAppRunning) {
		this.isAppRunning = isAppRunning;
	}
	
	public String getCoverPath() {
		return (String) cache.get(KEY_COVER_IMAGE_PATH);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		exit();
//		DBConnectManager.closeDB();
//		Utils.setLogText(getApplicationContext(), Utils.logStringCache);
	}

	public static AppUserEntity getUserInfo() {
		return userInfo;
	}

	public static void setUserInfo(AppUserEntity userInfo) {
		App.userInfo = userInfo;
	}

	class MyUnCaughtExceptionHandle implements Thread.UncaughtExceptionHandler{
		@Override
		public void uncaughtException(Thread thread, Throwable ex) {

			ex.printStackTrace();

			Intent intent = new Intent(instance, HomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			instance.startActivity(intent);

			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	/**
	 * app Launch
	 */
	private void appLaunch() {
		VolleyOperater<Object> operater = new VolleyOperater<>(getApplicationContext());
		operater.doRequest(Constants.URL_APP_LAUNCH, null, new VolleyOperater.ResponseListener() {

			@Override
			public void onRsp(boolean isSucceed, Object obj) {
				if (isSucceed && obj!= null) {

				}
			}
		}, Object.class);
	}
}

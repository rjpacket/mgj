package com.project.mgjandroid.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

import com.project.mgjandroid.base.App;

public class PreferenceUtils {

	public static final String CONFIG_FILE_NAME = "config";// config.xml

	public static final String THEME_SETTING = "theme_setting";// 主题设置

	// 系统内存
	public static final String SYSTEM_MAX_MEMORY = "system_max_memory";
	public static final String STATUS_BAR_HEIGHT = "statusBarHeight";// 状态栏高度
	public static final String PHYSICAL_HEIGHT = "physicalHeight";// 物理高度
	public static final String PHYSICAL_WIDTH = "physicalWidth";// 物理宽度
	public static final String UUID = "uuid";
	public static final String STATUS_BAR_HEIGHT_XLARGE = "statusBarHeight_xlarge";
	public static final String PHYSICAL_HEIGHT_XLARGE = "physicalHeight_xlarge";
	public static final String PHYSICAL_WIDTH_XLARGE = "physicalWidth_xlarge";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String ADDRESS_NAME = "address_name";
	public static final String ADDRESS_DES = "address_des";
	public static final String ADDRESS_CITY = "address_city";
	public static final String ADDRESS_CITY_CODE = "address_city_code";
	public static final String MINUS_TRANSLATION_X = "minus_translationX";
	public static final String COUNT_TRANSLATION_X = "count_translationX";

	public static final String MY_MINUS = "my_minus_translationX";
	public static final String MY_COUNT = "my_count_translationX";
	public static final String LAST_IN_VESION_CODE = "last_in_version_code";

	// 获取状态栏的高度
	public static int getStatusBarHeight(Context context) {
		if (DeviceParameter.isDeviceXLarge()) {
			return getIntPreference(STATUS_BAR_HEIGHT_XLARGE, 0, context);
		} else {
			return getIntPreference(STATUS_BAR_HEIGHT, 0, context);
		}
	}

	// 保存状态栏的高度
	public static void saveStatusBarHeight(int statusBarHeight, Context context) {
		if (DeviceParameter.isDeviceXLarge()) {
			saveIntPreference(STATUS_BAR_HEIGHT_XLARGE, statusBarHeight);
		} else {
			saveIntPreference(STATUS_BAR_HEIGHT, statusBarHeight);
		}
	}

	public static String[] getLocation(Context context) {
		String latitude = getStringPreference(LATITUDE, "", context);
		String longitude = getStringPreference(LONGITUDE, "", context);
		if (CheckUtils.isNoEmptyStr(latitude) && CheckUtils.isNoEmptyStr(longitude)) {
			return new String[] { latitude, longitude };
		} else {
			return null;
		}
	}

	public static void saveLocation(String latitude, String longitude, Context context) {
		saveStringPreference(LATITUDE, latitude, context);
		saveStringPreference(LONGITUDE, longitude, context);
	}

	public static String getAddressName(Context context) {
		return getStringPreference(ADDRESS_NAME, "", context);
	}

	public static void saveAddressName(String address, Context context) {
		saveStringPreference(ADDRESS_NAME, address, context);
	}

	public static String getAddressDes(Context context) {
		return getStringPreference(ADDRESS_DES, "", context);
	}

	public static void saveAddressDes(String address, Context context) {
		saveStringPreference(ADDRESS_DES, address, context);
	}

	public static String getAddressCityCode(Context context) {
		return getStringPreference(ADDRESS_CITY_CODE, "", context);
	}

	public static void saveAddressCityCode(String cityCode, Context context) {
		saveStringPreference(ADDRESS_CITY_CODE, cityCode, context);
	}

	public static String getAddressCity(Context context) {
		return getStringPreference(ADDRESS_CITY, "", context);
	}

	public static void saveAddressCity(String city, Context context) {
		saveStringPreference(ADDRESS_CITY, city, context);
	}


	public static int getVersionCode(Context context) {
		return getIntPreference(LAST_IN_VESION_CODE, 0, context);
	}

	public static void saveVersionCode(int code, Context context) {
		saveIntPreference(LAST_IN_VESION_CODE, code);
	}

	// 保存配置key-value
	public static void saveStringPreference(String keyName, String value, Context context) {
		Editor editor = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).edit();
		editor.putString(keyName, value);
		editor.commit();
	}

	/**
	 * 获取配置值
	 * 
	 * @param keyName
	 *            key
	 * @param defValue
	 *            默认值
	 * @param context
	 * @return
	 */
	public static String getStringPreference(String keyName, String defValue, Context context) {

		return context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).getString(keyName, defValue);
	}

	/**
	 * 该键值存在且不为空
	 * 
	 * @param keyName
	 * @param context
	 * @return
	 */
	public static boolean isStringExist(String keyName, Context context) {
		String value = getStringPreference(keyName, null, context);
		return value != null && value.length() != 0;
	}

	// 获取布尔类型，默认值false
	public static boolean getBoolPreference(String keyName, Context context) {
		return context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).getBoolean(keyName, false);
	}

	// 获取布尔类型，自定义默认值
	public static boolean getBoolPreference(String keyName, boolean defaultValue, Context context) {
		return context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).getBoolean(keyName, defaultValue);
	}

	// 保存布尔类型
	public static void saveBoolPreference(String keyName, boolean value, Context context) {
		Editor editor = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).edit();
		editor.putBoolean(keyName, value);
		editor.commit();
	}

	// 获取浮点类型，自定义默认值
	public static float getFloatPreference(String keyName, float defautValue, Context context) {
		return context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).getFloat(keyName, defautValue);
	}

	// 保存浮点类型
	public static void saveFloatPreference(String keyName, float value, Context context) {
		Editor editor = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).edit();
		editor.putFloat(keyName, value);
		editor.commit();
	}

	// 获取int类型，自定义默认值
	public static int getIntPreference(String keyName, int defautValue, Context context) {
		return context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).getInt(keyName, defautValue);
	}

	// 获取long类型，......
	public static long getLongPreference(String keyName, int defautValue, Context context) {
		return context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).getLong(keyName, defautValue);
	}

	// 保存long类型
	public static void saveLongPreference(String keyName, long value, Context context) {
		Editor editor = context.getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).edit();
		editor.putLong(keyName, value);
		editor.commit();
	}

	// 保存int类型
	public static void saveIntPreference(String keyName, int value) {
		Editor editor = App.getInstance().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).edit();
		editor.putInt(keyName, value);
		editor.commit();
	}

	// 清除某个配置
	public static void removePreference(String keyName) {
		Editor editor = App.getInstance().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).edit();
		editor.remove(keyName);
		editor.commit();
	}

	public static void removeAllPreference() {
		Editor editor = App.getInstance().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE).edit();
		editor.clear();
		editor.commit();

	}
}

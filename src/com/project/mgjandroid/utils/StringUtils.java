package com.project.mgjandroid.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.content.Context;
import android.text.ClipboardManager;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.Toast;

public class StringUtils {

	/**
	 * 判断邮箱
	 */
	public static boolean isEmailRight(String email) {
		// 邮箱不能为空，要是一个合法邮箱
		if (email == null || email.trim().equals("")) {
			return false;
		} else {
			Pattern p = Pattern.compile("^([a-z0-9A-Z]+[_|-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
			// Pattern p =
			// Pattern.compile("^([a-zA-Z0-9._%+-])+@([a-zA-Z0-9._-])+(\\.([a-zA-Z0-9_-])+)+$");
			Matcher m = p.matcher(email);
			return m.matches();
			// return (email
			// .matches("^([a-z0-9][a-z0-9_\\-\\.\\+]*)@([a-z0-9][a-z0-9\\.\\-]{0,63}\\.(com|org|net|biz|info|name|net|pro|aero|coop|museum|[a-z]{2,4}))$"));
		}
	}

	/**
	 * 判断手机号
	 * 
	 * @param phoneNum
	 * @return
	 */
	public static boolean isPhoneNumRight(String phoneNum) {
		String YD = "^[1]{1}(([3]{1}[4-9]{1})|([5]{1}[012789]{1})|([8]{1}[2378]{1})|([4]{1}[7]{1}))[0-9]{8}$";
		String LT = "^[1]{1}(([3]{1}[0-2]{1})|([5]{1}[56]{1})|([8]{1}[56]{1}))[0-9]{8}$";
		String DX = "^[1]{1}(([3]{1}[3]{1})|([5]{1}[3]{1})|([8]{1}[09]{1}))[0-9]{8}$";
		/**
		 * flag = 1 YD 2 LT 3 DX
		 */
		// 判断手机号码是否是11位
		if (phoneNum.length() == 11) {
			// 判断手机号码是否符合中国移动的号码规则
			if (phoneNum.matches(YD)) {
				return true;
			}
			// 判断手机号码是否符合中国联通的号码规则
			else if (phoneNum.matches(LT)) {
				return true;
			}
			// 判断手机号码是否符合中国电信的号码规则
			else if (phoneNum.matches(DX)) {
				return true;
			}
			// 都不合适 未知
			else {
				return false;
			}
		}
		// 不是11位
		else {
			return false;
		}
	}

	/**
	 * 判断密码
	 */
	public static boolean isPhoneRight(String phone) {
		if (CheckUtils.isEmptyStr(phone)) {
			return false;
		} else {
			Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]\\d{8}$");
			Matcher m = p.matcher(phone);
			return m.matches();
		}
	}

	/**
	 * 判断密码
	 */
	public static boolean isPasswordRight(String password) {
		if (password == null || password.trim().equals("")) {
			return false;
		} else {
			if (password.length() <= 5 || password.length() > 20) {
				return false;
			}
			return true;
		}
	}

	public static boolean isRePasswordRight(String password, String rePassword) {
		if (rePassword.trim().length() > 0 && password.trim().length() > 0) {
			return password.equals(rePassword) ? true : false;
		}
		return false;
	}

	/**
	 * 判断用户名
	 */
	public static boolean isNameRight(String name) {
		int num = 0;
		for (int i = 0; i < name.length(); i++) {
			String ss = String.valueOf(name.charAt(i));
			if (ss.matches("[^\\x00-\\xff]")) {
				num = num + 2;
			} else {
				num++;
			}
		}
		if (num >= 4) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * URL编码
	 * 
	 * @param url
	 */
	public static String encodeURL(String url) {
		if (null == url) {
			return "";
		}
		try {
			url = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return url;
	}

	public static String encodeURL2(String url) {
		if (null == url) {
			return "";
		}
		try {
			url = URLEncoder.encode(url, "GBK");
		} catch (UnsupportedEncodingException e) {
		}
		return url;
	}

	/**
	 * 读取JSON String类型值 如果某属性为null，JSONObject会得到"null"字符串，此方法统一处理这类问题。
	 * 
	 * @param jsonObj
	 * @param key
	 */
	public static String getJsonString(JSONObject jsonObj, String key) {
		if (jsonObj.isNull(key)) {
			return null;
		}
		String value = jsonObj.optString(key, null);
		if (value == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer(value);
		return sb.toString();
	}

	public static String getTextFromHtml(JSONObject jsonObj, String key) {
		String s = getJsonString(jsonObj, key);
		if (s != null) {
			s = Html.fromHtml(s).toString();
		}
		return s;
	}

	/**
	 * 读取assets目录下的文本文件
	 * 
	 * @return String fileName 文件内容
	 * @author ZhengTao Date:2011-02-17
	 */

	public static String readFromAssets(Context context, String fileName) {
		InputStream is;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			is = context.getAssets().open(fileName);
			byte buf[] = new byte[1024];
			int len;
			while ((len = is.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
			baos.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toString();
	}

	// /**
	// * 获取渠道名称
	// */
	// public static String getChannelFromManifest() {
	// try {
	// Context context = App.getInstance();
	// ApplicationInfo appInfo = context.getPackageManager()
	// .getApplicationInfo(context.getPackageName(),
	// PackageManager.GET_META_DATA);
	// return appInfo.metaData.getString("UMENG_CHANNEL");
	// } catch (Exception e) {
	// }
	// return null;
	// }
	//
	// /**
	// * 获友盟渠道名称
	// */
	// public static String getChannelFromManifest(Context context) {
	// try {
	// ApplicationInfo appInfo = context.getPackageManager()
	// .getApplicationInfo(context.getPackageName(),
	// PackageManager.GET_META_DATA);
	// return appInfo.metaData.getString("UMENG_CHANNEL");
	// } catch (Exception e) {
	// }
	// return null;
	// }

	// /**
	// * 返回当前程序版本名称
	// */
	// public static String getAppVersionName() {
	// return App.getInstance().getResources().getString(R.string.app_version);
	// }

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			// System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	/**
	 * @param context
	 *            上下文
	 * @param resId
	 *            字符串资源id
	 * @param foregroundColorSpan
	 *            要设置的字体前景色
	 * @return 设置后的字体颜色
	 */
	public static SpannableString setPreferenceFontColor(Context context, int resId, ForegroundColorSpan foregroundColorSpan) {
		SpannableString spannableString = new SpannableString(context.getResources().getString(resId));
		spannableString.setSpan(foregroundColorSpan, 0, spannableString.length(), 0);
		return spannableString;
	}

	public static SpannableString setPreferenceFontColor(String str, ForegroundColorSpan foregroundColorSpan) {
		SpannableString spannableString = new SpannableString(str);
		spannableString.setSpan(foregroundColorSpan, 0, spannableString.length(), 0);
		return spannableString;
	}

	/**
	 * 去除字符串中的空格、回车、换行符、制表符 ...
	 * 
	 * 
	 * */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");

		}
		return dest;
	}

	public static String toGBK(String str) {

		if (CheckUtils.isNoEmptyStr(str)) {
			try {
				byte[] bytes = str.getBytes();
				str = new String(bytes, "gb2312");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return str;
	}

	/**
	 * 白问正文处理
	 * 
	 * */
	public static String replaceAskContent(String str) {

		if (CheckUtils.isNoEmptyStr(str)) {
			str = str.replaceAll("\r", "\\\\r");
			str = str.replaceAll("\n", "\\\\n");
		}
		return str;
	}

	@SuppressWarnings("deprecation")
	public static void copyText(Context context, String str) {
		// 得到剪贴板管理器
		ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setText(str);
		Toast.makeText(context, "已复制链接到剪贴板", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 获取'_'前后字符串
	 * 
	 * */
	public static String getStringBeforeCharacter(String str) {
		return getStringByindex(str, '_', true);
	}

	public static String getStringAfterCharacter(String str) {
		return getStringByindex(str, '_', false);

	}

	/**
	 * 获取特殊字符前后字符串
	 * 
	 * */
	public static String getStringByindex(String str, int character, boolean isBefore) {
		if (CheckUtils.isNoEmptyStr(str)) {
			int a = str.indexOf(character);
			int b = str.length();
			if (a > 0 && a + 1 < b) {
				if (isBefore) {
					str = str.substring(0, a);
				} else {
					str = str.substring(a + 1, b);
				}
			}
		}

		return str;

	}

	/**
	 * 按字节截取
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String subStringByByte(String str, int len) {
		String result = null;
		if (str != null) {
			byte[] a = str.getBytes();
			if (a.length <= len) {
				result = str;
			} else if (len > 0) {
				try {
					result = new String(a, 0, len, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int length = result.length();
				if (str.charAt(length - 1) != result.charAt(length - 1)) {
					if (length < 2) {
						result = null;
					} else {
						result = result.substring(0, length - 1);
					}
				}
			}
		}
		return result;
	}

	public static boolean isChn(String str, int position) {
		return (str.charAt(position / 3) + "").matches("[\u4E00-\u9FA5]");
	}

	public static boolean isChar(String str, int position) {
		// matches("[\u4E00-\u9FA5]");
		return (str.charAt(position / 3) + "").matches("[,./'，。、’；？：”“‘’'';!@#%:]");
	}

	/**
	 * 半角转换为全角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		if (TextUtils.isEmpty(input)) {
			return "";
		}
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 通过转码对双引号的处理
	 * 
	 * @param str
	 * @return
	 */
	public static String toUnicodeString(String str) {
		if (CheckUtils.isEmptyStr(str)) {
			return "";
		}
		String str_tmp = str.replaceAll("\"", "\\\\\"");
		StringBuffer sb = new StringBuffer();
		for (int cIdx = 0; cIdx < str_tmp.length(); cIdx++) {
			char unicodeC = str_tmp.charAt(cIdx);
			int iUnicode = (int) unicodeC;
			sb.append(String.format("^U%04x", iUnicode));
		}
		return sb.toString();
	}

	// /**
	// * 设置分隔符
	// */
	// public static String addUrlSeparator(String str) {
	// if (!CheckUtils.isEmptyStr(str)) {
	// int a = str.indexOf("?");
	// if (a != -1) {
	// str = str + "&";
	// } else {
	// str = str + "?";
	// }
	// }
	// return str;
	// }
	//
	// /**
	// * 动态的向URL添加参数
	// */
	// public static String setUrlParameter(String str, String key, String
	// value) {
	// if (!CheckUtils.isEmptyStr(str) && !CheckUtils.isEmptyStr(key)) {
	// str = StringUtils.addUrlSeparator(str) + key + "=" + value;
	// }
	// return str;
	// }

	public static String BigDecimal2Str(BigDecimal bigDecimal){
		String str = "";
		if(bigDecimal.compareTo(BigDecimal.ZERO)>=0){
			if(bigDecimal.multiply(BigDecimal.TEN).remainder(BigDecimal.ONE).floatValue()>0){
				DecimalFormat df = new DecimalFormat("0.00"); // 保留2位小数
				str = df.format(bigDecimal);
			}else if(bigDecimal.remainder(BigDecimal.ONE).floatValue()>0){
				DecimalFormat df = new DecimalFormat("0.0"); // 保留1位小数
				str = df.format(bigDecimal);
			}else{
				DecimalFormat df = new DecimalFormat("0"); // 取整
				str = df.format(bigDecimal);
			}
		}else{
			bigDecimal = bigDecimal.multiply(new BigDecimal(-1));
			if(bigDecimal.multiply(BigDecimal.TEN).remainder(BigDecimal.ONE).floatValue()>0){
				DecimalFormat df = new DecimalFormat("0.00");
				str ="-" + df.format(bigDecimal);
			}else if(bigDecimal.remainder(BigDecimal.ONE).floatValue()>0){
				DecimalFormat df = new DecimalFormat("0.0");
				str ="-" +  df.format(bigDecimal);
			}else{
				DecimalFormat df = new DecimalFormat("0");
				str ="-" +  df.format(bigDecimal);
			}
		}
		return str;
	}
}

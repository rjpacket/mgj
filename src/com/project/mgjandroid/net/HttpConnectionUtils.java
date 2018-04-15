package com.project.mgjandroid.net;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.project.mgjandroid.utils.CheckUtils;

/**
 * @author jian
 * 
 * */

public class HttpConnectionUtils {
	
	
	
	
	/**
	 * @author jian
	 * 设置分隔符
	 */
	public static String addUrlSeparator(String str) {
		if (!CheckUtils.isEmptyStr(str)) {
			int a = str.indexOf("?");
			if (a != -1) {
				str = str + "&";
			} else {
				str = str + "?";
			}
		}
		return str;
	}

	/**
	 * @author jian
	 * 动态的向URL添加参数
	 */
	public static String setUrlParameter(String str, String key, String value) {
		if (!CheckUtils.isEmptyStr(str) && !CheckUtils.isEmptyStr(key)) {
			str = addUrlSeparator(str) + key + "=" + value;
		}
		return str;
	}
	
	
	/**
	 * @author jian
	 * 动态的向URL添加参数
	 */
	public static String setUrlParameter(String url, Map<String, String> map) {
		if(!CheckUtils.isEmptyStr(url)){
			if(map!=null&&map.size()>0){
				
				Set<String> set = map.keySet();
				Iterator<String> iterator = set.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					String value = map.get(key);	
					url=setUrlParameter(url,key,value);
				}
			}
		}
		return url;
	}

}

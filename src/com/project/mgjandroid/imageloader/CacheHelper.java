package com.project.mgjandroid.imageloader;

import com.project.mgjandroid.utils.StringUtils;


/**
 * Url的处理
 *
 * @author jian
 */
public class CacheHelper {

    public static String getFileNameFromUrl(String url) {
        return StringUtils.getMD5Str(url);
    }
}
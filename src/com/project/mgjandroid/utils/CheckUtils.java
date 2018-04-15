package com.project.mgjandroid.utils;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.provider.MediaStore;
import android.text.TextUtils;

public class CheckUtils {

    /**
     * @author jian
     */

    public static boolean isEmptyList(List arg) {

        if (arg != null && arg.size() > 0) {
            return false;
        }
        return true;
    }

    public static boolean isNoEmptyList(List arg) {
        return !isEmptyList(arg);
    }

    public static boolean isEmptyStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        // 过滤空格
        str = str.trim();
        if (str.equals(" ") || str.equals("") || "NULL".equals(str) || "null".equals(str)) {
            return true;
        }
        return false;
    }

    public static boolean isNoEmptyStr(String str) {
        return !isEmptyStr(str);
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 判断系统中是否存在可以启动的相机应用
     *
     * @return 存在返回true，不存在返回false
     */
    public static boolean hasCamera(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}

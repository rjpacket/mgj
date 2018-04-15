package com.project.mgjandroid.utils;

import android.content.Context;

public class DipToPx {

	/**
	 * dip转成px
	 * 
	 * @Title: dip2px
	 * @Description dip转成px
	 * @param context
	 * @param dipValue
	 *            要转换成px单位的dp单位数据
	 * @return 设定文件
	 * @return int 返回类型 px值
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}


}

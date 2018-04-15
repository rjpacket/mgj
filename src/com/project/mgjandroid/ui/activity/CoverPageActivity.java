package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

/**
 * 欢迎页面，显示广告
 * 
 * @author jian
 * 
 */
public class CoverPageActivity extends BaseActivity {
	private static final long DELAY_LOAD_TIME = 1800;
	@InjectView(R.id.img_add_hide)
	private ImageView add;
	@InjectView(R.id.buy_count_hide)
	private TextView tvCount;
	@InjectView(R.id.img_minus_hide)
	private ImageView minus;

	@InjectView(R.id.goods_item_choose_spec1)
	private TextView add1;
	@InjectView(R.id.goods_item_tv_buy_count_spec1)
	private TextView tvCount1;
	@InjectView(R.id.goods_item_img_minus_spec1)
	private ImageView minus1;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.coverpage_act);
		Injector.get(this).inject();
		toHomeActivity();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		int[] addLocation = new int[2];
		int[] minusLocation = new int[2];
		int[] countLocation = new int[2];
		try {
			add.getLocationInWindow(addLocation);
			minus.getLocationInWindow(minusLocation);
			tvCount.getLocationInWindow(countLocation);
			PreferenceUtils.saveFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, minusLocation[0] - addLocation[0], CoverPageActivity.this);
			PreferenceUtils.saveFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, -DipToPx.dip2px(mActivity,20), CoverPageActivity.this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int[] addLocation1 = new int[2];
		int[] minusLocation1 = new int[2];
		int[] countLocation1 = new int[2];
		try {
			add1.getLocationInWindow(addLocation1);
			minus1.getLocationInWindow(minusLocation1);
			tvCount1.getLocationInWindow(countLocation1);
			PreferenceUtils.saveFloatPreference(PreferenceUtils.MY_MINUS, minusLocation1[0] - addLocation1[0] - DipToPx.dip2px(mActivity,40), CoverPageActivity.this);
			PreferenceUtils.saveFloatPreference(PreferenceUtils.MY_COUNT, -DipToPx.dip2px(mActivity,60), CoverPageActivity.this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void toHomeActivity() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent();
				if (PreferenceUtils.getVersionCode(CoverPageActivity.this) < CommonUtils.getCurrentVersionCode()) {
					intent.setClass(CoverPageActivity.this, GuideActivity.class);
				} else {
					intent.setClass(CoverPageActivity.this, HomeActivity.class);
				}
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
				finish();
			}
		}, DELAY_LOAD_TIME);
	}

}

package com.project.mgjandroid.ui.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import java.lang.ref.WeakReference;

/**
 * Activity基类
 * @author jian
 *
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener{
	private WeakReference<Activity> reference;
	/**
	 * 当前activity是否还存在
	 */
	protected boolean isActAlive;

	protected View mDecorView;
	protected Activity mActivity;
	protected LayoutInflater mInflater;
	protected Resources mResource;
	protected TextView mTitle;

	protected boolean isCanBack=true;
	public boolean isCanBack() {
		return isCanBack;
	}
	
//	public boolean isAlive() {
//		return isActAlive;
//	}
//
//	public void setCanBack(boolean isCanBack) {
//		this.isCanBack = isCanBack;
//	}
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		reference = ((App) getApplication()).addActivity(this);
		isActAlive = true;
		mDecorView = getWindow().getDecorView();
		mActivity = this;
		mInflater = LayoutInflater.from(mActivity);
		mResource = mActivity.getResources();
//		mBack = (ImageView) findViewById(R.id.common_back);
//		mBack.setOnClickListener(this);
//		mTitle = (TextView) findViewById(R.id.common_title);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		isActAlive = false;
		((App) getApplication()).removeActivity(reference);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		isActAlive = true;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		isActAlive = false;
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if(isCanBack()){
			back();
		}
	}
	protected void back() {
		this.finish();
		overridePendingTransition(R.anim.unhold, R.anim.unfade);
	}

	public void toast(int resId) {
		ToastUtils.displayMsg(resId, this);
	}

	public void toast(String msg) {
		ToastUtils.displayMsg(msg, this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.common_back:
				onBackPressed();
				break;
		}
	}

	public void setTitle(String title){
		mTitle.setText(title);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		CommonUtils.hideKeyBoard(mActivity);
		return super.onTouchEvent(event);
	}
}

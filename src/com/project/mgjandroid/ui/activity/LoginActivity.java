package com.project.mgjandroid.ui.activity;

import com.project.mgjandroid.R;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
	@InjectView(R.id.login_register)
	private TextView tv_register;
	@InjectView(R.id.login_back)
	private ImageView img_back;
	@InjectView(R.id.login_sms)
	private TextView tv_smsLogin;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.login);
		Injector.get(this).inject();
		setListener();
	}

	private void setListener() {
		tv_register.setOnClickListener(this);
		img_back.setOnClickListener(this);
		tv_smsLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.login_back:
				finish();
				break;
			case R.id.login_register:
				startActivity(new Intent(this,RegisterActivity.class));
				break;
			case R.id.login_sms:
				startActivity(new Intent(this,SmsLoginActivity.class));
				break;
		}
	}
}

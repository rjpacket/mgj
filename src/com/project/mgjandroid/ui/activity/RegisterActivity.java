package com.project.mgjandroid.ui.activity;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.GoodsListModel;
import com.project.mgjandroid.model.SendSmsModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
	@InjectView(R.id.register_back)
	private ImageView img_back;
	@InjectView(R.id.register_next)
	private Button btn_next;
	@InjectView(R.id.register_mobile)
	private EditText et_mobile;
	private Context mContext;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.register);
		mContext = this;
		Injector.get(this).inject();
		setListener();
	}

	private void setListener() {
		img_back.setOnClickListener(this);
		btn_next.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.register_back:
				finish();
				break;
			case R.id.register_next:
				if(!TextUtils.isEmpty(et_mobile.getText().toString().trim())) {
					getSMSCode();
				}
				break;
		}
	}

	/**
	 * 请求短信验证码
	 */
	private void getSMSCode() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", et_mobile.getText().toString().trim());
		VolleyOperater<SendSmsModel> operater = new VolleyOperater<SendSmsModel>(RegisterActivity.this);
		operater.doRequest(Constants.URL_GET_MSG_CODE, map, new VolleyOperater.ResponseListener() {

			@Override
			public void onRsp(boolean isSucceed, Object obj) {
				if(isSucceed){
					SendSmsModel sendSmsModel = (SendSmsModel) obj;
					ToastUtils.displayMsg(sendSmsModel.getValue(),mContext);
				}
			}
		}, SendSmsModel.class);
	}
}

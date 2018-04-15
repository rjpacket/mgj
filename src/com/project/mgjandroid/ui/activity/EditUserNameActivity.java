package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.EditUserNameModel;
import com.project.mgjandroid.model.SmsLoginModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.Map;

public class EditUserNameActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.edit_user_back)
    private ImageView editUserBack;
    @InjectView(R.id.edit_user_mobile)
    private EditText etUserName;
    @InjectView(R.id.edit_user_next)
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_name);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        editUserBack.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("name")){
            String name = intent.getStringExtra("name");
            etUserName.setText(name);
            etUserName.setSelection(name.length());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_user_back:
                finish();
                break;
            case R.id.edit_user_next:
                if(CommonUtils.checkUsername(etUserName.getText().toString())) {
                    changeUserName();
                }else{
                    ToastUtils.displayMsg("输入内容不合法",mActivity);
                }
                break;
        }
    }

    private void changeUserName() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", etUserName.getText().toString().trim());
        VolleyOperater<EditUserNameModel> operater = new VolleyOperater<>(EditUserNameActivity.this);
        operater.doRequest(Constants.URL_CHANGE_USERNAME, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if(isSucceed && obj != null){
                    EditUserNameModel editUserNameModel = (EditUserNameModel) obj;
                    if(editUserNameModel.isSuccess()){
                        SmsLoginModel.ValueEntity.AppUserEntity userInfo = App.getUserInfo();
                        userInfo.setName(etUserName.getText().toString().trim());
                        App.setIsUserInfoChange(true);
                        Intent data = new Intent();
                        data.putExtra("newName",etUserName.getText().toString().trim());
                        setResult(UserInfoActivity.EDIT_USER_NAME_SUCCESS, data);
                        finish();
                    }else{
                        ToastUtils.displayMsg("换个昵称试试吧",mActivity);
                    }
                }
            }
        }, EditUserNameModel.class);
    }
}

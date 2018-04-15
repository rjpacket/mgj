package com.project.mgjandroid.ui.activity.homemaking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

/**
 * Created by yuandi on 2016/7/23.
 * 
 */
public class HomemakingTypeActivity extends BaseActivity{
    public final static String TYPE_ID = "type_id";
    public final static String TYPE_NAME = "type_name";

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.tv_person)
    private TextView tvPerson;
    @InjectView(R.id.tv_company)
    private TextView tvCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_making_type);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        tvPerson.setOnClickListener(this);
        tvCompany.setOnClickListener(this);
        tvTitle.setText("选择身份");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_person:
                Intent int1 = new Intent();
                int1.putExtra(TYPE_ID, 1);
                int1.putExtra(TYPE_NAME, "个人");
                setResult(RESULT_OK, int1);
                back();
                break;
            case R.id.tv_company:
                Intent int2 = new Intent();
                int2.putExtra(TYPE_ID, 2);
                int2.putExtra(TYPE_NAME, "商家");
                setResult(RESULT_OK, int2);
                back();
                break;
        }
    }
}

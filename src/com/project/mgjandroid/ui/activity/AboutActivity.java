package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

public class AboutActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.about_back)
    private ImageView aboutBack;
    @InjectView(R.id.about_version_code)
    private TextView tvCode;
    @InjectView(R.id.about_business)
    private View business;
    @InjectView(R.id.user_service_protocol)
    private TextView tvProtocol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Injector.get(this).inject();

        aboutBack.setOnClickListener(this);
        business.setOnClickListener(this);
        tvProtocol.setOnClickListener(this);
        tvCode.setText(getString(R.string.v)+CommonUtils.getCurrentVersionName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about_back:
                finish();
                break;
            case R.id.about_business:
//                startActivity(new Intent(mActivity, BusinessActivity.class));
                Intent intent = new Intent(mActivity,Banner2WebActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name","商务合作");
                bundle.putString("url","http://120.24.16.64/maguanjia/Buscooperation.html");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.user_service_protocol:
                Intent intent1 = new Intent(mActivity,Banner2WebActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("name","用户服务协议");
                bundle1.putString("url","http://120.24.16.64/maguanjia/ItemAndhttp.html");
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;
        }
    }
}

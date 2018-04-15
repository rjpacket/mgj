package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.RecruitBean;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.text.SimpleDateFormat;

/**
 * Created by yuandi on 2016/6/22.
 *
 */
public class RecruitInfomationActivity extends BaseActivity{

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.tv_name)
    private TextView tvName;
    @InjectView(R.id.tv_sticky_post)
    private TextView tvSticky;
    @InjectView(R.id.tv_publish_time)
    private TextView tvPublishTime;
    @InjectView(R.id.tv_category)
    private TextView tvCategory;
    @InjectView(R.id.tv_job_description)
    private TextView tvDesc;
    @InjectView(R.id.tv_call)
    private TextView tvCall;
    @InjectView(R.id.recruit_info_title)
    private TextView tvRecruitTitle;

    private CustomDialog dialog;
    private RecruitBean recruitBean;
    private SimpleDateFormat format = new SimpleDateFormat("发布时间：yyyy-MM-dd HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit_infomation);
        Injector.get(this).inject();
        recruitBean = (RecruitBean) getIntent().getSerializableExtra("recruit_bean");
        initView();
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        if(recruitBean != null && recruitBean.getType() == 1){
            tvTitle.setText("求职详情");
            tvRecruitTitle.setText("个人简介：");
        } else {
            tvTitle.setText("招聘详情");
            tvRecruitTitle.setText("职位描述：");
        }
        tvCall.setOnClickListener(this);

        tvName.setText(recruitBean.getName());
        tvCategory.setText("类别：" + recruitBean.getPositionCategoryName());
        tvPublishTime.setText(format.format(recruitBean.getCreateTime()));
        tvDesc.setText(recruitBean.getDescription());
        if(recruitBean.getIsTop() != 0) {
            tvSticky.setText("已置顶");
            tvSticky.setTextColor(getResources().getColor(R.color.red_job));
            tvSticky.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.tv_call) {
            showDialog(recruitBean.getMobile());
        }
    }

    private void showDialog(final String mobile) {
        if (dialog != null) {
            dialog.show();
            return;
        }
        dialog = new CustomDialog(mActivity, new CustomDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DIAL");
                intent.setData(Uri.parse(String.format(getString(R.string.withdraw_phone), mobile)));
                mActivity.startActivity(intent);
                dialog.dismiss();
            }
            @Override
            public void onExit() {
                dialog.dismiss();
            }
        },"呼叫","取消","联系电话",mobile);

        dialog.show();
    }
}

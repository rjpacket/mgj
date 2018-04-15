package com.project.mgjandroid.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.model.PublishSecondHandInfoModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuandi on 2016/7/13.
 *
 */
public class TipOffActivity extends BaseActivity{
    @InjectView(R.id.common_back) private ImageView ivBack;
    @InjectView(R.id.common_title) private TextView tvTitle;
    @InjectView(R.id.report) private TextView tvReport;
    @InjectView(R.id.report_act_tv_1) private TextView tvReport1;
    @InjectView(R.id.report_act_tv_2) private TextView tvReport2;
    @InjectView(R.id.report_act_tv_3) private TextView tvReport3;
    @InjectView(R.id.report_act_tv_4) private TextView tvReport4;
    @InjectView(R.id.report_act_tv_5) private TextView tvReport5;
    @InjectView(R.id.report_act_tv_6) private TextView tvReport6;

    private ArrayList<String> tipOffs = new ArrayList<>();
    private ArrayList<View> viewList = new ArrayList<>();
    private String[] tips = { "垃圾营销", "有害信息", "淫秽色情", "不实信息", "违法信息", "人身攻击" };
    private long id;
    private MLoadingDialog dialog;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_off);
        Injector.get(this).inject();
        id = getIntent().getLongExtra("second_hand_info_id", -1);
        mUrl = getIntent().getStringExtra("url");
        initView();
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        tvTitle.setText("举报");

        tvReport.setOnClickListener(this);
        tvReport1.setOnClickListener(this);
        tvReport2.setOnClickListener(this);
        tvReport3.setOnClickListener(this);
        tvReport4.setOnClickListener(this);
        tvReport5.setOnClickListener(this);
        tvReport6.setOnClickListener(this);

        viewList.add(tvReport1);
        viewList.add(tvReport2);
        viewList.add(tvReport3);
        viewList.add(tvReport4);
        viewList.add(tvReport5);
        viewList.add(tvReport6);

        dialog = new MLoadingDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.common_back:
                back();
                break;
            case R.id.report_act_tv_1:
                clickTips(0);
                break;
            case R.id.report_act_tv_2:
                clickTips(1);
                break;
            case R.id.report_act_tv_3:
                clickTips(2);
                break;
            case R.id.report_act_tv_4:
                clickTips(3);
                break;
            case R.id.report_act_tv_5:
                clickTips(4);
                break;
            case R.id.report_act_tv_6:
                clickTips(5);
                break;
            case R.id.report:
                report();
                break;
        }
    }

    private void clickTips(int position) {
        if(viewList.get(position).isSelected()) {
            viewList.get(position).setSelected(false);
            tipOffs.remove(tips[position]);
        }else{
            viewList.get(position).setSelected(true);
            tipOffs.add(tips[position]);
        }
        checkReportEnabled();
    }

    private void checkReportEnabled() {
        if (tipOffs.size() > 0) {
            tvReport.setEnabled(true);
        } else {
            tvReport.setEnabled(false);
        }
    }

    private void report() {
        dialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        StringBuffer stringBuffer = new StringBuffer();
        for(String tip:tipOffs){
            stringBuffer.append(tip + ";");
        }
        map.put("reason", stringBuffer.toString());
        VolleyOperater<PublishSecondHandInfoModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(mUrl, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                dialog.dismiss();
                if (isSucceed && obj != null) {
                    if(obj instanceof String) {
                        toast(obj.toString());
                        return;
                    }
                    PublishSecondHandInfoModel publishModel = (PublishSecondHandInfoModel) obj;
                    if (publishModel.getCode() == 0) {
                        toast("已举报");
                        finish();
                    }
                }
            }
        }, PublishSecondHandInfoModel.class);
    }
}

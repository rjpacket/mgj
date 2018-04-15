package com.project.mgjandroid.ui.activity;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;

public class ReportActivity extends BaseActivity{
    @InjectView(R.id.common_back)     private ImageView ivBack;
    @InjectView(R.id.common_title)    private TextView tvTitle;
    @InjectView(R.id.report_who)      private TextView tvWho;
    @InjectView(R.id.report_content)  private TextView tvContent;
    @InjectView(R.id.report)           private TextView tvReport;
    @InjectView(R.id.report_act_tv_1) private TextView tvReport1;
    @InjectView(R.id.report_act_tv_2) private TextView tvReport2;
    @InjectView(R.id.report_act_tv_3) private TextView tvReport3;
    @InjectView(R.id.report_act_tv_4) private TextView tvReport4;
    @InjectView(R.id.report_act_tv_5) private TextView tvReport5;
    @InjectView(R.id.report_act_tv_6) private TextView tvReport6;

    private String name, content;
    private ArrayList<String> reports = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Injector.get(this).inject();
        name = getIntent().getStringExtra("name");
        content = getIntent().getStringExtra("content");
        name = "尚先生";
        content = "奇偶奥法国灰暗";
        initView();
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        tvTitle.setText("举报");
        String txtName = "举报" + name + "发布的信息：";
        String txtContent = name + "：" + content;
        setColorText(tvWho, txtName, 2, (2 + name.length()), 0xFF598ABF);
        setColorText(tvContent, txtContent, 0, name.length(), 0xFF598ABF);

        tvReport.setOnClickListener(this);
        tvReport1.setOnClickListener(this);
        tvReport2.setOnClickListener(this);
        tvReport3.setOnClickListener(this);
        tvReport4.setOnClickListener(this);
        tvReport5.setOnClickListener(this);
        tvReport6.setOnClickListener(this);
    }


    private void setColorText(TextView tvWho, String str ,int start , int end ,int color) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
//        style.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.blue_dark)), 3, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new TextAppearanceSpan(null, 0, getResources().getDimensionPixelSize(R.dimen.my_text_size), null, null), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        style.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvWho.setText(style);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.common_back:
                onBackPressed();
                break;
            case R.id.report_act_tv_1:
                if(tvReport1.isSelected()) {
                    tvReport1.setSelected(false);
                    reports.remove("垃圾营销");
                }else{
                    tvReport1.setSelected(true);
                    reports.add("垃圾营销");
                }
                break;
            case R.id.report_act_tv_2:
                if(tvReport2.isSelected()) {
                    tvReport2.setSelected(false);
                    reports.remove("有害信息");
                }else{
                    tvReport2.setSelected(true);
                    reports.add("有害信息");
                }
                break;
            case R.id.report_act_tv_3:
                if(tvReport3.isSelected()) {
                    tvReport3.setSelected(false);
                    reports.remove("淫秽色情");
                }else{
                    tvReport3.setSelected(true);
                    reports.add("淫秽色情");
                }
                break;
            case R.id.report_act_tv_4:
                if(tvReport4.isSelected()) {
                    tvReport4.setSelected(false);
                    reports.remove("不实信息");
                }else{
                    tvReport4.setSelected(true);
                    reports.add("不实信息");
                }
                break;
            case R.id.report_act_tv_5:
                if(tvReport5.isSelected()) {
                    tvReport5.setSelected(false);
                    reports.remove("违法信息");
                }else{
                    tvReport5.setSelected(true);
                    reports.add("违法信息");
                }
                break;
            case R.id.report_act_tv_6:
                if(tvReport6.isSelected()) {
                    tvReport6.setSelected(false);
                    reports.remove("人身攻击");
                }else{
                    tvReport6.setSelected(true);
                    reports.add("人身攻击");
                }
                break;
            case R.id.report:
                report();
                break;
        }
    }

    private void report() {
        toast("提交");
    }
}

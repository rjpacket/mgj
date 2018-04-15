package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

/**
 * Created by rjp on 2016/4/28.
 * Email:rjpgoodjob@gmail.com
 */
public class AddCautionActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.add_caution_act_iv_back)
    private ImageView imgBack;
    @InjectView(R.id.add_caution_act_edit_text)
    private EditText etCaution;
    @InjectView(R.id.add_caution_act_tv_count)
    private TextView tvWordCount;
    @InjectView(R.id.add_caution_act_confirm)
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_add_caution);
        Injector.get(this).inject();
        initViews();
        initData();
    }

    private void initViews() {
        imgBack.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        etCaution.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvWordCount.setText(String.format(getString(R.string.caution_word_count), String.valueOf(s.length())));
            }
        });
    }

    private void initData() {
        String caution = getIntent().getStringExtra("caution");
        etCaution.setText(caution);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_caution_act_iv_back :
                back();
                break;
            case R.id.add_caution_act_confirm :
                Intent intent = new Intent();
                intent.putExtra("caution", etCaution.getText().toString().trim());
                AddCautionActivity.this.setResult(ConfirmOrderActivity.RESPONSE_SET_CAUTION, intent);
                back();
                break;
        }
    }
}

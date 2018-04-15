package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

public class AddBankCardActivity extends BaseActivity {
    public static final String TAG = "AddBankCardActivity";
    @InjectView(R.id.common_back)
    private ImageView imgBack;
    @InjectView(R.id.card_number_delete)
    private ImageView imgDelete;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.add_card_next)
    private TextView tvNext;
    @InjectView(R.id.card_number)
    private EditText etNumber;
    @InjectView(R.id.root_view)
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_card);
        Injector.get(this).inject();
        tvTitle.setText(getString(R.string.add_card));
        imgBack.setOnClickListener(this);
        initView();
    }

    private void initView() {
        tvNext.setOnClickListener(this);
        rootView.setOnClickListener(this);
        imgDelete.setOnClickListener(this);
        etNumber.addTextChangedListener(new TextWatcher() {

            private int oldLength = 0;
            private boolean isChange = true;
            private int curLength = 0;
            private int emptyNumB = 0;
            private int emptyNumA = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                oldLength = s.length();
                emptyNumB = 0;
                for (int i = 0; i < s.toString().length(); i++) {
                    if (s.charAt(i) == ' ') emptyNumB++;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                curLength = s.length();
                isChange = !(curLength == oldLength || curLength <= 3);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChange) {
                    int selectIndex = etNumber.getSelectionEnd();//获取光标位置
                    String content = s.toString().replaceAll(" ", "");
                    StringBuilder sb = new StringBuilder(content);
                    //遍历加空格
                    int index = 1;
                    emptyNumA = 0;
                    for (int i = 0; i < content.length(); i++) {
                        if ((i + 1) % 4 == 0) {
                            sb.insert(i + index, " ");
                            index++;
                            emptyNumA++;
                        }
                    }
                    String result = sb.toString();
                    if (result.endsWith(" ")) {
                        result = result.substring(0, result.length() - 1);
                    }
                    etNumber.setText(result);
                    if (emptyNumA > emptyNumB)
                        selectIndex = selectIndex + (emptyNumA - emptyNumB);
                    //处理光标位置
                    if (selectIndex > result.length() || selectIndex + 1 == result.length()) {
                        selectIndex = result.length();
                    } else if (selectIndex < 0) {
                        selectIndex = 0;
                    }
                    etNumber.setSelection(selectIndex);
                    isChange = false;
                }

                String str = etNumber.getText().toString().trim();
                str = str.replace(" ", "");
                if (TextUtils.isEmpty(str) || str.length() < 16 || str.length() > 19) {
                    tvNext.setEnabled(false);
                } else {
                    tvNext.setEnabled(true);
                }
            }
        });

        CommonUtils.showKeyBoard(etNumber);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.add_card_next:
                String str = etNumber.getText().toString().trim();
                str = str.replace(" ","");
                Intent intent = new Intent(this, BankCardInfoActivity.class);
                intent.putExtra("cardNumber",str);
                startActivity(intent);
                break;
            case R.id.card_number_delete:
                etNumber.setText("");
                imgDelete.setVisibility(View.INVISIBLE);
                break;
            case R.id.root_view:
                CommonUtils.hideKeyBoard2(etNumber);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CommonUtils.hideKeyBoard2(etNumber);
        return super.onTouchEvent(event);
    }
}

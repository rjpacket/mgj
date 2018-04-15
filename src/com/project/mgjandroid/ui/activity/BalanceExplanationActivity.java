package com.project.mgjandroid.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

/**
 * Created by User_Cjh on 2016/4/7.
 */
public class BalanceExplanationActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.balance_explanation_act_back)
    private ImageView back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_explanation);
        Injector.get(this).inject();
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.balance_explanation_act_back:
                finish();
                break;
        }
    }
}
